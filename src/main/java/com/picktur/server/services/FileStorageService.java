package com.picktur.server.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.picktur.server.constants.FileResolution;
import com.picktur.server.controllers.photo_upload.ResizedImageContainer;
import com.picktur.server.entities.User;
import com.picktur.server.entities.photo_upload.SubmitStatus;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;
import com.picktur.server.relations.TemporaryUploaded;
import com.picktur.server.repositories.documents.TemporaryPhotoRepo;
import com.picktur.server.repositories.relationships.TemporaryUploadedRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.Instant;
import java.util.Date;

@Service
public class FileStorageService {

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
    @Autowired
    TemporaryPhotoRepo temporaryPhotoRepo;
    @Autowired
    DtoPopulators populators;
    @Autowired
    TemporaryUploadedRepo temporaryUploadedRepo;
    @Autowired
    ImageService imageService;
    @Value("${photos.serverAddress}")
    String serverAddress;
    @Value("${photos.pattern}")
    String pattern;


    BufferedImage watermarkImage ;

    {
        try {File watermark;
            watermark = new File("/watermark.png");
            watermarkImage = ImageIO.read(watermark);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    public String uploadMultipartFile(MultipartFile multipartFile, FileResolution resolution) {

        String fileName = "";
        //String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            fileName = generateFileName(multipartFile);
            //fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket((resolution + fileName), file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @SneakyThrows
    public String uploadBufferedFile(BufferedImage bufferedImage, String fileName, FileResolution resolution) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", os);
        byte[] buffer = os.toByteArray();
        InputStream is = new ByteArrayInputStream(buffer);

        String uploadFileName = (resolution + fileName);

        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(buffer.length);
        s3client.putObject(new PutObjectRequest(bucketName, uploadFileName, is, meta));

        return uploadFileName;

    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadAndPersistAvatarAndIcon(MultipartFile file, FileResolution resolution) throws IOException {
        String fileName = uploadMultipartFile(file, resolution);
        String fileUrl = endpointUrl + "/" + bucketName + "/" + resolution + fileName;
        // ToDO: external parameter for server Address and pattern

        String result = fileUrl.replaceAll(pattern, serverAddress);
        return result;
    }

    public TemporaryPhoto uploadAndPersistFile(String photoCollection, MultipartFile file, User user) throws IOException {
        String fileName = uploadMultipartFile(file, FileResolution.FR_);
        String fileUrl = endpointUrl + "/" + bucketName + "/" + FileResolution.FR_ + fileName;
        // ToDO: external parameter for server Address and pattern

        String FR_fileName = fileUrl.replaceAll(pattern, serverAddress);

        // ToDO: transform photo to PhotoDto
        TemporaryPhoto result = new TemporaryPhoto();
        result.setUrl_fr(FR_fileName);
        result.setUrl_hr(FR_fileName);
        result.setUrl_mr(FR_fileName);
        result.setUrl_lr(FR_fileName);
        BufferedImage highResImage = resizeAndWatermarkPhoto(file, fileName, pattern, FileResolution.HR_, 600, false).getImage();
        result.setUrl_hr(resizeAndWatermarkBufferedPhoto(highResImage, fileName, pattern, FileResolution.HR_, 600, true).getUrl());
        result.setUrl_mr(resizeAndWatermarkBufferedPhoto(highResImage, fileName, pattern, FileResolution.MR_, 400, true).getUrl());

        ResizedImageContainer lowresContainer = resizeAndWatermarkBufferedPhoto(highResImage, fileName, pattern, FileResolution.LR_, 200, false);
        result.setUrl_lr(lowresContainer.getUrl());
        result.setLr_width(lowresContainer.getLr_width());
        result.setLr_heigh(lowresContainer.getLr_heigh());

        result.setSubmitStatus(SubmitStatus.TO_BE_SUBMITTED);
        result.setUploadInstant(Instant.now());
        result.setContainedTags(populators.extractTags(file));
        result.setCollection(photoCollection);
        result = temporaryPhotoRepo.save(result);

        // Link User who uploaded
        TemporaryUploaded temporaryUploaded = new TemporaryUploaded(user, result);
        temporaryUploadedRepo.save(temporaryUploaded);

        return result;
    }

    private ResizedImageContainer resizeAndWatermarkPhoto(MultipartFile file, String fileName, String pattern, FileResolution resolution, int dimension, boolean watermarked) throws IOException {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        ResizedImageContainer response = resizeAndWatermarkBufferedPhoto(originalImage, fileName, pattern, resolution, dimension, watermarked);
        response.setLr_width(originalImage.getWidth());
        response.setLr_heigh(originalImage.getHeight());
        return response;
    }

    private ResizedImageContainer resizeAndWatermarkBufferedPhoto(BufferedImage originalImage, String fileName, String pattern, FileResolution resolution, int dimension, boolean watermarked) {
        BufferedImage thumbnail = null;
        if (!watermarked) thumbnail = imageService.resize(originalImage, dimension);

        if (watermarked) thumbnail = imageService.resizeAndMarkImage(originalImage, watermarkImage, dimension);

        String _fileName = uploadBufferedFile(thumbnail, fileName, resolution);
        String _fileUrl = endpointUrl + "/" + bucketName + "/" + _fileName;

        ResizedImageContainer response = new ResizedImageContainer(thumbnail, _fileUrl.replaceAll(pattern, serverAddress), thumbnail.getWidth(), thumbnail.getHeight());
        return response;
    }


    /*
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }*/

}


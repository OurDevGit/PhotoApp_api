package com.picktur.server.controllers.photo_upload;

import com.picktur.server.configurations.security.UserPrincipal;
import com.picktur.server.controllers.user_management.BadRequestException;
import com.picktur.server.entities.User;
import com.picktur.server.entities.dto.photo_upload.TemporaryPhotoDto;
import com.picktur.server.entities.photo_upload.SubmitStatus;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;
import com.picktur.server.repositories.documents.AuthorizationRepo;
import com.picktur.server.repositories.documents.TemporaryPhotoRepo;
import com.picktur.server.repositories.documents.UserRepo;
import com.picktur.server.services.DtoPopulators;
import com.picktur.server.services.FileStorageService;
import com.picktur.server.services.TagService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/photo_submit")
public class PhotoSubmitController {

    private static final Logger logger = LoggerFactory.getLogger(PhotoSubmitController.class);
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    TemporaryPhotoRepo temporaryPhotoRepo;

    @Autowired
    UserRepo userRepo;
    @Autowired
    AuthorizationRepo authorizationRepo;
    @Autowired
    DtoPopulators populators;
    @Autowired
    TagService tagService;

    @Operation(summary = "Send list of photos.", description = "Send the photos to be submitted.")
    @PostMapping(value = "/uploadMultipleFiles", consumes = {MediaType.MULTIPART_MIXED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity uploadMultipleFiles(@AuthenticationPrincipal UserPrincipal principal,
                                              @RequestPart String collection,
                                              @RequestPart(value = "files", required = true) final List<MultipartFile> files) {

        User user = userRepo.findById(principal.getId()).get();

        // Batching files to upload onto server and persist with relation to collection

        files.stream().map(file -> {
            try {
                return fileStorageService.uploadAndPersistFile(collection, file, user);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toCollection(ArrayList::new));


        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Get number of photos Uploaded divided by Submit status.", description = "Get number of photos Uploaded divided by Submit status.")
    @GetMapping("/numberOfPhotosUploaded")
    public ResponseEntity<NumberOfPhotosUploadedResponse> numberOfPhotosUploaded(@AuthenticationPrincipal UserPrincipal principal) {
        NumberOfPhotosUploadedResponse response = new NumberOfPhotosUploadedResponse();

        User user = userRepo.findById(principal.getId()).get();

    /*    Iterable<TemporaryPhoto> tps = temporaryPhotoRepo.findAllByPhotoOwner(user);
       tps.forEach(a -> {
            System.out.println("Photo id: " + a.getId());
        });
    */

        Iterable<TemporaryPhoto> iterator = user.getTemporaryUpLoadedPhotos();
        iterator.forEach(p -> {
                    switch (p.getSubmitStatus()) {
                        case TO_BE_SUBMITTED:
                            response.setToBeSubmitted(response.getToBeSubmitted() + 1);
                            break;
                        case SUBMITTED:
                            response.setSubmitted(response.getSubmitted() + 1);
                            break;
                        case ACCEPTED:
                            response.setAccepted(response.getAccepted() + 1);
                            break;
                        case REJECTED:
                            response.setRejected(response.getRejected() + 1);
                            break;
                    }
                }
        );

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Operation(summary = "Get list of photos.", description = "Get all the photos To be Submitted, Submitted, Accepted, Rejected.")
    @GetMapping("/submitOperations")
    public ResponseEntity<MultiplePhotoUploadResponse> loadSubmitPage(@AuthenticationPrincipal UserPrincipal principal) {

        User user = userRepo.findById(principal.getId()).get();

        Iterable<TemporaryPhoto> iterator = user.getTemporaryUpLoadedPhotos();
        List<TemporaryPhoto> requestedPhotos = new ArrayList<>();
        iterator.forEach(requestedPhotos::add);

        //Populate Photo Array ListDto for Response
        ArrayList<TemporaryPhotoDto> requestedPhotosDto = populators.populateListOfPhotoDto(requestedPhotos);

        MultiplePhotoUploadResponse response = new MultiplePhotoUploadResponse(requestedPhotosDto);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Operation(summary = "Post changed photos's detail.", description = "Post the photos that have been changed with new state.")
    @PostMapping("/updateMultiplePhoto")
    public ResponseEntity updateMultiplePhoto(@RequestBody MultiplePhotoUpdateRequest request) {

        ArrayList<TemporaryPhotoDto> photoDtos = request.getPhotos();
        photoDtos.forEach(p -> {
            TemporaryPhoto actualPhoto = temporaryPhotoRepo.findById(p.getId()).get();
            actualPhoto.setCollection(p.getCollection());
            actualPhoto.setDescription(p.getDescription());
            actualPhoto.setTitle(p.getTitle());

            //ToDO: Update Authorizations
           /* if (p.getAuthorizations()!=null){
                ArrayList<Authorization> authorizations = new ArrayList<>();

                p.getAuthorizations().forEach(a ->{
                    Authorization actualAutorization = authorizationRepo.findById(a.getId()).get();


                });
            }*/
            actualPhoto.setCategories(p.getCategories());
            actualPhoto.setTags(p.getTags());
            temporaryPhotoRepo.save(actualPhoto);
        });

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Submit multiple photos for review.", description = "A list of photo Ids that are sent to review.")
    @PostMapping("/submitMultiplePhoto")
    public ResponseEntity submitMultiplePhoto(@AuthenticationPrincipal UserPrincipal principal, @RequestBody List<String> request) {

        changeSubmitStatus(principal, request, SubmitStatus.SUBMITTED);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Submit multiple photos for review.", description = "A list of photo Ids that are sent to review.")
    @PostMapping("/retrieve_submit_multiplePhoto")
    public ResponseEntity retrieveSubmitMultiplePhoto(@AuthenticationPrincipal UserPrincipal principal, @RequestBody List<String> request) {

        changeSubmitStatus(principal, request, SubmitStatus.TO_BE_SUBMITTED);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Add new Tag to Database.", description = "A new Tag is inserted in Db.")
    @PostMapping("/add_new_tag")
    public ResponseEntity addNewTagToDb(@AuthenticationPrincipal UserPrincipal principal, @RequestBody String newTag) {

        tagService.addNewTags(Arrays.asList(newTag));

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Test on how to use principal.", description = "It is just a test to see if works. returns Username and ID.")
    @GetMapping("/userPrincipalTest")
    public String testToDelete(@AuthenticationPrincipal UserPrincipal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        System.out.println("UserprincipalId from Security context --> " + userPrincipal.getId() + " <--");
        System.out.println("UserprincipalId from Parameter Injection --> " + principal.getId() + " <--");
        return "User is: " + principal.getUsername() + " (ID: " + principal.getId() + " )";
    }

    @PostMapping("/redeem_photos")
    public ResponseEntity redeemPhotos(@RequestBody ArrayList<String> photoToRedeem) {

        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAllByIdIn(photoToRedeem);

        photos.forEach(p -> {
            p.setSubmitStatus(SubmitStatus.TO_BE_SUBMITTED);
        });

        temporaryPhotoRepo.saveAll(photos);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }



    private void changeSubmitStatus(@AuthenticationPrincipal UserPrincipal principal, @RequestBody List<String> request, SubmitStatus newState) {
        Collection<TemporaryPhoto> userPhotos = userRepo.findById(principal.getId()).get().getTemporaryUpLoadedPhotos();
        ArrayList<String> photosIds = new ArrayList<>();
        userPhotos.forEach(up -> {
            photosIds.add(up.getId());
        });


        request.forEach(i -> {
            TemporaryPhoto actualPhoto = temporaryPhotoRepo.findById(i).get();
            if (actualPhoto != null && photosIds.contains(actualPhoto.getId())) {
                actualPhoto.setSubmitStatus(newState);
                temporaryPhotoRepo.save(actualPhoto);
            } else {
                throw new BadRequestException("Photo not found or not owned by user for id: " + i);
            }
        });
    }

}

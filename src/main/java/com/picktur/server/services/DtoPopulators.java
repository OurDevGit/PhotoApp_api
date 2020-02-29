package com.picktur.server.services;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.iptc.IptcDirectory;
import com.picktur.server.entities.*;
import com.picktur.server.entities.dto.*;
import com.picktur.server.entities.dto.photo_upload.TemporaryAuthorizationDto;
import com.picktur.server.entities.dto.photo_upload.TemporaryPhotoDto;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;
import com.picktur.server.repositories.documents.RejectingMotiveRepo;
import com.picktur.server.repositories.documents.TagRepo;
import com.picktur.server.repositories.relationships.BasketedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DtoPopulators {

    @Autowired
    TagService tagService;
    @Autowired
    TagRepo tagRepo;
    @Autowired
    RejectingMotiveRepo rejectingMotiveRepo;
    @Autowired
    BasketedRepo basketedRepo;

    public TemporaryPhotoDto populatePhotoDto(TemporaryPhoto p) {
        TemporaryPhotoDto response = new TemporaryPhotoDto();
        response.setId(p.getId());
        response.setUploadInstant(p.getUploadInstant());
        response.setUrl_fr(p.getUrl_fr());
        response.setUrl_hr(p.getUrl_hr());
        response.setUrl_mr(p.getUrl_mr());
        response.setUrl_lr(p.getUrl_lr());
        response.setLr_width(p.getLr_width());
        response.setLr_heigh(p.getLr_heigh());
        response.setTitle(p.getTitle());
        response.setSubmitStatus(p.getSubmitStatus());
        response.setDescription(p.getDescription());
        response.setAccountToFollow(p.getAccountToFollow());
        response.setRating(p.getRating());
        if (p.getRejectingMotives() != null) {

            Collection<RejectingMotive> rml = rejectingMotiveRepo.findAllByIdIn(p.getRejectingMotives());

            List<RejectingMotiveDto> responseContent = new ArrayList<>();
            rml.spliterator().forEachRemaining(rm -> {
                responseContent.add(populateRejectingMotiveDto(rm));
            });

            response.setRejectingMotives(responseContent);
        }
        // Populate ContainedDTO
        if (p.getContainedTags() != null) response.setContainedTags(populateTagDtoList(p.getContainedTags()));
        // Populate AssignedTagsDTO
        if (p.getTags() != null) response.setTags(populateTagDtoList(p.getTags()));
        // Populate CategoryDTO
        if (p.getCategories() != null) response.setCategories(populateCategoryDtoList(p.getCategories()));
        // Populate AuthorizationDTO
        if (p.getAuthorizations() != null)
            response.setAuthorizations(populateAuthorizationDtoList(p.getAuthorizations()));
        response.setRating(p.getRating());
        response.setWeight(p.getWeight());
        if (p.getPhotoOwner().getUsername() != null) response.setOwner(p.getPhotoOwner().getUsername());
        response.setOwnerId(p.getPhotoOwner().getId());
        return response;
    }

    public ArrayList<String> populateTagDtoList(Collection<String> p) {
        ArrayList<String> tagsToConvert = new ArrayList<>();
        p.forEach(tag -> {
            tagsToConvert.add(tag);
        });
        return tagsToConvert;
    }

    public ArrayList<String> populateCategoryDtoList(Collection<String> p) {
        ArrayList<String> categoriesToConvert = new ArrayList<>();
        p.forEach(category -> {
            categoriesToConvert.add(category);
        });
        return categoriesToConvert;
    }

    public ArrayList<TemporaryAuthorizationDto> populateAuthorizationDtoList(Collection<Authorization> p) {
        ArrayList<TemporaryAuthorizationDto> authorizationsToConvert = new ArrayList<>();
        p.forEach(temporaryAuthorization -> {
            TemporaryAuthorizationDto temporaryAuthorizationDto = new TemporaryAuthorizationDto();
            temporaryAuthorizationDto.setDocumentUrl(temporaryAuthorization.getDocumentUrl());
            temporaryAuthorizationDto.setId(temporaryAuthorization.getId());
            temporaryAuthorizationDto.setCaption(temporaryAuthorization.getCaption());
            temporaryAuthorizationDto.setAuthorizationKind(temporaryAuthorization.getAuthorizationKind());
            authorizationsToConvert.add(temporaryAuthorizationDto);
        });
        return authorizationsToConvert;
    }

    public ArrayList<TemporaryPhotoDto> populateListOfPhotoDto(List<TemporaryPhoto> photoList) {
        ArrayList<TemporaryPhotoDto> collectedPhotosDto = new ArrayList<>();

        photoList.forEach(p -> {
            TemporaryPhotoDto photoDto = new TemporaryPhotoDto();
            photoDto.setId(p.getId());
            photoDto.setUploadInstant(p.getUploadInstant());
            photoDto.setUrl_fr(p.getUrl_fr());
            photoDto.setUrl_hr(p.getUrl_hr());
            photoDto.setUrl_mr(p.getUrl_mr());
            photoDto.setUrl_lr(p.getUrl_lr());
            photoDto.setLr_width(p.getLr_width());
            photoDto.setLr_heigh(p.getLr_heigh());
            photoDto.setTitle(p.getTitle());
            photoDto.setSubmitStatus(p.getSubmitStatus());
            photoDto.setDescription(p.getDescription());
            photoDto.setAccountToFollow(p.getAccountToFollow());
            // Populate ContainedDTO
            if (p.getContainedTags() != null) photoDto.setContainedTags(populateTagDtoList(p.getContainedTags()));
            // Populate AssignedTagsDTO
            if (p.getTags() != null) photoDto.setTags(populateTagDtoList(p.getTags()));
            // Populate CategoryDTO
            if (p.getCategories() != null) photoDto.setCategories(populateCategoryDtoList(p.getCategories()));
            // Populate AuthorizationDTO
            if (p.getAuthorizations() != null)
                photoDto.setAuthorizations(populateAuthorizationDtoList(p.getAuthorizations()));
            photoDto.setRating(p.getRating());
            if (p.getRejectingMotives() != null) {

                Collection<RejectingMotive> rml = rejectingMotiveRepo.findAllByIdIn(p.getRejectingMotives());

                List<RejectingMotiveDto> responseContent = new ArrayList<>();
                rml.spliterator().forEachRemaining(rm -> {
                    responseContent.add(populateRejectingMotiveDto(rm));
                });

                photoDto.setRejectingMotives(responseContent);
            }
            photoDto.setWeight(p.getWeight());
            if (p.getPhotoOwner().getUsername() != null) photoDto.setOwner(p.getPhotoOwner().getUsername());
            photoDto.setOwnerId(p.getPhotoOwner().getId());
            collectedPhotosDto.add(photoDto);
        });
        return collectedPhotosDto;
    }

    public ArrayList<String> extractTags(MultipartFile file) throws IOException {

        List<String> allTags = new ArrayList<>();
        tagRepo.findAll().spliterator().forEachRemaining(actualTag -> {
            allTags.add(actualTag.getValue());
        });

        ArrayList<String> result = new ArrayList<>();
        try {
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            Metadata metadataFromMPF = ImageMetadataReader.readMetadata(inputStream);

            IptcDirectory directory = metadataFromMPF.getFirstDirectoryOfType(IptcDirectory.class);
            if (directory != null) {
                String[] tags = directory.getStringArray(IptcDirectory.TAG_KEYWORDS);
                List<String> newTags = new ArrayList<>();
                if (tags != null) {
                    Arrays.asList(tags).forEach(t -> {
                        if (!allTags.contains(t)) {
                            newTags.add(t);
                            allTags.add(t);
                        }
                        result.add(t);
                    });
                }
                tagService.addNewTags(newTags);
            }
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        }
        return result;

    }

    public RejectingMotiveDto populateRejectingMotiveDto(RejectingMotive rm) {
        RejectingMotiveDto actualRejectingMotive = new RejectingMotiveDto();
        actualRejectingMotive.setId(rm.getId());
        actualRejectingMotive.setValue(rm.getValue());
        return actualRejectingMotive;
    }

    public ArrayList<BasketDto> populateBasketDtoListFromList(Collection<Basket> basketList) {
        ArrayList<BasketDto> response = new ArrayList<>();
        basketList.spliterator().forEachRemaining(basket -> {
            BasketDto basketDto = new BasketDto();
            basketDto.setId(basket.getId());
            basketDto.setValue(basket.getValue());
            response.add(basketDto);
        });
        return response;
    }

    public BasketDto populateBasketDto(Basket basket) {

        BasketDto response = new BasketDto();
        response.setId(basket.getId());
        response.setValue(basket.getValue());

        return response;
    }

    public PhotoDto populatePhotoDto(Photo photo) {
        PhotoDto response = new PhotoDto();
        response.setId(photo.getId());
        response.setUploadInstant(photo.getUploadInstant());
        response.setUrl_fr(photo.getUrl_fr());
        response.setUrl_hr(photo.getUrl_hr());
        response.setUrl_mr(photo.getUrl_mr());
        response.setUrl_lr(photo.getUrl_lr());
        response.setLr_width(photo.getLr_width());
        response.setLr_heigh(photo.getLr_heigh());
        response.setTitle(photo.getTitle());
        response.setLikes(photo.getLikes());
        response.setDownloads(photo.getDownloads());
        response.setViewed(photo.getViewed());
        response.setDescription(photo.getDescription());

        response.setAccountToFollow(photo.getAccountToFollow());
        response.setCollection(this.populatePhotoCollectionDto(photo.getPhotoCollection()));

        response.setBaskets(photo.getBaskets().stream().map(basket ->
            populateBasketDto(basket)).collect(Collectors.toList()));

        // Populate ContainedDTO
        if (photo.getTags() != null) response.setTags(photo.getTags().stream()
                .map(tag -> new TagDto(tag.getId(), tag.getValue()))
                .collect(Collectors.toList()));

        // Populate CategoryDTO
        if (photo.getCategories() != null) response.setCategories(photo.getCategories().stream()
                .map(category -> new CategoryDto(category.getId(), category.getValue()))
                .collect(Collectors.toList()));
        // Populate AuthorizationDTO
        if (photo.getAuthorizations() != null)
            response.setAuthorizations(photo.getAuthorizations().stream()
                    .map(authorization -> new AuthorizationDto(authorization.getAuthorizationKind(), authorization.getUploadInstant()))
                    .collect(Collectors.toList()));
        if (photo.getPhotoOwner().getUsername() != null) response.setOwner(photo.getPhotoOwner().getUsername());
        if (photo.getPhotoOwner().getIcon() != null) response.setOwnerIcon(photo.getPhotoOwner().getIcon());
        response.setOwnerId(photo.getPhotoOwner().getId());
        return response;
    }

    public PhotoCollectionDto populatePhotoCollectionDto(PhotoCollection photoCollection) {
        PhotoCollectionDto result = new PhotoCollectionDto();
        if (photoCollection.getCreationInstant()!= null) result.setCreationInstant(photoCollection.getCreationInstant());
        if (photoCollection.getCreationInstant()!= null) {result.setId(photoCollection.getId());}
        result.setName(photoCollection.getName());
        return result;
    }

    public UserDto populateUserDto(User u) {
        UserDto response = new UserDto();
        response.setActive(u.isActive());
        response.setAvatar(u.getAvatar());
        response.setIcon(u.getIcon());
        response.setCreated(u.getCreated());
        response.setEmail(u.getEmail());
        response.setFacebook(u.getFacebook());
        response.setDescription(u.getDescription());
        response.setInstagram(u.getInstagram());
        response.setLocation(u.getLocation());
        response.setUsername(u.getUsername());
        response.setName(u.getName());
        response.setSurname(u.getSurname());
        response.setRoles(u.getRoles());
        return response;
    }
}

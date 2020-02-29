package com.picktur.server.controllers.admin;

import com.picktur.server.entities.RejectingMotive;
import com.picktur.server.entities.dto.RejectingMotiveDto;
import com.picktur.server.entities.dto.photo_upload.TemporaryPhotoDto;
import com.picktur.server.entities.photo_upload.SubmitStatus;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;
import com.picktur.server.repositories.documents.RejectingMotiveRepo;
import com.picktur.server.repositories.documents.TemporaryPhotoRepo;
import com.picktur.server.services.DtoPopulators;
import com.picktur.server.services.PhotoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/admin/publication_controller")
public class PublicationController {

    @Autowired
    TemporaryPhotoRepo temporaryPhotoRepo;
    @Autowired
    RejectingMotiveRepo rejectingMotiveRepo;
    @Autowired
    DtoPopulators populators;
    @Autowired
    PhotoManager photoManager;


    @GetMapping("/list_all_photos_in_submit_management")
    public ResponseEntity<ArrayList<TemporaryPhotoDto>> getAllPhotos() {

        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAll();
        ArrayList<TemporaryPhotoDto> responseContent = new ArrayList<>();

        photos.forEach(p -> {
            TemporaryPhotoDto pDto = populators.populatePhotoDto(p);
            responseContent.add(pDto);
        });

        return new ResponseEntity<ArrayList<TemporaryPhotoDto>>(responseContent, HttpStatus.ACCEPTED);
    }

    @GetMapping("/list_all_photos_in_submit_management_for_user")
    public ResponseEntity<ArrayList<TemporaryPhotoDto>> getAllPhotosForUser(@RequestBody String userId) {

        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAllByPhotoOwnerIdIs(userId);
        ArrayList<TemporaryPhotoDto> responseContent = new ArrayList<>();

        photos.forEach(p -> {
            TemporaryPhotoDto pDto = populators.populatePhotoDto(p);
            responseContent.add(pDto);
        });

        return new ResponseEntity<ArrayList<TemporaryPhotoDto>>(responseContent, HttpStatus.ACCEPTED);
    }

    @GetMapping("/list_to_be_submitted_photos")
    public ResponseEntity<ArrayList<TemporaryPhotoDto>> getToBeSubmittedPhotos() {

        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAllBySubmitStatusEquals(SubmitStatus.TO_BE_SUBMITTED);
        ArrayList<TemporaryPhotoDto> responseContent = new ArrayList<>();
        photos.forEach(p -> {
            TemporaryPhotoDto pDto = populators.populatePhotoDto(p);
            responseContent.add(pDto);
        });

        return new ResponseEntity<ArrayList<TemporaryPhotoDto>>(responseContent, HttpStatus.ACCEPTED);
    }

    @GetMapping("/list_to_be_submitted_photos_for_User")
    public ResponseEntity<ArrayList<TemporaryPhotoDto>> getToBeSubmittedPhotosByUser(String userId) {

        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAllBySubmitStatusEqualsAndPhotoOwnerIdIs(SubmitStatus.TO_BE_SUBMITTED, userId);
        ArrayList<TemporaryPhotoDto> responseContent = new ArrayList<>();
        photos.forEach(p -> {
            TemporaryPhotoDto pDto = populators.populatePhotoDto(p);
            responseContent.add(pDto);
        });

        return new ResponseEntity<ArrayList<TemporaryPhotoDto>>(responseContent, HttpStatus.ACCEPTED);
    }

    @GetMapping("/list_submitted_photos")
    public ResponseEntity<ArrayList<TemporaryPhotoDto>> getSubmittedPhotos() {

        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAllBySubmitStatusEquals(SubmitStatus.SUBMITTED);
        ArrayList<TemporaryPhotoDto> responseContent = new ArrayList<>();
        photos.forEach(p -> {
            TemporaryPhotoDto pDto = populators.populatePhotoDto(p);
            responseContent.add(pDto);
        });

        return new ResponseEntity<ArrayList<TemporaryPhotoDto>>(responseContent, HttpStatus.ACCEPTED);
    }

    @GetMapping("/list_submitted_photos_for_user")
    public ResponseEntity<ArrayList<TemporaryPhotoDto>> getSubmittedPhotosByUser(@RequestBody String userId) {

        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAllBySubmitStatusEqualsAndPhotoOwnerIdIs(SubmitStatus.SUBMITTED, userId);
        ArrayList<TemporaryPhotoDto> responseContent = new ArrayList<>();
        photos.forEach(p -> {
            TemporaryPhotoDto pDto = populators.populatePhotoDto(p);
            responseContent.add(pDto);
        });

        return new ResponseEntity<ArrayList<TemporaryPhotoDto>>(responseContent, HttpStatus.ACCEPTED);
    }

    @GetMapping("/list_rejected_photos")
    public ResponseEntity<ArrayList<TemporaryPhotoDto>> getRejectedPhotos() {

        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAllBySubmitStatusEquals(SubmitStatus.REJECTED);
        ArrayList<TemporaryPhotoDto> responseContent = new ArrayList<>();
        photos.forEach(p -> {
            TemporaryPhotoDto pDto = populators.populatePhotoDto(p);
            responseContent.add(pDto);
        });

        return new ResponseEntity<ArrayList<TemporaryPhotoDto>>(responseContent, HttpStatus.ACCEPTED);
    }

    @GetMapping("/list_rejected_photos_for_user")
    public ResponseEntity<ArrayList<TemporaryPhotoDto>> getRejectedPhotosByUser(@RequestBody String userId) {

        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAllBySubmitStatusEqualsAndPhotoOwnerIdIs(SubmitStatus.REJECTED, userId);
        ArrayList<TemporaryPhotoDto> responseContent = new ArrayList<>();
        photos.forEach(p -> {
            TemporaryPhotoDto pDto = populators.populatePhotoDto(p);
            responseContent.add(pDto);
        });

        return new ResponseEntity<ArrayList<TemporaryPhotoDto>>(responseContent, HttpStatus.ACCEPTED);
    }

    @GetMapping("/list_accepted_photos")
    public ResponseEntity<ArrayList<TemporaryPhotoDto>> getAcceptedPhotos() {

        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAllBySubmitStatusEquals(SubmitStatus.ACCEPTED);
        ArrayList<TemporaryPhotoDto> responseContent = new ArrayList<>();
        photos.forEach(p -> {
            TemporaryPhotoDto pDto = populators.populatePhotoDto(p);
            responseContent.add(pDto);
        });

        return new ResponseEntity<ArrayList<TemporaryPhotoDto>>(responseContent, HttpStatus.ACCEPTED);
    }

    @GetMapping("/list_accepted_photos_for_user")
    public ResponseEntity<ArrayList<TemporaryPhotoDto>> getAcceptedPhotosByUser(@RequestBody String userId) {

        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAllBySubmitStatusEqualsAndPhotoOwnerIdIs(SubmitStatus.ACCEPTED, userId);
        ArrayList<TemporaryPhotoDto> responseContent = new ArrayList<>();
        photos.forEach(p -> {
            TemporaryPhotoDto pDto = populators.populatePhotoDto(p);
            responseContent.add(pDto);
        });

        return new ResponseEntity<ArrayList<TemporaryPhotoDto>>(responseContent, HttpStatus.ACCEPTED);
    }

    @PostMapping("/delist_old_photos")
    public ResponseEntity delistPhotos(@RequestBody ArrayList<String> photosToDelist) {

        return new ResponseEntity<ArrayList<TemporaryPhotoDto>>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/accept_photos")
    public ResponseEntity acceptPhotos(@RequestBody ArrayList<String> photosToAccept) {

        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAllByIdIn(photosToAccept);

        photos.forEach(p -> {
            p.setSubmitStatus(SubmitStatus.ACCEPTED);
            photoManager.transformToPermanentPhoto(photos);
        });

        temporaryPhotoRepo.saveAll(photos);

        return new ResponseEntity(HttpStatus.ACCEPTED);

    }

    @PostMapping("/accept_and_rate_photo")
    public ResponseEntity acceptAndRatePhoto(@RequestBody AcceptRejectPhotoRequest request) {

        TemporaryPhoto photo = temporaryPhotoRepo.findById(request.getPhotoToManage()).get();

        if (photo != null) {
            photo.setSubmitStatus(SubmitStatus.ACCEPTED);
            photo.setRating(request.getRating());
            photoManager.transformToPermanentPhoto(Arrays.asList(photo));

        }

        temporaryPhotoRepo.save(photo);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/reject_Photo")
    public ResponseEntity rejectPhoto(@RequestBody AcceptRejectPhotoRequest request) {

        TemporaryPhoto photo = temporaryPhotoRepo.findById(request.getPhotoToManage()).get();

        if (photo != null) {
            //List<String> motives = rejectingMotiveRepo.findAllByIdIn(request.getMotivesForRejectingIds()).stream().map(RejectingMotive::getValue).collect(Collectors.toList());

            photo.setSubmitStatus(SubmitStatus.REJECTED);
            photo.setRating(request.getRating());
            photo.setRejectingMotives(request.getMotivesForRejectingIds());
        }

        temporaryPhotoRepo.save(photo);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }



    @PostMapping("/reject_photos")
    public ResponseEntity rejectPhotos(@RequestBody RejectPhotosRequest request) {
        //List<String> motives = rejectingMotiveRepo.findAllByIdIn(request.getMotivesForRejectingIds()).stream().map(RejectingMotive::getValue).collect(Collectors.toList());
        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAllByIdIn(request.getPhotosToReject());
        photos.forEach(p -> {
            p.setSubmitStatus(SubmitStatus.REJECTED);
            p.setRejectingMotives(request.getMotivesForRejectingIds());
        });

        temporaryPhotoRepo.saveAll(photos);

        return new ResponseEntity<ArrayList<TemporaryPhotoDto>>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/list_rejecting_motives")
    public ResponseEntity<List<RejectingMotiveDto>> listRejectingMotives() {
        Iterable<RejectingMotive> responseIterator = rejectingMotiveRepo.findAll();
        ArrayList<RejectingMotiveDto> response = new ArrayList<>();
        responseIterator.forEach(rm -> {
            RejectingMotiveDto actualRejectingMotive = populators.populateRejectingMotiveDto(rm);
            response.add(actualRejectingMotive);
        });
        return new ResponseEntity<List<RejectingMotiveDto>>(response, HttpStatus.ACCEPTED);
    }




}

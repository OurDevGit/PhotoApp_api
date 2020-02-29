package com.picktur.server.controllers.photo_management;

import com.picktur.server.configurations.security.UserPrincipal;
import com.picktur.server.controllers.user_management.ResourceNotFoundException;
import com.picktur.server.entities.Photo;
import com.picktur.server.entities.User;
import com.picktur.server.entities.dto.PhotoDto;
import com.picktur.server.relations.Viewed;
import com.picktur.server.repositories.documents.PhotoRepo;
import com.picktur.server.repositories.documents.UserRepo;
import com.picktur.server.repositories.relationships.ViewedRepo;
import com.picktur.server.services.DtoPopulators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/photo_details_controller")
public class PhotoDetailController {

    @Autowired
    PhotoRepo photoRepo;
    @Autowired
    DtoPopulators populators;
    @Autowired
    ViewedRepo viewedRepo;
    @Autowired
    UserRepo userRepo;

    @GetMapping("/photo_details/{photoId}")
    public ResponseEntity<PhotoDetails> getDetailPage(@AuthenticationPrincipal UserPrincipal principal, @PathVariable("photoId") String photoId) throws Throwable {

        User user = userRepo.findById(principal.getId()).get();

        PhotoDetails response = new PhotoDetails();
        Photo photo = photoRepo.findById(photoId).orElseThrow((Supplier<Throwable>) () -> new ResourceNotFoundException("Photo", "id", photoId));

        photo.setViewed(photo.getViewed()+1);
        photoRepo.save(photo);

        viewedRepo.save(new Viewed(user, photo));

        PhotoDto photoDto = populators.populatePhotoDto(photo);
        response.setPhotoDto(photoDto);
        Pageable pageable = PageRequest.of(0, 30);
        //ToDo: make logic of similar photos
        Page<Photo> photoPage = photoRepo.findAll(pageable);
        Page<PhotoDto> similarPhotos = photoPage.map(p -> populators.populatePhotoDto(p));
        response.setSimilarPhotos(similarPhotos);
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/photo_modal_viewed/{photoId}")
    public ResponseEntity addViewedThroughModal(@AuthenticationPrincipal UserPrincipal principal, @PathVariable("photoId") String photoId) throws Throwable {
        Photo photo = photoRepo.findById(photoId).orElseThrow((Supplier<Throwable>) () -> new ResourceNotFoundException("Photo", "id", photoId));
        User user = userRepo.findById(principal.getId()).get();
        photo.setViewed(photo.getViewed()+1);
        photoRepo.save(photo);
        viewedRepo.save(new Viewed(user, photo));
        return new ResponseEntity( HttpStatus.ACCEPTED);
    }

    @GetMapping("/similarPhotos/{photoId}")
    public ResponseEntity <ArrayList<PhotoDto>> getsimilarPhotos(@PathVariable("photoId") String photoId) throws Throwable {

        ArrayList<PhotoDto> response = new ArrayList<>();
        Photo photo = photoRepo.findById(photoId).orElseThrow((Supplier<Throwable>) () -> new ResourceNotFoundException("Photo", "id", photoId));
        //LOGIC
        // ToDo: develop logic to find smilar logic
        photoRepo.findAllByTagsContains(photo.getTags().stream().findFirst().get())
                .spliterator().forEachRemaining(photo1 -> {response.add(populators.populatePhotoDto(photo1));});
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/same_collection/{photoId}")
    public ResponseEntity <ArrayList<PhotoDto>> getSameCollection(@PathVariable("photoId") String photoId) throws Throwable {

        ArrayList<PhotoDto> response = new ArrayList<>();
        Photo photo = photoRepo.findById(photoId).orElseThrow((Supplier<Throwable>) () -> new ResourceNotFoundException("Photo", "id", photoId));
        photoRepo.findByPhotoCollection_Id(photo.getPhotoCollection().getId()).spliterator().forEachRemaining(photo1 -> response.add(populators.populatePhotoDto(photo1)));

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/like_amount/{photoId}")
    public ResponseEntity <Integer> getLikeAmount(@PathVariable("photoId") String photoId) throws Throwable {
// ToDo: implement edge count for like
        return new ResponseEntity<>(new Random().nextInt(1000), HttpStatus.ACCEPTED);
    }

    @GetMapping("/download_amount/{photoId}")
    public ResponseEntity <Integer> getDownloadsAmount(@PathVariable("photoId") String photoId) throws Throwable {
// ToDo: implement edge count for downloads
        return new ResponseEntity<>(new Random().nextInt(1000), HttpStatus.ACCEPTED);
    }

    @GetMapping("/views_amount/{photoId}")
    public ResponseEntity <Integer> getViewsAmount(@PathVariable("photoId") String photoId) throws Throwable {
// ToDo: implement edge count for view
        return new ResponseEntity<>(new Random().nextInt(1000), HttpStatus.ACCEPTED);
    }



}

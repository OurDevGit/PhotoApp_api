package com.picktur.server.controllers.list_management;

import com.picktur.server.entities.Photo;
import com.picktur.server.entities.Tag;
import com.picktur.server.entities.dto.PhotoDto;
import com.picktur.server.entities.dto.TagDto;
import com.picktur.server.repositories.documents.PhotoRepo;
import com.picktur.server.services.DtoPopulators;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Data
@RestController
@RequestMapping("/api/public/lists")
public class ListController {

    @Autowired
    PhotoRepo photoRepo;
    @Autowired
    DtoPopulators populators;

    @GetMapping("/homepage")
    public ResponseEntity<Page<PhotoDto>> getPhotoForHomePage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "size", defaultValue = "10") int size) {


        Pageable pageable = PageRequest.of(page, size, Sort.by("weight"));
        Page<Photo> photoPage = photoRepo.findAllByForHome(true, pageable);
        Page<PhotoDto> response = photoPage.map(p -> populators.populatePhotoDto(p));
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search_photos")
    public ResponseEntity<Page<PhotoDto>> searchPhotos(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                       @RequestParam(value = "category") String category,
                                                       @RequestParam(value = "tag") String tag,
                                                       @RequestParam(value = "key") String key,
                                                       @RequestParam(value = "userId") String userId,
                                                        @RequestParam(value = "order") List<Sort.Order> order,
                                                       @RequestParam(value = "direction") Sort.Direction direction){


        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        // ToDO: develop logic
        Page<PhotoDto> photoPage = photoRepo.findAll(pageable).map(photo -> populators.populatePhotoDto(photo));
        return new ResponseEntity<>(photoPage, HttpStatus.ACCEPTED);

    }

    @GetMapping("/availableToHome")
    public ResponseEntity<Page<PhotoDto>> availablePhotoForHome(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("uploadInstant"));
        Page<Photo> photoPage = photoRepo.findAllByForHomeOrderByUploadInstantDescRatingDesc(false, pageable);
        Page<PhotoDto> response = photoPage.map(p -> populators.populatePhotoDto(p));
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/not_choosen_for_Home")
    public ResponseEntity<Page<PhotoDto>> notChosenForHomePage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("uploadInstant"));
        Page<Photo> photoPage = photoRepo.findAllByForHome(false, pageable);
        Page<PhotoDto> response = photoPage.map(p -> populators.populatePhotoDto(p));
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/choosedForHome")
    public ResponseEntity choosedForHome(@RequestBody List<ChoosenForHome> choosenForHome) {
        Set<Photo> photosToUpdate = new HashSet<>();
        // remove all photos
        photoRepo.findAllByForHome(true).iterator().forEachRemaining(actualPhoto -> {
            actualPhoto.setForHome(false);
        photosToUpdate.add(actualPhoto);
        });

        choosenForHome.iterator().forEachRemaining(choosenPhoto -> {
            Photo actualPhoto = photoRepo.findPhotoById(choosenPhoto.getPhotoId());
            actualPhoto.setWeight(choosenPhoto.getWeight());
            actualPhoto.setForHome(true);
            photosToUpdate.add(actualPhoto);
        });

        // Update photos removed and photos added
        photoRepo.saveAll(photosToUpdate);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }


    private ArrayList<TagDto> populateResulList(ArrayList<Tag> tagList) {
        ArrayList<TagDto> resultList = new ArrayList();

        for (Tag t : tagList) {
            TagDto actualTagDto = new TagDto(t.getId(), t.getValue(), new Random().nextDouble());
            resultList.add(actualTagDto);
        }
        return resultList;
    }
}

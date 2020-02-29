package com.picktur.server.controllers.photo_management;

import com.picktur.server.configurations.security.UserPrincipal;
import com.picktur.server.entities.PhotoCollection;
import com.picktur.server.entities.User;
import com.picktur.server.entities.dto.PhotoCollectionDto;
import com.picktur.server.relations.Collectioned;
import com.picktur.server.relations.PhotoUserCollectioned;
import com.picktur.server.repositories.documents.PhotoCollectionRepo;
import com.picktur.server.repositories.documents.TemporaryPhotoRepo;
import com.picktur.server.repositories.documents.UserRepo;
import com.picktur.server.repositories.relationships.CollectionedRepo;
import com.picktur.server.repositories.relationships.PhotoUserCollectionedRepo;
import com.picktur.server.services.DtoPopulators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/photo_collection_controller")
public class CollectionController {

    @Autowired
    DtoPopulators populators;
    @Autowired
    UserRepo userRepo;
    @Autowired
    TemporaryPhotoRepo temporaryPhotoRepo;
    @Autowired
    PhotoCollectionRepo photoCollectionRepo;
    @Autowired
    PhotoUserCollectionedRepo photoUserCollectionedRepo;
    @Autowired
    CollectionedRepo collectionedRepo;


    @GetMapping("/get_collections")
    public ResponseEntity<List<PhotoCollectionDto>> getDetailPage(@AuthenticationPrincipal UserPrincipal principal) throws Throwable {
        ArrayList<PhotoCollectionDto> result = new ArrayList<>();
        User user = userRepo.findById(principal.getId()).get();
        result.addAll(user.getCollections().stream().map(c -> populators.populatePhotoCollectionDto(c)).collect(Collectors.toList()));
        Set<String> temporaryCategoryNames =  user.getTemporaryUpLoadedPhotos().stream().map(p -> p.getCollection()).collect(Collectors.toSet());
        result.addAll(temporaryCategoryNames.stream().map(n -> new PhotoCollectionDto(n)).collect(Collectors.toList()));

        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @PostMapping("/update_collection_name")
    public ResponseEntity updateCollection( CollectionRequest request) throws Throwable {
        PhotoCollection collection = photoCollectionRepo.findById(request.getPhotoCollectionId()).get();
        collection.setName(request.getPhotoCollectionName());
        photoCollectionRepo.save(collection);
        return new ResponseEntity(HttpStatus.ACCEPTED);

    }

    @PostMapping("/change_collection_forPhoto")
    public ResponseEntity changeCollection(@AuthenticationPrincipal UserPrincipal principal, CollectionRequest request) throws Throwable {
        PhotoCollection collection = photoCollectionRepo.findById(request.getPhotoCollectionId()).get();
        Collectioned oldCollectioned = collectionedRepo.findByCollectionedPhoto_Id(request.getOldPhotoCollectionId());
        oldCollectioned.setCollection(collection);
        collectionedRepo.save(oldCollectioned);
        return new ResponseEntity(HttpStatus.ACCEPTED);

    }

    @PostMapping("/add_collection")
    public ResponseEntity addCollection(@AuthenticationPrincipal UserPrincipal principal, CollectionRequest request) throws Throwable {
        User user = userRepo.findById(principal.getId()).get();
        PhotoCollection collection = new PhotoCollection();
        collection.setName(request.getPhotoCollectionName());
        photoCollectionRepo.save(collection);
        photoUserCollectionedRepo.save(new PhotoUserCollectioned(user, collection ));
    return new ResponseEntity(HttpStatus.ACCEPTED);

    }
}

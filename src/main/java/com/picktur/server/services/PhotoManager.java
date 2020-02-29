package com.picktur.server.services;

import com.picktur.server.entities.Photo;
import com.picktur.server.entities.PhotoCollection;
import com.picktur.server.entities.User;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;
import com.picktur.server.relations.*;
import com.picktur.server.repositories.documents.*;
import com.picktur.server.repositories.relationships.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PhotoManager {

    @Autowired
    PhotoRepo photoRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    CathegorizedRepo cathegorizedRepo;
    @Autowired
    TagRepo tagRepo;
    @Autowired
    TaggedRepo taggedRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UploadedRepo uploadedRepo;
    @Autowired
    AuthorizationRepo authorizationRepo;
    @Autowired
    AuthorizedRepo authorizedRepo;
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    PhotoCollectionRepo photoCollectionRepo;
    @Autowired
    CollectionedRepo collectionedRepo;
    @Autowired
    PhotoUserCollectionedRepo photoUserCollectionedRepo;

    public void transformToPermanentPhoto(Iterable<TemporaryPhoto> photos) {
        photos.iterator().forEachRemaining(tPhoto -> {
            Photo photo = new Photo();
            if (tPhoto.getTitle() != null) photo.setTitle(tPhoto.getTitle());
            if (tPhoto.getDescription() != null) photo.setDescription(tPhoto.getDescription());
            if (tPhoto.getUrl_fr() != null) photo.setUrl_fr(tPhoto.getUrl_fr());
            if (tPhoto.getUrl_hr() != null) photo.setUrl_hr(tPhoto.getUrl_hr());
            if (tPhoto.getUrl_mr() != null) photo.setUrl_mr(tPhoto.getUrl_mr());
            if (tPhoto.getUrl_lr() != null) photo.setUrl_lr(tPhoto.getUrl_lr());
            photo.setLr_width(tPhoto.getLr_width());
            photo.setLr_heigh(tPhoto.getLr_heigh());
            photo.setPublished(true);
            photo.setRating(tPhoto.getRating());
            photo.setWeight(0);
            photo.setUploadInstant(tPhoto.getUploadInstant());
            photoRepo.save(photo);

            if (tPhoto.getCategories() != null)
                categoryRepo.findAllByValueIgnoreCaseIn(tPhoto.getCategories()).iterator().forEachRemaining(category -> {
                    cathegorizedRepo.save(new Categorized(category, photo));
                });

            if (tPhoto.getTags() != null)
                tagRepo.findAllByValueInIgnoreCase(tPhoto.getTags()).iterator().forEachRemaining(tag -> {
                    taggedRepo.save(new Tagged(tag, photo));
                });

            User user = tPhoto.getPhotoOwner();
            Uploaded uploaded = new Uploaded(user, photo);
            uploadedRepo.save(uploaded);

            PhotoCollection photoCollection = null;
            if (tPhoto.getCollection() != null) {
                if (!tPhoto.getCollection().isEmpty()) {
                    photoCollection = photoCollectionRepo.findByNameAndUserId(tPhoto.getCollection(), user.getId());
                    if (photoCollection == null) {
                        photoCollection = new PhotoCollection();
                        photoCollection.setName(tPhoto.getCollection());
                        photoCollection.setCreationInstant(Instant.now());
                        photoCollection.setPublished(true);
                        photoCollectionRepo.save(photoCollection);
                        photoUserCollectionedRepo.save(new PhotoUserCollectioned(user, photoCollection));
                    }
                    collectionedRepo.save(new Collectioned(photoCollection, photo));
                }
            }



            if (tPhoto.getAuthorizations() != null)
                tPhoto.getAuthorizations().iterator().forEachRemaining(authorization -> {
                    Authorized authorized = new Authorized(authorization, photo);
                    authorizedRepo.save(authorized);
                });

        });
    }
}

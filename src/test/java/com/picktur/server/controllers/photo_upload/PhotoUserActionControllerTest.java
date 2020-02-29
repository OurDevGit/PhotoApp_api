package com.picktur.server.controllers.photo_upload;

import com.picktur.server.entities.Basket;
import com.picktur.server.entities.Photo;
import com.picktur.server.entities.User;
import com.picktur.server.relations.Basketed;
import com.picktur.server.relations.Uploaded;
import com.picktur.server.relations.UserBasketing;
import com.picktur.server.repositories.documents.BasketRepo;
import com.picktur.server.repositories.documents.PhotoRepo;
import com.picktur.server.repositories.documents.UserRepo;
import com.picktur.server.repositories.relationships.BasketedRepo;
import com.picktur.server.repositories.relationships.UploadedRepo;
import com.picktur.server.repositories.relationships.UserBasketingRepo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class PhotoUserActionControllerTest /*extends PhotoSubmitController*/ {

    @Autowired
    UserRepo userRepo;
    @Autowired
    PhotoRepo photoRepo;
    @Autowired
    BasketRepo basketRepo;
    @Autowired
    BasketedRepo basketedRepo;
    @Autowired
    UserBasketingRepo userBasketingRepo;
    @Autowired
    UploadedRepo uploadedRepo;

    @SneakyThrows
    @Test
    void endToEndBaskets() {
        User user = userRepo.findByUsername("nuspy").get();
        Photo photo = new Photo();
        photoRepo.save(photo);
        Uploaded uploaded = new Uploaded(user,photo);
        uploadedRepo.save(uploaded);

        //basket Creation
        Basket basket = new Basket();
        basket.setValue("newBasket");
        basketRepo.save(basket);
        UserBasketing userBasketing = new UserBasketing(user, basket);
        userBasketingRepo.save(userBasketing);

        //Add photo to basket
        Basketed basketed = new Basketed(basket, photo);
        basketedRepo.save(basketed);

        //Remove Basket
          // Remove photo links to basket


        // ********
        String basketToRemove = basket.getId();
        basketedRepo.deleteAllByBasket_Id(basketToRemove);
          // Remove User Links to basket
        userBasketingRepo.deleteByBasket_Id(basketToRemove);
          //Delete Basket
        basketRepo.deleteById(basketToRemove);
        photoRepo.delete(photo);
        uploadedRepo.deleteByPhoto_id(photo.getId());
        System.out.println(photo);
    }
}
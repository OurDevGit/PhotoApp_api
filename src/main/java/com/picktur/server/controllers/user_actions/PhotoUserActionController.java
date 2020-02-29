package com.picktur.server.controllers.user_actions;

import com.picktur.server.configurations.security.UserPrincipal;
import com.picktur.server.entities.Basket;
import com.picktur.server.entities.Photo;
import com.picktur.server.entities.User;
import com.picktur.server.entities.dto.BasketDto;
import com.picktur.server.entities.dto.PhotoDto;
import com.picktur.server.relations.Basketed;
import com.picktur.server.relations.Liked;
import com.picktur.server.relations.UserBasketing;
import com.picktur.server.repositories.documents.BasketRepo;
import com.picktur.server.repositories.documents.PhotoRepo;
import com.picktur.server.repositories.documents.UserRepo;
import com.picktur.server.repositories.relationships.BasketedRepo;
import com.picktur.server.repositories.relationships.LikedRepo;
import com.picktur.server.repositories.relationships.UserBasketingRepo;
import com.picktur.server.services.DtoPopulators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user_actions/photo_actions_controller")
public class PhotoUserActionController {

    @Autowired
    PhotoRepo photoRepo;
    @Autowired
    LikedRepo likedRepo;
    @Autowired
    BasketRepo basketRepo;
    @Autowired
    BasketedRepo basketedRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserBasketingRepo userBasketingRepo;
    @Autowired
    DtoPopulators populators;

    @PostMapping("/add_like")
    public ResponseEntity addLike(@AuthenticationPrincipal UserPrincipal principal, @RequestBody String photoLiked){

        User user = userRepo.findById(principal.getId()).get();
        Photo photo = photoRepo.findById(photoLiked).get();
        photo.setLikes(photo.getLikes()+1);
        Liked like = new Liked(user, photo);
        likedRepo.save(like);
        photoRepo.save(photo);
        return new ResponseEntity (HttpStatus.ACCEPTED);
    }

    @GetMapping("/is_liked/{photoLiked}")
    public ResponseEntity<Boolean> isLiked(@AuthenticationPrincipal UserPrincipal principal, @PathVariable("photoLiked") String photoLiked){

        User user = userRepo.findById(principal.getId()).get();
        //Photo photo = photoRepo.findById(photoLiked).get();

        Liked liked = likedRepo.findFirstByLikedPhoto_IdAndUser_Id(photoLiked, user.getId());
        if (liked==null) return new ResponseEntity<> (false, HttpStatus.ACCEPTED);
        return new ResponseEntity<> (true, HttpStatus.ACCEPTED);
    }

    @PostMapping("/add_to_basket")
    public ResponseEntity addToBasket(@AuthenticationPrincipal UserPrincipal principal, @RequestBody AddToBasketRequest request){
        List<String> baskets = request.getBaskets();
        baskets.iterator().forEachRemaining(
                basketId -> {
                    Basket basket = basketRepo.findById(basketId).get();
                    Photo photo = photoRepo.findById(request.getPhotoId()).get();
                    Basketed basketed = new Basketed(basket, photo);
                    basketedRepo.save(basketed);
                }
        );


        return new ResponseEntity (HttpStatus.ACCEPTED);
    }

    @GetMapping("/list_baskets_for_user")
    public ResponseEntity <ArrayList<BasketDto>> listBasketsForUser(@AuthenticationPrincipal UserPrincipal principal){

        User user = userRepo.findById(principal.getId()).get();
        ArrayList<BasketDto> response = populators.populateBasketDtoListFromList(user.getBaskets());

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }



    @PostMapping("/add_new_basket_for_user")
    public ResponseEntity <ArrayList<BasketDto>> addBasketsForUser(@AuthenticationPrincipal UserPrincipal principal, @RequestBody String newBasket){
        User user = userRepo.findById(principal.getId()).get();
        Basket basket = new Basket();
        basket.setValue(newBasket);
        basketRepo.save(basket);
        UserBasketing userBasketing = new UserBasketing(user, basket);
        userBasketingRepo.save(userBasketing);
        ArrayList<BasketDto> response = populators.populateBasketDtoListFromList(user.getBaskets());

        return new ResponseEntity<> (response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/remove_basket_for_user/{basketToRemove}")
    public ResponseEntity removeBasketsForUser(@PathVariable("basketToRemove") String basketToRemove){

        basketedRepo.deleteAllByBasket_Id(basketToRemove);
       userBasketingRepo.deleteByBasket_Id(basketToRemove);
       basketRepo.deleteById(basketToRemove);

        return new ResponseEntity (HttpStatus.ACCEPTED);
    }

    @GetMapping("/remove_like_for_user/{likeToRemove}")
    public ResponseEntity removeLikeForUser(@AuthenticationPrincipal UserPrincipal principal, @PathVariable("likeToRemove") String likeToRemove){

        likedRepo.removeByLikedPhoto_IdAndUser_Id(likeToRemove, principal.getId());

        Photo photo = photoRepo.findById(likeToRemove).get();
        photo.setLikes(photo.getLikes()-1);
        photoRepo.save(photo);

        return new ResponseEntity (HttpStatus.ACCEPTED);
    }

    @GetMapping("/list_baskets_Content/{basketId}")
    public ResponseEntity<ArrayList<PhotoDto>>  getBasketContent(@AuthenticationPrincipal UserPrincipal principal, @PathVariable("basketId") String basketId){
        Basket basket = basketRepo.findById(basketId).get();
        ArrayList<PhotoDto> result = new ArrayList<>();
        ArrayList<Photo> photos = new ArrayList<>();
        basket.getPhotos().spliterator().forEachRemaining(photo -> {
            PhotoDto dto = new PhotoDto();
            dto.setId(photo.getId());
            dto.setDescription(photo.getDescription());
            dto.setTitle(photo.getTitle());
            dto.setUrl_lr(photo.getUrl_lr());
            result.add(dto);
        });

        return new ResponseEntity<> (result, HttpStatus.ACCEPTED);
    }

}

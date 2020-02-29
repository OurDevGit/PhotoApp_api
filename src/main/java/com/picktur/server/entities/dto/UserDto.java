package com.picktur.server.entities.dto;

import com.picktur.server.entities.User;
import com.picktur.server.entities.dto.photo_upload.TemporaryPhotoDto;
import com.picktur.server.entities.user_registration.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDto {

    private String id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String avatar;
    private String icon;
    private String description;
    private String location;
    private String instagram;
    private String facebook;
    private boolean active;
    private Collection<SimpleUserDto> following;
    private Collection<BasketDto> baskets;
    private Collection<PhotoCollectionDto> collections;
    private Collection<PhotoDto> upLoadedPhotos;
    private Collection<TemporaryPhotoDto> temporaryUpLoadedPhotos;
    private Collection<PhotoDto> downLoadedPhotos;
    private Collection<PhotoDto> photos;
    private Collection<AuthorizationDto> authorizations;
    private List<Role> roles = new ArrayList();
    private Instant created;
    // private User createdBy;
    private Instant modified;
    // private User modifiedBy;

    public UserDto(User user){
        id = user.getId();
        username = user.getUsername();
        name = user.getName();
        surname = user.getSurname();
        email = user.getEmail();
        password = user.getPassword();
        avatar = user.getAvatar();
        description = user.getDescription();
        location = user.getLocation();
        instagram = user.getInstagram();
        facebook = user.getFacebook();
        active = user.isActive();
        following = user.getFollowing().stream().map(user1 -> new SimpleUserDto(user1)).collect(Collectors.toList());
        baskets = user.getBaskets().stream().map(basket -> new BasketDto(basket)).collect(Collectors.toList());
        collections = user.getCollections().stream().map(collection -> new PhotoCollectionDto(collection)).collect(Collectors.toList());
        created = user.getCreated();
    }
}

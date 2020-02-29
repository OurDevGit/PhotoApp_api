package com.picktur.server.entities;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Ref;
import com.arangodb.springframework.annotation.Relations;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;
import com.picktur.server.entities.user_registration.Role;
import com.picktur.server.relations.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@HashIndex(fields = { "name", "surname" }, unique = true)
@Document("users")
@Data
public class User {

    @Id
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
    @Relations(edges = Following.class, lazy = true)
    private Collection<User> following;

    @Relations(edges = UserBasketing.class, lazy = true)
    private Collection<Basket> baskets;

    @Relations(edges = PhotoUserCollectioned.class, lazy = true)
    private Collection<PhotoCollection> collections;

    @Relations(edges = Uploaded.class, lazy = true)
    private Collection<Photo> upLoadedPhotos;

    @Relations(edges = TemporaryUploaded.class, lazy = true)
    private Collection<TemporaryPhoto> temporaryUpLoadedPhotos;

    @Relations(edges = Downloaded.class, lazy = true)
    private Collection<Photo> downLoadedPhotos;

    @Relations(edges = Liked.class, lazy = true)
    private Collection<Photo> photos;

    @Relations(edges = com.picktur.server.relations.UserAuthorized.class, lazy = true)
    private Collection<Authorization> authorizations;

    @Ref
    private List<Role> roles = new ArrayList();
    @CreatedDate
    private Instant created;
    // @CreatedBy
    // private User createdBy;
    @LastModifiedDate
    private Instant modified;

    public User() {
        super();
    }

    public User(String name, String username, String email, String password) {
        super();
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    // @LastModifiedBy
    // private User modifiedBy;
}

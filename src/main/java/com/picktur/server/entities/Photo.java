package com.picktur.server.entities;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Relations;
import com.picktur.server.relations.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.Collection;

@Document("photos")
@Data
public class Photo {

    @Id
    private String id;// = UUID.randomUUID().toString();

    private String url_lr;
    private String url_mr;
    private String url_hr;
    private String url_fr;

    private int lr_width;
    private int lr_heigh;

    private String title = "";
    private String description = "";

    private String accountToFollow = "";

    private Instant uploadInstant;

    private boolean published;

    private double rating;
    private double weight;
    private boolean forHome = false;

    private int likes;
    private int downloads;
    private int viewed;

    @Relations(edges = Tagged.class, lazy = true)
    private Collection<Tag> tags;

    @Relations(edges = Categorized.class, lazy = true)
    private Collection<Category> categories;

    @Relations(edges = Authorized.class, lazy = true)
    private Collection<Authorization> Authorizations;

    @Relations(edges = Uploaded.class, lazy = false)
    private User photoOwner;

    @Relations(edges = Downloaded.class, lazy = true)
    private Collection<User> downloaderUsers;

    @Relations(edges = Collectioned.class, lazy = true)
    private PhotoCollection photoCollection;

    @Relations(edges = Basketed.class, lazy = true)
    private Collection<Basket> baskets;

    @Relations(edges = Liked.class, lazy = true)
    private Collection<User> users;

}

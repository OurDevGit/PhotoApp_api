package com.picktur.server.entities.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class PhotoDto {

    private String id;

    private Instant uploadInstant;

    private boolean published;

    private String url_lr;
    private String url_mr;
    private String url_hr;
    private String url_fr;

    private int lr_width;
    private int lr_heigh;

    private String title;
    private String description;

    private String accountToFollow;

    private List<TagDto> tags;
    private List<CategoryDto> categories;

    private List<AuthorizationDto> authorizations;
    List<BasketDto> baskets;

    private double rating;
    private double weight;

    private int likes;
    private int downloads;
    private int viewed;

    private String ownerId;
    private String owner;
    private String ownerIcon;

    private PhotoCollectionDto collection;

}

package com.picktur.server.entities.dto.photo_upload;

import com.picktur.server.entities.dto.RejectingMotiveDto;
import com.picktur.server.entities.photo_upload.SubmitStatus;
import lombok.Data;

import java.time.Instant;
import java.util.Collection;

@Data
public class TemporaryPhotoDto {

    private String id;

    private SubmitStatus submitStatus;

    private Instant uploadInstant;

    private String url_lr;
    private String url_mr;
    private String url_hr;
    private String url_fr;

    private int lr_width;
    private int lr_heigh;

    private String title;
    private String description;

    private String accountToFollow;

    private String collection;
    private Collection<String> containedTags;
    private Collection<String> tags;
    private Collection<String> categories;
    private Collection<TemporaryAuthorizationDto> Authorizations;

    private Collection<RejectingMotiveDto> rejectingMotives;

    private double rating;
    private double weight;

    private String ownerId;
    private String owner;

}

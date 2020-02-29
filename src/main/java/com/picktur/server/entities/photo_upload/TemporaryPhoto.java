package com.picktur.server.entities.photo_upload;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Relations;
import com.picktur.server.entities.Authorization;
import com.picktur.server.entities.User;
import com.picktur.server.relations.TemporaryAuthorized;
import com.picktur.server.relations.TemporaryUploaded;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

//@HashIndex(fields = { "id" }, unique = true)
@Document("TemporaryPhoto")
@Data
public class TemporaryPhoto {

    @Id
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

    @Relations(edges = TemporaryAuthorized.class, lazy = false)
    private Collection<Authorization> authorizations;

    private double rating;
    private double weight;

    @Relations(edges = TemporaryUploaded.class, lazy = false)
    private User photoOwner;

    private List<String> rejectingMotives;

}

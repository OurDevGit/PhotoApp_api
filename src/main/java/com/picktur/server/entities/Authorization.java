package com.picktur.server.entities;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Relations;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.Collection;

@Document("authorizations")
@Data
public class Authorization {

    @Id
    private String id;

    private String caption;

    private Instant uploadInstant;

    @Relations(edges = com.picktur.server.relations.UserAuthorized.class, lazy = true)
    private User user;

    @Relations(edges = com.picktur.server.relations.Authorized.class, lazy = true)
    private Collection<Photo> authorizedPhotos;

    @Relations(edges = com.picktur.server.relations.TemporaryAuthorized.class, lazy = true)
    private Collection<TemporaryPhoto> temporaryAuthorizedPhotos;

    private AuthorizationKind authorizationKind;

    private String documentUrl;

    public Authorization() {
        super();
    }
}

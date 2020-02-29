package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.Authorization;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Edge(value="temporaryAuthorized")
@Data
public class TemporaryAuthorized {

    @Id
    private String id;

    @From
    private Authorization authorization;

    @To
    private TemporaryPhoto authorizedPhoto;

    public TemporaryAuthorized(Authorization authorization, TemporaryPhoto authorizedPhoto) {
        this.authorization = authorization;
        this.authorizedPhoto = authorizedPhoto;
    }
}

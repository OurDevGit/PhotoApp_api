package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.Authorization;
import com.picktur.server.entities.Photo;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Edge(value="Authorized")
@Data
public class Authorized {

    @Id
    private String id;

    @From
    private Authorization authorization;

    @To
    private Photo authorizedPhoto;

    public Authorized(Authorization authorization, Photo authorizedPhoto) {
        this.authorization = authorization;
        this.authorizedPhoto = authorizedPhoto;
    }
}

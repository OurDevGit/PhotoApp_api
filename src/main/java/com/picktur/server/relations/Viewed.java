package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.Photo;
import com.picktur.server.entities.User;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Edge
@Data
public class Viewed {

    @Id
    private String id;

    @From
    private User user;

    @To
    private Photo viewedPhoto;

    public Viewed(User user, Photo viewedPhoto) {
        this.user = user;
        this.viewedPhoto = viewedPhoto;
    }
}

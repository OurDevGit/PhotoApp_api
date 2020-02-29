package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.Photo;
import com.picktur.server.entities.User;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Edge(value="liked")
@Data
public class Liked {

    @Id
    private String id;

    @From
    private User user;

    @To
    private Photo likedPhoto;

    public Liked(User user, Photo likedPhoto) {
        this.user = user;
        this.likedPhoto = likedPhoto;
    }
}

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
public class Uploaded {

    @Id
    private String id;

    @From
    private User owner;
    @To
    private Photo photo;

    public Uploaded(User owner, Photo photo) {
        this.owner = owner;
        this.photo = photo;
    }
}

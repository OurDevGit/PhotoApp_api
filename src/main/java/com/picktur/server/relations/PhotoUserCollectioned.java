package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.PhotoCollection;
import com.picktur.server.entities.User;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Edge("collectioned")
@Data
public class PhotoUserCollectioned {
    @Id
    private String id;
    @From
    private User user;
    @To
    private PhotoCollection collection;

    public PhotoUserCollectioned( User user, PhotoCollection collection) {
        this.collection = collection;
        this.user = user;
    }
}

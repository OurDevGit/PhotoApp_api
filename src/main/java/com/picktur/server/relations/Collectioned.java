package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.Photo;
import com.picktur.server.entities.PhotoCollection;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Edge("collectioned")
@Data
public class Collectioned {
    @Id
    private String id;
    @From
    private PhotoCollection collection;
    @To
    private Photo collectionedPhoto;

    public Collectioned(PhotoCollection collection, Photo collectionedPhoto) {
        this.collection = collection;
        this.collectionedPhoto = collectionedPhoto;
    }
}

package com.picktur.server.entities;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Relations;
import com.picktur.server.relations.Collectioned;
import com.picktur.server.relations.PhotoUserCollectioned;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.Collection;

//@HashIndex(fields = { "name", "surname" }, unique = true)
@Document("photoCollections")
@Data
public class PhotoCollection {

    @Id
    private String id;

    private String name;
    private Instant creationInstant;
    private boolean published;

    @Relations(edges = Collectioned.class, lazy = true)
    private Collection<Photo> photos;

    @Relations(edges = PhotoUserCollectioned.class, lazy = true)
    private User user;

}

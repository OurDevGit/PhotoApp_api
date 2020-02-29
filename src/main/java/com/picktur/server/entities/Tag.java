package com.picktur.server.entities;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Relations;
import com.picktur.server.relations.Tagged;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Collection;

//@HashIndex(fields = { "name", "surname" }, unique = true)
@Document("tags")
@Data
public class Tag {

    @Id
    private String id;

    private String value;

    private boolean isNew;

    @Relations(edges = Tagged.class, lazy = true)
    private Collection<Photo> taggedPhoto;


}

package com.picktur.server.entities;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Relations;
import com.picktur.server.relations.Categorized;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Collection;

//@HashIndex(fields = { "name", "surname" }, unique = true)
@Document("categories")
@Data
public class Category {

    @Id
    private String id;

    private String value;

    @Relations(edges = Categorized.class, lazy = true)
    private Collection<Photo> categorizedPhoto;


}

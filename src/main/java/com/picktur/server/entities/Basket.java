package com.picktur.server.entities;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Relations;
import com.picktur.server.relations.Basketed;
import com.picktur.server.relations.UserBasketing;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Collection;

@Document("baskets")
@Data
public class Basket {

    @Id
    private String id;

    @Relations(edges = UserBasketing.class, lazy = true)
    private User user;

    private String value;

    @Relations(edges = Basketed.class, lazy = true)
    private Collection<Photo> photos;



}

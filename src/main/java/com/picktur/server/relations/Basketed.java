package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.Basket;
import com.picktur.server.entities.Photo;
import lombok.Data;
import org.springframework.data.annotation.Id;


@Edge(value="Basketed")
@Data
public class Basketed {

    @Id
    private String id;

    @From
    private Basket basket;

    @To
    private Photo basketedPhoto;

    public Basketed(Basket basket, Photo basketedPhoto) {
        this.basket = basket;
        this.basketedPhoto = basketedPhoto;
    }
}

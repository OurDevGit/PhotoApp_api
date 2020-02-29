package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.Basket;
import com.picktur.server.entities.User;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Edge(value="UserBasketing")
@Data
public class UserBasketing {

    @Id
    private String id;

    @From
    private User user;

    @To
    private Basket basket;

    public UserBasketing(User user, Basket basket) {
        this.user = user;
        this.basket = basket;
    }
}

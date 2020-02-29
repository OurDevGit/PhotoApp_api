package com.picktur.server.entities.dto;

import com.picktur.server.entities.Basket;
import com.picktur.server.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class BasketDto {

    private String id;

    private User user;

    private String value;

    private Collection<PhotoDto> photos;

    public BasketDto (Basket basket){
        id = basket.getId();
        user = basket.getUser();
        value = basket.getValue();
    }

}

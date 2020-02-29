package com.picktur.server.controllers.user_actions;

import lombok.Data;

import java.util.List;

@Data
public class AddToBasketRequest {
    private String photoId;
    private List<String> baskets;
}

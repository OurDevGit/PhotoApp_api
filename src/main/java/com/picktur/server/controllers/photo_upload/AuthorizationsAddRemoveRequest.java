package com.picktur.server.controllers.photo_upload;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AuthorizationsAddRemoveRequest {

    private ArrayList<String> authorizationIds;
    private ArrayList<String> photoIds;

}

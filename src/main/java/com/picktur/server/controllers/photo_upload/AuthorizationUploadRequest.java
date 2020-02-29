package com.picktur.server.controllers.photo_upload;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AuthorizationUploadRequest {

    private String authorizationId;
    private ArrayList<String> authorizedPhotos;

}

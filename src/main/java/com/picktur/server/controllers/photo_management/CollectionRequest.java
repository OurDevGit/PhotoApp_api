package com.picktur.server.controllers.photo_management;

import lombok.Data;

import java.util.List;

@Data
public class CollectionRequest {
    private List<String> photos;
    private String photoCollectionName;
    private  String photoCollectionId;
    private  String oldPhotoCollectionId;
}

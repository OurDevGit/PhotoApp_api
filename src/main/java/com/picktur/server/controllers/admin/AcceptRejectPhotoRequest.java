package com.picktur.server.controllers.admin;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AcceptRejectPhotoRequest {
    private String photoToManage;
    private ArrayList<String> motivesForRejectingIds;
    private double rating;
}

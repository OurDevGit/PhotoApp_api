package com.picktur.server.controllers.admin;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RejectPhotosRequest {
   private ArrayList<String> photosToReject;
   private ArrayList<String> motivesForRejectingIds;
}

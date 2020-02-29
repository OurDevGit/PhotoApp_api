package com.picktur.server.controllers.photo_upload;

import com.picktur.server.entities.dto.photo_upload.TemporaryAuthorizationDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class AuthorizationListResponse {

    private HashMap<String, ArrayList<TemporaryAuthorizationDto>> authorizationMap;
    private ArrayList<TemporaryAuthorizationDto> authorizationList;

}

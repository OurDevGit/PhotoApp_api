package com.picktur.server.entities.dto.photo_upload;

import com.picktur.server.entities.AuthorizationKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;

@Data
@AllArgsConstructor @NoArgsConstructor
public class TemporaryAuthorizationDto {

    private String id;

    private String caption;

    private Instant uploadInstant;

    private ArrayList<String> authorizedPhotosId;

    private String documentUrl;

    // Can be SUBJECT, PROPERTY, BRAND, COPYRIGH
    private AuthorizationKind authorizationKind;


}

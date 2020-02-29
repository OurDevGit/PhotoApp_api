package com.picktur.server.controllers.photo_upload;

import com.picktur.server.entities.dto.photo_upload.TemporaryAuthorizationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class AuthorizationUploadResponse {

    private TemporaryAuthorizationDto authorization;

}

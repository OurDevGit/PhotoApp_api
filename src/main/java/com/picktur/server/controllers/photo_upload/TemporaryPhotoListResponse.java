package com.picktur.server.controllers.photo_upload;

import com.picktur.server.entities.dto.photo_upload.TemporaryPhotoDto;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TemporaryPhotoListResponse {

    private ArrayList<TemporaryPhotoDto> photos;

}

package com.picktur.server.controllers.photo_upload;

import com.picktur.server.entities.dto.photo_upload.TemporaryPhotoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiplePhotoUploadResponse {

    private ArrayList<TemporaryPhotoDto> photos;

}

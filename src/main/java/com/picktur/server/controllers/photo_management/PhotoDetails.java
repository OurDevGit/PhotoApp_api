package com.picktur.server.controllers.photo_management;

import com.picktur.server.entities.dto.PhotoDto;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PhotoDetails {
    PhotoDto photoDto;
    Page<PhotoDto> similarPhotos;
}

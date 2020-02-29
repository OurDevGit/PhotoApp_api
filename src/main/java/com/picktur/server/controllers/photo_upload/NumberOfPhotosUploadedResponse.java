package com.picktur.server.controllers.photo_upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumberOfPhotosUploadedResponse {

    private int toBeSubmitted;
    private int submitted;
    private int accepted;
    private int rejected;

}

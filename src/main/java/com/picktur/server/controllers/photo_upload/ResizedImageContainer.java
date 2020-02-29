package com.picktur.server.controllers.photo_upload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.image.BufferedImage;

@Data @AllArgsConstructor
public class ResizedImageContainer {
    private BufferedImage image;
    private String url;
    private int lr_width;
    private int lr_heigh;
}

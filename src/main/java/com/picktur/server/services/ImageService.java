package com.picktur.server.services;

import lombok.SneakyThrows;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class ImageService {

    @SneakyThrows
    public BufferedImage resize(BufferedImage image, int resolution) {
        BufferedImage result = Thumbnails.of(image).size(resolution*4, resolution).outputFormat("jpg").asBufferedImage();
        return result;
    }

    @SneakyThrows
    public BufferedImage resizeAndMarkImage(BufferedImage image, BufferedImage watermark, int resolution) {

        BufferedImage resizedWaterMark = Thumbnails.of(watermark).size((int) (resolution/2.5), (int) (resolution/2.5)).outputFormat("jpg").asBufferedImage();

        BufferedImage result = Thumbnails.of(image).size(resolution*4, resolution)
                .watermark(Positions.CENTER, resizedWaterMark, 0.3f)
                .outputFormat("jpg").asBufferedImage();

        return result;
    }
}

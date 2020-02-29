package com.picktur.server.entities.dto;

import com.picktur.server.entities.PhotoCollection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.Collection;

@Data
@NoArgsConstructor
public class PhotoCollectionDto {

    @Id
    private String id;

    private String name;
    private Instant creationInstant;
    private boolean published;

    private Collection<PhotoDto> photos;

    public PhotoCollectionDto(String name) {
        this.name = name;
        this.setId("Temporary Collection without Id");
    }

    public PhotoCollectionDto(PhotoCollection collection) {
        this.name = collection.getName();
        this.id = collection.getId();
    }
}

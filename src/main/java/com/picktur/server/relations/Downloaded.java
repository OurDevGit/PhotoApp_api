package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.Photo;
import com.picktur.server.entities.User;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Edge("downloaded")
@Data
public class Downloaded {

    @Id
    private String id;

    @From
    private User downloader;

    @To
    private Photo photo;

    public Downloaded(User downloader, Photo photo) {
        this.downloader = downloader;
        this.photo = photo;
    }
}

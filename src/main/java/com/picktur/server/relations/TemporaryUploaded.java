package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.User;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Edge(value="temporaryUploaded")
@Data
public class TemporaryUploaded {

    @Id
    private String id;

    @From
    private User owner;
    @To
    private TemporaryPhoto photo;

    public TemporaryUploaded(User owner, TemporaryPhoto photo) {
        super();
        this.owner = owner;
        this.photo = photo;
    }

}

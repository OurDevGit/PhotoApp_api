package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.Photo;
import com.picktur.server.entities.Tag;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Edge("tagged")
@Data
public class Tagged {
    @Id
    private String id;
    @From
    private Tag tag;
    @To
    private Photo taggedPhoto;

    public Tagged(Tag tag, Photo taggedPhoto) {
        this.tag = tag;
        this.taggedPhoto = taggedPhoto;
    }
}

package com.picktur.server.entities;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Relations;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;
import com.picktur.server.relations.RejectingMotived;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Collection;

@Document("rejectingMotives")
@Data
public class RejectingMotive {

    @Id
    private String id;

    private String value;

    @Relations(edges = RejectingMotived.class, lazy = true)
    private Collection<TemporaryPhoto> taggedPhoto;

}

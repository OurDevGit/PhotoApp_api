package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.picktur.server.entities.RejectingMotive;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Edge(value="rejectingMotived")
@Data
public class RejectingMotived {

    @Id
    private String id;

    @From
    private TemporaryPhoto authorizedPhoto;

    @From
    private RejectingMotive rejectingMotive;


    public RejectingMotived(RejectingMotive rejectingMotive, TemporaryPhoto authorizedPhoto) {
        this.rejectingMotive = rejectingMotive;
        this.authorizedPhoto = authorizedPhoto;
    }
}

package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.Authorization;
import com.picktur.server.entities.User;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Edge
@Data
public class UserAuthorized {

    @Id
    private String id;

    @From
    private User user;

    @To
    private Authorization authorization;

    public UserAuthorized( User user, Authorization authorization) {
        this.user = user;
        this.authorization = authorization;
    }
}

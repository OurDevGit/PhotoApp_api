package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.User;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Edge("following")
@Data
public class Following {
    @Id
    private String id;
    @From
    private User follower;
    @To
    private User followed;
    private LocalDateTime startedFollowing;
    private LocalDateTime endedFollowing;
    private boolean isFollowingActive;

    public Following(final User follower, final User followed){
        this.follower = follower;
        this.followed = followed;
        this.startedFollowing = LocalDateTime.now();
        this.isFollowingActive = true;
    }

}

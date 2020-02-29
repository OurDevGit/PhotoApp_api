package com.picktur.server.entities.user_registration;

import com.arangodb.springframework.annotation.Document;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Document("roles")
@Data
public class Role {
    @Id
    private String id;

    private RoleName name;
}

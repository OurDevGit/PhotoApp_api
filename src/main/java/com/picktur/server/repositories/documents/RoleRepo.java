package com.picktur.server.repositories.documents;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.entities.user_registration.Role;
import com.picktur.server.entities.user_registration.RoleName;

import java.util.Optional;

public interface RoleRepo extends ArangoRepository<Role, String> {
    Optional<Role> findByName(RoleName roleName);
}

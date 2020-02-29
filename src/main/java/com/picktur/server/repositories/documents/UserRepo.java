package com.picktur.server.repositories.documents;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.entities.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepo extends ArangoRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Collection<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}

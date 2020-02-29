package com.picktur.server.repositories.relationships;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.relations.UserAuthorized;

public interface UserAuthorizedRepo extends ArangoRepository<UserAuthorized, String> {


}

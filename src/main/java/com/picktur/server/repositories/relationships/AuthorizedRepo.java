package com.picktur.server.repositories.relationships;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.relations.Authorized;

public interface AuthorizedRepo extends ArangoRepository<Authorized, String> {


}

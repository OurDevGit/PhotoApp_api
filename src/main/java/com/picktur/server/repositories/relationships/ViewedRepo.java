package com.picktur.server.repositories.relationships;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.relations.Viewed;

public interface ViewedRepo extends ArangoRepository<Viewed, String> {

}

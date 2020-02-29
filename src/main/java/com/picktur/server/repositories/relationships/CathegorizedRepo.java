package com.picktur.server.repositories.relationships;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.relations.Categorized;

public interface CathegorizedRepo extends ArangoRepository<Categorized, String> {



}

package com.picktur.server.repositories.relationships;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.relations.Tagged;

public interface TaggedRepo extends ArangoRepository<Tagged, String> {



}

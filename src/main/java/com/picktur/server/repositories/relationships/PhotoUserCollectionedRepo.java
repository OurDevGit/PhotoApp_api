package com.picktur.server.repositories.relationships;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.relations.PhotoUserCollectioned;

public interface PhotoUserCollectionedRepo extends ArangoRepository<PhotoUserCollectioned, String> {



}

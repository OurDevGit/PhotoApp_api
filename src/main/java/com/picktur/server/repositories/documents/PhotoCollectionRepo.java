package com.picktur.server.repositories.documents;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.entities.PhotoCollection;

public interface PhotoCollectionRepo extends ArangoRepository<PhotoCollection, String> {
    PhotoCollection findByNameAndUserId(String collectionName, String userId);
}

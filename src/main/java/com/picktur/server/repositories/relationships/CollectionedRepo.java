package com.picktur.server.repositories.relationships;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.relations.Collectioned;

public interface CollectionedRepo extends ArangoRepository<Collectioned, String> {


    public Collectioned findByCollectionedPhoto_Id(String photoId);

}

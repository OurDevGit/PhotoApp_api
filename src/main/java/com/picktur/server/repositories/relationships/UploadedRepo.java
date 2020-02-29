package com.picktur.server.repositories.relationships;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.relations.Uploaded;

public interface UploadedRepo extends ArangoRepository<Uploaded, String> {

    void deleteByPhoto_id(String id);
}

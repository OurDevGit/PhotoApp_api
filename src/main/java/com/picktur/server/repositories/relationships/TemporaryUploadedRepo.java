package com.picktur.server.repositories.relationships;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.relations.TemporaryUploaded;

public interface TemporaryUploadedRepo extends ArangoRepository<TemporaryUploaded, String> {

}

package com.picktur.server.repositories.relationships;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.entities.Authorization;
import com.picktur.server.relations.TemporaryAuthorized;

public interface TemporaryAuthorizedRepo extends ArangoRepository<TemporaryAuthorized, String> {

    TemporaryAuthorized findTopByAuthorizedPhotoIdAndAuthorizationId( String authorizedPhoto, String authorization);

}

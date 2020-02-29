package com.picktur.server.repositories.documents;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.entities.Authorization;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;

import java.util.Collection;

public interface AuthorizationRepo extends ArangoRepository<Authorization, String> {

    Collection<Authorization> findAllByTemporaryAuthorizedPhotos(TemporaryPhoto photo);
    Iterable<Authorization> findAllByUserId(String userId);

}

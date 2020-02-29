package com.picktur.server.repositories.documents;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.entities.photo_upload.SubmitStatus;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;

import java.util.ArrayList;

public interface TemporaryPhotoRepo extends ArangoRepository<TemporaryPhoto, String> {

    Iterable<TemporaryPhoto> findAllByIdIn(ArrayList<String> idList);
    Iterable<TemporaryPhoto> findAllBySubmitStatusEquals(SubmitStatus submitStatus);
    Iterable<TemporaryPhoto> findAllBySubmitStatusEqualsAndPhotoOwnerIdIs(SubmitStatus submitStatus, String userId);
    Iterable<TemporaryPhoto> findAllByPhotoOwnerIdIs(String userId);
}

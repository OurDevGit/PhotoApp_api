package com.picktur.server.repositories.documents;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.entities.RejectingMotive;

import java.util.List;

public interface RejectingMotiveRepo extends ArangoRepository<RejectingMotive, String> {
    List<RejectingMotive> findAllByIdIn(List<String> ids);

}

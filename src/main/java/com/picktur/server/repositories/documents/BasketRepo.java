package com.picktur.server.repositories.documents;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.entities.Basket;

public interface BasketRepo extends ArangoRepository<Basket, String> {

}

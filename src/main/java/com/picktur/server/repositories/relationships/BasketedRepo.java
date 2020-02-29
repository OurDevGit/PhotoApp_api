package com.picktur.server.repositories.relationships;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.relations.Basketed;

import java.util.ArrayList;
import java.util.Collection;

public interface BasketedRepo extends ArangoRepository<Basketed, String> {

    Collection<Basketed> findAllByBasket_IdAndBasketedPhoto_IdIn(String basket, ArrayList<String> photos);

    Collection<Basketed> findAllByBasketedPhoto_Id(String photo);

    void deleteAllByBasket_IdAndBasketedPhoto_IdIn(String basket, ArrayList<String> photos);

    void deleteAllByBasket_Id(String basket);
}

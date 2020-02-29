package com.picktur.server.repositories.documents;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.entities.Category;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends ArangoRepository<Category, String> {
    Optional<Category> findByValue(String name);
    List<Category> findAllByValueIgnoreCaseContains(String word);
    List<Category> findAllByValueIgnoreCaseIn(Collection<String> categoryValues);
}

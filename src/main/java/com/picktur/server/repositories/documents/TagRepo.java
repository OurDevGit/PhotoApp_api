package com.picktur.server.repositories.documents;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.entities.Tag;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepo extends ArangoRepository<Tag, String> {
    Optional<Tag> findByValue(String name);

    Optional<Tag> findFirstByIdContaining(String s);

   //@Query("FOR t IN tags FILTER t.value like '@word'")
    List<Tag> readAllByValueIgnoreCaseContaining(String word);

    List<Tag> findByValueContainsAllIgnoreCase(String word);

    List<Tag> findAllByValueInIgnoreCase(Collection<String> tagValues);
}

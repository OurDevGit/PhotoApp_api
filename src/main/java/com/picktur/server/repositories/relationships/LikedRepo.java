package com.picktur.server.repositories.relationships;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.relations.Liked;

public interface LikedRepo extends ArangoRepository<Liked, String> {

    public void removeByLikedPhoto_IdAndUser_Id(String photoId, String userId);

    public Liked findFirstByLikedPhoto_IdAndUser_Id(String photoId, String userId);


}

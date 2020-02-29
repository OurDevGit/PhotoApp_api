package com.picktur.server.repositories.relationships;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.relations.UserBasketing;

public interface UserBasketingRepo extends ArangoRepository<UserBasketing, String> {

    public UserBasketing findByBasket_Id(String basket);
    public void deleteByBasket_Id(String basket);

}

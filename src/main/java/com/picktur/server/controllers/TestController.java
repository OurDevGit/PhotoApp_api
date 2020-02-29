package com.picktur.server.controllers;

import com.arangodb.springframework.core.ArangoOperations;
import com.picktur.server.entities.User;
import com.picktur.server.repositories.documents.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    UserRepo repo;
    @Autowired
    ArangoOperations operations;

    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    @RequestMapping("/createUser")
    public String createUser(){
        operations.collection("users").drop();
        User user = new User();
        user.setName("Pluto");
        user.setSurname("De Plutis");
        user.setEmail("a.taini");
        user.setPassword("123");
        repo.save(user);
        User newUser = repo.findById(user.getId()).get();



        return "User " + newUser.getName() +  " Created.";
    }
}

package com.picktur.server.services.instagram_checks;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class InstagramUser {

    @Autowired
    InstagramLogin instagramLogin;

    public InstagramSearchUsernameResult userResult(String name) throws IOException {
        Instagram4j instagram = instagramLogin.getInstagram();
        InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(name));
        StringBuffer sb = new StringBuffer();
        return userResult;
    }



}

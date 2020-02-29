package com.picktur.server.services.instagram_checks;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InstagramLogin {

    @Cacheable("ig_account")
    public Instagram4j getInstagram() throws IOException {
        Instagram4j instagram = Instagram4j.builder().username("plutus_in_fabula").password("hsaN123!!").build();
        instagram.setup();
        instagram.login();
        System.out.println("Login done" );
        return instagram;
    }

}

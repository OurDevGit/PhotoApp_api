package com.picktur.server.entities.user_registration;

import com.picktur.server.entities.User;
import lombok.Data;


@Data
public class AuditorProvider /*implements AuditorAware<User>*/ {

    private User user;

    //@Override
    //public Optional<User> getCurrentAuditor() {
    //    return null; // Optional.ofNullable(user);
    //}
}
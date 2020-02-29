package com.picktur.server.exceptions;

import com.picktur.server.entities.Tag;
import lombok.SneakyThrows;

import java.util.ArrayList;

public class MethodNotDevelopedException extends ArrayList<Tag> {
    @SneakyThrows
    public MethodNotDevelopedException(String brokenMethod) {
        throw new Exception("Method " + brokenMethod + " not yet developed");
    }
}

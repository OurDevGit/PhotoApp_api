package com.picktur.server.controllers.list_management;

import com.picktur.server.constants.Scope;
import lombok.Data;

import java.util.List;

@Data
public class ListRequest {
    private List<String> searchedTags;
    private List<String> searchedCategories;
    private List<String> searchedWords;
    private Scope scope;
    private boolean ageProtected;

}

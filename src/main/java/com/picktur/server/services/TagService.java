package com.picktur.server.services;

import com.picktur.server.entities.Tag;
import com.picktur.server.repositories.documents.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    TagRepo tagRepo;

    //@Autowired
    //CacheManager cacheManager;

    public void addNewTags(List<String> newTags) {

        List<Tag> tagsToAdd = new ArrayList<>();

        newTags.iterator().forEachRemaining(tag -> {
            Tag newTag = new Tag();
            newTag.setValue(tag);
            newTag.setNew(true);
            tagsToAdd.add(newTag);
        });

        if (tagsToAdd.size() > 0) tagRepo.saveAll(tagsToAdd);
        //cacheManager.getCache("allTags").clear();
    }
}

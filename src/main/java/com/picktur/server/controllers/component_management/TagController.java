package com.picktur.server.controllers.component_management;

import com.picktur.server.entities.Tag;
import com.picktur.server.entities.dto.TagDto;
import com.picktur.server.repositories.documents.TagRepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

@Data
@RestController
@RequestMapping("/api/public/tags")
public class TagController {

    @Autowired
    TagRepo repo;

    @GetMapping("/containing")
    public TagResponse tagsContaining(@RequestParam(value = "wordCrumble") String wordCrumble) {
        ArrayList<Tag> tagList = (ArrayList<Tag>) repo.readAllByValueIgnoreCaseContaining(wordCrumble  );//.orElse(new ArrayList<Tag>());

        ArrayList<TagDto> resultList = populateResulList(tagList);

        resultList.sort(Comparator.comparing(TagDto::getValue));

        TagResponse response = new TagResponse(resultList);
        return response;
    }

    @GetMapping("/getAll")
    public TagResponse getAllTags() {
        ArrayList<Tag> tagList = new ArrayList<>();
        repo.findAll().iterator().forEachRemaining(tagList::add);

        ArrayList<TagDto> resultList = populateResulList(tagList);

        resultList.sort(Comparator.comparing(TagDto::getWeight));

        TagResponse response = new TagResponse(resultList);
        return response;
    }

    private ArrayList<TagDto> populateResulList(ArrayList<Tag> tagList) {
        ArrayList<TagDto> resultList = new ArrayList();

        for (Tag t: tagList) {
            TagDto actualTagDto = new TagDto(t.getId(), t.getValue(), new Random().nextDouble());
            resultList.add(actualTagDto);
        }
        return resultList;
    }
}

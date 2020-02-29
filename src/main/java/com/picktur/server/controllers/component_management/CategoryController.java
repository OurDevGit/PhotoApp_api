package com.picktur.server.controllers.component_management;

import com.picktur.server.entities.Category;
import com.picktur.server.entities.dto.CategoryDto;
import com.picktur.server.repositories.documents.CategoryRepo;
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
@RequestMapping("/api/public/categories")
public class CategoryController {
    @Autowired
    CategoryRepo repo;

    @GetMapping("/containing")
    public CategoryResponse categoriesContaining(@RequestParam(value = "wordCrumble") String wordCrumble) {

        ArrayList<Category> categoryList = (ArrayList) repo.findAllByValueIgnoreCaseContains(wordCrumble);
        ArrayList<CategoryDto> resultList = populateResulList(categoryList);

        resultList.sort(Comparator.comparing(CategoryDto::getValue));

        CategoryResponse response = new CategoryResponse(resultList);
        return response;
    }

    @GetMapping("/getAll")
    public CategoryResponse getAllCategorys() {
        ArrayList<Category> categoryList = new ArrayList<>();
        repo.findAll().iterator().forEachRemaining(categoryList::add);

        ArrayList<CategoryDto> resultList = populateResulList(categoryList);

        resultList.sort(Comparator.comparing(CategoryDto::getWeight));

        CategoryResponse response = new CategoryResponse(resultList);
        return response;
    }

    private ArrayList<CategoryDto> populateResulList(ArrayList<Category> categoryList) {
        ArrayList<CategoryDto> resultList = new ArrayList();

        for (Category t: categoryList) {
            CategoryDto actualCategoryDto = new CategoryDto(t.getId(), t.getValue(), new Random().nextDouble());
            resultList.add(actualCategoryDto);
        }
        return resultList;
    }
}

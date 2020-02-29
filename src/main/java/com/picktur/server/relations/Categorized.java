package com.picktur.server.relations;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.picktur.server.entities.Photo;
import com.picktur.server.entities.Category;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Edge
@Data
public class Categorized {
    @Id
    private String id;
    @From
    private Category category;
    @To
    private Photo categorizedPhoto;

    public Categorized(Category category, Photo categorizedPhoto) {
        this.category = category;
        this.categorizedPhoto = categorizedPhoto;
    }
}

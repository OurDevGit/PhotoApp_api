package com.picktur.server.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String id;
    private String value;
    private double weight;

    public CategoryDto(String id, String value) {
        this.id = id;
        this.value = value;
    }
}

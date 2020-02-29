package com.picktur.server.controllers.component_management;

import com.picktur.server.entities.dto.TagDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TagResponse {
    @NonNull
    private ArrayList<TagDto> tags;
}

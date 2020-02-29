package com.picktur.server.entities.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class RejectingMotiveDto {

    @Id
    private String id;

    private String value;

}

package com.jojobi.spring5webfluxrest.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Category {

    @Id
    private String id;

    private String description;

    @Builder
    public Category(String id, String description) {
        this.id = id;
        this.description = description;
    }
}

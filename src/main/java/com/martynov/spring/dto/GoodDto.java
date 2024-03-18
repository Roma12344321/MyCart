package com.martynov.spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoodDto {
    private int id;
    private String name;
    private int price;
    private String description;
    private int likeCount;
    private int commentCount;
    private CategoryDto category;
}
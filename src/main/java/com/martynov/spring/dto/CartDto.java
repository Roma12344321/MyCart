package com.martynov.spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDto {
    private int id;
    private GoodDto good;
    private int amount;
}

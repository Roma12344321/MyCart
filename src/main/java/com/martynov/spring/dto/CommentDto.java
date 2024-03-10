package com.martynov.spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CommentDto {
    private int id;
    private String text;
    private int star;
    private Date date;
    private PersonDto person;
}

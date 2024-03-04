package com.martynov.spring.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@Data
public class Comment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "text")
    @Size(min = 1, max = 1000, message = "Некорректное описание")
    private String text;

    @Column(name = "star")
    @Min(value = 1, message = "Значение должно быть не менее 1")
    @Max(value = 10, message = "Значение должно быть не более 10")
    private int star;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "good_id", referencedColumnName = "id")
    private Good good;

    public Comment(String text, int star, Person person, Good good) {
        this.text = text;
        this.star = star;
        this.person = person;
        this.good = good;
    }
}

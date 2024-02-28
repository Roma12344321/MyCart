package com.martynov.spring.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "category")
@NoArgsConstructor
@Data
public class Category {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @Size(min = 0,max = 20)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Good> goodList;
}

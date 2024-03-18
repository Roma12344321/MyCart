package com.martynov.spring.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@Table(name = "person")
public class Person {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Некорректное имя")
    private String username;

    @Column(name = "year")
    @Min(value = 1901, message = "Некорректный год")
    private int year;

    @Column(name = "mail")
    @Email(message = "Некорректная почта")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "person")
    private List<Cart> carts;

    @OneToMany(mappedBy = "person")
    private List<Order> orders;

    @OneToOne(mappedBy = "person")
    private Balance balance;

    @OneToMany(mappedBy = "person")
    private List<Comment> comments;

    @OneToMany(mappedBy = "person")
    private List<Like> likes;
}
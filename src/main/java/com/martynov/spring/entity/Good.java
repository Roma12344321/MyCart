package com.martynov.spring.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "good")
@Data
@NoArgsConstructor
public class Good {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "cat_id",referencedColumnName = "id")
    private Category category;

    @Column(name = "image_path")
    private String imagePath;

    @OneToMany(mappedBy = "good")
    private List<Cart> carts;

    @OneToMany(mappedBy = "good")
    private List<Order> orders;

    @ManyToMany(mappedBy = "goods")
    private List<Tag> tags;

    @OneToMany(mappedBy = "good")
    private List<Comment> comments;

    @OneToMany(mappedBy = "good")
    private List<Like> likes;

    public Good(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}

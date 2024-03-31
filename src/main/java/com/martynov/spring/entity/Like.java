package com.martynov.spring.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "likes")
@NoArgsConstructor
@Data
public class Like implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", unique = true)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "good_id", referencedColumnName = "id", unique = true)
    private Good good;

    public Like(Person person, Good good) {
        this.person = person;
        this.good = good;
    }
}

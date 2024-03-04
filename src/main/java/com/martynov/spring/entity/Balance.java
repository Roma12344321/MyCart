package com.martynov.spring.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "balance")
@NoArgsConstructor
@Data
public class Balance {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sum")
    private int sum;

    @OneToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;

    public Balance(int sum) {
        this.sum = sum;
    }
}

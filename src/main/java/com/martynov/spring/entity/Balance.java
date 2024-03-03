package com.martynov.spring.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

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
    private double sum;

    @OneToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Person person;
}

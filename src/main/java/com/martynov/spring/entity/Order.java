package com.martynov.spring.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "good_id",referencedColumnName = "id")
    private Good good;

    @Column(name = "amount")
    private int amount;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private Date date;

    public Order(Person person, Good good, int amount, String address, String status, Date date) {
        this.person = person;
        this.good = good;
        this.amount = amount;
        this.address = address;
        this.status = status;
        this.date = date;
    }
}

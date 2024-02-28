package com.martynov.spring.repositories;

import com.martynov.spring.entity.Cart;
import com.martynov.spring.entity.Good;
import com.martynov.spring.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    List<Cart> findByPerson(Person person);
}
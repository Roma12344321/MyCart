package com.martynov.spring.repositories;

import com.martynov.spring.entity.Good;
import com.martynov.spring.entity.Order;
import com.martynov.spring.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
}

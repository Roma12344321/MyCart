package com.martynov.spring.repositories;

import com.martynov.spring.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodRepository extends JpaRepository<Good,Integer> {
}

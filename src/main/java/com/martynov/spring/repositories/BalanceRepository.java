package com.martynov.spring.repositories;

import com.martynov.spring.entity.Balance;
import com.martynov.spring.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance,Integer> {
    Balance findByPerson(Person person);
}

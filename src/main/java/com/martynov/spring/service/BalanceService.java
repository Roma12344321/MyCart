package com.martynov.spring.service;

import com.martynov.spring.entity.Balance;
import com.martynov.spring.entity.Person;
import com.martynov.spring.repositories.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final PersonService personService;

    @Transactional
    public void save(Balance balance) {
        balanceRepository.save(balance);
    }

    @Transactional(readOnly = true)
    public Balance getCurrentBalance() {
        Person person = personService.getCurrentPerson();
        return findByPerson(person);
    }

    @Transactional
    public void addSum(Balance balance) {
        Person person = personService.getCurrentPerson();
        Balance balanceFromDb = findByPerson(person);
        int sumAfter = balanceFromDb.getSum() + balance.getSum();
        balanceFromDb.setSum(sumAfter);
        person.setBalance(balanceFromDb);
    }

    @Transactional(readOnly = true)
    public Balance findByPerson(Person person) {
        return balanceRepository.findByPerson(person);
    }
}

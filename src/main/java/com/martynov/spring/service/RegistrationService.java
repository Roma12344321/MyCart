package com.martynov.spring.service;

import com.martynov.spring.entity.Person;
import com.martynov.spring.repositories.PeopleRepository;
import com.martynov.spring.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, PeopleRepository peopleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public void register(Person person) {
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setRole(Role.ROLE_USER);
        person.setCreatedAt(new Date());
        person.setPassword(encodedPassword);
        peopleRepository.save(person);
    }
}

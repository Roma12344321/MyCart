package com.martynov.spring.controllers;

import com.martynov.spring.entity.Person;
import com.martynov.spring.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PersonService personService;

    @GetMapping("/")
    public String index(Model model) {
        try {
            Person person = personService.getCurrentPerson();
            model.addAttribute("person",person);
        } catch (Exception e) {
            model.addAttribute("person",null);
        }
        return "index";
    }
}

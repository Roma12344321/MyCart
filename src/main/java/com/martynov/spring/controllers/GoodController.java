package com.martynov.spring.controllers;

import com.martynov.spring.entity.Comment;
import com.martynov.spring.entity.Good;
import com.martynov.spring.entity.Person;
import com.martynov.spring.service.CartService;
import com.martynov.spring.service.GoodService;
import com.martynov.spring.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/good")
public class GoodController {

    private final GoodService goodService;
    private final PersonService personService;
    private final CartService cartService;

    @GetMapping("")
    public String showAllGood(@RequestParam(value = "success", defaultValue = "false") Boolean success, Model model) {
        List<Good> goodList = goodService.findAllGood();
        model.addAttribute("success", success);
        model.addAttribute("goods", goodList);
        return "good/index";
    }

    @GetMapping("/{id}")
    public String showGoodById(@PathVariable("id") int id, Model model, @ModelAttribute("comment") Comment comment) {
        model.addAttribute("good", goodService.findById(id));
        try{
            model.addAttribute("person",personService.getCurrentPerson());
        } catch (Exception e) {}
        return "good/show_one";
    }

    @PreAuthorize("!hasRole('ROLE_WORKER')")
    @PostMapping("/{id}")
    public String addToCart(@PathVariable("id") int id) {
        Person person = personService.getCurrentPerson();
        if (person != null) {
            Good good = goodService.findById(id);
            cartService.addGoodToCart(person, good);
            return "redirect:/good?success=true";
        } else {
            return "redirect:/good?success=false";
        }
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getGoodImage(@PathVariable int id) {
        return goodService.getGoodImage(id);
    }
}

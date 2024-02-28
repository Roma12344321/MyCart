package com.martynov.spring.controllers;

import com.martynov.spring.entity.Good;
import com.martynov.spring.entity.Person;
import com.martynov.spring.service.CartService;
import com.martynov.spring.service.GoodService;
import com.martynov.spring.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/good")
public class GoodController {

    private final GoodService goodService;
    private final PersonService personService;
    private final CartService cartService;

    @GetMapping("")
    public String showAllGood(@RequestParam(value = "success", defaultValue = "false") Boolean success, Model model) {
        model.addAttribute("success", success);
        model.addAttribute("goods", goodService.findAllGood());
        return "good/index";
    }

    @GetMapping("/{id}")
    public String showGoodById(@PathVariable("id") int id, Model model) {
        model.addAttribute("good", goodService.findById(id));
        return "good/show_one";
    }

    @PostMapping("/{id}")
    public String addToCart(@PathVariable("id") int id) {
        Person person = personService.getCurrentPerson();
        if (person!=null){
            Good good = goodService.findById(id);
            cartService.addGoodToCart(person, good);
            return "redirect:/good?success=true";
        }
        else {
            return "redirect:/good?success=false";
        }
    }
    @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getGoodImage(@PathVariable int id) {
        return goodService.getGoodImage(id);
    }
}

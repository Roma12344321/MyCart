package com.martynov.spring.controllers;

import com.martynov.spring.entity.Cart;
import com.martynov.spring.entity.Person;
import com.martynov.spring.service.CartService;
import com.martynov.spring.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final PersonService personService;
    private final CartService cartService;

    @GetMapping("")
    public String index(Model model) {
        Person person = personService.getCurrentPerson();
        List<Cart> cartList = cartService.getAllCartsForPerson(person);
        model.addAttribute("cartList",cartList);
        return "cart/index";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        cartService.deleteOneCart(id);
        return "redirect:/cart";
    }

    @PostMapping("/{id}")
    public String add(@PathVariable("id") int id) {
        Person person = personService.getCurrentPerson();
        cartService.addGoodToCart(person,cartService.findById(id).getGood());
        return "redirect:/cart";
    }
}
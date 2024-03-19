package com.martynov.spring.controllers;

import com.martynov.spring.entity.Cart;
import com.martynov.spring.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("")
    public String index(Model model) {;
        List<Cart> cartList = cartService.getAllCartsForPerson();
        model.addAttribute("cartList",cartList);
        return "cart/index";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int cartId) {
        cartService.deleteOneCart(cartId);
        return "redirect:/cart";
    }

    @PostMapping("/{id}")
    public String add(@PathVariable("id") int goodId) {
        cartService.addGoodToCart(goodId);
        return "redirect:/cart";
    }
}
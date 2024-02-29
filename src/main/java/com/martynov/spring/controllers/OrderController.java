package com.martynov.spring.controllers;

import com.martynov.spring.entity.Person;
import com.martynov.spring.service.OrderService;
import com.martynov.spring.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final PersonService personService;

    @PostMapping("/{id}")
    public String makeOrder(@PathVariable("id") int cartId,
                            @RequestParam(
                                    value = "address",
                                    required = false,
                                    defaultValue = "online") String address) {
        orderService.addOneOrder(cartId, address, "TODO");
        return "redirect:/cart";
    }

    @GetMapping("/all")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "order/index";
    }

    @PostMapping()
    public String makeAllOrder(@RequestParam(
            value = "address",
            required = false,
            defaultValue = "online") String address) {
        Person person = personService.getCurrentPerson();
        orderService.addAllOrders(person, address, "TODO");
        return "redirect:/cart";
    }
}

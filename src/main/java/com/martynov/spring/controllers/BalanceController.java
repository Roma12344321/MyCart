package com.martynov.spring.controllers;

import com.martynov.spring.entity.Balance;
import com.martynov.spring.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/balance")
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("")
    public String index(Model model, @ModelAttribute("formBalance") Balance formBalance) {
        Balance balance = balanceService.getCurrentBalance();
        model.addAttribute("balance",balance);
        return "balance/index";
    }
    @PostMapping("")
    public String addSum(@ModelAttribute("balance") Balance balance) {
        System.out.println(balance.getSum());
        balanceService.addSum(balance);
        return "redirect:/balance";
    }
}

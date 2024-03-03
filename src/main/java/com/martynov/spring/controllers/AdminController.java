package com.martynov.spring.controllers;

import com.martynov.spring.entity.Category;
import com.martynov.spring.entity.Good;
import com.martynov.spring.service.CategoryService;
import com.martynov.spring.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;
    private final GoodService goodService;

    @GetMapping
    public String index() {
        return "admin/index";
    }

    @GetMapping("/good")
    public String showAllGood(Model model) {
        model.addAttribute("goods",goodService.findAllGood());
        return "admin/goods";
    }

    @GetMapping("/good/{id}")
    public String showGoodById(@PathVariable("id") int id, Model model) {
        model.addAttribute("good", goodService.findById(id));
        return "admin/show_one";
    }

    @DeleteMapping("/good/{id}")
    public String deleteGood(@PathVariable("id") int id) {
        goodService.deleteById(id);
        return "redirect:/admin/good";
    }

    @PatchMapping("/good/{id}")
    public String patchGood(@PathVariable("id") int id, @ModelAttribute("good") Good good, @RequestParam("cat_id") int catId) {
        goodService.patchGood(id,good,catId);
        return "redirect:/admin/good";
    }

    @GetMapping("/good/new")
    public String addGood(@ModelAttribute("good") Good good, @ModelAttribute("category") Category category, Model model) {
        model.addAttribute("cats", categoryService.findAll());
        return "admin/new";
    }

    @PostMapping("/good/new")
    public String create(@ModelAttribute Good good, @RequestParam("id") int id, @RequestParam("image") MultipartFile imageFile) {
        goodService.create(good,id,imageFile);
        return "redirect:/admin/good";
    }
}

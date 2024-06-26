package com.martynov.spring.controllers;

import com.martynov.spring.entity.Comment;
import com.martynov.spring.entity.Good;
import com.martynov.spring.entity.Person;
import com.martynov.spring.service.CartService;
import com.martynov.spring.service.GoodService;
import com.martynov.spring.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
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
        List<Good> goodList = goodService.findAllGoodWithCommentAndLikeCount(0, 100);
        model.addAttribute("success", success);
        model.addAttribute("goods", goodList);
        return "good/index";
    }

    @GetMapping("/{id}")
    public String showGoodById(@PathVariable("id") int id, Model model, @ModelAttribute("comment") Comment comment,
                               @RequestParam(value = "failure",required = false,defaultValue = "false") Boolean commentFailure) {
        model.addAttribute("failure",commentFailure);
        model.addAttribute("good", goodService.findByIdWithComments(id));
        try {
            model.addAttribute("person", personService.getCurrentPerson());
        } catch (Exception e) {
            model.addAttribute("person", null);
        }
        return "good/show_one";
    }

    @PreAuthorize("!hasRole('ROLE_WORKER')")
    @PostMapping("/{id}")
    public String addToCart(@PathVariable("id") int goodId) {
        cartService.addGoodToCart(goodId);
        return "redirect:/good?success=true";
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getGoodImage(@PathVariable int id) {
        var resource = goodService.getGoodImage(id);
         return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}

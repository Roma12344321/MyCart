package com.martynov.spring.controllers;

import com.martynov.spring.service.LikeService;
import com.martynov.spring.util.LikeAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{id}")
    public String makeLike(@PathVariable("id") int goodId) {
        try {
            likeService.makeLike(goodId);
        } catch (LikeAlreadyExistException e) {
            System.out.println("Лайк уже поставлен");
        }
        return "redirect:/good/{id}";
    }
}

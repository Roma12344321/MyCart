package com.martynov.spring.controllers;

import com.martynov.spring.entity.Comment;
import com.martynov.spring.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    @PostMapping("/{id}")
    public String create(@PathVariable("id") int goodId, @ModelAttribute("comment") @Valid Comment comment, BindingResult result) {
        comment.setId(0);
        if (result.hasErrors())
            return "good/show_one";
        commentService.createComment(goodId, comment.getText(), comment.getStar());
        return "redirect:/good/{id}";
    }
}

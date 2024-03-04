package com.martynov.spring.service;

import com.martynov.spring.entity.Comment;
import com.martynov.spring.entity.Good;
import com.martynov.spring.entity.Person;
import com.martynov.spring.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PersonService personService;
    private final GoodService goodService;

    @Transactional
    public void createComment(int goodId, String text, int star) {
        Person person = personService.getCurrentPerson();
        Good good = goodService.findById(goodId);
        Comment comment = new Comment(text, star, person, good);
        comment.setDate(new Date());
        commentRepository.save(comment);
    }
    @Transactional
    public List<Comment> getAllCommentForGood(Good good) {
        return commentRepository.findByGood(good);
    }
}

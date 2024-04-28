package com.martynov.spring.service;

import com.martynov.spring.entity.Comment;
import com.martynov.spring.entity.Good;
import com.martynov.spring.entity.Person;
import com.martynov.spring.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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
    @CacheEvict(value = {"good_by_id_with_comment", "good_by_id", "goods"}, allEntries = true)
    public void createComment(int goodId, String text, int star) {
        Person person = personService.getCurrentPerson();
        Good good = goodService.findByIdWithComments(goodId);
        Comment comment = new Comment(text, star, person, good);
        comment.setDate(new Date());
        commentRepository.save(comment);
    }

    @Transactional
    @CacheEvict(value = {"good_by_id_with_comment", "good_by_id", "goods"}, allEntries = true)
    public int deleteCommentAndReturnGoodId(int commentId) {
        Person person = personService.getCurrentPerson();
        Comment comment = commentRepository.findById(commentId).orElseThrow(RuntimeException::new);
        if (comment.getPerson().getUsername().equals(person.getUsername()) || person.getRole().equals("ROLE_ADMIN")) {
            commentRepository.delete(comment);
        } else {
            throw new RuntimeException();
        }
        return comment.getGood().getId();
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllCommentForGood(Good good) {
        return commentRepository.findByGood(good);
    }
}

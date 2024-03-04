package com.martynov.spring.repositories;

import com.martynov.spring.entity.Comment;
import com.martynov.spring.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByGood(Good good);
}

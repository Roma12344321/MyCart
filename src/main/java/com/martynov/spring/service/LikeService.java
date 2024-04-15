package com.martynov.spring.service;

import com.martynov.spring.entity.Good;
import com.martynov.spring.entity.Like;
import com.martynov.spring.entity.Person;
import com.martynov.spring.repositories.LikeRepository;
import com.martynov.spring.util.LikeAlreadyExistException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final EntityManager entityManager;
    private final PersonService personService;
    private final GoodService goodService;

    @Transactional(readOnly = true)
    public Like findByPersonAndGood(Person person, Good good) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT l FROM Like l WHERE l.person.id=:personId AND l.good.id=:goodId", Like.class)
                .setParameter("personId", person.getId())
                .setParameter("goodId", good.getId())
                .getSingleResultOrNull();
    }

    @Transactional
    @CacheEvict(value = {"good_by_id_with_comment", "good_by_id", "goods"}, allEntries = true)
    public void makeLike(int goodId) {
        Person person = personService.getCurrentPerson();
        Good good = goodService.findByIdWithComments(goodId);
        Like likeFromDb = findByPersonAndGood(person, good);
        if (likeFromDb == null) {
            Like like = new Like(person, good);
            likeRepository.save(like);
        } else {
            throw new LikeAlreadyExistException();
        }
    }
}

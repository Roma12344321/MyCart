package com.martynov.spring.service;

import com.martynov.spring.entity.Cart;
import com.martynov.spring.entity.Good;
import com.martynov.spring.entity.Person;
import com.martynov.spring.repositories.CartRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Scanner;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final EntityManager entityManager;
    private final PersonService personService;
    private final GoodService goodService;
    private static final int INITIAL_AMOUNT = 1;

    @Transactional
    public void addGoodToCart(int goodId) {
        Person person = personService.getCurrentPerson();
        if (person == null) {
            throw new RuntimeException();
        }
        Good good = goodService.findByIdWithOutComments(goodId);
        Session session = entityManager.unwrap(Session.class);
        Cart cartFromDb = session.createQuery(
                        "SELECT c FROM Cart c WHERE c.person.id=:personId AND c.good.id=:goodId",
                        Cart.class)
                .setParameter("personId", person.getId())
                .setParameter("goodId", good.getId())
                .getSingleResultOrNull();
        if (cartFromDb == null) {
            Cart cart = new Cart();
            cart.setPerson(person);
            cart.setGood(good);
            cart.setAmount(INITIAL_AMOUNT);
            cartRepository.save(cart);
        } else {
            int amount = cartFromDb.getAmount();
            cartFromDb.setAmount(++amount);
            cartRepository.save(cartFromDb);
        }
    }


    @Transactional(readOnly = true)
    public List<Cart> getAllCartsForPerson() {
        Person person = personService.getCurrentPerson();
        List<Cart> cartList = cartRepository.findByPerson(person);
        for (Cart cart : cartList) {
            cart.setGood(goodService.findByIdWithOutComments(cart.getGood().getId()));
        }
        return cartList;
    }

    @Transactional
    public void deleteOneCart(int id) {
        Cart cart = cartRepository.findById(id).orElseThrow(RuntimeException::new);
        int amount = cart.getAmount() - 1;
        if (amount <= 0) {
            cartRepository.delete(cart);
        } else {
            cart.setAmount(amount);
        }
    }

    @Transactional(readOnly = true)
    public Cart findById(int id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteCart(Cart cart) {
        cartRepository.delete(cart);
    }
}
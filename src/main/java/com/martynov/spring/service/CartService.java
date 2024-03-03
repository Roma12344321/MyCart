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


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final EntityManager entityManager;
    private static final int INITIAL_AMOUNT = 1;

    @Transactional
    public void addGoodToCart(Person person, Good good) {
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
    public List<Cart> getAllCartsForPerson(Person person) {
        return cartRepository.findByPerson(person);
    }

    @Transactional
    public void deleteOneCart(int id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        int amount = cart.getAmount() - 1;
        if (amount <= 0) {
            cartRepository.delete(cart);
        } else {
            cart.setAmount(amount);
        }
    }

    @Transactional
    public Cart findById(int id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteCart(Cart cart) {
        cartRepository.delete(cart);
    }
}
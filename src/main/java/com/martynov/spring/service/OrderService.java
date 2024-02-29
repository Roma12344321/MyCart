package com.martynov.spring.service;

import com.martynov.spring.entity.Cart;
import com.martynov.spring.entity.Order;
import com.martynov.spring.entity.Person;
import com.martynov.spring.repositories.OrderRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final EntityManager entityManager;

    private static final int INITIAL_AMOUNT = 1;
    private static final int EMPTY_COUNT = 0;

    @Transactional
    public void addOneOrder(int cartId, String address, String status) {
        Cart cart = cartService.findById(cartId);
        int cartAmountAfter = cart.getAmount() - 1;
        if (cartAmountAfter == EMPTY_COUNT) {
            cartService.deleteCart(cart);
        } else {
            cart.setAmount(cartAmountAfter);
        }
        Session session = entityManager.unwrap(Session.class);
        Order orderFromDb = getOrderFromDb(session, cart);
        if (orderFromDb == null) {
            Order order = new Order(cart.getPerson(), cart.getGood(), INITIAL_AMOUNT, address, status, new Date());
            orderRepository.save(order);
        } else {
            int amount = orderFromDb.getAmount() + 1;
            orderFromDb.setAmount(amount);
        }
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional
    public void addAllOrders(Person person, String address, String status) {
        List<Cart> carts = cartService.getAllCartsForPerson(person);
        Session session = entityManager.unwrap(Session.class);
        for (Cart cart : carts) {
            Order orderFromDb = getOrderFromDb(session, cart);
            if (orderFromDb == null) {
                Order order = new Order(cart.getPerson(), cart.getGood(), cart.getAmount(), address, status, new Date());
                orderRepository.save(order);
            } else {
                int amount = orderFromDb.getAmount() + cart.getAmount();
                orderFromDb.setAmount(amount);
            }
            cartService.deleteCart(cart);
        }
    }

    private Order getOrderFromDb(Session session, Cart cart) {
        return session.createQuery(
                        "SELECT o FROM Order o WHERE o.person.id=:personId AND o.good.id=:goodId",
                        Order.class)
                .setParameter("personId", cart.getPerson().getId())
                .setParameter("goodId", cart.getGood().getId())
                .getSingleResultOrNull();
    }
}

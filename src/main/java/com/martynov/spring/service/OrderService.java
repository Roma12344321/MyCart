package com.martynov.spring.service;

import com.martynov.spring.entity.*;
import com.martynov.spring.repositories.OrderRepository;
import com.martynov.spring.util.NotEnoughMoneyException;
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
    private final BalanceService balanceService;
    private static final int EMPTY_COUNT = 0;

    @Transactional
    public void addOrder(int cartId, String address, String status, int count) {
        Cart cart = cartService.findById(cartId);
        Person person = cart.getPerson();
        Good good = cart.getGood();
        Balance balance = balanceService.findByPerson(person);
        checkBalance(balance, good.getPrice());
        makeBalance(balance, good.getPrice());
        balanceService.save(balance);
        Session session = entityManager.unwrap(Session.class);
        Order orderFromDb = getOrderFromDb(session, cart);
        if (orderFromDb == null) {
            Order order = new Order(person, good, count, address, status, new Date());
            orderRepository.save(order);
        } else {
            int amount = orderFromDb.getAmount() + count;
            orderFromDb.setAmount(amount);
        }
        int cartAmountAfter = cart.getAmount() - count;
        if (cartAmountAfter == EMPTY_COUNT) {
            cartService.deleteCart(cart);
        } else {
            cart.setAmount(cartAmountAfter);
        }
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    private void makeBalance(Balance balance, int price) {
        int sumBefore = balance.getSum();
        balance.setSum(sumBefore - price);
    }

    private void checkBalance(Balance balance, int price) {
        if (balance.getSum() < price) {
            throw new NotEnoughMoneyException();
        }
    }

    @Transactional
    public void addAllOrders(Person person, String address, String status) {
        List<Cart> carts = cartService.getAllCartsForPerson();
        Session session = entityManager.unwrap(Session.class);
        Balance balance = balanceService.findByPerson(person);
        int price = 0;
        for (Cart cart : carts) {
            price += (cart.getAmount() * cart.getGood().getPrice());
        }
        checkBalance(balance, price);
        makeBalance(balance, price);
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

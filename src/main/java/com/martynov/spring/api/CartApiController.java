package com.martynov.spring.api;

import com.martynov.spring.dto.CartDto;
import com.martynov.spring.entity.Cart;
import com.martynov.spring.mapper.Mapper;
import com.martynov.spring.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartApiController {

    private final CartService cartService;
    private final Mapper<Cart,CartDto> cartMapper;

    @GetMapping()
    public List<CartDto> index() {
        List<Cart> cartList = cartService.getAllCartsForPerson();
        return cartMapper.mapEntityListToDtoList(cartList, CartDto.class);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable("id") int cartId) {
        cartService.deleteOneCart(cartId);
        return Map.of("success", "success");
    }

    @PostMapping("/{id}")
    public Map<String, String> add(@PathVariable("id") int goodId) {
        cartService.addGoodToCart(goodId);
        return Map.of("success", "success");
    }
}

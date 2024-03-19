package com.martynov.spring.api;

import com.martynov.spring.dto.CartDto;
import com.martynov.spring.entity.Cart;
import com.martynov.spring.entity.Person;
import com.martynov.spring.mapper.CartMapper;
import com.martynov.spring.service.CartService;
import com.martynov.spring.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartApiController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @GetMapping()
    public List<CartDto> index() {
        List<Cart> cartList = cartService.getAllCartsForPerson();
        return cartMapper.mapCartListToCartDtoList(cartList);
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

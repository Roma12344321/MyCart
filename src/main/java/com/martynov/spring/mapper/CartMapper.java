package com.martynov.spring.mapper;

import com.martynov.spring.dto.CartDto;
import com.martynov.spring.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final ModelMapper mapper;
    public CartDto mapCartToCartDto(Cart cart) {
        return mapper.map(cart, CartDto.class);
    }
    public List<CartDto> mapCartListToCartDtoList(List<Cart> cartList) {
        return cartList.stream().map(this::mapCartToCartDto).toList();
    }
}

package com.martynov.spring.mapper;

import com.martynov.spring.dto.GoodDto;
import com.martynov.spring.entity.Good;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoodMapper {
    private final ModelMapper modelMapper;

    public List<GoodDto> mapGoodListDoGoodDtoList(List<Good> goodList) {
        return goodList.stream().map(this::mapGoodToGoodDto).collect(Collectors.toList());
    }

    public GoodDto mapGoodToGoodDto(Good good) {
        return modelMapper.map(good,GoodDto.class);
    }
}

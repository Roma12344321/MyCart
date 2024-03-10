package com.martynov.spring.api;


import com.martynov.spring.dto.GoodDto;
import com.martynov.spring.entity.Good;
import com.martynov.spring.mapper.GoodMapper;
import com.martynov.spring.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/good")
@RequiredArgsConstructor
public class GoodApiController {

    private final GoodService goodService;
    private final GoodMapper goodMapper;
    @GetMapping()
    public List<GoodDto> showAllGood() {
        List<Good> goodList = goodService.findAllGood();
        return goodMapper.mapGoodListDoGoodDtoList(goodList);
    }
    @GetMapping("/{id}")
    public GoodDto showById(@PathVariable("id") int id) {
        Good good = goodService.findByIdWithOutComments(id);
        return goodMapper.mapGoodToGoodDto(good);
    }
}

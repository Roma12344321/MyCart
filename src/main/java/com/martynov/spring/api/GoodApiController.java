package com.martynov.spring.api;


import com.martynov.spring.dto.GoodDto;
import com.martynov.spring.entity.Good;
import com.martynov.spring.mapper.Mapper;
import com.martynov.spring.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/good")
@RequiredArgsConstructor
public class GoodApiController {

    private final GoodService goodService;
    private final Mapper<Good, GoodDto> mapper;

    @GetMapping()
    public List<GoodDto> showAllGood(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "count", required = false, defaultValue = "10") int count) throws InterruptedException {
        List<Good> goodList = goodService.findAllGoodWithCommentAndLikeCount(page, count);
        return mapper.mapEntityListToDtoList(goodList, GoodDto.class);
    }

    @GetMapping("/{id}")
    public GoodDto showById(@PathVariable("id") int id) {
        try {
            Good good = goodService.findByIdWithOutComments(id);
            return mapper.mapEntityToDto(good, GoodDto.class);
        } catch (RuntimeException e) {
            return null;
        }
    }
}

package com.martynov.spring.api;


import com.martynov.spring.dto.GoodDto;
import com.martynov.spring.entity.Good;
import com.martynov.spring.mapper.GoodMapper;
import com.martynov.spring.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/good")
@RequiredArgsConstructor
public class GoodApiController {

    private final GoodService goodService;
    private final GoodMapper goodMapper;

    @GetMapping()
    public List<GoodDto> showAllGood(@RequestParam(value = "page",required = false,defaultValue = "0") int page,
                                     @RequestParam(value = "count",required = false,defaultValue = "10") int count) {
        List<Good> goodList = goodService.findAllGoodWithCommentAndLikeCount(page, count);
        return goodMapper.mapGoodListDoGoodDtoList(goodList);
    }

    @GetMapping("/{id}")
    public GoodDto showById(@PathVariable("id") int id) {
        try {
            Good good = goodService.findByIdWithOutComments(id);
            return goodMapper.mapGoodToGoodDto(good);
        } catch (RuntimeException e) {
            return null;
        }
    }
}

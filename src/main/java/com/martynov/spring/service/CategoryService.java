package com.martynov.spring.service;

import com.martynov.spring.entity.Category;
import com.martynov.spring.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    @Transactional
    public Category findById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }
}

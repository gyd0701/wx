package com.xfu.service.impl;

import com.xfu.entity.Category;
import com.xfu.mapper.ICategoryMapper;
import com.xfu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    ICategoryMapper mapper;

    @Override
    public List<Category> findAll() {

        return mapper.findAllCategory();
    }
}

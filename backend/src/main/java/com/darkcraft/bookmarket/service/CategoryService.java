package com.darkcraft.bookmarket.service;

import com.darkcraft.bookmarket.dao.CategoryDAO;
import com.darkcraft.bookmarket.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryDAO categoryDAO;

    public List<Category> list() {
        return categoryDAO.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Category get(int id) {
        Category c= categoryDAO.findById(id).orElse(null);
        return c;
    }
}

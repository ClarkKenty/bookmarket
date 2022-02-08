package com.darkcraft.bookmarket.dao;

import com.darkcraft.bookmarket.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryDAO extends JpaRepository<Category, Integer> {

}

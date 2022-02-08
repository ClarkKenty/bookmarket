package com.darkcraft.bookmarket.dao;

import com.darkcraft.bookmarket.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDAO extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    @Override
    List<User> findAll();

    User getByUsernameAndPassword(String username, String password);
}

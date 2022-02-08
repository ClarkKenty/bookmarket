package com.darkcraft.bookmarket.service;

import com.darkcraft.bookmarket.dao.UserDAO;
import com.darkcraft.bookmarket.pojo.Book;
import com.darkcraft.bookmarket.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public boolean isExist(String username) {
        User user = getByName(username);
        return null!=user;
    }

    public User getByName(String username) {
        return userDAO.findByUsername(username);
    }

    public User get(String username, String password){
        return userDAO.getByUsernameAndPassword(username, password);
    }
    public List<User> findAll(){
        return userDAO.findAll();
    }
    public void add(User user) {
        userDAO.save(user);
    }
    public void addOrUpdate(User user) {
        userDAO.save(user);
    }
    public void deleteById(int id){
        userDAO.deleteById(id);
    }
}

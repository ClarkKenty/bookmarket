package com.darkcraft.bookmarket.service;

import com.darkcraft.bookmarket.dao.AssociationDao;
import com.darkcraft.bookmarket.pojo.Association;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociationService {
    @Autowired
    AssociationDao associationDao;

    public List<Association> findAll() {
        return associationDao.findAll();
    }
}

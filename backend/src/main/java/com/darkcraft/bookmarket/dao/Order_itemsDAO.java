package com.darkcraft.bookmarket.dao;

import com.darkcraft.bookmarket.pojo.Order_items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Order_itemsDAO extends JpaRepository<Order_items, Integer> {
    List<Order_items> findAllByOid(int oid);
}

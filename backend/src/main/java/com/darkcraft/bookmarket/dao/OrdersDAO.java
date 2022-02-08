package com.darkcraft.bookmarket.dao;

import com.darkcraft.bookmarket.bean.DailySold;
import com.darkcraft.bookmarket.pojo.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersDAO extends JpaRepository<Orders, Integer> {
    List<Orders> findAllByUid(int uid);
    @Query(value = "SELECT SUM(order_items.count) AS count  ,date_format(order_time, '%Y-%m-%d') as date FROM order_items,orders WHERE oid = orders.id GROUP BY date_format(order_time, '%Y-%m-%d') ORDER BY date_format(order_time, '%Y-%m-%d') ASC;", nativeQuery = true)
    List<DailySold> getDailSold();
}

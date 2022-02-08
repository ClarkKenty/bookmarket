package com.darkcraft.bookmarket.dao;

import com.darkcraft.bookmarket.bean.DailySold;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface dailyDAO extends CrudRepository<DailySold, Long> {
    @Query(value = "SELECT SUM(order_items.count) AS count  ,date_format(order_time, '%Y-%m-%d') as date FROM order_items,orders WHERE oid = orders.id GROUP BY date_format(order_time, '%Y-%m-%d') ORDER BY date_format(order_time, '%Y-%m-%d') ASC;", nativeQuery = true)
    List<DailySold> findSurveyCount();
}

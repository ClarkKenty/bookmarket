package com.darkcraft.bookmarket.service;

import com.darkcraft.bookmarket.dao.OrdersDAO;
import com.darkcraft.bookmarket.bean.DailySold;
import com.darkcraft.bookmarket.pojo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {
    @Autowired
    OrdersDAO ordersDAO;
    public List<Orders> listByUid(int uid){return ordersDAO.findAllByUid(uid);}
    public List<Orders> list() {
        return ordersDAO.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
    public Orders addOrder(Orders order){
        return ordersDAO.save(order);
    }
    public List<DailySold> getDailSold(){
        return ordersDAO.getDailSold();
    }
}

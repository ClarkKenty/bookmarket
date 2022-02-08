package com.darkcraft.bookmarket.service;

import com.darkcraft.bookmarket.dao.Order_itemsDAO;
import com.darkcraft.bookmarket.pojo.Order_items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Order_itemsService {
    @Autowired
    Order_itemsDAO order_itemsDAO;

    public void saveAll(List<Order_items> order_items){
        order_itemsDAO.saveAll(order_items);
    }
    public List<Order_items> listByOid(int oid){return order_itemsDAO.findAllByOid(oid);}
    public long countAllById(){
        return order_itemsDAO.count();
    }
}

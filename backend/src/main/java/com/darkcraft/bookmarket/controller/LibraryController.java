package com.darkcraft.bookmarket.controller;

import com.darkcraft.bookmarket.bean.CategoryCmp;
import com.darkcraft.bookmarket.bean.DailySold;
import com.darkcraft.bookmarket.bean.Tit;
import com.darkcraft.bookmarket.pojo.*;
import com.darkcraft.bookmarket.result.Result;
import com.darkcraft.bookmarket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
public class LibraryController {
    @Autowired
    BookService bookService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    Order_itemsService order_itemsService;

    @Autowired
    UserService userService;
    @Autowired
    AssociationService associationService;

    @CrossOrigin
    @GetMapping("/api/search")
    public List<Book> searchResult(@RequestParam("keywords") String keywords) {
        // 关键词为空时查询出所有书籍
        if ("".equals(keywords)) {
            return bookService.list();
        } else {
            return bookService.Search(keywords);
        }
    }

    @CrossOrigin
    @GetMapping("/api/getIdByUserName")
    public int getIdByUserName(@RequestParam("username") String username) {
        return userService.getByName(username).getId();
    }

    @CrossOrigin
    @PostMapping("/api/check")
    public Result CheckOut(@RequestBody OrdersAndOrderItems ordersAndOrderItems) {
        try {
            Orders savedOrders = ordersService.addOrder(ordersAndOrderItems.getOrders());
            List<Order_items> order_items = ordersAndOrderItems.getOrder_items();
            for (int i = 0; i < order_items.size(); i++) {
                Order_items item = order_items.get(i);
                item.setOid(savedOrders.getId());
                order_items.set(i, item);
            }
            order_itemsService.saveAll(order_items);
            bookService.updateBookCount();
//            order_itemsService.addOrderItems(order_items);
            return new Result(200);
        } catch (Exception e) {
            return new Result(400);
        }
    }

    @CrossOrigin
    @GetMapping("/api/books")
    public List<Book> list() throws Exception {
        return bookService.list();
    }

    @CrossOrigin
    @GetMapping("/api/bookbyid")
    public Book bookById(@RequestParam("id") int id) {
        return bookService.findAllById(id);
    }

    @CrossOrigin
    @GetMapping("/api/orders")
    public List<Orders> getOrders(@RequestParam("uid") int uid) {
        return ordersService.listByUid(uid);
    }

    @CrossOrigin
    @GetMapping("/api/daily")
    public List<DailySold> getDaily() {
        return ordersService.getDailSold();
    }

    @CrossOrigin
    @GetMapping("/api/tit")
    public List<Tit> getTit() {
        return bookService.getTit();
    }

    @CrossOrigin
    @GetMapping("/api/ctyCompare")
    public List<CategoryCmp> getCtyCompare() {
        return bookService.getCategoryCmp();
    }

    @CrossOrigin
    @GetMapping("/api/orderItems")
    public List<Order_items> getOrderItems(@RequestParam("oid") int oid) {
        return order_itemsService.listByOid(oid);
    }

    @CrossOrigin
    @PostMapping("/api/books")
    public Book addOrUpdate(@RequestBody Book book) throws Exception {
        bookService.addOrUpdate(book);
        return book;
    }

    @CrossOrigin
    @PostMapping("/api/delete")
    public void delete(@RequestBody Book book) throws Exception {
        bookService.deleteById(book.getId());
    }

    @CrossOrigin
    @GetMapping("/api/itemcount")
    public long itemCount() {
        return order_itemsService.countAllById();
    }

    @CrossOrigin
    @GetMapping("/api/getAssociation")
    public List<Association> getAssociation() {
        return associationService.findAll();
    }

    @CrossOrigin
    @GetMapping("/api/categories/{cid}/books")
    public List<Book> listByCategory(@PathVariable("cid") int cid) throws Exception {
        if (8 == cid) {
            return bookService.listHot();
        } else if (9 == cid) {
            return bookService.listNew();
        } else if (0 == cid) {
            return list();
        } else if (cid <= 6 && cid > 0) {
            return bookService.listByCategory(cid);
        } else {
            return bookService.findFavor(cid);
        }
    }

    @CrossOrigin
    @GetMapping("/api/apriori")
    public boolean apriori(float arg1, float arg2) {
        try {
            File file = new File("/tmp/1.csv");
            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        bookService.genCSV();
        String[] args = new String[]{"/root/.pyenv/shims/python3.8", "/tmp/apriori.py", "-f", "/tmp/1.csv", "-s", Float.toString(arg1), "-c", Float.toString(arg2)};
        try {
            Process proc = Runtime.getRuntime().exec(args);// 执行py文件
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}

class OrdersAndOrderItems {
    Orders orders;
    List<Order_items> order_items;

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public List<Order_items> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(List<Order_items> order_items) {
        this.order_items = order_items;
    }
}

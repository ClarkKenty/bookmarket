package com.darkcraft.bookmarket.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Order_items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @ManyToOne(targetEntity = Orders.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "oid", insertable = false, updatable = false)
    private Orders orders;

    public Book getBook() {
        return book;
    }

    @ManyToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "bid", insertable = false, updatable = false)
    private Book book;
    int count;

    public int getbPrice() {
        return bPrice;
    }

    public void setbPrice(int bPrice) {
        this.bPrice = bPrice;
    }

    int bPrice;

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbAuthor() {
        return bAuthor;
    }

    public void setbAuthor(String bAuthor) {
        this.bAuthor = bAuthor;
    }

    String bName;
    String bAuthor;

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    int oid;
    int bid;

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }


    public void setBook(Book book) {
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}

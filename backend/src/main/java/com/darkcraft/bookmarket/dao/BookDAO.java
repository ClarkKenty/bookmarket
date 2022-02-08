package com.darkcraft.bookmarket.dao;

import com.darkcraft.bookmarket.bean.CategoryCmp;
import com.darkcraft.bookmarket.bean.DailySold;
import com.darkcraft.bookmarket.bean.Tit;
import com.darkcraft.bookmarket.pojo.Book;
import com.darkcraft.bookmarket.pojo.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookDAO extends JpaRepository<Book, Integer> {
    List<Book> findAllByCategory(Category category);
    Book findById(int id);

    List<Book> findAllByTitleLikeOrAuthorLike(String keyword1, String keyword2);

    List<Book> findTop16ByOrderBySoldDesc();

    List<Book> findTop16ByOrderByDateDesc();

    @Query(value = "SELECT * FROM book WHERE id in (SELECT DISTINCT item2 FROM `order_items`, `orders`,`association` WHERE uid = ?1 AND orders.id = order_items.oid AND bid = item1);", nativeQuery = true)
    List<Book> findFavor(int uid);

    @Query(value = "SELECT category.name, SUM(sold) AS count FROM `book`,category WHERE cid = category.id GROUP BY cid;", nativeQuery = true)
    List<CategoryCmp> getCategoryCmp();
    @Query(value = "SELECT (SELECT SUM(sold) FROM book) AS sold,  (SELECT COUNT(*) FROM user) as user,(SELECT COUNT(*) FROM book ) as book;", nativeQuery = true)
    List<Tit> getTit();
}

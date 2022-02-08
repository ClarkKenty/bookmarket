package com.darkcraft.bookmarket.service;

import com.darkcraft.bookmarket.bean.CategoryCmp;
import com.darkcraft.bookmarket.bean.Tit;
import com.darkcraft.bookmarket.dao.BookDAO;
import com.darkcraft.bookmarket.pojo.Book;
import com.darkcraft.bookmarket.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookDAO bookDAO;
    @Autowired
    CategoryService categoryService;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Book> Search(String keywords) {
        return bookDAO.findAllByTitleLikeOrAuthorLike('%' + keywords + '%', '%' + keywords + '%');
    }

    public List<Book> list() {
        return bookDAO.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public void addOrUpdate(Book book) {
        bookDAO.save(book);
    }

    public void deleteById(int id) {
        bookDAO.deleteById(id);
    }

    public List<Book> listByCategory(int cid) {
        Category category = categoryService.get(cid);
        return bookDAO.findAllByCategory(category);
    }
    public void updateBookCount(){
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("updateBookCount");
        query.execute();
    }
    public List<Book> listHot(){
        return bookDAO.findTop16ByOrderBySoldDesc();
    }
    public Book findAllById(int id){
        return bookDAO.findById(id);
    }

    public List<Book> listNew(){
        return bookDAO.findTop16ByOrderByDateDesc();
    }
    public void genCSV(){
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("genCSV");
        query.execute();
    }
    public List<Book> findFavor(int uid){
        return bookDAO.findFavor(uid);
    }
    public List<CategoryCmp> getCategoryCmp(){
        return bookDAO.getCategoryCmp();
    }
    public List<Tit> getTit(){
        return bookDAO.getTit();
    }

}

package com.javacodegeeks.snippets.enterprise.hibernate.service;

import com.javacodegeeks.snippets.enterprise.hibernate.dao.AuthorDao;
import com.javacodegeeks.snippets.enterprise.hibernate.dao.BookDao;
import com.javacodegeeks.snippets.enterprise.hibernate.model.Author;
import com.javacodegeeks.snippets.enterprise.hibernate.model.Book;

import java.util.List;

public class BookService {

    private static BookDao bookDao;
    private static AuthorDao authorDao;

    public BookService() {
        bookDao = new BookDao();
        authorDao = new AuthorDao();
    }

    public void persist(Book entity) {
        bookDao.openCurrentSessionwithTransaction();
        bookDao.persist(entity);
        bookDao.closeCurrentSessionwithTransaction();
    }

    public void persist(Author entity) {
        authorDao.openCurrentSessionwithTransaction();
        authorDao.persist(entity);
        authorDao.closeCurrentSessionwithTransaction();
    }

    public void update(Book entity) {
        bookDao.openCurrentSessionwithTransaction();
        bookDao.update(entity);
        bookDao.closeCurrentSessionwithTransaction();
    }

    public Book findById(Long id) {
        bookDao.openCurrentSession();
        Book book = bookDao.findById(id);
        bookDao.closeCurrentSession();
        return book;
    }

    public void delete(Long id) {
        bookDao.openCurrentSessionwithTransaction();
        Book book = bookDao.findById(id);
        bookDao.delete(book);
        bookDao.closeCurrentSessionwithTransaction();
    }

    public List<Book> findAll() {
        bookDao.openCurrentSession();
        List<Book> books = bookDao.findAll();
        bookDao.closeCurrentSession();
        return books;
    }

    public void deleteAll() {
        bookDao.openCurrentSessionwithTransaction();
        bookDao.deleteAll();
        bookDao.closeCurrentSessionwithTransaction();
        authorDao.openCurrentSessionwithTransaction();
        authorDao.deleteAll();
        authorDao.closeCurrentSessionwithTransaction();
    }

    public BookDao bookDao() {
        return bookDao;
    }
}

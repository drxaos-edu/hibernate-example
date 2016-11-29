package com.javacodegeeks.snippets.enterprise.hibernate.dao;

import com.javacodegeeks.snippets.enterprise.hibernate.model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class AuthorDao implements DaoInterface<Author, Long> {

    private Session currentSession;

    private Transaction currentTransaction;

    public AuthorDao() {
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public void persist(Author entity) {
        getCurrentSession().save(entity);
    }

    public void update(Author entity) {
        getCurrentSession().update(entity);
    }

    public Author findById(Long id) {
        Author author = (Author) getCurrentSession().get(Author.class, id);
        return author;
    }

    public void delete(Author entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Author> findAll() {
        List<Author> authors = (List<Author>) getCurrentSession().createQuery("from Author").list();
        return authors;
    }

    public void deleteAll() {
        List<Author> entityList = findAll();
        for (Author entity : entityList) {
            delete(entity);
        }
    }

}

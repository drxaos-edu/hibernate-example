package com.javacodegeeks.snippets.enterprise.hibernate;

import com.javacodegeeks.snippets.enterprise.hibernate.model.Author;
import com.javacodegeeks.snippets.enterprise.hibernate.model.Book;
import com.javacodegeeks.snippets.enterprise.hibernate.service.BookService;

import java.util.List;

public class App {

    public static void main(String[] args) {
        BookService bookService = new BookService();
        Author dostoevsky = new Author("Fyodor", "Dostoevsky");
        Author tolstoy = new Author("Lev", "Tolstoy");
        Author austen = new Author("Jane", "Austen");
        Book book1 = new Book("The Brothers Karamazov", dostoevsky);
        Book book2 = new Book("War and Peace", tolstoy);
        Book book3 = new Book("Pride and Prejudice", austen);
        System.out.println("*** Persist - start ***");
        bookService.persist(dostoevsky);
        bookService.persist(tolstoy);
        bookService.persist(austen);
        bookService.persist(book1);
        bookService.persist(book2);
        bookService.persist(book3);
        List<Book> books1 = bookService.findAll();
        System.out.println("Books Persisted are :");
        for (Book b : books1) {
            System.out.println("-" + b.toString());
        }
        System.out.println("*** Persist - end ***");
        System.out.println("*** Update - start ***");
        book1.setTitle("The Idiot");
        bookService.update(book1);
        System.out.println("Book Updated is =>" + bookService.findById(book1.getId()).toString());
        System.out.println("*** Update - end ***");
        System.out.println("*** Find - start ***");
        Long id1 = book1.getId();
        Book another = bookService.findById(id1);
        System.out.println("Book found with id " + id1 + " is =>" + another.toString());
        System.out.println("*** Find - end ***");
        System.out.println("*** Delete - start ***");
        Long id3 = book3.getId();
        bookService.delete(id3);
        System.out.println("Deleted book with id " + id3 + ".");
        System.out.println("Now all books are " + bookService.findAll().size() + ".");
        System.out.println("*** Delete - end ***");
        System.out.println("*** FindAll - start ***");
        List<Book> books2 = bookService.findAll();
        System.out.println("Books found are :");
        for (Book b : books2) {
            System.out.println("-" + b.toString());
        }
        System.out.println("*** FindAll - end ***");
        System.out.println("*** DeleteAll - start ***");
        bookService.deleteAll();
        System.out.println("Books found are now " + bookService.findAll().size());
        System.out.println("*** DeleteAll - end ***");
        System.exit(0);
    }

}

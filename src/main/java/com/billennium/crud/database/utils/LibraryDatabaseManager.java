package com.billennium.crud.database.utils;

import com.billennium.crud.model.Author;
import com.billennium.crud.model.Book;
import com.billennium.crud.repositories.AuthorRepository;
import com.billennium.crud.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LibraryDatabaseManager {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public void addBookToDatabase(Book book) {
        bookRepository.save(book);
    }

    public void removeBookFromDatabase(Book book) {
        bookRepository.delete(book);
    }

    public Iterable<Book> getAllBooksFromDatabase() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseGet(() -> null);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseGet(() -> null);
    }

}

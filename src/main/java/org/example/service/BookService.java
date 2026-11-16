package org.example.service;

import org.example.entity.Book;
import org.example.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    public List<Book> getBooks(){
        return bookRepo.findAll();
    }

    public Book addBook(Book book){
        return bookRepo.save(book);
    }
}
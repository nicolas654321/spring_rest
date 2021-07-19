package com.wcs.spring_rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcs.spring_rest.entity.Book;
import com.wcs.spring_rest.repository.BookRepository;

@RestController
public class BookController {

    @RequestMapping("/")
    public String index() {
        return "this is index";
    }
    
    @Autowired
    private BookRepository bookRepo;
    
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @GetMapping("/book")
    public List<Book> findBooks(@RequestParam String texte) {
        List<Book> lesBooks = bookRepo.findAll();
        List<Book> lesBooksFound = new ArrayList<Book>();
        for (Book book : lesBooks) {
            if (book.getTitle().contains(texte) || book.getDescription().contains(texte)) {
                lesBooksFound.add(book);
            }
        }
        return lesBooksFound;
    }

    
    @GetMapping("/book/{id}")
    public Book findBookById(@PathVariable String id) {
        Optional<Book> book = bookRepo.findById(convStr(id));
        if (book.isEmpty()) {
            return new Book();
        }
        return book.get();
    }
    
    @PostMapping("/book")
    public void addBook(@RequestParam String title, @RequestParam String author, @RequestParam String description) {
        bookRepo.save(new Book(title, author, description));
    }
    
    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable String id) {
        bookRepo.deleteById(convStr(id));
    }
    
    @PutMapping("/book/{id}")
    public void updateBook(@PathVariable String id, 
            @RequestParam String title, @RequestParam String author, @RequestParam String description) {
        Book book = new Book();
        Optional<Book> oBook = bookRepo.findById(convStr(id));
        if (oBook.isPresent()) {
            book = oBook.get();
        }        
        book.setAuthor(author);
        book.setDescription(description);
        book.setTitle(title);
        bookRepo.save(book);
    }
    
    private Long convStr(String id) {
        return Long.valueOf(id);
    }
    
}

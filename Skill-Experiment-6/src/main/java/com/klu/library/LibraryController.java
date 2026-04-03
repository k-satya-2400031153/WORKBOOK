package com.klu.library;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LibraryController {

    private List<Book> books = new ArrayList<>();

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Online Library System!";
    }

    @GetMapping("/count")
    public int count() {
        return books.size();
    }

    @GetMapping("/price")
    public double price() {
        return 29.99;
    }

    @GetMapping("/books")
    public List<String> getBookTitles() {
        List<String> titles = new ArrayList<>();
        for (Book book : books) {
            titles.add(book.getTitle());
        }
        return titles;
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable String id) {
        for (Book book : books) {
            if (book.getId() != null && book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    @GetMapping("/search")
    public String search(@RequestParam String title) {
        return "Confirmation: You searched for the book titled '" + title + "'.";
    }

    @GetMapping("/author/{name}")
    public String getAuthor(@PathVariable String name) {
        return "Author profile for: " + name;
    }

    @PostMapping("/addbook")
    public String addBook(@RequestBody Book book) {
        books.add(book);
        return "Book added successfully!";
    }

    @GetMapping("/viewbooks")
    public List<Book> viewBooks() {
        return books;
    }
}
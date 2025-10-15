package com.bookstore.tests;

import com.bookstore.clients.BooksClient;
import com.bookstore.models.Book;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

public class BooksTests {

    private BooksClient booksClient;

    @BeforeClass
    public void setup() {
        booksClient = new BooksClient();
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = booksClient.getAllBooks();
        Assert.assertNotNull(books, "Book list should not be null");
        Assert.assertTrue(books.size() >= 0, "Book list should not be negative");
    }

}

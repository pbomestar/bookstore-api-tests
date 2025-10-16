package com.bookstore.tests;

import com.bookstore.clients.BooksClient;
import com.bookstore.models.Book;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;
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

    @Test
    public void testGetBookById() {

        // Some book ID
        int bookId = 35;

        Response response = booksClient.getBookById(bookId);

        Assert.assertEquals(200, response.statusCode(), "Status code should be 200");

        Book book = response.as(Book.class);

        Assert.assertNotNull(book, "Book object should not be null");
        Assert.assertEquals(bookId, book.getId(), "Book ID should match");
        Assert.assertNotNull(book.getTitle(), "Book title should not be null");
        Assert.assertNotNull(book.getPublishDate(), "Book publish date should not be null");

        // Print book
        System.out.println("Book retrieved: " + book);
    }

    @Test
    public void testCreateAndGetBook() {
        int bookId = 41;
        Book newBook = new Book();
        newBook.setId(bookId);
        newBook.setTitle("Example book");
        newBook.setDescription("Example description");
        newBook.setPageCount(150);
        newBook.setExcerpt("Example excerpt");
        newBook.setPublishDate(Instant.now().toString());

        Response createResponse = booksClient.createBook(newBook);
        Assert.assertEquals(createResponse.getStatusCode(), 200, "Book creation should return 200");

        Book createdBook = createResponse.as(Book.class);
        Assert.assertNotNull(createdBook.getId(), "Created book should have an ID");

        Response getResponse = booksClient.getBookById(bookId);
        Assert.assertEquals(getResponse.getStatusCode(), 200, "Fetching created book should return 200");

        Book fetchedBook = getResponse.as(Book.class);
        Assert.assertEquals(fetchedBook.getTitle(), newBook.getTitle(), "Title should match");
        Assert.assertEquals(fetchedBook.getDescription(), newBook.getDescription(), "Description should match");
    }

    @Test
    public void testUpdateBook() {

        int bookId =115;

        Response response = booksClient.getBookById(bookId);

        Assert.assertEquals(200, response.statusCode(), "Status code should be 200");

        Book book = response.as(Book.class);

        // Update some fields
        book.setTitle("Updated Title");
        book.setDescription("Updated Description");

        Response updateResponse = booksClient.updateBook(book.getId(), book);
        Assert.assertEquals(updateResponse.getStatusCode(), 200, "Update should return 200");

        // Fetch updated book
        Response getResponse = booksClient.getBookById(book.getId());
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        Book updatedBook = getResponse.as(Book.class);

        Assert.assertEquals(updatedBook.getTitle(), "Updated Title");
        Assert.assertEquals(updatedBook.getDescription(), "Updated Description");
    }

    @Test
    public void testDeleteBook() {
        // Create book to delete
        int bookId = 125;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Book to Delete");
        book.setDescription("Will be deleted in test");
        book.setPageCount(123);
        book.setExcerpt("Delete me excerpt");
        book.setPublishDate(Instant.now().toString());

        Response createResponse = booksClient.createBook(book);
        Assert.assertEquals(createResponse.getStatusCode(), 200);
        Book createdBook = createResponse.as(Book.class);

        // Delete the book
        Response deleteResponse = booksClient.deleteBook(createdBook.getId());
        Assert.assertTrue(deleteResponse.statusCode() == 200 || deleteResponse.statusCode() == 204,
                "Delete should return 200 or 204");

        // Verify that book is deleted
        Response getResponse = booksClient.getBookById(bookId);

        Assert.assertTrue(getResponse.statusCode() == 404 || getResponse.statusCode() == 400,
                "Deleted book should not be retrievable");
    }
}

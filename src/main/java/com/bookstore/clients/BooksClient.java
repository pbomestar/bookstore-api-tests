package com.bookstore.clients;

import com.bookstore.models.Book;
import com.bookstore.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BooksClient {

    private final String baseUrl = ConfigReader.getProperty("base.url");

    public BooksClient() {
        RestAssured.baseURI = baseUrl;
    }

    public List<Book> getAllBooks() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/Books")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", Book.class);
    }

    public Response getBookById(int id) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/Books/{id}", id);
    }

    public Response createBook(Book book) {
        return given()
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .post("/Books");
    }

    public Response updateBook(int id, Book book) {
        return given()
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .put("/Books/{id}", id);
    }

    public Response deleteBook(int id) {
        return given()
                .when()
                .delete("/Books/{id}", id);
    }
}
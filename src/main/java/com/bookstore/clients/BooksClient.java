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

}
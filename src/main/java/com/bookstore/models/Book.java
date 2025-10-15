package com.bookstore.models;

import lombok.Data;

@Data
public class Book {
    private Integer id;
    private String title;
    private String description;
    private Integer pageCount;
    private String excerpt;
    private String publishDate;
}
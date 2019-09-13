package com.example.library;

import org.json.JSONArray;

public class Book {
    private String imageUrl;
    private JSONArray author;
    private String shortDescription;
    private JSONArray categories;
    private String title;
    private String isbn;
    private String status;
    private int pages;
    private String date;
    private String longDescription;


    public Book(String stringImageUrl,JSONArray jsonArrayAutor, String stringShortDescription,
                JSONArray jsonArrayCategories, String stringTitle, String stringIsbn, String stringStatus,
                int intPages, String stringDate, String stringLongDescription){
        imageUrl = stringImageUrl;
        author = jsonArrayAutor;
        shortDescription = stringShortDescription;
        categories = jsonArrayCategories;
        title = stringTitle;
        isbn = stringIsbn;
        status = stringStatus;
        pages = intPages;
        date = stringDate;
        longDescription = stringLongDescription;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public JSONArray getAuthor() {
        return author;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public JSONArray getCategories() {
        return categories;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getStatus() {
        return status;
    }

    public int getPages() {
        return pages;
    }

    public String getDate() {
        return date;
    }

    public String getLongDescription() {
        return longDescription;
    }

}

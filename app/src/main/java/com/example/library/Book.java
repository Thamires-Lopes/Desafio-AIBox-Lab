package com.example.library;

import org.json.JSONArray;

public class Book {
    private String imageUrl;
    private JSONArray author;
    private String shortDescription;
    private JSONArray categories;
    private String title;

    public Book(String stringImageUrl,JSONArray jsonArrayAutor, String stringShortDescription, JSONArray jsonArrayCategories, String stringTitle){
        imageUrl = stringImageUrl;
        author = jsonArrayAutor;
        shortDescription = stringShortDescription;
        categories = jsonArrayCategories;
        title = stringTitle;

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
}

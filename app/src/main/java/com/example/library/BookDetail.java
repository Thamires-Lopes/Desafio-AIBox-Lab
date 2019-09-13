package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.library.ListOfBooks.EXTRA_AUTHOR;
import static com.example.library.ListOfBooks.EXTRA_DATE;
import static com.example.library.ListOfBooks.EXTRA_DESCRIPTION;
import static com.example.library.ListOfBooks.EXTRA_IMAGE;
import static com.example.library.ListOfBooks.EXTRA_ISBN;
import static com.example.library.ListOfBooks.EXTRA_PAGES;
import static com.example.library.ListOfBooks.EXTRA_STATUS;
import static com.example.library.ListOfBooks.EXTRA_TITLE;

public class BookDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_IMAGE);
        String authors = intent.getStringExtra(EXTRA_AUTHOR);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String isbn = intent.getStringExtra(EXTRA_ISBN);
        String status = intent.getStringExtra(EXTRA_STATUS);
        int pages = intent.getIntExtra(EXTRA_PAGES, 0);
        String date = intent.getStringExtra(EXTRA_DATE);
        String longDescription = intent.getStringExtra(EXTRA_DESCRIPTION);

        String visualDate = date.substring(0, 10);


        ImageView imageView = findViewById(R.id.image_view_book);
        TextView textViewAuthor = findViewById(R.id.text_view_autor_detail);
        TextView textViewTitle = findViewById(R.id.text_view_title_detail);
        TextView textViewIsbn = findViewById(R.id.text_view_isbn_detail);
        TextView textViewStatus = findViewById(R.id.text_view_status);
        TextView textViewPages = findViewById(R.id.text_view_pages);
        TextView textViewDate = findViewById(R.id.text_view_date);
        TextView textViewLongDescription = findViewById(R.id.text_view_long_description);

        Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
        textViewAuthor.setText("Author(s): " + authors);
        textViewTitle.setText(title);
        textViewIsbn.setText("ISBN: " + isbn);
        textViewStatus.setText("Status: " + status);
        textViewPages.setText("Pages: " + pages);
        textViewDate.setText("Published date: " + visualDate);
        textViewLongDescription.setText("Description: " + longDescription);




    }
}

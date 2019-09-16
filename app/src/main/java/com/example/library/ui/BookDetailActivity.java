package com.example.library.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library.R;
import com.example.library.persistencia.BookContract;
import com.example.library.persistencia.DBHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.library.ui.ListOfBooksActivity.EXTRA_AUTHOR;
import static com.example.library.ui.ListOfBooksActivity.EXTRA_DATE;
import static com.example.library.ui.ListOfBooksActivity.EXTRA_DESCRIPTION;
import static com.example.library.ui.ListOfBooksActivity.EXTRA_IMAGE;
import static com.example.library.ui.ListOfBooksActivity.EXTRA_ISBN;
import static com.example.library.ui.ListOfBooksActivity.EXTRA_PAGES;
import static com.example.library.ui.ListOfBooksActivity.EXTRA_STATUS;
import static com.example.library.ui.ListOfBooksActivity.EXTRA_TITLE;

public class BookDetailActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private ArrayList<String> favoritesBooks;
    private Button buttonFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        //DATABASE
        DBHelper dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_IMAGE);
        String authors = intent.getStringExtra(EXTRA_AUTHOR);
        String title = intent.getStringExtra(EXTRA_TITLE);
        final String isbn = intent.getStringExtra(EXTRA_ISBN);
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


        buttonFavorite = findViewById(R.id.switch_favorite);
        favoritesBooks = listIsbn();
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritesBooks = listIsbn();
                int contador = 0;
                if(favoritesBooks.isEmpty() != true){
                    for (String book: favoritesBooks){
                        contador += 1;
                        if (book.equals(isbn)){
                            deleteIsbn(isbn);
                            break;
                        }
                    }
                    if (contador == favoritesBooks.size()){
                        ContentValues values = new ContentValues();
                        values.put(BookContract.BookColumns.TABLE_BOOK_ISBN, isbn);
                        database.insert(BookContract.BookColumns.TABLE_BOOK, null, values);

                    }
                }else{
                    ContentValues values = new ContentValues();
                    values.put(BookContract.BookColumns.TABLE_BOOK_ISBN, isbn);
                    database.insert(BookContract.BookColumns.TABLE_BOOK, null, values);

                }

            }
        });

    }

    public ArrayList<String> listIsbn(){
        String sql = "SELECT " + BookContract.BookColumns.TABLE_BOOK_ISBN + " FROM " + BookContract.BookColumns.TABLE_BOOK + ";";
        ArrayList<String> isbns = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()) {
            isbns.add( cursor.getString(cursor.getColumnIndex(BookContract.BookColumns.TABLE_BOOK_ISBN)));
        }
        cursor.close();
        return isbns;

    }

    public void deleteIsbn(String stringIsbn){
        database.delete(BookContract.BookColumns.TABLE_BOOK, BookContract.BookColumns.TABLE_BOOK_ISBN +
                "=" + stringIsbn, null);

    }
}

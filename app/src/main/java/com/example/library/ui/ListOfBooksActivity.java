package com.example.library.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.library.R;
import com.example.library.dominio.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ListOfBooksActivity extends AppCompatActivity implements BookAdapter.OnItemClickListener {
    public static final String EXTRA_IMAGE = "imageUrl";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_AUTHOR = "autor";
    public static final String EXTRA_ISBN = "isbn";
    public static final String EXTRA_STATUS = "status";
    public static final String EXTRA_PAGES = "pages";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_DESCRIPTION = "longDescription";

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private ArrayList<Book> bookArrayList;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_books);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookArrayList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON(){
        String url = "https://api.myjson.com/bins/h8xi7";

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject book = response.getJSONObject(i);

                        String imageUrl = book.getString("thumbnailUrl");
                        JSONArray author = book.getJSONArray("authors");
                        String shortDescription = book.getString("shortDescription");
                        JSONArray categories = book.getJSONArray("categories");
                        String title = book.getString("title");
                        String isbn = book.getString("isbn");
                        String status = book.getString("status");
                        int pages = book.getInt("pageCount");
                        String longDescription = book.getString("longDescription");

                        JSONObject jsonDateGeneral = book.getJSONObject("publishedDate");
                        String dateString = jsonDateGeneral.getString("$date");


                        bookArrayList.add(new Book(imageUrl, author, shortDescription, categories, title,
                                isbn, status, pages, dateString, longDescription));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                bookAdapter = new BookAdapter(ListOfBooksActivity.this, bookArrayList);
                recyclerView.setAdapter(bookAdapter);
                bookAdapter.setOnClickListener(ListOfBooksActivity.this);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        requestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, BookDetailActivity.class);
        Book clickedBook = bookArrayList.get(position);
        StringBuilder authors = new StringBuilder();


        bookAdapter.buildString(clickedBook.getAuthor(), authors);
        detailIntent.putExtra(EXTRA_IMAGE, clickedBook.getImageUrl());
        detailIntent.putExtra(EXTRA_TITLE, clickedBook.getTitle());
        detailIntent.putExtra(EXTRA_AUTHOR, (CharSequence) authors);
        detailIntent.putExtra(EXTRA_ISBN, clickedBook.getIsbn());
        detailIntent.putExtra(EXTRA_STATUS, clickedBook.getStatus());
        detailIntent.putExtra(EXTRA_PAGES, clickedBook.getPages());
        detailIntent.putExtra(EXTRA_DATE, clickedBook.getDate());
        detailIntent.putExtra(EXTRA_DESCRIPTION, clickedBook.getLongDescription());


        startActivity(detailIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bookAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}

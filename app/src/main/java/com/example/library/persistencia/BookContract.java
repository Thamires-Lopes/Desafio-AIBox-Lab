package com.example.library.persistencia;

import android.provider.BaseColumns;

public class BookContract {
    private BookContract(){
    }

    public static final class BookColumns implements BaseColumns {
        public static final String TABLE_BOOK = "booksIsbn";
        public static final String TABLE_BOOK_ISBN = "isbn";
    }
}

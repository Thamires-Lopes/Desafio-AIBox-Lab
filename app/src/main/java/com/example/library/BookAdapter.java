package com.example.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private Context context;
    private ArrayList<Book> bookArrayList;
    private OnItemClickListener listener;

    public BookAdapter(Context parameterContext, ArrayList<Book> booksList){
        context = parameterContext;
        bookArrayList = booksList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener parameterListener){
        listener = parameterListener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.book_list, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book currentItem = bookArrayList.get(position);
        String imageUrl = currentItem.getImageUrl();
        JSONArray author = currentItem.getAuthor();
        String shortDescription = currentItem.getShortDescription();
        JSONArray categories = currentItem.getCategories();
        String title = currentItem.getTitle();
        StringBuilder authors = new StringBuilder();
        StringBuilder builderCategories = new StringBuilder();


        buildString(author, authors);
        buildString(categories, builderCategories);

        Picasso.get().load(imageUrl).fit().centerInside().into(holder.imageView);
        holder.textViewAuthor.setText("Author(s): " + authors);
        holder.textViewShortDescription.setText("Description: " + shortDescription);
        holder.textViewCategories.setText("Categories: " + builderCategories);
        holder.textViewTitle.setText(title);

    }

    public void buildString(JSONArray jsonArray, StringBuilder stringBuilder) {
        for (int i = 0; i < jsonArray.length(); i++){
            try {
                stringBuilder.append(jsonArray.get(i));
                if (i + 1 != jsonArray.length()){
                    stringBuilder.append(", ");
                }else{
                    stringBuilder.append(".");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textViewAuthor;
        public TextView textViewShortDescription;
        public TextView textViewCategories;
        public TextView textViewTitle;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textViewAuthor = itemView.findViewById(R.id.text_view_autor);
            textViewShortDescription = itemView.findViewById(R.id.text_view_short_description);
            textViewCategories = itemView.findViewById(R.id.text_view_categories);
            textViewTitle = itemView.findViewById(R.id.text_view_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}

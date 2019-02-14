package com.example.mostafa.bookgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookDetails  extends AppCompatActivity {
    private ImageView ivBookCover;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvdescreption;
    private TextView tvsubtitle;
    private ImageView cover;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);
        cover = (ImageView) findViewById(R.id.backdrop);
        ivBookCover = (ImageView) findViewById(R.id.ivBookCover);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvdescreption = (TextView) findViewById(R.id.tvinfo);
        tvsubtitle = (TextView) findViewById(R.id.tvsubTitle);



        // Use the book to populate the data into our views

        Book book = (Book) getIntent().getSerializableExtra(MainActivity.BOOK_DETAIL_KEY);
        loadBook(book);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(book.gettitle());

        loadBackdrop(book.getpic());

    }
    private void loadBackdrop(String pic) {
        final ImageView imageView = findViewById(R.id.backdrop);
        Glide.with(this).load(pic).apply(RequestOptions.centerCropTransform()).into(cover);
    }


    // Populate data for the book
    private void loadBook(Book book) {

        Picasso.get().load(book.getpic()).into(ivBookCover);
        tvAuthor.setText(book.getauthor());
        tvTitle.setText(book.gettitle());
        tvsubtitle.setText(book.getsubtitle());
        tvdescreption.setText(book.getdescreption());

    }
}

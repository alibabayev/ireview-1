package com.sscompany.ireview.Screens;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sscompany.ireview.AddItem;
import com.sscompany.ireview.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShowBookProperties extends AppCompatActivity {

    TextView name;
    TextView author;
    TextView genre;
    TextView note;
    ImageView coverPhoto;
    ImageView photo;
    ArrayList<String> stringDataForIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_book_properties);
        stringDataForIntent = new ArrayList<>();
        name = (TextView) findViewById(R.id.ShowBookName);
        author = (TextView) findViewById(R.id.ShowBookAuthor);
        genre = (TextView) findViewById(R.id.ShowBookGenre);
        note = (TextView) findViewById(R.id.ShowBookNote);

        coverPhoto = (ImageView) findViewById(R.id.ShowBookCoverPhoto);
        photo = (ImageView) findViewById(R.id.ShowBookReadingImage);

        name.setText("BOOK SAMPLE NAME");
        author.setText("BOOK SAMPLE AUTHOR");
        genre.setText("BOOK SAMPLE GENRE");
        note.setText("BOOK SAMPLE NOTE");
    }

    public void editBook(View view) {
        Intent intent = new Intent(getApplicationContext(), AddItem.class);
        stringDataForIntent.add("EDIT");
        stringDataForIntent.add(name.getText().toString());
        intent.putExtra("ADD_OR_EDIT", "EDIT");
        intent.putExtra("BOOKNAME",  name.getText().toString());
        intent.putExtra("BOOKAUTHOR", author.getText().toString());
        intent.putExtra("BOOKGENRE", genre.getText().toString());
        intent.putExtra("BOOKNOTE", note.getText().toString());
        intent.putExtra("BOOKID", "booksampleid");
        startActivity(intent);
    }
}
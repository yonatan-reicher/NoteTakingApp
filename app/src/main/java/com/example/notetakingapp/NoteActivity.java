package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity {
    TextView nameView;
    TextView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        nameView = findViewById(R.id.noteName);
        contentView = findViewById(R.id.noteContent);

        Intent intent = getIntent();
        nameView.setText(intent.getStringExtra("name"));
        nameView.setText(intent.getStringExtra("content"));
    }

    public Intent getBackIntent() {
        Intent i = new Intent();
        i.putExtra("name", nameView.getText());
        i.putExtra("content", contentView.getText());
        return i;
    }

    public void OnClickBackButton(View view) {
        startActivity(getBackIntent());
    }
}
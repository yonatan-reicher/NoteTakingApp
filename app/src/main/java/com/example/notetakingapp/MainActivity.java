package com.example.notetakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText nameBox;
    EditText contentBox;
    Button addButton;
    RecyclerView notesView;

    ArrayList<Note> notes;
    Map<String, Note> noteNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameBox = findViewById(R.id.nameBox);
        contentBox = findViewById(R.id.contentBox);
        addButton = findViewById(R.id.addButton);
        notesView = findViewById(R.id.notesView);
        notes = new ArrayList<Note>();
        noteNames = new HashMap<String, Note>();
    }

    Note makeCurrentNote() {
        return new Note(nameBox.getText().toString(), contentBox.getText().toString());
    }

    void showError(CharSequence msg) {
        // I think the `addButton` parameter doesn't matter much?
        Snackbar.make(this, addButton, msg, Snackbar.LENGTH_SHORT).show();
    }

    public void onAddButtonClicked(View view) {
        String name = nameBox.getText().toString();

        if (name.equals("")) {
            showError(getResources().getText(R.string.noNameError));
            return;
        }

        if (noteNames.containsKey(name)) {
            showError(getResources().getText(R.string.nameExistsError));
            return;
        }

        Note note = makeCurrentNote();
        notes.add(note);
        noteNames.put(note.getName(), note);
        notesView.setLayoutManager(new LinearLayoutManager(this));
        notesView.setAdapter(new NotesAdapter(notes));
    }

    void onNoteClicked(Note note) {

    }

    static class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
        ArrayList<Note> notes;

        public NotesAdapter(ArrayList<Note> notes) {
            this.notes = notes;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Note note = notes.get(position);
            holder.name.setText(note.name);
            holder.content.setText(note.content);
            holder.note = note;
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public final TextView name;
            public final TextView content;
            public Note note;
            final Context context;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                context = itemView.getContext();
                name = itemView.findViewById(R.id.name);
                content = itemView.findViewById(R.id.content);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra("name", note.name);
                intent.putExtra("content", note.content);
                context.startActivity(intent);
            }
        }
    }
}
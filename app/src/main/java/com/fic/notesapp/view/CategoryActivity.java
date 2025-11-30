package com.fic.notesapp.view;

import static android.widget.GridLayout.HORIZONTAL;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.notesapp.R;
import com.fic.notesapp.controller.CategoryController;
import com.fic.notesapp.controller.NoteController;
import com.fic.notesapp.model.note.Note;
import com.fic.notesapp.view.category.CategoryAdapter;
import com.fic.notesapp.view.note.NoteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView rvCategories;
    CategoryAdapter categoryAdapter;
    NoteAdapter noteAdapter;
    RecyclerView rvNotes;
    CategoryController categoryController;
    NoteController noteController;
    FloatingActionButton fabAdd, fabAddNote;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initComponents();
        initRV();
        initListeners();
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshCategories();
        refreshNotes();
    }


    private void initComponents() {

        rvCategories = findViewById(R.id.rvCategories);
        rvNotes = findViewById(R.id.rvNotes);
        categoryController = new CategoryController(this);
        noteController = new NoteController(this);
        fabAdd = findViewById(R.id.fabAdd);
        fabAddNote = findViewById(R.id.fabAddNote);

    }

    private void initRV() {

        LinearLayoutManager layoutManagerCategory = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        LinearLayoutManager layoutManagerNote = new LinearLayoutManager(this);
        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setData(categoryController.getAllCategories());
        noteAdapter = new NoteAdapter(new NoteAdapter.OnNoteActionListener() {
            @Override
            public void onEdit(Note note) {
                updateNoteActivity(note);
            }
        });
        noteAdapter.setData(noteController.getAllNotes());

        rvCategories.setLayoutManager(layoutManagerCategory);
        rvNotes.setLayoutManager(layoutManagerNote);
        rvCategories.setAdapter(categoryAdapter);
        rvNotes.setAdapter(noteAdapter);

    }

    private void initListeners() {

        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddCategoryActivity.class);
            startActivity(intent);
        });

        fabAddNote.setOnClickListener(view -> {
            addNoteActivity();
        });

    }

    private void refreshCategories() {
        categoryAdapter.setData(categoryController.getAllCategories());
    }

    private void refreshNotes() {
        noteAdapter.setData(noteController.getAllNotes());
    }

    private void addNoteActivity(){
        Intent intent = new Intent(this, AddNotesActivity.class);
        startActivity(intent);
    }

    private void updateNoteActivity(Note note){

        Intent intent = new Intent(this, UpdateCategoryActivity.class);

        intent.putExtra("NOTE_NAME", note.note_title);
        intent.putExtra("NOTE_CONTENT", note.note_content);
        intent.putExtra("NOTE_ID", note.note_id);
        intent.putExtra("NOTE_CATEGORY_ID", note.category_id);

        startActivity(intent);


    }

}
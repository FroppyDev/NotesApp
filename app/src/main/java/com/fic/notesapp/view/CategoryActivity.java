package com.fic.notesapp.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.notesapp.R;
import com.fic.notesapp.controller.CategoryController;
import com.fic.notesapp.controller.NoteController;
import com.fic.notesapp.model.category.Category;
import com.fic.notesapp.model.note.Note;
import com.fic.notesapp.view.category.CategoryAdapter;
import com.fic.notesapp.view.note.NoteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView rvCategories;
    CategoryAdapter categoryAdapter;
    NoteAdapter noteAdapter;
    RecyclerView rvNotes;
    CategoryController categoryController;
    NoteController noteController;
    FloatingActionButton fabAdd, fabAddNote;
    TextView tvNotesCount;
    List<Integer> searchList;



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
        searchList = new ArrayList<>();
        tvNotesCount = findViewById(R.id.tvNotesCount);

    }

    private void initRV() {

        List<Note> notesList = noteController.getAllNotes();

        LinearLayoutManager layoutManagerCategory = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        LinearLayoutManager layoutManagerNote = new LinearLayoutManager(this);
        categoryAdapter = new CategoryAdapter(new CategoryAdapter.CheckedAction() {
            @Override
            public void onChecked(Category category, boolean checked) {
                if (checked){
                    searchList.add(category.category_id);
                    refreshNotes();
                } else {
                    searchList.remove(Integer.valueOf(category.category_id));
                    refreshNotes();
                }
            }

            @Override
            public void onDelete(Category category) {
                if (category.category_id == 1){
                    return;
                } else {
                    deleteCategoryDialog(category);
                }
            }
        });
        categoryAdapter.setData(categoryController.getAllCategories());
        noteAdapter = new NoteAdapter(new NoteAdapter.OnNoteActionListener() {
            @Override
            public void onEdit(Note note) {
                addNoteActivity(true, note);
            }

            @Override
            public void onDelete(Note note) {
                refreshNotes();
            }
        });
        noteAdapter.setData(notesList);
        rvCategories.setLayoutManager(layoutManagerCategory);
        rvNotes.setLayoutManager(layoutManagerNote);
        rvCategories.setAdapter(categoryAdapter);
        rvNotes.setAdapter(noteAdapter);
        tvNotesCount.setText(" -> " + notesList.size());

    }

    private void initListeners() {

        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddCategoryActivity.class);
            startActivity(intent);
        });

        fabAddNote.setOnClickListener(view -> {
            addNoteActivity(false, null);
        });

    }

    private void refreshCategories() {
        categoryAdapter.setData(categoryController.getAllCategories());
    }

    private void refreshNotes() {

        List<Note> notesList;

        if (searchList.isEmpty()){
            notesList = noteController.getAllNotes();
            noteAdapter.setData(notesList);
            tvNotesCount.setText(" -> " + notesList.size());
        } else {
            notesList = noteController.getNotesByCategories(searchList);
            noteAdapter.setData(notesList);
            tvNotesCount.setText(" -> " + notesList.size());

        }

    }

    private void addNoteActivity(boolean onEdit, Note note){
        Intent intent = new Intent(this, AddNotesActivity.class);

        if (onEdit){
            intent.putExtra("ON_EDIT", true);
            intent.putExtra("NOTE_CATEGORY_ID", note.category_id);
            intent.putExtra("NOTE_TITLE", note.note_title);
            intent.putExtra("NOTE_CONTENT", note.note_content);
            intent.putExtra("NOTE_ID", note.note_id);
            intent.putExtra("NOTE_CATEGORY_NAME", categoryController.getCategoryById(note.category_id).category_name);
            startActivity(intent);
        } else {
            intent.putExtra("ON_EDIT", false);
            startActivity(intent);
        }

    }

    public void deleteCategory(Category category){
        try {
            noteController.updateNotesCategoryByDefault(category.category_id);
            categoryController.deleteCategory(category);

        } catch (Exception e){
            Log.i("ERROR_DELETE_CATEGORY", "Error al eliminar categoria:" + e.getMessage());
        }
    }

    public void deleteCategoryDialog(Category category){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_delete_category);
        List<Note> notes = noteController.getNotesByCategory(category.category_id);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnDecline = dialog.findViewById(R.id.btnDecline);
        TextView tvLinkedNotes = dialog.findViewById(R.id.tvLinkedNotes);
        tvLinkedNotes.setText(notes.size() + " " + getString(R.string.linked_notes));


        btnConfirm.setOnClickListener(view -> {
            deleteCategory(category);
            refreshCategories();
            refreshNotes();
            dialog.dismiss();
        });

        btnDecline.setOnClickListener(view -> {
            dialog.dismiss();
        });


        dialog.show();

    }

}
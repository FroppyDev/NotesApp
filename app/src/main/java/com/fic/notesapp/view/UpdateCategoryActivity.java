package com.fic.notesapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fic.notesapp.R;
import com.fic.notesapp.controller.CategoryController;
import com.fic.notesapp.controller.NoteController;
import com.fic.notesapp.model.category.Category;

import java.util.ArrayList;
import java.util.List;

public class UpdateCategoryActivity extends AppCompatActivity {

    NoteController noteController;
    CategoryController categoryController;
    EditText etTitle, etContent;
    Spinner spCategory;
    AppCompatButton btnAddNote;
    int idCategory = 1;
    String extraNoteName;
    String extraNoteContent;
    int extraNoteId;
    int extraCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);
        initExtras();
        initComponents();
        initListeners();
    }

    private void initExtras() {

        extraNoteName = getIntent().getStringExtra("NOTE_NAME");
        extraNoteContent = getIntent().getStringExtra("NOTE_CONTENT");
        extraNoteId = getIntent().getIntExtra("NOTE_ID", 0);
        extraCategoryId = getIntent().getIntExtra("NOTE_CATEGORY_ID", 0);

    }

    private void initComponents() {

        noteController = new NoteController(this);
        categoryController = new CategoryController(this);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnAddNote = findViewById(R.id.addNote);

        etTitle.setText(extraNoteName);
        etContent.setText(extraNoteContent);

        initSpinner();
    }

    private void initSpinner(){
        List<Category> listCategories = categoryController.getAllCategories();
        List<String> listCategoriesNames = new ArrayList<>();
        for(Category category : listCategories){
            listCategoriesNames.add(category.category_name);
        }

        spCategory = findViewById(R.id.spCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listCategoriesNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = listCategories.get(position);
                Log.i("SELECTED_CATEGORY", "Categoria seleccionada: " + selectedCategory.category_id);
                idCategory = selectedCategory.category_id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                idCategory = listCategories.get(0).category_id;
            }
        });
    }

    private void initListeners() {

        btnAddNote.setOnClickListener(v -> {
            if(validarDatos()){

                updateNote();
                finish();

            }
        });

    }

    private void updateNote() {

        try {
            noteController.updateNote(extraNoteId, idCategory, etTitle.getText().toString(), etContent.getText().toString(), "10/10/2023");
            Toast.makeText(this, "La nota se ha actualizado correctamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(this, "No se ha podido actualizar la nota", Toast.LENGTH_SHORT).show();
            Log.i("ERROR_ADD_NOTE", "Error al actualizar la nota: " + e.getMessage());
        }

    }

    private boolean validarDatos() {
        return !etTitle.getText().toString().isEmpty() && !etContent.getText().toString().isEmpty();
    }
}
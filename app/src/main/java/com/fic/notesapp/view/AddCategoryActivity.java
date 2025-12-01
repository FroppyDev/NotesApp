package com.fic.notesapp.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.fic.notesapp.R;
import com.fic.notesapp.controller.CategoryController;

public class AddCategoryActivity extends AppCompatActivity {

    EditText etTitle;
    AppCompatButton btnAddNote;
    CategoryController categoryController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        initComponents();
        initListener();
    }

    private void initComponents() {

        etTitle = findViewById(R.id.etTitle);
        btnAddNote = findViewById(R.id.addNote);
        categoryController = new CategoryController(this);

    }

    private void initListener(){
        btnAddNote.setOnClickListener(v ->{
            if (validateField()){
                addCategory();
            }
        });
    }

    private void addCategory() {
        try {
            categoryController.insertCategory(etTitle.getText().toString());
            Toast.makeText(this, "Se ha agregado una categoria", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Error al agregar categoria", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean validateField() {

        String title = etTitle.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError(getText(R.string.error_empty_field));
            return false;
        } else {
            return true;
        }
    }
}
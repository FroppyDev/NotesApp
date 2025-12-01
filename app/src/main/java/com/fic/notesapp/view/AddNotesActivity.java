package com.fic.notesapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.fic.notesapp.R;
import com.fic.notesapp.controller.CategoryController;
import com.fic.notesapp.controller.NoteController;
import com.fic.notesapp.model.CategorySpinner;
import com.fic.notesapp.model.category.Category;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddNotesActivity extends AppCompatActivity {

    NoteController noteController;
    CategoryController categoryController;
    EditText etTitle, etContent;
    Spinner spCategory;
    AppCompatButton btnAddNote;
    int idCategory = 1;
    boolean extraBoolean;
    int extraCategoryId;
    String extraNoteTitle, extraNoteCategoryName;
    String extraNoteContent;
    int extraNoteId;
    TextView tvViewName;
    List<Category> listCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        initControllers();
        initComponents();
        initListeners();

    }

    private void initControllers(){
        if (noteController == null && categoryController == null){
            noteController = new NoteController(this);
            categoryController = new CategoryController(this);
        }
        extraBoolean = getIntent().getBooleanExtra("ON_EDIT", false);
    }

    private void initComponents() {
        Log.i("ON_EDIT", "ON_EDIT: " + extraBoolean);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnAddNote = findViewById(R.id.addNote);
        tvViewName = findViewById(R.id.tvViewName);

        if (extraBoolean){
            initExtras();
            etTitle.setText(extraNoteTitle);
            etContent.setText(extraNoteContent);
            tvViewName.setText(getText(R.string.update_note_text));
            btnAddNote.setText(getText(R.string.btn_update_note));
        }

        initSpinner();
    }

    private void initExtras(){
        extraNoteContent = getIntent().getStringExtra("NOTE_CONTENT");
        extraNoteTitle = getIntent().getStringExtra("NOTE_TITLE");
        extraCategoryId = getIntent().getIntExtra("NOTE_CATEGORY_ID", 1);
        extraNoteId = getIntent().getIntExtra("NOTE_ID", 1);
        extraNoteCategoryName = getIntent().getStringExtra("NOTE_CATEGORY_NAME");
    }

    private void initSpinner(){
        listCategories = categoryController.getAllCategories();
        List<CategorySpinner> listCategoriesSpinners = new ArrayList<>();
        List<String> listCategoriesNames = new ArrayList<>();
        for(Category category : listCategories){
            listCategoriesSpinners.add(new CategorySpinner(category.category_name, category.category_id));
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

        if (extraBoolean){
            spCategory.setSelection(listCategoriesNames.indexOf(extraNoteCategoryName));
        }

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategorySpinner selectedCategory = listCategoriesSpinners.get(position);
                Log.i("SELECTED_CATEGORY", "Categoria seleccionada: " + selectedCategory.categoryName + " ID: " + selectedCategory.categoryId);
                idCategory = selectedCategory.categoryId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                idCategory = listCategoriesSpinners.get(0).categoryId;
            }
        });
    }

    private void initListeners() {

        btnAddNote.setOnClickListener(v -> {


            if (extraBoolean){
                if(validarDatos()){
                    updateNote();
                    finish();
                }
            } else {
                if(validarDatos()){

                    saveNote();
                    finish();

                }
            }
        });

    }

    private void updateNote(){
        try {
            noteController.updateNote(extraNoteId, idCategory, etTitle.getText().toString(), etContent.getText().toString(), getCurrentDate());
            Toast.makeText(this, "La nota se ha actualizado correctamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(this, "No se ha podido actualizar la nota", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveNote() {

        try {
            noteController.insertNote(etTitle.getText().toString(), etContent.getText().toString(), idCategory, getCurrentDate());
            Toast.makeText(this, "La nota se ha guardado correctamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(this, "No se ha podido añadir la nota", Toast.LENGTH_SHORT).show();
            Log.i("ERROR_ADD_NOTE", "Error al guardar la nota, cat_id: " + idCategory + "Error: " + e.getMessage());
        }
        
    }

    private boolean validarDatos() {
        return !etTitle.getText().toString().isEmpty() && !etContent.getText().toString().isEmpty();
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
}
package com.fic.notesapp.controller;

import android.content.Context;

import com.fic.notesapp.model.ProviderDatabase;
import com.fic.notesapp.model.note.Note;
import com.fic.notesapp.model.note.NoteDao;

import java.util.List;

public class NoteController {

    private final NoteDao noteDao;

    public NoteController(Context context) {
        this.noteDao = ProviderDatabase.getInstance(context).noteDao();
    }

    public void insertNote(String title, String content, int categoryId, String createdAt) {

        Note note = new Note();
        note.note_title = title;
        note.note_content = content;
        note.category_id = categoryId;
        note.created_at = createdAt;

        noteDao.insertNote(note);

    }

    public void updateNote(int noteId, int categoryId, String title, String content, String createdAt) {

        Note note = new Note();
        note.note_id = noteId;
        note.note_title = title;
        note.note_content = content;
        note.category_id = categoryId;
        note.created_at = createdAt;

        noteDao.updateNote(note);

    }

    public void deleteNote(Note note) { noteDao.deleteNote(note); }

    public List<Note> getAllNotes() { return noteDao.getAllNotes(); }

    public List<Note> getNotesByCategories(List<Integer> categoryId) { return noteDao.getNotesByCategories(categoryId); }

    public List<Note> getNotesByCategory(int categoryId) { return noteDao.getNotesByCategory(categoryId); }

    public void updateNotesCategoryByDefault(int idCategoryDeleted) {noteDao.updateNotesCategoryByDefault(idCategoryDeleted);}

}

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

    public void updateNote(int note_id, int categoryId, String title, String content, String createdAt) {

        Note note = new Note();
        note.note_id = note_id;
        note.note_title = title;
        note.note_content = content;
        note.category_id = categoryId;
        note.created_at = createdAt;

        noteDao.updateNote(note);

    }

    public List<Note> getAllNotes() { return noteDao.getAllNotes(); }
}

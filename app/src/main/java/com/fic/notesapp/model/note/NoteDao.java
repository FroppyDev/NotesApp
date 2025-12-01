package com.fic.notesapp.model.note;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insertNote(Note note);

    @Query("SELECT * FROM note ORDER BY note_id DESC")
    List<Note> getAllNotes();

    @Query("SELECT * FROM note WHERE note_id = :id")
    Note getNoteById(int id);

    @Query("SELECT * FROM note WHERE category_id = :categoryId")
    List<Note> getNotesByCategory(int categoryId);

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("SELECT * FROM note WHERE category_id IN (:categoryIds)")
    List<Note> getNotesByCategories(List<Integer> categoryIds);

    @Query("UPDATE note SET category_id = 1 WHERE category_id = :idCategoryDeleted")
    void updateNotesCategoryByDefault(int idCategoryDeleted);
}

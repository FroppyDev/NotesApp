package com.fic.notesapp.model.note;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.fic.notesapp.model.category.Category;

@Entity(tableName = "note", foreignKeys = {
        @ForeignKey(entity = Category.class, parentColumns = "category_id", childColumns = "note_id")})
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int note_id = 0;

    @ColumnInfo(name = "category_id")
    public int category_id;

    @ColumnInfo(name = "note_title")
    public String note_title;

    @ColumnInfo(name = "note_content")
    public String note_content;

    @ColumnInfo(name = "created_at")
    public String created_at;
}

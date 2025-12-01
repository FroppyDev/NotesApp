package com.fic.notesapp.view.note;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.notesapp.R;
import com.fic.notesapp.controller.CategoryController;
import com.fic.notesapp.controller.NoteController;
import com.fic.notesapp.model.category.Category;
import com.fic.notesapp.model.note.Note;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    TextView tvTitle, tvContent, tvDate, tvCategory;
    CategoryController categoryController;
    NoteController noteController;
    Category category;
    CardView cvNoteParent;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryController = new CategoryController(itemView.getContext());
        noteController = new NoteController(itemView.getContext());

    }

    public void render(Note item, NoteAdapter.OnNoteActionListener listener) {

        category = categoryController.getCategoryById(item.category_id);

        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvContent = itemView.findViewById(R.id.tvContent);
        tvDate = itemView.findViewById(R.id.tvDate);
        tvCategory = itemView.findViewById(R.id.tvCategory);
        cvNoteParent = itemView.findViewById(R.id.cvNoteParent);


        tvTitle.setText(item.note_title);
        tvContent.setText(item.note_content);
        tvDate.setText(item.created_at);
        tvCategory.setText(category.category_name);

        cvNoteParent.setOnClickListener(view -> {
            listener.onEdit(item);
        });

        cvNoteParent.setOnLongClickListener(view -> {

            deleteDialog(itemView.getContext(), item, listener);

            return true;
        });
    }

    private void deleteDialog(Context context, Note note, NoteAdapter.OnNoteActionListener listener) {

        Dialog dialog = new Dialog(context);
        View view = View.inflate(context, R.layout.dialog_delete_note, null);
        dialog.setContentView(view);

        Button btnConfirm = view.findViewById(R.id.btnConfirm);
        Button btnDecline = view.findViewById(R.id.btnDecline);

        btnConfirm.setOnClickListener(view1 -> {
            deleteNote(note);
            listener.onDelete(note);
            dialog.dismiss();
        });

        btnDecline.setOnClickListener(view1 -> {
            dialog.dismiss();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void deleteNote(Note note) {
        try{
            noteController.deleteNote(note);
            Toast.makeText(itemView.getContext(), "Se ha eliminado la nota", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(itemView.getContext(), "Error al eliminar la nota", Toast.LENGTH_SHORT).show();
            Log.i("ERROR_DELETE_NOTE", e.getMessage());
        }
    }
}

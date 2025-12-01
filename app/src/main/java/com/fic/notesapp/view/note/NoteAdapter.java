package com.fic.notesapp.view.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.notesapp.R;
import com.fic.notesapp.model.note.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private final List<Note> listNotes = new ArrayList<>();
    private final OnNoteActionListener listener;

    public NoteAdapter(OnNoteActionListener listener){
        this.listener = listener;
    }

    public void setData(List<Note> listNote){
        this.listNotes.clear();
        if(listNote != null){
            this.listNotes.addAll(listNote);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note item = listNotes.get(position);
        holder.render(item, listener);
    }


    @Override
    public int getItemCount() {
        return listNotes.size();
    }


    public interface OnNoteActionListener {
        void onEdit(Note note);
        void onDelete(Note note);
    }
}

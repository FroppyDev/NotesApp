package com.fic.notesapp.view.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.notesapp.R;
import com.fic.notesapp.model.category.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    private final List<Category> listCategories = new ArrayList<>();
    private final CheckedAction checkedAction;

    public CategoryAdapter(CheckedAction checkedAction) {
        this.checkedAction = checkedAction;
    }

    public void setData(List<Category> newListCategories){
        // Limpia la lista actual del adaptador.
        this.listCategories.clear();

        // Si la nueva lista no es nula, agrega todos sus elementos.
        if(newListCategories != null){
            this.listCategories.addAll(newListCategories);
        }

        // Notifica al RecyclerView que debe redibujar todos los elementos con la nueva información.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category item = listCategories.get(position);
        holder.render(item, checkedAction);
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }

    public interface CheckedAction {
        void onChecked(Category category, boolean checked);
        void onDelete(Category category);
    }
}

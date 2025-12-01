package com.fic.notesapp.view.category;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.notesapp.R;
import com.fic.notesapp.model.category.Category;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    CheckBox cbBusqueda;
    TextView tvTitle;
    CardView cvParent;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
    }


    public void render(Category item, CategoryAdapter.CheckedAction checkedAction) {
        cbBusqueda = itemView.findViewById(R.id.cbBusqueda);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        cvParent = itemView.findViewById(R.id.cvNoteParent);

        cbBusqueda.setOnClickListener(v -> {

            if (cbBusqueda.isChecked()){
                checkedAction.onChecked(item, true);
            } else {
                checkedAction.onChecked(item, false);
            }

        });

        cvParent.setOnLongClickListener(view -> {

            checkedAction.onDelete(item);

            return true;
        });

        
        tvTitle.setText(item.category_name);
    }
}

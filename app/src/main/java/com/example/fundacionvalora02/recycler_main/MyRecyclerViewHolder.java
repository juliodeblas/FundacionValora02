package com.example.fundacionvalora02.recycler_main;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fundacionvalora02.R;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView text_item_nombre, text_item_curso;
    com.example.fundacionvalora02.recycler_main.itemClickListener itemClickListener;

    public MyRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        text_item_nombre = itemView.findViewById(R.id.text_item_nombre);
        text_item_curso = itemView.findViewById(R.id.text_item_curso);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(com.example.fundacionvalora02.recycler_main.itemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}

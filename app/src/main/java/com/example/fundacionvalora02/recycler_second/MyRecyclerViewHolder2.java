package com.example.fundacionvalora02.recycler_second;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fundacionvalora02.R;
import com.example.fundacionvalora02.utils.Alumno;

import java.util.ArrayList;

public class MyRecyclerViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView text_item_nombre, text_item_apellidos, text_item_numero;
    itemClickListener2 itemClickListener2;

    public MyRecyclerViewHolder2(@NonNull View itemView) {
        super(itemView);
        text_item_nombre = itemView.findViewById(R.id.text_item_nombre_alumno);
        text_item_apellidos = itemView.findViewById(R.id.text_item_apellidos_alumno);
        text_item_numero = itemView.findViewById(R.id.text_item_numero_alumno);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener2(com.example.fundacionvalora02.recycler_second.itemClickListener2 itemClickListener2) {
        this.itemClickListener2 = itemClickListener2;
    }



    @Override
    public void onClick(View v) {
        itemClickListener2.onClick(v, getAdapterPosition());
    }
}

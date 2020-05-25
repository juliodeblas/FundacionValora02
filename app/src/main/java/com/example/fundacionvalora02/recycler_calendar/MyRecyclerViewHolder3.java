package com.example.fundacionvalora02.recycler_calendar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fundacionvalora02.R;

public class MyRecyclerViewHolder3 extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView text_nombre, text_fecha;
    itemClickListener3 listener3;

    public void setListener3(itemClickListener3 listener3) {
        this.listener3 = listener3;
    }

    public MyRecyclerViewHolder3(@NonNull View itemView) {
        super(itemView);

        text_nombre = itemView.findViewById(R.id.text_nombre_calendar);
        text_fecha = itemView.findViewById(R.id.text_fecha_calendar);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener3.onClick(v, getAdapterPosition());
    }
}

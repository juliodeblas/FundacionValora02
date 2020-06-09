package com.example.fundacionvalora02.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.fundacionvalora02.R;
import com.example.fundacionvalora02.utils.Alumno;
import com.example.fundacionvalora02.utils.Modulo;
import com.example.fundacionvalora02.utils.SemaforoSaberEstar;
import com.example.fundacionvalora02.utils.SemaforoSaberHacer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DialogoSpinner extends DialogFragment {

    Spinner spinner;
    View vista;
    OnDialogoSpinnerListener listener;

    FirebaseDatabase database;
    DatabaseReference reference;

    ArrayList<Modulo> lista_modulos;

    ArrayAdapter<Modulo> arrayAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnDialogoSpinnerListener) context;
        } catch (ClassCastException e) {
            Log.v("cast", "ERROR");
        }

        vista = LayoutInflater.from(context).inflate(R.layout.layout_dialogo_spinner, null);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        instancias();
        acciones();
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setView(vista);

        return dialogo.create();
    }

    private void acciones() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    Modulo modulo = (Modulo) parent.getAdapter().getItem(position);
                    listener.onSpinnerSelected(modulo);
                    dismiss();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void instancias() {
        spinner = vista.findViewById(R.id.spinner_modulos);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("modulos");
        lista_modulos = new ArrayList<>();
        lista_modulos.add(new Modulo("Módulos: ", " Año académico ", ""));
        arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.style_spinner, lista_modulos);
        arrayAdapter.notifyDataSetChanged();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Modulo modulo = item.getValue(Modulo.class);
                    lista_modulos.add(modulo);
                }

                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface OnDialogoSpinnerListener {
        void onSpinnerSelected(Modulo modulo);
    }
}

package com.example.fundacionvalora02.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.fundacionvalora02.R;
import com.example.fundacionvalora02.utils.SemaforoSaberEstar;
import com.example.fundacionvalora02.utils.SemaforoSaberHacer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class DialogoSemaforoSaberEstar extends DialogFragment {

    TextView text_conseguido, text_en_proceso, text_no_conseguido, text_faltas, text_faltas_justificadas, text_fecha;
    TextView text_n_conseguido, text_n_en_proceso, text_n_no_conseguido, text_n_faltas, text_n_faltas_justificadas;
    ImageButton button_mas_conseguido, button_menos_conseguido, button_mas_en_proceso, button_menos_en_proceso, button_mas_no_conseguido, button_menos_no_conseguido;
    ImageButton button_mas_faltas, button_menos_faltas, button_mas_faltas_justificadas, button_menos_faltas_justificadas;
    Button button_actualizar_semaforo_saber_estar;

    View vista;

    SemaforoSaberEstar semaforoSaberEstar = new SemaforoSaberEstar();

    int conseguido, en_proceso, no_conseguido, faltas, faltas_justificadas;

    FirebaseDatabase database;
    DatabaseReference reference;

    public interface OnSemaforoSESelected {
        void semaforoSaberEstarListener(SemaforoSaberEstar semaforo);
    }

    public DialogoSemaforoSaberEstar.OnSemaforoSESelected onSemaforoSESelected;

    public DialogoSemaforoSaberEstar(SemaforoSaberEstar semaforoSaberEstar) {
        this.semaforoSaberEstar = semaforoSaberEstar;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            onSemaforoSESelected = (DialogoSemaforoSaberEstar.OnSemaforoSESelected) getTargetFragment();
        } catch (ClassCastException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        vista = LayoutInflater.from(context).inflate(R.layout.layout_dialogo_semaforo_saber_estar, null);
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
        button_mas_conseguido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conseguido++;
                semaforoSaberEstar.setConseguido(conseguido);
                text_n_conseguido.setText(String.valueOf(conseguido));
            }
        });
        button_menos_conseguido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conseguido--;
                semaforoSaberEstar.setConseguido(conseguido);
                text_n_conseguido.setText(String.valueOf(conseguido));
            }
        });
        button_mas_en_proceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                en_proceso++;
                semaforoSaberEstar.setEn_proceso(en_proceso);
                text_n_en_proceso.setText(String.valueOf(en_proceso));
            }
        });
        button_menos_en_proceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                en_proceso--;
                semaforoSaberEstar.setEn_proceso(en_proceso);
                text_n_en_proceso.setText(String.valueOf(en_proceso));
            }
        });
        button_mas_no_conseguido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_conseguido++;
                semaforoSaberEstar.setNo_conseguido(no_conseguido);
                text_n_no_conseguido.setText(String.valueOf(no_conseguido));
            }
        });
        button_menos_no_conseguido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_conseguido--;
                semaforoSaberEstar.setNo_conseguido(no_conseguido);
                text_n_no_conseguido.setText(String.valueOf(no_conseguido));
            }
        });
        button_mas_faltas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faltas++;
                semaforoSaberEstar.setFalta(faltas);
                text_n_faltas.setText(String.valueOf(faltas));
            }
        });
        button_menos_faltas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faltas--;
                semaforoSaberEstar.setFalta(faltas);
                text_n_faltas.setText(String.valueOf(faltas));
            }
        });
        button_mas_faltas_justificadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faltas_justificadas++;
                semaforoSaberEstar.setFalta_justificada(faltas_justificadas);
                text_n_faltas_justificadas.setText(String.valueOf(faltas_justificadas));
            }
        });
        button_menos_faltas_justificadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faltas_justificadas--;
                semaforoSaberEstar.setFalta_justificada(faltas_justificadas);
                text_n_faltas_justificadas.setText(String.valueOf(faltas_justificadas));
            }
        });
        button_actualizar_semaforo_saber_estar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSemaforoSESelected.semaforoSaberEstarListener(semaforoSaberEstar);
                getDialog().dismiss();
            }
        });
    }

    private void instancias() {
        text_conseguido = vista.findViewById(R.id.text_conseguido_saber_estar);
        text_en_proceso = vista.findViewById(R.id.text_en_proceso_saber_estar);
        text_no_conseguido = vista.findViewById(R.id.text_no_conseguido_saber_estar);
        text_faltas = vista.findViewById(R.id.text_faltas_saber_estar);
        text_faltas_justificadas = vista.findViewById(R.id.text_faltas_justificadas_saber_estar);
        button_mas_conseguido = vista.findViewById(R.id.button_mas_conseguido_saber_estar);
        button_menos_conseguido = vista.findViewById(R.id.button_menos_conseguido_saber_estar);
        button_mas_en_proceso = vista.findViewById(R.id.button_mas_en_proceso_saber_estar);
        button_menos_en_proceso = vista.findViewById(R.id.button_menos_en_proceso_saber_estar);
        button_mas_no_conseguido = vista.findViewById(R.id.button_mas_no_conseguido_saber_estar);
        button_menos_no_conseguido = vista.findViewById(R.id.button_menos_no_conseguido_saber_estar);
        button_mas_faltas = vista.findViewById(R.id.button_mas_faltas_saber_estar);
        button_menos_faltas = vista.findViewById(R.id.button_menos_faltas_saber_estar);
        button_mas_faltas_justificadas = vista.findViewById(R.id.button_mas_faltas_justificadas_saber_estar);
        button_menos_faltas_justificadas = vista.findViewById(R.id.button_menos_faltas_justificadas_saber_estar);
        button_actualizar_semaforo_saber_estar = vista.findViewById(R.id.button_dialogo_saber_estar);
        text_n_conseguido = vista.findViewById(R.id.text_numero_conseguido_saber_estar);
        text_n_en_proceso = vista.findViewById(R.id.text_numero_en_proceso_saber_estar);
        text_n_no_conseguido = vista.findViewById(R.id.text_numero_no_conseguido_saber_estar);
        text_n_faltas = vista.findViewById(R.id.text_numero_faltas_saber_estar);
        text_n_faltas_justificadas = vista.findViewById(R.id.text_numero_faltas_justificadas_saber_estar);
        text_fecha = vista.findViewById(R.id.text_fecha_se);
        Calendar calendar = Calendar.getInstance();
        String fecha = DateFormat.getDateInstance().format(calendar.getTime());
        text_fecha.setText(fecha);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        reference.child("semaforos_saber_estar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    SemaforoSaberEstar semaforo = item.getValue(SemaforoSaberEstar.class);

                    if (semaforo.getId_alumno().equals(semaforoSaberEstar.getId_alumno())) {
                        text_n_conseguido.setText(String.valueOf(semaforo.getConseguido()));
                        text_n_en_proceso.setText(String.valueOf(semaforo.getEn_proceso()));
                        text_n_no_conseguido.setText(String.valueOf(semaforo.getNo_conseguido()));
                        text_n_faltas.setText(String.valueOf(semaforo.getFalta()));
                        text_n_faltas_justificadas.setText(String.valueOf(semaforo.getFalta_justificada()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        conseguido = semaforoSaberEstar.getConseguido();
        en_proceso = semaforoSaberEstar.getEn_proceso();
        no_conseguido = semaforoSaberEstar.getNo_conseguido();
        faltas = semaforoSaberEstar.getFalta();
        faltas_justificadas = semaforoSaberEstar.getFalta_justificada();
    }

}

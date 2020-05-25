package com.example.fundacionvalora02.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.fundacionvalora02.R;
import com.example.fundacionvalora02.utils.Evento;

import java.util.Calendar;
import java.util.Random;

public class DialogoEventoCrear extends DialogFragment {

    EditText nombreEvento;
    EditText fechaEvento;
    EditText descripcionEvento;
    Button crearEvento;
    OnDialogoPersoListener listener;
    View vista;
    String fecha;
    String letters = "abcdefghijklmnopqrstuvwxyz";
    Calendar clicked_day_calendar;

    char[] alfanumerico = (letters + letters.toUpperCase() + "0123456789").toCharArray();

    public DialogoEventoCrear(Calendar calendar) {
        this.clicked_day_calendar = calendar;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogoEventoCrear.OnDialogoPersoListener) context;
        } catch (ClassCastException e) {
            Log.v("cast", "ERROR");
        }

        vista = LayoutInflater.from(context).inflate(R.layout.layout_dialogo_personalizado_crear_evento, null);
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
        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nombreEvento.getText().toString().matches("") && !fechaEvento.getText().toString().matches("") &&
                        !descripcionEvento.getText().toString().matches("")) {
                    String titulo = nombreEvento.getText().toString();
                    String descripcion = nombreEvento.getText().toString();
                    String[] listaDatos = fecha.split(" ");
                    fecha = clicked_day_calendar.getTime().toString();
                    String id = generadorAlfanumerico(10);
                    Long milisegundos = clicked_day_calendar.getTimeInMillis();
                    Evento evento = new Evento(id, titulo, descripcion, listaDatos[0], listaDatos[2], listaDatos[1], listaDatos[5], milisegundos);
                    listener.onDialogoSelected(evento);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Debe introducir todos los datos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void instancias() {
        nombreEvento = vista.findViewById(R.id.nombreEvento);
        fechaEvento = vista.findViewById(R.id.fechaEvento);
        descripcionEvento = vista.findViewById(R.id.decripcionEvento);
        crearEvento = vista.findViewById(R.id.crearEvento);
        fechaEvento.setEnabled(false);
        fecha = clicked_day_calendar.getTime().toString();
        String[] listaDatos1 = fecha.split(" ");
        fechaEvento.setText(listaDatos1[2] + "/" + listaDatos1[1] + "/" + listaDatos1[5]);
    }

    public String generadorAlfanumerico(int length) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            builder.append(alfanumerico[new Random().nextInt(alfanumerico.length)]);
        }

        return builder.toString();
    }

    public interface OnDialogoPersoListener {
        void onDialogoSelected(Evento evento);
    }
}

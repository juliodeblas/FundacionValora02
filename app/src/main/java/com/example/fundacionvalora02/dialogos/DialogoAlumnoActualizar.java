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
import com.example.fundacionvalora02.SecondActivity;
import com.example.fundacionvalora02.utils.Alumno;

public class DialogoAlumnoActualizar extends DialogFragment {

    EditText edit_nombre, edit_apellidos, edit_numero, edit_perfil, edit_grupo, edit_id;
    View vista;
    Button button_actualizar;
    OnDialogoPersoListener listener;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnDialogoPersoListener) context;
        } catch (ClassCastException e) {
            Log.v("cast", "ERROR");
        }

        vista = LayoutInflater.from(context).inflate(R.layout.layout_dialogo_personalizado_actualizar, null);
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
        button_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_nombre.getText().toString().matches("") && !edit_apellidos.getText().toString().matches("")
                        && !edit_numero.getText().toString().matches("") && !edit_grupo.getText().toString().matches("") &&
                        !edit_perfil.getText().toString().matches("") && !edit_id.getText().toString().matches("")) {
                    String nombre, apellidos, numero, perfil, grupo, id;
                    nombre = edit_nombre.getText().toString();
                    apellidos = edit_apellidos.getText().toString();
                    numero = edit_numero.getText().toString();
                    perfil = edit_perfil.getText().toString();
                    grupo = edit_grupo.getText().toString();
                    id = edit_id.getText().toString();

                    Alumno alumno = new Alumno(nombre, apellidos, numero, perfil, grupo, id);
                    listener.onDialogoSelectedAct(alumno);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Debe introducir todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void instancias() {
        button_actualizar = vista.findViewById(R.id.button_actualizar_dialogo);
        edit_nombre = vista.findViewById(R.id.edit_nombre_dialogo_act);
        edit_nombre.setText(SecondActivity.selected_alumno.getNombre());
        edit_apellidos = vista.findViewById(R.id.edit_apellidos_dialogo_act);
        edit_apellidos.setText(SecondActivity.selected_alumno.getApellidos());
        edit_numero = vista.findViewById(R.id.edit_numero_dialogo_act);
        edit_numero.setText(SecondActivity.selected_alumno.getNumero_orden());
        edit_perfil = vista.findViewById(R.id.edit_perfil_dialogo_act);
        edit_perfil.setText(SecondActivity.selected_alumno.getPerfil());
        edit_grupo = vista.findViewById(R.id.edit_grupo_dialogo_act);
        edit_grupo.setText(SecondActivity.selected_alumno.getGrupo());
        edit_id = vista.findViewById(R.id.edit_id_dialogo);
        edit_id.setEnabled(false);
        edit_id.setText(SecondActivity.selected_modulo.getId());
    }

    public interface OnDialogoPersoListener {
        void onDialogoSelectedAct(Alumno alumno);
    }
}

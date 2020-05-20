package com.example.fundacionvalora02.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fundacionvalora02.R;
import com.example.fundacionvalora02.utils.Alumno;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUno extends Fragment {

    TextView text_nombre, text_apellidos, text_numero, text_grupo, text_perfil;

    private Alumno selected_alumno;
    private String selected_key_alumno;

    public FragmentUno() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            selected_alumno = (Alumno) getArguments().getSerializable(String.valueOf(R.string.TAG_SELECTED_ALUMNO));
            selected_key_alumno = getArguments().getString(String.valueOf(R.string.TAG_SELECTED_KEY_ALUMNO));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uno, container, false);
        text_nombre = view.findViewById(R.id.text_nombre_detalle);
        text_nombre.setText(text_nombre.getText() + selected_alumno.getNombre());
        text_apellidos = view.findViewById(R.id.text_apellidos_detalle);
        text_apellidos.setText(text_apellidos.getText() + selected_alumno.getApellidos());
        text_numero = view.findViewById(R.id.text_numero_detalle);
        text_numero.setText(text_numero.getText() + selected_alumno.getNumero_orden());
        text_grupo = view.findViewById(R.id.text_grupo_detalle);
        text_grupo.setText(selected_alumno.getGrupo());
        text_perfil = view.findViewById(R.id.text_perfil_detalle);
        text_perfil.setText(selected_alumno.getPerfil());

        return view;
    }
}

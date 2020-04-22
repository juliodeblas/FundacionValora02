package com.example.fundacionvalora02.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogoRecycler extends DialogFragment {

    OnSingleDialogSelecter onSingleDialogSelecter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onSingleDialogSelecter = (OnSingleDialogSelecter) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("¿Qué quieres hacer?");
        builder.setMessage("Si-Ver alumnos del módulo \nNo-Continuar en esta pantalla");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = "si";
                onSingleDialogSelecter.onDataSingleSelecter(s);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = "no";
                onSingleDialogSelecter.onDataSingleSelecter(s);
            }
        });

        return builder.create();
    }

    public interface OnSingleDialogSelecter{
        public void onDataSingleSelecter(String s);
    }
}

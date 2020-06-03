package com.example.fundacionvalora02.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fundacionvalora02.R;
import com.example.fundacionvalora02.dialogos.DialogoSemaforoSaberEstar;
import com.example.fundacionvalora02.utils.Alumno;
import com.example.fundacionvalora02.utils.SemaforoSaberEstar;
import com.example.fundacionvalora02.utils.Timestampp;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTres extends Fragment implements DialogoSemaforoSaberEstar.OnSemaforoSESelected {

    PieChart pieChart;
    ImageButton imageButton;

    private Alumno selected_alumno;
    private String selected_key_alumno;
    private SemaforoSaberEstar selected_semaforo_saber_estar;
    private String selected_key_semaforo_saber_estar;

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference reference1;

    Activity activity;

    public FragmentTres() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

        if (getArguments() != null) {
            selected_alumno = (Alumno) getArguments().getSerializable(String.valueOf(R.string.TAG_SELECTED_ALUMNO));
            selected_key_alumno = getArguments().getString(String.valueOf(R.string.TAG_SELECTED_KEY_ALUMNO));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tres, container, false);
        pieChart = view.findViewById(R.id.piechart_semaforo_saber_estar);
        imageButton = view.findViewById(R.id.imagebutton_semaforo_saber_estar);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("semaforos_saber_estar");
        Calendar calendar = Calendar.getInstance();
        String fecha = DateFormat.getDateInstance().format(calendar.getTime());
        String fecha_sin_punto = fecha.replace(".", "");
        reference1 = database.getReference(fecha_sin_punto + " - " + selected_alumno.getNombre() + " " + selected_alumno.getApellidos() + " - Saber Estar");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    SemaforoSaberEstar semaforoSaberEstar = item.getValue(SemaforoSaberEstar.class);
                    if ((getActivity() == null) || (getContext() == null)) {
                        Log.w("Info", "Creado con exito");
                    }
                    else {
                        if (semaforoSaberEstar.getId_alumno().equals(selected_alumno.getId())) {
                            selected_semaforo_saber_estar = semaforoSaberEstar;
                            selected_key_semaforo_saber_estar = item.getKey();
                            int conseguido, en_proceso, no_conseguido, faltas, faltas_justificadas;
                            conseguido = selected_semaforo_saber_estar.getConseguido();
                            en_proceso = selected_semaforo_saber_estar.getEn_proceso();
                            no_conseguido = selected_semaforo_saber_estar.getNo_conseguido();
                            faltas = selected_semaforo_saber_estar.getFalta();
                            faltas_justificadas = selected_semaforo_saber_estar.getFalta_justificada();

                            int[] datos = {conseguido, en_proceso, no_conseguido, faltas, faltas_justificadas};
                            String[] nombres_datos = {"Conseguido", "En Proceso", "No conseguido", "Faltas", "F.Justificadas"};

                            List<PieEntry> pieEntries = new ArrayList<>();
                            for (int i = 0; i < datos.length; i++) {
                                pieEntries.add(new PieEntry(datos[i], nombres_datos[i]));
                            }

                            PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                            pieDataSet.setColors(FragmentDos.COLORES_JULIO);
                            pieDataSet.setValueTextSize(25);

                            PieData pieData = new PieData(pieDataSet);

                            Description description = new Description();
                            description.setText(selected_alumno.getNombre() + " " + selected_alumno.getApellidos());
                            description.setEnabled(true);
                            description.setTextSize(30);

                            pieChart.setDescription(description);
                            pieChart.setBackgroundColor(getResources().getColor(R.color.light_white));

                            pieChart.setData(pieData);
                            pieChart.invalidate();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoSemaforoSaberEstar dialogoSemaforoSaberEstar = new DialogoSemaforoSaberEstar(selected_semaforo_saber_estar);
                dialogoSemaforoSaberEstar.setTargetFragment(FragmentTres.this, 2);
                dialogoSemaforoSaberEstar.show(getFragmentManager(), "semaforo_saber_estar");
            }
        });

        return view;
    }

    @Override
    public void semaforoSaberEstarListener(SemaforoSaberEstar semaforo) {
        reference.child(selected_key_semaforo_saber_estar).setValue(semaforo);
        Timestampp timestampp = new Timestampp(selected_alumno.getId(), semaforo.getConseguido(), semaforo.getEn_proceso(), semaforo.getNo_conseguido(), semaforo.getFalta(), semaforo.getFalta_justificada());
        reference1.push().setValue(timestampp);
        pieChart.notifyDataSetChanged();
    }

    public static final int[] COLORES_JULIO = {
            rgb("#4caf50"), rgb("#ffeb3b"), rgb("#f44336"), rgb("#b0bec5"), rgb("eeeeee")
    };
}

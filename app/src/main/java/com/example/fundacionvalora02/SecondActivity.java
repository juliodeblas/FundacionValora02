package com.example.fundacionvalora02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fundacionvalora02.dialogos.DialogoAlumno;
import com.example.fundacionvalora02.recycler_second.MyRecyclerViewHolder2;
import com.example.fundacionvalora02.recycler_second.itemClickListener2;
import com.example.fundacionvalora02.utils.Alumno;
import com.example.fundacionvalora02.utils.Modulo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecondActivity extends AppCompatActivity implements DialogoAlumno.OnDialogoPersoListener {

    Bundle bundle;
    TextView text_alumnos_modulo;
    RecyclerView recycler_alumnos;
    Button button_crear, button_actualizar, button_eliminar;
    public static String selected_key_modulo;
    public static Modulo selected_modulo;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerOptions<Alumno> options;
    FirebaseRecyclerAdapter<Alumno, MyRecyclerViewHolder2> adapter;

    String id_modulo;

    Alumno selected_alumno;
    String selected_key_alumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        bundle = getIntent().getExtras();
        instancias();
        acciones();
    }

    private void acciones() {
        button_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoAlumno dialogoAlumno = new DialogoAlumno();
                dialogoAlumno.show(getSupportFragmentManager(), "perso");
            }
        });
        button_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(selected_key_alumno).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SecondActivity.this, "¡Borrado correctamente!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SecondActivity.this, "" + "Debe seleccionar un alumno", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void instancias() {
        text_alumnos_modulo = findViewById(R.id.text_alumnos_modulo);
        recycler_alumnos = findViewById(R.id.recycler_second);
        recycler_alumnos.setLayoutManager(new LinearLayoutManager(this));
        button_crear = findViewById(R.id.button_crear_alumno);
        button_actualizar = findViewById(R.id.button_actualizar_alumno);
        button_eliminar = findViewById(R.id.button_eliminar_alumno);
        if (bundle != null) {
            selected_key_modulo = bundle.getString(String.valueOf(R.string.TAG_SELECTED_KEY));
            selected_modulo = (Modulo) bundle.getSerializable(String.valueOf(R.string.TAG_SELECTED_MODULO));
        }
        id_modulo = selected_modulo.getId();
        text_alumnos_modulo.setText(text_alumnos_modulo.getText() + selected_modulo.getNombre() + " " + selected_modulo.getCurso());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("alumnos");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayAlumno();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void displayAlumno() {
        options = new FirebaseRecyclerOptions.Builder<Alumno>().setQuery(databaseReference, Alumno.class).build();
        adapter = new FirebaseRecyclerAdapter<Alumno, MyRecyclerViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder2 holder, int position, @NonNull final Alumno model) {
                if(selected_modulo.getId().equals(model.getId_modulo())){
                    holder.text_item_nombre.setText(model.getNombre());
                    holder.text_item_apellidos.setText(model.getApellidos());
                    holder.text_item_numero.setText(model.getNumero_orden());
                }else {
                    System.out.println("...");
                }

                holder.setItemClickListener2(new itemClickListener2() {
                    @Override
                    public void onClick(View view, int position) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
                        builder.setTitle("¿Qué quieres hacer?");
                        builder.setMessage("Si-Ver detalles del alumno \nNo-Continuar en esta pantalla");
                        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.create();
                        builder.show();

                        selected_alumno = model;
                        selected_key_alumno = getSnapshots().getSnapshot(position).getKey();
                        Log.d("Key item", "" + selected_key_alumno);
                    }
                });

            }

            @NonNull
            @Override
            public MyRecyclerViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_recycler_second, parent, false);

                return new MyRecyclerViewHolder2(view);
            }
        };
        adapter.startListening();
        recycler_alumnos.setAdapter(adapter);
    }

    @Override
    public void onDialogoSelected(Alumno alumno) {
        databaseReference.push().setValue(alumno);
        adapter.notifyDataSetChanged();
    }
}

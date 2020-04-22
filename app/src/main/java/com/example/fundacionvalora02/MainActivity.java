package com.example.fundacionvalora02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fundacionvalora02.dialogos.DialogoRecycler;
import com.example.fundacionvalora02.recycler.MyRecyclerViewHolder;
import com.example.fundacionvalora02.recycler.itemClickListener;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DialogoRecycler.OnSingleDialogSelecter {

    RecyclerView recycler_view;
    EditText edit_nombre, edit_curso;
    Button button_crear, button_eliminar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerOptions<Modulo> options;
    FirebaseRecyclerAdapter<Modulo, MyRecyclerViewHolder> adapter;

    Modulo selected_modulo;
    String selected_key;
    String respuesta = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instancias();
    }

    private void instancias() {
        recycler_view = findViewById(R.id.recycler_main);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        edit_nombre = findViewById(R.id.edit_nombre);
        edit_curso = findViewById(R.id.edit_curso);
        button_crear = findViewById(R.id.button_crear);
        button_eliminar = findViewById(R.id.button_eliminar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("FV-FIREBASE");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearModulo();
            }
        });

        button_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(selected_key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "¡Borrado correctamente!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        displayModulo();
    }

    @Override
    protected void onStop() {
        if (adapter != null) {
            adapter.stopListening();
        }
        super.onStop();
    }

    private void crearModulo() {
        String nombre = edit_nombre.getText().toString();
        String curso = edit_curso.getText().toString();
        ArrayList<Alumno> lista_alumnos = new ArrayList<Alumno>();

        Modulo modulo = new Modulo(nombre, curso, lista_alumnos);

        databaseReference.push().setValue(modulo);

        adapter.notifyDataSetChanged();
    }

    private void displayModulo() {
        options = new FirebaseRecyclerOptions.Builder<Modulo>().setQuery(databaseReference, Modulo.class).build();
        adapter = new FirebaseRecyclerAdapter<Modulo, MyRecyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position, @NonNull final Modulo model) {
                holder.text_item_nombre.setText(model.getNombre());
                holder.text_item_curso.setText(model.getCurso());

                holder.setItemClickListener(new itemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        DialogoRecycler dialogoRecycler = new DialogoRecycler();
                        dialogoRecycler.show(getSupportFragmentManager(), "Aviso");

                        if (respuesta.equals("si")) {

                        } else if (respuesta.equals("no")) {

                        }

                        selected_modulo = model;
                        selected_key = getSnapshots().getSnapshot(position).getKey();
                        Log.d("Key item", "" + selected_key);

                        edit_nombre.setText(model.getNombre());
                        edit_curso.setText(model.getCurso());
                    }
                });
            }

            @NonNull
            @Override
            public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_recycler, parent, false);

                return new MyRecyclerViewHolder(view);
            }
        };

        adapter.startListening();
        recycler_view.setAdapter(adapter);
    }

    @Override
    public void onDataSingleSelecter(String s) {
        respuesta = s;
    }
}

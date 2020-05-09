package com.example.fundacionvalora02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fundacionvalora02.recycler_main.MyRecyclerViewHolder;
import com.example.fundacionvalora02.recycler_main.itemClickListener;
import com.example.fundacionvalora02.utils.Modulo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    EditText edit_nombre, edit_curso, edit_id;
    Button button_crear, button_eliminar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerOptions<Modulo> options;
    FirebaseRecyclerAdapter<Modulo, MyRecyclerViewHolder> adapter;

    Modulo selected_modulo;
    String selected_key_modulo;

    Toolbar toolbar;

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
        edit_id = findViewById(R.id.edit_id);
        button_crear = findViewById(R.id.button_crear);
        button_eliminar = findViewById(R.id.button_eliminar);
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("modulos");

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
                if (!edit_nombre.getText().toString().matches("") && !edit_curso.getText().toString().matches("") && !edit_id.getText().toString().matches("")) {
                    crearModulo();
                } else {
                    Toast.makeText(getApplicationContext(), "Debe introducir todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_modulo != null) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("Confirmar eliminación");
                    dialog.setMessage("¿Seguro que quieres continuar?");
                    dialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseReference.child(selected_key_modulo).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.create();
                    dialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Debe seleccionar un módulo", Toast.LENGTH_SHORT).show();
                }

            }
        });

        selected_key_modulo = "null";
        selected_modulo = null;
        displayModulo();
    }


    private void crearModulo() {
        String nombre = edit_nombre.getText().toString();
        String curso = edit_curso.getText().toString();
        String id = edit_id.getText().toString();

        Modulo modulo = new Modulo(nombre, curso, id);

        databaseReference.push().setValue(modulo);

        adapter.notifyDataSetChanged();
    }

    private void displayModulo() {
        options = new FirebaseRecyclerOptions.Builder<Modulo>().setQuery(databaseReference, Modulo.class).build();
        adapter = new FirebaseRecyclerAdapter<Modulo, MyRecyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyRecyclerViewHolder holder, int position, @NonNull final Modulo model) {
                holder.text_item_nombre.setText(model.getNombre());
                holder.text_item_curso.setText(model.getCurso());

                holder.setItemClickListener(new itemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("¿Qué quieres hacer?");
                        builder.setMessage("Si-Ver alumnos del módulo \nNo-Continuar en esta pantalla");
                        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                                intent.putExtra(String.valueOf(R.string.TAG_SELECTED_KEY_MODULO), selected_key_modulo);
                                intent.putExtra(String.valueOf(R.string.TAG_SELECTED_MODULO), selected_modulo);
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.create();
                        builder.show();

                        selected_modulo = model;
                        selected_key_modulo = getSnapshots().getSnapshot(position).getKey();
                        Log.d("Key item", "" + selected_key_modulo);

                        edit_nombre.setText(model.getNombre());
                        edit_curso.setText(model.getCurso());
                        edit_id.setText(model.getId());
                    }
                });
            }

            @NonNull
            @Override
            public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_recycler_main, parent, false);

                return new MyRecyclerViewHolder(view);
            }
        };

        adapter.startListening();
        recycler_view.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout_main:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;
        }

        return true;
    }
}

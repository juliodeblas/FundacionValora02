package com.example.fundacionvalora02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.fundacionvalora02.dialogos.DialogoSpinner;
import com.example.fundacionvalora02.recycler_main.MyRecyclerViewHolder;
import com.example.fundacionvalora02.recycler_main.itemClickListener;
import com.example.fundacionvalora02.utils.Alumno;
import com.example.fundacionvalora02.utils.Modulo;
import com.example.fundacionvalora02.utils.SemaforoSaberEstar;
import com.example.fundacionvalora02.utils.SemaforoSaberHacer;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements DialogoSpinner.OnDialogoSpinnerListener {

    RecyclerView recycler_view;
    EditText edit_nombre, edit_curso;
    Button button_crear;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;

    FirebaseRecyclerOptions<Modulo> options;
    FirebaseRecyclerAdapter<Modulo, MyRecyclerViewHolder> adapter;

    public static Modulo selected_modulo;
    public static String selected_key_modulo;

    int i = 0;
    String letters = "abcdefghijklmnopqrstuvwxyz";
    char[] alfanumerico = (letters + letters.toUpperCase() + "0123456789").toCharArray();

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instancias();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull final DataSnapshot snapshot) {
                final Modulo modulo = snapshot.getValue(Modulo.class);

                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            Alumno alumno = item.getValue(Alumno.class);

                            if (modulo.getId().equals(alumno.getId_modulo())) {
                                String key = item.getKey();
                                databaseReference1.child(key).removeValue();
                            }
                        }
                        return;

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                databaseReference1.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        final Alumno alumno = dataSnapshot.getValue(Alumno.class);

                        databaseReference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot item : dataSnapshot.getChildren()) {
                                    SemaforoSaberHacer semaforo = item.getValue(SemaforoSaberHacer.class);

                                    if (alumno.getId().equals(semaforo.getId_alumno())) {
                                        String key = item.getKey();
                                        databaseReference2.child(key).removeValue();
                                    }
                                }
                                return;

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        databaseReference3.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot item : dataSnapshot.getChildren()) {
                                    SemaforoSaberEstar semaforo = item.getValue(SemaforoSaberEstar.class);

                                    if (alumno.getId().equals(semaforo.getId_alumno())) {
                                        String key = item.getKey();
                                        databaseReference3.child(key).removeValue();
                                    }
                                }
                                return;

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void instancias() {
        recycler_view = findViewById(R.id.recycler_main);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        edit_nombre = findViewById(R.id.edit_nombre);
        edit_curso = findViewById(R.id.edit_curso);
        button_crear = findViewById(R.id.button_crear);
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("modulos");
        databaseReference1 = firebaseDatabase.getReference("alumnos");
        databaseReference2 = firebaseDatabase.getReference("semaforos_saber_hacer");
        databaseReference3 = firebaseDatabase.getReference("semaforos_saber_estar");

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
                if (!edit_nombre.getText().toString().matches("") && !edit_curso.getText().toString().matches("")) {
                    if (adapter.getItemCount() > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Elija una opción.");
                        builder.setMessage("¿Quiere importar alumnos de otros módulos?");
                        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DialogoSpinner dialogoSpinner = new DialogoSpinner();
                                dialogoSpinner.show(getSupportFragmentManager(), "spinner");
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                crearModulo();
                            }
                        });
                        builder.create();
                        builder.show();
                    } else {
                        crearModulo();
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Debe introducir todos los datos.", Toast.LENGTH_SHORT).show();
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
        String id = generadorAlfanumerico(10);

        Modulo modulo = new Modulo(nombre, curso, id);

        databaseReference.push().setValue(modulo);

        adapter.notifyDataSetChanged();

        edit_curso.setText("");
        edit_nombre.setText("");
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
                        builder.setMessage("CONTINUAR - Ver alumnos del módulo \nELIMINAR - Eliminar módulo\nCANCEL - Continuar en esta pantalla");
                        builder.setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                                intent.putExtra(String.valueOf(R.string.TAG_SELECTED_KEY_MODULO), selected_key_modulo);
                                intent.putExtra(String.valueOf(R.string.TAG_SELECTED_MODULO), selected_modulo);
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("ELIMINAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                                builder2.setTitle("Confirmar eliminación.");
                                builder2.setMessage("¿Seguro que quieres continuar?");
                                builder2.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlertDialog.Builder dialog1 = new AlertDialog.Builder(MainActivity.this);
                                        dialog1.setTitle("¿Estás realmente seguro que quieres continuar?");
                                        dialog1.setMessage("Se eliminarán también los alumnos y semáforos asociados al módulo.");
                                        dialog1.setPositiveButton("SI", new DialogInterface.OnClickListener() {
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

                                        dialog1.create();
                                        dialog1.show();
                                    }
                                });
                                builder2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder2.create();
                                builder2.show();
                                adapter.notifyDataSetChanged();
                            }
                        });
                        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.create();
                        builder.show();

                        selected_modulo = model;
                        selected_key_modulo = getSnapshots().getSnapshot(position).getKey();
                        Log.d("Key item", "" + selected_key_modulo);

                        edit_nombre.setText("");
                        edit_curso.setText("");
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
        switch (item.getItemId()) {
            case R.id.menu_logout_main:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;
            case R.id.menu_calendario_main:
                Intent intent2 = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent2);
                break;
        }

        return true;
    }

    public String generadorAlfanumerico(int length) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            builder.append(alfanumerico[new Random().nextInt(alfanumerico.length)]);
        }

        return builder.toString();
    }

    @Override
    public void onSpinnerSelected(Modulo modulo) {
        String nombre = edit_nombre.getText().toString();
        String curso = edit_curso.getText().toString();
        String id = generadorAlfanumerico(10);

        Modulo modulo1 = new Modulo(nombre, curso, id);

        databaseReference.push().setValue(modulo1);

        adapter.notifyDataSetChanged();

        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Alumno alumno = item.getValue(Alumno.class);
                    if (alumno.getId_modulo().equals(modulo.getId())) {
                        String nombre = alumno.getNombre();
                        String apellidos = alumno.getApellidos();
                        String numero = alumno.getNumero_orden();
                        String perfil = alumno.getPerfil();
                        String grupo = alumno.getGrupo();
                        String id_modulo = id;
                        String id = generadorAlfanumerico(10);
                        Calendar calendar = Calendar.getInstance();
                        String fecha = DateFormat.getDateInstance().format(calendar.getTime());

                        Alumno alumno1 = new Alumno(nombre, apellidos, numero, perfil, grupo, id_modulo, id);
                        SemaforoSaberHacer semaforoSaberHacer = new SemaforoSaberHacer(0, 0, 0, 0, 0, id, fecha);
                        SemaforoSaberEstar semaforoSaberEstar = new SemaforoSaberEstar(0, 0, 0, 0, 0, id, fecha);

                        databaseReference1.push().setValue(alumno1);
                        databaseReference2.push().setValue(semaforoSaberHacer);
                        databaseReference3.push().setValue(semaforoSaberEstar);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        edit_curso.setText("");
        edit_nombre.setText("");

        adapter.notifyDataSetChanged();
    }
}

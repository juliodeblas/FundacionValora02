package com.example.fundacionvalora02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.fundacionvalora02.dialogos.DialogoAlumnoActualizar;
import com.example.fundacionvalora02.dialogos.DialogoAlumnoCrear;
import com.example.fundacionvalora02.recycler_second.MyRecyclerViewHolder2;
import com.example.fundacionvalora02.recycler_second.itemClickListener2;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity implements DialogoAlumnoCrear.OnDialogoPersoListener, DialogoAlumnoActualizar.OnDialogoPersoListener {

    Bundle bundle;
    TextView text_alumnos_modulo;
    RecyclerView recycler_alumnos;
    Button button_crear, button_actualizar;
    Toolbar toolbar;

    public static String selected_key_modulo;
    public static Modulo selected_modulo;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    Query query;


    FirebaseRecyclerOptions<Alumno> options;
    FirebaseRecyclerAdapter<Alumno, MyRecyclerViewHolder2> adapter;

    public static Alumno selected_alumno;
    public static String selected_key_alumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        bundle = getIntent().getExtras();
        instancias();
        acciones();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                final Alumno alumno = dataSnapshot.getValue(Alumno.class);

                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            SemaforoSaberHacer semaforo = item.getValue(SemaforoSaberHacer.class);

                            if (alumno.getId().equals(semaforo.getId_alumno())) {
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

                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            SemaforoSaberEstar semaforo = item.getValue(SemaforoSaberEstar.class);

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
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void acciones() {
        button_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoAlumnoCrear dialogoAlumno = new DialogoAlumnoCrear();
                dialogoAlumno.show(getSupportFragmentManager(), "perso");
            }
        });
        button_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_alumno != null) {
                    DialogoAlumnoActualizar dialogoAlumno = new DialogoAlumnoActualizar();
                    dialogoAlumno.show(getSupportFragmentManager(), "perso");
                } else {
                    Toast.makeText(getApplicationContext(), "Debe seleccionar un alumno.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selected_key_alumno = "null";
        selected_alumno = null;
        displayAlumno();
    }

    private void instancias() {
        text_alumnos_modulo = findViewById(R.id.text_alumnos_modulo);
        recycler_alumnos = findViewById(R.id.recycler_second);
        recycler_alumnos.setLayoutManager(new LinearLayoutManager(this));
        button_crear = findViewById(R.id.button_crear_alumno);
        button_actualizar = findViewById(R.id.button_actualizar_alumno);

        toolbar = findViewById(R.id.toolbar_second);
        setSupportActionBar(toolbar);

        if (bundle != null) {
            selected_key_modulo = bundle.getString(String.valueOf(R.string.TAG_SELECTED_KEY_MODULO));
            selected_modulo = (Modulo) bundle.getSerializable(String.valueOf(R.string.TAG_SELECTED_MODULO));
        }
        text_alumnos_modulo.setText(text_alumnos_modulo.getText() + selected_modulo.getNombre() + " " + selected_modulo.getCurso() + "\n(Código: " + selected_modulo.getId() + ")");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("alumnos");
        query = databaseReference.orderByChild("id_modulo").equalTo(selected_modulo.getId());
        databaseReference1 = firebaseDatabase.getReference().child("semaforos_saber_hacer");
        databaseReference2 = firebaseDatabase.getReference().child("semaforos_saber_estar");

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
        options = new FirebaseRecyclerOptions.Builder<Alumno>().setQuery(query, Alumno.class).build();
        adapter = new FirebaseRecyclerAdapter<Alumno, MyRecyclerViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder2 holder, int position, @NonNull final Alumno model) {
                holder.text_item_nombre.setText(model.getNombre());
                holder.text_item_apellidos.setText(model.getApellidos());
                holder.text_item_numero.setText(model.getNumero_orden());


                holder.setItemClickListener2(new itemClickListener2() {
                    @Override
                    public void onClick(View view, int position) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
                        builder.setTitle("¿Qué quieres hacer?");
                        builder.setMessage("CONTINUAR - Ver detalles del alumno \nELIMINAR - Eliminar alumno\nCANCEL - Continuar en esta pantalla");
                        builder.setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(SecondActivity.this, DetalleAlumnoActivity.class);
                                intent.putExtra(String.valueOf(R.string.TAG_SELECTED_KEY_ALUMNO), selected_key_alumno);
                                intent.putExtra(String.valueOf(R.string.TAG_SELECTED_ALUMNO), selected_alumno);
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("ELIMINAR", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (selected_alumno != null) {
                                    AlertDialog.Builder dialogo = new AlertDialog.Builder(SecondActivity.this);
                                    dialogo.setTitle("Confirmar eliminación.");
                                    dialogo.setMessage("¿Seguro que quieres continuar?");
                                    dialogo.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            AlertDialog.Builder dialog1 = new AlertDialog.Builder(SecondActivity.this);
                                            dialog1.setTitle("¿Estás realmente seguro que quieres continuar?");
                                            dialog1.setMessage("Se eliminarán también los semáforos asociados al alumno.");
                                            dialog1.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    databaseReference.child(selected_key_alumno).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(SecondActivity.this, "¡Borrado correctamente!", Toast.LENGTH_SHORT).show();


                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(SecondActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    dialogo.create();
                                    dialogo.show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Debe seleccionar un alumno.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
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
    public void onDialogoSelected(Alumno alumno, SemaforoSaberHacer semaforoSaberHacer, SemaforoSaberEstar semaforoSaberEstar) {
        databaseReference.push().setValue(alumno);
        databaseReference1.push().setValue(semaforoSaberHacer);
        databaseReference2.push().setValue(semaforoSaberEstar);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogoSelectedAct(Alumno alumno) {
        databaseReference.child(selected_key_alumno).setValue(alumno);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_others, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout_others:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SecondActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;
            case R.id.menu_home_others:
                Intent intent1 = new Intent(SecondActivity.this, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);

                break;
            case R.id.menu_calendario_others:
                Intent intent2 = new Intent(SecondActivity.this, CalendarActivity.class);
                startActivity(intent2);
                break;
        }

        return true;
    }
}

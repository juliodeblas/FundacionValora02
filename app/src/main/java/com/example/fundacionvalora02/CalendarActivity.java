package com.example.fundacionvalora02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.fundacionvalora02.dialogos.DialogoEventoCrear;
import com.example.fundacionvalora02.recycler_calendar.MyRecyclerViewHolder3;
import com.example.fundacionvalora02.recycler_calendar.itemClickListener3;
import com.example.fundacionvalora02.utils.Evento;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity implements DialogoEventoCrear.OnDialogoPersoListener {

    CalendarView calendarView;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseRecyclerOptions<Evento> options;
    FirebaseRecyclerAdapter<Evento, MyRecyclerViewHolder3> adapter;
    Toolbar toolbar;
    List<EventDay> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        instancias();
        acciones();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Evento evento1 = item.getValue(Evento.class);
                    Date date = new Date(evento1.getTime());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    events.add(new EventDay(calendar, R.drawable.chincheta));
                    calendarView.setEvents(events);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

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
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clicked_day_calendar = eventDay.getCalendar();
                DialogoEventoCrear dialogoEventoCrear = new DialogoEventoCrear(clicked_day_calendar);
                dialogoEventoCrear.show(getSupportFragmentManager(), "dialogo");
            }
        });
    }

    private void instancias() {
        events = new ArrayList<>();
        calendarView = findViewById(R.id.calendar);
        calendarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        recyclerView = findViewById(R.id.recycler_calendario);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.toolbar_calendario);
        setSupportActionBar(toolbar);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("eventos");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayEventos();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        if (adapter != null) {
            adapter.stopListening();
        }
        super.onStop();
    }

    private void displayEventos() {
        options = new FirebaseRecyclerOptions.Builder<Evento>().setQuery(reference, Evento.class).build();
        adapter = new FirebaseRecyclerAdapter<Evento, MyRecyclerViewHolder3>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder3 holder, int position, @NonNull Evento model) {
                holder.text_nombre.setText(model.getTituloEvento());
                holder.text_fecha.setText(model.mostrarFecha());

                holder.setListener3(new itemClickListener3() {
                    @Override
                    public void onClick(View view, int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);
                        builder.setTitle("Evento");
                        builder.setMessage("¿Qué quiere hacer con este evento?");
                        builder.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(CalendarActivity.this);
                                builder1.setTitle("¿Estás realmente seguro que quieres continuar?");
                                builder1.setMessage("Se eliminará el evento definitivamente.");
                                builder1.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot item : dataSnapshot.getChildren()) {
                                                    Evento evento = item.getValue(Evento.class);
                                                    if (evento.getId().equals(model.getId())) {
                                                        String key = item.getKey();
                                                        reference.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(CalendarActivity.this, "¡Borrado correctamente!", Toast.LENGTH_SHORT).show();
                                                                finish();
                                                                startActivity(getIntent());
                                                            }
                                                        });
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder1.create();
                                builder1.show();
                            }
                        }).setNegativeButton("VER DETALLES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(CalendarActivity.this);
                                builder1.setTitle("Detalles del evento: " + model.getTituloEvento());
                                builder1.setMessage(model.getDescripcionEvento());
                                builder1.setPositiveButton("SALIR", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder1.create();
                                builder1.show();
                            }
                        }).setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create();
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public MyRecyclerViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_recycler_calendar, parent, false);
                return new MyRecyclerViewHolder3(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDialogoSelected(Evento evento) {
        reference.push().setValue(evento);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_calendar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout_calendar:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CalendarActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;
            case R.id.menu_home_calendar:
                Intent intent1 = new Intent(CalendarActivity.this, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);

                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        
    }
}

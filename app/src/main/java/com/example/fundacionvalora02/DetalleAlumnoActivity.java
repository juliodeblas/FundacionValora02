package com.example.fundacionvalora02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.fundacionvalora02.fragments.AdaptadorFragments;
import com.example.fundacionvalora02.fragments.FragmentDos;
import com.example.fundacionvalora02.fragments.FragmentTres;
import com.example.fundacionvalora02.fragments.FragmentUno;
import com.example.fundacionvalora02.utils.Alumno;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DetalleAlumnoActivity extends AppCompatActivity {

    Bundle bundle;
    ViewPager viewPager;
    TabLayout tabLayout;
    AdaptadorFragments adaptadorFragments;
    ArrayList<Fragment> listaFragments;
    Toolbar toolbar;

    public static Alumno selected_alumno;
    public static String selected_key_alumno;

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference reference1;

    FragmentUno fragmentUno;
    FragmentDos fragmentDos;
    FragmentTres fragmentTres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_alumno);
        bundle = getIntent().getExtras();
        instancias();
        iniciarPager();
        acciones();
    }

    private void acciones() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.v("scroll", String.valueOf(position));
                Fragment fragment = adaptadorFragments.getItem(position);
                Drawable drawable = fragment.getView().findViewById(R.id.fondo).getBackground();
                tabLayout.setBackground(drawable);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.getCurrentItem();
    }

    private void iniciarPager() {
        fragmentUno = new FragmentUno();
        fragmentDos = new FragmentDos();
        fragmentTres = new FragmentTres();
        listaFragments = new ArrayList<>();
        fragmentUno.setArguments(bundle);
        fragmentDos.setArguments(bundle);
        fragmentTres.setArguments(bundle);
        listaFragments.add(fragmentUno);
        listaFragments.add(fragmentDos);
        listaFragments.add(fragmentTres);
        adaptadorFragments = new AdaptadorFragments(getSupportFragmentManager(),  listaFragments);
        viewPager.setAdapter(adaptadorFragments);
    }

    private void instancias() {
        if (bundle != null) {
            selected_key_alumno = bundle.getString(String.valueOf(R.string.TAG_SELECTED_KEY_ALUMNO));
            selected_alumno = (Alumno) bundle.getSerializable(String.valueOf(R.string.TAG_SELECTED_ALUMNO));
        }
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        toolbar = findViewById(R.id.toolbar_detalle);
        setSupportActionBar(toolbar);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("semaforos_saber_hacer");
        reference1 = database.getReference("semaforos_saber_estar");
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
                Intent intent = new Intent(DetalleAlumnoActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;
            case R.id.menu_home_others:
                Intent intent1 = new Intent(DetalleAlumnoActivity.this, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);

                break;
            case R.id.menu_calendario_others:
                Intent intent2 = new Intent(DetalleAlumnoActivity.this, CalendarActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
}

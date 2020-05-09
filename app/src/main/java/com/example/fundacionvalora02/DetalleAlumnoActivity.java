package com.example.fundacionvalora02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.example.fundacionvalora02.fragments.AdaptadorFragments;
import com.example.fundacionvalora02.fragments.FragmentDos;
import com.example.fundacionvalora02.fragments.FragmentTres;
import com.example.fundacionvalora02.fragments.FragmentUno;
import com.example.fundacionvalora02.utils.Alumno;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DetalleAlumnoActivity extends AppCompatActivity {

    Bundle bundle;
    ViewPager viewPager;
    TabLayout tabLayout;
    AdaptadorFragments adaptadorFragments;
    ArrayList<Fragment> listaFragments;

    public static Alumno selected_alumno;
    public static String selected_key_alumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_alumno);
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
        listaFragments = new ArrayList<>();
        listaFragments.add(new FragmentUno());
        listaFragments.add(new FragmentDos());
        listaFragments.add(new FragmentTres());
        adaptadorFragments = new AdaptadorFragments(getSupportFragmentManager(), 0, listaFragments);
        viewPager.setAdapter(adaptadorFragments);
    }

    private void instancias() {
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        if (bundle != null) {
            selected_key_alumno = bundle.getString(String.valueOf(R.string.TAG_SELECTED_KEY_ALUMNO));
            selected_alumno = (Alumno) bundle.getSerializable(String.valueOf(R.string.TAG_SELECTED_ALUMNO));
        }
    }
}

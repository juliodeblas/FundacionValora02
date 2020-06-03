package com.example.fundacionvalora02.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class AdaptadorFragments extends FragmentPagerAdapter {

    private ArrayList<Fragment> lista_fragments;
    private String[] nombres = new String[]{"Detalles", "Saber Hacer", "Saber estar"};

    public AdaptadorFragments(@NonNull FragmentManager fm, ArrayList<Fragment> lista_fragments) {
        super(fm);
        this.lista_fragments = lista_fragments;
    }

    public void agregarFragment(Fragment fragment) {
        this.lista_fragments.add(fragment);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lista_fragments.get(position);
    }

    @Override
    public int getCount() {
        return lista_fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return nombres[position];
    }
}

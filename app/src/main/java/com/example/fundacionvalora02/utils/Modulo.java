package com.example.fundacionvalora02.utils;

import java.util.ArrayList;

public class Modulo {

    String nombre, curso;
    ArrayList<Alumno> lista_alumnos;

    public Modulo() {
    }

    public Modulo(String nombre, String curso, ArrayList<Alumno> lista_alumnos) {
        this.nombre = nombre;
        this.curso = curso;
        this.lista_alumnos = lista_alumnos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public ArrayList<Alumno> getLista_alumnos() {
        return lista_alumnos;
    }

    public void setLista_alumnos(ArrayList<Alumno> lista_alumnos) {
        this.lista_alumnos = lista_alumnos;
    }
}

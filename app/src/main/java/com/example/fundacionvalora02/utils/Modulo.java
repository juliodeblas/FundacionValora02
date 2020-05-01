package com.example.fundacionvalora02.utils;

import java.io.Serializable;
import java.util.ArrayList;

public class Modulo implements Serializable {

    String nombre, curso, id;

    public Modulo() {
    }

    public Modulo(String nombre, String curso, String id) {
        this.nombre = nombre;
        this.curso = curso;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

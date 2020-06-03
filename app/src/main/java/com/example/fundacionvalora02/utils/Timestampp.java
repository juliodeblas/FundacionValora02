package com.example.fundacionvalora02.utils;

public class Timestampp {

    String id_alumno, nombre, apellidos;
    int conseguido, en_proceso, no_conseguido, faltas, faltas_justificadas;

    public Timestampp() {
    }

    public Timestampp(String id_alumno, String nombre, String apellidos, int conseguido, int en_proceso, int no_conseguido, int faltas, int faltas_justificadas) {
        this.id_alumno = id_alumno;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.conseguido = conseguido;
        this.en_proceso = en_proceso;
        this.no_conseguido = no_conseguido;
        this.faltas = faltas;
        this.faltas_justificadas = faltas_justificadas;
    }

    public String getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(String id_alumno) {
        this.id_alumno = id_alumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getConseguido() {
        return conseguido;
    }

    public void setConseguido(int conseguido) {
        this.conseguido = conseguido;
    }

    public int getEn_proceso() {
        return en_proceso;
    }

    public void setEn_proceso(int en_proceso) {
        this.en_proceso = en_proceso;
    }

    public int getNo_conseguido() {
        return no_conseguido;
    }

    public void setNo_conseguido(int no_conseguido) {
        this.no_conseguido = no_conseguido;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getFaltas_justificadas() {
        return faltas_justificadas;
    }

    public void setFaltas_justificadas(int faltas_justificadas) {
        this.faltas_justificadas = faltas_justificadas;
    }
}

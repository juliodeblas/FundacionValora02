package com.example.fundacionvalora02.utils;

import java.io.Serializable;

public class Alumno implements Serializable {

    String nombre, apellidos, numero_orden, perfil, grupo, id_modulo;

    public Alumno() {
    }

    public Alumno(String nombre, String apellidos, String numero_orden, String perfil, String grupo, String id_modulo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numero_orden = numero_orden;
        this.perfil = perfil;
        this.grupo = grupo;
        this.id_modulo = id_modulo;
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

    public String getNumero_orden() {
        return numero_orden;
    }

    public void setNumero_orden(String numero_orden) {
        this.numero_orden = numero_orden;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getId_modulo() {
        return id_modulo;
    }

    public void setId_modulo(String id_modulo) {
        this.id_modulo = id_modulo;
    }
}

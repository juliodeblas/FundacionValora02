package com.example.fundacionvalora02.utils;

import java.io.Serializable;

public class SemaforoSaberEstar implements Serializable {

    int no_conseguido, conseguido, en_proceso, falta, falta_justificada;
    String id_alumno;
    String fecha;

    public SemaforoSaberEstar() {
    }

    public SemaforoSaberEstar(int no_conseguido, int conseguido, int en_proceso, int falta, int falta_justificada, String id_alumno, String fecha) {
        this.no_conseguido = no_conseguido;
        this.conseguido = conseguido;
        this.en_proceso = en_proceso;
        this.falta = falta;
        this.falta_justificada = falta_justificada;
        this.id_alumno = id_alumno;
        this.fecha = fecha;
    }

    public int getNo_conseguido() {
        return no_conseguido;
    }

    public void setNo_conseguido(int no_conseguido) {
        this.no_conseguido = no_conseguido;
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

    public int getFalta() {
        return falta;
    }

    public void setFalta(int falta) {
        this.falta = falta;
    }

    public int getFalta_justificada() {
        return falta_justificada;
    }

    public void setFalta_justificada(int falta_justificada) {
        this.falta_justificada = falta_justificada;
    }

    public String getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(String id_alumno) {
        this.id_alumno = id_alumno;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

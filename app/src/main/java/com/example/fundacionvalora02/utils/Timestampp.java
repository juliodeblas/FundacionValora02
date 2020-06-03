package com.example.fundacionvalora02.utils;

public class Timestampp {

    String id_modulo;
    int conseguido, en_proceso, no_conseguido, faltas, faltas_justificadas;

    public Timestampp() {
    }

    public Timestampp(String id_modulo, int conseguido, int en_proceso, int no_conseguido, int faltas, int faltas_justificadas) {
        this.id_modulo = id_modulo;
        this.conseguido = conseguido;
        this.en_proceso = en_proceso;
        this.no_conseguido = no_conseguido;
        this.faltas = faltas;
        this.faltas_justificadas = faltas_justificadas;
    }

    public String getId_modulo() {
        return id_modulo;
    }

    public void setId_modulo(String id_modulo) {
        this.id_modulo = id_modulo;
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

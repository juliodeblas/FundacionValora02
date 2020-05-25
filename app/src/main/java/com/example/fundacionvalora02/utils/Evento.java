package com.example.fundacionvalora02.utils;

public class Evento {
    private String id;
    private String tituloEvento;
    private String descripcionEvento;
    private String diaSemana;
    private String diaMes;
    private String mes;
    private String año;

    public Evento(String id, String tituloEvento, String descripcionEvento, String diaSemana, String diaMes, String mes, String año) {
        this.id = id;
        this.tituloEvento = tituloEvento;
        this.descripcionEvento = descripcionEvento;
        this.diaSemana = diaSemana;
        this.diaMes = diaMes;
        this.mes = mes;
        this.año = año;
    }

    public Evento(String diaSemana, String diaMes, String mes, String año) {
        this.diaSemana = diaSemana;
        this.diaMes = diaMes;
        this.mes = mes;
        this.año = año;
    }


    public Evento() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTituloEvento() {
        return tituloEvento;
    }

    public void setTituloEvento(String tituloEvento) {
        this.tituloEvento = tituloEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getDiaMes() {
        return diaMes;
    }

    public void setDiaMes(String diaMes) {
        this.diaMes = diaMes;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "diaSemana='" + diaSemana + '\'' +
                ", diaMes=" + diaMes +
                ", mes=" + mes +
                ", año=" + año;
    }

    public String mostrarFecha() {
        return diaMes + "/" + mes + "/" + año;
    }
}

package com.vistony.salesforce.Entity.SQLite;

public class ZonaSQLiteEntity {
    public String zona_id;
    public String compania_id;
    public String fuerzatrabajo_id;
    public String disemana;

    public ZonaSQLiteEntity(String zona_id, String compania_id, String fuerzatrabajo_id, String disemana) {
        this.zona_id = zona_id;
        this.compania_id = compania_id;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.disemana = disemana;
    }

    public ZonaSQLiteEntity() {
    }

    public String getZona_id() {
        return zona_id;
    }

    public void setZona_id(String zona_id) {
        this.zona_id = zona_id;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    public String getDisemana() {
        return disemana;
    }

    public void setDisemana(String disemana) {
        this.disemana = disemana;
    }
}

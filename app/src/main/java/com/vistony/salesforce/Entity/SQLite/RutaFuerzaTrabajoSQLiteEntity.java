package com.vistony.salesforce.Entity.SQLite;

public class RutaFuerzaTrabajoSQLiteEntity {
    public String compania_id;
    public String zona_id;
    public String zona;
    public String dia;
    public String frecuencia;
    public String fechainicioruta;
    public String estado;


    public RutaFuerzaTrabajoSQLiteEntity(String compania_id, String zona_id, String zona, String dia, String frecuencia, String fechainicioruta, String estado) {
        this.compania_id = compania_id;
        this.zona_id = zona_id;
        this.zona = zona;
        this.dia = dia;
        this.frecuencia = frecuencia;
        this.fechainicioruta = fechainicioruta;
        this.estado = estado;
    }

    public RutaFuerzaTrabajoSQLiteEntity() {
    }

    public String getFechainicioruta() {
        return fechainicioruta;
    }

    public void setFechainicioruta(String fechainicioruta) {
        this.fechainicioruta = fechainicioruta;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getZona_id() {
        return zona_id;
    }

    public void setZona_id(String zona_id) {
        this.zona_id = zona_id;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

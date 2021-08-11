package com.vistony.salesforce.Entity.SQLite;

public class ParametrosSQLiteEntity {
    public String id;
    public String nombreparametro;
    public String cantidadregistros;
    public String fechacarga;
    public String hash;


    public ParametrosSQLiteEntity() {
    }

    public ParametrosSQLiteEntity(String id, String nombreparametro, String cantidadregistros, String fechacarga, String hash) {
        this.id = id;
        this.nombreparametro = nombreparametro;
        this.cantidadregistros = cantidadregistros;
        this.fechacarga = fechacarga;
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreparametro() {
        return nombreparametro;
    }

    public void setNombreparametro(String nombreparametro) {
        this.nombreparametro = nombreparametro;
    }

    public String getCantidadregistros() {
        return cantidadregistros;
    }

    public void setCantidadregistros(String cantidadregistros) {
        this.cantidadregistros = cantidadregistros;
    }

    public String getFechacarga() {
        return fechacarga;
    }

    public void setFechacarga(String fechacarga) {
        this.fechacarga = fechacarga;
    }
}

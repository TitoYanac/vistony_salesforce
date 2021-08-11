package com.vistony.salesforce.Entity.SQLite;

public class CatalogoSQLiteEntity {
    public String compania_id;
    public String catalogo_id;
    public String catalogo;
    public String ruta;

    public CatalogoSQLiteEntity(String compania_id, String catalogo_id, String catalogo, String ruta) {
        this.compania_id = compania_id;
        this.catalogo_id = catalogo_id;
        this.catalogo = catalogo;
        this.ruta = ruta;
    }

    public CatalogoSQLiteEntity() {
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getCatalogo_id() {
        return catalogo_id;
    }

    public void setCatalogo_id(String catalogo_id) {
        this.catalogo_id = catalogo_id;
    }

    public String getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(String catalogo) {
        this.catalogo = catalogo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}

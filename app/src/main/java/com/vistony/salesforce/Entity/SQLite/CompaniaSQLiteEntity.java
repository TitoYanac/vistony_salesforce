package com.vistony.salesforce.Entity.SQLite;

public class CompaniaSQLiteEntity {
    public String compania_id;
    public String nombrecompania;

    public CompaniaSQLiteEntity(String compania_id, String nombrecompania) {
        this.compania_id = compania_id;
        this.nombrecompania = nombrecompania;
    }

    public CompaniaSQLiteEntity() {
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getNombrecompania() {
        return nombrecompania;
    }

    public void setNombrecompania(String nombrecompania) {
        this.nombrecompania = nombrecompania;
    }
}


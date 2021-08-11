package com.vistony.salesforce.Entity.SQLite;

public class CobradorSQLiteEntity {

    public String cobrador_id;
    public String compania_id;
    public String nombrecobrador;

    public CobradorSQLiteEntity(String cobrador_id, String compania_id, String nombrecobrador) {
        this.cobrador_id = cobrador_id;
        this.compania_id = compania_id;
        this.nombrecobrador = nombrecobrador;
    }

    public CobradorSQLiteEntity() {
    }

    public String getCobrador_id() {
        return cobrador_id;
    }

    public void setCobrador_id(String cobrador_id) {
        this.cobrador_id = cobrador_id;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getNombrecobrador() {
        return nombrecobrador;
    }

    public void setNombrecobrador(String nombrecobrador) {
        this.nombrecobrador = nombrecobrador;
    }
}

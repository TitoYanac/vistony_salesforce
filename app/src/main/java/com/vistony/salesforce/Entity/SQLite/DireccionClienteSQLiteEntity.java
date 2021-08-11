package com.vistony.salesforce.Entity.SQLite;

public class DireccionClienteSQLiteEntity {
    public String compania_id;
    public String cliente_id;
    public String domembarque_id;
    public String direccion;
    public String zona_id;
    public String zona;
    public String fuerzatrabajo_id;
    public String nombrefuerzatrabajo;


    public DireccionClienteSQLiteEntity(String compania_id, String cliente_id, String domembarque_id, String direccion, String zona_id, String zona, String fuerzatrabajo_id, String nombrefuerzatrabajo) {
        this.compania_id = compania_id;
        this.cliente_id = cliente_id;
        this.domembarque_id = domembarque_id;
        this.direccion = direccion;
        this.zona_id = zona_id;
        this.zona = zona;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.nombrefuerzatrabajo = nombrefuerzatrabajo;
    }

    public DireccionClienteSQLiteEntity() {
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getDomembarque_id() {
        return domembarque_id;
    }

    public void setDomembarque_id(String domembarque_id) {
        this.domembarque_id = domembarque_id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    public String getNombrefuerzatrabajo() {
        return nombrefuerzatrabajo;
    }

    public void setNombrefuerzatrabajo(String nombrefuerzatrabajo) {
        this.nombrefuerzatrabajo = nombrefuerzatrabajo;
    }
}

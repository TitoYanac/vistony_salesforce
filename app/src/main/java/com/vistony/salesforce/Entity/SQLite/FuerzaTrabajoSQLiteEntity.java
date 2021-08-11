package com.vistony.salesforce.Entity.SQLite;

public class FuerzaTrabajoSQLiteEntity {
    public String fuerzatrabajo_id;
    public String compania_id;
    public String nombrefuerzatrabajo;

    public FuerzaTrabajoSQLiteEntity(String fuerzatrabajo_id, String compania_id, String nombrefuerzatrabajo) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.compania_id = compania_id;
        this.nombrefuerzatrabajo = nombrefuerzatrabajo;
    }

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getNombrefuerzatrabajo() {
        return nombrefuerzatrabajo;
    }

    public void setNombrefuerzatrabajo(String nombrefuerzatrabajo) {
        this.nombrefuerzatrabajo = nombrefuerzatrabajo;
    }
}

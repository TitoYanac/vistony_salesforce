package com.vistony.salesforce.Entity.SQLite;

public class MotivoVisitaSQLiteEntity {
    public String compania_id;
    public String fuerzatrabajo_id;
    public String usuario_id;
    public String code;
    public String name;
    public String type;

    public MotivoVisitaSQLiteEntity(String compania_id, String fuerzatrabajo_id, String usuario_id, String code, String name, String type) {
        this.compania_id = compania_id;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        this.code = code;
        this.name = name;
        this.type = type;
    }

    public MotivoVisitaSQLiteEntity() {
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

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

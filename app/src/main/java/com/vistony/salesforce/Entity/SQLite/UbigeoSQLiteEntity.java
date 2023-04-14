package com.vistony.salesforce.Entity.SQLite;

public class UbigeoSQLiteEntity {
    public String compania_id;
    public String fuerzatrabajo_id;
    public String usuario_id;
    public String code;
    public String U_SYP_DEPA;
    public String U_SYP_PROV;
    public String U_SYP_DIST;
    public String U_VIS_Flete;

    public UbigeoSQLiteEntity(String compania_id, String fuerzatrabajo_id, String usuario_id, String code, String u_SYP_DEPA, String u_SYP_PROV, String u_SYP_DIST, String u_VIS_Flete) {
        this.compania_id = compania_id;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        this.code = code;
        U_SYP_DEPA = u_SYP_DEPA;
        U_SYP_PROV = u_SYP_PROV;
        U_SYP_DIST = u_SYP_DIST;
        U_VIS_Flete = u_VIS_Flete;
    }

    public UbigeoSQLiteEntity() {

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

    public String getU_SYP_DEPA() {
        return U_SYP_DEPA;
    }

    public void setU_SYP_DEPA(String u_SYP_DEPA) {
        U_SYP_DEPA = u_SYP_DEPA;
    }

    public String getU_SYP_PROV() {
        return U_SYP_PROV;
    }

    public void setU_SYP_PROV(String u_SYP_PROV) {
        U_SYP_PROV = u_SYP_PROV;
    }

    public String getU_SYP_DIST() {
        return U_SYP_DIST;
    }

    public void setU_SYP_DIST(String u_SYP_DIST) {
        U_SYP_DIST = u_SYP_DIST;
    }

    public String getU_VIS_Flete() {
        return U_VIS_Flete;
    }

    public void setU_VIS_Flete(String u_VIS_Flete) {
        U_VIS_Flete = u_VIS_Flete;
    }
}

package com.vistony.salesforce.Entity.SQLite;

public class ListaPrecioDetalleSQLiteEntity {
    public String compania_id;
    //public String listaprecio_id;
    public String contado;
    public String credito;
    public String producto_id;
    public String producto;
    public String umd;
    public String gal;
    public String typo;
    public String u_vis_cashdscnt;


    public ListaPrecioDetalleSQLiteEntity(String compania_id, String contado, String credito, String producto_id, String producto, String umd, String gal, String typo,String u_vis_cashdscnt) {
        this.compania_id = compania_id;
        this.contado = contado;
        this.credito = credito;
        this.producto_id = producto_id;
        this.producto = producto;
        this.umd = umd;
        this.gal = gal;
        this.typo = typo;
        this.u_vis_cashdscnt = u_vis_cashdscnt;
    }

    public ListaPrecioDetalleSQLiteEntity() {
    }

    public String getTypo() {
        return typo;
    }

    public void setTypo(String typo) {
        this.typo = typo;
    }

    public String getU_vis_cashdscnt() {
        return u_vis_cashdscnt;
    }

    public void setU_vis_cashdscnt(String u_vis_cashdscnt) {
        this.u_vis_cashdscnt = u_vis_cashdscnt;
    }

    public String getGal() {
        return gal;
    }

    public void setGal(String gal) {
        this.gal = gal;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getContado() {
        return contado;
    }

    public void setContado(String contado) {
        this.contado = contado;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(String producto_id) {
        this.producto_id = producto_id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getUmd() {
        return umd;
    }

    public void setUmd(String umd) {
        this.umd = umd;
    }

}

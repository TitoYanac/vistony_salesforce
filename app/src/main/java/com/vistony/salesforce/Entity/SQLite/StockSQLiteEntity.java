package com.vistony.salesforce.Entity.SQLite;

public class StockSQLiteEntity {
    public String compania_id;
    public String producto_id;
    public String producto;
    public String umd;
    public String stock;
    public String almacen_id;
    public String comprometido;
    public String enstock;
    public String pedido;

    public StockSQLiteEntity(String compania_id, String producto_id, String producto, String umd, String stock, String almacen_id, String comprometido, String enstock, String pedido) {
        this.compania_id = compania_id;
        this.producto_id = producto_id;
        this.producto = producto;
        this.umd = umd;
        this.stock = stock;
        this.almacen_id = almacen_id;
        this.comprometido = comprometido;
        this.enstock = enstock;
        this.pedido = pedido;
    }

    public StockSQLiteEntity() {
    }

    public String getComprometido() {
        return comprometido;
    }

    public void setComprometido(String comprometido) {
        this.comprometido = comprometido;
    }

    public String getEnstock() {
        return enstock;
    }

    public void setEnstock(String enstock) {
        this.enstock = enstock;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getAlmacen_id() {
        return almacen_id;
    }

    public void setAlmacen_id(String almacen_id) {
        this.almacen_id = almacen_id;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(String producto_id) {
        this.producto_id = producto_id;
    }

    public String getUmd() {
        return umd;
    }

    public void setUmd(String umd) {
        this.umd = umd;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}

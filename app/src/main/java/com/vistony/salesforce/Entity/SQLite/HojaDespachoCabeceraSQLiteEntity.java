package com.vistony.salesforce.Entity.SQLite;

public class HojaDespachoCabeceraSQLiteEntity {
    public String compania_id;
    public String fuerzatrabajo_id;
    public String usuario_id;
    public String control_id;
    public String asistente_id;
    public String asistente;
    public String placa;
    public String marca;
    public String pesototal;
    public String fechahojadespacho;

    public HojaDespachoCabeceraSQLiteEntity(String compania_id, String fuerzatrabajo_id, String usuario_id, String control_id, String asistente_id, String asistente, String placa, String marca, String pesototal, String fechahojadespacho) {
        this.compania_id = compania_id;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        this.control_id = control_id;
        this.asistente_id = asistente_id;
        this.asistente = asistente;
        this.placa = placa;
        this.marca = marca;
        this.pesototal = pesototal;
        this.fechahojadespacho = fechahojadespacho;
    }

    public HojaDespachoCabeceraSQLiteEntity() {

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

    public String getControl_id() {
        return control_id;
    }

    public void setControl_id(String control_id) {
        this.control_id = control_id;
    }

    public String getAsistente_id() {
        return asistente_id;
    }

    public void setAsistente_id(String asistente_id) {
        this.asistente_id = asistente_id;
    }

    public String getAsistente() {
        return asistente;
    }

    public void setAsistente(String asistente) {
        this.asistente = asistente;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPesototal() {
        return pesototal;
    }

    public void setPesototal(String pesototal) {
        this.pesototal = pesototal;
    }

    public String getFechahojadespacho() {
        return fechahojadespacho;
    }

    public void setFechahojadespacho(String fechahojadespacho) {
        this.fechahojadespacho = fechahojadespacho;
    }
}

package com.vistony.salesforce.Entity.Adapters;

public class DireccionCliente {
    public String cliente_id;
    public String domembarque_id;
    public String direccion;
    public String zona_id;
    public String zona;
    public String nombrefuerzatrabajo;
    public String fuerzatrabajo_id;
    public String addresscode;

    public DireccionCliente(
            String cliente_id,
            String domembarque_id,
            String direccion,
            String zona_id,
            String zona,
            String nombrefuerzatrabajo,
            String fuerzatrabajo_id,
            String addresscode
    ) {
        this.cliente_id = cliente_id;
        this.domembarque_id = domembarque_id;
        this.direccion = direccion;
        this.zona_id = zona_id;
        this.zona = zona;
        this.nombrefuerzatrabajo = nombrefuerzatrabajo;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.addresscode = addresscode;
    }

    public DireccionCliente() {
    }

    public String getAddresscode() {
        return addresscode;
    }

    public void setAddresscode(String addresscode) {
        this.addresscode = addresscode;
    }

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
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

    public String getNombrefuerzatrabajo() {
        return nombrefuerzatrabajo;
    }

    public void setNombrefuerzatrabajo(String nombrefuerzatrabajo) {
        this.nombrefuerzatrabajo = nombrefuerzatrabajo;
    }
}

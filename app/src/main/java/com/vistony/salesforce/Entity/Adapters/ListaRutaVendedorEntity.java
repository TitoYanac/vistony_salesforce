package com.vistony.salesforce.Entity.Adapters;

public class ListaRutaVendedorEntity {

    public String cliente_id;
    public String direccion;
    public String domembarque_id;
    public String nombrecliente;
    public int ordenvisita;
    public String saldomn;
    public String zona;
    public int imvclientecabecera;

    public ListaRutaVendedorEntity (String cliente_id, String direccion,String domembarque_id, String nombrecliente
            ,int ordenvisita,String saldomn,String zona, int imvclientecabecera) {
        this.cliente_id = cliente_id;
        this.direccion = direccion;
        this.domembarque_id=domembarque_id;
        this.nombrecliente = nombrecliente;
        this.ordenvisita = ordenvisita;
        this.saldomn=saldomn;
        this.zona=zona;
        this.imvclientecabecera = imvclientecabecera;
    }

    public String getDomembarque_id() {
        return domembarque_id;
    }

    public void setDomembarque_id(String domembarque_id) {
        this.domembarque_id = domembarque_id;
    }

    public String getSaldomn() {
        return saldomn;
    }

    public void setSaldomn(String saldomn) {
        this.saldomn = saldomn;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public int getOrdenvisita() {
        return ordenvisita;
    }

    public void setOrdenvisita(int ordenvisita) {
        this.ordenvisita = ordenvisita;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public ListaRutaVendedorEntity(){}

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getImvclientecabecera() {
        return imvclientecabecera;
    }

    public void setImvclientecabecera(int imvclientecabecera) {
        this.imvclientecabecera = imvclientecabecera;
    }
}

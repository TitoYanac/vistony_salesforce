package com.vistony.salesforce.Entity.Adapters;

import java.io.Serializable;

public class ListaListadoPromocionEntity implements Serializable {
    public String id;
    public String lista_precio;

    public ListaListadoPromocionEntity(String id, String lista_precio) {
        this.id = id;
        this.lista_precio = lista_precio;
    }

    public ListaListadoPromocionEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLista_precio() {
        return lista_precio;
    }

    public void setLista_precio(String lista_precio) {
        this.lista_precio = lista_precio;
    }
}

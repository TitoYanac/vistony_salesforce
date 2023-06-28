package com.vistony.salesforce.Entity.Adapters;

import com.vistony.salesforce.Entity.SQLite.PromocionDetalleSQLiteEntity;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaListadoPromocionEntity implements Serializable {
    public String id;
    public String lista_precio;
    public ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabeceraEntity;

    public ListaListadoPromocionEntity(String id, String lista_precio) {
        this.id = id;
        this.lista_precio = lista_precio;
    }

    public ArrayList<ListaPromocionCabeceraEntity> getListaPromocionCabeceraEntity() {
        return listaPromocionCabeceraEntity;
    }

    public void setListaPromocionCabeceraEntity(ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabeceraEntity) {
        this.listaPromocionCabeceraEntity = listaPromocionCabeceraEntity;
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

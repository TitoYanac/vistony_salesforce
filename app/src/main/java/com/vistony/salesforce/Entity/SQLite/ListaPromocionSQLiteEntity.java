package com.vistony.salesforce.Entity.SQLite;

import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;

import java.util.ArrayList;

public class ListaPromocionSQLiteEntity {
    public String compania_id;
    public String lista_promocion_id;
    public String lista_promocion;
    public String u_vis_cashdscnt;
    public ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabeceraEntity;


    public ListaPromocionSQLiteEntity(String compania_id, String lista_promocion_id, String lista_promocion, String u_vis_cashdscnt) {
        this.compania_id = compania_id;
        this.lista_promocion_id = lista_promocion_id;
        this.lista_promocion = lista_promocion;
        this.u_vis_cashdscnt = u_vis_cashdscnt;
    }

    public ListaPromocionSQLiteEntity() {
    }

    public ArrayList<ListaPromocionCabeceraEntity> getListaPromocionCabeceraEntity() {
        return listaPromocionCabeceraEntity;
    }

    public void setListaPromocionCabeceraEntity(ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabeceraEntity) {
        this.listaPromocionCabeceraEntity = listaPromocionCabeceraEntity;
    }

    public String getU_vis_cashdscnt() {
        return u_vis_cashdscnt;
    }

    public void setU_vis_cashdscnt(String u_vis_cashdscnt) {
        this.u_vis_cashdscnt = u_vis_cashdscnt;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getLista_promocion_id() {
        return lista_promocion_id;
    }

    public void setLista_promocion_id(String lista_promocion_id) {
        this.lista_promocion_id = lista_promocion_id;
    }

    public String getLista_promocion() {
        return lista_promocion;
    }

    public void setLista_promocion(String lista_promocion) {
        this.lista_promocion = lista_promocion;
    }
}

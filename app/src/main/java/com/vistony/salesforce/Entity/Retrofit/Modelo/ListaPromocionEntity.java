package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class ListaPromocionEntity {

    @SerializedName("ListTypeID")
    private String lista_promocion_id;

    @SerializedName("ListTypeName")
    private String lista_promocion;
/*
    @SerializedName("U_VIS_CashDscnt")
    private String u_vis_cashdscnt;

    public String getU_vis_cashdscnt() {
        return u_vis_cashdscnt;
    }

    public void setU_vis_cashdscnt(String u_vis_cashdscnt) {
        this.u_vis_cashdscnt = u_vis_cashdscnt;
    }
*/
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

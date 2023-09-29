package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class PriceListHeadEntity {
    private String compania_id;

    private String fuerzatrabajo_id;

    private String usuario_id;

    @SerializedName("ListNum")
    private String ListNum;

    @SerializedName("ListName")
    private String ListName;

    @SerializedName("PrcntIncrease")
    private String PrcntIncrease;

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

    public String getListNum() {
        return ListNum;
    }

    public void setListNum(String listNum) {
        ListNum = listNum;
    }

    public String getListName() {
        return ListName;
    }

    public void setListName(String listName) {
        ListName = listName;
    }

    public String getPrcntIncrease() {
        return PrcntIncrease;
    }

    public void setPrcntIncrease(String prcntIncrease) {
        PrcntIncrease = prcntIncrease;
    }
}

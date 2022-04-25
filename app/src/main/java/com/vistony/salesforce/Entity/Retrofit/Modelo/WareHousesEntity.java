package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class WareHousesEntity {
    @SerializedName("Itemcode")
    private String Itemcode;

    @SerializedName("ItemName")
    private String ItemName;

    @SerializedName("UDM")
    private String UDM;

    @SerializedName("EnStock")
    private String EnStock;

    @SerializedName("Comprometido")
    private String Comprometido;

    @SerializedName("Pedido")
    private String Pedido;

    @SerializedName("Disponible")
    private String Disponible;

    @SerializedName("CodAlmacen")
    private String CodAlmacen;

    @SerializedName("WhsName")
    private String WhsName;

    @SerializedName("U_VIST_SUCUSU")
    private String U_VIST_SUCUSU;

    public WareHousesEntity(String itemcode, String itemName, String UDM, String enStock, String comprometido, String pedido, String disponible, String codAlmacen, String whsName, String u_VIST_SUCUSU) {
        Itemcode = itemcode;
        ItemName = itemName;
        this.UDM = UDM;
        EnStock = enStock;
        Comprometido = comprometido;
        Pedido = pedido;
        Disponible = disponible;
        CodAlmacen = codAlmacen;
        WhsName = whsName;
        U_VIST_SUCUSU = u_VIST_SUCUSU;
    }

    public WareHousesEntity() {
    }

    public String getItemcode() {
        return Itemcode;
    }

    public void setItemcode(String itemcode) {
        Itemcode = itemcode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getUDM() {
        return UDM;
    }

    public void setUDM(String UDM) {
        this.UDM = UDM;
    }

    public String getEnStock() {
        return EnStock;
    }

    public void setEnStock(String enStock) {
        EnStock = enStock;
    }

    public String getComprometido() {
        return Comprometido;
    }

    public void setComprometido(String comprometido) {
        Comprometido = comprometido;
    }

    public String getPedido() {
        return Pedido;
    }

    public void setPedido(String pedido) {
        Pedido = pedido;
    }

    public String getDisponible() {
        return Disponible;
    }

    public void setDisponible(String disponible) {
        Disponible = disponible;
    }

    public String getCodAlmacen() {
        return CodAlmacen;
    }

    public void setCodAlmacen(String codAlmacen) {
        CodAlmacen = codAlmacen;
    }

    public String getWhsName() {
        return WhsName;
    }

    public void setWhsName(String whsName) {
        WhsName = whsName;
    }

    public String getU_VIST_SUCUSU() {
        return U_VIST_SUCUSU;
    }

    public void setU_VIST_SUCUSU(String u_VIST_SUCUSU) {
        U_VIST_SUCUSU = u_VIST_SUCUSU;
    }
}

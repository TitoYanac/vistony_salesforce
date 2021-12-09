package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class UserIndiaEntity {
    @NonNull
    @SerializedName("imei")
    private String imei;
    @NonNull
    @SerializedName("UserName")
    private String nombreusuario;

    @NonNull
    @SerializedName("UserCode")
    private String usuario_id;

    @NonNull
    @SerializedName("CashList")
    private String lista_precios1;

    @NonNull
    @SerializedName("CreditList")
    private String lista_precios2;

    @NonNull
    @SerializedName("WarehouseCode")
    private String almacen;

    @NonNull
    @SerializedName("SalesPersonCode")
    private String fuerzatrabajo_id;

    //@NonNull
    //@SerializedName("NombreFuerzaTrabajo")
    private String nombrefuerzadetrabajo;

    @NonNull
    @SerializedName("Profile")
    private String perfil;

    @NonNull
    @SerializedName("Receipt")
    private String recibo;

    @NonNull
    @SerializedName("CompanyCode")
    private String companiaid;

    @NonNull
    @SerializedName("CompanyName")
    private String nombrecompania;

    @NonNull
    @SerializedName("CogsAcct")
    private String cogsacct;

    @NonNull
    @SerializedName("CTAINGDCTO")
    private String u_vist_ctaingdcto;

    @NonNull
    @SerializedName("DocumentsOwner")
    private String documentsowner;

    @NonNull
    @SerializedName("Branch")
    private String u_vist_sucusu;

    @NonNull
    @SerializedName("CostCenter")
    private String centrocosto;

    @NonNull
    @SerializedName("BusinessUnit")
    private String unidadnegocio;

    @NonNull
    @SerializedName("ProductionLine")
    private String lineaproduccion;

    //@NonNull
    //@SerializedName("Impuesto_ID")
    private String impuesto_id;

    //@NonNull
    //@SerializedName("Impuesto")
    private String Impuesto;

    @NonNull
    @SerializedName("Rate")
    private String tipocambio;


    @NonNull
    //@SerializedName("U_VIS_CashDscnt")
    private String u_vis_cashdscnt;

    @NonNull
    public String getU_vis_cashdscnt() {
        return u_vis_cashdscnt;
    }

    public void setU_vis_cashdscnt(@NonNull String u_vis_cashdscnt) {
        this.u_vis_cashdscnt = u_vis_cashdscnt;
    }

    @NonNull
    public String getU_vist_sucusu() {
        return u_vist_sucusu;
    }

    public void setU_vist_sucusu(@NonNull String u_vist_sucusu) {
        this.u_vist_sucusu = u_vist_sucusu;
    }

    @NonNull
    public String getCentrocosto() {
        return centrocosto;
    }

    public void setCentrocosto(@NonNull String centrocosto) {
        this.centrocosto = centrocosto;
    }

    @NonNull
    public String getUnidadnegocio() {
        return unidadnegocio;
    }

    public void setUnidadnegocio(@NonNull String unidadnegocio) {
        this.unidadnegocio = unidadnegocio;
    }

    @NonNull
    public String getLineaproduccion() {
        return lineaproduccion;
    }

    public void setLineaproduccion(@NonNull String lineaproduccion) {
        this.lineaproduccion = lineaproduccion;
    }

    @NonNull
    public String getImpuesto_id() {
        return impuesto_id;
    }

    public void setImpuesto_id(@NonNull String impuesto_id) {
        this.impuesto_id = impuesto_id;
    }

    @NonNull
    public String getImpuesto() {
        return Impuesto;
    }

    public void setImpuesto(@NonNull String impuesto) {
        Impuesto = impuesto;
    }

    @NonNull
    public String getTipocambio() {
        return tipocambio;
    }

    public void setTipocambio(@NonNull String tipocambio) {
        this.tipocambio = tipocambio;
    }

    @NonNull
    public String getCogsacct() {
        return cogsacct;
    }

    public void setCogsacct(@NonNull String cogsacct) {
        this.cogsacct = cogsacct;
    }

    @NonNull
    public String getU_vist_ctaingdcto() {
        return u_vist_ctaingdcto;
    }

    public void setU_vist_ctaingdcto(@NonNull String u_vist_ctaingdcto) {
        this.u_vist_ctaingdcto = u_vist_ctaingdcto;
    }

    @NonNull
    public String getDocumentsowner() {
        return documentsowner;
    }

    public void setDocumentsowner(@NonNull String documentsowner) {
        this.documentsowner = documentsowner;
    }

    @NonNull
    public String getCompaniaid() {
        return companiaid;
    }

    public void setCompaniaid(@NonNull String companiaid) {
        this.companiaid = companiaid;
    }

    @NonNull
    public String getNombrecompania() {
        return nombrecompania;
    }

    public void setNombrecompania(@NonNull String nombrecompania) {
        this.nombrecompania = nombrecompania;
    }

    @NonNull
    public String getImei() {
        return imei;
    }

    public void setImei(@NonNull String imei) {
        this.imei = imei;
    }

    @NonNull
    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(@NonNull String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    @NonNull
    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(@NonNull String usuario_id) {
        this.usuario_id = usuario_id;
    }

    @NonNull
    public String getLista_precios1() {
        return lista_precios1;
    }

    public void setLista_precios1(@NonNull String lista_precios1) {
        this.lista_precios1 = lista_precios1;
    }

    @NonNull
    public String getLista_precios2() {
        return lista_precios2;
    }

    public void setLista_precios2(@NonNull String lista_precios2) {
        this.lista_precios2 = lista_precios2;
    }

    @NonNull
    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(@NonNull String almacen) {
        this.almacen = almacen;
    }

    @NonNull
    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(@NonNull String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    @NonNull
    public String getNombrefuerzadetrabajo() {
        return nombrefuerzadetrabajo;
    }

    public void setNombrefuerzadetrabajo(@NonNull String nombrefuerzadetrabajo) {
        this.nombrefuerzadetrabajo = nombrefuerzadetrabajo;
    }

    @NonNull
    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(@NonNull String perfil) {
        this.perfil = perfil;
    }

    @NonNull
    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(@NonNull String recibo) {
        this.recibo = recibo;
    }
}

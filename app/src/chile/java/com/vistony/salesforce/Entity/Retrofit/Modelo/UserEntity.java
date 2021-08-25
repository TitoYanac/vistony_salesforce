package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserEntity {

    @NonNull
    @SerializedName("Imei")
    private String imei;

    @NonNull
    @SerializedName("UserName")
    private String nombreusuario;

    @NonNull
    @SerializedName("UserCode")
    private String usuario_id;

    @NonNull
    @SerializedName("WhsCode")
    private String almacen;

    @NonNull
    @SerializedName("SlpCode")
    private String fuerzatrabajo_id;

    @NonNull
    @SerializedName("Profile")
    private String perfil;

    @NonNull
    @SerializedName("CompanyCode")
    private String companiaid;

    @NonNull
    @SerializedName("CompanyName")
    private String nombrecompania;

    @NonNull
    @SerializedName("Branch")
    private String u_vist_sucusu;

    @NonNull
    @SerializedName("Settings")
    private List<SettingsUserEntity> Settings;

    @NonNull
    public List<SettingsUserEntity> getSettings() {
        return Settings;
    }

    public void setSettings(@NonNull List<SettingsUserEntity> settings) {
        Settings = settings;
    }

    //@NonNull
    //@SerializedName("Impuesto_ID")
    private String impuesto_id;

    //@NonNull
    //@SerializedName("Impuesto")
    private String Impuesto;

    //@NonNull
    //@SerializedName("Rate")
    private String tipocambio;

    //@NonNull
    //@SerializedName("U_VIS_CashDscnt")
    private String u_vis_cashdscnt;

    //@NonNull
    //@SerializedName("NombreFuerzaTrabajo")
    private String nombrefuerzadetrabajo;

    //@NonNull
    //@SerializedName("CTAINGDCTO")
    private String u_vist_ctaingdcto;

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
    public String getU_vist_ctaingdcto() {
        return u_vist_ctaingdcto;
    }

    public void setU_vist_ctaingdcto(@NonNull String u_vist_ctaingdcto) {
        this.u_vist_ctaingdcto = u_vist_ctaingdcto;
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
}

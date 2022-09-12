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
    private String user_code;

    @NonNull
    @SerializedName("WhsCode")
    private String almacen;

    @NonNull
    @SerializedName("SlpCode")
    private String slp_code;

    @NonNull
    @SerializedName("Profile")
    private String perfil;

    @NonNull
    @SerializedName("CompanyCode")
    private String companiaid;

    @NonNull
    @SerializedName("Country")
    private String country;

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
    @SerializedName("Rate")
    private String rate;

    @NonNull
    @SerializedName("Print")
    private String print;

    @NonNull
    @SerializedName("Phone")
    private String phone;

    @NonNull
    @SerializedName("Quotation")
    private String quotation;

    @NonNull
    @SerializedName("Census")
    private String census;

    @NonNull
    public String getCensus() {
        return census;
    }

    public void setCensus(@NonNull String census) {
        this.census = census;
    }

    @NonNull
    public String getQuotation() {
        return quotation;
    }

    public void setQuotation(@NonNull String quotation) {
        this.quotation = quotation;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public String getPrint() {
        return print;
    }

    public void setPrint(@NonNull String print) {
        this.print = print;
    }

    @NonNull
    public String getRate() {
        return rate;
    }

    public void setRate(@NonNull String rate) {
        this.rate = rate;
    }

    @NonNull
    public List<SettingsUserEntity> getSettings() {
        return Settings;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    public void setSettings(@NonNull List<SettingsUserEntity> settings) {
        Settings = settings;
    }

    @NonNull
    public String getU_vist_sucusu() {
        return u_vist_sucusu;
    }

    public void setU_vist_sucusu(@NonNull String u_vist_sucusu) {
        this.u_vist_sucusu = u_vist_sucusu;
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
    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(@NonNull String almacen) {
        this.almacen = almacen;
    }

    @NonNull
    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(@NonNull String perfil) {
        this.perfil = perfil;
    }

    @NonNull
    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(@NonNull String user_code) {
        this.user_code = user_code;
    }

    @NonNull
    public String getSlp_code() {
        return slp_code;
    }

    public void setSlp_code(@NonNull String slp_code) {
        this.slp_code = slp_code;
    }
}

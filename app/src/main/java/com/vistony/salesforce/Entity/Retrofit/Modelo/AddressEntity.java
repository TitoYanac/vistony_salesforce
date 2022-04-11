package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class AddressEntity {

    private String companiaid;

    private String clienteId;

    @SerializedName("ShipToCode")
    private String domicilioEmbarque;

    @SerializedName("Street")
    private String direccion;

    @SerializedName("TerritoryID")
    private String zonaid;

    @SerializedName("Territory")
    private String zona;

    @SerializedName("SlpCode")
    private String fuerzatrabajoid;

    @SerializedName("SlpName")
    private String nombrefuerzatrabajo;

    @SerializedName("Latitude")
    private String latitude;

    @SerializedName("Longitude")
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCompaniaid() {
        return companiaid;
    }

    public void setCompaniaid(String companiaid) {
        this.companiaid = companiaid;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getDomicilioEmbarque() {
        return domicilioEmbarque;
    }

    public void setDomicilioEmbarque(String domicilioEmbarque) {
        this.domicilioEmbarque = domicilioEmbarque;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getZonaid() {
        return zonaid;
    }

    public void setZonaid(String zonaid) {
        this.zonaid = zonaid;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getFuerzatrabajoid() {
        return fuerzatrabajoid;
    }

    public void setFuerzatrabajoid(String fuerzatrabajoid) {
        this.fuerzatrabajoid = fuerzatrabajoid;
    }

    public String getNombrefuerzatrabajo() {
        return nombrefuerzatrabajo;
    }

    public void setNombrefuerzatrabajo(String nombrefuerzatrabajo) {
        this.nombrefuerzatrabajo = nombrefuerzatrabajo;
    }
}

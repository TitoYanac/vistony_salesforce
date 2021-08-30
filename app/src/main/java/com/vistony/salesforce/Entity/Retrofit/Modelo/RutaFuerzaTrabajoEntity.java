package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class RutaFuerzaTrabajoEntity {
    @SerializedName("TerritoryId")
    private String zona_id;

    @SerializedName("Territory")
    private String zona;

    @SerializedName("Day")
    private String dia;

    @SerializedName("Frequency")
    private String frecuencia;

    @SerializedName("InitDate")
    private String fechainicioruta;

    @SerializedName("Status")
    private String estado;

    @SerializedName("VisitDate")
    private String visitDate;

    @SerializedName("SlpCode")
    private String slpCode;

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getSlpCode() {
        return slpCode;
    }

    public void setSlpCode(String slpCode) {
        this.slpCode = slpCode;
    }

    public String getFechainicioruta() {
        return fechainicioruta;
    }

    public void setFechainicioruta(String fechainicioruta) {
        this.fechainicioruta = fechainicioruta;
    }

    public String getZona_id() {
        return zona_id;
    }

    public void setZona_id(String zona_id) {
        this.zona_id = zona_id;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

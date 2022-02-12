package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class HeaderDispatchSheetEntity {

    @SerializedName("Control_ID")
    private String control_id;

    @SerializedName("Asistente_ID")
    private String asistente_id;

    @SerializedName("Asistente")
    private String asistente;

    @SerializedName("Placa")
    private String placa;

    @SerializedName("Marca")
    private String marca;

    @SerializedName("Peso_Total")
    private String peso_total;

    public String getControl_id() {
        return control_id;
    }

    public void setControl_id(String control_id) {
        this.control_id = control_id;
    }

    public String getAsistente_id() {
        return asistente_id;
    }

    public void setAsistente_id(String asistente_id) {
        this.asistente_id = asistente_id;
    }

    public String getAsistente() {
        return asistente;
    }

    public void setAsistente(String asistente) {
        this.asistente = asistente;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPeso_total() {
        return peso_total;
    }

    public void setPeso_total(String peso_total) {
        this.peso_total = peso_total;
    }
}

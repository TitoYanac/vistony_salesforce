package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;

public class CobranzaCabeceraEntityResponse {
    @SerializedName("InsertCobranzaCResult")
    private String cobranzaCabeceraEntity;

    public void CobranzaCabeceraEntityResponse(String cobranzaCabeceraEntity) {
        this.cobranzaCabeceraEntity = cobranzaCabeceraEntity;
    }
    public String getCobranzaCabeceraEntity() {
        return cobranzaCabeceraEntity;
    }
}

package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class VisitaEntity {
    @SerializedName("Registrar_VisitaResult")
    private String Resultado;

    @NonNull
    public String getResultado() {
        return Resultado;
    }

    public void setResultado(@NonNull String resultado) {
        Resultado = resultado;
    }
}

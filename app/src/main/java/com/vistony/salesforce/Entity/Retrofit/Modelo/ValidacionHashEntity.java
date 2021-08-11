package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ValidacionHashEntity {
    @NonNull
    @SerializedName("Semejanza")
    private String semejanza;

    @NonNull
    public String getSemejanza() {
        return semejanza;
    }

    public void setSemejanza(@NonNull String semejanza) {
        this.semejanza = semejanza;
    }
}

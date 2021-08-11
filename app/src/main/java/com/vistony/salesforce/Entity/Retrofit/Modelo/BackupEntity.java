package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class BackupEntity {
    @NonNull
    @SerializedName("Result")
    private Boolean estado;

    @NonNull
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(@NonNull Boolean estado) {
        this.estado = estado;
    }
}

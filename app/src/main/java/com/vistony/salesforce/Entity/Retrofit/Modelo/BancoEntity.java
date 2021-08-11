package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class BancoEntity {
    @SerializedName("BankID")
    private String Banco_ID;

    @SerializedName("BankName")
    private String Nombre_Banco;
    @NonNull
    public String getBanco_ID() {
        return Banco_ID;
    }

    public void setBanco_ID( @NonNull String banco_ID) {
        Banco_ID = banco_ID;
    }
    @NonNull
    public String getNombre_Banco() {
        return Nombre_Banco;
    }

    public void setNombre_Banco(@NonNull String nombre_Banco) {
        Nombre_Banco = nombre_Banco;
    }
}

package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class BancoEntity {
    @SerializedName("BankId")
    private String Banco_ID;

    @SerializedName("BankName")
    private String Nombre_Banco;

    @SerializedName("SingleDeposit")
    private String OperacionUnica;

    @SerializedName("PagoPOS")
    private String PagoPOS;

    public String getPagoPOS() {
        return PagoPOS;
    }

    public void setPagoPOS(String pagoPOS) {
        PagoPOS = pagoPOS;
    }

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

    public String getOperacionUnica() {
        return OperacionUnica;
    }

    public void setOperacionUnica(String operacionUnica) {
        OperacionUnica = operacionUnica;
    }
}

package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.DepositEntity;

import java.util.List;

public class DepositList {
    @SerializedName("Deposits")
    private List<DepositEntity> depositos;

    public List<DepositEntity> getDepositos() {
        return depositos;
    }

    public void setDepositos(List<DepositEntity> depositos) {
        this.depositos = depositos;
    }
}

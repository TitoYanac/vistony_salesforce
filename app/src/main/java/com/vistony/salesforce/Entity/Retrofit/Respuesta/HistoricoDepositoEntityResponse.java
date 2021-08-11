package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricoDepositoEntity;

import java.util.List;

public class HistoricoDepositoEntityResponse {
    @SerializedName("LeerCobranzaCResult")
    //@SerializedName("data")
    private List<HistoricoDepositoEntity> historicoDepositoEntity;

    public void HistoricoDepositoEntityResponse(List<HistoricoDepositoEntity> historicoDepositoEntity) {
        this.historicoDepositoEntity = historicoDepositoEntity;
    }
    public List<HistoricoDepositoEntity> getHistoricoDeposito() {
        return historicoDepositoEntity;
    }
}

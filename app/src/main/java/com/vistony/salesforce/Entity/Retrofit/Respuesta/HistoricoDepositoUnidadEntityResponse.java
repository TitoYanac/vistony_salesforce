package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricoDepositoUnidadEntity;

import java.util.List;

public class HistoricoDepositoUnidadEntityResponse {

    @SerializedName("LeerCobranzaDepositoResult")
    //@SerializedName("data")
    private List<HistoricoDepositoUnidadEntity> historicoDepositoUnidadEntity;

    public void setHistoricoDepositoEntityResponse(List<HistoricoDepositoUnidadEntity> historicoDepositoUnidadEntity) {
        this.historicoDepositoUnidadEntity = historicoDepositoUnidadEntity;
    }
    public List<HistoricoDepositoUnidadEntity> getHistoricoDepositoUnidad() {
        return historicoDepositoUnidadEntity;
    }
}

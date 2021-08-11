package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricoCobranzaEntity;

import java.util.List;

public class HistoricoCobranzaEntityResponse {
    @SerializedName("CollectionDetail")
    private List<HistoricoCobranzaEntity> historicoCobranzaEntity;

    public void HistoricoDepositoEntityResponse(List<HistoricoCobranzaEntity> historicoCobranzaEntity) {
        this.historicoCobranzaEntity = historicoCobranzaEntity;
    }

    public List<HistoricoCobranzaEntity> getHistoricoCobranza() {
        return historicoCobranzaEntity;
    }
}

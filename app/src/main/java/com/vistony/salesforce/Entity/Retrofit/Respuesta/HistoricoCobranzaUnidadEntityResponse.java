package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricoCobranzaUnidadEntity;

import java.util.List;

public class HistoricoCobranzaUnidadEntityResponse {
    //@SerializedName("data")
    @SerializedName("LeerCobranzaReciboResult")
    private List<HistoricoCobranzaUnidadEntity> historicoCobranzaUnidadEntity;

    public void setHistoricoCobranzaUnidadEntity(List<HistoricoCobranzaUnidadEntity> historicoCobranzaUnidadEntity) {
        this.historicoCobranzaUnidadEntity = historicoCobranzaUnidadEntity;
    }
    public List<HistoricoCobranzaUnidadEntity> getHistoricoCobranzaUnidadEntity() {
        return historicoCobranzaUnidadEntity;
    }
}

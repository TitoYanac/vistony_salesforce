package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricoFacturasEntity;

import java.util.List;

public class HistoricoFacturasEntityResponse {
    @SerializedName("Documents")
    //@SerializedName("data")

    private List<HistoricoFacturasEntity> historicoFacturasEntity;

    public void HistoricosFacturasEntityResponse(List<HistoricoFacturasEntity> historicoFacturasEntity) {
        this.historicoFacturasEntity = historicoFacturasEntity;
    }
    public List<HistoricoFacturasEntity> getHistoricoFacturas() {
        return historicoFacturasEntity;
    }
}

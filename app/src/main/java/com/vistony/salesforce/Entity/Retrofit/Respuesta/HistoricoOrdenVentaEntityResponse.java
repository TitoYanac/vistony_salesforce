package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricoOrdenVentaEntity;

import java.util.List;

public class HistoricoOrdenVentaEntityResponse {
    @SerializedName("Pedidos_Leer_OrdenVentaCResult")
    //@SerializedName("data")
    private List<HistoricoOrdenVentaEntity> historicoOrdenVentaEntity;

    public void HistoricoOrdenVentaEntityResponse(List<HistoricoOrdenVentaEntity> historicoOrdenVentaEntity) {
        this.historicoOrdenVentaEntity = historicoOrdenVentaEntity;
    }
    public List<HistoricoOrdenVentaEntity> getHistoricoOrdenVenta() {
        return historicoOrdenVentaEntity;
    }
}

package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesOrderTraceabilityEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricoOrdenVentaEntity;

import java.util.List;

public class HistoricSalesOrderTraceabilityEntityResponse {
    @SerializedName("Traceabilities")
    private List<HistoricSalesOrderTraceabilityEntity> historicSalesOrderTraceabilityEntity;

    public void HistoricSalesOrderTraceabilityEntityResponse(List<HistoricSalesOrderTraceabilityEntity> historicSalesOrderTraceabilityEntity) {
        this.historicSalesOrderTraceabilityEntity = historicSalesOrderTraceabilityEntity;
    }
    public List<HistoricSalesOrderTraceabilityEntity> getHistoricSalesOrderTraceability() {
        return historicSalesOrderTraceabilityEntity;
    }
}

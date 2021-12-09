package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricoCobranzaEntity;

import java.util.List;

public class HistoricContainerSalesEntityResponse {
    @SerializedName("RecordSales")
    private List<HistoricContainerSalesEntity> historicContainerSalesEntity;

    public HistoricContainerSalesEntityResponse(List<HistoricContainerSalesEntity> historicContainerSalesEntity) {
        this.historicContainerSalesEntity = historicContainerSalesEntity;
    }

    public List<HistoricContainerSalesEntity> getHistoricContainerSales() {
        return historicContainerSalesEntity;
    }
}

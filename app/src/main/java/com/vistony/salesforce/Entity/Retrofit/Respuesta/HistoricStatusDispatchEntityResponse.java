package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricoOrdenVentaEntity;

import java.util.List;

public class HistoricStatusDispatchEntityResponse {
    @SerializedName("SalesOrder")
    private List<HistoricStatusDispatchEntity> historicStatusDispatchEntity;

    public void HistoricStatusDispatchEntityResponse(List<HistoricStatusDispatchEntity> historicStatusDispatchEntity) {
        this.historicStatusDispatchEntity = historicStatusDispatchEntity;
    }
    public List<HistoricStatusDispatchEntity> getHistoricStatusDispatch() {
        return historicStatusDispatchEntity;
    }
}

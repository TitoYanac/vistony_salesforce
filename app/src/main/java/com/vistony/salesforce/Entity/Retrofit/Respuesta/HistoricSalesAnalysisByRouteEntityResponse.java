package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesAnalysisByRouteEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesOrderTraceabilityEntity;

import java.util.List;

public class HistoricSalesAnalysisByRouteEntityResponse {
    @SerializedName("AnalysisRoutes")
    private List<HistoricSalesAnalysisByRouteEntity> historicSalesAnalysisByRouteEntity;

    public void HistoricSalesAnalysisByRouteEntityResponse(List<HistoricSalesAnalysisByRouteEntity> historicSalesAnalysisByRouteEntity) {
        this.historicSalesAnalysisByRouteEntity = historicSalesAnalysisByRouteEntity;
    }
    public List<HistoricSalesAnalysisByRouteEntity> getHistoricSalesAnalysisByRoute() {
        return historicSalesAnalysisByRouteEntity;
    }
}

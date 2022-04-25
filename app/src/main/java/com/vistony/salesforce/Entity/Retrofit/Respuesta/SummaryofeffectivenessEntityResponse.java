package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SummaryofeffectivenessEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WareHousesEntity;

import java.util.List;

public class SummaryofeffectivenessEntityResponse {
    @SerializedName("Obtener_Summaryofeffectiveness")

    private List<SummaryofeffectivenessEntity> summaryofeffectivenessEntityList;

    public SummaryofeffectivenessEntityResponse(List<SummaryofeffectivenessEntity> summaryofeffectivenessEntityList) {
        this.summaryofeffectivenessEntityList = summaryofeffectivenessEntityList;
    }

    public List<SummaryofeffectivenessEntity> getSummaryofeffectivenessEntities() {
        return summaryofeffectivenessEntityList;
    }
}

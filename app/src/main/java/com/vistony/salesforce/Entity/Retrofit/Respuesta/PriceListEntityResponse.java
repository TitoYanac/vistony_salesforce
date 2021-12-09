package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.MotivoVisitaEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.PriceListEntity;

import java.util.List;

public class PriceListEntityResponse {
    @SerializedName("PriceList")
    private List<PriceListEntity> priceListEntity;

    public PriceListEntityResponse(List<PriceListEntity> priceListEntity)  {
        this.priceListEntity = priceListEntity;
    }

    public List<PriceListEntity> getPriceListEntity() {
        return priceListEntity;
    }
}

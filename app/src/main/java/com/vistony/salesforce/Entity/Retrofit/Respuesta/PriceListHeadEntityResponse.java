package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.PriceListEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.PriceListHeadEntity;

import java.util.List;

public class PriceListHeadEntityResponse {
    @SerializedName("PriceListHeader")
    private List<PriceListHeadEntity> priceListHeadEntity;

    public PriceListHeadEntityResponse(List<PriceListHeadEntity> priceListHeadEntity)  {
        this.priceListHeadEntity = priceListHeadEntity;
    }

    public List<PriceListHeadEntity> getPriceListHeadEntity() {
        return priceListHeadEntity;
    }
}

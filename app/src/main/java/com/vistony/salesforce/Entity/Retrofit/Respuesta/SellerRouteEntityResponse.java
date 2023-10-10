package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.AgenciaEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SellerRouteEntity;

import java.util.List;

public class SellerRouteEntityResponse {
    @SerializedName("SalesRoute")
    private List<SellerRouteEntity> sellerRouteEntity;

    public SellerRouteEntityResponse (List<SellerRouteEntity> sellerRouteEntity)  {
        this.sellerRouteEntity = sellerRouteEntity;
    }

    public List<SellerRouteEntity> getSellerRouteEntity() {
        return sellerRouteEntity;
    }
}

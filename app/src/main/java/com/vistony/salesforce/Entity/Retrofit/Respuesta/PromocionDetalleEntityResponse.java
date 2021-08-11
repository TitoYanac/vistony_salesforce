package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.PromocionDetalleEntity;

import java.util.List;

public class PromocionDetalleEntityResponse {
    @SerializedName("PromotionListDetail")
    private List<PromocionDetalleEntity> promocionDetalleEntity;

    public PromocionDetalleEntityResponse (List<PromocionDetalleEntity> promocionDetalleEntity)  {
        this.promocionDetalleEntity = promocionDetalleEntity;
    }

    public List<PromocionDetalleEntity> getPromocionDetalleEntity() {
        return promocionDetalleEntity;
    }
}

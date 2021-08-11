package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.PromocionCabeceraEntity;

import java.util.List;

public class PromocionCabeceraEntityResponse {
    @SerializedName("PromotionListHeader")
    private List<PromocionCabeceraEntity> promocionCabeceraEntity;

    public PromocionCabeceraEntityResponse (List<PromocionCabeceraEntity> promocionCabeceraEntity)  {
        this.promocionCabeceraEntity = promocionCabeceraEntity;
    }

    public List<PromocionCabeceraEntity> getPromocionCabeceraEntity() {
        return promocionCabeceraEntity;
    }

}

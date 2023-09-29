package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BancoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerHeadEntity;

import java.util.List;

public class BusinessLayerEntityResponse {
    @SerializedName("BusinessLayer")
    private List<BusinessLayerHeadEntity> businessLayerHeadEntity;

    public BusinessLayerEntityResponse (List<BusinessLayerHeadEntity> businessLayerHeadEntity)  {
        this.businessLayerHeadEntity = businessLayerHeadEntity;
    }

    public List<BusinessLayerHeadEntity> getBusinessLayerEntityResponse() {
        return businessLayerHeadEntity;
    }
}

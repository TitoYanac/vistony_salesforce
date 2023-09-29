package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerHeadEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerSalesDetailHeaderEntity;

import java.util.List;

public class BusinessLayerSalesDetailEntityResponse {
    @SerializedName("BusinessLayerDetail")
    private List<BusinessLayerSalesDetailHeaderEntity> businessLayerSalesDetailHeaderEntity;

    public BusinessLayerSalesDetailEntityResponse (List<BusinessLayerSalesDetailHeaderEntity> businessLayerSalesDetailHeaderEntity)  {
        this.businessLayerSalesDetailHeaderEntity = businessLayerSalesDetailHeaderEntity;
    }

    public List<BusinessLayerSalesDetailHeaderEntity> getBusinessLayerSalesDetailEntityResponse() {
        return businessLayerSalesDetailHeaderEntity;
    }
}

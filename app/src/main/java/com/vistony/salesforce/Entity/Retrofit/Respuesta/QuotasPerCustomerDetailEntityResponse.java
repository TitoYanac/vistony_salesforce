package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerDetailEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerHeadEntity;

import java.util.List;

public class QuotasPerCustomerDetailEntityResponse {
    @SerializedName("QuotasDetail")
    private List<QuotasPerCustomerDetailEntity> quotasPerCustomerDetailEntity;

    public QuotasPerCustomerDetailEntityResponse (List<QuotasPerCustomerDetailEntity> quotasPerCustomerDetailEntity)  {
        this.quotasPerCustomerDetailEntity = quotasPerCustomerDetailEntity;
    }

    public List<QuotasPerCustomerDetailEntity> getQuotasPerCustomerDetailEntity() {
        return quotasPerCustomerDetailEntity;
    }
}

package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerHeadEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ResumenDiarioEntity;

import java.util.List;

public class QuotasPerCustomerEntityHeadResponse {
    @SerializedName("Quotas")
    private List<QuotasPerCustomerHeadEntity> quotasPerCustomerHeadEntity;

    public QuotasPerCustomerEntityHeadResponse(List<QuotasPerCustomerHeadEntity> quotasPerCustomerHeadEntity)  {
        this.quotasPerCustomerHeadEntity = quotasPerCustomerHeadEntity;
    }

    public List<QuotasPerCustomerHeadEntity> getQuotasPerCustomerEntity() {
        return quotasPerCustomerHeadEntity;
    }
}

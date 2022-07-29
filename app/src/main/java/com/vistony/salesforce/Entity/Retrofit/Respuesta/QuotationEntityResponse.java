package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotationEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.TerminoPagoEntity;

import java.util.List;

public class QuotationEntityResponse {
    @SerializedName("Quotation")
    private List<QuotationEntity> quotationEntity;

    public List<QuotationEntity> getQuotationEntity() {
        return quotationEntity;
    }

    public void setQuotationEntity(List<QuotationEntity> quotationEntity) {
        this.quotationEntity = quotationEntity;
    }
}

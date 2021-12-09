package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerHeadEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerInvoiceEntity;

import java.util.List;

public class QuotasPerCustomerInvoiceEntityResponse {
    @SerializedName("QuotaInvoices")
    private List<QuotasPerCustomerInvoiceEntity> quotasPerCustomerInvoiceEntity;

    public QuotasPerCustomerInvoiceEntityResponse(List<QuotasPerCustomerInvoiceEntity> quotasPerCustomerInvoiceEntity)  {
        this.quotasPerCustomerInvoiceEntity = quotasPerCustomerInvoiceEntity;
    }

    public List<QuotasPerCustomerInvoiceEntity> getQuotasPerCustomerInvoiceEntity() {
        return quotasPerCustomerInvoiceEntity;
    }
}

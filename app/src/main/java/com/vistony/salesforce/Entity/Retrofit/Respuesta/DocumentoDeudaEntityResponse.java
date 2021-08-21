package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.InvoicesEntity;

import java.util.List;

public class DocumentoDeudaEntityResponse {
    @SerializedName("Documents")
    private List<InvoicesEntity> invoicesEntity;

    public void DocumentoDeudaEntityResponse(List<InvoicesEntity> invoicesEntity) {
        this.invoicesEntity = invoicesEntity;
    }
    public List<InvoicesEntity> getDocumentoDeuda() {
        return invoicesEntity;
    }
}

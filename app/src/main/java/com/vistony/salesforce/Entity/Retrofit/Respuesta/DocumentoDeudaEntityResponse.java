package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.DocumentoDeudaEntity;

import java.util.List;

public class DocumentoDeudaEntityResponse {
    @SerializedName("Documents")
    private List<DocumentoDeudaEntity> documentoDeudaEntity;

    public void DocumentoDeudaEntityResponse(List<DocumentoDeudaEntity> documentoDeudaEntity) {
        this.documentoDeudaEntity = documentoDeudaEntity;
    }
    public List<DocumentoDeudaEntity> getDocumentoDeuda() {
        return documentoDeudaEntity;
    }
}

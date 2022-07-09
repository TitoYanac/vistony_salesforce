package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SignatureREntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.StockEntity;

import java.util.List;

public class SignatureEntityResponse {
    @SerializedName("")
    private List<SignatureREntity> signatureREntity;

    public SignatureEntityResponse (List<SignatureREntity> signatureREntity)  {
        this.signatureREntity = signatureREntity;
    }

    public List<SignatureREntity> getSignatureREntity() {
        return signatureREntity;
    }
}

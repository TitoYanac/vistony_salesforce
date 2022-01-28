package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.EscColoursCEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.EscColoursDEntity;

import java.util.List;

public class EscColoursDEntityResponse {
    @SerializedName("EscColoursD")
    private List<EscColoursDEntity> escColoursDEntity;

    public EscColoursDEntityResponse (List<EscColoursDEntity> escColoursDEntity)  {
        this.escColoursDEntity = escColoursDEntity;
    }

    public List<EscColoursDEntity> getEscColoursDEntity() {
        return escColoursDEntity;
    }
}

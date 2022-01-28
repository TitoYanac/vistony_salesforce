package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.AgenciaEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.EscColoursCEntity;

import java.util.List;

public class EscColoursCEntityResponse {
    @SerializedName("EscColoursC")
    private List<EscColoursCEntity> escColoursCEntity;

    public EscColoursCEntityResponse (List<EscColoursCEntity> escColoursCEntity)  {
        this.escColoursCEntity = escColoursCEntity;
    }

    public List<EscColoursCEntity> getEscColoursEntity() {
        return escColoursCEntity;
    }
}

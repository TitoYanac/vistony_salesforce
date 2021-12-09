package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.PromocionDetalleEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ResumenDiarioEntity;

import java.util.List;

public class ResumenDiarioEntityResponse {
    @SerializedName("ResumenDiarioList")
    private List<ResumenDiarioEntity> resumenDiarioEntity;

    public ResumenDiarioEntityResponse (List<ResumenDiarioEntity> resumenDiarioEntity)  {
        this.resumenDiarioEntity = resumenDiarioEntity;
    }

    public List<ResumenDiarioEntity> getResumenDiarioEntity() {
        return resumenDiarioEntity;
    }
}

package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.RutaFuerzaTrabajoEntity;

import java.util.List;

public class RutaFuerzaTrabajoEntityResponse {

    @SerializedName("WorkPath")
    private List<RutaFuerzaTrabajoEntity> rutaFuerzaTrabajoEntity;

    public RutaFuerzaTrabajoEntityResponse (List<RutaFuerzaTrabajoEntity> rutaFuerzaTrabajoEntity)  {
        this.rutaFuerzaTrabajoEntity = rutaFuerzaTrabajoEntity;
    }

    public List<RutaFuerzaTrabajoEntity> getRutaFuerzaTrabajoEntity() {
        return rutaFuerzaTrabajoEntity;
    }
}

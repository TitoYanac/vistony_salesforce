package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.AgenciaEntity;

import java.util.List;

public class AgenciaEntityResponse {
    @SerializedName("Agencies")
    private List<AgenciaEntity> agenciaEntity;

    public AgenciaEntityResponse (List<AgenciaEntity> agenciaEntity)  {
        this.agenciaEntity = agenciaEntity;
    }

    public List<AgenciaEntity> getAgenciaEntity() {
        return agenciaEntity;
    }
}

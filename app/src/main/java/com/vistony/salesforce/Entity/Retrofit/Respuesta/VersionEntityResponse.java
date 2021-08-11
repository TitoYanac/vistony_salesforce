package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VersionEntity;

import java.util.List;

public class VersionEntityResponse {
    @SerializedName("Obtener_AppVersionResult") //Pruebas Ventas - SAP
    //@SerializedName("data") //Pruebas Mockups - Distribucion
    private List<VersionEntity> vs;

    public VersionEntityResponse(List<VersionEntity> vs) {
        this.vs = vs;
    }

    public List<VersionEntity> getVs() {
        return vs;
    }

}

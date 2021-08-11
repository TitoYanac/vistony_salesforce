package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ValidacionHashEntity;

import java.util.List;

public class ValidacionHashEntityResponse {
    @SerializedName("Validacion_Hash") //Pruebas Mockups - Distribucion
    private List<ValidacionHashEntity> validacionHashEntities;

    public List<ValidacionHashEntity> getValidacionHashEntities() {
        return validacionHashEntities;
    }

    public void setValidacionHashEntities(List<ValidacionHashEntity> validacionHashEntities) {
        this.validacionHashEntities = validacionHashEntities;
    }
}

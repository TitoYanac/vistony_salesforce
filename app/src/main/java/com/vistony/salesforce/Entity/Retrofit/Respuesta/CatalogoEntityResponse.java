package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CatalogoEntity;

import java.util.List;

public class CatalogoEntityResponse {
    @SerializedName("data")
    private List<CatalogoEntity> catalogoEntity;

    public CatalogoEntityResponse (List<CatalogoEntity> catalogoEntity)  {
        this.catalogoEntity = catalogoEntity;
    }

    public List<CatalogoEntity> getCatalogoEntity() {
        return catalogoEntity;
    }
}

package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ComisionesEntity;

import java.util.List;

public class ComisionesEntityResponse {
    //@SerializedName("data")
    @SerializedName("Commissions")
    private List<ComisionesEntity> comisionesEntity;

    public ComisionesEntityResponse (List<ComisionesEntity> comisionesEntity)  {
        this.comisionesEntity = comisionesEntity;
    }

    public List<ComisionesEntity> getComisionesEntity() {
        return comisionesEntity;
    }
}

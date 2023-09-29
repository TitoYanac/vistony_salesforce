package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerHeadEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ObjectEntity;

import java.util.List;

public class ObjectEntityResponse {
    @SerializedName("Objects")
    private List<ObjectEntity> objectEntity;

    public ObjectEntityResponse (List<ObjectEntity> objectEntity)  {
        this.objectEntity = objectEntity;
    }

    public List<ObjectEntity> getObjectEntityResponse() {
        return objectEntity;
    }
}

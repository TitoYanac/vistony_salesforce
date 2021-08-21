package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomersEntity;

import java.util.List;

public class ClienteEntityResponse {
    @SerializedName("Customers")
    private List<CustomersEntity> CustomersEntity;

    public List<CustomersEntity> getCustomersEntity() {
        return CustomersEntity;
    }
    public void setCustomersEntity(List<CustomersEntity> CustomersEntity) {
        this.CustomersEntity = CustomersEntity;
    }
}

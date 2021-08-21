package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.AddressEntity;

import java.util.List;

public class DireccionClienteEntityResponse {
    @SerializedName("Addresses")
    //@SerializedName("data")
    private List<AddressEntity> AddressEntity;

    public List<AddressEntity> getAddressEntity() {
        return AddressEntity;
    }

    public void setAddressEntity(List<AddressEntity> AddressEntity) {
        this.AddressEntity = AddressEntity;
    }
}

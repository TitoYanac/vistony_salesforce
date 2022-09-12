package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.LeadAddressEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ListaPromocionEntity;

import java.util.List;

public class LeadAddressEntityResponse {
    @SerializedName("Addresses")
    private List<LeadAddressEntity> leadAddressEntity;

    public LeadAddressEntityResponse (List<LeadAddressEntity> leadAddressEntity)  {
        this.leadAddressEntity = leadAddressEntity;
    }

    public List<LeadAddressEntity> getLeadAddressEntity() {
        return leadAddressEntity;
    }
}

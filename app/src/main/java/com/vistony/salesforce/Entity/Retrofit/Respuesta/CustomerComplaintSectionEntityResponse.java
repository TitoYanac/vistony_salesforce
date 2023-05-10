package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ComisionesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintSectionEntity;

import java.util.List;

public class CustomerComplaintSectionEntityResponse {
    @SerializedName("CustomerComplaintSection")
    private List<CustomerComplaintSectionEntity> customerComplaintSectionEntity;

    public CustomerComplaintSectionEntityResponse (List<CustomerComplaintSectionEntity> customerComplaintSectionEntity)  {
        this.customerComplaintSectionEntity = customerComplaintSectionEntity;
    }

    public List<CustomerComplaintSectionEntity> setCustomerComplaintSectionEntityResponse() {
        return customerComplaintSectionEntity;
    }

}

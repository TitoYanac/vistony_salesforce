package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintFormsEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintSectionEntity;

import java.util.List;

public class CustomerComplaintFormsEntityResponse {
    @SerializedName("Formulario")
    private CustomerComplaintFormsEntity customerComplaintFormsEntity;

    public CustomerComplaintFormsEntityResponse (CustomerComplaintFormsEntity customerComplaintFormsEntity)  {
        this.customerComplaintFormsEntity = customerComplaintFormsEntity;
    }

    public CustomerComplaintFormsEntity getCustomerComplaintFormsEntityResponse() {
        return customerComplaintFormsEntity;
    }
}

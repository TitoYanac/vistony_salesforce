package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerComplaintFormsEntity {
    public String forms_id;
    @SerializedName("Name")
    public String forms;
    @SerializedName("Secciones")
    public List<CustomerComplaintSectionEntity> listCustomerComplaintSection;

    public String getForms_id() {
        return forms_id;
    }

    public void setForms_id(String forms_id) {
        this.forms_id = forms_id;
    }

    public String getForms() {
        return forms;
    }

    public void setForms(String forms) {
        this.forms = forms;
    }

    public List<CustomerComplaintSectionEntity> getListCustomerComplaintSection() {
        return listCustomerComplaintSection;
    }

    public void setListCustomerComplaintSection(List<CustomerComplaintSectionEntity> listCustomerComplaintSection) {
        this.listCustomerComplaintSection = listCustomerComplaintSection;
    }
}

package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerComplaintFormsEntity {
    public String forms_id;

    @SerializedName("Name")
    public String forms;

    @SerializedName("Secciones")
    public List<CustomerComplaintSectionEntity> listCustomerComplaintSection;

    public String forms_date;

    public String salesrepcode;

    public String user_id;

    public String time;

    public String getForms_date() {
        return forms_date;
    }

    public void setForms_date(String forms_date) {
        this.forms_date = forms_date;
    }

    public String getSalesrepcode() {
        return salesrepcode;
    }

    public void setSalesrepcode(String salesrepcode) {
        this.salesrepcode = salesrepcode;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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

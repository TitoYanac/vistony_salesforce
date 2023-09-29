package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessLayerSalesDetailHeaderEntity {
    @SerializedName("Code")
    public String Code;

    @SerializedName("Object")
    public String Object;

    @SerializedName("Name")
    public String Name;

    @SerializedName("Action")
    public String Action;

    @SerializedName("Detail")
    public List<BusinessLayerSalesDetailDetailEntity> Details;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getObject() {
        return Object;
    }

    public void setObject(String object) {
        Object = object;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public List<BusinessLayerSalesDetailDetailEntity> getDetails() {
        return Details;
    }

    public void setDetails(List<BusinessLayerSalesDetailDetailEntity> details) {
        Details = details;
    }
}

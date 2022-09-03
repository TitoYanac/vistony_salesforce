package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeaderStatusDispatchEntity {
    @SerializedName("DocEntry")
    public String DocEntry;

    @SerializedName("Details")
    public List<DetailStatusDispatchEntity> Details;

    public String getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(String docEntry) {
        DocEntry = docEntry;
    }

    public List<DetailStatusDispatchEntity> getDetails() {
        return Details;
    }

    public void setDetails(List<DetailStatusDispatchEntity> details) {
        Details = details;
    }
}

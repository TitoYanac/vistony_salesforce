package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SalesOrderEntity;

import java.util.List;

public class SalesOrderEntityResponse {
    @SerializedName("SalesOrders")
    private List<SalesOrderEntity> salesOrderEntity;

    public List<SalesOrderEntity> getSalesOrderEntity() {
        return salesOrderEntity;
    }

    public void setSalesOrderEntity(List<SalesOrderEntity> salesOrderEntity) {
        this.salesOrderEntity = salesOrderEntity;
    }
}

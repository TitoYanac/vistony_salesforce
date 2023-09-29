package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ListaPrecioEntity;

import java.util.List;

public class ListaPrecioDetalleWarehouseEntityResponse {
    @SerializedName("PriceList")
    private List<ListaPrecioEntity> listaPrecioEntity;

    public ListaPrecioDetalleWarehouseEntityResponse (List<ListaPrecioEntity> listaPrecioEntity)  {
        this.listaPrecioEntity = listaPrecioEntity;
    }

    public List<ListaPrecioEntity> getListaPrecioDetalleWarehouseEntity() {
        return listaPrecioEntity;
    }
}

package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ListaPrecioDetalleEntity;

import java.util.List;

public class ListaPrecioDetalleEntityResponse {
    @SerializedName("PriceList")
    private List<ListaPrecioDetalleEntity> listaPrecioDetalleEntity;

    public ListaPrecioDetalleEntityResponse (List<ListaPrecioDetalleEntity> listaPrecioDetalleEntity)  {
        this.listaPrecioDetalleEntity = listaPrecioDetalleEntity;
    }

    public List<ListaPrecioDetalleEntity> getListaPrecioDetalleEntity() {
        return listaPrecioDetalleEntity;
    }
}

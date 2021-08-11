package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ListaPromocionEntity;

import java.util.List;

public class ListaPromocionEntityResponse {
    @SerializedName("PromotionListType")
    private List<ListaPromocionEntity> listaPromocionEntity;

    public ListaPromocionEntityResponse (List<ListaPromocionEntity> listaPromocionEntity)  {
        this.listaPromocionEntity = listaPromocionEntity;
    }

    public List<ListaPromocionEntity> getListaPromocionEntity() {
        return listaPromocionEntity;
    }
}

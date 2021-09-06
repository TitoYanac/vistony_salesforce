package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CobranzaDetalleEntity {

    @SerializedName("Collections")
    private List<CobranzaItemEntity> cobranzaItem;

    public List<CobranzaItemEntity> getCobranzaItem() {
        return cobranzaItem;
    }

    public void setCobranzaItem(List<CobranzaItemEntity> cobranzaItem) {
        this.cobranzaItem = cobranzaItem;
    }
}

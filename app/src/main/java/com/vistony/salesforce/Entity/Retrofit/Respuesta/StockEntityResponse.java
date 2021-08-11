package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.StockEntity;

import java.util.List;

public class StockEntityResponse {
    @SerializedName("Stock")
    //@SerializedName("data")
    private List<StockEntity> stockEntity;

    public StockEntityResponse (List<StockEntity> stockEntity)  {
        this.stockEntity = stockEntity;
    }

    public List<StockEntity> getStockEntity() {
        return stockEntity;
    }
}

package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BancoEntity;

import java.util.List;

public class BancoEntityResponse {
    @SerializedName("Banks")
    //@SerializedName("data") //Pruebas Mockups Pedidos
    private List<BancoEntity> bancoEntity;

    public BancoEntityResponse (List<BancoEntity> bancoEntity)  {
        this.bancoEntity = bancoEntity;
    }

    public List<BancoEntity> getBancoEntity() {
        return bancoEntity;
    }

}

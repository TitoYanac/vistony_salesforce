package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.KardexPagoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ListaPrecioEntity;

import java.util.List;

public class KardexPagoEntityResponse {
    @SerializedName("Kardex")
    private List<KardexPagoEntity> kardexPagoEntity;

    public KardexPagoEntityResponse (List<KardexPagoEntity> kardexPagoEntity)  {
        this.kardexPagoEntity = kardexPagoEntity;
    }

    public List<KardexPagoEntity> getKardexPagoEntity() {
        return kardexPagoEntity;
    }
}

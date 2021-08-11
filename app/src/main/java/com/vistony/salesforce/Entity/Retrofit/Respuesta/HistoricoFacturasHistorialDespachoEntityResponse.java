package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricoFacturasHistorialDespachoEntity;

import java.util.List;

public class HistoricoFacturasHistorialDespachoEntityResponse {
    //@SerializedName("Pedidos_Obtener_AgenciasResult")
    @SerializedName("data") //Pruebas Mockups Pedidos
    private List<HistoricoFacturasHistorialDespachoEntity> historicoFacturasHistorialDespachoEntity;

    public HistoricoFacturasHistorialDespachoEntityResponse (List<HistoricoFacturasHistorialDespachoEntity> historicoFacturasHistorialDespachoEntity)  {
        this.historicoFacturasHistorialDespachoEntity = historicoFacturasHistorialDespachoEntity;
    }

    public List<HistoricoFacturasHistorialDespachoEntity> getHistoricoFacturasHistorialDespachoEntity() {
        return historicoFacturasHistorialDespachoEntity;
    }
}

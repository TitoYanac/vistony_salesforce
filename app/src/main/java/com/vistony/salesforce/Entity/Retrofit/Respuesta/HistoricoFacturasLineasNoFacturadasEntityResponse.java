package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricoFacturasLineasNoFacturadasEntity;

import java.util.List;

public class HistoricoFacturasLineasNoFacturadasEntityResponse {

    @SerializedName("data") //Pruebas Mockups Pedidos
    private List<HistoricoFacturasLineasNoFacturadasEntity> historicoFacturasLineasNoFacturadasEntity;

    public HistoricoFacturasLineasNoFacturadasEntityResponse (List<HistoricoFacturasLineasNoFacturadasEntity> historicoFacturasLineasNoFacturadasEntity)  {
        this.historicoFacturasLineasNoFacturadasEntity = historicoFacturasLineasNoFacturadasEntity;
    }

    public List<HistoricoFacturasLineasNoFacturadasEntity> getHistoricoFacturasLineasNoFacturadasEntity() {
        return historicoFacturasLineasNoFacturadasEntity;
    }
}

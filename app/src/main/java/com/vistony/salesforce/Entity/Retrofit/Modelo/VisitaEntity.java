package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;
import java.util.List;

public class VisitaEntity {

    @SerializedName("Visits")
    private List<VisitaSQLiteEntity> visitas;

    public List<VisitaSQLiteEntity> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<VisitaSQLiteEntity> visitas) {
        this.visitas = visitas;
    }
}

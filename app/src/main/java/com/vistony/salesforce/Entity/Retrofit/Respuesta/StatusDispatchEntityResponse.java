package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Dao.SQLite.StatusDispatchSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.StatusDispatchEntity;

import java.util.List;

public class StatusDispatchEntityResponse {
    @SerializedName("StatusDispatch")
    private List<StatusDispatchEntity> statusDispatchEntity;

    public void setStatusDispatch(List<StatusDispatchEntity> statusDispatchEntity) {
        this.statusDispatchEntity = statusDispatchEntity;
    }
    public List<StatusDispatchEntity> getStatusDispatch() {
        return statusDispatchEntity;
    }
}

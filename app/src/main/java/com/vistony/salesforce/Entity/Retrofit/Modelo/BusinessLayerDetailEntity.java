package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class BusinessLayerDetailEntity {
    @SerializedName("LineId")
    private String LineId;

    @SerializedName("U_VIS_Type")
    private String U_VIS_Type;

    @SerializedName("U_VIS_Object")
    private String U_VIS_Object;

    @SerializedName("U_VIS_Action")
    private String U_VIS_Action;

    public String getLineId() {
        return LineId;
    }

    public void setLineId(String lineId) {
        LineId = lineId;
    }

    public String getU_VIS_Type() {
        return U_VIS_Type;
    }

    public void setU_VIS_Type(String u_VIS_Type) {
        U_VIS_Type = u_VIS_Type;
    }

    public String getU_VIS_Object() {
        return U_VIS_Object;
    }

    public void setU_VIS_Object(String u_VIS_Object) {
        U_VIS_Object = u_VIS_Object;
    }

    public String getU_VIS_Action() {
        return U_VIS_Action;
    }

    public void setU_VIS_Action(String u_VIS_Action) {
        U_VIS_Action = u_VIS_Action;
    }
}

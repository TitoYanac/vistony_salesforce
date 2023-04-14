package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class UbigeoEntity {
    @SerializedName("Code")
    public String code;

    @SerializedName("County")
    public String U_SYP_DEPA;

    @SerializedName("City")
    public String U_SYP_PROV;

    @SerializedName("Block")
    public String U_SYP_DIST;

    @SerializedName("Flete")
    public String U_VIS_Flete;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getU_SYP_DEPA() {
        return U_SYP_DEPA;
    }

    public void setU_SYP_DEPA(String u_SYP_DEPA) {
        U_SYP_DEPA = u_SYP_DEPA;
    }

    public String getU_SYP_PROV() {
        return U_SYP_PROV;
    }

    public void setU_SYP_PROV(String u_SYP_PROV) {
        U_SYP_PROV = u_SYP_PROV;
    }

    public String getU_SYP_DIST() {
        return U_SYP_DIST;
    }

    public void setU_SYP_DIST(String u_SYP_DIST) {
        U_SYP_DIST = u_SYP_DIST;
    }

    public String getU_VIS_Flete() {
        return U_VIS_Flete;
    }

    public void setU_VIS_Flete(String u_VIS_Flete) {
        U_VIS_Flete = u_VIS_Flete;
    }
}

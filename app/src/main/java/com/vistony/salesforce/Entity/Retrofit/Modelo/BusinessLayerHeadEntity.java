package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessLayerHeadEntity {
    @SerializedName("Code")
    private String Code;

    @SerializedName("Name")
    private String Name;

    @SerializedName("U_VIS_Objetive")
    private String U_VIS_Objetive;

    @SerializedName("U_VIS_VariableType")
    private String U_VIS_VariableType;

    @SerializedName("U_VIS_Variable")
    private String U_VIS_Variable;

    @SerializedName("U_VIS_Trigger")
    private String U_VIS_Trigger;

    @SerializedName("U_VIS_TriggerType")
    private String U_VIS_TriggerType;

    @SerializedName("U_VIS_Active")
    private String U_VIS_Active;

    @SerializedName("U_VIS_ValidFrom")
    private String U_VIS_ValidFrom;

    @SerializedName("U_VIS_ValidUntil")
    private String U_VIS_ValidUntil;

    @SerializedName("Detail")
    private List<BusinessLayerDetailEntity> Details;

    public List<BusinessLayerDetailEntity> getDetails() {
        return Details;
    }

    public void setDetails(List<BusinessLayerDetailEntity> details) {
        Details = details;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getU_VIS_Objetive() {
        return U_VIS_Objetive;
    }

    public void setU_VIS_Objetive(String u_VIS_Objetive) {
        U_VIS_Objetive = u_VIS_Objetive;
    }

    public String getU_VIS_VariableType() {
        return U_VIS_VariableType;
    }

    public void setU_VIS_VariableType(String u_VIS_VariableType) {
        U_VIS_VariableType = u_VIS_VariableType;
    }

    public String getU_VIS_Variable() {
        return U_VIS_Variable;
    }

    public void setU_VIS_Variable(String u_VIS_Variable) {
        U_VIS_Variable = u_VIS_Variable;
    }

    public String getU_VIS_Trigger() {
        return U_VIS_Trigger;
    }

    public void setU_VIS_Trigger(String u_VIS_Trigger) {
        U_VIS_Trigger = u_VIS_Trigger;
    }

    public String getU_VIS_TriggerType() {
        return U_VIS_TriggerType;
    }

    public void setU_VIS_TriggerType(String u_VIS_TriggerType) {
        U_VIS_TriggerType = u_VIS_TriggerType;
    }

    public String getU_VIS_Active() {
        return U_VIS_Active;
    }

    public void setU_VIS_Active(String u_VIS_Active) {
        U_VIS_Active = u_VIS_Active;
    }

    public String getU_VIS_ValidFrom() {
        return U_VIS_ValidFrom;
    }

    public void setU_VIS_ValidFrom(String u_VIS_ValidFrom) {
        U_VIS_ValidFrom = u_VIS_ValidFrom;
    }

    public String getU_VIS_ValidUntil() {
        return U_VIS_ValidUntil;
    }

    public void setU_VIS_ValidUntil(String u_VIS_ValidUntil) {
        U_VIS_ValidUntil = u_VIS_ValidUntil;
    }
}

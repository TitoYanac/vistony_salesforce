package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessLayerSalesDetailDetailEntity {

    @SerializedName("LineID")
    public String LineId;

    @SerializedName("RangeActive")
    public String RangeActive;

    @SerializedName("Object")
    public String Object;

    @SerializedName("TypeObject")
    public String TypeObject;

    @SerializedName("ValueMin")
    public String ValueMin;

    @SerializedName("ValueMax")
    public String ValueMax;

    @SerializedName("Field")
    public String Field;

    @SerializedName("Variable")
    public String Variable;

    public String getLineId() {
        return LineId;
    }

    public void setLineId(String lineId) {
        LineId = lineId;
    }

    public String getRangeActive() {
        return RangeActive;
    }

    public void setRangeActive(String rangeActive) {
        RangeActive = rangeActive;
    }

    public String getObject() {
        return Object;
    }

    public void setObject(String object) {
        Object = object;
    }

    public String getTypeObject() {
        return TypeObject;
    }

    public void setTypeObject(String typeObject) {
        TypeObject = typeObject;
    }

    public String getValueMin() {
        return ValueMin;
    }

    public void setValueMin(String valueMin) {
        ValueMin = valueMin;
    }

    public String getValueMax() {
        return ValueMax;
    }

    public void setValueMax(String valueMax) {
        ValueMax = valueMax;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public String getVariable() {
        return Variable;
    }

    public void setVariable(String variable) {
        Variable = variable;
    }
}

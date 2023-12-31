package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeaderDispatchSheetEntity {

    @SerializedName("DocEntry")
    private String control_id;

    @SerializedName("AssistantCode")
    private String asistente_id;

    @SerializedName("Assistant")
    private String asistente;

    @SerializedName("LicensePlate")
    private String placa;

    @SerializedName("Brand")
    private String marca;

    @SerializedName("OverallWeight")
    private String peso_total;

    @SerializedName("Details")
    //@SerializedName("Detail")
    private List<DetailDispatchSheetEntity> ListDedailDispatch;

    @SerializedName("TotalDocument")
    private String TotalDocument;

    @SerializedName("DriverCode")
    private String DriverCode;

    @SerializedName("VehicleCode")
    private String VehiculeCode;

    @SerializedName("VehiclePlate")
    private String VehiclePlate;

    @SerializedName("DriverMobile")
    private String DriverMobile;

    @SerializedName("DriverName")
    private String DriverName;

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public String getDriverMobile() {
        return DriverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        DriverMobile = driverMobile;
    }

    public String getVehiclePlate() {
        return VehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        VehiclePlate = vehiclePlate;
    }

    public String getDriverCode() {
        return DriverCode;
    }

    public void setDriverCode(String driverCode) {
        DriverCode = driverCode;
    }

    public String getVehiculeCode() {
        return VehiculeCode;
    }

    public void setVehiculeCode(String vehiculeCode) {
        VehiculeCode = vehiculeCode;
    }

    public String getTotalDocument() {
        return TotalDocument;
    }

    public void setTotalDocument(String totalDocument) {
        TotalDocument = totalDocument;
    }

    public List<DetailDispatchSheetEntity> getListDedailDispatch() {
        return ListDedailDispatch;
    }

    public void setListDedailDispatch(List<DetailDispatchSheetEntity> listDedailDispatch) {
        ListDedailDispatch = listDedailDispatch;
    }

    public String getControl_id() {
        return control_id;
    }

    public void setControl_id(String control_id) {
        this.control_id = control_id;
    }

    public String getAsistente_id() {
        return asistente_id;
    }

    public void setAsistente_id(String asistente_id) {
        this.asistente_id = asistente_id;
    }

    public String getAsistente() {
        return asistente;
    }

    public void setAsistente(String asistente) {
        this.asistente = asistente;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPeso_total() {
        return peso_total;
    }

    public void setPeso_total(String peso_total) {
        this.peso_total = peso_total;
    }
}

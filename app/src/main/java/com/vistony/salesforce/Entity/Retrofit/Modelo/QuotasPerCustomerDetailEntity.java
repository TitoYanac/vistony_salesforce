package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class QuotasPerCustomerDetailEntity {
    @NonNull
    @SerializedName("QuotaNumber")
    private String cuota;
    @NonNull
    @SerializedName("Due")
    private String vencido;
    @NonNull
    @SerializedName("Corriente")
    private String corriente;
    @NonNull
    @SerializedName("Order")
    private String pedido;
    @NonNull
    @SerializedName("Date")
    private String fecha;
    @NonNull
    @SerializedName("Total")
    private String total;

    @NonNull
    public String getTotal() {
        return total;
    }

    public void setTotal(@NonNull String total) {
        this.total = total;
    }

    @NonNull
    public String getCuota() {
        return cuota;
    }

    public void setCuota(@NonNull String cuota) {
        this.cuota = cuota;
    }

    @NonNull
    public String getVencido() {
        return vencido;
    }

    public void setVencido(@NonNull String vencido) {
        this.vencido = vencido;
    }

    @NonNull
    public String getCorriente() {
        return corriente;
    }

    public void setCorriente(@NonNull String corriente) {
        this.corriente = corriente;
    }

    @NonNull
    public String getPedido() {
        return pedido;
    }

    public void setPedido(@NonNull String pedido) {
        this.pedido = pedido;
    }

    @NonNull
    public String getFecha() {
        return fecha;
    }

    public void setFecha(@NonNull String fecha) {
        this.fecha = fecha;
    }
}

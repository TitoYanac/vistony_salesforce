package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class HistoricStatusDispatchEntity {

    @SerializedName("FuerzaTrabajo_ID")
    private String FuerzaTrabajo_ID;

    @SerializedName("Usuario_ID")
    private String Usuario_ID;

    @SerializedName("TipoDespacho_ID")
    private String TipoDespacho_ID;

    @SerializedName("TipoDespacho")
    private String TipoDespacho;

    @SerializedName("MotivoDespacho_ID")
    private String MotivoDespacho_ID;

    @SerializedName("MotivoDespacho")
    private String MotivoDespacho;

    @SerializedName("Observacion")
    private String Observacion;

    @SerializedName("Latitud")
    private String Latitud;

    @SerializedName("Longitud")
    private String Longitud;

    @SerializedName("Fecha")
    private String Fecha;

    @SerializedName("Hora")
    private String Hora;

    @SerializedName("Entrega_ID")
    private String Entrega_ID;

    @SerializedName("Cliente_ID")
    private String Cliente_ID;

    @SerializedName("Cliente")
    private String Cliente;

    @SerializedName("Entrega")
    private String Entrega;

    public HistoricStatusDispatchEntity(String fuerzaTrabajo_ID, String usuario_ID, String tipoDespacho_ID, String tipoDespacho, String motivoDespacho_ID, String motivoDespacho, String observacion, String latitud, String longitud, String fecha, String hora, String entrega_ID, String cliente_ID, String cliente, String entrega) {
        FuerzaTrabajo_ID = fuerzaTrabajo_ID;
        Usuario_ID = usuario_ID;
        TipoDespacho_ID = tipoDespacho_ID;
        TipoDespacho = tipoDespacho;
        MotivoDespacho_ID = motivoDespacho_ID;
        MotivoDespacho = motivoDespacho;
        Observacion = observacion;
        Latitud = latitud;
        Longitud = longitud;
        Fecha = fecha;
        Hora = hora;
        Entrega_ID = entrega_ID;
        Cliente_ID = cliente_ID;
        Cliente = cliente;
        Entrega = entrega;
    }

    public HistoricStatusDispatchEntity() {

    }

    public String getFuerzaTrabajo_ID() {
        return FuerzaTrabajo_ID;
    }

    public void setFuerzaTrabajo_ID(String fuerzaTrabajo_ID) {
        FuerzaTrabajo_ID = fuerzaTrabajo_ID;
    }

    public String getUsuario_ID() {
        return Usuario_ID;
    }

    public void setUsuario_ID(String usuario_ID) {
        Usuario_ID = usuario_ID;
    }

    public String getTipoDespacho_ID() {
        return TipoDespacho_ID;
    }

    public void setTipoDespacho_ID(String tipoDespacho_ID) {
        TipoDespacho_ID = tipoDespacho_ID;
    }

    public String getTipoDespacho() {
        return TipoDespacho;
    }

    public void setTipoDespacho(String tipoDespacho) {
        TipoDespacho = tipoDespacho;
    }

    public String getMotivoDespacho_ID() {
        return MotivoDespacho_ID;
    }

    public void setMotivoDespacho_ID(String motivoDespacho_ID) {
        MotivoDespacho_ID = motivoDespacho_ID;
    }

    public String getMotivoDespacho() {
        return MotivoDespacho;
    }

    public void setMotivoDespacho(String motivoDespacho) {
        MotivoDespacho = motivoDespacho;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        Observacion = observacion;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getEntrega_ID() {
        return Entrega_ID;
    }

    public void setEntrega_ID(String entrega_ID) {
        Entrega_ID = entrega_ID;
    }

    public String getCliente_ID() {
        return Cliente_ID;
    }

    public void setCliente_ID(String cliente_ID) {
        Cliente_ID = cliente_ID;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getEntrega() {
        return Entrega;
    }

    public void setEntrega(String entrega) {
        Entrega = entrega;
    }
}

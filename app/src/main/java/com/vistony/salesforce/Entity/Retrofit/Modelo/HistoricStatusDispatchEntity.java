package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HistoricStatusDispatchEntity implements Serializable  {

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

    private String Factura_ID;

    private String Factura;

    private String FotoLocal;

    private String FotoGuia;

    private String Chk_Recibido;

    private String messageServerDispatch;

    private String messageServerTimeDispatch;

    private String drivermobile;

    private String drivername;

    public HistoricStatusDispatchEntity(
            String fuerzaTrabajo_ID, String usuario_ID, String tipoDespacho_ID, String tipoDespacho,
            String motivoDespacho_ID, String motivoDespacho, String observacion, String latitud,
            String longitud, String fecha, String hora, String entrega_ID, String cliente_ID,
            String cliente, String entrega,String factura_ID,String factura
            ,String FotoLocal,String FotoGuia,String Chk_Recibido,String messageServerDispatch
            ,String messageServerTimeDispatch,String drivermobile,String drivername
    ) {
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
        Factura_ID = factura_ID;
        Factura = factura;
        FotoLocal = FotoLocal;
        FotoGuia = FotoGuia;
        this.Chk_Recibido=Chk_Recibido;
        this.messageServerDispatch=messageServerDispatch;
        this.messageServerTimeDispatch=messageServerTimeDispatch;
        this.drivermobile=drivermobile;
        this.drivername=drivername;

    }

    public HistoricStatusDispatchEntity() {

    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getDrivermobile() {
        return drivermobile;
    }

    public void setDrivermobile(String drivermobile) {
        this.drivermobile = drivermobile;
    }

    public String getMessageServerDispatch() {
        return messageServerDispatch;
    }

    public void setMessageServerDispatch(String messageServerDispatch) {
        this.messageServerDispatch = messageServerDispatch;
    }

    public String getMessageServerTimeDispatch() {
        return messageServerTimeDispatch;
    }

    public void setMessageServerTimeDispatch(String messageServerTimeDispatch) {
        this.messageServerTimeDispatch = messageServerTimeDispatch;
    }

    public String getChk_Recibido() {
        return Chk_Recibido;
    }

    public void setChk_Recibido(String chk_Recibido) {
        Chk_Recibido = chk_Recibido;
    }

    public String getFotoLocal() {
        return FotoLocal;
    }

    public void setFotoLocal(String fotoLocal) {
        FotoLocal = fotoLocal;
    }

    public String getFotoGuia() {
        return FotoGuia;
    }

    public void setFotoGuia(String fotoGuia) {
        FotoGuia = fotoGuia;
    }

    public String getFactura_ID() {
        return Factura_ID;
    }

    public void setFactura_ID(String factura_ID) {
        Factura_ID = factura_ID;
    }

    public String getFactura() {
        return Factura;
    }

    public void setFactura(String factura) {
        Factura = factura;
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

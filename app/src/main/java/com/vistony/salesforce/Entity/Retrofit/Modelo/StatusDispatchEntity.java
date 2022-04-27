package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class StatusDispatchEntity {
    public String compania_id;

    public String fuerzatrabajo_id;

    public String usuario_id;

    @SerializedName("typedispatch_id")
    public String typedispatch_id;

    @SerializedName("reasondispatch_id")
    public String reasondispatch_id;

    @SerializedName("cliente_id")
    public String cliente_id;

    @SerializedName("factura_id")
    public String factura_id;

    @SerializedName("entrega_id")
    public String entrega_id;

    @SerializedName("chkrecibido")
    public String chkrecibido;

    @SerializedName("observation")
    public String observation;

    public String foto;

    public String fecha_registro;

    public String hora_registro;

    public String fotoGuia;

    public String latitud;

    public String longitud;

    public String cliente;

    public String entrega;

    public String factura;

    public String typedispatch;

    public String reasondispatch;

    public StatusDispatchEntity(String compania_id, String fuerzatrabajo_id, String usuario_id, String typedispatch_id, String reasondispatch_id, String cliente_id, String factura_id, String entrega_id, String chkrecibido, String observation,
   String foto,String fecha_registro,String hora_registro,String fotoGuia,String latitud,String longitud,
                                String cliente,String entrega,String factura,String typedispatch,String reasondispatch
                                ) {
        this.compania_id = compania_id;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        this.typedispatch_id = typedispatch_id;
        this.reasondispatch_id = reasondispatch_id;
        this.cliente_id = cliente_id;
        this.factura_id = factura_id;
        this.entrega_id = entrega_id;
        this.chkrecibido = chkrecibido;
        this.observation = observation;
        this.foto = foto;
        this.fecha_registro = fecha_registro;
        this.hora_registro = hora_registro;
        this.fotoGuia = fotoGuia;
        this.latitud = latitud;
        this.longitud = longitud;
        this.cliente = cliente;
        this.entrega = entrega;
        this.factura = factura;
        this.typedispatch = typedispatch;
        this.reasondispatch = reasondispatch;
    }

    public String getTypedispatch() {
        return typedispatch;
    }

    public void setTypedispatch(String typedispatch) {
        this.typedispatch = typedispatch;
    }

    public String getReasondispatch() {
        return reasondispatch;
    }

    public void setReasondispatch(String reasondispatch) {
        this.reasondispatch = reasondispatch;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFotoGuia() {
        return fotoGuia;
    }

    public void setFotoGuia(String fotoGuia) {
        this.fotoGuia = fotoGuia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getHora_registro() {
        return hora_registro;
    }

    public void setHora_registro(String hora_registro) {
        this.hora_registro = hora_registro;
    }

    public StatusDispatchEntity() {
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getTypedispatch_id() {
        return typedispatch_id;
    }

    public void setTypedispatch_id(String typedispatch_id) {
        this.typedispatch_id = typedispatch_id;
    }

    public String getReasondispatch_id() {
        return reasondispatch_id;
    }


    public void setReasondispatch_id(String reasondispatch_id) {
        this.reasondispatch_id = reasondispatch_id;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getFactura_id() {
        return factura_id;
    }

    public void setFactura_id(String factura_id) {
        this.factura_id = factura_id;
    }

    public String getEntrega_id() {
        return entrega_id;
    }

    public void setEntrega_id(String entrega_id) {
        this.entrega_id = entrega_id;
    }

    public String getChkrecibido() {
        return chkrecibido;
    }

    public void setChkrecibido(String chkrecibido) {
        this.chkrecibido = chkrecibido;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}

package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class StatusDispatchEntity {
    public String compania_id;

    @SerializedName("UserCode")
    public String fuerzatrabajo_id;



    public String usuario_id;

    @SerializedName("Delivered")
    public String Delivered;

    @SerializedName("ReturnReason")
    public String ReturnReason;

    @SerializedName("cliente_id")
    public String cliente_id;

    @SerializedName("factura_id")
    public String factura_id;

    @SerializedName("entrega_id")
    public String entrega_id;

    @SerializedName("chkrecibido")
    public String chkrecibido;

    @SerializedName("Comments")
    public String Comments;

    public String foto;

    public String fecha_registro;

    public String hora_registro;

    public String PhotoDocument;

    @SerializedName("Latitude")
    public String latitud;

    @SerializedName("Longitude")
    public String longitud;

    public String cliente;

    public String entrega;

    public String factura;

    public String typedispatch;

    public String reasondispatch;

    @SerializedName("ErrorCode")
    public String haveError;

    @SerializedName("Message")
    public String Message;

    @SerializedName("IdStatusDispatch")
    public String idStatusDispatch;

    @SerializedName("LineId")
    public String LineId;

    public String PhotoStore;

    @SerializedName("DocEntry")
    public String DocEntry;

    public String Address;

    public String checkintime;

    public String checkouttime;

    public String chk_timestatus;

    @SerializedName("UserName")
    public String fuerzatrabajo;

    public StatusDispatchEntity(String compania_id, String fuerzatrabajo_id, String usuario_id, String Delivered, String ReturnReason, String cliente_id, String factura_id, String entrega_id, String chkrecibido, String Comments,
                                String foto, String fecha_registro, String hora_registro, String PhotoDocument, String latitud, String longitud,
                                String cliente, String entrega, String factura, String typedispatch, String reasondispatch, String LineId
            , String PhotoStore,String DocEntry,String Address,String checkintime,String checkouttime
                                ,String chk_timestatus,String fuerzatrabajo
                                ) {
        this.compania_id = compania_id;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        this.Delivered = Delivered;
        this.ReturnReason = ReturnReason;
        this.cliente_id = cliente_id;
        this.factura_id = factura_id;
        this.entrega_id = entrega_id;
        this.chkrecibido = chkrecibido;
        this.Comments = Comments;
        this.foto = foto;
        this.fecha_registro = fecha_registro;
        this.hora_registro = hora_registro;
        this.PhotoDocument = PhotoDocument;
        this.latitud = latitud;
        this.longitud = longitud;
        this.cliente = cliente;
        this.entrega = entrega;
        this.factura = factura;
        this.typedispatch = typedispatch;
        this.reasondispatch = reasondispatch;
        this.LineId=LineId;
        this.PhotoStore = PhotoStore;
        this.DocEntry=DocEntry;
        this.Address=Address;
        this.checkintime=checkintime;
        this.checkouttime=checkouttime;
        this.chk_timestatus=chk_timestatus;
        this.fuerzatrabajo=fuerzatrabajo;

    }

    public String getFuerzatrabajo() {
        return fuerzatrabajo;
    }

    public void setFuerzatrabajo(String fuerzatrabajo) {
        this.fuerzatrabajo = fuerzatrabajo;
    }

    public String getChk_timestatus() {
        return chk_timestatus;
    }

    public void setChk_timestatus(String chk_timestatus) {
        this.chk_timestatus = chk_timestatus;
    }

    public String getCheckintime() {
        return checkintime;
    }

    public void setCheckintime(String checkintime) {
        this.checkintime = checkintime;
    }

    public String getCheckouttime() {
        return checkouttime;
    }

    public void setCheckouttime(String checkouttime) {
        this.checkouttime = checkouttime;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(String docEntry) {
        DocEntry = docEntry;
    }

    public String getPhotoStore() {
        return PhotoStore;
    }

    public void setPhotoStore(String photoStore) {
        PhotoStore = photoStore;
    }


    public String getLineId() {
        return LineId;
    }

    public void setLineId(String lineId) {
        LineId = lineId;
    }

    public String getHaveError() {
        return haveError;
    }

    public void setHaveError(String haveError) {
        this.haveError = haveError;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getIdStatusDispatch() {
        return idStatusDispatch;
    }

    public void setIdStatusDispatch(String idStatusDispatch) {
        this.idStatusDispatch = idStatusDispatch;
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

    public String getPhotoDocument() {
        return PhotoDocument;
    }

    public void setPhotoDocument(String photoDocument) {
        this.PhotoDocument = photoDocument;
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

    public String getDelivered() {
        return Delivered;
    }

    public void setDelivered(String delivered) {
        this.Delivered = delivered;
    }

    public String getReturnReason() {
        return ReturnReason;
    }


    public void setReturnReason(String returnReason) {
        this.ReturnReason = returnReason;
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

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        this.Comments = comments;
    }
}

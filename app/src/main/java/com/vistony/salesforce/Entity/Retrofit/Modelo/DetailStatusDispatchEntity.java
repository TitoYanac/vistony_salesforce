package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class DetailStatusDispatchEntity {
    @SerializedName("LineId")
    public String LineId;

    @SerializedName("checkintime")
    public String checkintime;

    @SerializedName("checkouttime")
    public String checkouttime;

    @SerializedName("Latitude")
    public String latitud;

    @SerializedName("Longitude")
    public String longitud;

    @SerializedName("UserCode")
    public String fuerzatrabajo_id;

    @SerializedName("Delivered")
    public String Delivered;

    @SerializedName("ReturnReasonValue")
    public String ReturnReason;

    @SerializedName("Comments")
    public String Comments;

    @SerializedName("PhotoDocument")
    public String PhotoDocument;

    @SerializedName("PhotoStore")
    public String PhotoStore;

    @SerializedName("UserName")
    public String fuerzatrabajo;

    @SerializedName("entrega_id")
    public String entrega_id;

    @SerializedName("ErrorCode")
    public String haveError;

    @SerializedName("Message")
    public String Message;

    @SerializedName("DeliveryNotes")
    public String DeliveryNotes;

    @SerializedName("ReturnReasonText")
    public String ReturnReasonText;

    public String getReturnReasonText() {
        return ReturnReasonText;
    }

    public void setReturnReasonText(String returnReasonText) {
        ReturnReasonText = returnReasonText;
    }

    public String getDeliveryNotes() {
        return DeliveryNotes;
    }

    public void setDeliveryNotes(String deliveryNotes) {
        DeliveryNotes = deliveryNotes;
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

    public String getEntrega_id() {
        return entrega_id;
    }

    public void setEntrega_id(String entrega_id) {
        this.entrega_id = entrega_id;
    }

    public String getPhotoDocument() {
        return PhotoDocument;
    }

    public void setPhotoDocument(String photoDocument) {
        PhotoDocument = photoDocument;
    }

    public String getPhotoStore() {
        return PhotoStore;
    }

    public void setPhotoStore(String photoStore) {
        PhotoStore = photoStore;
    }

    public String getFuerzatrabajo() {
        return fuerzatrabajo;
    }

    public void setFuerzatrabajo(String fuerzatrabajo) {
        this.fuerzatrabajo = fuerzatrabajo;
    }

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    public String getDelivered() {
        return Delivered;
    }

    public void setDelivered(String delivered) {
        Delivered = delivered;
    }

    public String getReturnReason() {
        return ReturnReason;
    }

    public void setReturnReason(String returnReason) {
        ReturnReason = returnReason;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getLineId() {
        return LineId;
    }

    public void setLineId(String lineId) {
        LineId = lineId;
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
}

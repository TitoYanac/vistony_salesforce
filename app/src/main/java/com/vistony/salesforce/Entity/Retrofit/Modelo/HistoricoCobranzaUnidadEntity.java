package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class HistoricoCobranzaUnidadEntity {
    @SerializedName("Bancarizacion")
    private String bancarizacion;

    @SerializedName("Banco_ID")
    private String banco_id;

    @SerializedName("Cliente_ID")
    private String cliente_id;

    @SerializedName("Cliente_Nombre")
    private String nombrecliente;

    @SerializedName("Comentario")
    private String comentario;

    @SerializedName("Compania_ID")
    private String compania_id;

    @SerializedName("DepositoDirecto")
    private String depositodirecto;

    @SerializedName("Deposito_ID")
    private String deposito_id;

    @SerializedName("Detalle_Item")
    private String detalle_item;

    @SerializedName("Documento_ID")
    private String documento_id;

    @SerializedName("Estado")
    private String estado;

    @SerializedName("EstadoQR")
    private String estadoqr;

    @SerializedName("FechaCobranza")
    private String fechacobranza;

    @SerializedName("FechaDeposito")
    private String fechadeposito;

    @SerializedName("Fuerzatrabajo_ID")
    private String fuerzatrabajo_id;

    @SerializedName("ImporteDocumento")
    private String importedocumento;

    @SerializedName("MontoCobrado")
    private String montocobrado;

    @SerializedName("MotivoAnulacion")
    private String motivoanulacion;

    @SerializedName("Nro_Documento")
    private String nro_documento;

    @SerializedName("NuevoSaldoDocumento")
    private String nuevosaldodocumento;

    @SerializedName("PagoPOS")
    private String pagopos;

    @SerializedName("Recibo")
    private String recibo;

    @SerializedName("SaldoDocumento")
    private String saldodocumento;

    @SerializedName("TipoIngreso")
    private String tipoingreso;

    @SerializedName("Usuario_ID")
    private String usuario_id;

    public String getPagopos() {
        return pagopos;
    }

    public void setPagopos(String pagopos) {
        this.pagopos = pagopos;
    }

    public String getDeposito_id() {
        return deposito_id;
    }

    public void setDeposito_id(String deposito_id) {
        this.deposito_id = deposito_id;
    }

    public String getImportedocumento() {
        return importedocumento;
    }

    public void setImportedocumento(String importedocumento) {
        this.importedocumento = importedocumento;
    }

    public String getMontocobrado() {
        return montocobrado;
    }

    public void setMontocobrado(String montocobrado) {
        this.montocobrado = montocobrado;
    }

    public String getNro_documento() {
        return nro_documento;
    }

    public void setNro_documento(String nro_documento) {
        this.nro_documento = nro_documento;
    }

    @NonNull
    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(@NonNull String cliente_id) {
        this.cliente_id = cliente_id;
    }

    @NonNull
    public String getBancarizacion() {
        return bancarizacion;
    }

    public void setBancarizacion(@NonNull String bancarizacion) {
        this.bancarizacion = bancarizacion;
    }

    @NonNull
    public String getBanco_id() {
        return banco_id;
    }

    public void setBanco_id(@NonNull String banco_id) {
        this.banco_id = banco_id;
    }

    @NonNull
    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(@NonNull String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    @NonNull
    public String getComentario() {
        return comentario;
    }

    public void setComentario(@NonNull String comentario) {
        this.comentario = comentario;
    }

    @NonNull
    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(@NonNull String compania_id) {
        this.compania_id = compania_id;
    }

    @NonNull
    public String getDepositodirecto() {
        return depositodirecto;
    }

    public void setDepositodirecto(@NonNull String depositodirecto) {
        this.depositodirecto = depositodirecto;
    }

    @NonNull
    public String getDetalle_item() {
        return detalle_item;
    }

    public void setDetalle_item(@NonNull String detalle_item) {
        this.detalle_item = detalle_item;
    }

    @NonNull
    public String getDocumento_id() {
        return documento_id;
    }

    public void setDocumento_id(@NonNull String documento_id) {
        this.documento_id = documento_id;
    }

    @NonNull
    public String getEstado() {
        return estado;
    }

    public void setEstado(@NonNull String estado) {
        this.estado = estado;
    }

    @NonNull
    public String getEstadoqr() {
        return estadoqr;
    }

    public void setEstadoqr(@NonNull String estadoqr) {
        this.estadoqr = estadoqr;
    }

    @NonNull
    public String getFechacobranza() {
        return fechacobranza;
    }

    public void setFechacobranza(@NonNull String fechacobranza) {
        this.fechacobranza = fechacobranza;
    }

    @NonNull
    public String getFechadeposito() {
        return fechadeposito;
    }

    public void setFechadeposito(@NonNull String fechadeposito) {
        this.fechadeposito = fechadeposito;
    }

    @NonNull
    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(@NonNull String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }




    @NonNull
    public String getMotivoanulacion() {
        return motivoanulacion;
    }

    public void setMotivoanulacion(@NonNull String motivoanulacion) {
        this.motivoanulacion = motivoanulacion;
    }


    @NonNull
    public String getNuevosaldodocumento() {
        return nuevosaldodocumento;
    }

    public void setNuevosaldodocumento(@NonNull String nuevosaldodocumento) {
        this.nuevosaldodocumento = nuevosaldodocumento;
    }

    @NonNull
    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(@NonNull String recibo) {
        this.recibo = recibo;
    }

    @NonNull
    public String getSaldodocumento() {
        return saldodocumento;
    }

    public void setSaldodocumento(@NonNull String saldodocumento) {
        this.saldodocumento = saldodocumento;
    }

    @NonNull
    public String getTipoingreso() {
        return tipoingreso;
    }

    public void setTipoingreso(@NonNull String tipoingreso) {
        this.tipoingreso = tipoingreso;
    }

    @NonNull
    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(@NonNull String usuario_id) {
        this.usuario_id = usuario_id;
    }
}

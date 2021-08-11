package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class HistoricoDepositoUnidadEntity {

    @SerializedName("Bancarizacion")
    private String bancarizacion;

    @SerializedName("Banco_ID")
    private String banco_id;

    @SerializedName("Comentario")
    private String comentario;

    @SerializedName("Compania_ID")
    private String compania_id;

    @SerializedName("DepositoDirecto")
    private String depositodirecto;

    @SerializedName("Deposito_ID")
    private String deposito_id;

    @SerializedName("Estado")
    private String estado;

    @SerializedName("FechaDeposito")
    private String fechadeposito;

    @SerializedName("FechaDiferida")
    private String fechadiferida;

    @SerializedName("FuerzaTrabajo_ID")
    private String fuerzaTrabajo_id;

    @SerializedName("MontoDeposito")
    private String montodeposito;

    @SerializedName("MotivoAnulacion")
    private String motivoanulacion;

    @SerializedName("TipoIngreso")
    private String tipoingreso;

    @SerializedName("Usuario_ID")
    private String usuario_id;

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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
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
    public String getDeposito_id() {
        return deposito_id;
    }

    public void setDeposito_id(@NonNull String deposito_id) {
        this.deposito_id = deposito_id;
    }

    @NonNull
    public String getEstado() {
        return estado;
    }

    public void setEstado(@NonNull String estado) {
        this.estado = estado;
    }

    @NonNull
    public String getFechadeposito() {
        return fechadeposito;
    }

    public void setFechadeposito(@NonNull String fechadeposito) {
        this.fechadeposito = fechadeposito;
    }

    @NonNull
    public String getFechadiferida() {
        return fechadiferida;
    }

    public void setFechadiferida(@NonNull String fechadiferida) {
        this.fechadiferida = fechadiferida;
    }

    @NonNull
    public String getFuerzaTrabajo_id() {
        return fuerzaTrabajo_id;
    }

    public void setFuerzaTrabajo_id(@NonNull String fuerzaTrabajo_id) {
        this.fuerzaTrabajo_id = fuerzaTrabajo_id;
    }

    @NonNull
    public String getMontodeposito() {
        return montodeposito;
    }

    public void setMontodeposito(@NonNull String montodeposito) {
        this.montodeposito = montodeposito;
    }

    @NonNull
    public String getMotivoanulacion() {
        return motivoanulacion;
    }

    public void setMotivoanulacion(@NonNull String motivoanulacion) {
        this.motivoanulacion = motivoanulacion;
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

package com.vistony.salesforce.Entity.SQLite;

public class ConfiguracionSQLEntity {
   public String papel;
   public String tamanio;
   public String totalrecibos;
   public String secuenciarecibos;
   public String modeloimpresora;
   public String direccionimpresora;
   public String tipoimpresora;
   public String vinculaimpresora;

    public ConfiguracionSQLEntity(String papel, String tamanio, String totalrecibos, String secuenciarecibos,String modeloimpresora,String direccionimpresora,String tipoimpresora
    ,String vinculaimpresora
    ) {
        this.papel = papel;
        this.tamanio = tamanio;
        this.totalrecibos = totalrecibos;
        this.secuenciarecibos = secuenciarecibos;
        this.modeloimpresora = modeloimpresora;
        this.direccionimpresora = direccionimpresora;
        this.tipoimpresora = tipoimpresora;
        this.vinculaimpresora = vinculaimpresora;
    }

    public ConfiguracionSQLEntity() {
    }

    public String getModeloimpresora() {
        return modeloimpresora;
    }

    public void setModeloimpresora(String modeloimpresora) {
        this.modeloimpresora = modeloimpresora;
    }

    public String getDireccionimpresora() {
        return direccionimpresora;
    }

    public void setDireccionimpresora(String direccionimpresora) {
        this.direccionimpresora = direccionimpresora;
    }

    public String getTipoimpresora() {
        return tipoimpresora;
    }

    public void setTipoimpresora(String tipoimpresora) {
        this.tipoimpresora = tipoimpresora;
    }

    public String getVinculaimpresora() {
        return vinculaimpresora;
    }

    public void setVinculaimpresora(String vinculaimpresora) {
        this.vinculaimpresora = vinculaimpresora;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public String getTotalrecibos() {
        return totalrecibos;
    }

    public void setTotalrecibos(String totalrecibos) {
        this.totalrecibos = totalrecibos;
    }

    public String getSecuenciarecibos() {
        return secuenciarecibos;
    }

    public void setSecuenciarecibos(String secuenciarecibos) {
        this.secuenciarecibos = secuenciarecibos;
    }
}

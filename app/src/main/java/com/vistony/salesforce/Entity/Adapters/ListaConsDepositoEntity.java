package com.vistony.salesforce.Entity.Adapters;

public class ListaConsDepositoEntity {
    public String cliente_id;
    public String nombrecliente;
    public String recibo;
    public String documento_id;
    public String nrodocumento;
    public String fechacobranza;
    public String importe;
    public String saldo;
    public String cobrado;
    public String nuevosaldo;
    public boolean checkbox;
    public String tv_txtbancarizado;
    public String tv_txtpagodirecto;

    public ListaConsDepositoEntity(String cliente_id,
                                   String nombrecliente,
                                   String recibo,
                                   String documento_id,
                                   String nrodocumento,
                                   String fechacobranza,
                                   String importe,
                                   String saldo,
                                   String cobrado,
                                   String nuevosaldo,
                                   boolean checkbox,
                                   String tv_txtbancarizado,
                                   String tv_txtpagodirecto
    ) {
        this.cliente_id = cliente_id;
        this.nombrecliente = nombrecliente;
        this.documento_id = documento_id;
        this.nrodocumento = nrodocumento;
        this.fechacobranza = fechacobranza;
        this.importe = importe;
        this.saldo = saldo;
        this.cobrado = cobrado;
        this.nuevosaldo = nuevosaldo;
        this.checkbox = checkbox;
        this.recibo = recibo;
        this.tv_txtbancarizado= tv_txtbancarizado;
        this.tv_txtpagodirecto = tv_txtpagodirecto;
    }

    public String getTv_txtpagodirecto() {
        return tv_txtpagodirecto;
    }

    public void setTv_txtpagodirecto(String tv_txtpagodirecto) {
        this.tv_txtpagodirecto = tv_txtpagodirecto;
    }

    public String getTv_txtbancarizado() {
        return tv_txtbancarizado;
    }

    public void setTv_txtbancarizado(String tv_txtbancarizado) {
        this.tv_txtbancarizado = tv_txtbancarizado;
    }

    public ListaConsDepositoEntity() {

    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getDocumento_id() {
        return documento_id;
    }

    public void setDocumento_id(String documento_id) {
        this.documento_id = documento_id;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getFechacobranza() {
        return fechacobranza;
    }

    public void setFechacobranza(String fechacobranza) {
        this.fechacobranza = fechacobranza;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getCobrado() {
        return cobrado;
    }

    public void setCobrado(String cobrado) {
        this.cobrado = cobrado;
    }

    public String getNuevosaldo() {
        return nuevosaldo;
    }

    public void setNuevosaldo(String nuevosaldo) {
        this.nuevosaldo = nuevosaldo;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }
}

package com.vistony.salesforce.Entity.Adapters;

import java.io.Serializable;

public class ListaClienteDetalleEntity  implements Serializable {
    public String cliente_id;
    public String nombrecliente;
    public String domembarque;
    public String direccion;
    public String documento_id;
    public String nrodocumento;
    public String fechaemision;
    public String fechavencimiento;
    public String importe;
    public String saldo;
    public String cobrado;
    public String nuevo_saldo;
    public int imvclientedetalle;
    public String moneda;
    public String zona_id;
    public String docentry;
    public String chkruta;
    public String pymntgroup;
    public String additionaldiscount;

    public ListaClienteDetalleEntity() {

    }

    public ListaClienteDetalleEntity(
            String cliente_id, String nombrecliente, String domembarque, String direccion, String documento_id,
            String nrodocumento, String fechaemision, String fechavencimiento, String importe, String saldo,
            String cobrado, String nuevo_saldo, int imvclientedetalle, String moneda, String zona_id,
            String docentry,String chkruta,String pymntgroup,String additionaldiscount) {
        this.cliente_id = cliente_id;
        this.nombrecliente = nombrecliente;
        this.domembarque = domembarque;
        this.direccion = direccion;
        this.documento_id = documento_id;
        this.nrodocumento = nrodocumento;
        this.fechaemision = fechaemision;
        this.fechavencimiento = fechavencimiento;
        this.importe = importe;
        this.saldo = saldo;
        this.cobrado = cobrado;
        this.nuevo_saldo = nuevo_saldo;
        this.imvclientedetalle = imvclientedetalle;
        this.moneda = moneda;
        this.zona_id = zona_id;
        this.docentry = docentry;
        this.chkruta = chkruta;
        this.pymntgroup = pymntgroup;
        this.additionaldiscount = additionaldiscount;
    }

    public String getAdditionaldiscount() {
        return additionaldiscount;
    }

    public void setAdditionaldiscount(String additionaldiscount) {
        this.additionaldiscount = additionaldiscount;
    }

    public String getPymntgroup() {
        return pymntgroup;
    }

    public void setPymntgroup(String pymntgroup) {
        this.pymntgroup = pymntgroup;
    }

    public String getChkruta() {
        return chkruta;
    }

    public void setChkruta(String chkruta) {
        this.chkruta = chkruta;
    }

    public String getDocentry() {
        return docentry;
    }

    public void setDocentry(String docentry) {
        this.docentry = docentry;
    }

    public String getZona_id() {
        return zona_id;
    }

    public void setZona_id(String zona_id) {
        this.zona_id = zona_id;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
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

    public String getDomembarque() {
        return domembarque;
    }

    public void setDomembarque(String domembarque) {
        this.domembarque = domembarque;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(String fechaemision) {
        this.fechaemision = fechaemision;
    }

    public String getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(String fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
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

    public String getNuevo_saldo() {
        return nuevo_saldo;
    }

    public void setNuevo_saldo(String nuevo_saldo) {
        this.nuevo_saldo = nuevo_saldo;
    }

    public int getImvclientedetalle() {
        return imvclientedetalle;
    }

    public void setImvclientedetalle(int imvclientedetalle) {
        this.imvclientedetalle = imvclientedetalle;
    }

    @Override
    public String toString() {
        return "Lead{" +
                "cliente_id='" + cliente_id + '\'' +
                "nombrecliente='" + nombrecliente + '\'' +
                "domembarque='" + domembarque + '\'' +
                "direccion='" + direccion + '\'' +
                "nrodocumento='" + nrodocumento + '\'' +
                ", fechaemision='" + fechaemision + '\'' +
                ", fechavencimiento='" + fechavencimiento + '\'' +
                ", importe='" + importe + '\'' +
                ", saldo='" + saldo + '\'' +
                ", imvclientedetalle='" + imvclientedetalle + '\'' +
                '}';
    }

}

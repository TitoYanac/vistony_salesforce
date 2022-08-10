package com.vistony.salesforce.Entity.SQLite;

public class HojaDespachoDetalleSQLiteEntity {
    public String compania_id;
    public String fuerzatrabajo_id;
    public String usuario_id;
    public String control_id;
    public String item_id;
    public String cliente_id;
    public String domembarque_id;
    public String direccion;
    public String factura_id;
    public String entrega_id;
    public String entrega;
    public String factura;
    public String saldo;
    public String estado;
    public String fuerzatrabajo_factura_id;
    public String fuerzatrabajo_factura;
    public String terminopago_id;
    public String terminopago;
    public String peso;
    public String comentariodespacho;
    public String nombrecliente;
    public boolean chkupdatedispatch;
    public boolean chkvisitsectionstart;
    public boolean chkvisitsectionend;

    public HojaDespachoDetalleSQLiteEntity(
            String compania_id, String fuerzatrabajo_id, String usuario_id, String control_id, String item_id, String cliente_id,
            String domembarque_id, String direccion, String factura_id, String entrega_id, String entrega, String factura,
            String saldo, String estado, String fuerzatrabajo_factura_id, String fuerzatrabajo_factura, String terminopago_id,
            String terminopago, String peso, String comentariodespacho, String nombrecliente
            ,boolean chkupdatedispatch
            ,boolean chkvisitsectionstart
            ,boolean chkvisitsectionend
    ) {
        this.compania_id = compania_id;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        this.control_id = control_id;
        this.item_id = item_id;
        this.cliente_id = cliente_id;
        this.domembarque_id = domembarque_id;
        this.direccion = direccion;
        this.factura_id = factura_id;
        this.entrega_id = entrega_id;
        this.entrega = entrega;
        this.factura = factura;
        this.saldo = saldo;
        this.estado = estado;
        this.fuerzatrabajo_factura_id = fuerzatrabajo_factura_id;
        this.fuerzatrabajo_factura = fuerzatrabajo_factura;
        this.terminopago_id = terminopago_id;
        this.terminopago = terminopago;
        this.peso = peso;
        this.comentariodespacho = comentariodespacho;
        this.nombrecliente = nombrecliente;
        this.chkupdatedispatch = chkupdatedispatch;
        this.chkvisitsectionstart = chkvisitsectionstart;
        this.chkvisitsectionend = chkvisitsectionend;
    }

    public HojaDespachoDetalleSQLiteEntity() {

    }

    public boolean isChkvisitsectionstart() {
        return chkvisitsectionstart;
    }

    public void setChkvisitsectionstart(boolean chkvisitsectionstart) {
        this.chkvisitsectionstart = chkvisitsectionstart;
    }

    public boolean isChkvisitsectionend() {
        return chkvisitsectionend;
    }

    public void setChkvisitsectionend(boolean chkvisitsectionend) {
        this.chkvisitsectionend = chkvisitsectionend;
    }

    public boolean isChkupdatedispatch() {
        return chkupdatedispatch;
    }

    public void setChkupdatedispatch(boolean chkupdatedispatch) {
        this.chkupdatedispatch = chkupdatedispatch;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
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

    public String getControl_id() {
        return control_id;
    }

    public void setControl_id(String control_id) {
        this.control_id = control_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getDomembarque_id() {
        return domembarque_id;
    }

    public void setDomembarque_id(String domembarque_id) {
        this.domembarque_id = domembarque_id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFuerzatrabajo_factura_id() {
        return fuerzatrabajo_factura_id;
    }

    public void setFuerzatrabajo_factura_id(String fuerzatrabajo_factura_id) {
        this.fuerzatrabajo_factura_id = fuerzatrabajo_factura_id;
    }

    public String getFuerzatrabajo_factura() {
        return fuerzatrabajo_factura;
    }

    public void setFuerzatrabajo_factura(String fuerzatrabajo_factura) {
        this.fuerzatrabajo_factura = fuerzatrabajo_factura;
    }

    public String getTerminopago_id() {
        return terminopago_id;
    }

    public void setTerminopago_id(String terminopago_id) {
        this.terminopago_id = terminopago_id;
    }

    public String getTerminopago() {
        return terminopago;
    }

    public void setTerminopago(String terminopago) {
        this.terminopago = terminopago;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getComentariodespacho() {
        return comentariodespacho;
    }

    public void setComentariodespacho(String comentariodespacho) {
        this.comentariodespacho = comentariodespacho;
    }
}

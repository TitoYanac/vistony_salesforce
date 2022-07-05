package com.vistony.salesforce.Entity.Adapters;

import java.io.Serializable;

public class ListaHistoricoCobranzaEntity implements Serializable {
    public String bancarizacion;
    public String banco_id;
    public String cliente_id;
    public String cliente_nombre;
    public String comentario;
    public String compania_id;
    public String deposito_id;
    public String detalle_item;
    public String documento_id;
    public String estado;
    public String estadoqr;
    public String fechacobranza;
    public String fechadeposito;
    public String fuerzatrabajo_id;
    public String importedocumento;
    public String montocobrado;
    public String motivoanulacion;
    public String nro_documento;
    public String nuevosaldodocumento;
    public String recibo;
    public String saldodocumento;
    public String tipoingreso;
    public String usuario_id;
    public boolean chkconciliado;
    public String chkwsrecibido;
    public String depositodirecto;
    public String pagopos;
    public String codesap;
    public String mensajeWS;
    public String horacobranza;
    public String docentry;
    public String collectioncheck;
    public String e_signature;
    public String chkesignature;

    public ListaHistoricoCobranzaEntity(
            String bancarizacion, String banco_id, String cliente_id, String cliente_nombre,
            String comentario, String compania_id, String deposito_id, String detalle_item,
            String documento_id, String estado, String estadoqr, String fechacobranza,
            String fechadeposito, String fuerzatrabajo_id, String importedocumento, String montocobrado,
            String motivoanulacion, String nro_documento, String nuevosaldodocumento, String recibo,
            String saldodocumento, String tipoingreso, String usuario_id, boolean chkconciliado,
            String chkwsrecibido, String depositodirecto, String pagopos, String codesap,
            String mensajeWS, String horacobranza, String docentry,String collectioncheck,
            String e_signature,String chkesignature
    ) {
        this.bancarizacion = bancarizacion;
        this.banco_id = banco_id;
        this.cliente_id = cliente_id;
        this.cliente_nombre = cliente_nombre;
        this.comentario = comentario;
        this.compania_id = compania_id;
        this.deposito_id = deposito_id;
        this.detalle_item = detalle_item;
        this.documento_id = documento_id;
        this.estado = estado;
        this.estadoqr = estadoqr;
        this.fechacobranza = fechacobranza;
        this.fechadeposito = fechadeposito;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.importedocumento = importedocumento;
        this.montocobrado = montocobrado;
        this.motivoanulacion = motivoanulacion;
        this.nro_documento = nro_documento;
        this.nuevosaldodocumento = nuevosaldodocumento;
        this.recibo = recibo;
        this.saldodocumento = saldodocumento;
        this.tipoingreso = tipoingreso;
        this.usuario_id = usuario_id;
        this.chkconciliado = chkconciliado;
        this.chkwsrecibido = chkwsrecibido;
        this.depositodirecto = depositodirecto;
        this.pagopos = pagopos;
        this.codesap = codesap;
        this.mensajeWS = mensajeWS;
        this.horacobranza = horacobranza;
        this.docentry = docentry;
        this.collectioncheck = collectioncheck;
        this.e_signature = e_signature;
        this.chkesignature = chkesignature;
    }

    public ListaHistoricoCobranzaEntity() {
    }

    public String getE_signature() {
        return e_signature;
    }

    public void setE_signature(String e_signature) {
        this.e_signature = e_signature;
    }

    public String getChkesignature() {
        return chkesignature;
    }

    public void setChkesignature(String chkesignature) {
        this.chkesignature = chkesignature;
    }

    public String getCollectioncheck() {
        return collectioncheck;
    }

    public void setCollectioncheck(String collectioncheck) {
        this.collectioncheck = collectioncheck;
    }

    public String getDocentry() {
        return docentry;
    }

    public void setDocentry(String docentry) {
        this.docentry = docentry;
    }

    public String getMensajeWS() {
        return mensajeWS;
    }

    public void setMensajeWS(String mensajeWS) {
        this.mensajeWS = mensajeWS;
    }

    public String getHoracobranza() {
        return horacobranza;
    }

    public void setHoracobranza(String horacobranza) {
        this.horacobranza = horacobranza;
    }

    public String getCodesap() {
        return codesap;
    }

    public void setCodesap(String codesap) {
        this.codesap = codesap;
    }

    public String getPagopos() {
        return pagopos;
    }

    public void setPagopos(String pagopos) {
        this.pagopos = pagopos;
    }

    public String getDepositodirecto() {
        return depositodirecto;
    }

    public void setDepositodirecto(String depositodirecto) {
        this.depositodirecto = depositodirecto;
    }

    public String getChkwsrecibido() {
        return chkwsrecibido;
    }

    public void setChkwsrecibido(String chkwsrecibido) {
        this.chkwsrecibido = chkwsrecibido;
    }

    public String getBancarizacion() {
        return bancarizacion;
    }

    public void setBancarizacion(String bancarizacion) {
        this.bancarizacion = bancarizacion;
    }

    public String getEstadoqr() {
        return estadoqr;
    }

    public void setEstadoqr(String estadoqr) {
        this.estadoqr = estadoqr;
    }

    public String getMotivoanulacion() {
        return motivoanulacion;
    }

    public void setMotivoanulacion(String motivoanulacion) {
        this.motivoanulacion = motivoanulacion;
    }

    public String getTipoingreso() {
        return tipoingreso;
    }

    public void setTipoingreso(String tipoingreso) {
        this.tipoingreso = tipoingreso;
    }

    public String getCliente_nombre() {
        return cliente_nombre;
    }

    public void setCliente_nombre(String cliente_nombre) {
        this.cliente_nombre = cliente_nombre;
    }

    public String getNro_documento() {
        return nro_documento;
    }

    public void setNro_documento(String nro_documento) {
        this.nro_documento = nro_documento;
    }

    public String getBanco_id() {
        return banco_id;
    }

    public void setBanco_id(String banco_id) {
        this.banco_id = banco_id;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getDeposito_id() {
        return deposito_id;
    }

    public void setDeposito_id(String deposito_id) {
        this.deposito_id = deposito_id;
    }

    public String getDetalle_item() {
        return detalle_item;
    }

    public void setDetalle_item(String detalle_item) {
        this.detalle_item = detalle_item;
    }

    public String getDocumento_id() {
        return documento_id;
    }

    public void setDocumento_id(String documento_id) {
        this.documento_id = documento_id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechacobranza() {
        return fechacobranza;
    }

    public void setFechacobranza(String fechacobranza) {
        this.fechacobranza = fechacobranza;
    }

    public String getFechadeposito() {
        return fechadeposito;
    }

    public void setFechadeposito(String fechadeposito) {
        fechadeposito = fechadeposito;
    }

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
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

    public String getNuevosaldodocumento() {
        return nuevosaldodocumento;
    }

    public void setNuevosaldodocumento(String nuevosaldodocumento) {
        this.nuevosaldodocumento = nuevosaldodocumento;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public String getSaldodocumento() {
        return saldodocumento;
    }

    public void setSaldodocumento(String saldodocumento) {
        this.saldodocumento = saldodocumento;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public boolean isChkconciliado() {
        return chkconciliado;
    }

    public void setChkconciliado(boolean chkconciliado) {
        this.chkconciliado = chkconciliado;
    }
}

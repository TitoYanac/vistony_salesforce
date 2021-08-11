package com.vistony.salesforce.Entity.SQLite;

public class CobranzaDetalleSQLiteEntity {

    public String id;
    public String cobranza_id;
    public String cliente_id;
    public String documento_id;
    public String compania_id;
    public String importedocumento;
    public String saldodocumento;
    public String nuevosaldodocumento;
    public String saldocobrado;
    public String fechacobranza;
    public String recibo;
    public String nrofactura;
    public String chkqrvalidado;
    public String chkbancarizado;
    public String motivoanulacion;
    public String usuario_id;
    public String chkwsrecibido;
    public String banco_id;
    public String chkwsdepositorecibido;
    public String comentario;
    public String chkwsupdate;
    public String chkdepositado;
    public String chkwsqrvalidado;
    public String chkanulado;
    public String pagodirecto;
    public String pagopos;

    public CobranzaDetalleSQLiteEntity(String id, String cobranza_id, String cliente_id, String documento_id, String compania_id, String importedocumento, String saldodocumento, String nuevosaldodocumento, String saldocobrado, String fechacobranza, String recibo, String nrofactura, String chkqrvalidado, String chkbancarizado, String motivoanulacion, String usuario_id, String chkwsrecibido, String banco_id, String chkwsdepositorecibido, String comentario, String chkwsupdate, String chkdepositado, String chkwsqrvalidado, String chkanulado, String pagodirecto, String pagopos) {
        this.id = id;
        this.cobranza_id = cobranza_id;
        this.cliente_id = cliente_id;
        this.documento_id = documento_id;
        this.compania_id = compania_id;
        this.importedocumento = importedocumento;
        this.saldodocumento = saldodocumento;
        this.nuevosaldodocumento = nuevosaldodocumento;
        this.saldocobrado = saldocobrado;
        this.fechacobranza = fechacobranza;
        this.recibo = recibo;
        this.nrofactura = nrofactura;
        this.chkqrvalidado = chkqrvalidado;
        this.chkbancarizado = chkbancarizado;
        this.motivoanulacion = motivoanulacion;
        this.usuario_id = usuario_id;
        this.chkwsrecibido = chkwsrecibido;
        this.banco_id = banco_id;
        this.chkwsdepositorecibido = chkwsdepositorecibido;
        this.comentario = comentario;
        this.chkwsupdate = chkwsupdate;
        this.chkdepositado = chkdepositado;
        this.chkwsqrvalidado = chkwsqrvalidado;
        this.chkanulado = chkanulado;
        this.pagodirecto = pagodirecto;
        this.pagopos = pagopos;
    }

    public CobranzaDetalleSQLiteEntity() {

    }

    public String getPagopos() {
        return pagopos;
    }

    public void setPagopos(String pagopos) {
        this.pagopos = pagopos;
    }

    public String getPagodirecto() {
        return pagodirecto;
    }

    public void setPagodirecto(String pagodirecto) {
        this.pagodirecto = pagodirecto;
    }

    public String getChkwsqrvalidado() {
        return chkwsqrvalidado;
    }

    public void setChkwsqrvalidado(String chkwsqrvalidado) {
        this.chkwsqrvalidado = chkwsqrvalidado;
    }

    public String getChkanulado() {
        return chkanulado;
    }

    public void setChkanulado(String chkanulado) {
        this.chkanulado = chkanulado;
    }

    public String getChkdepositado() {
        return chkdepositado;
    }

    public void setChkdepositado(String chkdepositado) {
        this.chkdepositado = chkdepositado;
    }

    public String getChkwsupdate() {
        return chkwsupdate;
    }

    public void setChkwsupdate(String chkwsupdate) {
        this.chkwsupdate = chkwsupdate;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getChkwsdepositorecibido() {
        return chkwsdepositorecibido;
    }

    public void setChkwsdepositorecibido(String chkwsdepositorecibido) {
        this.chkwsdepositorecibido = chkwsdepositorecibido;
    }

    public String getBanco_id() {
        return banco_id;
    }

    public void setBanco_id(String banco_id) {
        this.banco_id = banco_id;
    }

    public String getChkwsrecibido() {
        return chkwsrecibido;
    }

    public void setChkwsrecibido(String chkwsrecibido) {
        this.chkwsrecibido = chkwsrecibido;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getChkbancarizado() {
        return chkbancarizado;
    }

    public void setChkbancarizado(String chkbancarizado) {
        this.chkbancarizado = chkbancarizado;
    }

    public String getMotivoanulacion() {
        return motivoanulacion;
    }

    public void setMotivoanulacion(String motivoanulacion) {
        this.motivoanulacion = motivoanulacion;
    }

    public String getChkqrvalidado() {
        return chkqrvalidado;
    }

    public void setChkqrvalidado(String chkqrvalidado) {
        this.chkqrvalidado = chkqrvalidado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(String nrofactura) {
        this.nrofactura = nrofactura;
    }

    public String getCobranza_id() {
        return cobranza_id;
    }

    public void setCobranza_id(String cobranza_id) {
        this.cobranza_id = cobranza_id;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getDocumento_id() {
        return documento_id;
    }

    public void setDocumento_id(String documento_id) {
        this.documento_id = documento_id;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getImportedocumento() {
        return importedocumento;
    }

    public void setImportedocumento(String importedocumento) {
        this.importedocumento = importedocumento;
    }

    public String getSaldodocumento() {
        return saldodocumento;
    }

    public void setSaldodocumento(String saldodocumento) {
        this.saldodocumento = saldodocumento;
    }

    public String getNuevosaldodocumento() {
        return nuevosaldodocumento;
    }

    public void setNuevosaldodocumento(String nuevosaldodocumento) {
        this.nuevosaldodocumento = nuevosaldodocumento;
    }

    public String getSaldocobrado() {
        return saldocobrado;
    }

    public void setSaldocobrado(String saldocobrado) {
        this.saldocobrado = saldocobrado;
    }

    public String getFechacobranza() {
        return fechacobranza;
    }

    public void setFechacobranza(String fechacobranza) {
        this.fechacobranza = fechacobranza;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }


}

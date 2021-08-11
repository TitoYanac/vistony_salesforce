package com.vistony.salesforce.Entity.SQLite;

public class VisitaSQLiteEntity {
    private String compania_id,cliente_id,direccion_id,fecha_registro,hora_registro,zona_id,fuerzatrabajo_id,usuario_id,tipo,motivo,observacion,chkenviado,chkrecibido,latitud,longitud;

    public VisitaSQLiteEntity(){}

    public VisitaSQLiteEntity(String compania_id, String cliente_id, String direccion_id, String fecha_registro, String hora_registro, String zona_id, String fuerzatrabajo_id, String usuario_id, String tipo, String motivo, String observacion, String chkenviado, String chkrecibido, String latitud, String longitud) {
        this.compania_id = compania_id;
        this.cliente_id = cliente_id;
        this.direccion_id = direccion_id;
        this.fecha_registro = fecha_registro;
        this.hora_registro = hora_registro;
        this.zona_id = zona_id;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        this.tipo = tipo;
        this.motivo = motivo;
        this.observacion = observacion;
        this.chkenviado = chkenviado;
        this.chkrecibido = chkrecibido;
        this.latitud = latitud;
        this.longitud = longitud;
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

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getDireccion_id() {
        return direccion_id;
    }

    public void setDireccion_id(String direccion_id) {
        this.direccion_id = direccion_id;
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

    public String getZona_id() {
        return zona_id;
    }

    public void setZona_id(String zona_id) {
        this.zona_id = zona_id;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getChkenviado() {
        return chkenviado;
    }

    public void setChkenviado(String chkenviado) {
        this.chkenviado = chkenviado;
    }

    public String getChkrecibido() {
        return chkrecibido;
    }

    public void setChkrecibido(String chkrecibido) {
        this.chkrecibido = chkrecibido;
    }
}

package com.vistony.salesforce.Entity.Adapters;

import java.io.Serializable;

public class ListaHistoricoDepositoEntity  implements Serializable {
    public String bancarizacion;
    public String banco_id;
    public String comentario;
    public String compania_id;
    public String deposito_id;
    public String estado;
    public String fechadeposito;
    public String fechadiferida;
    public String fuerzatrabajo_id;
    public String montodeposito;
    public String motivoanulacion;
    public String tipoingreso;
    public String usuario_id;
    public boolean checkbox;
    public int imvdetalle;
    public String depositodirecto;

    public ListaHistoricoDepositoEntity(
            String bancarizacion,
            String banco_id,
            String comentario,
            String compania_id,
            String deposito_id,
            String estado,
            String fechadeposito,
            String fechadiferida,
            String fuerzatrabajo_id,
            String montodeposito,
            String motivoanulacion,
            String tipoingreso,
            String usuario_id,
            boolean checkbox,
            int imvdetalle,
            String depositodirecto

    ) {
        this.bancarizacion=bancarizacion;
        this.banco_id = banco_id;
        this.comentario = comentario;
        this.compania_id = compania_id;
        this.deposito_id = deposito_id;
        this.estado = estado;
        this.fechadeposito = fechadeposito;
        this.fechadiferida = fechadiferida;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.montodeposito = montodeposito;
        this.motivoanulacion = motivoanulacion;
        this.tipoingreso = tipoingreso;
        this.usuario_id = usuario_id;
        this.checkbox = checkbox;
        this.imvdetalle = imvdetalle;
        this.depositodirecto=depositodirecto;
    }

    public ListaHistoricoDepositoEntity() {
    }

    public String getDepositodirecto() {
        return depositodirecto;
    }

    public void setDepositodirecto(String depositodirecto) {
        this.depositodirecto = depositodirecto;
    }

    public String getBancarizacion() {
        return bancarizacion;
    }

    public void setBancarizacion(String bancarizacion) {
        this.bancarizacion = bancarizacion;
    }

    public String getFechadiferida() {
        return fechadiferida;
    }

    public void setFechadiferida(String fechadiferida) {
        this.fechadiferida = fechadiferida;
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

    public String getBanco_id() {
        return banco_id;
    }

    public void setBanco_id(String banco_id) {
        this.banco_id = banco_id;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechadeposito() {
        return fechadeposito;
    }

    public void setFechadeposito(String fechadeposito) {
        this.fechadeposito = fechadeposito;
    }

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    public String getMontodeposito() {
        return montodeposito;
    }

    public void setMontodeposito(String montodeposito) {
        this.montodeposito = montodeposito;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public int getImvdetalle() {
        return imvdetalle;
    }

    public void setImvdetalle(int imvdetalle) {
        this.imvdetalle = imvdetalle;
    }
}


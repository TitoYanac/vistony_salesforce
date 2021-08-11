package com.vistony.salesforce.Entity;

/**
 * Created by Shopper on 12/04/2018.
 */

public class LoginEntity {

    public String compania_id;
    public String fuerzatrabajo_id;
    public String nombrecompania;
    public String nombrefuerzadetrabajo;
    public String nombreusuario;
    public String usuario_id;
    public String estado;
    public String recibo;
    public String bloqueopago;
    public String listaprecio_id_1;
    public String listaprecio_id_2;
    public String planta;
    public String almacen_id;
    public String perfil;
    public String cogsacct;
    public String u_vist_ctaingdcto;
    public String documentsowner;
    public String U_VIST_SUCUSU;
    public String CentroCosto;
    public String UnidadNegocio;
    public String LineaProduccion;
    public String Impuesto_ID;
    public String Impuesto;
    public String TipoCambio;
    public String U_VIS_CashDscnt;

    public LoginEntity ()
    {

    }

    public LoginEntity(String compania_id, String fuerzatrabajo_id, String nombrecompania, String nombrefuerzadetrabajo, String nombreusuario, String usuario_id, String estado, String recibo, String bloqueopago, String listaprecio_id_1, String listaprecio_id_2, String planta, String almacen_id, String perfil, String cogsacct, String u_vist_ctaingdcto, String documentsowner, String u_VIST_SUCUSU, String centroCosto, String unidadNegocio, String lineaProduccion, String impuesto_ID, String impuesto, String tipoCambio, String u_VIS_CashDscnt) {
        this.compania_id = compania_id;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.nombrecompania = nombrecompania;
        this.nombrefuerzadetrabajo = nombrefuerzadetrabajo;
        this.nombreusuario = nombreusuario;
        this.usuario_id = usuario_id;
        this.estado = estado;
        this.recibo = recibo;
        this.bloqueopago = bloqueopago;
        this.listaprecio_id_1 = listaprecio_id_1;
        this.listaprecio_id_2 = listaprecio_id_2;
        this.planta = planta;
        this.almacen_id = almacen_id;
        this.perfil = perfil;
        this.cogsacct = cogsacct;
        this.u_vist_ctaingdcto = u_vist_ctaingdcto;
        this.documentsowner = documentsowner;
        U_VIST_SUCUSU = u_VIST_SUCUSU;
        CentroCosto = centroCosto;
        UnidadNegocio = unidadNegocio;
        LineaProduccion = lineaProduccion;
        Impuesto_ID = impuesto_ID;
        Impuesto = impuesto;
        TipoCambio = tipoCambio;
        U_VIS_CashDscnt = u_VIS_CashDscnt;
    }

    public String getU_VIS_CashDscnt() {
        return U_VIS_CashDscnt;
    }

    public void setU_VIS_CashDscnt(String u_VIS_CashDscnt) {
        U_VIS_CashDscnt = u_VIS_CashDscnt;
    }

    public String getU_VIST_SUCUSU() {
        return U_VIST_SUCUSU;
    }

    public void setU_VIST_SUCUSU(String u_VIST_SUCUSU) {
        U_VIST_SUCUSU = u_VIST_SUCUSU;
    }

    public String getCentroCosto() {
        return CentroCosto;
    }

    public void setCentroCosto(String centroCosto) {
        CentroCosto = centroCosto;
    }

    public String getUnidadNegocio() {
        return UnidadNegocio;
    }

    public void setUnidadNegocio(String unidadNegocio) {
        UnidadNegocio = unidadNegocio;
    }

    public String getLineaProduccion() {
        return LineaProduccion;
    }

    public void setLineaProduccion(String lineaProduccion) {
        LineaProduccion = lineaProduccion;
    }

    public String getImpuesto_ID() {
        return Impuesto_ID;
    }

    public void setImpuesto_ID(String impuesto_ID) {
        Impuesto_ID = impuesto_ID;
    }

    public String getImpuesto() {
        return Impuesto;
    }

    public void setImpuesto(String impuesto) {
        Impuesto = impuesto;
    }

    public String getTipoCambio() {
        return TipoCambio;
    }

    public void setTipoCambio(String tipoCambio) {
        TipoCambio = tipoCambio;
    }

    public String getCogsacct() {
        return cogsacct;
    }

    public void setCogsacct(String cogsacct) {
        this.cogsacct = cogsacct;
    }

    public String getU_vist_ctaingdcto() {
        return u_vist_ctaingdcto;
    }

    public void setU_vist_ctaingdcto(String u_vist_ctaingdcto) {
        this.u_vist_ctaingdcto = u_vist_ctaingdcto;
    }

    public String getDocumentsowner() {
        return documentsowner;
    }

    public void setDocumentsowner(String documentsowner) {
        this.documentsowner = documentsowner;
    }

    public String getListaprecio_id_1() {
        return listaprecio_id_1;
    }

    public void setListaprecio_id_1(String listaprecio_id_1) {
        this.listaprecio_id_1 = listaprecio_id_1;
    }

    public String getListaprecio_id_2() {
        return listaprecio_id_2;
    }

    public void setListaprecio_id_2(String listaprecio_id_2) {
        this.listaprecio_id_2 = listaprecio_id_2;
    }

    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    public String getAlmacen_id() {
        return almacen_id;
    }

    public void setAlmacen_id(String almacen_id) {
        this.almacen_id = almacen_id;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }


    public String getBloqueopago() {
        return bloqueopago;
    }

    public void setBloqueopago(String bloqueopago) {
        this.bloqueopago = bloqueopago;
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

    public String getNombrecompania() {
        return nombrecompania;
    }

    public void setNombrecompania(String nombrecompania) {
        this.nombrecompania = nombrecompania;
    }

    public String getNombrefuerzadetrabajo() {
        return nombrefuerzadetrabajo;
    }

    public void setNombrefuerzadetrabajo(String nombrefuerzadetrabajo) {
        this.nombrefuerzadetrabajo = nombrefuerzadetrabajo;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }
}

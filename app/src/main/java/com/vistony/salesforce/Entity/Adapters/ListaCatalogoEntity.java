package com.vistony.salesforce.Entity.Adapters;

public class ListaCatalogoEntity {
    public String catalogo_id;
    public String catalogo;
    public String ruta;

    public ListaCatalogoEntity(String catalogo_id, String catalogo, String ruta) {
        this.catalogo_id = catalogo_id;
        this.catalogo = catalogo;
        this.ruta = ruta;
    }
    public ListaCatalogoEntity() {
    }

    public String getCatalogo_id() {
        return catalogo_id;
    }

    public void setCatalogo_id(String catalogo_id) {
        this.catalogo_id = catalogo_id;
    }

    public String getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(String catalogo) {
        this.catalogo = catalogo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}

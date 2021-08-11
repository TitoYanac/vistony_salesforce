package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CatalogoEntity {
    @SerializedName("descripcion")
    private String descripcion;

    @NonNull
    @SerializedName("categoria")
    private String categoria;

    @NonNull
    @SerializedName("ruta")
    private String ruta;

    @NonNull
    @SerializedName("tipo")
    private String tipo;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @NonNull
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NonNull String categoria) {
        this.categoria = categoria;
    }

    @NonNull
    public String getRuta() {
        return ruta;
    }

    public void setRuta(@NonNull String ruta) {
        this.ruta = ruta;
    }

    @NonNull
    public String getTipo() {
        return tipo;
    }

    public void setTipo(@NonNull String tipo) {
        this.tipo = tipo;
    }
}

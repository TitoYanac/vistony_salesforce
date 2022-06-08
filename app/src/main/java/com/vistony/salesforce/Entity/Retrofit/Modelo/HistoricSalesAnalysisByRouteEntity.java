package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoricSalesAnalysisByRouteEntity {

    @SerializedName("CardCode")
    public String cliente_id;

    @SerializedName("CardName")
    public String cardname;

    @SerializedName("ShipToCode")
    public String shiptocode;

    @SerializedName("Street")
    public String street;

    @SerializedName("TerritoryID")
    public String territoryid;

    @SerializedName("Territory")
    public String territory;

    @SerializedName("SlpCode")
    public String slpcode;

    @SerializedName("Day")
    public String dia;

    @SerializedName("CommercialClass")
    public String clase_comercial;

    @SerializedName("GallonCurrentYearCurrentPeriod")
    public String galanioactualperiodoactual;

    @SerializedName("GallonCurrentYearPreviousPeriod")
    public String galanioactual1periodoanterior;

    @SerializedName("GallonCurrentYearSecondPriorPeriod")
    public String galanioactual2periodoAnterior;

    @SerializedName("GallonPreviousYearCurrentPeriod")
    public String galanioanteriorperiodoactual;

    @SerializedName("GallonPreviousYearPreviousPeriod")
    public String galanioanterior1periodoanterior;

    @SerializedName("GallonPreviousYearSecondPreviousPeriod")
    public String galanioanterior2periodoanterior;

    @SerializedName("AverageQuarterCurrentYear")
    public String promediotrimestreanioactual;

    @SerializedName("AverageQuarterPreviousYear")
    public String promediotrimestreanioanterior;

    @SerializedName("Indicator1")
    public String Prom;

    @SerializedName("Indicator2")
    public String prom2122;

    @SerializedName("Quota")
    public String cuota;

    @SerializedName("Indicator3")
    public String porcentajeavancecuota;

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getShiptocode() {
        return shiptocode;
    }

    public void setShiptocode(String shiptocode) {
        this.shiptocode = shiptocode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTerritoryid() {
        return territoryid;
    }

    public void setTerritoryid(String territoryid) {
        this.territoryid = territoryid;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getSlpcode() {
        return slpcode;
    }

    public void setSlpcode(String slpcode) {
        this.slpcode = slpcode;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getClase_comercial() {
        return clase_comercial;
    }

    public void setClase_comercial(String clase_comercial) {
        this.clase_comercial = clase_comercial;
    }

    public String getGalanioactualperiodoactual() {
        return galanioactualperiodoactual;
    }

    public void setGalanioactualperiodoactual(String galanioactualperiodoactual) {
        this.galanioactualperiodoactual = galanioactualperiodoactual;
    }

    public String getGalanioactual1periodoanterior() {
        return galanioactual1periodoanterior;
    }

    public void setGalanioactual1periodoanterior(String galanioactual1periodoanterior) {
        this.galanioactual1periodoanterior = galanioactual1periodoanterior;
    }

    public String getGalanioactual2periodoAnterior() {
        return galanioactual2periodoAnterior;
    }

    public void setGalanioactual2periodoAnterior(String galanioactual2periodoAnterior) {
        this.galanioactual2periodoAnterior = galanioactual2periodoAnterior;
    }

    public String getGalanioanteriorperiodoactual() {
        return galanioanteriorperiodoactual;
    }

    public void setGalanioanteriorperiodoactual(String galanioanteriorperiodoactual) {
        this.galanioanteriorperiodoactual = galanioanteriorperiodoactual;
    }

    public String getGalanioanterior1periodoanterior() {
        return galanioanterior1periodoanterior;
    }

    public void setGalanioanterior1periodoanterior(String galanioanterior1periodoanterior) {
        this.galanioanterior1periodoanterior = galanioanterior1periodoanterior;
    }

    public String getGalanioanterior2periodoanterior() {
        return galanioanterior2periodoanterior;
    }

    public void setGalanioanterior2periodoanterior(String galanioanterior2periodoanterior) {
        this.galanioanterior2periodoanterior = galanioanterior2periodoanterior;
    }

    public String getPromediotrimestreanioactual() {
        return promediotrimestreanioactual;
    }

    public void setPromediotrimestreanioactual(String promediotrimestreanioactual) {
        this.promediotrimestreanioactual = promediotrimestreanioactual;
    }

    public String getPromediotrimestreanioanterior() {
        return promediotrimestreanioanterior;
    }

    public void setPromediotrimestreanioanterior(String promediotrimestreanioanterior) {
        this.promediotrimestreanioanterior = promediotrimestreanioanterior;
    }

    public String getProm() {
        return Prom;
    }

    public void setProm(String prom) {
        Prom = prom;
    }

    public String getProm2122() {
        return prom2122;
    }

    public void setProm2122(String prom2122) {
        this.prom2122 = prom2122;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public String getPorcentajeavancecuota() {
        return porcentajeavancecuota;
    }

    public void setPorcentajeavancecuota(String porcentajeavancecuota) {
        this.porcentajeavancecuota = porcentajeavancecuota;
    }
}

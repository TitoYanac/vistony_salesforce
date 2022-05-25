package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class SummaryofeffectivenessEntity {

    @NonNull
    @SerializedName("Route")
    public String route;

    @NonNull
    @SerializedName("Customers")
    public String clients;

    @NonNull
    @SerializedName("Visit")
    public String visits;

    @NonNull
    @SerializedName("SalesOrder")
    public String salesorders;

    @NonNull
    @SerializedName("Collection")
    public String collections;

    @NonNull
    @SerializedName("Debtor")
    public String balanceclients;

    @NonNull
    @SerializedName("AmountSO")
    public String amountsalesorders;

    @NonNull
    @SerializedName("AmountCll")
    public String amountcollections;

    @NonNull
    @SerializedName("OrdersEff")
    public String orderseffectiveness;

    @NonNull
    @SerializedName("CollctnEff")
    public String collectionseffectiveness;

    @NonNull
    @SerializedName("VisitEff")
    public String visitseffectiveness;

    @NonNull
    @SerializedName("CusCoverage")
    public String coverageclients;

    @NonNull
    @SerializedName("Coverage")
    public String coverage;

    @NonNull
    @SerializedName("CoverageEff")
    public String coverageeffectiveness;

    @NonNull
    public String getVisitseffectiveness() {
        return visitseffectiveness;
    }



    public void setVisitseffectiveness(@NonNull String visitseffectiveness) {
        this.visitseffectiveness = visitseffectiveness;
    }

    @NonNull
    public String getRoute() {
        return route;
    }

    public void setRoute(@NonNull String route) {
        this.route = route;
    }

    @NonNull
    public String getClients() {
        return clients;
    }

    public void setClients(@NonNull String clients) {
        this.clients = clients;
    }

    @NonNull
    public String getVisits() {
        return visits;
    }

    public void setVisits(@NonNull String visits) {
        this.visits = visits;
    }

    @NonNull
    public String getSalesorders() {
        return salesorders;
    }

    public void setSalesorders(@NonNull String salesorders) {
        this.salesorders = salesorders;
    }

    @NonNull
    public String getCollections() {
        return collections;
    }

    public void setCollections(@NonNull String collections) {
        this.collections = collections;
    }

    @NonNull
    public String getBalanceclients() {
        return balanceclients;
    }

    public void setBalanceclients(@NonNull String balanceclients) {
        this.balanceclients = balanceclients;
    }

    @NonNull
    public String getAmountsalesorders() {
        return amountsalesorders;
    }

    public void setAmountsalesorders(@NonNull String amountsalesorders) {
        this.amountsalesorders = amountsalesorders;
    }

    @NonNull
    public String getAmountcollections() {
        return amountcollections;
    }

    public void setAmountcollections(@NonNull String amountcollections) {
        this.amountcollections = amountcollections;
    }

    @NonNull
    public String getOrderseffectiveness() {
        return orderseffectiveness;
    }

    public void setOrderseffectiveness(@NonNull String orderseffectiveness) {
        this.orderseffectiveness = orderseffectiveness;
    }

    @NonNull
    public String getCollectionseffectiveness() {
        return collectionseffectiveness;
    }

    public void setCollectionseffectiveness(@NonNull String collectionseffectiveness) {
        this.collectionseffectiveness = collectionseffectiveness;
    }

    @NonNull
    public String getCoverageclients() {
        return coverageclients;
    }

    public void setCoverageclients(@NonNull String coverageclients) {
        this.coverageclients = coverageclients;
    }

    @NonNull
    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(@NonNull String coverage) {
        this.coverage = coverage;
    }

    @NonNull
    public String getCoverageeffectiveness() {
        return coverageeffectiveness;
    }

    public void setCoverageeffectiveness(@NonNull String coverageeffectiveness) {
        this.coverageeffectiveness = coverageeffectiveness;
    }
}

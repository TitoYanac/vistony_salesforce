package com.vistony.salesforce.Entity.Adapters;

public class ListaPendingCollectionEntity {
    public String date;
    public String count;

    public ListaPendingCollectionEntity(String date, String count) {
        this.date = date;
        this.count = count;
    }

    public ListaPendingCollectionEntity() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}

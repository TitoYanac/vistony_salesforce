package com.vistony.salesforce.Entity.Adapters;

import android.widget.CheckBox;

public class ListKardexOfPaymentEntity {
    public String legalnumber;
    public String invoicedate;
    public String duedate;
    public String DocAmount;
    public String balance;
    public boolean invoice;

    public ListKardexOfPaymentEntity(String legalnumber, String invoicedate, String duedate, String docAmount, String balance, boolean invoice) {
        this.legalnumber = legalnumber;
        this.invoicedate = invoicedate;
        this.duedate = duedate;
        DocAmount = docAmount;
        this.balance = balance;
        this.invoice = invoice;
    }
    public ListKardexOfPaymentEntity() {
    }

    public String getLegalnumber() {
        return legalnumber;
    }

    public void setLegalnumber(String legalnumber) {
        this.legalnumber = legalnumber;
    }

    public String getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getDocAmount() {
        return DocAmount;
    }

    public void setDocAmount(String docAmount) {
        DocAmount = docAmount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public boolean isInvoice() {
        return invoice;
    }

    public void setInvoice(boolean invoice) {
        this.invoice = invoice;
    }
}

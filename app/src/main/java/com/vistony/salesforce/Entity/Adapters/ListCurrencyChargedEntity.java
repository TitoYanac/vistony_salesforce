package com.vistony.salesforce.Entity.Adapters;

public class ListCurrencyChargedEntity {
    private String id;
    private String deposit_id;
    private String currency_id;
    private String typecurrency_id;
    private String unitcurrency_id;
    private String quantity;
    private String amount;
    private boolean state_sp_typecurrency;
    private boolean state_sp_unitcurrency;
    public ListCurrencyChargedEntity(
            String id,
            String deposit_id,
            String currency_id,
            String typecurrency_id,
            String unitcurrency_id,
            String quantity,
            String amount,
            boolean state_sp_typecurrency,
            boolean state_sp_unitcurrency
    ) {
        this.id = id;
        this.deposit_id = deposit_id;
        this.currency_id = currency_id;
        this.typecurrency_id = typecurrency_id;
        this.unitcurrency_id = unitcurrency_id;
        this.quantity = quantity;
        this.amount = amount;
        this.state_sp_typecurrency = state_sp_typecurrency;
        this.state_sp_unitcurrency = state_sp_unitcurrency;
    }

    public boolean isState_sp_typecurrency() {
        return state_sp_typecurrency;
    }

    public void setState_sp_typecurrency(boolean state_sp_typecurrency) {
        this.state_sp_typecurrency = state_sp_typecurrency;
    }

    public boolean isState_sp_unitcurrency() {
        return state_sp_unitcurrency;
    }

    public void setState_sp_unitcurrency(boolean state_sp_unitcurrency) {
        this.state_sp_unitcurrency = state_sp_unitcurrency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeposit_id() {
        return deposit_id;
    }

    public void setDeposit_id(String deposit_id) {
        this.deposit_id = deposit_id;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public String getTypecurrency_id() {
        return typecurrency_id;
    }

    public void setTypecurrency_id(String typecurrency_id) {
        this.typecurrency_id = typecurrency_id;
    }

    public String getUnitcurrency_id() {
        return unitcurrency_id;
    }

    public void setUnitcurrency_id(String unitcurrency_id) {
        this.unitcurrency_id = unitcurrency_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

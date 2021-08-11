package com.vistony.salesforce.Entity.SQLite;

public class BancoSQLiteEntity {
    public String banco_id;
    public String compania_id;
    public String nombrebanco;


    public BancoSQLiteEntity(String banco_id, String compania_id, String nombrebanco) {
        this.banco_id = banco_id;
        this.compania_id = compania_id;
        this.nombrebanco = nombrebanco;
    }

    public BancoSQLiteEntity() {

    }


    public String getBanco_id() {
        return banco_id;
    }

    public void setBanco_id(String banco_id) {
        this.banco_id = banco_id;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getNombrebanco() {
        return nombrebanco;
    }

    public void setNombrebanco(String nombrebanco) {
        this.nombrebanco = nombrebanco;
    }
}

package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.LoginEntity;

import java.util.List;

public class LoginEntityResponse {
    @SerializedName("Users")
    private List<LoginEntity> users;
    public LoginEntityResponse(List<LoginEntity> users) {
        this.users = users;
    }
    public List<LoginEntity> getUsers() {
        return users;
    }


}

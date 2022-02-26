package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.UserEntity;

import java.util.List;

public class LoginEntityResponse {
    @SerializedName("Users")
    private List<UserEntity> users;

    public List<UserEntity> getUsers() {
        return users;
    }
    public LoginEntityResponse(List<UserEntity> users) {
        this.users = users;
    }



}

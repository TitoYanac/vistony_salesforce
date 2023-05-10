package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class CustomerComplaintResponseEntity {
    @SerializedName("Code")
    public String response_id;
    @SerializedName("Respuesta")
    public String response;

    public String responseAttach;

    public String reponseRouteFile;

    public String reponseAttachType;

    public String responseChoisse;

    public String getResponseChoisse() {
        return responseChoisse;
    }

    public void setResponseChoisse(String responseChoisse) {
        this.responseChoisse = responseChoisse;
    }

    public String getReponseAttachType() {
        return reponseAttachType;
    }

    public void setReponseAttachType(String reponseAttachType) {
        this.reponseAttachType = reponseAttachType;
    }

    public String getResponseAttach() {
        return responseAttach;
    }

    public void setResponseAttach(String responseAttach) {
        this.responseAttach = responseAttach;
    }

    public String getReponseRouteFile() {
        return reponseRouteFile;
    }

    public void setReponseRouteFile(String reponseRouteFile) {
        this.reponseRouteFile = reponseRouteFile;
    }

    public String getResponse_id() {
        return response_id;
    }

    public void setResponse_id(String response_id) {
        this.response_id = response_id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

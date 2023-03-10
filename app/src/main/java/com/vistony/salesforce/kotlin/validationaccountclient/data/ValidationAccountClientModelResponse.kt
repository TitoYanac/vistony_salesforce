package com.vistony.salesforce.kotlin.validationaccountclient.data

import com.google.gson.annotations.SerializedName
import com.vistony.salesforce.Entity.Retrofit.Modelo.AddressEntity

class ValidationAccountClientModelResponse {
    @SerializedName("ValidationAccountClient")
    private var ValidationAccountClientModel: List<ValidationAccountClientModel?>? = null

    fun getValidationAccountModel(): List<ValidationAccountClientModel?>? {
        return ValidationAccountClientModel
    }

    fun setValidationAccountModel(ValidationAccountClientModel: List<ValidationAccountClientModel?>?) {
        this.ValidationAccountClientModel = ValidationAccountClientModel
    }
}
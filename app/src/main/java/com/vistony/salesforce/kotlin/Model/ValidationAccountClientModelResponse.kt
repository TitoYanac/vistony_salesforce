package com.vistony.salesforce.kotlin.Model

import com.google.gson.annotations.SerializedName

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
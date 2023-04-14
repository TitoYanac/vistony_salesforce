package com.vistony.salesforce.kotlin.data

import com.google.gson.annotations.SerializedName
import com.vistony.salesforce.kotlin.data.ValidationAccountClientModel

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
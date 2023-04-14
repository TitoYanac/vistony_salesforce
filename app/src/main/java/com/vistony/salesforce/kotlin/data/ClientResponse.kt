package com.vistony.salesforce.kotlin.data

import com.google.gson.annotations.SerializedName

class ClientResponse {
    @SerializedName("Customers")
    private var client: List<Client>? = null

    fun setClient(client: List<Client>?)
    {
        this.client = client
    }

    fun getClient(): List<Client>? {
        return client
    }
}
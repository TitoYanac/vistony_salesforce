package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuoteEffectivenessEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ReasonDispatchEntity;

import java.util.List;

public class QuoteEffectivenessEntityResponse {
    @SerializedName("Quotes")
    private List<QuoteEffectivenessEntity> quoteEffectivenessEntity;
    public QuoteEffectivenessEntityResponse(List<QuoteEffectivenessEntity> quoteEffectivenessEntity) {
        this.quoteEffectivenessEntity = quoteEffectivenessEntity;
    }
    public List<QuoteEffectivenessEntity> getQuoteEffectivenessEntities() {
        return quoteEffectivenessEntity;
    }
}

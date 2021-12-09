package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerDetailEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerInvoiceEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.QuotasPerCustomerDetailEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.QuotasPerCustomerInvoiceEntityResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotasPerCustomerInvoiceRepository extends ViewModel {
    private BancoSQLite bancoSQLite;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<List<QuotasPerCustomerInvoiceEntity>> status= new MutableLiveData<>();


    public MutableLiveData<List<QuotasPerCustomerInvoiceEntity>> getQuotasPerCustomerInvoice(
            String Imei,
            Context context,
            String CardCode
    ){
        Config.getClient().create(Api.class).getQuotasPerCustomerInvoice(Imei,CardCode).enqueue(new Callback<QuotasPerCustomerInvoiceEntityResponse>() {
            @Override
            public void onResponse(Call<QuotasPerCustomerInvoiceEntityResponse> call, Response<QuotasPerCustomerInvoiceEntityResponse> response) {
                Log.e("REOS","QuotasPerCustomerInvoiceRepository.getQuotasPerCustomerInvoice.call:" + call.toString());
                QuotasPerCustomerInvoiceEntityResponse quotasPerCustomerInvoiceEntityResponse=response.body();
                Log.e("REOS","QuotasPerCustomerInvoiceRepository.getQuotasPerCustomerInvoice.response:" + response.toString());
                if(response.isSuccessful() && quotasPerCustomerInvoiceEntityResponse.getQuotasPerCustomerInvoiceEntity().size()>0) {
                    Log.e("REOS", "QuotasPerCustomerInvoiceRepository.getQuotasPerCustomerInvoice.entro:");
                    //QuotasPerCustomerView.newInstanceRecibirListaDetail(QuotasPerCustomerDetailEntityResponse.getQuotasPerCustomerDetailEntity());
                    // }
                    status.setValue(quotasPerCustomerInvoiceEntityResponse.getQuotasPerCustomerInvoiceEntity());
                }
            }

            @Override
            public void onFailure(Call<QuotasPerCustomerInvoiceEntityResponse> call, Throwable t) {
                status.setValue(null);
            }
        });
        return status;
    }
}

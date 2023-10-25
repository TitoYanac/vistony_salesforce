package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.ReasonDispatchSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ReasonDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.TypeDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ReasonDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.TypeDispatchEntityResponse;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReasonDispatchRepository  extends ViewModel {
    private MutableLiveData<String> status= new MutableLiveData<>();
    ReasonDispatchSQLite reasonDispatchSQLite;
    ParametrosSQLite parametrosSQLite;
    public MutableLiveData<String> geReasonDispatch (String Imei, Context context, Executor executor){
        Config.getClient().create(Api.class).getReasonDispatch(Imei).enqueue(new Callback<ReasonDispatchEntityResponse>() {
            @Override
            public void onResponse(Call<ReasonDispatchEntityResponse> call, Response<ReasonDispatchEntityResponse> response) {

                ReasonDispatchEntityResponse reasonDispatchEntityResponse=response.body();
                if(response.isSuccessful() && Objects.requireNonNull(reasonDispatchEntityResponse).getReasonDispatchEntities().size()>0){
                    executor.execute(() -> {
                    reasonDispatchSQLite = new ReasonDispatchSQLite(context);
                    parametrosSQLite = new ParametrosSQLite(context);
                    reasonDispatchSQLite.DeleteTableReasonDispatch();
                    reasonDispatchSQLite.AddReasonDispatch (reasonDispatchEntityResponse.getReasonDispatchEntities());
                    Integer countReasonDispatch=getCountReasonDispatch(context);
                    parametrosSQLite.ActualizaCantidadRegistros("24", "MOTIVO DESPACHO", ""+countReasonDispatch, getDateTime());
                    status.setValue("1");
                    });
                }else
                {
                    status.setValue("0");
                }
            }

            @Override
            public void onFailure(Call<ReasonDispatchEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getCountReasonDispatch(Context context){
        return reasonDispatchSQLite.getCountReasonDispatch() ;
    }
}

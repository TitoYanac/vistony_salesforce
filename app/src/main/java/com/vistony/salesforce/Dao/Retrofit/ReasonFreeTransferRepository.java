package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.ReasonDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.ReasonFreeTransferSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ReasonDispatchEntityResponse;
import com.vistony.salesforce.R;

import java.util.Objects;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReasonFreeTransferRepository  extends ViewModel {
    private MutableLiveData<String> status= new MutableLiveData<>();
    ReasonFreeTransferSQLiteDao reasonFreeTransferSQLiteDao;
    ParametrosSQLite parametrosSQLite;
    public MutableLiveData<String> getReasonFreeTransfer (String Imei, Context context, Executor executor){
        Config.getClient().create(Api.class).getReasonFreeTransfer(Imei).enqueue(new Callback<ReasonDispatchEntityResponse>() {
            @Override
            public void onResponse(Call<ReasonDispatchEntityResponse> call, Response<ReasonDispatchEntityResponse> response) {

                ReasonDispatchEntityResponse reasonDispatchEntityResponse=response.body();
                if(response.isSuccessful() &&
                        //Objects.requireNonNull(reasonDispatchEntityResponse).getReasonDispatchEntities().size()>0
                        response.body()!=null
                ){
                    executor.execute(() -> {
                    reasonFreeTransferSQLiteDao = new ReasonFreeTransferSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);

                    reasonFreeTransferSQLiteDao.DeleteTableReasonFreeTransfer();
                    reasonFreeTransferSQLiteDao.AddReasonFreeTransfer (reasonDispatchEntityResponse.getReasonDispatchEntities());
                    Integer countReasonFreeTransfer=getCountReasonFreeTransfer(context);
                    parametrosSQLite.ActualizaCantidadRegistros("26", context.getResources().getString(R.string.reason_free_transfer).toUpperCase(), ""+countReasonFreeTransfer, getDateTime());
                    });
                    status.setValue("1");
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

    private Integer getCountReasonFreeTransfer(Context context){
        return reasonFreeTransferSQLiteDao.getCountReasonFreeTransfer() ;
    }
}

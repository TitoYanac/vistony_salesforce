package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.EscColoursCSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.EscColoursDSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.PriceListSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.EscColoursCEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PriceListEntityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EscColoursCRepository extends ViewModel {
    private EscColoursCSQLiteDao escColoursCSQLiteDao;
    private EscColoursDSQLiteDao escColoursDSQLiteDao;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String> getEscColours(String Imei, Context context){

        Config.getClient().create(Api.class).getScColoursC(Imei).enqueue(new Callback<EscColoursCEntityResponse>() {
            @Override
            public void onResponse(Call<EscColoursCEntityResponse> call, Response<EscColoursCEntityResponse> response) {
                Log.e("REOS","EscColoursCRepository-getEscColours-call"+call.toString());
                EscColoursCEntityResponse escColoursCEntityResponse=response.body();
                Log.e("REOS","EscColoursCRepository-getEscColours-call"+response.toString());
                if(response.isSuccessful() && escColoursCEntityResponse.getEscColoursEntity() .size()>0){

                    escColoursCSQLiteDao = new EscColoursCSQLiteDao(context);
                    escColoursDSQLiteDao = new EscColoursDSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);
                    escColoursCSQLiteDao.ClearEscColoursC() ;
                    escColoursDSQLiteDao.ClearEscColoursD() ;
                    //escColoursCSQLiteDao.InsertEscColoursC (escColoursCEntityResponse.getEscColoursEntity());


                    for(int i=0;i< escColoursCEntityResponse.getEscColoursEntity().size();i++ )
                    {

                        escColoursCSQLiteDao.InsertEscColoursC (escColoursCEntityResponse.getEscColoursEntity());


                        //Documentos
                        if (escColoursCEntityResponse.getEscColoursEntity().get(i).getEscColoursDEntityList() == null || escColoursCEntityResponse.getEscColoursEntity().get(i).getEscColoursDEntityList().size() == 0) {
                            //ObjCliente.setListInvoice(null);
                        } else {

                            for(int j=0;j<escColoursCEntityResponse.getEscColoursEntity().get(i).getEscColoursDEntityList().size();j++)
                            {
                                escColoursCEntityResponse.getEscColoursEntity().get(i).getEscColoursDEntityList().get(j).setId_esc_colours_c(escColoursCEntityResponse.getEscColoursEntity().get(i).getId());
                            }
                            escColoursDSQLiteDao.InsertEscColoursD (escColoursCEntityResponse.getEscColoursEntity().get(i).getEscColoursDEntityList());

                        }
                    }
                    Integer countColoursC=escColoursCSQLiteDao.getCountEscColoursC();
                    parametrosSQLite.ActualizaCantidadRegistros("21", "COLORES CABECERA", ""+countColoursC, getDateTime());
                    Integer countColoursD=escColoursDSQLiteDao.getCountEscColoursD();
                    parametrosSQLite.ActualizaCantidadRegistros("22", "COLORES DETALLE", ""+countColoursD, getDateTime());
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<EscColoursCEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }
}

package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.RutaFuerzaTrabajoSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.RutaFuerzaTrabajoEntityResponse;
import com.vistony.salesforce.Entity.SQLite.RutaFuerzaTrabajoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RutaFuerzaTrabajoRepository extends ViewModel {

    private ArrayList<RutaFuerzaTrabajoSQLiteEntity> LRFTrabajo =  new ArrayList<>();
    private Context context;
    RutaFuerzaTrabajoSQLiteDao rutaFuerzaTrabajoSQLiteDao;
    private MutableLiveData<String> status= new MutableLiveData<>();
    private ParametrosSQLite parametrosSQLite;
    /*public RutaFuerzaTrabajoRepository(final Context context){
        this.context=context;
    }*/

    /*public ArrayList<RutaFuerzaTrabajoSQLiteEntity> getRutaFuerzaTrabajoWS(String Imei){
        Api api = Config.getClient().create(Api.class);

        Call<RutaFuerzaTrabajoEntityResponse> call = api.getRutaFuerzaTrabajo(Imei);

        try{
            Response<RutaFuerzaTrabajoEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                RutaFuerzaTrabajoEntityResponse rutaFuerzaTrabajoEntityResponse=response.body();
                for(int i=0;i<rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().size();i++){

                    RutaFuerzaTrabajoSQLiteEntity ObjRFTrabajo = new RutaFuerzaTrabajoSQLiteEntity();
                    ObjRFTrabajo.compania_id = SesionEntity.compania_id;
                    ObjRFTrabajo.zona_id = rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().get(i).getZona_id();
                    ObjRFTrabajo.zona = rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().get(i).getZona();
                    ObjRFTrabajo.dia = rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().get(i).getDia();
                    ObjRFTrabajo.frecuencia = rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().get(i).getFrecuencia();
                    ObjRFTrabajo.fechainicioruta = rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().get(i).getFechainicioruta();
                    ObjRFTrabajo.estado = rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().get(i).getEstado();
                    LRFTrabajo.add(ObjRFTrabajo);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return LRFTrabajo;
    }*/

    public MutableLiveData<String> getInsertDBWorkPath(String Imei, Context context){
        Config.getClient().create(Api.class).getRutaFuerzaTrabajo(Imei).enqueue(new Callback<RutaFuerzaTrabajoEntityResponse>() {
            @Override
            public void onResponse(Call<RutaFuerzaTrabajoEntityResponse> call, Response<RutaFuerzaTrabajoEntityResponse> response) {

                RutaFuerzaTrabajoEntityResponse rutaFuerzaTrabajoList=response.body();

                if(response.isSuccessful() && rutaFuerzaTrabajoList.getRutaFuerzaTrabajoEntity().size()>0){

                    rutaFuerzaTrabajoSQLiteDao = new RutaFuerzaTrabajoSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);

                    rutaFuerzaTrabajoSQLiteDao.LimpiarTablaRutaFuerzaTrabajo()  ;
                    rutaFuerzaTrabajoSQLiteDao.InsertWorkPath (rutaFuerzaTrabajoList.getRutaFuerzaTrabajoEntity());
                    Integer countWorkPath=getCountWorkPath (context);
                    //parametrosSQLite.ActualizaCantidadRegistros("2", "BANCOS", ""+countBank, getDateTime());
                    parametrosSQLite.ActualizaCantidadRegistros("12", "RUTA FUERZATRABAJO", String.valueOf(countWorkPath), getDateTime());
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<RutaFuerzaTrabajoEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }


    public ArrayList<String> getDayDB(Context context){
        rutaFuerzaTrabajoSQLiteDao = new RutaFuerzaTrabajoSQLiteDao(context);
        return rutaFuerzaTrabajoSQLiteDao.getDayWorkPath();
    }
    private Integer getCountWorkPath(Context context){
        return rutaFuerzaTrabajoSQLiteDao.ObtenerCantidadRutaFuerzaTrabajo() ;
    }
}

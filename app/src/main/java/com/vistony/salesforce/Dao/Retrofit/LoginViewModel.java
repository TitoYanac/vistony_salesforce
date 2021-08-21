package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vistony.salesforce.Dao.SQLIte.UsuarioSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.LoginEntityResponse;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.R;

import org.json.JSONObject;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vistony.salesforce.Controller.Retrofit.Config.closeConection;

public class LoginViewModel extends ViewModel{
    private UsuarioSQLiteDao usuarioSQLiteDao;

    private MutableLiveData<ArrayList<String>> profile= new MutableLiveData<>();

    public MutableLiveData<ArrayList<String>> getAndLoadUsers(final String imei,final Context context){
        Config.getClient().create(Api.class).getUsers(imei).enqueue(new Callback<LoginEntityResponse>() {
            @Override
            public void onResponse(Call<LoginEntityResponse> call, Response<LoginEntityResponse> response) {

                usuarioSQLiteDao = new UsuarioSQLiteDao(context);
                if(response.isSuccessful() && response.body().getUsers().size()>0){
                    usuarioSQLiteDao.LimpiarTablaUsuario();
                    for (int i = 0; i < response.body().getUsers().size(); i++){
                        usuarioSQLiteDao.InsertaUsuario(
                                response.body().getUsers().get(i).getCompaniaid(),
                                response.body().getUsers().get(i).getFuerzatrabajo_id(),
                                response.body().getUsers().get(i).getNombrecompania(),
                                response.body().getUsers().get(i).getNombreusuario(), //getNombrefuerzadetrabajo pero son iguales anombre de usuario
                                response.body().getUsers().get(i).getNombreusuario(),
                                response.body().getUsers().get(i).getUsuario_id(),
                                response.body().getUsers().get(i).getRecibo(),
                                "0",
                                response.body().getUsers().get(i).getPerfil(),
                                "0",
                                response.body().getUsers().get(i).getLista_precios1(),
                                response.body().getUsers().get(i).getLista_precios2(),
                                response.body().getUsers().get(i).getAlmacen(),
                                response.body().getUsers().get(i).getCogsacct(),
                                response.body().getUsers().get(i).getU_vist_ctaingdcto(),
                                response.body().getUsers().get(i).getDocumentsowner(),
                                response.body().getUsers().get(i).getU_vist_sucusu(),
                                response.body().getUsers().get(i).getCentrocosto(),
                                response.body().getUsers().get(i).getUnidadnegocio(),
                                response.body().getUsers().get(i).getLineaproduccion(),
                                "IGV",
                                "18",
                                response.body().getUsers().get(i).getTipocambio(),
                                response.body().getUsers().get(i).getU_vis_cashdscnt()
                        );
                    }

                }else{
                    Log.e("JEPICAME","=>"+response.code());
                }
                profile.setValue(usuarioSQLiteDao.ObtenerPerfiles());
            }

            @Override
            public void onFailure(Call<LoginEntityResponse> call, Throwable t) {
                usuarioSQLiteDao = new UsuarioSQLiteDao(context);
                ArrayList<String> perfiles=usuarioSQLiteDao.ObtenerPerfiles();
                if(perfiles.size()>0){
                    profile.setValue(perfiles);
                }else{
                    profile.setValue(null);
                }
            }
        });
        //closeConection();
        return profile;
    }

    public ArrayList<String> getCompanies(String profile){
        return usuarioSQLiteDao.ObtenerCompania(profile);
    }

    public ArrayList<String> getUsers(String profile, String compania){
        return usuarioSQLiteDao.ObtenerUsuario(profile,compania);
    }

    public ArrayList<UsuarioSQLiteEntity> loginUser(String compania, String usuario){
        Integer statusQuery=usuarioSQLiteDao.ActualizaUsuario(compania,usuario);
        ArrayList<UsuarioSQLiteEntity> userSesion=usuarioSQLiteDao.ObtenerUsuarioSesion();
        if(!userSesion.isEmpty() && statusQuery==1){
            return userSesion;
        }else{
            return null;
        }
    }
}

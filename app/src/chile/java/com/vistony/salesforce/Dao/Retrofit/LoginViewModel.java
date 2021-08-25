package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.LoginEntityResponse;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel{
    private UsuarioSQLite usuarioSQLite;

    private MutableLiveData<ArrayList<String>> profile= new MutableLiveData<>();

    public MutableLiveData<ArrayList<String>> getAndLoadUsers(final String imei,final Context context){
        Toast.makeText(context, "VENGO DE CHILE", Toast.LENGTH_SHORT).show();

        Config.getClient().create(Api.class).getUsers("http://169.47.196.209/v1.0/api/User?imei="+imei,"").enqueue(new Callback<LoginEntityResponse>() {
            @Override
            public void onResponse(Call<LoginEntityResponse> call, Response<LoginEntityResponse> response) {

                usuarioSQLite = new UsuarioSQLite(context);
                if(response.isSuccessful() && response.body().getUsers().size()>0){
                    usuarioSQLite.LimpiarTablaUsuario();
                    for (int i = 0; i < response.body().getUsers().size(); i++){
                        usuarioSQLite.InsertaUsuario(
                                response.body().getUsers().get(i).getCompaniaid(),
                                response.body().getUsers().get(i).getFuerzatrabajo_id(),
                                response.body().getUsers().get(i).getNombrecompania(),
                                response.body().getUsers().get(i).getNombreusuario(), //getNombrefuerzadetrabajo pero son iguales anombre de usuario
                                response.body().getUsers().get(i).getNombreusuario(),
                                response.body().getUsers().get(i).getUsuario_id(),
                                ""+response.body().getUsers().get(i).getSettings().get(0).getRecibo(),
                                "0",
                                (response.body().getUsers().get(i).getPerfil().equals("VENDEDOR")? "SALES PERSON":response.body().getUsers().get(i).getPerfil()),
                                "0",
                                null,//response.body().getUsers().get(i).getLista_precios1(),
                                null,//response.body().getUsers().get(i).getLista_precios2(),
                                response.body().getUsers().get(i).getAlmacen(),
                                response.body().getUsers().get(i).getSettings().get(0).getCogsAcct(),
                                response.body().getUsers().get(i).getU_vist_ctaingdcto(),
                                response.body().getUsers().get(i).getFuerzatrabajo_id(),
                                response.body().getUsers().get(i).getU_vist_sucusu(),
                                response.body().getUsers().get(i).getSettings().get(0).getCentrocosto(),
                                response.body().getUsers().get(i).getSettings().get(0).getUnidadnegocio(),
                                response.body().getUsers().get(i).getSettings().get(0).getLineaproduccion(),
                                "IGV",
                                "18",
                                response.body().getUsers().get(i).getTipocambio(),
                                response.body().getUsers().get(i).getU_vis_cashdscnt()
                        );
                    }

                }else{
                    Log.e("JEPICAME","=>"+response.code());
                }
                profile.setValue(usuarioSQLite.ObtenerPerfiles());
            }

            @Override
            public void onFailure(Call<LoginEntityResponse> call, Throwable t) {
                usuarioSQLite = new UsuarioSQLite(context);
                ArrayList<String> perfiles= usuarioSQLite.ObtenerPerfiles();
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
        return usuarioSQLite.ObtenerCompania(profile);
    }

    public ArrayList<String> getUsers(String profile, String compania){
        return usuarioSQLite.ObtenerUsuario(profile,compania);
    }

    public ArrayList<UsuarioSQLiteEntity> loginUser(String compania, String usuario){
        Integer statusQuery= usuarioSQLite.ActualizaUsuario(compania,usuario);
        ArrayList<UsuarioSQLiteEntity> userSesion= usuarioSQLite.ObtenerUsuarioSesion();
        if(!userSesion.isEmpty() && statusQuery==1){
            return userSesion;
        }else{
            return null;
        }
    }
}

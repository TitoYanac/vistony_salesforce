package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BancoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.UserEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;

public class UsuarioSQLite {
    SqliteController sqliteController;
    SQLiteDatabase bd;

    public UsuarioSQLite(Context context)
    {
        sqliteController = new SqliteController(context);
    }
    public void abrir(){
        bd = sqliteController.getWritableDatabase();
        Log.i("SQLite", "Se abre conexion sqlite desde "+this.getClass().getName());
    }

    public void cerrar(){
        sqliteController.close();
    }

    public int InsertaUsuario (//UserEntity vendedor
    List<UserEntity> vendedor
    ){

        abrir();

        //if(vendedor.getSettings()==null || vendedor.getSettings().size()==0){
        //    return 0;
        //}else{
        try {
            Log.e("REOS", "UsuarioSQLite.InsertaUsuario.vendedor.size(): " + vendedor.size());
            ContentValues registro = new ContentValues();
            for (int i = 0; i < vendedor.size(); i++) {
                registro.put("imei", vendedor.get(i).getImei());
                registro.put("compania_id", vendedor.get(i).getCompaniaid());
                registro.put("fuerzatrabajo_id", vendedor.get(i).getSlp_code());
                registro.put("nombrecompania", vendedor.get(i).getNombrecompania());
                registro.put("nombrefuerzatrabajo", vendedor.get(i).getNombreusuario());
                registro.put("nombreusuario", vendedor.get(i).getNombreusuario());
                registro.put("usuario_id", vendedor.get(i).getUser_code());
                registro.put("recibo", vendedor.get(i).getSettings().get(0).getRecibo());
                registro.put("chksesion", "0");
                registro.put("online", "0");
                registro.put("perfil", vendedor.get(i).getPerfil());
                registro.put("chkbloqueopago", vendedor.get(i).getSettings().get(0).getMaxDateDeposit());
                // registro.put("listaPrecios_id_1",null);
                //registro.put("listaPrecios_id_2",null);
                registro.put("almacen_id", vendedor.get(i).getAlmacen());
                registro.put("CogsAcct", vendedor.get(i).getSettings().get(0).getCogsAcct());
                registro.put("U_VIST_CTAINGDCTO", vendedor.get(i).getSettings().get(0).getDiscAccount());
                registro.put("DocumentsOwner", vendedor.get(i).getUser_code());
                registro.put("U_VIST_SUCUSU", vendedor.get(i).getU_vist_sucusu());//branch
                registro.put("CentroCosto", vendedor.get(i).getSettings().get(0).getCentrocosto());
                registro.put("UnidadNegocio", vendedor.get(i).getSettings().get(0).getUnidadnegocio());
                registro.put("LineaProduccion", vendedor.get(i).getSettings().get(0).getLineaproduccion());
                registro.put("Impuesto_ID", vendedor.get(i).getSettings().get(0).getTaxCode());
                registro.put("Impuesto", vendedor.get(i).getSettings().get(0).getTaxRate());
                registro.put("U_VIS_CashDscnt", vendedor.get(i).getSettings().get(0).getCashDscnt());
                registro.put("Language", vendedor.get(i).getSettings().get(0).getLanguage());
                registro.put("Country", vendedor.get(i).getCountry());
                registro.put("flag_stock", vendedor.get(i).getSettings().get(0).getOutStock());
                registro.put("flag_backup", vendedor.get(i).getSettings().get(0).getFlagBackup());
                registro.put("rate", vendedor.get(i).getRate());
                registro.put("print", vendedor.get(i).getSettings().get(0).getUsePrinter());
                registro.put("activecurrency", vendedor.get(i).getSettings().get(0).getChangeCurrency());
                registro.put("planta", vendedor.get(i).getPhone());
                Log.e("REOS", "UsuarioSQLite.InsertaUsuario.rate: " + vendedor.get(i).getRate());
                Log.e("REOS", "UsuarioSQLite.InsertaUsuario.vendedor.get(i).getNombreusuario(): " + vendedor.get(i).getNombreusuario());
                bd.insert("usuario", null, registro);
            }
        }
        catch (Exception e){
            Log.e("REOS", "UsuarioSQLite.InsertaUsuario.e: " + e.toString());
        }
            bd.close();

            return 1;

    }



    public int ActualizaUsuario (String CompanyName, String nombrefuerzatrabajo)
    {
        int resultado=0;

        try {
            logoutAllUser();

            abrir();
            ContentValues registro = new ContentValues();
            registro.put("online",1);
            registro.put("chksesion",1);

            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("usuario",registro,"nombrecompania like '%"+CompanyName+"%' and nombrefuerzatrabajo like '%"+nombrefuerzatrabajo+"%'" ,null);
            cerrar();
        }catch (Exception e){
            Log.e("Error"," UsuarioSQLiteDato "+e.getMessage());
        }finally {
            cerrar();
        }

        return resultado;
    }

    private Integer logoutAllUser(){
        int resultado=0;

        try {
            abrir();
            ContentValues registro = new ContentValues();
            registro.put("online",0);
            registro.put("chksesion",0);

            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("usuario",registro,"nombrecompania like '%asdasds%'" ,null);
            cerrar();
        }catch (Exception e){
            Sentry.captureMessage(e.getMessage());
        }finally {
            cerrar();
        }

        return resultado;
    }

    public int LimpiarTablaUsuario ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from usuario ");
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public UsuarioSQLiteEntity ObtenerUsuarioSesion ()
    {
        UsuarioSQLiteEntity usuarioSQLiteEntity = new UsuarioSQLiteEntity();

        try {
            abrir();
            Cursor fila = bd.rawQuery("Select * from usuario where chksesion=? LIMIT 1;",new String [] {"1"});

            if (fila.moveToFirst()) {
                do {
                    usuarioSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
                    usuarioSQLiteEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
                    usuarioSQLiteEntity.setNombrecompania(fila.getString(fila.getColumnIndex("nombrecompania")));
                    usuarioSQLiteEntity.setNombrefuerzatrabajo(fila.getString(fila.getColumnIndex("nombrefuerzatrabajo")));
                    usuarioSQLiteEntity.setNombreUsuario(fila.getString(fila.getColumnIndex("nombreusuario")));
                    usuarioSQLiteEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
                    usuarioSQLiteEntity.setRecibo(fila.getString(fila.getColumnIndex("recibo")));
                    usuarioSQLiteEntity.setChksesion(fila.getString(fila.getColumnIndex("chksesion")));
                    usuarioSQLiteEntity.setOnline(fila.getString(fila.getColumnIndex("online")));
                    usuarioSQLiteEntity.setPerfil(fila.getString(fila.getColumnIndex("perfil")));
                    usuarioSQLiteEntity.setChkbloqueopago(fila.getString(fila.getColumnIndex("chkbloqueopago")));
                    usuarioSQLiteEntity.setListaprecio_id_1(fila.getString(fila.getColumnIndex("listaPrecios_id_1")));
                    usuarioSQLiteEntity.setListaprecio_id_2(fila.getString(fila.getColumnIndex("listaPrecios_id_2")));
                    usuarioSQLiteEntity.setAlmacen_id(fila.getString(fila.getColumnIndex("almacen_id")));
                    usuarioSQLiteEntity.setCogsacct(fila.getString(fila.getColumnIndex("CogsAcct")));
                    usuarioSQLiteEntity.setU_vist_ctaingdcto(fila.getString(fila.getColumnIndex("U_VIST_CTAINGDCTO")));
                    usuarioSQLiteEntity.setDocumentsowner(fila.getString(fila.getColumnIndex("DocumentsOwner")));
                    usuarioSQLiteEntity.setU_VIST_SUCUSU(fila.getString(fila.getColumnIndex("U_VIST_SUCUSU")));
                    usuarioSQLiteEntity.setCentroCosto(fila.getString(fila.getColumnIndex("CentroCosto")));
                    usuarioSQLiteEntity.setUnidadNegocio(fila.getString(fila.getColumnIndex("UnidadNegocio")));
                    usuarioSQLiteEntity.setLineaProduccion(fila.getString(fila.getColumnIndex("LineaProduccion")));
                    usuarioSQLiteEntity.setImpuesto_ID(fila.getString(fila.getColumnIndex("Impuesto_ID")));
                    usuarioSQLiteEntity.setImpuesto(fila.getString(fila.getColumnIndex("Impuesto")));
                    usuarioSQLiteEntity.setU_VIS_CashDscnt(fila.getString(fila.getColumnIndex("U_VIS_CashDscnt")));
                    usuarioSQLiteEntity.setFLAG_STOCK(fila.getString(fila.getColumnIndex("flag_stock")));
                    usuarioSQLiteEntity.setFLAG_BACKUP(fila.getString(fila.getColumnIndex("flag_backup")));
                    usuarioSQLiteEntity.setCountry(fila.getString(fila.getColumnIndex("Country")));
                    usuarioSQLiteEntity.setLenguage(fila.getString(fila.getColumnIndex("Language")));
                    usuarioSQLiteEntity.setImei(fila.getString(fila.getColumnIndex("imei")));
                    usuarioSQLiteEntity.setRate(fila.getString(fila.getColumnIndex("rate")));
                    usuarioSQLiteEntity.setPrint(fila.getString(fila.getColumnIndex("print")));
                    usuarioSQLiteEntity.setActivecurrency(fila.getString(fila.getColumnIndex("activecurrency")));
                    usuarioSQLiteEntity.setPlanta (fila.getString(fila.getColumnIndex("planta")));
                } while (fila.moveToNext());
            }


        }catch (Exception e){
            Sentry.captureMessage(e.getMessage());
        }

        bd.close();
        return usuarioSQLiteEntity;

    }

    public int ActualizaRecibo (String recibo)
    {

        int resultado=0;
        String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("recibo",recibo);
            //registro.put("precio",pre);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("usuario",registro,"",null);

            /*Cursor fila = bd.rawQuery(
                    "update cobranzadetalle set cobranza_id = '"+cobranza_id+ "'" +
                            //+ ",chkdepositado = '"+chkdepositado+"'"+
                            " where recibo= '"+recibo+"'"
                            //+ " and compania_id= '"+compania_id+"'"
                    ,null);
            */

            //resultado=1;
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }

        return  resultado;

    }


    public ArrayList<UsuarioSQLiteEntity> ObtenerUsuarioBlockPay ()
    {
        ArrayList<UsuarioSQLiteEntity> listaDDeudaentity = new ArrayList<UsuarioSQLiteEntity>();
        try {


            UsuarioSQLiteEntity usuarioSQLiteEntity;
            abrir();
            Cursor fila = bd.rawQuery("Select chkbloqueopago from usuario where chksesion='1'", null);

            while (fila.moveToNext()) {
                usuarioSQLiteEntity = new UsuarioSQLiteEntity();
                usuarioSQLiteEntity.setChkbloqueopago(fila.getString(0));
                listaDDeudaentity.add(usuarioSQLiteEntity);
            }

            bd.close();
        }catch (Exception e)
        {
            bd.close();
            e.printStackTrace();
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaDDeudaentity;

    }

    public ArrayList<String> ObtenerUsuario(String profile,String compania){
        try {
            abrir();

            Cursor fila = bd.rawQuery("SELECT nombreusuario AS usuario FROM usuario WHERE nombrecompania ='"+compania+"' AND perfil='"+profile+"';", null);
            ArrayList<String> usuarios = new  ArrayList<String>();

            if (fila.moveToFirst()) {
                do {
                    usuarios.add(fila.getString(fila.getColumnIndex("usuario")));
                } while (fila.moveToNext());
            }

            bd.close();
            return usuarios;

        }catch (Exception e){
            bd.close();
            return null;
        }
    }

    public ArrayList<String> ObtenerPerfiles(){
        try {
            abrir();

            Cursor fila = bd.rawQuery("SELECT DISTINCT(perfil) AS perfil FROM usuario;", null);
            ArrayList<String> perfiles = new  ArrayList<String>();

            if (fila.moveToFirst()) {
                if (fila.moveToFirst()) {
                    do {
                        perfiles.add(fila.getString(fila.getColumnIndex("perfil")));
                    } while (fila.moveToNext());
                }
            }

            bd.close();
            return perfiles;

        }catch (Exception e){
            bd.close();
            return null;
        }
    }

    public ArrayList<String> ObtenerCompania(String profile){
        try {
            abrir();
            Cursor fila = bd.rawQuery("SELECT DISTINCT(nombrecompania) AS compania FROM usuario WHERE perfil ='"+profile+"';", null);
            ArrayList<String> compania = new  ArrayList<String>();

            if (fila.moveToFirst()) {
                if (fila.moveToFirst()) {
                    do {
                        compania.add(fila.getString(fila.getColumnIndex("compania")));
                    } while (fila.moveToNext());
                }
            }
            bd.close();
            return compania;
        }catch (Exception e){
            bd.close();
            return null;
        }
    }

    public String ObtenerListaPrecioUsuario(String contado)
    {
        UsuarioSQLiteEntity usuarioSQLiteEntity;
        abrir();
        String codigolistaprecio="";
        Cursor fila=null;
        try
        {

            if(contado.equals("1"))
            {
                fila = bd.rawQuery(
                        "Select listaPrecios_id_1 from usuario where chksesion='1'", null);
            }
            else
            {
                fila = bd.rawQuery(
                        "Select listaPrecios_id_2 from usuario where chksesion='1'", null);
            }

            while (fila.moveToNext()) {
                codigolistaprecio=fila.getString(0);
            }

            bd.close();
        }catch (Exception e)
        {
            bd.close();
            e.printStackTrace();
        }

        return codigolistaprecio;
    }

}

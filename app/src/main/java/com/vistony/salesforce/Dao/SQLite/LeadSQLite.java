package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.ImageCameraController;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Adapters.DireccionCliente;
import com.vistony.salesforce.Entity.Retrofit.Modelo.LeadAddressEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.LeadEntity;
import com.vistony.salesforce.Entity.SQLite.DireccionClienteSQLiteEntity;

import java.util.ArrayList;

public class LeadSQLite {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<DireccionClienteSQLiteEntity> listaDireccionClienteSQLiteEntity;
    Context context;
    public LeadSQLite(Context context)
    {
        sqliteController = new SqliteController(context);
        this.context=context;
    }
    private void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos desde " + this.getClass().getName());
        bd = sqliteController.getWritableDatabase();
    }

    private void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion desde " + this.getClass().getName());
        sqliteController.close();
    }

    public int addLead(
            String razon_social,
            String ruc,
            String nombre_comercial,
            String numero_telefono,
            String numero_celular,
            String persona_contacto,
            String correo,
            String latitud,
            String longitud,
            String comentario,
            String categoria,
            String foto,
            String fecha,
            String sales_person,
            String document_owner,
            String direccion,
            String referencias,
            String cardcode,
            String domembarque_id,
            String type,
            String addresscode
    )
    {
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("razon_social",razon_social);
        registro.put("ruc",ruc);
        registro.put("nombre_comercial",nombre_comercial);
        registro.put("numero_telefono",numero_telefono);
        registro.put("numero_celular",numero_celular);
        registro.put("persona_contacto",persona_contacto);
        registro.put("correo",correo);
        registro.put("latitud",latitud);
        registro.put("longitud",longitud);
        registro.put("comentario",comentario);
        registro.put("categoria",categoria);
        registro.put("foto",foto);
        registro.put("fecha",fecha);
        registro.put("recibido_api",0);
        registro.put("sales_person",sales_person);
        registro.put("document_owner",document_owner);
        registro.put("direccion",direccion);
        registro.put("referencias",referencias);
        registro.put("cardcode",cardcode);
        registro.put("domembarque_id",domembarque_id);
        registro.put("type",type);
        registro.put("addresscode",addresscode);
        bd.insert("lead",null,registro);
        bd.close();
        return 1;
    }

    public int updateStatusSend(String fecha, String razon_social)
    {
        int resultado=0;

        try {
            abrir();
            ContentValues registro = new ContentValues();
            registro.put("recibido_api",1);

            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("lead",registro,"fecha like '%"+fecha+"%' and razon_social like '%"+razon_social+"%' and recibido_api=0" ,null);
            cerrar();
        }catch (Exception e){
            Log.e("Error"," leadSqlite "+e.getMessage());
        }finally {
            cerrar();
        }

        return resultado;
    }

    public ArrayList<LeadEntity> getLeadNotSend(){
        try {
            abrir();

            Cursor fila = bd.rawQuery("SELECT * FROM lead WHERE recibido_api =0;", null);
            ArrayList<LeadEntity> leads = new  ArrayList<LeadEntity>();

            if (fila.moveToFirst()) {
                do {
                    LeadEntity leadEntity=new LeadEntity();

                    leadEntity.setId(""+fila.getInt(fila.getColumnIndex("id")));
                    leadEntity.setDocument_owner(fila.getString(fila.getColumnIndex("document_owner")));
                    leadEntity.setSales_person(fila.getString(fila.getColumnIndex("sales_person")));
                    leadEntity.setRazon_social(fila.getString(fila.getColumnIndex("razon_social")));
                    leadEntity.setRuc(fila.getString(fila.getColumnIndex("ruc")));
                    leadEntity.setNombre_comercial(fila.getString(fila.getColumnIndex("nombre_comercial")));
                    leadEntity.setNumero_telefono(fila.getString(fila.getColumnIndex("numero_telefono")));
                    leadEntity.setNumero_celular(fila.getString(fila.getColumnIndex("numero_celular")));
                    leadEntity.setPersona_contacto(fila.getString(fila.getColumnIndex("persona_contacto")));
                    leadEntity.setCorreo(fila.getString(fila.getColumnIndex("correo")));
                    leadEntity.setLatitud(fila.getString(fila.getColumnIndex("latitud")));
                    leadEntity.setLongitud(fila.getString(fila.getColumnIndex("longitud")));
                    leadEntity.setDireccion(fila.getString(fila.getColumnIndex("direccion")));
                    leadEntity.setReferencias(fila.getString(fila.getColumnIndex("referencias")));
                    leadEntity.setComentario(fila.getString(fila.getColumnIndex("comentario")));
                    leadEntity.setCategoria(fila.getString(fila.getColumnIndex("categoria")));
                    leadEntity.setFoto(fila.getString(fila.getColumnIndex("foto")));
                    leadEntity.setFecha(fila.getString(fila.getColumnIndex("fecha")));
                    leadEntity.setRecibido_api(""+fila.getInt(fila.getColumnIndex("recibido_api")));

                    leads.add(leadEntity);
                } while (fila.moveToNext());
            }

            bd.close();
            return leads;

        }catch (Exception ex){
            Log.e("JEPICAME","=>"+ex.getMessage());
            bd.close();
            return null;
        }
    }

    public int clearTableLead (){
        abrir();
        bd.execSQL("delete from lead");
        bd.close();
        return 1;
    }


    public ArrayList<LeadAddressEntity> getGeolocationClient(){
        try {
            abrir();

            Cursor fila = bd.rawQuery("SELECT  cardcode,domembarque_id,IFNULL(latitud,'0') AS latitud ,IFNULL(longitud,'0') AS longitud,foto FROM lead WHERE recibido_api =0 and type='leadUpdateClientCensus' LIMIT 3 ;", null);
            ArrayList<LeadAddressEntity> leads = new  ArrayList<LeadAddressEntity>();

            if (fila.moveToFirst()) {
                do {
                    String encoded = null;
                    ImageCameraController imageCameraController=new ImageCameraController();
                    LeadAddressEntity leadAddressEntity=new LeadAddressEntity();
                    leadAddressEntity.setCardCode(fila.getString(fila.getColumnIndex("cardcode")));
                    DireccionSQLite direccionSQLite=new DireccionSQLite(context);
                    ArrayList<DireccionCliente> listDireccionCliente=new ArrayList<>();
                    listDireccionCliente=direccionSQLite.getListAddressOV(fila.getString(fila.getColumnIndex("cardcode")),fila.getString(fila.getColumnIndex("domembarque_id")));
                    for(int i= 0;i<listDireccionCliente.size();i++)
                    {
                        leadAddressEntity.setAddressCode(listDireccionCliente.get(i).getAddresscode());
                    }
                    //leadAddressEntity.setAddressCode(fila.getString(fila.getColumnIndex("addresscode")));
                    leadAddressEntity.setLatitude(fila.getString(fila.getColumnIndex("latitud")));
                    leadAddressEntity.setLongitude(fila.getString(fila.getColumnIndex("longitud")));
                    encoded=imageCameraController.getBASE64(fila.getString(fila.getColumnIndex("foto")));
                    String Base64PhotoLocal = encoded.replace("\n", "");
                    String Base64PhotoLocal2 = Base64PhotoLocal.replace("'\u003d'", "=");
                    leadAddressEntity.setPhoto(Base64PhotoLocal2);
                    leads.add(leadAddressEntity);
                } while (fila.moveToNext());
            }

            bd.close();
            return leads;

        }catch (Exception ex){
            Log.e("REOS","LeadSQLite-getGeolocationClient-ex"+ex.getMessage());
            bd.close();
            return null;
        }
    }

    public int UpdateResultLeadAddress (String cardcode,String addresscode,String message){
        int status=0;
        String domembarque_id="";
        DireccionSQLite direccionSQLite=new DireccionSQLite(context);
        ArrayList<DireccionCliente> listDireccionCliente=new ArrayList<>();
        listDireccionCliente=direccionSQLite.getListAddress(cardcode,addresscode);
        for(int i=0;i<listDireccionCliente.size();i++)
        {
            domembarque_id=listDireccionCliente.get(i).getDomembarque_id();
        }
        try {
            abrir();
            ContentValues registro = new ContentValues();
            registro.put("recibido_api","1");
            registro.put("messageserver",message);
            bd.update("lead",registro,"cardcode=? and domembarque_id=?",new String[]{cardcode,domembarque_id});
            status=1;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }
        return status;
    }


    public int UpdateLeadJSON (String json,String cardcode){
        int status=0;

        try {
            abrir();

            ContentValues registro = new ContentValues();
            registro.put("comentario",json);

            bd.update("lead",registro,"cardcode=?",new String[]{cardcode});
            status=1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }
        return status;
    }
}

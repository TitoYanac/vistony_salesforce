package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Adapters.DireccionCliente;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BancoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerHeadEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.LeadAddressEntity;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;


public class BusinessLayerHeadSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<BancoSQLiteEntity> listaBancoSQLiteEntity;
    Context context;
    public BusinessLayerHeadSQLiteDao(Context context)
    {
        sqliteController = new SqliteController(context);
        this.context=context;
    }
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos " + sqliteController.getDatabaseName() );
        bd = sqliteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + sqliteController.getDatabaseName() );
        sqliteController.close();
    }


    //public int InsertaBanco (String banco_id,String compania_id,String nombrebanco)
    public int addBusinessLayer (List<BusinessLayerHeadEntity> BusinessLayer)
    {
        abrir();
        BusinessLayerDetailSQLiteDao businessLayerDetailSQLiteDao=new BusinessLayerDetailSQLiteDao(context);
        for (int i = 0; i < BusinessLayer.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id", SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id", SesionEntity.usuario_id);
            registro.put("Code",BusinessLayer.get(i).getCode());
            registro.put("Name",BusinessLayer.get(i).getName());
            registro.put("U_VIS_Objetive",BusinessLayer.get(i).getU_VIS_Objetive());
            registro.put("U_VIS_VariableType",BusinessLayer.get(i).getU_VIS_VariableType());
            registro.put("U_VIS_Variable",BusinessLayer.get(i).getU_VIS_Variable());
            registro.put("U_VIS_Trigger",BusinessLayer.get(i).getU_VIS_Trigger());
            registro.put("U_VIS_TriggerType",BusinessLayer.get(i).getU_VIS_TriggerType());
            registro.put("U_VIS_Active",BusinessLayer.get(i).getU_VIS_Active());
            registro.put("U_VIS_ValidFrom",BusinessLayer.get(i).getU_VIS_ValidFrom());
            registro.put("U_VIS_ValidUntil",BusinessLayer.get(i).getU_VIS_ValidUntil());
            businessLayerDetailSQLiteDao.addBusinessLayerDetail(BusinessLayer.get(i).getDetails(),BusinessLayer.get(i).getCode());
            bd.insert("businesslayerhead",null,registro);
        }

        bd.close();
        return 1;
    }

    public int clearTableBusinessLayerHead()
    {
        abrir();
        bd.execSQL("delete from businesslayerhead ");
        bd.close();
        return 1;
    }

    public Integer getCountBusinessLayerHead ()
    {
        Integer resultado=0;
        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(compania_id) from businesslayerhead",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));
            }
        }catch (Exception e)
        {
            //Sentry.captureMessage(e.getMessage());
        }finally {
            bd.close();
        }
        return resultado;
    }

    public ArrayList<BusinessLayerHeadEntity> getBusinessLayer(
            String U_VIS_Objetive
    ){
        try {
            abrir();
            BusinessLayerDetailSQLiteDao businessLayerDetailSQLiteDao=new BusinessLayerDetailSQLiteDao(context);
            Cursor fila = bd.rawQuery("SELECT  * FROM businesslayerhead WHERE U_VIS_Objetive ='"+U_VIS_Objetive+"'", null);
            ArrayList<BusinessLayerHeadEntity> listBusinessLayerHeadEntity = new  ArrayList<BusinessLayerHeadEntity>();

            if (fila.moveToFirst()) {
                do {
                    BusinessLayerHeadEntity businessLayerHeadEntity=new BusinessLayerHeadEntity();
                    businessLayerHeadEntity.setCode(fila.getString(fila.getColumnIndex("Code")));
                    businessLayerHeadEntity.setName(fila.getString(fila.getColumnIndex("Name")));
                    businessLayerHeadEntity.setU_VIS_Objetive(fila.getString(fila.getColumnIndex("U_VIS_Objetive")));
                    businessLayerHeadEntity.setU_VIS_VariableType(fila.getString(fila.getColumnIndex("U_VIS_VariableType")));
                    businessLayerHeadEntity.setU_VIS_Variable(fila.getString(fila.getColumnIndex("U_VIS_Variable")));
                    businessLayerHeadEntity.setU_VIS_Trigger(fila.getString(fila.getColumnIndex("U_VIS_Trigger")));
                    businessLayerHeadEntity.setU_VIS_TriggerType(fila.getString(fila.getColumnIndex("U_VIS_TriggerType")));
                    businessLayerHeadEntity.setU_VIS_Active(fila.getString(fila.getColumnIndex("U_VIS_Active")));
                    businessLayerHeadEntity.setU_VIS_ValidFrom(fila.getString(fila.getColumnIndex("U_VIS_ValidFrom")));
                    businessLayerHeadEntity.setU_VIS_ValidUntil(fila.getString(fila.getColumnIndex("U_VIS_ValidUntil")));
                    businessLayerHeadEntity.setDetails(businessLayerDetailSQLiteDao.getBusinessLayerDetail(fila.getString(fila.getColumnIndex("Code"))));
                    listBusinessLayerHeadEntity.add(businessLayerHeadEntity);
                } while (fila.moveToNext());
            }
            bd.close();
            return listBusinessLayerHeadEntity;
        }catch (Exception ex){
            Log.e("REOS","LeadSQLite-getGeolocationBlock-ex"+ex.getMessage());
            bd.close();
            return null;
        }
    }
}

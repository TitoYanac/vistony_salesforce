package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerDetailEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerHeadEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerSalesDetailDetailEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerSalesDetailHeaderEntity;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;

public class BusinessLayerSalesDetailHeaderDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    Context context;
    public BusinessLayerSalesDetailHeaderDao(Context context)
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
    public int addBusinessLayerSalesOrder (List<BusinessLayerSalesDetailHeaderEntity> businessLayerSalesDetailHeaderEntity)
    {
        abrir();
        BusinessLayerSalesDetailDetailDao businessLayerSalesDetailDetailDao=new BusinessLayerSalesDetailDetailDao(context);
        for (int i = 0; i < businessLayerSalesDetailHeaderEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id", SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id", SesionEntity.usuario_id);
            registro.put("Code",businessLayerSalesDetailHeaderEntity.get(i).getCode());
            registro.put("Object",businessLayerSalesDetailHeaderEntity.get(i).getObject());
            registro.put("Name",businessLayerSalesDetailHeaderEntity.get(i).getName());
            registro.put("Action",businessLayerSalesDetailHeaderEntity.get(i).getAction());
            businessLayerSalesDetailDetailDao.addBusinessLayerSalesDetailDetail(businessLayerSalesDetailHeaderEntity.get(i).getDetails(),businessLayerSalesDetailHeaderEntity.get(i).getCode());
            bd.insert("businesslayersalesdetailHeader",null,registro);
        }

        bd.close();
        return 1;
    }

    public int clearTableBusinessLayerSalesDetailHeader()
    {
        abrir();
        bd.execSQL("delete from businesslayersalesdetailHeader ");
        bd.close();
        return 1;
    }

    public Integer getCountBusinessLayerSalesDetailHeader ()
    {
        Integer resultado=0;
        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(compania_id) from businesslayersalesdetailHeader",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));
            }
        }catch (Exception e)
        {
            Sentry.captureMessage(e.getMessage());
        }finally {
            bd.close();
        }
        return resultado;
    }

    public ArrayList<BusinessLayerSalesDetailHeaderEntity> getBusinessLayerSalesDetail(
            String Object
    ){
        try {
            abrir();
            BusinessLayerSalesDetailDetailDao businessLayerSalesDetailDetailDao=new BusinessLayerSalesDetailDetailDao(context);
            Cursor fila = bd.rawQuery("SELECT  * FROM businesslayersalesdetailHeader WHERE Object ='"+Object+"'", null);
            ArrayList<BusinessLayerSalesDetailHeaderEntity> listBusinessLayerSalesDetailHeaderEntity = new  ArrayList<BusinessLayerSalesDetailHeaderEntity>();

            if (fila.moveToFirst()) {
                do {
                    BusinessLayerSalesDetailHeaderEntity businessLayerSalesDetailHeaderEntity=new BusinessLayerSalesDetailHeaderEntity();
                    businessLayerSalesDetailHeaderEntity.setCode(fila.getString(fila.getColumnIndex("Code")));
                    businessLayerSalesDetailHeaderEntity.setObject(fila.getString(fila.getColumnIndex("Object")));
                    businessLayerSalesDetailHeaderEntity.setName(fila.getString(fila.getColumnIndex("Name")));
                    businessLayerSalesDetailHeaderEntity.setAction(fila.getString(fila.getColumnIndex("Action")));
                    businessLayerSalesDetailHeaderEntity.setDetails(businessLayerSalesDetailDetailDao.getBusinessLayerSalesDetailDetail(fila.getString(fila.getColumnIndex("Code"))));
                    listBusinessLayerSalesDetailHeaderEntity.add(businessLayerSalesDetailHeaderEntity);
                } while (fila.moveToNext());
            }
            bd.close();
            return listBusinessLayerSalesDetailHeaderEntity;
        }catch (Exception ex){
            Log.e("REOS","LeadSQLite-getGeolocationBlock-ex"+ex.getMessage());
            bd.close();
            return null;
        }
    }

}

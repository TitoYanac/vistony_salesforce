package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerDetailEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerSalesDetailDetailEntity;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;


public class BusinessLayerSalesDetailDetailDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;

    public BusinessLayerSalesDetailDetailDao(Context context)
    {
        sqliteController = new SqliteController(context);
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
    public int addBusinessLayerSalesDetailDetail (
            List<BusinessLayerSalesDetailDetailEntity> businessLayerSalesDetailDetailEntity,
            String Code
    )
    {
        abrir();
        for (int i = 0; i < businessLayerSalesDetailDetailEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id", SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id", SesionEntity.usuario_id);
            registro.put("Code",Code);
            registro.put("LineId",businessLayerSalesDetailDetailEntity.get(i).getLineId());
            registro.put("RangeActive",businessLayerSalesDetailDetailEntity.get(i).getRangeActive());
            registro.put("Object",businessLayerSalesDetailDetailEntity.get(i).getObject());
            registro.put("TypeObject",businessLayerSalesDetailDetailEntity.get(i).getTypeObject());
            registro.put("ValueMin",businessLayerSalesDetailDetailEntity.get(i).getValueMin());
            registro.put("ValueMax",businessLayerSalesDetailDetailEntity.get(i).getValueMax());
            registro.put("Field",businessLayerSalesDetailDetailEntity.get(i).getField());
            registro.put("Variable",businessLayerSalesDetailDetailEntity.get(i).getVariable());
            bd.insert("businesslayersalesdetailDetail",null,registro);
        }

        bd.close();
        return 1;
    }

    public int clearTableBusinessLayerSalesDetailDetail()
    {
        abrir();
        bd.execSQL("delete from businesslayersalesdetailDetail ");
        bd.close();
        return 1;
    }

    public Integer CountBusinessLayerSalesDetailDetail ()
    {
        Integer resultado=0;
        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(compania_id) from businesslayersalesdetailDetail",null);

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

    public ArrayList<BusinessLayerSalesDetailDetailEntity> getBusinessLayerSalesDetailDetail(
            String Code
    ){
        try {
            abrir();

            Cursor fila = bd.rawQuery("SELECT  * FROM businesslayersalesdetailDetail WHERE Code ='"+Code+"'", null);
            ArrayList<BusinessLayerSalesDetailDetailEntity> listBusinessLayerSalesDetailDetailEntity = new  ArrayList<BusinessLayerSalesDetailDetailEntity>();

            if (fila.moveToFirst()) {
                do {
                    BusinessLayerSalesDetailDetailEntity businessLayerSalesDetailDetailEntity=new BusinessLayerSalesDetailDetailEntity();
                    businessLayerSalesDetailDetailEntity.setLineId(fila.getString(fila.getColumnIndex("LineId")));
                    businessLayerSalesDetailDetailEntity.setRangeActive(fila.getString(fila.getColumnIndex("RangeActive")));
                    businessLayerSalesDetailDetailEntity.setObject(fila.getString(fila.getColumnIndex("Object")));
                    businessLayerSalesDetailDetailEntity.setTypeObject(fila.getString(fila.getColumnIndex("TypeObject")));
                    businessLayerSalesDetailDetailEntity.setValueMin(fila.getString(fila.getColumnIndex("ValueMin")));
                    businessLayerSalesDetailDetailEntity.setValueMax(fila.getString(fila.getColumnIndex("ValueMax")));
                    businessLayerSalesDetailDetailEntity.setField(fila.getString(fila.getColumnIndex("Field")));
                    businessLayerSalesDetailDetailEntity.setVariable(fila.getString(fila.getColumnIndex("Variable")));
                    listBusinessLayerSalesDetailDetailEntity.add(businessLayerSalesDetailDetailEntity);
                } while (fila.moveToNext());
            }
            bd.close();
            return listBusinessLayerSalesDetailDetailEntity;
        }catch (Exception ex){
            Log.e("REOS","LeadSQLite-getGeolocationBlock-ex"+ex.getMessage());
            bd.close();
            return null;
        }
    }

    public ArrayList<BusinessLayerSalesDetailDetailEntity> getBusinessLayerSalesDetailDetailPercent(
            String Code,
            String percent
    ){
        try {
            abrir();

            Cursor fila = bd.rawQuery("SELECT  * FROM businesslayersalesdetailDetail WHERE Code ='"+Code+"' and CAST(ValueMin AS INT)  <='"+percent+"' AND CAST(ValueMax  AS INT) >='"+percent+"' AND RangeActive='Y' ", null);
            ArrayList<BusinessLayerSalesDetailDetailEntity> listBusinessLayerSalesDetailDetailEntity = new  ArrayList<BusinessLayerSalesDetailDetailEntity>();

            if (fila.moveToFirst()) {
                do {
                    BusinessLayerSalesDetailDetailEntity businessLayerSalesDetailDetailEntity=new BusinessLayerSalesDetailDetailEntity();
                    businessLayerSalesDetailDetailEntity.setLineId(fila.getString(fila.getColumnIndex("LineId")));
                    businessLayerSalesDetailDetailEntity.setRangeActive(fila.getString(fila.getColumnIndex("RangeActive")));
                    businessLayerSalesDetailDetailEntity.setObject(fila.getString(fila.getColumnIndex("Object")));
                    businessLayerSalesDetailDetailEntity.setTypeObject(fila.getString(fila.getColumnIndex("TypeObject")));
                    businessLayerSalesDetailDetailEntity.setValueMin(fila.getString(fila.getColumnIndex("ValueMin")));
                    businessLayerSalesDetailDetailEntity.setValueMax(fila.getString(fila.getColumnIndex("ValueMax")));
                    businessLayerSalesDetailDetailEntity.setField(fila.getString(fila.getColumnIndex("Field")));
                    businessLayerSalesDetailDetailEntity.setVariable(fila.getString(fila.getColumnIndex("Variable")));
                    listBusinessLayerSalesDetailDetailEntity.add(businessLayerSalesDetailDetailEntity);
                } while (fila.moveToNext());
            }
            bd.close();
            return listBusinessLayerSalesDetailDetailEntity;
        }catch (Exception ex){
            Log.e("REOS","LeadSQLite-getGeolocationBlock-ex"+ex.getMessage());
            bd.close();
            return null;
        }
    }

    public ArrayList<BusinessLayerSalesDetailDetailEntity> getBusinessLayerSalesDetailDetailType(
            String Code,
            String Object,
            String TypeObject
    ){
        try {
            abrir();

            Cursor fila = bd.rawQuery("SELECT  * FROM businesslayersalesdetailDetail WHERE Code ='"+Code+"' and Object='"+Object+"' AND TypeObject='"+TypeObject+"' AND RangeActive='N'", null);
            ArrayList<BusinessLayerSalesDetailDetailEntity> listBusinessLayerSalesDetailDetailEntity = new  ArrayList<BusinessLayerSalesDetailDetailEntity>();

            if (fila.moveToFirst()) {
                do {
                    BusinessLayerSalesDetailDetailEntity businessLayerSalesDetailDetailEntity=new BusinessLayerSalesDetailDetailEntity();
                    businessLayerSalesDetailDetailEntity.setLineId(fila.getString(fila.getColumnIndex("LineId")));
                    businessLayerSalesDetailDetailEntity.setRangeActive(fila.getString(fila.getColumnIndex("RangeActive")));
                    businessLayerSalesDetailDetailEntity.setObject(fila.getString(fila.getColumnIndex("Object")));
                    businessLayerSalesDetailDetailEntity.setTypeObject(fila.getString(fila.getColumnIndex("TypeObject")));
                    businessLayerSalesDetailDetailEntity.setValueMin(fila.getString(fila.getColumnIndex("ValueMin")));
                    businessLayerSalesDetailDetailEntity.setValueMax(fila.getString(fila.getColumnIndex("ValueMax")));
                    businessLayerSalesDetailDetailEntity.setField(fila.getString(fila.getColumnIndex("Field")));
                    businessLayerSalesDetailDetailEntity.setVariable(fila.getString(fila.getColumnIndex("Variable")));
                    listBusinessLayerSalesDetailDetailEntity.add(businessLayerSalesDetailDetailEntity);
                } while (fila.moveToNext());
            }
            bd.close();
            return listBusinessLayerSalesDetailDetailEntity;
        }catch (Exception ex){
            Log.e("REOS","LeadSQLite-getGeolocationBlock-ex"+ex.getMessage());
            bd.close();
            return null;
        }
    }
}

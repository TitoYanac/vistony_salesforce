package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerDetailEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerHeadEntity;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;

public class BusinessLayerDetailSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<BancoSQLiteEntity> listaBancoSQLiteEntity;

    public BusinessLayerDetailSQLiteDao(Context context)
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
    public int addBusinessLayerDetail (
            List<BusinessLayerDetailEntity> BusinessLayerDetail,
            String Code
    )
    {
        abrir();
        for (int i = 0; i < BusinessLayerDetail.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id", SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id", SesionEntity.usuario_id);
            registro.put("Code",Code);
            registro.put("LineId",BusinessLayerDetail.get(i).getLineId());
            registro.put("U_VIS_Type",BusinessLayerDetail.get(i).getU_VIS_Type());
            registro.put("U_VIS_Object",BusinessLayerDetail.get(i).getU_VIS_Object());
            registro.put("U_VIS_Action",BusinessLayerDetail.get(i).getU_VIS_Action());
            bd.insert("businesslayerdetail",null,registro);
        }

        bd.close();
        return 1;
    }

    public int clearTableBusinessLayerDetail()
    {
        abrir();
        bd.execSQL("delete from businesslayerdetail ");
        bd.close();
        return 1;
    }

    public Integer CountBusinessLayerDetail ()
    {
        Integer resultado=0;
        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(compania_id) from businesslayerdetail",null);

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

    public ArrayList<BusinessLayerDetailEntity> getBusinessLayerDetail(
            String Code
    ){
        try {
            abrir();

            Cursor fila = bd.rawQuery("SELECT  * FROM businesslayerdetail WHERE Code ='"+Code+"'", null);
            ArrayList<BusinessLayerDetailEntity> listBusinessLayerDetailEntity = new  ArrayList<BusinessLayerDetailEntity>();

            if (fila.moveToFirst()) {
                do {
                    BusinessLayerDetailEntity businessLayerDetailEntity=new BusinessLayerDetailEntity();
                    businessLayerDetailEntity.setLineId(fila.getString(fila.getColumnIndex("LineId")));
                    businessLayerDetailEntity.setU_VIS_Type(fila.getString(fila.getColumnIndex("U_VIS_Type")));
                    businessLayerDetailEntity.setU_VIS_Object(fila.getString(fila.getColumnIndex("U_VIS_Object")));
                    businessLayerDetailEntity.setU_VIS_Action(fila.getString(fila.getColumnIndex("U_VIS_Action")));
                    listBusinessLayerDetailEntity.add(businessLayerDetailEntity);
                } while (fila.moveToNext());
            }
            bd.close();
            return listBusinessLayerDetailEntity;
        }catch (Exception ex){
            Log.e("REOS","LeadSQLite-getGeolocationBlock-ex"+ex.getMessage());
            bd.close();
            return null;
        }
    }
}

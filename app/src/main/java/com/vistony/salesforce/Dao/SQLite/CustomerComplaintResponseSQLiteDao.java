package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintQuestionsEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintResponseEntity;
import com.vistony.salesforce.Entity.SQLite.ConfiguracionSQLEntity;

import java.util.ArrayList;
import java.util.List;

public class CustomerComplaintResponseSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<ConfiguracionSQLEntity> listaConfiguracionSQLiteEntity;
    Context context;
    public CustomerComplaintResponseSQLiteDao(Context context)
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


    public int addCustomerComplaintResponse (
            List<CustomerComplaintResponseEntity> lead,
            String code,
            String questioncode
    )
    {
        abrir();
        for (int i = 0; i < lead.size(); i++)
        {
            Log.e("REOS", "CustomerComplaintResponseSQLiteDao.addCustomerComplaintResponse.I : " + i);
            ContentValues registro = new ContentValues();
            registro.put("questioncode",questioncode);
            registro.put("responsecode",lead.get(i).getResponse_id());
            registro.put("responsename",lead.get(i).getResponse());
            registro.put("typeresponse",lead.get(i).getReponseAttachType());
            registro.put("fileattach",lead.get(i).getReponseRouteFile());
            registro.put("entrycode",code);
            registro.put("responsechoisse",lead.get(i).getResponseChoisse());
            bd.insert("formresponse",null,registro);
        }
        bd.close();
        return 1;
    }

    public int ClearTableResponse ()
    {
        abrir();
        bd.execSQL("delete from formresponse");
        bd.close();
        return 1;
    }

    public ArrayList<CustomerComplaintResponseEntity> getResponseJSON (
            String code,
            String questioncode
    )
    {
        ArrayList<CustomerComplaintResponseEntity> listCustomerComplaintResponseEntity=new ArrayList<>();
        CustomerComplaintResponseEntity customerComplaintResponseEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from formresponse " +
                            " where entrycode=  '"+code+"' AND questioncode='"+questioncode+"' "
                    ,null);
            while (fila.moveToNext())
            {
                customerComplaintResponseEntity= new CustomerComplaintResponseEntity();
                customerComplaintResponseEntity.setResponse_id (fila.getString(fila.getColumnIndex("responsecode")));
                customerComplaintResponseEntity.setResponse (fila.getString(fila.getColumnIndex("responsename")));
                customerComplaintResponseEntity.setResponse (fila.getString(fila.getColumnIndex("responsename")));
                listCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
            }
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listCustomerComplaintResponseEntity;
    }
}


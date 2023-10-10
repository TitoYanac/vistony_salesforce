package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintQuestionsEntity;
import com.vistony.salesforce.Entity.SQLite.ConfiguracionSQLEntity;

import java.util.ArrayList;
import java.util.List;

public class CustomerComplaintQuestionSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<ConfiguracionSQLEntity> listaConfiguracionSQLiteEntity;
    Context context;
    public CustomerComplaintQuestionSQLiteDao(Context context)
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


    public int addCustomerComplaintQuestion (
            List<CustomerComplaintQuestionsEntity> lead,
            String code,
            String sectioncode
    )
    {
        abrir();
        CustomerComplaintResponseSQLiteDao customerComplaintResponseSQLiteDao=new CustomerComplaintResponseSQLiteDao(context);
        for (int i = 0; i < lead.size(); i++)
        {
            Log.e("REOS", "CustomerComplaintQuestionSQLiteDao.addCustomerComplaintQuestion.I : " + i);
            ContentValues registro = new ContentValues();
            registro.put("sectioncode",sectioncode);
            registro.put("questioncode",lead.get(i).getQuestion_id());
            registro.put("questionname",lead.get(i).getQuestion());
            registro.put("entrycode",code);
            registro.put("codedepence","");
            registro.put("questionsanswered",lead.get(i).getQuestionAnswered());
            if(lead.get(i).getListCustomerComplaintResponse()!=null)
            {
                customerComplaintResponseSQLiteDao.addCustomerComplaintResponse(
                        lead.get(i).getListCustomerComplaintResponse(),
                        code,
                        lead.get(i).getQuestion_id()
                );
            }

            bd.insert("formquestion",null,registro);
        }
        bd.close();
        return 1;
    }

    public int ClearTableFormQuestion ()
    {
        abrir();
        bd.execSQL("delete from formquestion");
        bd.close();
        return 1;
    }

    public ArrayList<CustomerComplaintQuestionsEntity> getQuestionJSON (
            String code,
            String sectioncode
    )
    {
        ArrayList<CustomerComplaintQuestionsEntity> listCustomerComplaintQuestionsEntity=new ArrayList<>();
        CustomerComplaintQuestionsEntity customerComplaintQuestionsEntity;
        CustomerComplaintResponseSQLiteDao customerComplaintResponseSQLiteDao=new CustomerComplaintResponseSQLiteDao(context);
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from formquestion " +
                            " where entrycode=  '"+code+"' AND sectioncode='"+sectioncode+"' "
                    ,null);
            while (fila.moveToNext())
            {
                customerComplaintQuestionsEntity= new CustomerComplaintQuestionsEntity();
                customerComplaintQuestionsEntity.setQuestion (fila.getString(fila.getColumnIndex("questionname")));
                customerComplaintQuestionsEntity.setQuestion_id (fila.getString(fila.getColumnIndex("questioncode")));
                customerComplaintQuestionsEntity.setListCustomerComplaintResponse (
                        customerComplaintResponseSQLiteDao.getResponseJSON(
                                code,
                                fila.getString(fila.getColumnIndex("questioncode"))
                ));
                listCustomerComplaintQuestionsEntity.add(customerComplaintQuestionsEntity);
            }
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listCustomerComplaintQuestionsEntity;
    }
}

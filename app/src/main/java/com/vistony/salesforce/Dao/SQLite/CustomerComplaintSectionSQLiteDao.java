package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintFormsEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintSectionEntity;
import com.vistony.salesforce.Entity.SQLite.ConfiguracionSQLEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;

import java.util.ArrayList;
import java.util.List;

public class CustomerComplaintSectionSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<ConfiguracionSQLEntity> listaConfiguracionSQLiteEntity;
    Context context;
    public CustomerComplaintSectionSQLiteDao(Context context)
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


    public int addCustomerComplaintSection (
            List<CustomerComplaintSectionEntity> lead,
            String code,
            String forms_id
    )
    {
        abrir();
        CustomerComplaintQuestionSQLiteDao customerComplaintQuestionSQLiteDao=new CustomerComplaintQuestionSQLiteDao(context);
        for (int i = 0; i < lead.size(); i++)
        {
            Log.e("REOS", "CustomerComplaintSectionSQLiteDao.addCustomerComplaintSection.I : " + i);
            ContentValues registro = new ContentValues();
            //registro.put("sectioncode",lead.get(i).getSection_id());
            registro.put("sectioncode",String.valueOf(i) );
            registro.put("sectionname",lead.get(i).getSection());
            registro.put("entrycode",code);
            registro.put("formcode",forms_id);

            if(lead.get(i).getListCustomercomplaintQuestions()!=null)
            {
                customerComplaintQuestionSQLiteDao.addCustomerComplaintQuestion(
                        lead.get(i).getListCustomercomplaintQuestions(),
                        code,
                        String.valueOf(i)
                );
            }

            bd.insert("formsection",null,registro);
        }
        bd.close();
        return 1;
    }

    public int ClearTableSection ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from formsection");
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<CustomerComplaintSectionEntity> getSectionsJSON (
            String code
    )
    {
        ArrayList<CustomerComplaintSectionEntity> listCustomerComplaintSectionEntity=new ArrayList<>();
        CustomerComplaintSectionEntity customerComplaintSectionEntity;
        CustomerComplaintQuestionSQLiteDao customerComplaintQuestionSQLiteDao=new CustomerComplaintQuestionSQLiteDao(context);
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from formsection " +
                            " where entrycode=  '"+code+"'"
                    ,null);
            while (fila.moveToNext())
            {
                customerComplaintSectionEntity= new CustomerComplaintSectionEntity();
                customerComplaintSectionEntity.setSection (fila.getString(fila.getColumnIndex("sectionname")));
                customerComplaintSectionEntity.setSection_id (fila.getString(fila.getColumnIndex("sectioncode")));
                customerComplaintSectionEntity.setListCustomercomplaintQuestions(
                        customerComplaintQuestionSQLiteDao.getQuestionJSON(
                                code,
                                fila.getString(fila.getColumnIndex("sectioncode"))
                        )
                );
                listCustomerComplaintSectionEntity.add(customerComplaintSectionEntity);
            }
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listCustomerComplaintSectionEntity;
    }
}

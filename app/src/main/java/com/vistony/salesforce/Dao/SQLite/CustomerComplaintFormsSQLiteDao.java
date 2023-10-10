package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintFormsEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.CustomerComplaintFormsEntityResponse;

import java.util.ArrayList;
import java.util.List;

public class CustomerComplaintFormsSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    Context context;
    public CustomerComplaintFormsSQLiteDao(Context context)
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


    public int addCustomerComplaintForms (
            List<CustomerComplaintFormsEntity> lead
    )
    {
        try {
            abrir();
            FormulasController formulasController = new FormulasController(context);
            CustomerComplaintSectionSQLiteDao customerComplaintSectionSQLiteDao = new CustomerComplaintSectionSQLiteDao(context);
            String code = formulasController.ObtenerFechaHoraCadena();
            for (int i = 0; i < lead.size(); i++) {
                Log.e("REOS", "CustomerComplaintSectionSQLiteDao.addCustomerComplaintSection.I : " + i);
                ContentValues registro = new ContentValues();
                registro.put("formcode", lead.get(i).getForms_id());
                registro.put("formname", lead.get(i).getForms());
                registro.put("entrycode", code);
                registro.put("date", lead.get(i).getForms_date());
                registro.put("salesrepcode", lead.get(i).getSalesrepcode());
                registro.put("userid", lead.get(i).getUser_id());
                registro.put("time", lead.get(i).getTime());
                registro.put("chk_send", "Y");
                registro.put("chk_receive", "N");
                registro.put("msg_server", "");
                if (lead.get(i).getListCustomerComplaintSection() != null) {
                    customerComplaintSectionSQLiteDao.addCustomerComplaintSection(
                            lead.get(i).getListCustomerComplaintSection(),
                            code,
                            lead.get(i).getForms_id()
                    );
                }

                bd.insert("form", null, registro);
            }
            bd.close();
        }catch (Exception e)
        {
            Log.e("REOS", "CustomerComplaintSectionSQLiteDao.addCustomerComplaintSection.error : " + e.toString());
        }
        return 1;
    }

    public int LimpiarTableForms ()
    {
        abrir();
        bd.execSQL("delete from form");
        bd.close();
        return 1;
    }



    public ArrayList<CustomerComplaintFormsEntity> getFormsJSON ()
    {
        ArrayList<CustomerComplaintFormsEntity> listCustomerComplaintFormsEntity=new ArrayList<>();
        ArrayList<CustomerComplaintFormsEntityResponse> listCustomerComplaintFormsEntityResponse=new ArrayList<>();

        CustomerComplaintFormsEntity customerComplaintFormsEntity;
        CustomerComplaintSectionSQLiteDao customerComplaintSectionSQLiteDao=new CustomerComplaintSectionSQLiteDao(context);
        //CustomerComplaintFormsEntityResponse customerComplaintFormsEntityResponse=new CustomerComplaintFormsEntityResponse(customerComplaintFormsEntity)
        //customerComplaintFormsEntityResponse.set
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from form " +
                            " where chk_send= 'Y' and chk_receive= 'N' "
                    ,null);
            while (fila.moveToNext())
            {
                customerComplaintFormsEntity= new CustomerComplaintFormsEntity();
                customerComplaintFormsEntity.setForms_id (fila.getString(fila.getColumnIndex("formcode")));
                customerComplaintFormsEntity.setForms (fila.getString(fila.getColumnIndex("formname")));
                customerComplaintFormsEntity.setListCustomerComplaintSection(
                        customerComplaintSectionSQLiteDao.getSectionsJSON(
                                fila.getString(fila.getColumnIndex("entrycode"))
                        )
                );
                listCustomerComplaintFormsEntity.add(customerComplaintFormsEntity);

            }
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listCustomerComplaintFormsEntity;
    }
}

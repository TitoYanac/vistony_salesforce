package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.DataBaseManager;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Adapters.DireccionCliente;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BancoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.DocumentDetailEntity;
import com.vistony.salesforce.Entity.SQLite.DireccionClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentDetailSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

public class DocumentDetailSQLite {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    Context appContext;
    private void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos desde " + this.getClass().getName());
        bd = sqliteController.getWritableDatabase();
    }

    private void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion desde " + this.getClass().getName());
        sqliteController.close();
    }
    public DocumentDetailSQLite(Context context){

        DataBaseManager.initializeInstance(new SqliteController(context));
        sqliteController = new SqliteController(context);
        bd = sqliteController.getWritableDatabase();
        appContext = context;
    }

    public int addDocumentsDetail (List<DocumentDetailEntity> documentdetail)
    {
        abrir();

        for (int i = 0; i < documentdetail.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id",SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id",SesionEntity.usuario_id);
            registro.put("DocEntry",documentdetail.get(i).getDocEntry());
            registro.put("LineNum",documentdetail.get(i).getLineNum());
            registro.put("ItemCode",documentdetail.get(i).getItemCode());
            registro.put("Dscription",documentdetail.get(i).getDscription());
            registro.put("Quantity",documentdetail.get(i).getQuantity());
            registro.put("LineTotal",documentdetail.get(i).getLineTotal());
            registro.put("WhsCode",documentdetail.get(i).getWhsCode());
            registro.put("LineStatus",documentdetail.get(i).getLineStatus());
            registro.put("TaxCode",documentdetail.get(i).getTaxCode());
            registro.put("DiscPrcnt",documentdetail.get(i).getDiscPrcnt());
            registro.put("TaxOnly",documentdetail.get(i).getTaxOnly());
            bd.insert("documentdetail",null,registro);
        }

        bd.close();
        return 1;
    }


    public ArrayList<DocumentDetailSQLiteEntity> getListDocumentDetail(String DocEntry){
        ArrayList<DocumentDetailSQLiteEntity> ListDocumentDetail = new ArrayList<>();

        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try {

            Cursor fila = sqlite.rawQuery("SELECT * FROM documentdetail WHERE DocEntry=?", new String[]{DocEntry});

            if (fila.moveToFirst()) {
                if (fila.moveToFirst()) {
                    do {
                        DocumentDetailSQLiteEntity documentDetailSQLiteEntity = new DocumentDetailSQLiteEntity();
                        documentDetailSQLiteEntity.setCompania_id (fila.getString(fila.getColumnIndex("compania_id")));
                        documentDetailSQLiteEntity.setFuerzatrabajo_id (fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
                        documentDetailSQLiteEntity.setUsuario_id (fila.getString(fila.getColumnIndex("usuario_id")));
                        documentDetailSQLiteEntity.setDocEntry (fila.getString(fila.getColumnIndex("DocEntry")));
                        documentDetailSQLiteEntity.setLineNum (fila.getString(fila.getColumnIndex("LineNum")));
                        documentDetailSQLiteEntity.setItemCode (fila.getString(fila.getColumnIndex("ItemCode")));
                        documentDetailSQLiteEntity.setDscription (fila.getString(fila.getColumnIndex("Dscription")));
                        documentDetailSQLiteEntity.setQuantity (fila.getString(fila.getColumnIndex("Quantity")));
                        documentDetailSQLiteEntity.setLineTotal (fila.getString(fila.getColumnIndex("LineTotal")));
                        documentDetailSQLiteEntity.setWhsCode (fila.getString(fila.getColumnIndex("WhsCode")));
                        documentDetailSQLiteEntity.setLineStatus (fila.getString(fila.getColumnIndex("LineStatus")));
                        documentDetailSQLiteEntity.setTaxCode (fila.getString(fila.getColumnIndex("TaxCode")));
                        documentDetailSQLiteEntity.setDiscPrcnt (fila.getString(fila.getColumnIndex("DiscPrcnt")));
                        documentDetailSQLiteEntity.setTaxOnly (fila.getString(fila.getColumnIndex("TaxOnly")));
                        ListDocumentDetail.add(documentDetailSQLiteEntity);
                    } while (fila.moveToNext());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return ListDocumentDetail;
    }

    public int ClearTableDocumentDetail(){
        int status=0;
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try{
            sqlite.execSQL("DELETE FROM documentdetail");
            status=1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return status;
    }

    public int getCountDocumentDetail ()
    {
        int resultado=0;

        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
        try {
            Cursor fila = sqlite.rawQuery(
                    "Select count(compania_id) from documentdetail",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return resultado;
    }

}

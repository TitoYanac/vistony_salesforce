package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.DataBaseManager;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;

import java.util.ArrayList;

public class DocumentoSQLite {

    private ArrayList<DocumentoDeudaSQLiteEntity> listaDDeudaentity;

    public DocumentoSQLite(Context context){
        DataBaseManager.initializeInstance(new SqliteController(context));
    }

    public int InsertaDocumentoDeuda (
            String documento_id,
            String domembarque_id,
            String compania_id,
            String cliente_id,
            String fuerzatrabajo_id,
            String fechaemision,
            String fechavencimiento,
            String nrofactura,
            String moneda,
            String importefactura,
            String saldo,
            String saldo_sin_procesar,
            String doc_entry)
    {

        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        ContentValues registro = new ContentValues();
        registro.put("documento_id",documento_id);
        registro.put("domembarque_id",domembarque_id);
        registro.put("compania_id",compania_id);
        registro.put("cliente_id",cliente_id);
        registro.put("fuerzatrabajo_id",fuerzatrabajo_id);
        registro.put("fechaemision",fechaemision);
        registro.put("fechavencimiento",fechavencimiento);
        registro.put("nrofactura",nrofactura);
        registro.put("moneda",moneda);
        registro.put("importefactura",importefactura);
        registro.put("saldo",saldo);
        registro.put("saldo_sin_procesar",saldo_sin_procesar);
        registro.put("doc_entry",doc_entry);


        sqlite.insert("documentodeuda",null,registro);

        DataBaseManager.getInstance().closeDatabase();

        return 1;
    }

    public ArrayList<DocumentoDeudaSQLiteEntity> ObtenerDDeudaporcliente (String compania_id, String fuerzatrabajo_id, String cliente_id) {

        listaDDeudaentity = new ArrayList<DocumentoDeudaSQLiteEntity>();
        DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            Cursor fila = sqlite.rawQuery("Select DISTINCT * from documentodeuda where compania_id=? and fuerzatrabajo_id=? and cliente_id=? order by fechaemision ASC", new String[]{compania_id,fuerzatrabajo_id,cliente_id});

            while (fila.moveToNext()) {
                documentoDeudaSQLiteEntity = new DocumentoDeudaSQLiteEntity();
                documentoDeudaSQLiteEntity.setDocumento_id(fila.getString(fila.getColumnIndex("documento_id")));
                documentoDeudaSQLiteEntity.setDomembarque_id(fila.getString(fila.getColumnIndex("domembarque_id")));
                documentoDeudaSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
                documentoDeudaSQLiteEntity.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
                documentoDeudaSQLiteEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
                documentoDeudaSQLiteEntity.setFechaemision(fila.getString(fila.getColumnIndex("fechaemision")));
                documentoDeudaSQLiteEntity.setFechavencimiento(fila.getString(fila.getColumnIndex("fechavencimiento")));
                documentoDeudaSQLiteEntity.setNrofactura(fila.getString(fila.getColumnIndex("nrofactura")));
                documentoDeudaSQLiteEntity.setMoneda(fila.getString(fila.getColumnIndex("moneda")));
                documentoDeudaSQLiteEntity.setImportefactura(fila.getString(fila.getColumnIndex("importefactura")));
                documentoDeudaSQLiteEntity.setSaldo(fila.getString(fila.getColumnIndex("saldo")));
                documentoDeudaSQLiteEntity.setSaldo_sin_procesar(fila.getString(fila.getColumnIndex("saldo_sin_procesar")));
                documentoDeudaSQLiteEntity.setDocumento_entry(fila.getString(fila.getColumnIndex("doc_entry")));
                listaDDeudaentity.add(documentoDeudaSQLiteEntity);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }
        return listaDDeudaentity;

    }

    public int ActualizaNuevoSaldo (String compania_id, String documento_id, String nuevo_saldo)
    {
        int resultado=0;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

            ContentValues registro = new ContentValues();
            registro.put("saldo",nuevo_saldo);

            resultado = sqlite.update("documentodeuda",registro,"documento_id='"+documento_id+"'"+" and compania_id='"+compania_id+"'" ,null);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return resultado;
    }

/*
    public int ObtenerCantidadDocumentosDeuda ()
    {
        int resultado=0;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            Cursor fila = sqlite.rawQuery(
                    "Select count(documento_id) from documentodeuda",null);

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
*/
}

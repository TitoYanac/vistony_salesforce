package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;

import java.util.ArrayList;

public class DocumentoDeudaSQLiteDao {

    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<DocumentoDeudaSQLiteEntity> listaDDeudaentity;

    public DocumentoDeudaSQLiteDao(Context context)
    {
        sqLiteController = new SQLiteController(context);
    }
    public void abrir(){
        Log.i("SQLite", "Se abre conexion desde " + this.getClass().getName() );
        bd = sqLiteController.getWritableDatabase();
    }

    /** Cierra conexion a la base ade datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + sqLiteController.getDatabaseName() );
        sqLiteController.close();
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
            String saldo_sin_procesar)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();

        abrir();
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


        bd.insert("documentodeuda",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<DocumentoDeudaSQLiteEntity> ObtenerDDeudaporcliente (String compania_id, String fuerzatrabajo_id, String cliente_id)
    {
        try {
            listaDDeudaentity = new ArrayList<DocumentoDeudaSQLiteEntity>();
            DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;
            abrir();
            Cursor fila = bd.rawQuery(
                    //"Select * from documentodeuda where compania_id='" + compania_id + "' and fuerzatrabajo_id='" + fuerzatrabajo_id + "' and cliente_id='" + cliente_id + "'", null);
            "Select * from documentodeuda where compania_id='" + compania_id + "' and fuerzatrabajo_id='" + fuerzatrabajo_id + "' and cliente_id='" + cliente_id + "' order by fechaemision ASC", null);

            while (fila.moveToNext()) {
                documentoDeudaSQLiteEntity = new DocumentoDeudaSQLiteEntity();
                documentoDeudaSQLiteEntity.setDocumento_id(fila.getString(0));
                documentoDeudaSQLiteEntity.setDomembarque_id(fila.getString(1));
                documentoDeudaSQLiteEntity.setCompania_id(fila.getString(2));
                documentoDeudaSQLiteEntity.setCliente_id(fila.getString(3));
                documentoDeudaSQLiteEntity.setFuerzatrabajo_id(fila.getString(4));
                documentoDeudaSQLiteEntity.setFechaemision(fila.getString(5));
                documentoDeudaSQLiteEntity.setFechavencimiento(fila.getString(6));
                documentoDeudaSQLiteEntity.setNrofactura(fila.getString(7));
                documentoDeudaSQLiteEntity.setMoneda(fila.getString(8));
                documentoDeudaSQLiteEntity.setImportefactura(fila.getString(9));
                documentoDeudaSQLiteEntity.setSaldo(fila.getString(10));
                documentoDeudaSQLiteEntity.setSaldo_sin_procesar(fila.getString(11));
                listaDDeudaentity.add(documentoDeudaSQLiteEntity);
            }

            bd.close();
        }catch (Exception e)
        {
            bd.close();
            System.out.println(e.getMessage());
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaDDeudaentity;

    }

    public int ActualizaNuevoSaldo (String compania_id, String documento_id, String nuevo_saldo)
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("saldo",nuevo_saldo);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("documentodeuda",registro,"documento_id='"+documento_id+"'"+" and compania_id='"+compania_id+"'" ,null);
            bd.close();
        }catch (Exception e)
        {
            bd.close();
            System.out.println(e.getMessage());

        }
        return resultado;
    }

    public int LimpiarTablaDocumentoDeuda ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
       // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from documentodeuda ");
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int ObtenerCantidadDocumentosDeuda ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(documento_id) from documentodeuda",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));

            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return resultado;
    }

}

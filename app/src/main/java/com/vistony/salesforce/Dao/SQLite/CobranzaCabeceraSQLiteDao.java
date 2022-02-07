package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.JSON.CollectionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.DepositEntity;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

public class CobranzaCabeceraSQLiteDao {

    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<CobranzaCabeceraSQLiteEntity> listaCobranzaCabeceraSQLiteEntity;

    public CobranzaCabeceraSQLiteDao(Context context)
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

    public int InsertaCobranzaCabecera (String cobranza_id,String usuario_id,String cobrador_id,String banco_id,String compania_id
            ,String totalmontocobrado, String tipoingreso,String chkbancarizado,String fechadiferido,String fechadeposito,String depositodirecto,String pagopos)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        int resultado=0;
        try {

            abrir();
            ContentValues registro = new ContentValues();
            registro.put("cobranza_id",cobranza_id);
            registro.put("usuario_id",usuario_id);
            registro.put("banco_id",banco_id);
            registro.put("compania_id",compania_id);
            registro.put("totalmontocobrado",totalmontocobrado);
            registro.put("fuerzatrabajo_id",cobrador_id);
            registro.put("chkdepositado","Y");
            registro.put("chkanulado","N");
            registro.put("tipoingreso",tipoingreso);
            registro.put("chkbancarizado",chkbancarizado);
            registro.put("fechadiferido",fechadiferido);
            registro.put("chkwsrecibido","N");
            registro.put("fechadeposito",fechadeposito);
            registro.put("comentarioanulado","");
            registro.put("chkwsanulado","N");
            registro.put("pagodirecto",depositodirecto);
            registro.put("pagopos",pagopos);
            registro.put("sap_code","");
            registro.put("mensajeWS","");

            bd.insert("cobranzacabecera",null,registro);
            bd.close();
            resultado=1;

        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return resultado;
    }

    public int AnularCobranzaCabecera (String cobranza_id,String compania_id,String fuerzatrabajo_id,String comentarioanulado,String chkwsanulado)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        int resultado=0;
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkanulado","Y");
            registro.put("chkwsanulado","N");
            registro.put("comentarioanulado",comentarioanulado);
            //registro.put("precio",pre);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("cobranzacabecera",registro,"cobranza_id='"+cobranza_id+"'"+" and compania_id='"+compania_id+"'"+" and fuerzatrabajo_id='"+fuerzatrabajo_id+"'"  ,null);

            /*Cursor fila = bd.rawQuery(
                    "update cobranzadetalle set cobranza_id = '"+cobranza_id+ "'" +
                            //+ ",chkdepositado = '"+chkdepositado+"'"+
                            " where recibo= '"+recibo+"'"
                            //+ " and compania_id= '"+compania_id+"'"
                    ,null);
            */

            //resultado=1;
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return resultado;
    }


    public ArrayList<CobranzaCabeceraSQLiteEntity> ObtenerCobranzaCabeceraporID (String id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaCabeceraSQLiteEntity = new ArrayList<CobranzaCabeceraSQLiteEntity>();
        CobranzaCabeceraSQLiteEntity cobcabeceraentity;

        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzacabecera " +
                            " where cobranza_id= '"+id+"'"  //+" and fuerzatrabajo_id='"+fuerzatrabajo_id+"'"  //and chkanulado='0' "// +
                    //"and chkqrvalidado='1'"
                    ,null);

            while (fila.moveToNext())
            {

                cobcabeceraentity= new CobranzaCabeceraSQLiteEntity();
                cobcabeceraentity.setCobranza_id(fila.getString(0));
                cobcabeceraentity.setUsuario_id(fila.getString(1));
                cobcabeceraentity.setBanco_id(fila.getString(2));
                cobcabeceraentity.setCompania_id(fila.getString(3));
                cobcabeceraentity.setTotalmontocobrado(fila.getString(4));
                cobcabeceraentity.setChkdepositado(fila.getString(5));
                cobcabeceraentity.setChkanulado(fila.getString(6));
                cobcabeceraentity.setFuerzatrabajo_id(fila.getString(7));
                cobcabeceraentity.setTipoingreso(fila.getString(8));
                cobcabeceraentity.setChkbancarizado(fila.getString(9));
                cobcabeceraentity.setFechadiferido(fila.getString(10));
                cobcabeceraentity.setPagodirecto(fila.getString(17));
                cobcabeceraentity.setPagopos(fila.getString(18));
                listaCobranzaCabeceraSQLiteEntity.add(cobcabeceraentity);
            }

           // bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaCobranzaCabeceraSQLiteEntity;
    }

    public int ActualizarCobranzaCabeceraWS (
            String cobranza_id,
            String compania_id,
            String fuerzatrabajo_id,
            String chkwsrecibido,
            String Code,
            String Message)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        int resultado=0;
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkwsrecibido",chkwsrecibido);
            registro.put("sap_code",Code);
            registro.put("mensajeWS",Message);

            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("cobranzacabecera",registro,"cobranza_id='"+cobranza_id+"'"+" and compania_id='"+compania_id+"'"+" and fuerzatrabajo_id='"+fuerzatrabajo_id+"'"  ,null);

            bd.close();
        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS","CobranzaCabeceraSQLiteDao-ActualizarCobranzaCabeceraWS-error: "+e.toString());
            resultado=0;
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return resultado;
    }


    public ArrayList<DepositEntity> ObtenerCobranzaCabeceraPendientesWS (String compania_id, String usuario_id)
    {

        ArrayList<DepositEntity> depositos = new ArrayList<DepositEntity>();

        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzacabecera where (chkwsrecibido= 'N' or chkwsrecibido= '0')  and compania_id=? and usuario_id=? ",new String[]{compania_id,usuario_id});

            while (fila.moveToNext())
            {
                DepositEntity deposito= new DepositEntity();
                deposito.setDeposit(fila.getString(0));
                deposito.setUserID(fila.getString(1));
                deposito.setBankID(fila.getString(2));
                deposito.setCompanyCode(fila.getString(3));
                deposito.setAmountDeposit(fila.getString( 4));
                //deposito.setChkdepositado(fila.getString(5));
                //deposito.setChkanulado(fila.getString(6));
                deposito.setSlpCode(fila.getString(7));
                deposito.setIncomeType((fila.getString(8).equals("Deposito"))?"DE":"CH");
                deposito.setBanking((fila.getString(9)));
                deposito.setDeferredDate(fila.getString(10));
                //deposito.setChkwsrecibido(fila.getString(11));
                deposito.setStatus("P"); //always pendiente
                deposito.setDate(fila.getString(12));
                deposito.setCancelReason(fila.getString(13));
                deposito.setDirectDeposit((fila.getString(17)));
                deposito.setPOSPay((fila.getString(18)));
                deposito.setComments("");
                depositos.add(deposito);
            }

            bd.close();
        }catch (Exception e){
            e.printStackTrace();
            Log.e("REOS","CobranzaCabeceraSQLiteDao-ObtenerCobranzaCabeceraPendientesWS-error: "+e.toString());
        }
        return depositos;
    }

    public int ActualizarAnuladoCobranzaCabeceraWS (String cobranza_id,String compania_id,String usuario_id,String chkwsanulado)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        int resultado=0;
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkwsanulado",chkwsanulado);
            //registro.put("precio",pre);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("cobranzacabecera",registro,"cobranza_id='"+cobranza_id+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'"  ,null);

            /*Cursor fila = bd.rawQuery(
                    "update cobranzadetalle set cobranza_id = '"+cobranza_id+ "'" +
                            //+ ",chkdepositado = '"+chkdepositado+"'"+
                            " where recibo= '"+recibo+"'"
                            //+ " and compania_id= '"+compania_id+"'"
                    ,null);
            */

            //resultado=1;
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return resultado;
    }

    public ArrayList<CobranzaCabeceraSQLiteEntity> ObtenerCobranzaCabeceraPendientesAnulacionWS (String compania_id,String usuario_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaCabeceraSQLiteEntity = new ArrayList<CobranzaCabeceraSQLiteEntity>();
        CobranzaCabeceraSQLiteEntity cobcabeceraentity;

        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzacabecera where chkwsanulado= 'N' " //+
                            +" and compania_id='"+compania_id+"' and usuario_id='"+usuario_id+"'"// +
                            +"and chkanulado='Y' "
                    ,null);

            while (fila.moveToNext())
            {

                cobcabeceraentity= new CobranzaCabeceraSQLiteEntity();
                cobcabeceraentity.setCobranza_id(fila.getString(0));
                cobcabeceraentity.setUsuario_id(fila.getString(1));
                cobcabeceraentity.setBanco_id(fila.getString(2));
                cobcabeceraentity.setCompania_id(fila.getString(3));
                cobcabeceraentity.setTotalmontocobrado(fila.getString(4));
                cobcabeceraentity.setChkdepositado(fila.getString(5));
                cobcabeceraentity.setChkanulado(fila.getString(6));
                cobcabeceraentity.setFuerzatrabajo_id(fila.getString(7));
                cobcabeceraentity.setTipoingreso(fila.getString(8));
                cobcabeceraentity.setChkbancarizado(fila.getString(9));
                cobcabeceraentity.setFechadiferido(fila.getString(10));
                cobcabeceraentity.setChkwsrecibido(fila.getString(11));
                cobcabeceraentity.setFechadeposito(fila.getString(12));
                cobcabeceraentity.setComentarioanulado(fila.getString(13));
                cobcabeceraentity.setPagodirecto(fila.getString(17));
                cobcabeceraentity.setPagopos(fila.getString(18));
                listaCobranzaCabeceraSQLiteEntity.add(cobcabeceraentity);
            }

            // bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaCobranzaCabeceraSQLiteEntity;
    }


    public ArrayList<CobranzaCabeceraSQLiteEntity> ObtenerHistoricoDepostito (Context context,String compania_id,String usuario_id,String fechaini,String fechafin)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase()
        listaCobranzaCabeceraSQLiteEntity = new ArrayList<CobranzaCabeceraSQLiteEntity>();
        CobranzaCabeceraSQLiteEntity cobcabeceraentity;

        try {
            abrir();
            String query="Select * from cobranzacabecera where fechadeposito>='"+fechaini+"'"
                    +" and compania_id='"+compania_id+"' and usuario_id='"+usuario_id+"'"// +
                    +"and fechadeposito<='"+fechafin+"'";
                    Log.e("jpcm",query);
            Cursor fila = bd.rawQuery(query ,null);

            while (fila.moveToNext())
            {
                String nombrebanco="";
                BancoSQLite bancoSQLite=new BancoSQLite(context);
                ArrayList<BancoSQLiteEntity> bancoSQLiteEntityArrayList=new ArrayList<>();


                cobcabeceraentity= new CobranzaCabeceraSQLiteEntity();
                cobcabeceraentity.setCobranza_id(fila.getString(0));
                cobcabeceraentity.setUsuario_id(fila.getString(1));
                cobcabeceraentity.setBanco_id(fila.getString(2));
                cobcabeceraentity.setCompania_id(fila.getString(3));
                cobcabeceraentity.setTotalmontocobrado(fila.getString(4));
                cobcabeceraentity.setChkdepositado(fila.getString(5));
                cobcabeceraentity.setChkanulado(fila.getString(6));
                cobcabeceraentity.setFuerzatrabajo_id(fila.getString(7));
                cobcabeceraentity.setTipoingreso(fila.getString(8));
                cobcabeceraentity.setChkbancarizado(fila.getString(9));
                cobcabeceraentity.setFechadiferido(fila.getString(10));
                cobcabeceraentity.setChkwsrecibido(fila.getString(11));
                cobcabeceraentity.setFechadeposito(fila.getString(12));
                cobcabeceraentity.setComentarioanulado(fila.getString(13));
                cobcabeceraentity.setPagopos(fila.getString(18));
                bancoSQLiteEntityArrayList=bancoSQLite.ObtenerBancoporID(SesionEntity.compania_id,cobcabeceraentity.getBanco_id());
                for(int i=0;i<bancoSQLiteEntityArrayList.size();i++)
                {
                    cobcabeceraentity.setBankname(bancoSQLiteEntityArrayList.get(i).getNombrebanco());
                }


                ;
                listaCobranzaCabeceraSQLiteEntity.add(cobcabeceraentity);
            }

            // bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaCobranzaCabeceraSQLiteEntity;
    }

    public int UpdateCobranzaCabecera (String cobranza_id,String cobranza_id_update,String compania_id,String usuario_id)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        int resultado=0;
        try {

            ContentValues registro = new ContentValues();
            registro.put("cobranza_id",cobranza_id_update);
            //registro.put("precio",pre);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("cobranzacabecera",registro,"cobranza_id='"+cobranza_id+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'"  ,null);

            /*Cursor fila = bd.rawQuery(
                    "update cobranzadetalle set cobranza_id = '"+cobranza_id+ "'" +
                            //+ ",chkdepositado = '"+chkdepositado+"'"+
                            " where recibo= '"+recibo+"'"
                            //+ " and compania_id= '"+compania_id+"'"
                    ,null);
            */

            //resultado=1;
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return resultado;
    }

    public ArrayList<CobranzaCabeceraSQLiteEntity> ObtenerCobranzaCabeceraPendientesEnvioTotalWS (String compania_id, String usuario_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaCabeceraSQLiteEntity = new ArrayList<CobranzaCabeceraSQLiteEntity>();
        CobranzaCabeceraSQLiteEntity cobcabeceraentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery("SELECT * FROM cobranzacabecera WHERE chkwsrecibido=? AND compania_id=? AND usuario_id=? AND chkanulado=?",new String[]{"N",compania_id,usuario_id,"N"});

            while (fila.moveToNext()){
                cobcabeceraentity= new CobranzaCabeceraSQLiteEntity();
                cobcabeceraentity.setCobranza_id(fila.getString(0));
                cobcabeceraentity.setUsuario_id(fila.getString(1));
                cobcabeceraentity.setBanco_id(fila.getString(2));
                cobcabeceraentity.setCompania_id(fila.getString(3));
                cobcabeceraentity.setTotalmontocobrado(fila.getString(4));
                cobcabeceraentity.setChkdepositado(fila.getString(5));
                cobcabeceraentity.setChkanulado(fila.getString(6));
                cobcabeceraentity.setFuerzatrabajo_id(fila.getString(7));
                cobcabeceraentity.setTipoingreso(fila.getString(8));
                cobcabeceraentity.setChkbancarizado(fila.getString(9));
                cobcabeceraentity.setFechadiferido(fila.getString(10));
                cobcabeceraentity.setChkwsrecibido(fila.getString(11));
                cobcabeceraentity.setFechadeposito(fila.getString(12));
                cobcabeceraentity.setComentarioanulado(fila.getString(13));
                cobcabeceraentity.setPagodirecto(fila.getString(17));
                cobcabeceraentity.setPagopos(fila.getString(18));
                listaCobranzaCabeceraSQLiteEntity.add(cobcabeceraentity);
            }

            bd.close();
        }catch (Exception e){
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaCobranzaCabeceraSQLiteEntity;
    }

    public ArrayList<DepositEntity> UpdateDepositCanceled (String compania_id, String usuario_id)
    {

        ArrayList<DepositEntity> depositos = new ArrayList<DepositEntity>();

        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select sap_code,banco_id,cobranza_id,comentarioanulado from cobranzacabecera where chkwsanulado= 'N' and chkanulado='Y' and compania_id=? and usuario_id=? ",new String[]{compania_id,usuario_id});

            while (fila.moveToNext())
            {
                DepositEntity deposito= new DepositEntity();
                deposito.setCode(fila.getString(0));
                deposito.setBankID(fila.getString(1));
                deposito.setDeposit(fila.getString(2));
                deposito.setCancelReason(fila.getString(3));
                deposito.setStatus("A"); //always Anulado
                depositos.add(deposito);
            }

            bd.close();
        }catch (Exception e){
            e.printStackTrace();
            Log.e("REOS","CobranzaCabeceraSQLiteDao-ObtenerCobranzaCabeceraPendientesWS-error: "+e.toString());
        }
        return depositos;
    }

    public int UpdateAnularCobranzaCabecera (String cobranza_id,String compania_id,String fuerzatrabajo_id)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        int resultado=0;
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkwsanulado","Y");
            //registro.put("precio",pre);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("cobranzacabecera",registro,"cobranza_id='"+cobranza_id+"'"+" and compania_id='"+compania_id+"'"+" and fuerzatrabajo_id='"+fuerzatrabajo_id+"'"  ,null);
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return resultado;
    }

}

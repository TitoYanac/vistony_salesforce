package com.vistony.salesforce.Controller.Funcionalidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteController extends SQLiteOpenHelper
{
    private SQLiteDatabase db=null;
    public  SQLiteController instance ;
    public  Context context;
    private static final String __DATABASE = "dbcobranzas";
    //private static final String __DATABASE = "dbcobranzas";
    //Version 1.1.6
    //private static final int __VERSION = 5;
    private static final int __VERSION = 7;
    //private static final int __VERSION = 2; Version 1.1.4 - 1.1.5
    //private static final int __VERSION = 1; Version 1.1.3 hacia abajo

    //private static final String DATABASE_ALTER_COBRANZACABECERA_CHKUPDATE = "ALTER TABLE cobranzacabecera ADD COLUMN chkupdate text;";
    //private static final String DATABASE_ALTER_COBRANZACABECERA_CHKWSUPDATE = "ALTER TABLE cobranzacabecera ADD COLUMN chkwsupdate text;";
    //private static final String DATABASE_ALTER_COBRANZADETALLE_CHKUPDATE = "ALTER TABLE cobranzadetalle ADD COLUMN chkupdate text;";
    //private static final String DATABASE_ALTER_COBRANZADETALLE_CHKWSUPDATE = "ALTER TABLE cobranzadetalle ADD COLUMN chkwsupdate text;";




    public SQLiteController(Context context)
    {
        super(context,__DATABASE,null,__VERSION);
        if (instance == null){
            instance = this;
            instance.context = context;
            instance.db = instance.getWritableDatabase();
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Sistema
        db.execSQL("create table parametros (parametro_id text,nombreparametro text, cantidadregistros text, fechacarga text" +
                //",hash text" +
                ")");
        db.execSQL("create table configuracion (papel text,tamanio text, totalrecibos text, secuenciarecibos text,modeloimpresora text, direccionimpresora text,tipoimpresora text,vinculaimpresora text )");

        //Cobranzas
            //Maestros
            db.execSQL("create table usuario (compania_id text,fuerzatrabajo_id text ,nombrecompania text, nombrefuerzatrabajo text,nombreusuario text,usuario_id text,recibo text, chksesion text, online text,perfil text,chkbloqueopago text,listaPrecios_id_1 text,listaPrecios_id_2 text,planta text,almacen_id text,CogsAcct text,U_VIST_CTAINGDCTO text,DocumentsOwner text,U_VIST_SUCUSU text,CentroCosto text,UnidadNegocio text,LineaProduccion text,Impuesto_ID text,Impuesto text,TipoCambio text,U_VIS_CashDscnt text)");
            db.execSQL("create table cliente (cliente_id text,domembarque_id text ,compania_id text, nombrecliente text,direccion text,zona_id text,ordenvisita text,zona text,rucdni text,moneda text,telefonofijo text,telefonomovil text,correo text,ubigeo_id text,impuesto_id text,impuesto text,tipocambio text,categoria TEXT,linea_credito TEXT,terminopago_id TEXT)");
            db.execSQL("create table banco (banco_id text , compania_id text,nombrebanco text)");
            db.execSQL("create table compania (compania_id text , nombrecompania text)");
            db.execSQL("create table documentodeuda (documento_id text ,domembarque_id text, compania_id text,cliente_id text,fuerzatrabajo_id text,fechaemision text,fechavencimiento text,nrofactura text,moneda text,importefactura text,saldo text,saldo_sin_procesar text)");
            db.execSQL("create table fuerzatrabajo (fuerzatrabajo_id text , compania_id text,nombrefuerzatrabajo text)");
            db.execSQL("create table rutavendedor (cliente_id text,domembarque_id text ,compania_id text, nombrecliente text,direccion text,zona_id text,ordenvisita text,zona text,rucdni text,moneda text,telefonofijo text,telefonomovil text,correo text,ubigeo_id text,impuesto_id text,impuesto text,tipocambio text,categoria TEXT,linea_credito TEXT,terminopago_id TEXT, chk_visita TEXT,chk_pedido TEXT,chk_cobranza TEXT,chk_ruta TEXT,fecharuta TEXT,saldomn text)");
           // db.execSQL("create table catalogo (compania_id text ,catalogo_id text,catalogo text,ruta text)");

            //Transaccional
            db.execSQL("create table cobranzacabecera (cobranza_id text , usuario_id text,banco_id text,compania_id text,totalmontocobrado text,chkdepositado text,chkanulado text,fuerzatrabajo_id text ,tipoingreso text,chkbancarizado text,fechadiferido text, chkwsrecibido text, fechadeposito text,comentarioanulado  text,chkwsanulado text,chkupdate text,chkwsupdate text,pagodirecto text,pagopos text)");
            db.execSQL("create table cobranzadetalle (id INTEGER PRIMARY KEY AUTOINCREMENT,cobranza_id text , cliente_id text,documento_id text,compania_id text,importedocumento text,saldodocumento text,nuevosaldodocumento text,saldocobrado text, fechacobranza text,recibo text,nrofactura text,chkdepositado text,chkqrvalidado text,chkanulado text ,fuerzatrabajo_id text,chkbancarizado text,motivoanulacion text,usuario_id text, chkwsrecibido text,banco_id text,chkwsdepositorecibido text,chkwsqrvalidado text,comentario text,chkwsanulado text,chkupdate text,chkwsupdate text,pagodirecto text,pagopos text)");
            db.execSQL("CREATE TABLE visita (compania_id TEXT,cliente_id TEXT,direccion_id TEXT,fecha_registro TEXT,hora_registro TEXT,zona_id TEXT,fuerzatrabajo_id TEXT,usuario_id TEXT,tipo TEXT,motivo TEXT,observacion TEXT,chkenviado TEXT,chkrecibido TEXT,latitud TEXT,longitud TEXT )");


        //Pedidos
            //Maestros
            db.execSQL("CREATE TABLE terminopago (compania_id text,terminopago_id TEXT,terminopago TEXT,contado TEXT,dias_vencimiento TEXT)");
            db.execSQL("CREATE TABLE agencia (compania_id text,agencia_id TEXT ,agencia TEXT, ubigeo_id TEXT, ruc TEXT, direccion TEXT)");
            db.execSQL("CREATE TABLE listapromocion (compania_id text,lista_promocion_id TEXT ,lista_promocion TEXT,U_VIS_CashDscnt text)");
            db.execSQL("CREATE TABLE promocioncabecera (compania_id text ,lista_promocion_id text ,promocion_id TEXT,producto_id TEXT,producto TEXT,umd TEXT,cantidad TEXT,fuerzatrabajo_id TEXT,usuario_id TEXT,total_preciobase TEXT,descuento TEXT)");
            db.execSQL("CREATE TABLE promociondetalle (compania_id text ,lista_promocion_id text ,promocion_id TEXT,promocion_detalle_id TEXT,producto_id TEXT,producto TEXT,umd TEXT,cantidad TEXT,fuerzatrabajo_id TEXT,usuario_id TEXT,preciobase TEXT,chkdescuento TEXT,descuento TEXT)");
            db.execSQL("CREATE TABLE listapreciodetalle (compania_id text ,contado TEXT,credito TEXT,producto_id TEXT,producto TEXT,umd TEXT,gal TEXT ,U_VIS_CashDscnt text)");
            db.execSQL("CREATE TABLE stock (compania_id text,producto_id TEXT,producto TEXT,umd TEXT,stock TEXT,almacen_id TEXT,comprometido TEXT,enstock TEXT,pedido TEXT)");
            db.execSQL("CREATE TABLE rutafuerzatrabajo (compania_id text,zona_id TEXT,zona TEXT,dia TEXT,frecuencia TEXT,fechainicioruta TEXT,estado TEXT)");
            db.execSQL("CREATE TABLE direccioncliente (compania_id text,cliente_id text,domembarque_id text,direccion text,zona_id TEXT,zona TEXT,fuerzatrabajo_id TEXT,nombrefuerzatrabajo TEXT)");
            //Transaccional
            db.execSQL("CREATE TABLE ordenventacabecera (compania_id text ,ordenventa_id TEXT,cliente_id TEXT ,domembarque_id TEXT,terminopago_id TEXT,agencia_id TEXT,moneda_id TEXT,comentario TEXT,almacen_id TEXT, impuesto_id TEXT,montosubtotal TEXT,montodescuento TEXT,montoimpuesto TEXT,montototal TEXT,fuerzatrabajo_id TEXT,usuario_id TEXT,enviadoERP TEXT,recibidoERP TEXT,ordenventa_ERP_id TEXT,listaprecio_id TEXT,planta_id TEXT,fecharegistro TEXT,tipocambio TEXT,fechatipocambio TEXT,rucdni TEXT,U_SYP_MDTD TEXT,U_SYP_MDSD TEXT,U_SYP_MDCD TEXT,U_SYP_MDMT TEXT,U_SYP_STATUS TEXT,DocType TEXT,mensajeWS TEXT,total_gal_acumulado TEXT,descuentocontado TEXT)");
            db.execSQL("CREATE TABLE ordenventadetalle (compania_id text ,ordenventa_id TEXT,lineaordenventa_id TEXT,producto_id TEXT,umd TEXT,cantidad TEXT,preciounitario TEXT,montosubtotal TEXT,porcentajedescuento TEXT,montodescuento TEXT,montoimpuesto TEXT,montototallinea TEXT,lineareferencia TEXT,impuesto_id TEXT,producto TEXT,AcctCode TEXT,almacen_id TEXT,promocion_id TEXT,gal_unitario TEXT,gal_acumulado TEXT,U_SYP_FECAT07 TEXT,montosubtotalcondescuento TEXT,chk_descuentocontado TEXT)");
            db.execSQL("CREATE TABLE ordenventadetallepromocion (compania_id text ,ordenventa_id TEXT,lineaordenventa_id TEXT,producto_id TEXT,umd TEXT,cantidad TEXT,preciounitario TEXT,montosubtotal TEXT,porcentajedescuento TEXT,montodescuento TEXT,montoimpuesto TEXT,montototallinea TEXT,lineareferencia TEXT,impuesto_id TEXT,producto TEXT,AcctCode TEXT,almacen_id TEXT,promocion_id TEXT,gal_unitario TEXT,gal_acumulado TEXT,U_SYP_FECAT07 TEXT,montosubtotalcondescuento TEXT,chk_descuentocontado TEXT )");

            //Nuevo Flujo de Descuentos
            db.execSQL("CREATE TABLE descuento (compania_id text ,descuento_id TEXT,lineaordenventa_id TEXT,producto_id TEXT,umd TEXT,cantidad TEXT,preciounitario TEXT,montosubtotal TEXT,porcentajedescuento TEXT,montodescuento TEXT,montoimpuesto TEXT,montototallinea TEXT,lineareferencia TEXT,impuesto_id TEXT,producto TEXT,AcctCode TEXT,almacen_id TEXT,promocion_id TEXT,gal_unitario TEXT,gal_acumulado TEXT,U_SYP_FECAT07 TEXT,montosubtotalcondescuento TEXT)");
        //Distribucion
            //Maestros
          //  db.execSQL("CREATE TABLE hojadespacho (compania_id text,control_id TEXT,item_id TEXT,cliente_id TEXT,documento_id TEXT,empaque_id TEXT,nroempaque TEXT,nrofactura TEXT,estado TEXT,fuerzatrabajo_id TEXT,nombrefuerzatrabajo TEXT,domembarque_id TEXT,direccion TEXT,saldo TEXT,nombrecliente TEXT)");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*if (oldVersion < 2) {
            db.execSQL(DATABASE_ALTER_COBRANZACABECERA_CHKUPDATE);
            db.execSQL(DATABASE_ALTER_COBRANZACABECERA_CHKWSUPDATE);
            db.execSQL(DATABASE_ALTER_COBRANZADETALLE_CHKUPDATE);
            db.execSQL(DATABASE_ALTER_COBRANZADETALLE_CHKWSUPDATE);
        }*/
        if (oldVersion < __VERSION)
        {
            deleteDatabase(context);
        }

    }

    public  static void deleteDatabase(Context mContext)
    {

        mContext.deleteDatabase("dbcobranzas");
        //mContext.deleteDatabase("dbcobranzas");

    }



}

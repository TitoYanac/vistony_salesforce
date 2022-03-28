package com.vistony.salesforce.Controller.Utilitario;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;

import io.sentry.BuildConfig;
import io.sentry.Sentry;

public class SqliteController extends SQLiteOpenHelper {

    private SQLiteDatabase db=null;
    private SqliteController instance = null;
    private Context context;
    //ParametrosSQLite parametrosSQLite;
    private static final String DATABASE_NAME = "dbcobranzas";
    private static final int VERSION = 11;


    public SqliteController(Context context){

        super(context,DATABASE_NAME,null,VERSION);
        if (instance == null){
            try {
                instance = this;
                instance.context = context;
                instance.db = instance.getWritableDatabase();
            }catch (Exception e){
                Sentry.captureMessage(e.getMessage());
                Toast.makeText(context.getApplicationContext(), "Ocurrio un Error al levantar el SqlController "+e.getMessage(), Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
        }
    }

    public SQLiteDatabase getInstance() {
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //parametrosSQLite=new ParametrosSQLite(context);
        //Sistema
        db.execSQL("create table parametros (parametro_id text,nombreparametro text, cantidadregistros text, fechacarga text)");
        db.execSQL("create table configuracion (papel text,tamanio text, totalrecibos text, secuenciarecibos text,modeloimpresora text, direccionimpresora text,tipoimpresora text,vinculaimpresora text )");
        db.execSQL("create table usuario (compania_id text,fuerzatrabajo_id text ,nombrecompania text, nombrefuerzatrabajo text,nombreusuario text,usuario_id text,recibo text, chksesion text, online text,perfil text,chkbloqueopago text,listaPrecios_id_1 text,listaPrecios_id_2 text,planta text,almacen_id text,CogsAcct text,U_VIST_CTAINGDCTO text,DocumentsOwner text,U_VIST_SUCUSU text,CentroCosto text,UnidadNegocio text,LineaProduccion text,Impuesto_ID text,Impuesto text,U_VIS_CashDscnt text,Language text,Country text,flag_stock TEXT,flag_backup TEXT,imei TEXT,rate TEXT,print TEXT,activecurrency TEXT)");

        //Cobranzas
        //Maestros
        db.execSQL("CREATE TABLE cliente (cliente_id text,domembarque_id text ,compania_id text, nombrecliente text,direccion text,zona_id text,ordenvisita text,zona text,rucdni text,moneda text,telefonofijo text,telefonomovil text,correo text,ubigeo_id text,impuesto_id text,impuesto text,tipocambio text,categoria TEXT,linea_credito TEXT,linea_credito_usado TEXT,terminopago_id TEXT,lista_precio TEXT,DueDays TEXT,domfactura_id TEXT,lineofbusiness TEXT,lastpurchase TEXT)");
        db.execSQL("CREATE TABLE banco (banco_id text , compania_id text,nombrebanco text)");
        db.execSQL("CREATE TABLE compania (compania_id text , nombrecompania text)");
        db.execSQL("CREATE TABLE documentodeuda (documento_id text ,domembarque_id text, compania_id text,cliente_id text,fuerzatrabajo_id text,fechaemision text,fechavencimiento text,nrofactura text,moneda text,importefactura text,saldo text,saldo_sin_procesar text,doc_entry TEXT,pymntgroup TEXT)");
        db.execSQL("CREATE TABLE fuerzatrabajo (fuerzatrabajo_id text , compania_id text,nombrefuerzatrabajo text)");
        db.execSQL("CREATE TABLE rutavendedor (cliente_id text,domembarque_id text ,compania_id text, nombrecliente text,direccion text,zona_id text,ordenvisita text,zona text,rucdni text,moneda text,telefonofijo text,telefonomovil text,correo text,ubigeo_id text,impuesto_id text,impuesto text,tipocambio text,categoria TEXT,linea_credito TEXT,terminopago_id TEXT, chk_visita TEXT,chk_pedido TEXT,chk_cobranza TEXT,chk_ruta TEXT,fecharuta TEXT,saldomn text,slpCode TEXT,userCode TEXT,salesorderamount TEXT,collectionamount TEXT,lastpurchase TEXT,saldosincontado TEXT)");

        //Transaccional
        db.execSQL("CREATE TABLE cobranzacabecera (cobranza_id text , usuario_id text,banco_id text,compania_id text,totalmontocobrado text,chkdepositado text,chkanulado text,fuerzatrabajo_id text ,tipoingreso text,chkbancarizado text,fechadiferido text, chkwsrecibido text, fechadeposito text,comentarioanulado  text,chkwsanulado text,chkupdate text,chkwsupdate text,pagodirecto text,pagopos text,sap_code TEXT,mensajeWS TEXT,countsend TEXT)");
        db.execSQL("CREATE TABLE cobranzadetalle (id INTEGER PRIMARY KEY AUTOINCREMENT,cobranza_id text , cliente_id text,documento_id text,compania_id text,importedocumento text,saldodocumento text,nuevosaldodocumento text,saldocobrado text, fechacobranza text,recibo text,nrofactura text,chkdepositado text,chkqrvalidado text,chkanulado text ,fuerzatrabajo_id text,chkbancarizado text,motivoanulacion text," +
                "usuario_id text, chkwsrecibido text,banco_id text,chkwsdepositorecibido text,chkwsqrvalidado text,comentario text,chkwsanulado text,chkupdate text,chkwsupdate text,pagodirecto text,pagopos text,sap_code TEXT,mensajeWS TEXT,horacobranza TEXT,countsend TEXT, cardname TEXT, codeSMS TEXT,docentry TEXT )");
        db.execSQL("CREATE TABLE visita (id TEXT,compania_id TEXT,cliente_id TEXT,direccion_id TEXT,fecha_registro TEXT,hora_registro TEXT,zona_id TEXT,fuerzatrabajo_id TEXT,usuario_id TEXT,tipo TEXT,motivo TEXT,observacion TEXT,chkenviado TEXT,chkrecibido TEXT,latitud TEXT,longitud TEXT,countsend TEXT,chkruta TEXT,id_trans_mobile TEXT,amount TEXT,terminopago_id TEXT,hora_anterior TEXT)");

        //Pedidos
            //Maestros
            db.execSQL("CREATE TABLE terminopago (compania_id text,terminopago_id TEXT,terminopago TEXT,contado TEXT,dias_vencimiento TEXT)");
            db.execSQL("CREATE TABLE agencia (compania_id text,agencia_id TEXT ,agencia TEXT, ubigeo_id TEXT, ruc TEXT, direccion TEXT)");
            db.execSQL("CREATE TABLE listapromocion (compania_id text,lista_promocion_id TEXT ,lista_promocion TEXT,U_VIS_CashDscnt text)");
            db.execSQL("CREATE TABLE promocioncabecera (compania_id text ,lista_promocion_id text ,promocion_id TEXT,producto_id TEXT,producto TEXT,umd TEXT,cantidad TEXT,fuerzatrabajo_id TEXT,usuario_id TEXT,total_preciobase TEXT,descuento TEXT)");
            db.execSQL("CREATE TABLE promociondetalle (compania_id text ,lista_promocion_id text ,promocion_id TEXT,promocion_detalle_id TEXT,producto_id TEXT,producto TEXT,umd TEXT,cantidad TEXT,fuerzatrabajo_id TEXT,usuario_id TEXT,preciobase TEXT,chkdescuento TEXT,descuento TEXT)");
            db.execSQL("CREATE TABLE listapreciodetalle (compania_id text ,contado TEXT,credito TEXT,producto_id TEXT,producto TEXT,umd TEXT,gal TEXT ,U_VIS_CashDscnt text,Tipo TEXT,porcentaje_dsct TEXT,stock_almacen TEXT,stock_general TEXT,units TEXT)");

            //db.execSQL("CREATE TABLE stock (compania_id text,producto_id TEXT,producto TEXT,umd TEXT,stock TEXT,almacen_id TEXT,comprometido TEXT,enstock TEXT,pedido TEXT)");
            db.execSQL("CREATE TABLE rutafuerzatrabajo (compania_id text,zona_id TEXT,zona TEXT,dia TEXT,frecuencia TEXT,fechainicioruta TEXT,estado TEXT)");
            db.execSQL("CREATE TABLE direccioncliente (compania_id text,cliente_id text,domembarque_id text,direccion text,zona_id TEXT,zona TEXT,fuerzatrabajo_id TEXT,nombrefuerzatrabajo TEXT)");

            //Transaccional
            db.execSQL("CREATE TABLE ordenventacabecera (compania_id text ,ordenventa_id TEXT,cliente_id TEXT ,domembarque_id TEXT,terminopago_id TEXT,agencia_id TEXT,moneda_id TEXT,comentario " +
                    "TEXT,almacen_id TEXT, impuesto_id TEXT,montosubtotal TEXT,montodescuento TEXT,montoimpuesto TEXT,montototal TEXT,fuerzatrabajo_id TEXT,usuario_id TEXT,enviadoERP TEXT,recibidoERP TEXT,ordenventa_ERP_id" +
                    " TEXT,listaprecio_id TEXT,planta_id TEXT,fecharegistro TEXT,tipocambio TEXT,fechatipocambio TEXT,rucdni TEXT,U_SYP_MDTD TEXT,U_SYP_MDSD TEXT,U_SYP_MDCD TEXT," +
                    "U_SYP_MDMT TEXT,U_SYP_STATUS TEXT,DocType TEXT,mensajeWS TEXT,total_gal_acumulado TEXT,descuentocontado TEXT,dueDays_cliente TEXT,excede_lineacredito TEXT,U_VIS_AgencyRUC TEXT,U_VIS_AgencyName TEXT,U_VIS_AgencyDir TEXT,domfactura_id TEXT,domembarque_text TEXT,cliente_text TEXT, terminopago_text TEXT,quotation TEXT,dispatchdate TEXT,countsend TEXT)");

            db.execSQL("CREATE TABLE ordenventadetalle (compania_id text ,ordenventa_id TEXT,lineaordenventa_id TEXT,producto_id TEXT,umd TEXT,cantidad TEXT,preciounitario TEXT,montosubtotal TEXT,porcentajedescuento TEXT,montodescuento TEXT,montoimpuesto TEXT,montototallinea TEXT,lineareferencia TEXT,impuesto_id TEXT,producto TEXT,AcctCode TEXT,almacen_id TEXT,promocion_id TEXT,gal_unitario TEXT,gal_acumulado TEXT,U_SYP_FECAT07 TEXT,montosubtotalcondescuento TEXT,chk_descuentocontado TEXT)");
            db.execSQL("CREATE TABLE ordenventadetallepromocion (compania_id text ,ordenventa_id TEXT,lineaordenventa_id TEXT,producto_id TEXT,umd TEXT,cantidad TEXT,preciounitario TEXT,montosubtotal TEXT,porcentajedescuento TEXT,montodescuento TEXT,montoimpuesto TEXT,montototallinea TEXT,lineareferencia TEXT,impuesto_id TEXT,producto TEXT,AcctCode TEXT,almacen_id TEXT,promocion_id TEXT,gal_unitario TEXT,gal_acumulado TEXT,U_SYP_FECAT07 TEXT,montosubtotalcondescuento TEXT,chk_descuentocontado TEXT )");
            //Nuevo Flujo de Descuentos
            //db.execSQL("CREATE TABLE descuento (compania_id text ,descuento_id TEXT,lineaordenventa_id TEXT,producto_id TEXT,umd TEXT,cantidad TEXT,preciounitario TEXT,montosubtotal TEXT,porcentajedescuento TEXT,montodescuento TEXT,montoimpuesto TEXT,montototallinea TEXT,lineareferencia TEXT,impuesto_id TEXT,producto TEXT,AcctCode TEXT,almacen_id TEXT,promocion_id TEXT,gal_unitario TEXT,gal_acumulado TEXT,U_SYP_FECAT07 TEXT,montosubtotalcondescuento TEXT)");

        //Lead
            db.execSQL("CREATE TABLE lead (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,document_owner TEXT,sales_person TEXT,razon_social TEXT,ruc TEXT,nombre_comercial TEXT,numero_telefono TEXT,numero_celular TEXT,persona_contacto TEXT,correo TEXT,latitud TEXT,longitud TEXT,direccion TEXT,referencias TEXT,comentario TEXT,categoria TEXT,foto TEXT,fecha TEXT,recibido_api INTEGER)");
            db.execSQL("CREATE TABLE motivovisita (compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT,code TEXT,name TEXT,type TEXT,fecha TEXT)");
            db.execSQL("CREATE TABLE pricelist (compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT,pricelist_id TEXT,pricelist TEXT)");

            //Version 4--Migracion Huawey
            db.execSQL("CREATE TABLE commissions (variable text,uom TEXT,advance TEXT,quota TEXT,percentage TEXT,esc_colours TEXT,hidedata TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT)");
            db.execSQL("CREATE TABLE esc_colours_c (id text,description TEXT,status TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT)");
            db.execSQL("CREATE TABLE esc_colours_d (id_esc_colours_c TEXT,id TEXT,rangemin TEXT,rangemax TEXT,colourmin TEXT,colourmax TEXT,degrade TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT )");

            //Version 5 --Cobranzas Distribucion
            db.execSQL("CREATE TABLE headerdispatchsheet (compania_id text,fuerzatrabajo_id text,usuario_id text,control_id TEXT,asistente_id TEXT,asistente TEXT,placa TEXT,marca TEXT,pesototal TEXT,fechahojadespacho TEXT)");
            db.execSQL("CREATE TABLE detaildispatchsheet (compania_id text,fuerzatrabajo_id text,usuario_id text,control_id TEXT,item_id TEXT,cliente_id TEXT,domembarque_id TEXT,direccion TEXT,factura_id TEXT,entrega_id TEXT,entrega TEXT,factura TEXT,saldo TEXT,estado TEXT, fuerzatrabajo_factura_id TEXT,fuerzatrabajo_factura TEXT,terminopago_id TEXT,terminopago TEXT,peso TEXT,comentariodespacho TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>1&&newVersion<=2){
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN slpCode TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN userCode TEXT");
        }


        if(oldVersion==1&&newVersion==3){
            db.execSQL("CREATE TABLE pricelist (compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT,pricelist_id TEXT,pricelist TEXT)");
            db.execSQL("CREATE TABLE motivovisita (compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT,code TEXT,name TEXT,type TEXT,fecha TEXT)");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN slpCode TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN userCode TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN rate TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN print TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN activecurrency TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN imei TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN quotation TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN salesorderamount TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN collectionamount TEXT");
            db.execSQL("ALTER TABLE cobranzacabecera ADD COLUMN sap_code TEXT");
            db.execSQL("ALTER TABLE cobranzacabecera ADD COLUMN mensajeWS TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN horacobranza TEXT");
        }
        if(oldVersion==2&&newVersion==3){
            db.execSQL("CREATE TABLE pricelist (compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT,pricelist_id TEXT,pricelist TEXT)");
            db.execSQL("ALTER TABLE usuario ADD COLUMN print TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN activecurrency TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN salesorderamount TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN collectionamount TEXT");
        }
        if(oldVersion==3&&newVersion==4){
            db.execSQL("CREATE TABLE commissions (variable text,uom TEXT,advance TEXT,quota TEXT,percentage TEXT,esc_colours TEXT,hidedata TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT)");
            db.execSQL("CREATE TABLE esc_colours_c (id text,description TEXT,status TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT)");
            db.execSQL("CREATE TABLE esc_colours_d (id_esc_colours_c TEXT,id TEXT,rangemin TEXT,rangemax TEXT,colourmin TEXT,colourmax TEXT,degrade TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT )");
        }
        if(oldVersion==3&&newVersion==5){
            db.execSQL("CREATE TABLE commissions (variable text,uom TEXT,advance TEXT,quota TEXT,percentage TEXT,esc_colours TEXT,hidedata TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT)");
            db.execSQL("CREATE TABLE esc_colours_c (id text,description TEXT,status TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT)");
            db.execSQL("CREATE TABLE esc_colours_d (id_esc_colours_c TEXT,id TEXT,rangemin TEXT,rangemax TEXT,colourmin TEXT,colourmax TEXT,degrade TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT )");
            db.execSQL("CREATE TABLE headerdispatchsheet (compania_id text,fuerzatrabajo_id text,usuario_id text,control_id TEXT,asistente_id TEXT,asistente TEXT,placa TEXT,marca TEXT,pesototal TEXT,fechahojadespacho TEXT)");
            db.execSQL("CREATE TABLE detaildispatchsheet (compania_id text,fuerzatrabajo_id text,usuario_id text,control_id TEXT,item_id TEXT,cliente_id TEXT,domembarque_id TEXT,direccion TEXT,factura_id TEXT,entrega_id TEXT,entrega TEXT,factura TEXT,saldo TEXT,estado TEXT, fuerzatrabajo_factura_id TEXT,fuerzatrabajo_factura TEXT,terminopago_id TEXT,terminopago TEXT,peso TEXT,comentariodespacho TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN dispatchdate TEXT");

        }
        if(oldVersion==4&&newVersion==5){
            db.execSQL("CREATE TABLE headerdispatchsheet (compania_id text,fuerzatrabajo_id text,usuario_id text,control_id TEXT,asistente_id TEXT,asistente TEXT,placa TEXT,marca TEXT,pesototal TEXT,fechahojadespacho TEXT)");
            db.execSQL("CREATE TABLE detaildispatchsheet (compania_id text,fuerzatrabajo_id text,usuario_id text,control_id TEXT,item_id TEXT,cliente_id TEXT,domembarque_id TEXT,direccion TEXT,factura_id TEXT,entrega_id TEXT,entrega TEXT,factura TEXT,saldo TEXT,estado TEXT, fuerzatrabajo_factura_id TEXT,fuerzatrabajo_factura TEXT,terminopago_id TEXT,terminopago TEXT,peso TEXT,comentariodespacho TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN dispatchdate TEXT");
        }
        if(oldVersion==5&&newVersion==6){
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN countsend TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN countsend TEXT");
            db.execSQL("ALTER TABLE cobranzacabecera ADD COLUMN countsend TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN countsend TEXT");

        }
        if(oldVersion==6&&newVersion==7) {
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN cardname TEXT");
        }
        if(oldVersion==3&&newVersion==7){
            db.execSQL("CREATE TABLE commissions (variable text,uom TEXT,advance TEXT,quota TEXT,percentage TEXT,esc_colours TEXT,hidedata TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT)");
            db.execSQL("CREATE TABLE esc_colours_c (id text,description TEXT,status TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT)");
            db.execSQL("CREATE TABLE esc_colours_d (id_esc_colours_c TEXT,id TEXT,rangemin TEXT,rangemax TEXT,colourmin TEXT,colourmax TEXT,degrade TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT )");
            db.execSQL("CREATE TABLE headerdispatchsheet (compania_id text,fuerzatrabajo_id text,usuario_id text,control_id TEXT,asistente_id TEXT,asistente TEXT,placa TEXT,marca TEXT,pesototal TEXT,fechahojadespacho TEXT)");
            db.execSQL("CREATE TABLE detaildispatchsheet (compania_id text,fuerzatrabajo_id text,usuario_id text,control_id TEXT,item_id TEXT,cliente_id TEXT,domembarque_id TEXT,direccion TEXT,factura_id TEXT,entrega_id TEXT,entrega TEXT,factura TEXT,saldo TEXT,estado TEXT, fuerzatrabajo_factura_id TEXT,fuerzatrabajo_factura TEXT,terminopago_id TEXT,terminopago TEXT,peso TEXT,comentariodespacho TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN dispatchdate TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN countsend TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN countsend TEXT");
            db.execSQL("ALTER TABLE cobranzacabecera ADD COLUMN countsend TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN countsend TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN cardname TEXT");

        }
        if(oldVersion==7&&newVersion==8) {
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN codeSMS TEXT");
        }
        if(oldVersion==8&&newVersion==9) {
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN docentry TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN lineofbusiness TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN lastpurchase TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN lastpurchase TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN  units TEXT");

        }
        if(oldVersion==6&&newVersion==9) {
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN cardname TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN codeSMS TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN docentry TEXT");
        }
        if(oldVersion==9&&newVersion==10) {
            db.execSQL("ALTER TABLE visita ADD COLUMN chkruta TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN id_trans_mobile TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN amount TEXT");
        }
        if(oldVersion==10&&newVersion==11) {
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN saldosincontado TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN terminopago_id TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN hora_anterior TEXT");
            db.execSQL("ALTER TABLE documentodeuda ADD COLUMN pymntgroup TEXT");

        }
    }

    public  static void deleteDatabase(Context mContext){
        mContext.deleteDatabase("dbcobranzas");
    }
}

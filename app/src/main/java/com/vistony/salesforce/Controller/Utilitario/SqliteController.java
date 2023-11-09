package com.vistony.salesforce.Controller.Utilitario;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;

import io.sentry.BuildConfig;

public class SqliteController extends SQLiteOpenHelper {

    private SQLiteDatabase db=null;
    private SqliteController instance = null;
    private Context context;
    //ParametrosSQLite parametrosSQLite;
    private static final String DATABASE_NAME = "dbcobranzas";
    private static final int VERSION = 46;


    public SqliteController(Context context){

        super(context,DATABASE_NAME,null,VERSION);
        if (instance == null){
            try {
                instance = this;
                instance.context = context;
                instance.db = instance.getWritableDatabase();
            }catch (Exception e){
                Log.e("REOS","SqliteController.Construtor.context:"+context);
                Log.e("REOS","SqliteController.Construtor.error:"+e.toString());
                Toast.makeText(context, "Ocurrio un Error al levantar el SqlController "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
        db.execSQL("create table usuario (compania_id text,fuerzatrabajo_id text ,nombrecompania text, nombrefuerzatrabajo text,nombreusuario text,usuario_id text,recibo text, chksesion text, online text,perfil text,chkbloqueopago text,listaPrecios_id_1 text,listaPrecios_id_2 text,planta text,almacen_id text,CogsAcct text,U_VIST_CTAINGDCTO text,DocumentsOwner text,U_VIST_SUCUSU text,CentroCosto text,UnidadNegocio text,LineaProduccion text,Impuesto_ID text,Impuesto text,U_VIS_CashDscnt text,Language text,Country text,flag_stock TEXT,flag_backup TEXT,imei TEXT,rate TEXT,print TEXT,activecurrency TEXT,quotation TEXT,census TEXT" +
                ",oiltaxstatus TEXT, deliverydateauto TEXT, deliveryrefusedmoney TEXT,status TEXT,sendvisits TEXT,sendvalidations TEXT,U_VIS_ManagementType TEXT)");

        //Cobranzas
        //Maestros
        db.execSQL("CREATE TABLE cliente (cliente_id text,domembarque_id text ,compania_id text, nombrecliente text,direccion text,zona_id text,ordenvisita text,zona text,rucdni text,moneda text,telefonofijo text,telefonomovil text,correo text,ubigeo_id text,impuesto_id text,impuesto text,tipocambio text,categoria TEXT,linea_credito TEXT,linea_credito_usado TEXT,terminopago_id TEXT,lista_precio TEXT,DueDays TEXT,domfactura_id TEXT,lineofbusiness TEXT,lastpurchase TEXT,statuscounted TEXT,customerwhitelist TEXT)");
        db.execSQL("CREATE TABLE banco (banco_id text , compania_id text,nombrebanco text,singledeposit text,pagopos text)");
        db.execSQL("CREATE TABLE compania (compania_id text , nombrecompania text)");
        db.execSQL("CREATE TABLE documentodeuda (documento_id text ,domembarque_id text, compania_id text,cliente_id text,fuerzatrabajo_id text,fechaemision text,fechavencimiento text,nrofactura text,moneda text,importefactura text,saldo text,saldo_sin_procesar text,doc_entry TEXT,pymntgroup TEXT,additionaldiscount TEXT)");
        db.execSQL("CREATE TABLE fuerzatrabajo (fuerzatrabajo_id text , compania_id text,nombrefuerzatrabajo text)");
        db.execSQL("CREATE TABLE rutavendedor (cliente_id text,domembarque_id text ,compania_id text, nombrecliente text,direccion text,zona_id text,ordenvisita text,zona text,rucdni text,moneda text,telefonofijo text,telefonomovil text,correo text,ubigeo_id text,impuesto_id text,impuesto text,tipocambio text,categoria TEXT,linea_credito TEXT,terminopago_id TEXT, chk_visita TEXT,chk_pedido TEXT,chk_cobranza TEXT,chk_ruta TEXT,fecharuta TEXT,saldomn text,slpCode TEXT,userCode TEXT,salesorderamount TEXT,collectionamount TEXT,lastpurchase TEXT,saldosincontado TEXT,chkgeolocation TEXT,chkvisitsection TEXT" +
                ",terminopago TEXT,contado TEXT,latitud TEXT,longitud TEXT,addresscode TEXT,statuscounted TEXT,typevisit TEXT, quotationamount TEXT,chk_quotation TEXT,customerwhitelist TEXT)");

        //Transaccional
        db.execSQL("CREATE TABLE cobranzacabecera (cobranza_id text , usuario_id text,banco_id text,compania_id text,totalmontocobrado text,chkdepositado text,chkanulado text,fuerzatrabajo_id text ,tipoingreso text,chkbancarizado text,fechadiferido text, chkwsrecibido text, fechadeposito text,comentarioanulado  text,chkwsanulado text,chkupdate text,chkwsupdate text,pagodirecto text,pagopos text,sap_code TEXT,mensajeWS TEXT,countsend TEXT,collection_salesperson TEXT)");
        db.execSQL("CREATE TABLE cobranzadetalle (id INTEGER PRIMARY KEY AUTOINCREMENT,cobranza_id text , cliente_id text,documento_id text,compania_id text,importedocumento text,saldodocumento text,nuevosaldodocumento text,saldocobrado text, fechacobranza text,recibo text,nrofactura text,chkdepositado text,chkqrvalidado text,chkanulado text ,fuerzatrabajo_id text,chkbancarizado text,motivoanulacion text," +
                "usuario_id text, chkwsrecibido text,banco_id text,chkwsdepositorecibido text,chkwsqrvalidado text,comentario text,chkwsanulado text,chkupdate text,chkwsupdate text,pagodirecto text,pagopos text,sap_code TEXT,mensajeWS TEXT,horacobranza TEXT,countsend TEXT, cardname TEXT, codeSMS TEXT,docentry TEXT,collectioncheck TEXT" +
                ",e_signature TEXT,chkesignature TEXT,phone TEXT,collection_salesperson TEXT,type_description TEXT" +
                ")");
        db.execSQL("CREATE TABLE cobranzadetalleSMS (id INTEGER PRIMARY KEY AUTOINCREMENT,recibo TEXT,phone TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT,date TEXT,hour TEXT,chk_send TEXT)");
        db.execSQL("CREATE TABLE visita (id TEXT,compania_id TEXT,cliente_id TEXT,direccion_id TEXT,fecha_registro TEXT,hora_registro TEXT,zona_id TEXT,fuerzatrabajo_id TEXT,usuario_id TEXT,tipo TEXT,motivo TEXT,observacion TEXT,chkenviado TEXT,chkrecibido TEXT,latitud TEXT,longitud TEXT,countsend TEXT,chkruta TEXT,id_trans_mobile TEXT,amount TEXT,terminopago_id TEXT,hora_anterior TEXT)");

        //Pedidos
            //Maestros
            db.execSQL("CREATE TABLE terminopago (compania_id text,terminopago_id TEXT,terminopago TEXT,contado TEXT,dias_vencimiento TEXT)");
            db.execSQL("CREATE TABLE agencia (compania_id text,agencia_id TEXT ,agencia TEXT, ubigeo_id TEXT, ruc TEXT, direccion TEXT)");
            db.execSQL("CREATE TABLE listapromocion (compania_id text,lista_promocion_id TEXT ,lista_promocion TEXT,U_VIS_CashDscnt text)");
            db.execSQL("CREATE TABLE promocioncabecera (compania_id text ,lista_promocion_id text ,promocion_id TEXT,producto_id TEXT,producto TEXT,umd TEXT,cantidad TEXT,fuerzatrabajo_id TEXT,usuario_id TEXT,total_preciobase TEXT,descuento TEXT,cantidad_maxima TEXT,tipo_malla TEXT )");
            db.execSQL("CREATE TABLE promociondetalle (compania_id text ,lista_promocion_id text ,promocion_id TEXT,promocion_detalle_id TEXT,producto_id TEXT,producto TEXT,umd TEXT,cantidad TEXT,fuerzatrabajo_id TEXT,usuario_id TEXT,preciobase TEXT,chkdescuento TEXT,descuento TEXT)");
            db.execSQL("CREATE TABLE listapreciodetalle (compania_id text ,contado TEXT,credito TEXT,producto_id TEXT,producto TEXT,umd TEXT,gal TEXT ,U_VIS_CashDscnt text,Tipo TEXT,porcentaje_dsct TEXT,stock_almacen TEXT,stock_general TEXT,units TEXT" +
                    ", oiltax TEXT, liter TEXT, SIGAUS TEXT,MonedaAdicional TEXT,MonedaAdicionalContado TEXT,MonedaAdicionalCredito TEXT,CodePriceListCash TEXT,CodePriceListCredit TEXT,CodAlmacen TEXT )");

            //db.execSQL("CREATE TABLE stock (compania_id text,producto_id TEXT,producto TEXT,umd TEXT,stock TEXT,almacen_id TEXT,comprometido TEXT,enstock TEXT,pedido TEXT)");
            db.execSQL("CREATE TABLE rutafuerzatrabajo (compania_id text,zona_id TEXT,zona TEXT,dia TEXT,frecuencia TEXT,fechainicioruta TEXT,estado TEXT)");
            db.execSQL("CREATE TABLE direccioncliente (compania_id text,cliente_id text,domembarque_id text,direccion text,zona_id TEXT,zona TEXT,fuerzatrabajo_id TEXT,nombrefuerzatrabajo TEXT,latitud TEXT,longitud TEXT,addresscode TEXT, deliveryday TEXT, zipcode TEXT)");

            //Transaccional
            db.execSQL("CREATE TABLE ordenventacabecera (compania_id text ,ordenventa_id TEXT,cliente_id TEXT ,domembarque_id TEXT,terminopago_id TEXT,agencia_id TEXT,moneda_id TEXT,comentario " +
                    "TEXT,almacen_id TEXT, impuesto_id TEXT,montosubtotal TEXT,montodescuento TEXT,montoimpuesto TEXT,montototal TEXT,fuerzatrabajo_id TEXT,usuario_id TEXT,enviadoERP TEXT,recibidoERP TEXT,ordenventa_ERP_id" +
                    " TEXT,listaprecio_id TEXT,planta_id TEXT,fecharegistro TEXT,tipocambio TEXT,fechatipocambio TEXT,rucdni TEXT,U_SYP_MDTD TEXT,U_SYP_MDSD TEXT,U_SYP_MDCD TEXT," +
                    "U_SYP_MDMT TEXT,U_SYP_STATUS TEXT,DocType TEXT,mensajeWS TEXT,total_gal_acumulado TEXT,descuentocontado TEXT,dueDays_cliente TEXT,excede_lineacredito TEXT,U_VIS_AgencyRUC TEXT" +
                    ",U_VIS_AgencyName TEXT,U_VIS_AgencyDir TEXT,domfactura_id TEXT,domembarque_text TEXT,cliente_text TEXT" +
                    ", terminopago_text TEXT,quotation TEXT,dispatchdate TEXT,countsend TEXT,route TEXT, U_VIT_VENMOS TEXT, U_VIS_Flete TEXT, U_VIS_CompleteOV TEXT, U_VIS_TipTransGrat TEXT, status TEXT,U_VIS_DiscountPercent TEXT,U_VIS_ReasonDiscountPercent TEXT,U_VIS_MOTAPLDESC TEXT,U_VIST_SUCUSU TEXT, DocEntry TEXT)");

            db.execSQL("CREATE TABLE ordenventadetalle (compania_id text ,ordenventa_id TEXT,lineaordenventa_id TEXT,producto_id TEXT,umd TEXT,cantidad TEXT,preciounitario TEXT,montosubtotal TEXT,porcentajedescuento TEXT,montodescuento TEXT,montoimpuesto TEXT,montototallinea TEXT,lineareferencia TEXT,impuesto_id TEXT,producto TEXT,AcctCode TEXT,almacen_id TEXT,promocion_id TEXT,gal_unitario TEXT,gal_acumulado TEXT,U_SYP_FECAT07 TEXT,montosubtotalcondescuento TEXT,chk_descuentocontado TEXT)");
            db.execSQL("CREATE TABLE ordenventadetallepromocion (compania_id text ,ordenventa_id TEXT,lineaordenventa_id TEXT,producto_id TEXT,umd TEXT,cantidad TEXT,preciounitario TEXT,montosubtotal TEXT,porcentajedescuento TEXT,montodescuento TEXT,montoimpuesto TEXT,montototallinea TEXT,lineareferencia TEXT,impuesto_id TEXT,producto TEXT,AcctCode TEXT,almacen_id TEXT,promocion_id TEXT,gal_unitario TEXT,gal_acumulado TEXT,U_SYP_FECAT07 TEXT,montosubtotalcondescuento TEXT,chk_descuentocontado TEXT )");
            //Nuevo Flujo de Descuentos
            //db.execSQL("CREATE TABLE descuento (compania_id text ,descuento_id TEXT,lineaordenventa_id TEXT,producto_id TEXT,umd TEXT,cantidad TEXT,preciounitario TEXT,montosubtotal TEXT,porcentajedescuento TEXT,montodescuento TEXT,montoimpuesto TEXT,montototallinea TEXT,lineareferencia TEXT,impuesto_id TEXT,producto TEXT,AcctCode TEXT,almacen_id TEXT,promocion_id TEXT,gal_unitario TEXT,gal_acumulado TEXT,U_SYP_FECAT07 TEXT,montosubtotalcondescuento TEXT)");

        //Lead
            db.execSQL("CREATE TABLE lead (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,document_owner TEXT,sales_person TEXT,razon_social TEXT,ruc TEXT,nombre_comercial TEXT,numero_telefono TEXT,numero_celular TEXT,persona_contacto TEXT,correo TEXT,latitud TEXT,longitud TEXT,direccion TEXT,referencias TEXT,comentario TEXT,categoria TEXT,foto TEXT,fecha TEXT,recibido_api INTEGER,cardcode TEXT,domembarque_id TEXT,type TEXT,addresscode TEXT,messageserver TEXT,chk_ServerGeolocation TEXT,MessageServerGeolocation TEXT )");
            db.execSQL("CREATE TABLE motivovisita (compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT,code TEXT,name TEXT,type TEXT,fecha TEXT)");
            db.execSQL("CREATE TABLE pricelist (compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT,pricelist_id TEXT,pricelist TEXT)");

            //Version 4--Migracion Huawey
            db.execSQL("CREATE TABLE commissions (variable text,uom TEXT,advance TEXT,quota TEXT,percentage TEXT,esc_colours TEXT,hidedata TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT)");
            db.execSQL("CREATE TABLE esc_colours_c (id text,description TEXT,status TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT)");
            db.execSQL("CREATE TABLE esc_colours_d (id_esc_colours_c TEXT,id TEXT,rangemin TEXT,rangemax TEXT,colourmin TEXT,colourmax TEXT,degrade TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT )");

            //Version 5 --Cobranzas Distribucion
            db.execSQL("CREATE TABLE headerdispatchsheet (compania_id text,fuerzatrabajo_id text,usuario_id text,control_id TEXT,asistente_id TEXT,asistente TEXT,placa TEXT,marca TEXT,pesototal TEXT,fechahojadespacho TEXT,drivercode TEXT,vehiclecode TEXT,vehicleplate TEXT, drivermobile TEXT,drivername TEXT,datetimeregister TEXT )");
            db.execSQL("CREATE TABLE detaildispatchsheet (compania_id text,fuerzatrabajo_id text,usuario_id text,control_id TEXT,item_id TEXT,cliente_id TEXT,domembarque_id TEXT,direccion TEXT,factura_id TEXT,entrega_id TEXT,entrega TEXT,factura TEXT,saldo TEXT,estado TEXT, fuerzatrabajo_factura_id TEXT,fuerzatrabajo_factura TEXT,terminopago_id TEXT,terminopago TEXT,peso TEXT,comentariodespacho TEXT,estado_id TEXT,motivo TEXT,motivo_id TEXT,fotoguia TEXT,fotolocal TEXT)");


            //Version 12  --Tramo de Visita
            db.execSQL("CREATE TABLE visitsection (compania_id text,fuerzatrabajo_id text,usuario_id text,cliente_id TEXT,domembarque_id TEXT,latitudini TEXT,longitudini TEXT,dateini TEXT,timeini TEXT,latitudfin TEXT,longitudfin TEXT,datefin TEXT,timefin TEXT,chkrecibido TEXT,idref TEXT,idrefitemid TEXT,legalnumberref TEXT)");

            //Version 13  -- Distribution Second Phase
            db.execSQL("CREATE TABLE reasondispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,reasondispatch_id TEXT,reasondispatch TEXT,typedispatch_id TEXT)");
            db.execSQL("CREATE TABLE typedispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,typedispatch TEXT,statusupdate TEXT)");
            db.execSQL("CREATE TABLE statusdispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,reasondispatch_id TEXT,cliente_id TEXT,factura_id TEXT,entrega_id TEXT,chkrecibido TEXT,observation TEXT,foto TEXT,fecha_registro TEXT,hora_registro TEXT,fotoGuia TEXT,latitud TEXT,longitud TEXT,cliente TEXT,factura TEXT,entrega TEXT,typedispatch TEXT,reasondispatch TEXT,control_id TEXT,item_id TEXT,domembarque_id text,checkintime text,checkouttime text,chk_timestatus text,fuerzatrabajo text, messageServerDispatch text, messageServerTimeDispatch text,chk_statusServerDispatch text, messageServerStatusDispatch)");

            //Version 12  --Tramo de Visita
            db.execSQL("CREATE TABLE quoteeffectiveness (compania_id text,fuerzatrabajo_id text,usuario_id text,code TEXT,type TEXT,quote TEXT,umd TEXT)");

            //Version 26  --Tramo de Visita
            db.execSQL("CREATE TABLE ubigeous (compania_id text,fuerzatrabajo_id text,usuario_id text,code TEXT,U_SYP_DEPA TEXT,U_SYP_PROV TEXT,U_SYP_DIST TEXT,U_VIS_Flete TEXT)");

            //Version 26  --Tramo de Visita
            db.execSQL("CREATE TABLE documentdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,DocEntry TEXT,LineNum TEXT,ItemCode TEXT,Dscription TEXT,Quantity TEXT,LineTotal TEXT,WhsCode TEXT,LineStatus TEXT,TaxCode TEXT,DiscPrcnt TEXT,TaxOnly TEXT)");

            //Version 26  --Tramo de Visita
            db.execSQL("CREATE TABLE questionshead (compania_id text,fuerzatrabajo_id text,usuario_id text,Code TEXT,U_Tipo TEXT,U_Pregunta TEXT,U_Estado TEXT)");

            //Version 26  --Tramo de Visita
            db.execSQL("CREATE TABLE questionsdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code TEXT,LineId TEXT,U_Estado TEXT,U_Orden TEXT,U_Respuesta TEXT)");

            //Formulario
            //db.execSQL("CREATE TABLE form (formcode text,formname text,entrycode text,cardcode TEXT,shiptocode TEXT,docentry TEXT,date TEXT,salesrepcode TEXT,userid TEXT,time TEXT,chk_send TEXT,chk_receive TEXT,msg_server TEXT)");
            db.execSQL("CREATE TABLE form (formcode text,formname text,entrycode text,date TEXT,salesrepcode TEXT,userid TEXT,time TEXT,chk_send TEXT,chk_receive TEXT,msg_server TEXT)");
            //Sección
            db.execSQL("CREATE TABLE formsection (formcode text,sectioncode text,sectionname text,entrycode TEXT)");

            //Questions
            db.execSQL("CREATE TABLE formquestion (sectioncode text,questioncode text,questionname text,entrycode TEXT, codedepence TEXT,questionsanswered TEXT)");

            //Questions
            db.execSQL("CREATE TABLE formresponse (questioncode text,responsecode text,responsename text,typeresponse TEXT, fileattach TEXT,entrycode TEXT, responsechoisse TEXT)");

            //ReasonFreetransfer
            db.execSQL("CREATE TABLE reasonfreetransfer (compania_id text,fuerzatrabajo_id text,usuario_id text,Value TEXT, Dscription TEXT)");

            //PriceListHead
            db.execSQL("CREATE TABLE pricelisthead (compania_id text,fuerzatrabajo_id text,usuario_id text,ListNum TEXT,ListName TEXT,U_VIS_PercentageIncrease TEXT)");

            //Business Layer Head
            db.execSQL("CREATE TABLE businesslayerhead (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,Name text,U_VIS_Objetive text,U_VIS_VariableType text,U_VIS_Variable text,U_VIS_Trigger text,U_VIS_TriggerType text,U_VIS_Active text,U_VIS_ValidFrom text,U_VIS_ValidUntil text )");

            //Business Layer Detail
            db.execSQL("CREATE TABLE businesslayerdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,LineId text,U_VIS_Type text,U_VIS_Object text,U_VIS_Action text)");

            //Object App
            db.execSQL("CREATE TABLE object (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,Name text)");

            //Business Layer Head
            db.execSQL("CREATE TABLE businesslayersalesdetailHeader (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text ,Object text,Name text, Action text )");

            //Business Layer Head
            db.execSQL("CREATE TABLE businesslayersalesdetailDetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text ,LineId text,RangeActive Text,Object text ,TypeObject text,ValueMin text,ValueMax text,Field text,Variable text )");

            //Almacenes
            db.execSQL("CREATE TABLE warehouse (WhsCode text,WhsName text,PriceListCash text,PriceListCredit text,U_VIST_SUCUSU text)");

            //Almacenes
            db.execSQL("CREATE TABLE sellerroute (CardCode text,Address text,Chk_Visit text,Chk_Pedido text,Chk_Cobranza text,Chk_Ruta text,FechaRuta text)");
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
        if(oldVersion==11&&newVersion==12) {
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN latitud TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN longitud TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkgeolocation TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkvisitsection TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN cardcode TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN domembarque_id TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN type TEXT");
            db.execSQL("CREATE TABLE visitsection (compania_id text,fuerzatrabajo_id text,usuario_id text,cliente_id TEXT,domembarque_id TEXT,latitudini TEXT,longitudini TEXT,dateini TEXT,timeini TEXT,latitudfin TEXT,longitudfin TEXT,datefin TEXT,timefin TEXT,chkrecibido TEXT)");
        }
        if(oldVersion==6&&newVersion==13) {
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN codeSMS TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN docentry TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN lineofbusiness TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN lastpurchase TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN lastpurchase TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN  units TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN saldosincontado TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN chkruta TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN id_trans_mobile TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN amount TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN terminopago_id TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN hora_anterior TEXT");
            db.execSQL("ALTER TABLE documentodeuda ADD COLUMN pymntgroup TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN latitud TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN longitud TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkgeolocation TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkvisitsection TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN cardcode TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN domembarque_id TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN type TEXT");
            db.execSQL("CREATE TABLE visitsection (compania_id text,fuerzatrabajo_id text,usuario_id text,cliente_id TEXT,domembarque_id TEXT,latitudini TEXT,longitudini TEXT,dateini TEXT,timeini TEXT,latitudfin TEXT,longitudfin TEXT,datefin TEXT,timefin TEXT,chkrecibido TEXT)");
            db.execSQL("CREATE TABLE reasondispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,reasondispatch_id TEXT,reasondispatch TEXT,typedispatch_id TEXT)");
            db.execSQL("CREATE TABLE typedispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,typedispatch TEXT)");
            db.execSQL("CREATE TABLE statusdispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,reasondispatch_id TEXT,cliente_id TEXT,factura_id TEXT,entrega_id TEXT,chkrecibido TEXT,observation TEXT,foto TEXT,fecha_registro TEXT,hora_registro TEXT,fotoGuia TEXT,latitud TEXT,longitud TEXT,cliente TEXT,factura TEXT,entrega TEXT,typedispatch TEXT,reasondispatch TEXT)");
        }
        if(oldVersion==7&&newVersion==13) {
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN codeSMS TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN docentry TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN lineofbusiness TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN lastpurchase TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN lastpurchase TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN  units TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN saldosincontado TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN chkruta TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN id_trans_mobile TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN amount TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN terminopago_id TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN hora_anterior TEXT");
            db.execSQL("ALTER TABLE documentodeuda ADD COLUMN pymntgroup TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN latitud TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN longitud TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkgeolocation TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkvisitsection TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN cardcode TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN domembarque_id TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN type TEXT");
            db.execSQL("CREATE TABLE visitsection (compania_id text,fuerzatrabajo_id text,usuario_id text,cliente_id TEXT,domembarque_id TEXT,latitudini TEXT,longitudini TEXT,dateini TEXT,timeini TEXT,latitudfin TEXT,longitudfin TEXT,datefin TEXT,timefin TEXT,chkrecibido TEXT)");
            db.execSQL("CREATE TABLE reasondispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,reasondispatch_id TEXT,reasondispatch TEXT,typedispatch_id TEXT)");
            db.execSQL("CREATE TABLE typedispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,typedispatch TEXT)");
            db.execSQL("CREATE TABLE statusdispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,reasondispatch_id TEXT,cliente_id TEXT,factura_id TEXT,entrega_id TEXT,chkrecibido TEXT,observation TEXT,foto TEXT,fecha_registro TEXT,hora_registro TEXT,fotoGuia TEXT,latitud TEXT,longitud TEXT,cliente TEXT,factura TEXT,entrega TEXT,typedispatch TEXT,reasondispatch TEXT)");
        }
        if(oldVersion==11&&newVersion==13) {
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN latitud TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN longitud TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkgeolocation TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkvisitsection TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN cardcode TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN domembarque_id TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN type TEXT");
            db.execSQL("CREATE TABLE visitsection (compania_id text,fuerzatrabajo_id text,usuario_id text,cliente_id TEXT,domembarque_id TEXT,latitudini TEXT,longitudini TEXT,dateini TEXT,timeini TEXT,latitudfin TEXT,longitudfin TEXT,datefin TEXT,timefin TEXT,chkrecibido TEXT)");
            db.execSQL("CREATE TABLE reasondispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,reasondispatch_id TEXT,reasondispatch TEXT,typedispatch_id TEXT)");
            db.execSQL("CREATE TABLE typedispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,typedispatch TEXT)");
            db.execSQL("CREATE TABLE statusdispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,reasondispatch_id TEXT,cliente_id TEXT,factura_id TEXT,entrega_id TEXT,chkrecibido TEXT,observation TEXT,foto TEXT,fecha_registro TEXT,hora_registro TEXT,fotoGuia TEXT,latitud TEXT,longitud TEXT,cliente TEXT,factura TEXT,entrega TEXT,typedispatch TEXT,reasondispatch TEXT)");
        }
        if(oldVersion==4&&newVersion==13) {
            db.execSQL("CREATE TABLE headerdispatchsheet (compania_id text,fuerzatrabajo_id text,usuario_id text,control_id TEXT,asistente_id TEXT,asistente TEXT,placa TEXT,marca TEXT,pesototal TEXT,fechahojadespacho TEXT)");
            db.execSQL("CREATE TABLE detaildispatchsheet (compania_id text,fuerzatrabajo_id text,usuario_id text,control_id TEXT,item_id TEXT,cliente_id TEXT,domembarque_id TEXT,direccion TEXT,factura_id TEXT,entrega_id TEXT,entrega TEXT,factura TEXT,saldo TEXT,estado TEXT, fuerzatrabajo_factura_id TEXT,fuerzatrabajo_factura TEXT,terminopago_id TEXT,terminopago TEXT,peso TEXT,comentariodespacho TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN dispatchdate TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN countsend TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN countsend TEXT");
            db.execSQL("ALTER TABLE cobranzacabecera ADD COLUMN countsend TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN countsend TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN cardname TEXT");

            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN codeSMS TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN docentry TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN lineofbusiness TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN lastpurchase TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN lastpurchase TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN  units TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN saldosincontado TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN chkruta TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN id_trans_mobile TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN amount TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN terminopago_id TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN hora_anterior TEXT");
            db.execSQL("ALTER TABLE documentodeuda ADD COLUMN pymntgroup TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN latitud TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN longitud TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkgeolocation TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkvisitsection TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN cardcode TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN domembarque_id TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN type TEXT");
            db.execSQL("CREATE TABLE visitsection (compania_id text,fuerzatrabajo_id text,usuario_id text,cliente_id TEXT,domembarque_id TEXT,latitudini TEXT,longitudini TEXT,dateini TEXT,timeini TEXT,latitudfin TEXT,longitudfin TEXT,datefin TEXT,timefin TEXT,chkrecibido TEXT)");
            db.execSQL("CREATE TABLE reasondispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,reasondispatch_id TEXT,reasondispatch TEXT,typedispatch_id TEXT)");
            db.execSQL("CREATE TABLE typedispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,typedispatch TEXT)");
            db.execSQL("CREATE TABLE statusdispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,reasondispatch_id TEXT,cliente_id TEXT,factura_id TEXT,entrega_id TEXT,chkrecibido TEXT,observation TEXT,foto TEXT,fecha_registro TEXT,hora_registro TEXT,fotoGuia TEXT,latitud TEXT,longitud TEXT,cliente TEXT,factura TEXT,entrega TEXT,typedispatch TEXT,reasondispatch TEXT)");
        }
        if(oldVersion==13&&newVersion==14) {
            db.execSQL("ALTER TABLE banco ADD COLUMN pagopos TEXT");
            db.execSQL("ALTER TABLE banco ADD COLUMN singledeposit TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN collectioncheck TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN terminopago TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN contado TEXT");

        }

        if(oldVersion==11&&newVersion==14) {
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN latitud TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN longitud TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkgeolocation TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkvisitsection TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN cardcode TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN domembarque_id TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN type TEXT");
            db.execSQL("CREATE TABLE visitsection (compania_id text,fuerzatrabajo_id text,usuario_id text,cliente_id TEXT,domembarque_id TEXT,latitudini TEXT,longitudini TEXT,dateini TEXT,timeini TEXT,latitudfin TEXT,longitudfin TEXT,datefin TEXT,timefin TEXT,chkrecibido TEXT)");
            db.execSQL("CREATE TABLE reasondispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,reasondispatch_id TEXT,reasondispatch TEXT,typedispatch_id TEXT)");
            db.execSQL("CREATE TABLE typedispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,typedispatch TEXT)");
            db.execSQL("CREATE TABLE statusdispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,reasondispatch_id TEXT,cliente_id TEXT,factura_id TEXT,entrega_id TEXT,chkrecibido TEXT,observation TEXT,foto TEXT,fecha_registro TEXT,hora_registro TEXT,fotoGuia TEXT,latitud TEXT,longitud TEXT,cliente TEXT,factura TEXT,entrega TEXT,typedispatch TEXT,reasondispatch TEXT)");
            db.execSQL("ALTER TABLE banco ADD COLUMN pagopos TEXT");
            db.execSQL("ALTER TABLE banco ADD COLUMN singledeposit TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN collectioncheck TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN terminopago TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN contado TEXT");

        }
        if(oldVersion==6&&newVersion==14) {
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN codeSMS TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN docentry TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN lineofbusiness TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN lastpurchase TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN lastpurchase TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN  units TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN saldosincontado TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN chkruta TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN id_trans_mobile TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN amount TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN terminopago_id TEXT");
            db.execSQL("ALTER TABLE visita ADD COLUMN hora_anterior TEXT");
            db.execSQL("ALTER TABLE documentodeuda ADD COLUMN pymntgroup TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN latitud TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN longitud TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkgeolocation TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chkvisitsection TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN cardcode TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN domembarque_id TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN type TEXT");
            db.execSQL("CREATE TABLE visitsection (compania_id text,fuerzatrabajo_id text,usuario_id text,cliente_id TEXT,domembarque_id TEXT,latitudini TEXT,longitudini TEXT,dateini TEXT,timeini TEXT,latitudfin TEXT,longitudfin TEXT,datefin TEXT,timefin TEXT,chkrecibido TEXT)");
            db.execSQL("CREATE TABLE reasondispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,reasondispatch_id TEXT,reasondispatch TEXT,typedispatch_id TEXT)");
            db.execSQL("CREATE TABLE typedispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,typedispatch TEXT)");
            db.execSQL("CREATE TABLE statusdispatch (compania_id text,fuerzatrabajo_id text,usuario_id text,typedispatch_id TEXT,reasondispatch_id TEXT,cliente_id TEXT,factura_id TEXT,entrega_id TEXT,chkrecibido TEXT,observation TEXT,foto TEXT,fecha_registro TEXT,hora_registro TEXT,fotoGuia TEXT,latitud TEXT,longitud TEXT,cliente TEXT,factura TEXT,entrega TEXT,typedispatch TEXT,reasondispatch TEXT)");
            db.execSQL("ALTER TABLE banco ADD COLUMN pagopos TEXT");
            db.execSQL("ALTER TABLE banco ADD COLUMN singledeposit TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN collectioncheck TEXT");
        }
        if(oldVersion==14&&newVersion==15) {
            db.execSQL("ALTER TABLE usuario ADD COLUMN quotation TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN e_signature TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN chkesignature TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN phone TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN latitud TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN longitud TEXT");
            db.execSQL("CREATE TABLE cobranzadetalleSMS (id INTEGER PRIMARY KEY AUTOINCREMENT,recibo TEXT,phone TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT,date TEXT,hour TEXT,chk_send TEXT)");
        }
        if(oldVersion==15&&newVersion==16) {
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN control_id TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN item_id TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN domembarque_id TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN checkintime TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN checkouttime TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN chk_timestatus TEXT");
            db.execSQL("CREATE TABLE quoteeffectiveness (compania_id text,fuerzatrabajo_id text,usuario_id text,code TEXT,type TEXT,quote TEXT,umd TEXT)");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN estado_id TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN motivo TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN motivo_id TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN fotoguia TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN fotolocal TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN idref TEXT");
        }
        if(oldVersion==16&&newVersion==17) {
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN fuerzatrabajo TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerTimeDispatch TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivercode TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN vehiclecode TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN vehicleplate TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivermobile TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivername TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN messageserver TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN census TEXT");
            db.execSQL("ALTER TABLE typedispatch ADD COLUMN statusupdate TEXT");
        }
        if(oldVersion==17&&newVersion==18) {
            db.execSQL("ALTER TABLE visitsection ADD COLUMN idrefitemid TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN legalnumberref TEXT");
        }
        if(oldVersion==18&&newVersion==19) {
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN chk_statusServerDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerStatusDispatch TEXT");
        }
        if(oldVersion==17&&newVersion==19) {
            db.execSQL("ALTER TABLE visitsection ADD COLUMN idrefitemid TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN legalnumberref TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN chk_statusServerDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerStatusDispatch TEXT");
        }
        if(oldVersion==16&&newVersion==19) {
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivercode TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN vehiclecode TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN vehicleplate TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivermobile TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivername TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN messageserver TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN census TEXT");
            db.execSQL("ALTER TABLE typedispatch ADD COLUMN statusupdate TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN idrefitemid TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN legalnumberref TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN fuerzatrabajo TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerTimeDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN chk_statusServerDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerStatusDispatch TEXT");
        }
        if(oldVersion==19&&newVersion==20) {
            db.execSQL("ALTER TABLE lead ADD COLUMN chk_ServerGeolocation TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN MessageServerGeolocation TEXT");
        }

        if(oldVersion==20&&newVersion==21) {
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN datetimeregister TEXT");
        }
        if(oldVersion==21&&newVersion==22) {
            db.execSQL("ALTER TABLE usuario ADD COLUMN oiltaxstatus TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN oiltax TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN liter TEXT");
        }
        if(oldVersion==22&&newVersion==23){
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN deliveryday TEXT");
        }
        if(oldVersion==20&&newVersion==23){
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN datetimeregister TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN oiltaxstatus TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN oiltax TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN liter TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN deliveryday TEXT");
        }

        if(oldVersion==14&&newVersion==23) {

            db.execSQL("ALTER TABLE usuario ADD COLUMN quotation TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN e_signature TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN chkesignature TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN phone TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN latitud TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN longitud TEXT");
            db.execSQL("CREATE TABLE cobranzadetalleSMS (id INTEGER PRIMARY KEY AUTOINCREMENT,recibo TEXT,phone TEXT,compania_id text,fuerzatrabajo_id TEXT,usuario_id TEXT,date TEXT,hour TEXT,chk_send TEXT)");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN control_id TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN item_id TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN domembarque_id TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN checkintime TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN checkouttime TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN chk_timestatus TEXT");
            db.execSQL("CREATE TABLE quoteeffectiveness (compania_id text,fuerzatrabajo_id text,usuario_id text,code TEXT,type TEXT,quote TEXT,umd TEXT)");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN estado_id TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN motivo TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN motivo_id TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN fotoguia TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN fotolocal TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN idref TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivercode TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN vehiclecode TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN vehicleplate TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivermobile TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivername TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN messageserver TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN census TEXT");
            db.execSQL("ALTER TABLE typedispatch ADD COLUMN statusupdate TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN idrefitemid TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN legalnumberref TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN fuerzatrabajo TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerTimeDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN chk_statusServerDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerStatusDispatch TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN chk_ServerGeolocation TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN MessageServerGeolocation TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN datetimeregister TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN oiltaxstatus TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN oiltax TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN liter TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN deliveryday TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN SIGAUS TEXT");
        }

        if(oldVersion==15&&newVersion==23) {
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN phone TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN control_id TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN item_id TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN domembarque_id TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN checkintime TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN checkouttime TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN chk_timestatus TEXT");
            db.execSQL("CREATE TABLE quoteeffectiveness (compania_id text,fuerzatrabajo_id text,usuario_id text,code TEXT,type TEXT,quote TEXT,umd TEXT)");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN estado_id TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN motivo TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN motivo_id TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN fotoguia TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN fotolocal TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN idref TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivercode TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN vehiclecode TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN vehicleplate TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivermobile TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivername TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN messageserver TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN census TEXT");
            db.execSQL("ALTER TABLE typedispatch ADD COLUMN statusupdate TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN idrefitemid TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN legalnumberref TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN fuerzatrabajo TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerTimeDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN chk_statusServerDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerStatusDispatch TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN chk_ServerGeolocation TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN MessageServerGeolocation TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN datetimeregister TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN oiltaxstatus TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN oiltax TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN liter TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN deliveryday TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN SIGAUS TEXT");
        }
        if(oldVersion==23&&newVersion==24)
        {
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN route TEXT");
        }

        if(oldVersion==15&&newVersion==24) {
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN phone TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN control_id TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN item_id TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN domembarque_id TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN checkintime TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN checkouttime TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN chk_timestatus TEXT");
            db.execSQL("CREATE TABLE quoteeffectiveness (compania_id text,fuerzatrabajo_id text,usuario_id text,code TEXT,type TEXT,quote TEXT,umd TEXT)");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN estado_id TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN motivo TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN motivo_id TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN fotoguia TEXT");
            db.execSQL("ALTER TABLE detaildispatchsheet ADD COLUMN fotolocal TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN idref TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivercode TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN vehiclecode TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN vehicleplate TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivermobile TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN drivername TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN messageserver TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN addresscode TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN census TEXT");
            db.execSQL("ALTER TABLE typedispatch ADD COLUMN statusupdate TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN idrefitemid TEXT");
            db.execSQL("ALTER TABLE visitsection ADD COLUMN legalnumberref TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN fuerzatrabajo TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerTimeDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN chk_statusServerDispatch TEXT");
            db.execSQL("ALTER TABLE statusdispatch ADD COLUMN messageServerStatusDispatch TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN chk_ServerGeolocation TEXT");
            db.execSQL("ALTER TABLE lead ADD COLUMN MessageServerGeolocation TEXT");
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN datetimeregister TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN oiltaxstatus TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN oiltax TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN liter TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN deliveryday TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN SIGAUS TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN route TEXT");
        }
        if(oldVersion==20&&newVersion==24){
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN datetimeregister TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN oiltaxstatus TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN oiltax TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN liter TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN deliveryday TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN SIGAUS TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN route TEXT");
        }

        if(oldVersion==24&&newVersion==25){
            db.execSQL("ALTER TABLE usuario ADD COLUMN deliverydateauto TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN collection_salesperson TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN type_description TEXT");
            db.execSQL("ALTER TABLE cobranzacabecera ADD COLUMN collection_salesperson TEXT");
        }

        if(oldVersion==20&&newVersion==25){
            db.execSQL("ALTER TABLE headerdispatchsheet ADD COLUMN datetimeregister TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN oiltaxstatus TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN oiltax TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN liter TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN deliveryday TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN SIGAUS TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN route TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN deliverydateauto TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN collection_salesperson TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN type_description TEXT");
            db.execSQL("ALTER TABLE cobranzacabecera ADD COLUMN collection_salesperson TEXT");
        }
        if(oldVersion==22&&newVersion==25){
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN deliveryday TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN route TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN deliverydateauto TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN collection_salesperson TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN type_description TEXT");
            db.execSQL("ALTER TABLE cobranzacabecera ADD COLUMN collection_salesperson TEXT");
        }

        if(oldVersion==25&&newVersion==26){
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN zipcode TEXT");
            db.execSQL("CREATE TABLE ubigeous (compania_id text,fuerzatrabajo_id text,usuario_id text,code TEXT,U_SYP_DEPA TEXT,U_SYP_PROV TEXT,U_SYP_DIST TEXT,U_VIS_Flete TEXT)");
        }
        if(oldVersion==24&&newVersion==26){
            db.execSQL("ALTER TABLE usuario ADD COLUMN deliverydateauto TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN collection_salesperson TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN type_description TEXT");
            db.execSQL("ALTER TABLE cobranzacabecera ADD COLUMN collection_salesperson TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN zipcode TEXT");
            db.execSQL("CREATE TABLE ubigeous (compania_id text,fuerzatrabajo_id text,usuario_id text,code TEXT,U_SYP_DEPA TEXT,U_SYP_PROV TEXT,U_SYP_DIST TEXT,U_VIS_Flete TEXT)");
        }
        if(oldVersion==26&&newVersion==27){
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIT_VENMOS TEXT");
        }
        if(oldVersion==25&&newVersion==26){
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN zipcode TEXT");
            db.execSQL("CREATE TABLE ubigeous (compania_id text,fuerzatrabajo_id text,usuario_id text,code TEXT,U_SYP_DEPA TEXT,U_SYP_PROV TEXT,U_SYP_DIST TEXT,U_VIS_Flete TEXT)");
            //db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIT_VENMOS TEXT");
        }
        if(oldVersion==24&&newVersion==27){
            db.execSQL("ALTER TABLE usuario ADD COLUMN deliverydateauto TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN collection_salesperson TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN type_description TEXT");
            db.execSQL("ALTER TABLE cobranzacabecera ADD COLUMN collection_salesperson TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN zipcode TEXT");
            db.execSQL("CREATE TABLE ubigeous (compania_id text,fuerzatrabajo_id text,usuario_id text,code TEXT,U_SYP_DEPA TEXT,U_SYP_PROV TEXT,U_SYP_DIST TEXT,U_VIS_Flete TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIT_VENMOS TEXT");
        }
        if(oldVersion==25&&newVersion==27){
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN zipcode TEXT");
            db.execSQL("CREATE TABLE ubigeous (compania_id text,fuerzatrabajo_id text,usuario_id text,code TEXT,U_SYP_DEPA TEXT,U_SYP_PROV TEXT,U_SYP_DIST TEXT,U_VIS_Flete TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIT_VENMOS TEXT");
        }
        if(oldVersion==27&&newVersion==28){
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_Flete TEXT");
        }
        if(oldVersion==28&&newVersion==29){
            db.execSQL("ALTER TABLE cliente ADD COLUMN statuscounted TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN statuscounted TEXT");
        }
        if(oldVersion==27&&newVersion==29){
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_Flete TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN statuscounted TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN statuscounted TEXT");
        }

        if(oldVersion==29&&newVersion==30){
            db.execSQL("CREATE TABLE documentdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,DocEntry TEXT,LineNum TEXT,ItemCode TEXT,Dscription TEXT,Quantity TEXT,LineTotal TEXT,WhsCode TEXT,WhsCode TEXT,LineStatus TEXT,TaxCode TEXT,DiscPrcnt TEXT,DiscPrcnt TEXT,TaxOnly TEXT)");
        }

        if(oldVersion==31&&newVersion==32){
            //Formulario
            db.execSQL("CREATE TABLE form (formcode text,formname text,entrycode text,cardcode TEXT,shiptocode TEXT,docentry TEXT,date TEXT,salesrepcode TEXT,userid TEXT,time TEXT,chk_send TEXT,chk_receive TEXT,msg_server TEXT)");
            //Sección
            db.execSQL("CREATE TABLE formsection (formcode text,sectioncode text,sectionname text,entrycode TEXT)");
            //Questions
            db.execSQL("CREATE TABLE formquestion (sectioncode text,questioncode text,questionname text,entrycode TEXT, codedepence TEXT)");
            //Questions
            db.execSQL("CREATE TABLE formresponse (questioncode text,responsecode text,responsename text,typeresponse TEXT, fileattach TEXT)");
        }
        if(oldVersion==27&&newVersion==32){
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_Flete TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN statuscounted TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN statuscounted TEXT");
            db.execSQL("CREATE TABLE documentdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,DocEntry TEXT,LineNum TEXT,ItemCode TEXT,Dscription TEXT,Quantity TEXT,LineTotal TEXT,WhsCode TEXT,LineStatus TEXT,TaxCode TEXT,DiscPrcnt TEXT,TaxOnly TEXT)");
            db.execSQL("CREATE TABLE questionshead (compania_id text,fuerzatrabajo_id text,usuario_id text,Code TEXT,U_Tipo TEXT,U_Pregunta TEXT,U_Estado TEXT)");
            db.execSQL("CREATE TABLE questionsdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code TEXT,LineId TEXT,U_Estado TEXT,U_Orden TEXT,U_Respuesta TEXT)");
            db.execSQL("CREATE TABLE form (formcode text,formname text,entrycode text,cardcode TEXT,shiptocode TEXT,docentry TEXT,date TEXT,salesrepcode TEXT,userid TEXT,time TEXT,chk_send TEXT,chk_receive TEXT,msg_server TEXT)");
            db.execSQL("CREATE TABLE formsection (formcode text,sectioncode text,sectionname text,entrycode TEXT)");
            db.execSQL("CREATE TABLE formquestion (sectioncode text,questioncode text,questionname text,entrycode TEXT, codedepence TEXT)");
            db.execSQL("CREATE TABLE formresponse (questioncode text,responsecode text,responsename text,typeresponse TEXT, fileattach TEXT)");
        }

        if(oldVersion==32&&newVersion==33){
            //Formulario
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_CompleteOV TEXT");
        }

        if(oldVersion==27&&newVersion==33){
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_Flete TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN statuscounted TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN statuscounted TEXT");
            db.execSQL("CREATE TABLE documentdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,DocEntry TEXT,LineNum TEXT,ItemCode TEXT,Dscription TEXT,Quantity TEXT,LineTotal TEXT,WhsCode TEXT,LineStatus TEXT,TaxCode TEXT,DiscPrcnt TEXT,TaxOnly TEXT)");
            db.execSQL("CREATE TABLE questionshead (compania_id text,fuerzatrabajo_id text,usuario_id text,Code TEXT,U_Tipo TEXT,U_Pregunta TEXT,U_Estado TEXT)");
            db.execSQL("CREATE TABLE questionsdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code TEXT,LineId TEXT,U_Estado TEXT,U_Orden TEXT,U_Respuesta TEXT)");
            db.execSQL("CREATE TABLE form (formcode text,formname text,entrycode text,cardcode TEXT,shiptocode TEXT,docentry TEXT,date TEXT,salesrepcode TEXT,userid TEXT,time TEXT,chk_send TEXT,chk_receive TEXT,msg_server TEXT)");
            db.execSQL("CREATE TABLE formsection (formcode text,sectioncode text,sectionname text,entrycode TEXT)");
            db.execSQL("CREATE TABLE formquestion (sectioncode text,questioncode text,questionname text,entrycode TEXT, codedepence TEXT)");
            db.execSQL("CREATE TABLE formresponse (questioncode text,responsecode text,responsename text,typeresponse TEXT, fileattach TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_CompleteOV TEXT");
        }

        if(oldVersion==26&&newVersion==33){
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIT_VENMOS TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_Flete TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN statuscounted TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN statuscounted TEXT");
            db.execSQL("CREATE TABLE documentdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,DocEntry TEXT,LineNum TEXT,ItemCode TEXT,Dscription TEXT,Quantity TEXT,LineTotal TEXT,WhsCode TEXT,LineStatus TEXT,TaxCode TEXT,DiscPrcnt TEXT,TaxOnly TEXT)");
            db.execSQL("CREATE TABLE questionshead (compania_id text,fuerzatrabajo_id text,usuario_id text,Code TEXT,U_Tipo TEXT,U_Pregunta TEXT,U_Estado TEXT)");
            db.execSQL("CREATE TABLE questionsdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code TEXT,LineId TEXT,U_Estado TEXT,U_Orden TEXT,U_Respuesta TEXT)");
            db.execSQL("CREATE TABLE form (formcode text,formname text,entrycode text,cardcode TEXT,shiptocode TEXT,docentry TEXT,date TEXT,salesrepcode TEXT,userid TEXT,time TEXT,chk_send TEXT,chk_receive TEXT,msg_server TEXT)");
            db.execSQL("CREATE TABLE formsection (formcode text,sectioncode text,sectionname text,entrycode TEXT)");
            db.execSQL("CREATE TABLE formquestion (sectioncode text,questioncode text,questionname text,entrycode TEXT, codedepence TEXT)");
            db.execSQL("CREATE TABLE formresponse (questioncode text,responsecode text,responsename text,typeresponse TEXT, fileattach TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_CompleteOV TEXT");
        }

        if(oldVersion==27&&newVersion==34){
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_Flete TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN statuscounted TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN statuscounted TEXT");
            db.execSQL("CREATE TABLE documentdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,DocEntry TEXT,LineNum TEXT,ItemCode TEXT,Dscription TEXT,Quantity TEXT,LineTotal TEXT,WhsCode TEXT,LineStatus TEXT,TaxCode TEXT,DiscPrcnt TEXT,TaxOnly TEXT)");
            db.execSQL("CREATE TABLE questionshead (compania_id text,fuerzatrabajo_id text,usuario_id text,Code TEXT,U_Tipo TEXT,U_Pregunta TEXT,U_Estado TEXT)");
            db.execSQL("CREATE TABLE questionsdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code TEXT,LineId TEXT,U_Estado TEXT,U_Orden TEXT,U_Respuesta TEXT)");
            db.execSQL("CREATE TABLE form (formcode text,formname text,entrycode text,date TEXT,salesrepcode TEXT,userid TEXT,time TEXT,chk_send TEXT,chk_receive TEXT,msg_server TEXT)");
            db.execSQL("CREATE TABLE formsection (formcode text,sectioncode text,sectionname text,entrycode TEXT)");
            db.execSQL("CREATE TABLE formquestion (sectioncode text,questioncode text,questionname text,entrycode TEXT, codedepence TEXT,questionsanswered TEXT)");
            db.execSQL("CREATE TABLE formresponse (questioncode text,responsecode text,responsename text,typeresponse TEXT, fileattach TEXT,entrycode TEXT, responsechoisse TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_CompleteOV TEXT");
        }

        if(oldVersion==34&&newVersion==35)
        {
            db.execSQL("CREATE TABLE reasonfreetransfer (compania_id text,fuerzatrabajo_id text,usuario_id text,Value TEXT, Dscription TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_TipTransGrat TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN MonedaAdicional TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN MonedaAdicionalContado TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN MonedaAdicionalCredito TEXT");
        }

        if(oldVersion==24&&newVersion==35){
            db.execSQL("ALTER TABLE usuario ADD COLUMN deliverydateauto TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN collection_salesperson TEXT");
            db.execSQL("ALTER TABLE cobranzadetalle ADD COLUMN type_description TEXT");
            db.execSQL("ALTER TABLE cobranzacabecera ADD COLUMN collection_salesperson TEXT");
            db.execSQL("ALTER TABLE direccioncliente ADD COLUMN zipcode TEXT");
            db.execSQL("CREATE TABLE ubigeous (compania_id text,fuerzatrabajo_id text,usuario_id text,code TEXT,U_SYP_DEPA TEXT,U_SYP_PROV TEXT,U_SYP_DIST TEXT,U_VIS_Flete TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIT_VENMOS TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_Flete TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN statuscounted TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN statuscounted TEXT");
            db.execSQL("CREATE TABLE documentdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,DocEntry TEXT,LineNum TEXT,ItemCode TEXT,Dscription TEXT,Quantity TEXT,LineTotal TEXT,WhsCode TEXT,LineStatus TEXT,TaxCode TEXT,DiscPrcnt TEXT,TaxOnly TEXT)");
            db.execSQL("CREATE TABLE questionshead (compania_id text,fuerzatrabajo_id text,usuario_id text,Code TEXT,U_Tipo TEXT,U_Pregunta TEXT,U_Estado TEXT)");
            db.execSQL("CREATE TABLE questionsdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code TEXT,LineId TEXT,U_Estado TEXT,U_Orden TEXT,U_Respuesta TEXT)");
            db.execSQL("CREATE TABLE form (formcode text,formname text,entrycode text,cardcode TEXT,shiptocode TEXT,docentry TEXT,date TEXT,salesrepcode TEXT,userid TEXT,time TEXT,chk_send TEXT,chk_receive TEXT,msg_server TEXT)");
            db.execSQL("CREATE TABLE formsection (formcode text,sectioncode text,sectionname text,entrycode TEXT)");
            db.execSQL("CREATE TABLE formquestion (sectioncode text,questioncode text,questionname text,entrycode TEXT, codedepence TEXT)");
            db.execSQL("CREATE TABLE formresponse (questioncode text,responsecode text,responsename text,typeresponse TEXT, fileattach TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_CompleteOV TEXT");
            db.execSQL("CREATE TABLE reasonfreetransfer (compania_id text,fuerzatrabajo_id text,usuario_id text,Value TEXT, Dscription TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_TipTransGrat TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN MonedaAdicional TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN MonedaAdicionalContado TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN MonedaAdicionalCredito TEXT");
        }

        if(oldVersion==35&&newVersion==36)
        {
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN typevisit TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN quotationamount TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chk_quotation TEXT");
        }

        if(oldVersion==36&&newVersion==37)
        {
            db.execSQL("ALTER TABLE usuario ADD COLUMN deliveryrefusedmoney TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN status TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN sendvisits TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN customerwhitelist TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN customerwhitelist TEXT");
            db.execSQL("ALTER TABLE documentodeuda ADD COLUMN additionaldiscount TEXT");

        }

        if(oldVersion==34&&newVersion==37)
        {
            db.execSQL("CREATE TABLE reasonfreetransfer (compania_id text,fuerzatrabajo_id text,usuario_id text,Value TEXT, Dscription TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_TipTransGrat TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN MonedaAdicional TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN MonedaAdicionalContado TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN MonedaAdicionalCredito TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN typevisit TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN quotationamount TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chk_quotation TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN deliveryrefusedmoney TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN status TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN sendvisits TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN customerwhitelist TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN customerwhitelist TEXT");
            db.execSQL("ALTER TABLE documentodeuda ADD COLUMN additionaldiscount TEXT");
        }
        if(oldVersion==37&&newVersion==38)
        {
            db.execSQL("ALTER TABLE usuario ADD COLUMN sendvalidations TEXT");
        }
        if(oldVersion==38&&newVersion==39)
        {
            db.execSQL("ALTER TABLE promocioncabecera ADD COLUMN cantidad_maxima TEXT");
            db.execSQL("ALTER TABLE promocioncabecera ADD COLUMN tipo_malla TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN status TEXT");
        }
        if(oldVersion==39&&newVersion==40)
        {
            db.execSQL("CREATE TABLE pricelisthead (compania_id text,fuerzatrabajo_id text,usuario_id text,ListNum TEXT,ListName TEXT,U_VIS_PercentageIncrease TEXT)");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN CodePriceListCash TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN CodePriceListCredit TEXT");
        }
        if(oldVersion==40&&newVersion==41)
        {
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_DiscountPercent TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_ReasonDiscountPercent TEXT");
            //Business Layer Head
            db.execSQL("CREATE TABLE businesslayerhead (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,Name text,U_VIS_Objetive text,U_VIS_VariableType text,U_VIS_Variable text,U_VIS_Trigger text,U_VIS_TriggerType text,U_VIS_Active text,U_VIS_ValidFrom text,U_VIS_ValidUntil text )");
            //Business Layer Detail
            db.execSQL("CREATE TABLE businesslayerdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,LineId text,U_VIS_Type text,U_VIS_Object text,U_VIS_Action text)");
            //Object App
            db.execSQL("CREATE TABLE object (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,Name text)");
        }

        if(oldVersion==35&&newVersion==41)
        {
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN typevisit TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN quotationamount TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chk_quotation TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN deliveryrefusedmoney TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN status TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN sendvisits TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN customerwhitelist TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN customerwhitelist TEXT");
            db.execSQL("ALTER TABLE documentodeuda ADD COLUMN additionaldiscount TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN sendvalidations TEXT");
            db.execSQL("ALTER TABLE promocioncabecera ADD COLUMN cantidad_maxima TEXT");
            db.execSQL("ALTER TABLE promocioncabecera ADD COLUMN tipo_malla TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN status TEXT");
            db.execSQL("CREATE TABLE pricelisthead (compania_id text,fuerzatrabajo_id text,usuario_id text,ListNum TEXT,ListName TEXT,U_VIS_PercentageIncrease TEXT)");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN CodePriceListCash TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN CodePriceListCredit TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_DiscountPercent TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_ReasonDiscountPercent TEXT");
            db.execSQL("CREATE TABLE businesslayerhead (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,Name text,U_VIS_Objetive text,U_VIS_VariableType text,U_VIS_Variable text,U_VIS_Trigger text,U_VIS_TriggerType text,U_VIS_Active text,U_VIS_ValidFrom text,U_VIS_ValidUntil text )");
            db.execSQL("CREATE TABLE businesslayerdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,LineId text,U_VIS_Type text,U_VIS_Object text,U_VIS_Action text)");
            db.execSQL("CREATE TABLE object (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,Name text)");
        }
        if(oldVersion==41&&newVersion==42)
        {
            //Business Layer Head
            db.execSQL("CREATE TABLE businesslayersalesdetailHeader (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text ,Object text,Name text, Action text )");
            //Business Layer Head
            db.execSQL("CREATE TABLE businesslayersalesdetailDetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text ,LineId text,RangeActive Text,Object text ,TypeObject text,ValueMin text,ValueMax text,Field text,Variable text )");
        }

        if(oldVersion==33&&newVersion==42){
            //Formulario
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_CompleteOV TEXT");
            db.execSQL("CREATE TABLE reasonfreetransfer (compania_id text,fuerzatrabajo_id text,usuario_id text,Value TEXT, Dscription TEXT)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_TipTransGrat TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN MonedaAdicional TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN MonedaAdicionalContado TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN MonedaAdicionalCredito TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN typevisit TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN quotationamount TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN chk_quotation TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN deliveryrefusedmoney TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN status TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN sendvisits TEXT");
            db.execSQL("ALTER TABLE cliente ADD COLUMN customerwhitelist TEXT");
            db.execSQL("ALTER TABLE rutavendedor ADD COLUMN customerwhitelist TEXT");
            db.execSQL("ALTER TABLE documentodeuda ADD COLUMN additionaldiscount TEXT");
            db.execSQL("ALTER TABLE usuario ADD COLUMN sendvalidations TEXT");
            db.execSQL("ALTER TABLE promocioncabecera ADD COLUMN cantidad_maxima TEXT");
            db.execSQL("ALTER TABLE promocioncabecera ADD COLUMN tipo_malla TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN status TEXT");
            db.execSQL("CREATE TABLE pricelisthead (compania_id text,fuerzatrabajo_id text,usuario_id text,ListNum TEXT,ListName TEXT,U_VIS_PercentageIncrease TEXT)");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN CodePriceListCash TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN CodePriceListCredit TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_DiscountPercent TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_ReasonDiscountPercent TEXT");
            db.execSQL("CREATE TABLE businesslayerhead (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,Name text,U_VIS_Objetive text,U_VIS_VariableType text,U_VIS_Variable text,U_VIS_Trigger text,U_VIS_TriggerType text,U_VIS_Active text,U_VIS_ValidFrom text,U_VIS_ValidUntil text )");
            db.execSQL("CREATE TABLE businesslayerdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,LineId text,U_VIS_Type text,U_VIS_Object text,U_VIS_Action text)");
            db.execSQL("CREATE TABLE object (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,Name text)");
            db.execSQL("CREATE TABLE businesslayersalesdetailHeader (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text ,Object text,Name text, Action text )");
            db.execSQL("CREATE TABLE businesslayersalesdetailDetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text ,LineId text,RangeActive Text,Object text ,TypeObject text,ValueMin text,ValueMax text,Field text,Variable text )");
        }

        if(oldVersion==42&&newVersion==43)
        {
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_MOTAPLDESC TEXT");
        }

        if(oldVersion==43&&newVersion==44)
        {
            //Almacenes
            db.execSQL("CREATE TABLE warehouse (WhsCode text,WhsName text,PriceListCash text,PriceListCredit text,U_VIST_SUCUSU text)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIST_SUCUSU TEXT");
        }
        if(oldVersion==38&&newVersion==44)
        {
            db.execSQL("ALTER TABLE usuario ADD COLUMN U_VIS_ManagementType TEXT");
            db.execSQL("ALTER TABLE promocioncabecera ADD COLUMN cantidad_maxima TEXT");
            db.execSQL("ALTER TABLE promocioncabecera ADD COLUMN tipo_malla TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN status TEXT");
            db.execSQL("CREATE TABLE pricelisthead (compania_id text,fuerzatrabajo_id text,usuario_id text,ListNum TEXT,ListName TEXT,U_VIS_PercentageIncrease TEXT)");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN CodePriceListCash TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN CodePriceListCredit TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_DiscountPercent TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_ReasonDiscountPercent TEXT");
            //42
            db.execSQL("CREATE TABLE businesslayerhead (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,Name text,U_VIS_Objetive text,U_VIS_VariableType text,U_VIS_Variable text,U_VIS_Trigger text,U_VIS_TriggerType text,U_VIS_Active text,U_VIS_ValidFrom text,U_VIS_ValidUntil text )");
            db.execSQL("CREATE TABLE businesslayerdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,LineId text,U_VIS_Type text,U_VIS_Object text,U_VIS_Action text)");
            db.execSQL("CREATE TABLE object (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,Name text)");
            db.execSQL("CREATE TABLE businesslayersalesdetailHeader (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text ,Object text,Name text, Action text )");
            db.execSQL("CREATE TABLE businesslayersalesdetailDetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text ,LineId text,RangeActive Text,Object text ,TypeObject text,ValueMin text,ValueMax text,Field text,Variable text )");
            //43
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_MOTAPLDESC TEXT");
            //44
            db.execSQL("CREATE TABLE warehouse (WhsCode text,WhsName text,PriceListCash text,PriceListCredit text,U_VIST_SUCUSU text)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIST_SUCUSU TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN CodAlmacen TEXT");
        }

        if(oldVersion==44&&newVersion==45)
        {
            db.execSQL("CREATE TABLE sellerroute (CardCode text,Address text,Chk_Visit text,Chk_Pedido text,Chk_Cobranza text,Chk_Ruta text,FechaRuta text)");
        }

        if(oldVersion==45&&newVersion==46)
        {
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN DocEntry TEXT");
        }
        if(oldVersion==38&&newVersion==46)
        {
            db.execSQL("ALTER TABLE usuario ADD COLUMN U_VIS_ManagementType TEXT");
            db.execSQL("ALTER TABLE promocioncabecera ADD COLUMN cantidad_maxima TEXT");
            db.execSQL("ALTER TABLE promocioncabecera ADD COLUMN tipo_malla TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN status TEXT");
            db.execSQL("CREATE TABLE pricelisthead (compania_id text,fuerzatrabajo_id text,usuario_id text,ListNum TEXT,ListName TEXT,U_VIS_PercentageIncrease TEXT)");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN CodePriceListCash TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN CodePriceListCredit TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_DiscountPercent TEXT");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_ReasonDiscountPercent TEXT");
            //42
            db.execSQL("CREATE TABLE businesslayerhead (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,Name text,U_VIS_Objetive text,U_VIS_VariableType text,U_VIS_Variable text,U_VIS_Trigger text,U_VIS_TriggerType text,U_VIS_Active text,U_VIS_ValidFrom text,U_VIS_ValidUntil text )");
            db.execSQL("CREATE TABLE businesslayerdetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,LineId text,U_VIS_Type text,U_VIS_Object text,U_VIS_Action text)");
            db.execSQL("CREATE TABLE object (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text,Name text)");
            db.execSQL("CREATE TABLE businesslayersalesdetailHeader (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text ,Object text,Name text, Action text )");
            db.execSQL("CREATE TABLE businesslayersalesdetailDetail (compania_id text,fuerzatrabajo_id text,usuario_id text,Code text ,LineId text,RangeActive Text,Object text ,TypeObject text,ValueMin text,ValueMax text,Field text,Variable text )");
            //43
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIS_MOTAPLDESC TEXT");
            //44
            db.execSQL("CREATE TABLE warehouse (WhsCode text,WhsName text,PriceListCash text,PriceListCredit text,U_VIST_SUCUSU text)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN U_VIST_SUCUSU TEXT");
            db.execSQL("ALTER TABLE listapreciodetalle ADD COLUMN CodAlmacen TEXT");
            //45
            db.execSQL("CREATE TABLE sellerroute (CardCode text,Address text,Chk_Visit text,Chk_Pedido text,Chk_Cobranza text,Chk_Ruta text,FechaRuta text)");
            //46
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN DocEntry TEXT");
        }
        if(oldVersion==44&&newVersion==46)
        {
            db.execSQL("CREATE TABLE sellerroute (CardCode text,Address text,Chk_Visit text,Chk_Pedido text,Chk_Cobranza text,Chk_Ruta text,FechaRuta text)");
            db.execSQL("ALTER TABLE ordenventacabecera ADD COLUMN DocEntry TEXT");
        }
    }

    public  static void deleteDatabase(Context mContext){
        mContext.deleteDatabase("dbcobranzas");
    }
}

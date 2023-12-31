package com.vistony.salesforce.Controller.Utilitario;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Dao.SQLite.QuoteEffectivenessSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.SellerRouteSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Dao.SQLite.VisitaSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuoteEffectivenessEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SummaryofeffectivenessEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import harmony.java.awt.Color;

public class ResumenDiarioPDF extends AppCompatActivity {
    private final static String NOMBRE_DIRECTORIO = "ResumenDiario";
    private final static String ETIQUETA_ERROR = "ERROR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
        } else {

        }
    }

    public void generarPdf(Context context, List<HistoricContainerSalesEntity> resumenDiarioEntityList, String fecha, List<SummaryofeffectivenessEntity> summaryofeffectivenessEntityList) {
        // Creamos el documento.
        RutaVendedorSQLiteDao rutaVendedorSQLiteDao = new RutaVendedorSQLiteDao(context);
        SellerRouteSQLiteDao sellerRouteSQLiteDao = new SellerRouteSQLiteDao(context);
        VisitaSQLite visitaSQLite = new VisitaSQLite(context);
        Rectangle pagina = new Rectangle(
                36, 36,
                //559
                650
                //, 806
                , 1120
                //PageSize.ARCH_A
        );
        Document documento = new Document(pagina);

        ArrayList<String> ObjList = new ArrayList<>();
        String fechasap = "", CardName = "", LicTradNum = "", Street = "", Ubigeo = "", Phone = "";
        Set<String> listaCorrelativo;
        int countVisitTypeCOB=0,countVisitTypeOV=0,cantvisit=0;
        float amountVisitTypeCOB=0,amountVisitTypeOV=0;
        fechasap=fecha;

        try {

        String countvisitroute="",countclientsroute="",countclientsbalanceroute="",countcollectionsroute="",amountcollectionsroute=""
               ,countsalesordersroute="",amountsalesordersroute="",effectivenessvisitroute="",effectivenessasalesordersroute="",effectivenesscollectionsroute=""
                ,countvisitnoroute="",countcollectionsnoroute="",amountcollectionsnoroute="",countsalesordersnoroute="",amountsalesordernoroute="",conectivity=""
                ,countquotationroute="",amountquotationroute="",countquotationnoroute="",amountquotationnoroute="";
            //Log.e("REOS","ResumenDiarioPDF-generarPdf-summaryofeffectivenessEntityList.size.()"+summaryofeffectivenessEntityList.size());
            if (summaryofeffectivenessEntityList==null)
            {
                try{
                    conectivity="OFFLINE";
                    summaryofeffectivenessEntityList=new ArrayList<>();
                    Log.e("REOS","DocumentCobranzaPDF.generarPdf.Ingresoifllenado_summaryofeffectivenessEntityList:");
                    //Agregar en Ruta
                    SummaryofeffectivenessEntity summaryofeffectivenessEntity=new SummaryofeffectivenessEntity();
                    summaryofeffectivenessEntity.route="Y";
                    if(visitaSQLite.getCountVisitWithTypeVisit(fechasap,"1")>rutaVendedorSQLiteDao.ObtenerCantidadVisitadosRutaVendedor(fechasap,"1"))
                    {
                        summaryofeffectivenessEntity.visits=String.valueOf (visitaSQLite.getCountVisitWithTypeVisit(fechasap,"1"));
                    }
                    else {
                        summaryofeffectivenessEntity.visits=String.valueOf (rutaVendedorSQLiteDao.ObtenerCantidadVisitadosRutaVendedor(fechasap,"1"));
                    }
                    //summaryofeffectivenessEntity.clients=String.valueOf(rutaVendedorSQLiteDao.ObtenerCantidadRutaVendedor(fechasap,"1"));
                    summaryofeffectivenessEntity.clients=String.valueOf(sellerRouteSQLiteDao.getCountSellerRouteForDate(fechasap));
                    //summaryofeffectivenessEntity.clients=String.valueOf(rutaVendedorSQLiteDao.ObtenerCantidadRutaVendedor(fechasap,"1"));
                    //summaryofeffectivenessEntity.balanceclients=String.valueOf(rutaVendedorSQLiteDao.GetCountClientwithBalance(fechasap,"1"));
                    summaryofeffectivenessEntity.balanceclients=sellerRouteSQLiteDao.getCountSellerRouteForDateBalance(fechasap).toString();
                    summaryofeffectivenessEntity.collections =String.valueOf(visitaSQLite.getCountVisitWithTypeVisitCOB(fechasap,"1","02"));
                    summaryofeffectivenessEntity.amountcollections =String.valueOf(visitaSQLite.getSumVisitWithTypeCOB(fechasap,"1","02"));

                    if(visitaSQLite.getCountVisitWithTypeOVCOB(fechasap,"1","01")>0)
                    {
                        summaryofeffectivenessEntity.salesorders=String.valueOf(visitaSQLite.getCountVisitWithTypeOVCOB(fechasap,"1","01"));
                    }else
                    {
                        summaryofeffectivenessEntity.salesorders=String.valueOf(rutaVendedorSQLiteDao.ObtenerCantidadPedidoRutaVendedor(fechasap,"1"));
                    }

                    if(visitaSQLite.getSumVisitWithTypeOVCOB(fechasap,"1","01")>0)
                    {
                        summaryofeffectivenessEntity.amountsalesorders=String.valueOf(visitaSQLite.getSumVisitWithTypeOVCOB(fechasap,"1","01"));
                    }else
                    {
                        summaryofeffectivenessEntity.amountsalesorders=String.valueOf(rutaVendedorSQLiteDao.ObtenerMontoPedidoRutaVendedor(SesionEntity.compania_id,fechasap,"1"));
                    }

                    if(visitaSQLite.getCountVisitWithTypeOVCOB(fechasap,"1","12")>0)
                    {
                        summaryofeffectivenessEntity.countquotation=String.valueOf(visitaSQLite.getCountVisitWithTypeOVCOB(fechasap,"1","12"));
                    }else
                    {
                        summaryofeffectivenessEntity.countquotation=String.valueOf(rutaVendedorSQLiteDao.getQuantityQuotationRoute(fechasap,"1"));
                    }

                    if(visitaSQLite.getSumVisitWithTypeOVCOB(fechasap,"1","12")>0)
                    {
                        summaryofeffectivenessEntity.amountquotation=String.valueOf(visitaSQLite.getSumVisitWithTypeOVCOB(fechasap,"1","12"));
                    }else
                    {
                        summaryofeffectivenessEntity.amountquotation= String.valueOf(rutaVendedorSQLiteDao.getAmountQuotationRoute(SesionEntity.compania_id,fechasap,"1"));
                    }

                    summaryofeffectivenessEntity.orderseffectiveness =Induvis.getAmountRouteeffectiveness(
                            Double.parseDouble(summaryofeffectivenessEntity.salesorders),
                            //rutaVendedorSQLiteDao.ObtenerCantidadRutaVendedor(fechasap,"1")
                            sellerRouteSQLiteDao.getCountSellerRouteForDate(fechasap)
                    );
                    summaryofeffectivenessEntity.collectionseffectiveness =Induvis.getAmountRouteeffectiveness(
                            Double.parseDouble(summaryofeffectivenessEntity.collections),
                            //rutaVendedorSQLiteDao.GetCountClientwithBalance(fechasap,"1")
                            sellerRouteSQLiteDao.getCountSellerRouteForDateBalance(fechasap)
                    );
                    summaryofeffectivenessEntity.visitseffectiveness =Induvis.getAmountRouteeffectiveness(
                            Double.parseDouble(summaryofeffectivenessEntity.visits),
                            //rutaVendedorSQLiteDao.ObtenerCantidadRutaVendedor(fechasap,"1")
                            sellerRouteSQLiteDao.getCountSellerRouteForDate(fechasap)
                    );
                    summaryofeffectivenessEntityList.add(summaryofeffectivenessEntity);
                    //Agregar Fuera de Ruta
                    summaryofeffectivenessEntity=new SummaryofeffectivenessEntity();
                    summaryofeffectivenessEntity.route="N";
                    if(visitaSQLite.getCountVisitWithTypeOVCOB(fechasap,"0","02")>0)
                    {
                        summaryofeffectivenessEntity.collections =String.valueOf(visitaSQLite.getCountVisitWithTypeOVCOB(fechasap,"0","02"));
                    }else {
                        summaryofeffectivenessEntity.collections =String.valueOf( rutaVendedorSQLiteDao.ObtenerCantidadCobranzaRutaVendedor(fechasap,"0"));
                    }

                    if(visitaSQLite.getSumVisitWithTypeOVCOB(fechasap,"0","02")>0)
                    {
                        summaryofeffectivenessEntity.amountcollections = String.valueOf( visitaSQLite.getSumVisitWithTypeOVCOB(fechasap,"0","02"));
                    }else {
                        summaryofeffectivenessEntity.amountcollections = String.valueOf( rutaVendedorSQLiteDao.ObtenerCantidadCobranzaRutaVendedor(fechasap,"0"));
                    }
                    if(visitaSQLite.getCountVisitWithTypeOVCOB(fechasap,"0","01")>0)
                    {
                        summaryofeffectivenessEntity.salesorders =String.valueOf(visitaSQLite.getCountVisitWithTypeOVCOB(fechasap,"0","01"));
                    }else {
                        summaryofeffectivenessEntity.salesorders =String.valueOf(rutaVendedorSQLiteDao.ObtenerCantidadPedidoRutaVendedor(fechasap,"0"));
                    }

                    if(visitaSQLite.getSumVisitWithTypeOVCOB(fechasap,"0","01")>0)
                    {
                        summaryofeffectivenessEntity.amountsalesorders= String.valueOf(visitaSQLite.getSumVisitWithTypeOVCOB(fechasap,"0","01"));
                    }else {
                        summaryofeffectivenessEntity.amountsalesorders= String.valueOf(rutaVendedorSQLiteDao.ObtenerMontoPedidoRutaVendedor(SesionEntity.compania_id,fechasap,"0"));
                    }

                    if(visitaSQLite.getCountVisitWithTypeVisit(fechasap,"0")>0)
                    {
                        summaryofeffectivenessEntity.visits =  String.valueOf(visitaSQLite.getCountVisitWithTypeVisit(fechasap,"0"));
                    }else {
                        summaryofeffectivenessEntity.visits =  String.valueOf(rutaVendedorSQLiteDao.ObtenerCantidadVisitadosRutaVendedor(fechasap,"0"));
                    }

                    if(visitaSQLite.getCountVisitWithTypeOVCOB(fechasap,"0","12")>0)
                    {
                        summaryofeffectivenessEntity.countquotation =String.valueOf(visitaSQLite.getCountVisitWithTypeOVCOB(fechasap,"0","12"));
                    }else {
                        summaryofeffectivenessEntity.countquotation =String.valueOf(rutaVendedorSQLiteDao.getQuantityQuotationRoute(fechasap,"0"));
                    }

                    if(visitaSQLite.getSumVisitWithTypeOVCOB(fechasap,"0","12")>0)
                    {
                        summaryofeffectivenessEntity.amountquotation= String.valueOf(visitaSQLite.getSumVisitWithTypeOVCOB(fechasap,"0","12"));
                    }else {
                        summaryofeffectivenessEntity.amountquotation= String.valueOf(rutaVendedorSQLiteDao.getAmountQuotationRoute(SesionEntity.compania_id,fechasap,"0"));
                    }


                    summaryofeffectivenessEntityList.add(summaryofeffectivenessEntity);
                    Log.e("REOS","DocumentCobranzaPDF.generarPdf.Ingresoifllenadosalida_summaryofeffectivenessEntityList-noroute:");
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e("REOS","DocumentCobranzaPDF.generarPdf.e:" + e.toString());
                }
            }else {
                conectivity="ONLINE";
            }

                for (int j = 0; j < summaryofeffectivenessEntityList.size(); j++) {
                    if (summaryofeffectivenessEntityList.get(j).getRoute().equals("Y")) {
                        countvisitroute = (summaryofeffectivenessEntityList.get(j).getVisits());
                        countclientsroute = (summaryofeffectivenessEntityList.get(j).getClients());
                        countclientsbalanceroute = (summaryofeffectivenessEntityList.get(j).getBalanceclients());
                        countcollectionsroute = (summaryofeffectivenessEntityList.get(j).getCollections());
                        amountcollectionsroute = (summaryofeffectivenessEntityList.get(j).getAmountcollections());
                        countsalesordersroute = (summaryofeffectivenessEntityList.get(j).getSalesorders());
                        amountsalesordersroute = (summaryofeffectivenessEntityList.get(j).getAmountsalesorders());
                        effectivenessvisitroute = (summaryofeffectivenessEntityList.get(j).getVisitseffectiveness());
                        effectivenessasalesordersroute = (summaryofeffectivenessEntityList.get(j).getOrderseffectiveness());
                        effectivenesscollectionsroute = (summaryofeffectivenessEntityList.get(j).getCollectionseffectiveness());
                        countquotationroute = summaryofeffectivenessEntityList.get(j).getCountquotation();
                        amountquotationroute = summaryofeffectivenessEntityList.get(j).getAmountquotation();
                    } else {
                        countvisitnoroute = (summaryofeffectivenessEntityList.get(j).getVisits());
                        countcollectionsnoroute = (summaryofeffectivenessEntityList.get(j).getCollections());
                        amountcollectionsnoroute = (summaryofeffectivenessEntityList.get(j).getAmountcollections());
                        countsalesordersnoroute = (summaryofeffectivenessEntityList.get(j).getSalesorders());
                        amountsalesordernoroute = (summaryofeffectivenessEntityList.get(j).getAmountsalesorders());
                        countquotationnoroute = summaryofeffectivenessEntityList.get(j).getCountquotation();
                        countquotationnoroute=summaryofeffectivenessEntityList.get(j).getAmountquotation();
                    }

            }



        fechasap=fecha;
        listaCorrelativo = new HashSet<String>(ObjList);
        Object[] ObjArrayListaCorrelativo = listaCorrelativo.toArray();

            File f = crearFichero(fechasap+".pdf");

            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream(
                    f.getAbsolutePath());

            // Asociamos el flujo que acabamos de crear al documento.
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);
            Calendar now = GregorianCalendar.getInstance();
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);

            documento.open();

            Font font = FontFactory.getFont(FontFactory.HELVETICA, 28,
                    Font.BOLD, Color.black);
            Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 20,
                    Font.BOLD, Color.black);

            Font font3 = FontFactory.getFont(FontFactory.HELVETICA, 16,
                    Font.NORMAL, Color.black);
            Font font4 = FontFactory.getFont(FontFactory.HELVETICA, 32,
                    Font.BOLD, Color.black);
            Font font5 = FontFactory.getFont(FontFactory.HELVETICA, 16,
                    Font.NORMAL, Color.black);
            Font font6 = FontFactory.getFont(FontFactory.HELVETICA, 16,
                    Font.BOLD, Color.black);
            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.
            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.
            Bitmap bitmap=null;

            //bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo_negro_vistony);
            UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
            UsuarioSQLite usuarioSQLite=new UsuarioSQLite(context);
            ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

            //set image company
            switch (ObjUsuario.compania_id) {
                case "01":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo_negro_vistony);
                    break;
                case "C011":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo_bluker_negro);
                    break;
                case "13":
                    bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.logo_rofalab_negro2);
                    break;
                default:
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo_negro_vistony);
                    break;
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100 ,stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            imagen.setAlignment(Element.ALIGN_CENTER);
            documento.add(imagen);

            PdfPTable tbldireccion = new PdfPTable(1);
            tbldireccion.setWidthPercentage(100);
            PdfPCell cellTable = null;
            Log.d("REOS","DocumentCobranzaPDF.generarPdf.BuildConfig.FLAVOR:" + BuildConfig.FLAVOR);
            cellTable=new PdfPCell(new Phrase(Induvis.getInformation(BuildConfig.FLAVOR),font3));
            Log.d("REOS","DocumentCobranzaPDF.generarPdf.Induvis.getInformation(BuildConfig.FLAVOR):" + Induvis.getInformation(BuildConfig.FLAVOR));
            cellTable.disableBorderSide(Rectangle.BOX);
            cellTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbldireccion.addCell(cellTable);
            cellTable = new PdfPCell(new Phrase(context.getResources().getString(R.string.query_summary_diary) ,font2));
            cellTable.disableBorderSide(Rectangle.BOX);
            cellTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbldireccion.addCell(cellTable);
            documento.add(tbldireccion);

            PdfPTable tbllblvendedor = new PdfPTable(1);
            tbllblvendedor.setWidthPercentage(100);
            PdfPCell cellTablelblvendedor = null;
            cellTablelblvendedor=new PdfPCell(new Phrase(context.getResources().getString(R.string.separator_short)+context.getResources().getString(R.string.seller_data).toUpperCase()+context.getResources().getString(R.string.separator_short) ,font6));
            cellTablelblvendedor.disableBorderSide(Rectangle.BOX);
            cellTablelblvendedor.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbllblvendedor.addCell(cellTablelblvendedor);
            documento.add(tbllblvendedor);

            PdfPTable tblvendedor = new PdfPTable(2);
            tbldireccion.setWidthPercentage(100);
            PdfPCell cellTablevendedor = null;
            cellTablevendedor=new PdfPCell(new Phrase(context.getResources().getString(R.string.code) ,font6));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor=new PdfPCell(new Phrase(SesionEntity.fuerzatrabajo_id,font3));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor = new PdfPCell(new Phrase(context.getResources().getString(R.string.name),font6));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor = new PdfPCell(new Phrase(SesionEntity.nombrefuerzadetrabajo,font3));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor = new PdfPCell(new Phrase(context.getResources().getString(R.string.date),font6));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor = new PdfPCell(new Phrase(Induvis.getDate(BuildConfig.FLAVOR, fechasap) ,font3));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor = new PdfPCell(new Phrase(context.getResources().getString(R.string.hour),font6));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor = new PdfPCell(new Phrase(Induvis.getTime(BuildConfig.FLAVOR, obtenerHoraActual()) ,font3));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor = new PdfPCell(new Phrase(context.getResources().getString(R.string.conectivity),font6));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor = new PdfPCell(new Phrase(conectivity ,font3));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            documento.add(tblvendedor);

            PdfPTable tbllblvariable = new PdfPTable(1);
            tbllblvariable.setWidthPercentage(100);
            PdfPCell cellTablelblvariable= null;
            cellTablelblvariable=new PdfPCell(new Phrase(context.getResources().getString(R.string.separator_short)+context.getResources().getString(R.string.product_focus).toUpperCase()+context.getResources().getString(R.string.separator_short),font6));
            cellTablelblvariable.disableBorderSide(Rectangle.BOX);
            cellTablelblvariable.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbllblvariable.addCell(cellTablelblvariable);
            documento.add(tbllblvariable);
            //float[] columnWidthsCabecera = {1.5f,10f,7f};
            PdfPTable tblCabecera = new PdfPTable(5);
            tblCabecera.setWidthPercentage(100);
            PdfPCell cellCabecera = null;
            cellCabecera = new PdfPCell(new Phrase(context.getResources().getString(R.string.item),font6));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase(context.getResources().getString(R.string.variable),font6));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase(context.getResources().getString(R.string.umd),font6));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase(context.getResources().getString(R.string.quotas),font6));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase(context.getResources().getString(R.string.gallon)+" "+context.getResources().getString(R.string.day)  ,font6));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblCabecera.addCell(cellCabecera);

            //Log.d("REOS","DocumentCobranzaPDF.generarPdf.resumenDiarioEntityList.size:" + resumenDiarioEntityList.size());
            if(resumenDiarioEntityList!=null)
            {
                for(int i=0;i<resumenDiarioEntityList.size();i++)
                {
                    cellCabecera = new PdfPCell(new Phrase(String.valueOf(i+1),font3));
                    cellCabecera.disableBorderSide(Rectangle.BOX);
                    cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tblCabecera.addCell(cellCabecera);
                    cellCabecera = new PdfPCell(new Phrase(resumenDiarioEntityList.get(i).getVariable(),font3));
                    cellCabecera.disableBorderSide(Rectangle.BOX);
                    cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tblCabecera.addCell(cellCabecera);
                    cellCabecera = new PdfPCell(new Phrase(resumenDiarioEntityList.get(i).getUmd(),font3));
                    cellCabecera.disableBorderSide(Rectangle.BOX);
                    cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tblCabecera.addCell(cellCabecera);
                    cellCabecera = new PdfPCell(new Phrase((resumenDiarioEntityList.get(i).getMontototal()),font3));
                    cellCabecera.disableBorderSide(Rectangle.BOX);
                    cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tblCabecera.addCell(cellCabecera);
                    cellCabecera = new PdfPCell(new Phrase((resumenDiarioEntityList.get(i).getSumgaloun()),font3));
                    cellCabecera.disableBorderSide(Rectangle.BOX);
                    cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tblCabecera.addCell(cellCabecera);

                }
                tblCabecera.addCell(cellCabecera);
                documento.add(tblCabecera);
            }else {

                PdfPTable tblNodata = new PdfPTable(1);
                tblNodata.setWidthPercentage(100);
                PdfPCell celltblNodata = null;
                celltblNodata=new PdfPCell(new Phrase(context.getResources().getString(R.string.mse_module_availaible) ,font3));
                celltblNodata.disableBorderSide(Rectangle.BOX);
                celltblNodata.setHorizontalAlignment(Element.ALIGN_CENTER);
                tblNodata.addCell(celltblNodata);
                documento.add(tblNodata);

            }


            PdfPTable tbclientes = new PdfPTable(1);
            tbclientes.setWidthPercentage(100);
            PdfPCell cellTableclientes = null;
            cellTableclientes=new PdfPCell(new Phrase(context.getResources().getString(R.string.separator)+context.getResources().getString(R.string.clients).toUpperCase()+context.getResources().getString(R.string.separator) ,font6));
            cellTableclientes.disableBorderSide(Rectangle.BOX);
            cellTableclientes.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbclientes.addCell(cellTableclientes);
            documento.add(tbclientes);


            PdfPTable tblvisitados = new PdfPTable(2);
            tblvisitados.setWidthPercentage(100);
            PdfPCell  cellTablevisitados= null;
            cellTablevisitados=new PdfPCell(new Phrase(context.getResources().getString(R.string.visits),font6));
            cellTablevisitados.disableBorderSide(Rectangle.BOX);
            cellTablevisitados.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvisitados.addCell(cellTablevisitados);
            cellTablevisitados=new PdfPCell(new Phrase(String.valueOf(
                    //cantvisit
                    countvisitroute
            ),font3));
            cellTablevisitados.disableBorderSide(Rectangle.BOX);
            cellTablevisitados.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvisitados.addCell(cellTablevisitados);
            cellTablevisitados=new PdfPCell(new Phrase(context.getResources().getString(R.string.scheduled),font6));
            cellTablevisitados.disableBorderSide(Rectangle.BOX);
            cellTablevisitados.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvisitados.addCell(cellTablevisitados);
            cellTablevisitados=new PdfPCell(new Phrase(String.valueOf(
                    //rutaVendedorSQLiteDao.ObtenerCantidadRutaVendedor(fechasap,"1")
                    countclientsroute
            ),font3));
            cellTablevisitados.disableBorderSide(Rectangle.BOX);
            cellTablevisitados.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvisitados.addCell(cellTablevisitados);
            cellTablevisitados=new PdfPCell(new Phrase(context.getResources().getString(R.string.debt),font6));
            cellTablevisitados.disableBorderSide(Rectangle.BOX);
            cellTablevisitados.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvisitados.addCell(cellTablevisitados);
            cellTablevisitados=new PdfPCell(new Phrase(String.valueOf(
                    //rutaVendedorSQLiteDao.GetCountClientwithBalance(fechasap,"1")
                    countclientsbalanceroute
            ),font3));
            cellTablevisitados.disableBorderSide(Rectangle.BOX);
            cellTablevisitados.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvisitados.addCell(cellTablevisitados);
            documento.add(tblvisitados);

            PdfPTable tbruta = new PdfPTable(1);
            tbruta.setWidthPercentage(100);
            PdfPCell cellTableruta = null;
            cellTableruta=new PdfPCell(new Phrase(context.getResources().getString(R.string.separator)+context.getResources().getString(R.string.route).toUpperCase()+context.getResources().getString(R.string.separator),font6));
            cellTableruta.disableBorderSide(Rectangle.BOX);
            cellTableruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbruta.addCell(cellTableruta);
            documento.add(tbruta);

            PdfPTable tbltipo = new PdfPTable(3);
            tbltipo.setWidthPercentage(100);
            PdfPCell  cellTableTipo= null;
            cellTableTipo=new PdfPCell(new Phrase(context.getResources().getString(R.string.type),font6));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase(context.getResources().getString(R.string.quantity),font6));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase(context.getResources().getString(R.string.amount),font6));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase(context.getResources().getString(R.string.collection) ,font6));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbltipo.addCell(cellTableTipo);
            //countVisitTypeCOB=visitaSQLite.getCountVisitWithTypeVisitCOB(fechasap,"1","02");
            cellTableTipo=new PdfPCell(new Phrase(String.valueOf(
                    //countVisitTypeCOB
                    countcollectionsroute
                    ),font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            //amountVisitTypeCOB=visitaSQLite.getSumVisitWithTypeCOB(fechasap,"1","02");
            cellTableTipo=new PdfPCell(new Phrase(String.valueOf(
                    //amountVisitTypeCOB
                    Convert.currencyForView(amountcollectionsroute)
            ),font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase(context.getResources().getString(R.string.salesorder),font6));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase(String.valueOf(
                    countsalesordersroute
            ),font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase(String.valueOf(
                   Convert.currencyForView(amountsalesordersroute)
            ),font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase(context.getResources().getString(R.string.Quotation),font6));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase(String.valueOf(
                    countquotationroute
            ),font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase(String.valueOf(
                    Convert.currencyForView(amountquotationroute)
            ),font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            documento.add(tbltipo);

            PdfPTable tbefecruta = new PdfPTable(1);
            tbefecruta.setWidthPercentage(100);
            PdfPCell celltbefecruta = null;
            celltbefecruta=new PdfPCell(new Phrase(context.getResources().getString(R.string.separator_short)+context.getResources().getString(R.string.effectiveness).toUpperCase()+" "
                    +context.getResources().getString(R.string.route).toUpperCase()+context.getResources().getString(R.string.separator_short)  ,font6));
            celltbefecruta.disableBorderSide(Rectangle.BOX);
            celltbefecruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbefecruta.addCell(celltbefecruta);
            documento.add(tbefecruta);

            PdfPTable tblEfec = new PdfPTable(3);
            tblEfec.setWidthPercentage(100);
            PdfPCell  cellTableEfec= null;
            cellTableEfec=new PdfPCell(new Phrase(context.getResources().getString(R.string.type),font6));
            cellTableEfec.disableBorderSide(Rectangle.BOX);
            cellTableEfec.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblEfec.addCell(cellTableEfec);
            cellTableEfec=new PdfPCell(new Phrase(context.getResources().getString(R.string.quotas),font6));
            cellTableEfec.disableBorderSide(Rectangle.BOX);
            cellTableEfec.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblEfec.addCell(cellTableEfec);
            cellTableEfec=new PdfPCell(new Phrase(context.getResources().getString(R.string.avance) ,font6));
            cellTableEfec.disableBorderSide(Rectangle.BOX);
            cellTableEfec.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblEfec.addCell(cellTableEfec);
            documento.add(tblEfec);


            PdfPTable tblefectividad = new PdfPTable(3);
            tblefectividad.setWidthPercentage(100);
            PdfPCell celltblefectividad = null;
            celltblefectividad=new PdfPCell(new Phrase(context.getResources().getString(R.string.effectiveness)+" "+context.getResources().getString(R.string.salesorder) ,font6));
            celltblefectividad.disableBorderSide(Rectangle.BOX);
            celltblefectividad.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblefectividad.addCell(celltblefectividad);
            ArrayList<QuoteEffectivenessEntity> quoteEffectivenessEntityArrayList=new ArrayList<>();
            QuoteEffectivenessSQLiteDao quoteEffectivenessSQLiteDao=new QuoteEffectivenessSQLiteDao(context);
            quoteEffectivenessEntityArrayList=quoteEffectivenessSQLiteDao.getListQuoteEffectivenessEntity("1");
            String efpedido="0",efcobranza="0",efvisita="0";
            for (int i=0;i<quoteEffectivenessEntityArrayList.size();i++)
            {
                efpedido=quoteEffectivenessEntityArrayList.get(i).getQuote()+" "+quoteEffectivenessEntityArrayList.get(i).getUmd();
            }
            celltblefectividad=new PdfPCell(new Phrase(efpedido,font6));
            celltblefectividad.disableBorderSide(Rectangle.BOX);
            celltblefectividad.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblefectividad.addCell(celltblefectividad);

            try{
                celltblefectividad=new PdfPCell(new Phrase(
                        effectivenessasalesordersroute
                        ,font3));
            }catch (Exception e)
            {
                e.printStackTrace();
                Log.e("REOS","DocumentCobranzaPDF.generarPdf.e:" + e.toString());
            }
            celltblefectividad.disableBorderSide(Rectangle.BOX);
            celltblefectividad.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblefectividad.addCell(celltblefectividad);
            celltblefectividad=new PdfPCell(new Phrase(context.getResources().getString(R.string.effectiveness)+" "+context.getResources().getString(R.string.collection) ,font6));
            celltblefectividad.disableBorderSide(Rectangle.BOX);
            celltblefectividad.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblefectividad.addCell(celltblefectividad);

            quoteEffectivenessEntityArrayList=quoteEffectivenessSQLiteDao.getListQuoteEffectivenessEntity("2");
            for (int i=0;i<quoteEffectivenessEntityArrayList.size();i++)
            {
                efcobranza=quoteEffectivenessEntityArrayList.get(i).getQuote()+" "+quoteEffectivenessEntityArrayList.get(i).getUmd();
            }
            celltblefectividad=new PdfPCell(new Phrase(efcobranza,font6));
            celltblefectividad.disableBorderSide(Rectangle.BOX);
            celltblefectividad.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblefectividad.addCell(celltblefectividad);

            try{
                celltblefectividad=new PdfPCell(new Phrase(
                        /*Induvis.getAmountRouteeffectiveness(
                                //(rutaVendedorSQLiteDao.ObtenerCantidadCobranzaRutaVendedor(fechasap,"1")),
                                //visitaSQLite.getCountVisitWithTypeOVCOB(fechasap,"1","02"),
                                countVisitTypeCOB,
                                rutaVendedorSQLiteDao.GetCountClientwithBalance(fechasap,"1")*/
                        effectivenesscollectionsroute
                        ,font3));
            }catch (Exception e)
            {
                e.printStackTrace();
                Log.e("REOS","DocumentCobranzaPDF.generarPdf.e:" + e.toString());
            }
            celltblefectividad.disableBorderSide(Rectangle.BOX);
            celltblefectividad.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblefectividad.addCell(celltblefectividad);
            celltblefectividad=new PdfPCell(new Phrase(context.getResources().getString(R.string.effectiveness)+" "+context.getResources().getString(R.string.visits) ,font6));
            celltblefectividad.disableBorderSide(Rectangle.BOX);
            celltblefectividad.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblefectividad.addCell(celltblefectividad);

            quoteEffectivenessEntityArrayList=quoteEffectivenessSQLiteDao.getListQuoteEffectivenessEntity("3");
            for (int i=0;i<quoteEffectivenessEntityArrayList.size();i++)
            {
                efvisita=quoteEffectivenessEntityArrayList.get(i).getQuote()+" "+quoteEffectivenessEntityArrayList.get(i).getUmd();
            }
            celltblefectividad=new PdfPCell(new Phrase(efvisita,font6));
            celltblefectividad.disableBorderSide(Rectangle.BOX);
            celltblefectividad.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblefectividad.addCell(celltblefectividad);

            try{
                celltblefectividad=new PdfPCell(new Phrase(
                        effectivenessvisitroute
                        ,font3));
            }catch (Exception e)
            {
                e.printStackTrace();
                Log.e("REOS","DocumentCobranzaPDF.generarPdf.e:" + e.toString());
            }
            celltblefectividad.disableBorderSide(Rectangle.BOX);
            celltblefectividad.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblefectividad.addCell(celltblefectividad);
            documento.add(tblefectividad);


            PdfPTable tblnoruta = new PdfPTable(1);
            tblnoruta.setWidthPercentage(100);
            PdfPCell cellTablenoruta = null;
            cellTablenoruta=new PdfPCell(new Phrase(context.getResources().getString(R.string.separator_short)+context.getResources().getString(R.string.no).toUpperCase()+" "
                    +context.getResources().getString(R.string.route).toUpperCase()+context.getResources().getString(R.string.separator_short)
                    ,font6));
            cellTablenoruta.disableBorderSide(Rectangle.BOX);
            cellTablenoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblnoruta.addCell(cellTablenoruta);
            documento.add(tblnoruta);
            PdfPTable tbltiponoruta = new PdfPTable(3);
            tbltiponoruta.setWidthPercentage(100);
            PdfPCell  cellTableTiponoruta= null;
            cellTableTiponoruta=new PdfPCell(new Phrase(context.getResources().getString(R.string.type),font6));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(context.getResources().getString(R.string.quantity),font6));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(context.getResources().getString(R.string.amount),font6));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(context.getResources().getString(R.string.collection),font6));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(String.valueOf(
                    countcollectionsnoroute
            ),font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(String.valueOf(
                    Convert.currencyForView(amountcollectionsnoroute)

            ) ,font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(context.getResources().getString(R.string.salesorder),font6));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbltiponoruta.addCell(cellTableTiponoruta);

            cellTableTiponoruta=new PdfPCell(new Phrase(String.valueOf(
                    countsalesordersnoroute
            ),font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(String.valueOf(
                    Convert.currencyForView(amountsalesordernoroute)

            ),font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(context.getResources().getString(R.string.visits),font6));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(String.valueOf(
                    countvisitnoroute
            ),font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(String.valueOf(Convert.currencyForView("0")),font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(context.getResources().getString(R.string.quotation),font6));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(
                    String.valueOf(visitaSQLite.getCountVisitWithTypeOVCOB(fechasap,"0","12")
            ),font3));

            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(String.valueOf(Convert.currencyForView(String.valueOf(visitaSQLite.getSumVisitWithTypeOVCOB(fechasap,"0","12")))),font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            documento.add(tbltiponoruta);

        } catch (DocumentException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());
            Log.e("REOS","ResumenDiarioPDF-generarPdf-DocumentException:"+ e.toString());
        } catch (IOException e) {
            Log.e(ETIQUETA_ERROR, e.getMessage());
            Log.e("REOS","ResumenDiarioPDF-generarPdf-IOException:"+ e.toString());
        } catch (Exception e){
            e.printStackTrace();
            Log.e("REOS","ResumenDiarioPDF-generarPdf-Exception:"+ e.toString());
        }
        finally {
            Log.e("JPCM","ENTRO AL FINALLY  AFTER ERROR");
            // Cerramos el documento.
            documento.close();
            //DeleteDocumentPDF(fechasap,context);
            OpenDocumentPDF(fechasap,context);
            ///////////////////ABRIR PDF////////////////////////

           /* File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator+"ResumenDiario"+File.separator+fechasap+".pdf");

            Uri excelPath = FileProvider.getUriForFile(context, context.getPackageName(), file);

            Intent target = new Intent(Intent.ACTION_VIEW);

            target.setDataAndType(excelPath,"application/pdf");
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                context.startActivity(target);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Es necesario que instales algun visor de PDF", Toast.LENGTH_SHORT).show();
            }*/

            ///////////////////////////////////////////////////

        }
    }


    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    /**
     * Obtenemos la ruta donde vamos a almacenar el fichero.
     *
     * @return
     */
    public static File getRuta() {

        // El fichero sera almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }

        return ruta;
    }

    private void DeleteDocumentPDF(String fechasap,Context context)
    {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator
                    +NOMBRE_DIRECTORIO+File.separator+fechasap+".pdf");

            file.delete();
            /*Uri  excelPath = FileProvider.getUriForFile(context, context.getPackageName(), file);

            Intent target = new Intent(Intent.ACTION_DELETE);
            target.setDataAndType(excelPath,"application/pdf");
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(target);*/
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("REOS","ResumenDiarioPDF-OpenDocumentPDF-e"+e.toString());
            Toast.makeText(context, "Es necesario que instales algun visor de PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private void OpenDocumentPDF(String fechasap,Context context)
    {
        try {
            /*File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator
                    +NOMBRE_DIRECTORIO+File.separator+fechasap+".pdf");

            Uri  excelPath2 = FileProvider.getUriForFile(context, context.getPackageName(), file2);

            Intent target2 = new Intent(Intent.ACTION_DELETE);
            target2.setDataAndType(excelPath2,"application/pdf");
            target2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(target2);*/

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator
                    +NOMBRE_DIRECTORIO+File.separator+fechasap+".pdf");

            Uri  excelPath = FileProvider.getUriForFile(context, context.getPackageName(), file);

            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(excelPath,"application/pdf");
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(target);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("REOS","ResumenDiarioPDF-OpenDocumentPDF-e"+e.toString());
            Toast.makeText(context, context.getResources().getString(R.string.mse_necessary_install_PDF), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String obtenerHoraActual() {
        String formato = "HHmmss";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
        LocalDateTime ahora = LocalDateTime.now();
        return formateador.format(ahora);
    }

}

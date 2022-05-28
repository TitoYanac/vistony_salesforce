package com.vistony.salesforce.Controller.Utilitario;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

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
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.KardexPagoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerDetailEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerHeadEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerInvoiceEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import harmony.java.awt.Color;

public class QuotasPerCustomerPDF extends AppCompatActivity {
    private final static String NOMBRE_DIRECTORIO = "Quota";
    private final static String ETIQUETA_ERROR = "ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
        } else {

        }
    }

    public void generarPdf(Context context, List<QuotasPerCustomerInvoiceEntity> quotasPerCustomerInvoiceEntity
            , List<QuotasPerCustomerDetailEntity> quotasPerCustomerDetailEntity, ArrayList<ListaClienteCabeceraEntity> ListaClienteCabeceraEntity)  {
        Log.e("REOS","QuotasPerCustomerPDF.generarPdf.quotasPerCustomerInvoiceEntity.SIZE():"+quotasPerCustomerInvoiceEntity.size());
        Log.e("REOS","QuotasPerCustomerPDF.generarPdf.quotasPerCustomerDetailEntity.SIZE():"+quotasPerCustomerDetailEntity.size());
        Log.e("REOS","QuotasPerCustomerPDF.generarPdf.ListaClienteCabeceraEntity.SIZE():"+ListaClienteCabeceraEntity.size());
        // Creamos el documento.
        Rectangle pagina = new Rectangle(
                36, 36,
                //559
                650
                //, 806
                , 1120
                //PageSize.ARCH_A
        );
        Document documento = new Document(pagina);
        Log.e("REOS","QuotasPerCustomerPDF.generarPdf.IngresoPDF:");
        ArrayList<String> ObjList=new ArrayList<>();
        ArrayList<String> Correlativo=new ArrayList<>();
        String CardCode="",CardName="",LicTradNum="",Street="",Ubigeo="",Phone="";
        Set<String> listaCorrelativo ;
        for(int i=0;i<ListaClienteCabeceraEntity.size();i++)
        {
            CardCode=ListaClienteCabeceraEntity.get(i).getCliente_id();
            CardName=ListaClienteCabeceraEntity.get(i).getNombrecliente();
            LicTradNum=ListaClienteCabeceraEntity.get(i).getRucdni();
            Street=ListaClienteCabeceraEntity.get(i).getDireccion();
            Ubigeo=ListaClienteCabeceraEntity.get(i).getUbigeo_id();
            Phone=ListaClienteCabeceraEntity.get(i).getTelefonomovil();;
        }
        Log.e("REOS","QuotasPerCustomerPDF.generarPdf.CardCode:"+CardCode);
        try {
            File f = crearFichero(CardCode+".pdf");

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
            Font font5 = FontFactory.getFont(FontFactory.HELVETICA, 24,
                    Font.BOLD , Color.black);
            Font font6 = FontFactory.getFont(FontFactory.HELVETICA, 16,
                    Font.BOLD, Color.black);
            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.
            Bitmap bitmap=null;

            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo_negro_vistony);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100 ,stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            imagen.setAlignment(Element.ALIGN_CENTER);
            documento.add(imagen);

            PdfPTable tblempresa = new PdfPTable(1);
            tblempresa.setWidthPercentage(100);
            PdfPCell cellTableempresa = null;
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.BuildConfig.FLAVOR:" + BuildConfig.FLAVOR);
            cellTableempresa=new PdfPCell(new Phrase(Induvis.getInformation(BuildConfig.FLAVOR),font3));
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.Induvis.getInformation(BuildConfig.FLAVOR):" + Induvis.getInformation(BuildConfig.FLAVOR));
            cellTableempresa.disableBorderSide(Rectangle.BOX);
            cellTableempresa.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblempresa.addCell(cellTableempresa);
            documento.add(tblempresa);

            PdfPTable tbllblkardex = new PdfPTable(1);
            tbllblkardex.setWidthPercentage(100);
            PdfPCell celltbllblkardex = null;
            celltbllblkardex=new PdfPCell(new Phrase("***********CALCULO DE CUOTAS*************",font5));
            celltbllblkardex.disableBorderSide(Rectangle.BOX);
            celltbllblkardex.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbllblkardex.addCell(celltbllblkardex);
            documento.add(tbllblkardex);

            PdfPTable tbllblcliente = new PdfPTable(1);
            tbllblcliente.setWidthPercentage(100);
            PdfPCell celltbllblcliente = null;
            celltbllblcliente=new PdfPCell(new Phrase("************************DATOS CLIENTE**********************",font2 ));
            celltbllblcliente.disableBorderSide(Rectangle.BOX);
            celltbllblcliente.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbllblcliente.addCell(celltbllblcliente);
            documento.add(tbllblcliente);

            PdfPTable tblCabecera = new PdfPTable(2);
            tblCabecera.setWidthPercentage(100);
            PdfPCell cellCabecera = null;
            cellCabecera = new PdfPCell(new Phrase("Cód.Cliente",font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase(CardCode,font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase("Doc.ID",font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase(LicTradNum,font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase("Cliente",font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase(CardName,font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase("Telefono",font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase(Phone,font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase("Dirección",font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase(Street,font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase("Moneda",font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase(SesionEntity.currency,font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            documento.add(tblCabecera);

            PdfPTable tbllblresumen = new PdfPTable(1);
            tbllblresumen.setWidthPercentage(100);
            PdfPCell celltbllblresumen = null;
            celltbllblresumen=new PdfPCell(new Phrase("****************************RESUMEN**************************",font2 ));
            celltbllblresumen.disableBorderSide(Rectangle.BOX);
            celltbllblresumen.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbllblresumen.addCell(celltbllblresumen);
            documento.add(tbllblresumen);

            for(int l=0;l<quotasPerCustomerInvoiceEntity.size();l++)
            {
                String tipo,vendedor;
                String[] sourcedata= quotasPerCustomerInvoiceEntity.get(l).getTipo().split("-");
                tipo=sourcedata[0];
                vendedor=sourcedata[1];



                PdfPTable tbllblfactura = new PdfPTable(1);
                tbllblfactura.setWidthPercentage(100);
                PdfPCell celltbllblfactura = null;
                celltbllblfactura=new PdfPCell(new Phrase(quotasPerCustomerInvoiceEntity.get(l).getNrofactura()+"*******************************************",font2 ));
                celltbllblfactura.disableBorderSide(Rectangle.BOX);
                celltbllblfactura.setHorizontalAlignment(Element.ALIGN_CENTER);
                tbllblfactura.addCell(celltbllblfactura);
                documento.add(tbllblfactura);

                PdfPTable tblterminopago= new PdfPTable(2);
                tblterminopago.setWidthPercentage(100);

                PdfPCell celltblterminopago = null;
                celltblterminopago = new PdfPCell(new Phrase("Cónd.Venta:",font3));
                celltblterminopago.disableBorderSide(Rectangle.BOX);
                celltblterminopago.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblterminopago.addCell(celltblterminopago);
                celltblterminopago = new PdfPCell(new Phrase(quotasPerCustomerInvoiceEntity.get(l).getCondicionpago(),font3));
                celltblterminopago.disableBorderSide(Rectangle.BOX);
                celltblterminopago.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblterminopago.addCell(celltblterminopago);
                celltblterminopago = new PdfPCell(new Phrase("Vendedor Factura:",font3));
                celltblterminopago.disableBorderSide(Rectangle.BOX);
                celltblterminopago.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblterminopago.addCell(celltblterminopago);
                celltblterminopago = new PdfPCell(new Phrase(vendedor,font3));
                celltblterminopago.disableBorderSide(Rectangle.BOX);
                celltblterminopago.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblterminopago.addCell(celltblterminopago);
                documento.add(tblterminopago);

                PdfPTable tblresumencuotas= new PdfPTable(4);
                tblresumencuotas.setWidthPercentage(100);
                PdfPCell celltblresumencuotas = null;
                celltblresumencuotas = new PdfPCell(new Phrase("F.Emision:",font6));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);

                String fecha,año,mes,dia;
                String[] sourceemision= quotasPerCustomerInvoiceEntity.get(l).getFechaemision().split(" ");
                fecha= sourceemision[0];
                String[] sourcefechadesordenada= fecha.split("/");
                año=sourcefechadesordenada[2];
                mes=sourcefechadesordenada[0];
                dia=sourcefechadesordenada[1];
                celltblresumencuotas = new PdfPCell(new Phrase(dia+"/"+mes+"/"+año,font6));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                celltblresumencuotas = new PdfPCell(new Phrase("F.Vencimiento:",font3));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                String fecha2,año2,mes2,dia2;
                String[] sourceemision2= quotasPerCustomerInvoiceEntity.get(l).getFechavencimiento() .split(" ");
                fecha2= sourceemision2[0];
                String[] sourcefechadesordenada2= fecha2.split("/");
                año2=sourcefechadesordenada2[2];
                mes2=sourcefechadesordenada2[0];
                dia2=sourcefechadesordenada2[1];
                celltblresumencuotas = new PdfPCell(new Phrase(dia2+"/"+mes2+"/"+año2,font3));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                celltblresumencuotas = new PdfPCell(new Phrase("Importe:",font3));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                celltblresumencuotas = new PdfPCell(new Phrase(Convert.currencyForView(quotasPerCustomerInvoiceEntity.get(l).getImportefactura())  ,font3));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                celltblresumencuotas = new PdfPCell(new Phrase("Saldo:",font3));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                celltblresumencuotas = new PdfPCell(new Phrase(Convert.currencyForView(quotasPerCustomerInvoiceEntity.get(l).getSaldo()) ,font3));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                celltblresumencuotas = new PdfPCell(new Phrase("Monto Semanal:",font6));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                celltblresumencuotas = new PdfPCell(new Phrase(Convert.currencyForView(quotasPerCustomerInvoiceEntity.get(l).getMontocuota()) ,font6));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                celltblresumencuotas = new PdfPCell(new Phrase("Nro. Semanas:",font3));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                celltblresumencuotas = new PdfPCell(new Phrase(quotasPerCustomerInvoiceEntity.get(l).getCuota(),font3));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                celltblresumencuotas = new PdfPCell(new Phrase("Tipo:",font6));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                celltblresumencuotas = new PdfPCell(new Phrase(tipo ,font6));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                celltblresumencuotas = new PdfPCell(new Phrase("Dias Mora:",font6));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                celltblresumencuotas = new PdfPCell(new Phrase(quotasPerCustomerInvoiceEntity.get(l).getDiasvencimiento() ,font6));
                celltblresumencuotas.disableBorderSide(Rectangle.BOX);
                celltblresumencuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblresumencuotas.addCell(celltblresumencuotas);
                documento.add(tblresumencuotas);
            }

            PdfPTable tbldetalle = new PdfPTable(1);
            tbldetalle.setWidthPercentage(100);
            PdfPCell celltbldetalle = null;
            celltbldetalle=new PdfPCell(new Phrase("****************************DETALLE**************************",font2 ));
            celltbldetalle.disableBorderSide(Rectangle.BOX);
            celltbldetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbldetalle.addCell(celltbldetalle);
            documento.add(tbldetalle);

            for(int l=0;l<quotasPerCustomerDetailEntity.size();l++)
            {
                PdfPTable tbldetallecuotas= new PdfPTable(4);
                tbldetallecuotas.setWidthPercentage(100);
                PdfPCell celltbldetallecuotas = null;
                celltbldetallecuotas = new PdfPCell(new Phrase("CUOTA:",font6));
                celltbldetallecuotas.disableBorderSide(Rectangle.BOX);
                celltbldetallecuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tbldetallecuotas.addCell(celltbldetallecuotas);
                celltbldetallecuotas = new PdfPCell(new Phrase(quotasPerCustomerDetailEntity.get(l).getCuota(),font6));
                celltbldetallecuotas.disableBorderSide(Rectangle.BOX);
                celltbldetallecuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tbldetallecuotas.addCell(celltbldetallecuotas);
                celltbldetallecuotas = new PdfPCell(new Phrase("F.Cobro:",font3));
                celltbldetallecuotas.disableBorderSide(Rectangle.BOX);
                celltbldetallecuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tbldetallecuotas.addCell(celltbldetallecuotas);
                String fecha,año,mes,dia;
                String[] sourceemision= quotasPerCustomerDetailEntity.get(l).getFecha().split(" ");
                fecha= sourceemision[0];
                String[] sourcefechadesordenada= fecha.split("/");
                año=sourcefechadesordenada[2];
                mes=sourcefechadesordenada[0];
                dia=sourcefechadesordenada[1];
                celltbldetallecuotas = new PdfPCell(new Phrase(dia+"/"+mes+"/"+año,font3));
                celltbldetallecuotas.disableBorderSide(Rectangle.BOX);
                celltbldetallecuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tbldetallecuotas.addCell(celltbldetallecuotas);
                celltbldetallecuotas = new PdfPCell(new Phrase("Vencido:",font3));
                celltbldetallecuotas.disableBorderSide(Rectangle.BOX);
                celltbldetallecuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tbldetallecuotas.addCell(celltbldetallecuotas);
                celltbldetallecuotas = new PdfPCell(new Phrase(Convert.currencyForView(quotasPerCustomerDetailEntity.get(l).getVencido()),font3));
                celltbldetallecuotas.disableBorderSide(Rectangle.BOX);
                celltbldetallecuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tbldetallecuotas.addCell(celltbldetallecuotas);
                celltbldetallecuotas = new PdfPCell(new Phrase("Corriente:",font3));
                celltbldetallecuotas.disableBorderSide(Rectangle.BOX);
                celltbldetallecuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tbldetallecuotas.addCell(celltbldetallecuotas);
                celltbldetallecuotas = new PdfPCell(new Phrase(Convert.currencyForView(quotasPerCustomerDetailEntity.get(l).getCorriente()),font3));
                celltbldetallecuotas.disableBorderSide(Rectangle.BOX);
                celltbldetallecuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tbldetallecuotas.addCell(celltbldetallecuotas);
                celltbldetallecuotas = new PdfPCell(new Phrase("Pedido:",font3));
                celltbldetallecuotas.disableBorderSide(Rectangle.BOX);
                celltbldetallecuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tbldetallecuotas.addCell(celltbldetallecuotas);
                celltbldetallecuotas = new PdfPCell(new Phrase(Convert.currencyForView(quotasPerCustomerDetailEntity.get(l).getPedido()),font3));
                celltbldetallecuotas.disableBorderSide(Rectangle.BOX);
                celltbldetallecuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tbldetallecuotas.addCell(celltbldetallecuotas);
                celltbldetallecuotas = new PdfPCell(new Phrase("TOTAL:",font6));
                celltbldetallecuotas.disableBorderSide(Rectangle.BOX);
                celltbldetallecuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tbldetallecuotas.addCell(celltbldetallecuotas);
                celltbldetallecuotas = new PdfPCell(new Phrase(Convert.currencyForView(quotasPerCustomerDetailEntity.get(l).getTotal()),font6));
                celltbldetallecuotas.disableBorderSide(Rectangle.BOX);
                celltbldetallecuotas.setHorizontalAlignment(Element.ALIGN_LEFT);
                tbldetallecuotas.addCell(celltbldetallecuotas);
                documento.add(tbldetallecuotas);
            }





            //documento.add(tblLineas);
        } catch (DocumentException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());
            Log.e("REOS","QuotasPerCustomerPDF.DocumentException.e"+e.toString());

        } catch (IOException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());
            Log.e("REOS","QuotasPerCustomerPDF.IOException.e"+e.toString());

        } catch (Exception e){
            e.printStackTrace();
            Log.e("JPCM","capturado->"+ e);
            Log.e("REOS","QuotasPerCustomerPDF.Exception.e"+e.toString());
        }
        finally {
            Log.e("JPCM","ENTRO AL FINALLY  AFTER ERROR");
            // Cerramos el documento.
            documento.close();

            OpenDocumentPDF(CardCode,context);

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

    private void OpenDocumentPDF(String CardCode,Context context)
    {

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator
                    +NOMBRE_DIRECTORIO+File.separator+CardCode+".pdf");

            Uri  excelPath = FileProvider.getUriForFile(context, context.getPackageName(), file);

            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(excelPath,"application/pdf");
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(target);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("REOS","QuotasPerCustomerPDF-OpenDocumentPDF-e"+e.toString());
            Toast.makeText(context, "Es necesario que instales algun visor de PDF", Toast.LENGTH_SHORT).show();
        }
    }


}

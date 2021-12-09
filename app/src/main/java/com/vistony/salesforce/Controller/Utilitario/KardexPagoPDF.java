package com.vistony.salesforce.Controller.Utilitario;

import android.Manifest;
import android.content.ActivityNotFoundException;
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

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
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
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.KardexPagoEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaDetallePromocionSQLiteEntity;
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

public class KardexPagoPDF extends AppCompatActivity {
    private final static String NOMBRE_DIRECTORIO = "KardexPago";
    private final static String ETIQUETA_ERROR = "ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
        } else {

        }
    }

    public void generarPdf(Context context, List<KardexPagoEntity> ListKardexPagoEntity) {
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

        ArrayList<String> ObjList=new ArrayList<>();
        ArrayList<String> Correlativo=new ArrayList<>();
        String CardCode="",CardName="",LicTradNum="",Street="",Ubigeo="",Phone="";
        Set<String> listaCorrelativo ;
        for(int i=0;i<ListKardexPagoEntity.size();i++)
        {
            CardCode=ListKardexPagoEntity.get(i).getCardCode();
            CardName=ListKardexPagoEntity.get(i).getCardName();
            LicTradNum=ListKardexPagoEntity.get(i).getLicTradNum();
            Street=ListKardexPagoEntity.get(i).getStreet();
            Ubigeo=ListKardexPagoEntity.get(i).getU_SYP_DEPA()+"-"+ListKardexPagoEntity.get(i).getU_SYP_PROV()+"-"+ListKardexPagoEntity.get(i).getU_SYP_DIST();
            Phone=ListKardexPagoEntity.get(i).getPhone1();
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.ListKardexPagoEntity.get(i).getNumAtCard():" + ListKardexPagoEntity.get(i).getNumAtCard());
            ObjList.add(ListKardexPagoEntity.get(i).getNumAtCard());
        }
        listaCorrelativo = new HashSet<String>(ObjList);

        for(int j=0;j<ObjList.size();j++)
        {
            if(!Correlativo.isEmpty())
            {
                int contador=0;
                for(int l=0;l<Correlativo.size();l++)
                {
                    if(ObjList.get(j).equals(Correlativo.get(l)))
                    {
                        contador++;
                    }

                }
                if(contador==0)
                {
                    Correlativo.add(ObjList.get(j).toString());
                }

            }
            else {
                Correlativo.add(ObjList.get(j).toString());
            }
        }

        Log.e("REOS","DocumentCobranzaPDF.generarPdf.listaCorrelativo:" + listaCorrelativo.size());
        Object[] ObjArrayListaCorrelativo = listaCorrelativo.toArray();
        for(int j=0;j<Correlativo.size();j++)
        {
            //ObjArrayListaCorrelativo[j].toString();
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.Correlativo:" + Correlativo.get(j));
        }
        Log.e("REOS","DocumentCobranzaPDF.generarPdf.ObjArrayListaCorrelativo.length:" + ObjArrayListaCorrelativo.length);
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
            Font font5 = FontFactory.getFont(FontFactory.HELVETICA, 18,
                    Font.BOLD , Color.black);
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
            celltbllblkardex=new PdfPCell(new Phrase("***********KARDEX DE PAGO*************",font));
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
            cellCabecera = new PdfPCell(new Phrase("Cod.Cliente",font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase(CardCode,font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase("Doc.Id",font3));
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
            cellCabecera = new PdfPCell(new Phrase("Direccion",font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase(Street,font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase("Ubigeo",font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase(Ubigeo,font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblCabecera.addCell(cellCabecera);
            documento.add(tblCabecera);

            PdfPTable tbllbldetalle = new PdfPTable(1);
            tbllbldetalle.setWidthPercentage(100);
            PdfPCell celltbllbldetalle = null;
            celltbllbldetalle=new PdfPCell(new Phrase("****************************DETALLE**************************",font2 ));
            celltbllbldetalle.disableBorderSide(Rectangle.BOX);
            celltbllbldetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbllbldetalle.addCell(celltbllbldetalle);
            documento.add(tbllbldetalle);






            for(int l=0;l<Correlativo.size();l++)
            {
                PdfPTable tbllblfactura = new PdfPTable(1);
                tbllblfactura.setWidthPercentage(100);
                PdfPCell celltbllblfactura = null;
                celltbllblfactura=new PdfPCell(new Phrase(Correlativo.get(l).toString()+"****************************************************",font5 ));
                celltbllblfactura.disableBorderSide(Rectangle.BOX);
                celltbllblfactura.setHorizontalAlignment(Element.ALIGN_CENTER);
                tbllblfactura.addCell(celltbllblfactura);
                documento.add(tbllblfactura);



                int contador=0;
                int item=1;
                for(int j=0;j<ListKardexPagoEntity.size();j++)
                {

                    if(Correlativo.get(l).equals(ListKardexPagoEntity.get(j).getNumAtCard())&&contador==0) {

                        PdfPTable tblterminopago= new PdfPTable(2);
                        tblterminopago.setWidthPercentage(100);
                        PdfPCell celltblterminopago = null;
                        celltblterminopago = new PdfPCell(new Phrase("Cond.Venta:",font3));
                        celltblterminopago.disableBorderSide(Rectangle.BOX);
                        celltblterminopago.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tblterminopago.addCell(celltblterminopago);
                        celltblterminopago = new PdfPCell(new Phrase(ListKardexPagoEntity.get(j).getPymntGroup(),font3));
                        celltblterminopago.disableBorderSide(Rectangle.BOX);
                        celltblterminopago.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tblterminopago.addCell(celltblterminopago);
                        documento.add(tblterminopago);

                        PdfPTable tblLineas = new PdfPTable(4);
                        tblLineas.setWidthPercentage(100);
                        PdfPCell cellLineasDetalle = null;
                        cellLineasDetalle = new PdfPCell(new Phrase("Fecha.Emision:", font3));
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                        cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tblLineas.addCell(cellLineasDetalle);

                        String fecha,año,mes,dia;
                        String[] sourceemision= ListKardexPagoEntity.get(j).getTaxDate().split(" ");
                        fecha= sourceemision[0];
                        String[] sourcefechadesordenada= fecha.split("/");
                        año=sourcefechadesordenada[2];
                        mes=sourcefechadesordenada[0];
                        dia=sourcefechadesordenada[1];

                        cellLineasDetalle = new PdfPCell(new Phrase(dia+"/"+mes+"/"+año, font3));
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                        cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tblLineas.addCell(cellLineasDetalle);
                        cellLineasDetalle = new PdfPCell(new Phrase("Fecha.Vencto", font3));
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                        cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tblLineas.addCell(cellLineasDetalle);

                        sourceemision= ListKardexPagoEntity.get(j).getDocDueDate().split(" ");
                        fecha= sourceemision[0];
                        sourcefechadesordenada= fecha.split("/");
                        año=sourcefechadesordenada[2];
                        mes=sourcefechadesordenada[0];
                        dia=sourcefechadesordenada[1];
                        cellLineasDetalle = new PdfPCell(new Phrase(dia+"/"+mes+"/"+año, font3));
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                        cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tblLineas.addCell(cellLineasDetalle);
                        cellLineasDetalle = new PdfPCell(new Phrase("Importe:", font3));
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                        cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tblLineas.addCell(cellLineasDetalle);
                        cellLineasDetalle = new PdfPCell(new Phrase(ListKardexPagoEntity.get(j).getDocCur()+" "+ListKardexPagoEntity.get(j).getDocTotal(), font3));
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                        cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tblLineas.addCell(cellLineasDetalle);
                        cellLineasDetalle = new PdfPCell(new Phrase("Saldo:", font3));
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                        cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tblLineas.addCell(cellLineasDetalle);
                        cellLineasDetalle = new PdfPCell(new Phrase(ListKardexPagoEntity.get(j).getDocCur()+" "+ListKardexPagoEntity.get(j).getsALDO(), font3));
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                        cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tblLineas.addCell(cellLineasDetalle);
                        documento.add(tblLineas);
                        contador++;

                        PdfPTable tblpagos = new PdfPTable(1);
                        tblpagos.setWidthPercentage(100);
                        PdfPCell celltblpagos = null;
                        celltblpagos = new PdfPCell(new Phrase("Pagos------------------------------------------------------------------------------------------", font3));
                        celltblpagos.disableBorderSide(Rectangle.BOX);
                        celltblpagos.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tblpagos.addCell(celltblpagos);
                        documento.add(tblpagos);

                    }

                    if(Correlativo.get(l).equals(ListKardexPagoEntity.get(j).getNumAtCard()))
                    {
                        PdfPTable tbldatarecibos = new PdfPTable(4);
                        tbldatarecibos.setWidthPercentage(100);
                        PdfPCell celltbldatarecibos= null;

                        celltbldatarecibos = new PdfPCell(new Phrase("Item:", font3));
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX);
                        celltbldatarecibos.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tbldatarecibos.addCell(celltbldatarecibos);

                        celltbldatarecibos = new PdfPCell(new Phrase(String.valueOf(item), font3));
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX);
                        celltbldatarecibos.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tbldatarecibos.addCell(celltbldatarecibos);

                        celltbldatarecibos = new PdfPCell(new Phrase("F.Pago", font3));
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX);
                        celltbldatarecibos.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tbldatarecibos.addCell(celltbldatarecibos);

                        String fecha,año,mes,dia;
                        String[] sourceemision= ListKardexPagoEntity.get(j).getfECHADEPAGO().split(" ");
                        fecha= sourceemision[0];
                        String[] sourcefechadesordenada= fecha.split("/");
                        año=sourcefechadesordenada[2];
                        mes=sourcefechadesordenada[0];
                        dia=sourcefechadesordenada[1];
                        celltbldatarecibos = new PdfPCell(new Phrase(dia+"/"+mes+"/"+año, font3));
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX);
                        celltbldatarecibos.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tbldatarecibos.addCell(celltbldatarecibos);


                        celltbldatarecibos = new PdfPCell(new Phrase("Recibo", font3));
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX);
                        celltbldatarecibos.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tbldatarecibos.addCell(celltbldatarecibos);

                        celltbldatarecibos = new PdfPCell(new Phrase(ListKardexPagoEntity.get(j).getComments(), font3));
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX);
                        celltbldatarecibos.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tbldatarecibos.addCell(celltbldatarecibos);

                        celltbldatarecibos = new PdfPCell(new Phrase("MontoCobrado:", font3));
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX);
                        celltbldatarecibos.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tbldatarecibos.addCell(celltbldatarecibos);

                        celltbldatarecibos = new PdfPCell(new Phrase(ListKardexPagoEntity.get(j).getImporteCobrado(), font3));
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX);
                        celltbldatarecibos.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tbldatarecibos.addCell(celltbldatarecibos);
                        documento.add(tbldatarecibos);
                        item++;

                        PdfPTable tbldatabanco = new PdfPTable(2);
                        tbldatabanco.setWidthPercentage(100);
                        PdfPCell celltbldatabanco= null;
                        celltbldatabanco = new PdfPCell(new Phrase("Nro. Operacion:", font3));
                        celltbldatabanco.disableBorderSide(Rectangle.BOX);
                        celltbldatabanco.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tbldatabanco.addCell(celltbldatabanco);
                        celltbldatabanco = new PdfPCell(new Phrase(ListKardexPagoEntity.get(j).getnROOPERA(), font3));
                        celltbldatabanco.disableBorderSide(Rectangle.BOX);
                        celltbldatabanco.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tbldatabanco.addCell(celltbldatabanco);
                        celltbldatabanco = new PdfPCell(new Phrase("Banco:", font3));
                        celltbldatabanco.disableBorderSide(Rectangle.BOX);
                        celltbldatabanco.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tbldatabanco.addCell(celltbldatabanco);
                        celltbldatabanco = new PdfPCell(new Phrase(ListKardexPagoEntity.get(j).getBanco(), font3));
                        celltbldatabanco.disableBorderSide(Rectangle.BOX);
                        celltbldatabanco.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tbldatabanco.addCell(celltbldatabanco);
                        documento.add(tbldatabanco);


                    }

                    if(Correlativo.get(l).equals(ListKardexPagoEntity.get(j).getNumAtCard())) {
                        PdfPTable tblpagos = new PdfPTable(1);
                        tblpagos.setWidthPercentage(100);
                        PdfPCell celltblpagos = null;
                        celltblpagos = new PdfPCell(new Phrase("--------------------------------------------------------------------------------------------------", font3));
                        celltblpagos.disableBorderSide(Rectangle.BOX);
                        celltblpagos.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tblpagos.addCell(celltblpagos);
                        documento.add(tblpagos);
                    }
                    /*for(int k=0;k<ListKardexPagoEntity.size();k++) {
                        if (k == 0) {
                            cellLineasDetalle = new PdfPCell(new Phrase("NRO.OP", font3));
                            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tblLineas.addCell(cellLineasDetalle);
                            cellLineasDetalle = new PdfPCell(new Phrase("BANCO", font3));
                            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tblLineas.addCell(cellLineasDetalle);
                            cellLineasDetalle = new PdfPCell(new Phrase("F.PAGO", font3));
                            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tblLineas.addCell(cellLineasDetalle);
                            cellLineasDetalle = new PdfPCell(new Phrase("A CUENTA", font3));
                            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tblLineas.addCell(cellLineasDetalle);
                            cellLineasDetalle = new PdfPCell(new Phrase("ESTADO", font3));
                            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tblLineas.addCell(cellLineasDetalle);
                            cellLineasDetalle = new PdfPCell(new Phrase("RECIBO", font3));
                            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tblLineas.addCell(cellLineasDetalle);
                        }
                        if (ListKardexPagoEntity.get(l).getNumAtCard().equals(ListKardexPagoEntity.get(k).getNumAtCard())) {

                            cellLineasDetalle = new PdfPCell(new Phrase(ListKardexPagoEntity.get(l).getnROOPERA(), font3));
                            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tblLineas.addCell(cellLineasDetalle);
                            cellLineasDetalle = new PdfPCell(new Phrase(ListKardexPagoEntity.get(l).getBanco(), font3));
                            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tblLineas.addCell(cellLineasDetalle);
                            cellLineasDetalle = new PdfPCell(new Phrase(ListKardexPagoEntity.get(l).getfECHADEPAGO(), font3));
                            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tblLineas.addCell(cellLineasDetalle);
                            cellLineasDetalle = new PdfPCell(new Phrase(ListKardexPagoEntity.get(l).getImporteCobrado(), font3));
                            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tblLineas.addCell(cellLineasDetalle);
                            cellLineasDetalle = new PdfPCell(new Phrase("Aprobado", font3));
                            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tblLineas.addCell(cellLineasDetalle);
                            cellLineasDetalle = new PdfPCell(new Phrase(ListKardexPagoEntity.get(l).getComments(), font3));
                            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tblLineas.addCell(cellLineasDetalle);

                        }

                    }*/

                   // }

                }
            }
            //documento.add(tblLineas);
        } catch (DocumentException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());
            Log.e("REOS","KardexPagoRPDF.DocumentException.e"+e.toString());

        } catch (IOException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());
            Log.e("REOS","KardexPagoRPDF.IOException.e"+e.toString());

        } catch (Exception e){
            e.printStackTrace();
            Log.e("JPCM","capturado->"+ e);
            Log.e("REOS","KardexPagoRPDF.Exception.e"+e.toString());
        }
        finally {
            Log.e("JPCM","ENTRO AL FINALLY  AFTER ERROR");
            // Cerramos el documento.
            documento.close();

            ///////////////////ABRIR PDF////////////////////////

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator+"KardexPago"+File.separator+CardCode+".pdf");

            Uri  excelPath = FileProvider.getUriForFile(context, context.getPackageName(), file);

            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(excelPath,"application/pdf");
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                context.startActivity(target);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Es necesario que instales algun visor de PDF", Toast.LENGTH_SHORT).show();
            }

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

}



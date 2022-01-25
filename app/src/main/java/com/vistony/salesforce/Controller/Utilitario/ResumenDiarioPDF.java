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
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.KardexPagoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ResumenDiarioEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
    DecimalFormat format = new DecimalFormat("#0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
        } else {

        }
    }

    public void generarPdf(Context context, List<HistoricContainerSalesEntity> resumenDiarioEntityList,String fecha) {
        // Creamos el documento.
        RutaVendedorSQLiteDao rutaVendedorSQLiteDao=new RutaVendedorSQLiteDao(context);
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
        String fechasap="",CardName="",LicTradNum="",Street="",Ubigeo="",Phone="";
        Set<String> listaCorrelativo ;
        for(int i=0;i<resumenDiarioEntityList.size();i++)
        {
            //fechasap=resumenDiarioEntityList.get(i).getFechasap();
        }
        fechasap=fecha;
        listaCorrelativo = new HashSet<String>(ObjList);
        Object[] ObjArrayListaCorrelativo = listaCorrelativo.toArray();
        try {
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
            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.
            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.
            Bitmap bitmap=null;

            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo_negro_vistony);

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
            cellTable = new PdfPCell(new Phrase("RESUMEN DEL DIA",font2));
            cellTable.disableBorderSide(Rectangle.BOX);
            cellTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbldireccion.addCell(cellTable);
            documento.add(tbldireccion);

            PdfPTable tbllblvendedor = new PdfPTable(1);
            tbllblvendedor.setWidthPercentage(100);
            PdfPCell cellTablelblvendedor = null;
            cellTablelblvendedor=new PdfPCell(new Phrase("********************************DATOS VENDEDOR***************************",font5));
            cellTablelblvendedor.disableBorderSide(Rectangle.BOX);
            cellTablelblvendedor.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbllblvendedor.addCell(cellTablelblvendedor);
            documento.add(tbllblvendedor);

            PdfPTable tblvendedor = new PdfPTable(2);
            tbldireccion.setWidthPercentage(100);
            PdfPCell cellTablevendedor = null;
            cellTablevendedor=new PdfPCell(new Phrase("CODIGO",font3));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor=new PdfPCell(new Phrase(SesionEntity.fuerzatrabajo_id,font3));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor = new PdfPCell(new Phrase("NOMBRE",font3));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor = new PdfPCell(new Phrase(SesionEntity.nombrefuerzadetrabajo,font3));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor = new PdfPCell(new Phrase("FECHA",font3));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            cellTablevendedor = new PdfPCell(new Phrase(fechasap,font3));
            cellTablevendedor.disableBorderSide(Rectangle.BOX);
            cellTablevendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvendedor.addCell(cellTablevendedor);
            documento.add(tblvendedor);

            PdfPTable tbllblvariable = new PdfPTable(1);
            tbllblvariable.setWidthPercentage(100);
            PdfPCell cellTablelblvariable= null;
            cellTablelblvariable=new PdfPCell(new Phrase("********************************PRODUCTO FOCO******************************",font5));
            cellTablelblvariable.disableBorderSide(Rectangle.BOX);
            cellTablelblvariable.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbllblvariable.addCell(cellTablelblvariable);
            documento.add(tbllblvariable);
            //float[] columnWidthsCabecera = {1.5f,10f,7f};
            PdfPTable tblCabecera = new PdfPTable(3);
            tblCabecera.setWidthPercentage(100);
            PdfPCell cellCabecera = null;
            cellCabecera = new PdfPCell(new Phrase("Item",font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase("Variable",font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblCabecera.addCell(cellCabecera);
            cellCabecera = new PdfPCell(new Phrase("Monto",font3));
            cellCabecera.disableBorderSide(Rectangle.BOX);
            cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblCabecera.addCell(cellCabecera);
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
                cellCabecera = new PdfPCell(new Phrase(resumenDiarioEntityList.get(i).getMontototal(),font3));
                cellCabecera.disableBorderSide(Rectangle.BOX);
                cellCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
                tblCabecera.addCell(cellCabecera);
            }
            documento.add(tblCabecera);
            if(resumenDiarioEntityList.size()==0)
            {
                PdfPTable tblNodata = new PdfPTable(1);
                tblNodata.setWidthPercentage(100);
                PdfPCell celltblNodata = null;
                celltblNodata=new PdfPCell(new Phrase("No hay Data Disponible",font3));
                celltblNodata.disableBorderSide(Rectangle.BOX);
                celltblNodata.setHorizontalAlignment(Element.ALIGN_CENTER);
                tblNodata.addCell(celltblNodata);
                documento.add(tblNodata);
            }


            PdfPTable tbclientes = new PdfPTable(1);
            tbclientes.setWidthPercentage(100);
            PdfPCell cellTableclientes = null;
            cellTableclientes=new PdfPCell(new Phrase("**************************************CLIENTES***********************************",font5));
            cellTableclientes.disableBorderSide(Rectangle.BOX);
            cellTableclientes.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbclientes.addCell(cellTableclientes);
            documento.add(tbclientes);

            PdfPTable tblvisitados = new PdfPTable(2);
            tblvisitados.setWidthPercentage(100);
            PdfPCell  cellTablevisitados= null;
            cellTablevisitados=new PdfPCell(new Phrase("VISITADOS",font3));
            cellTablevisitados.disableBorderSide(Rectangle.BOX);
            cellTablevisitados.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvisitados.addCell(cellTablevisitados);
            cellTablevisitados=new PdfPCell(new Phrase(String.valueOf(rutaVendedorSQLiteDao.ObtenerCantidadVisitadosRutaVendedor(fechasap,"1")),font3));
            cellTablevisitados.disableBorderSide(Rectangle.BOX);
            cellTablevisitados.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvisitados.addCell(cellTablevisitados);
            cellTablevisitados=new PdfPCell(new Phrase("PROGRAMADOS",font3));
            cellTablevisitados.disableBorderSide(Rectangle.BOX);
            cellTablevisitados.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvisitados.addCell(cellTablevisitados);
            cellTablevisitados=new PdfPCell(new Phrase(String.valueOf(rutaVendedorSQLiteDao.ObtenerCantidadRutaVendedor(fechasap,"1")),font3));
            cellTablevisitados.disableBorderSide(Rectangle.BOX);
            cellTablevisitados.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvisitados.addCell(cellTablevisitados);
            documento.add(tblvisitados);

            PdfPTable tbruta = new PdfPTable(1);
            tbruta.setWidthPercentage(100);
            PdfPCell cellTableruta = null;
            cellTableruta=new PdfPCell(new Phrase("*****************************************RUTA*************************************",font5));
            cellTableruta.disableBorderSide(Rectangle.BOX);
            cellTableruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbruta.addCell(cellTableruta);
            documento.add(tbruta);

            PdfPTable tbltipo = new PdfPTable(3);
            tbltipo.setWidthPercentage(100);
            PdfPCell  cellTableTipo= null;
            cellTableTipo=new PdfPCell(new Phrase("TIPO",font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase("CANTIDAD",font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase("MONTO",font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase("COBRANZA",font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase(String.valueOf(rutaVendedorSQLiteDao.ObtenerCantidadCobranzaRutaVendedor(fechasap,"1")),font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase(String.valueOf(rutaVendedorSQLiteDao.ObtenerMontoCobranzaRutaVendedor(SesionEntity.compania_id,fechasap,"1")),font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase("PEDIDO",font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase(String.valueOf(rutaVendedorSQLiteDao.ObtenerCantidadPedidoRutaVendedor(fechasap,"1")),font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            cellTableTipo=new PdfPCell(new Phrase(String.valueOf(rutaVendedorSQLiteDao.ObtenerMontoPedidoRutaVendedor(SesionEntity.compania_id,fechasap,"1")),font3));
            cellTableTipo.disableBorderSide(Rectangle.BOX);
            cellTableTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltipo.addCell(cellTableTipo);
            documento.add(tbltipo);


            PdfPTable tblefectividad = new PdfPTable(2);
            tblefectividad.setWidthPercentage(100);
            PdfPCell celltblefectividad = null;
            celltblefectividad=new PdfPCell(new Phrase("EFECTIVIDAD PEDIDOS",font3));
            celltblefectividad.disableBorderSide(Rectangle.BOX);
            celltblefectividad.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblefectividad.addCell(celltblefectividad);
            try{
                celltblefectividad=new PdfPCell(new Phrase(
                        Induvis.getAmountRouteeffectiveness(
                                (rutaVendedorSQLiteDao.ObtenerCantidadPedidoRutaVendedor(fechasap,"1")),
                                rutaVendedorSQLiteDao.ObtenerCantidadRutaVendedor(fechasap,"1")
                        ),font3));
            }catch (Exception e)
            {
                e.printStackTrace();
                Log.e("REOS","DocumentCobranzaPDF.generarPdf.e:" + e.toString());
            }
            celltblefectividad.disableBorderSide(Rectangle.BOX);
            celltblefectividad.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblefectividad.addCell(celltblefectividad);
            celltblefectividad=new PdfPCell(new Phrase("EFECTIVIDAD COBRANZA",font3));
            celltblefectividad.disableBorderSide(Rectangle.BOX);
            celltblefectividad.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblefectividad.addCell(celltblefectividad);
            try{
                celltblefectividad=new PdfPCell(new Phrase(
                        Induvis.getAmountRouteeffectiveness(
                                (rutaVendedorSQLiteDao.ObtenerCantidadCobranzaRutaVendedor(fechasap,"1")),
                                rutaVendedorSQLiteDao.ObtenerCantidadRutaVendedor(fechasap,"1")
                        ),font3));
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
            cellTablenoruta=new PdfPCell(new Phrase("****************************************NO RUTA*********************************",font5));
            cellTablenoruta.disableBorderSide(Rectangle.BOX);
            cellTablenoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblnoruta.addCell(cellTablenoruta);
            documento.add(tblnoruta);

            PdfPTable tblvisitadosnoruta = new PdfPTable(2);
            tblvisitadosnoruta.setWidthPercentage(100);
            PdfPCell  cellTablevisitadosnoruta= null;
            cellTablevisitadosnoruta=new PdfPCell(new Phrase("VISITADOS",font3));
            cellTablevisitadosnoruta.disableBorderSide(Rectangle.BOX);
            cellTablevisitadosnoruta.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvisitadosnoruta.addCell(cellTablevisitadosnoruta);
            cellTablevisitadosnoruta=new PdfPCell(new Phrase(String.valueOf(rutaVendedorSQLiteDao.ObtenerCantidadVisitadosRutaVendedor(fechasap,"0")),font3));
            cellTablevisitadosnoruta.disableBorderSide(Rectangle.BOX);
            cellTablevisitadosnoruta.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvisitadosnoruta.addCell(cellTablevisitadosnoruta);
            /*cellTablevisitadosnoruta=new PdfPCell(new Phrase("PROGRAMADOS",font3));
            cellTablevisitadosnoruta.disableBorderSide(Rectangle.BOX);
            cellTablevisitadosnoruta.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblvisitadosnoruta.addCell(cellTablevisitadosnoruta);
            cellTablevisitadosnoruta=new PdfPCell(new Phrase(String.valueOf(rutaVendedorSQLiteDao.ObtenerCantidadRutaVendedor(fechasap,"0")),font3));
            cellTablevisitadosnoruta.disableBorderSide(Rectangle.BOX);
            cellTablevisitadosnoruta.setHorizontalAlignment(Element.ALIGN_LEFT);*/

            tblvisitadosnoruta.addCell(cellTablevisitadosnoruta);
            documento.add(tblvisitadosnoruta);

            PdfPTable tbltiponoruta = new PdfPTable(3);
            tbltiponoruta.setWidthPercentage(100);
            PdfPCell  cellTableTiponoruta= null;
            cellTableTiponoruta=new PdfPCell(new Phrase("TIPO",font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase("CANTIDAD",font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase("MONTO",font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase("COBRANZA",font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(String.valueOf(rutaVendedorSQLiteDao.ObtenerCantidadCobranzaRutaVendedor(fechasap,"0")),font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(String.valueOf(rutaVendedorSQLiteDao.ObtenerMontoCobranzaRutaVendedor(SesionEntity.compania_id,fechasap,"0")) ,font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase("PEDIDO",font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(String.valueOf(rutaVendedorSQLiteDao.ObtenerCantidadPedidoRutaVendedor(fechasap,"0")),font3));
            cellTableTiponoruta.disableBorderSide(Rectangle.BOX);
            cellTableTiponoruta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbltiponoruta.addCell(cellTableTiponoruta);
            cellTableTiponoruta=new PdfPCell(new Phrase(String.valueOf(rutaVendedorSQLiteDao.ObtenerMontoPedidoRutaVendedor(SesionEntity.compania_id,fechasap,"0")),font3));
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
            Toast.makeText(context, "Es necesario que instales algun visor de PDF", Toast.LENGTH_SHORT).show();
        }
    }

}
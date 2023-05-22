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
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Dao.SQLite.QuoteEffectivenessSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Dao.SQLite.VisitaSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import harmony.java.awt.Color;

public class HistoricPromotionPDF extends AppCompatActivity {
    private final static String NOMBRE_DIRECTORIO = "HistoricPromotion";
    private final static String ETIQUETA_ERROR = "ERROR";
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
        } else {

        }
    }

    public void generarPdf(Context context, List<ListaPromocionCabeceraEntity> listPromotion) {
        // Creamos el documento.
        /*Rectangle pagina = new Rectangle(
                36, 36,
                //559
                650
                //, 806
                , 1120
                //PageSize.ARCH_A
        );*/
        Rectangle pagina = new Rectangle(
                36, 36,
                //559
                //650
                842
                //, 806
                //, 1120
                //, 650
                ,595
                //PageSize.ARCH_A
        );
        /*Rectangle pagina = new Rectangle(
                PageSize.A4
        );*/
        Log.e("REOS","HistoricPromotionPDF.generarPdf.pagina.getBottom();:" + pagina.getBottom());
        Log.e("REOS","HistoricPromotionPDF.generarPdf.pagina.getTop();:" + pagina.getTop());
        Log.e("REOS","HistoricPromotionPDF.generarPdf.pagina.getRight();:" + pagina.getRight());
        Log.e("REOS","HistoricPromotionPDF.generarPdf.pagina.getLeft();:" + pagina.getLeft());

        Document documento = new Document(pagina);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        fecha =dateFormat.format(date);

        try {


            File f = crearFichero(fecha+".pdf");

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

            Font font3 = FontFactory.getFont(FontFactory.HELVETICA, 12,
                    Font.NORMAL, Color.black);
            Font font4 = FontFactory.getFont(FontFactory.HELVETICA, 32,
                    Font.BOLD, Color.black);
            Font font5 = FontFactory.getFont(FontFactory.HELVETICA, 16,
                    Font.BOLD, Color.black);
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

            ArrayList<String> ObjList=new ArrayList<>();
            ArrayList<String> promotionObj=new ArrayList<>();

            ArrayList<String> ObjListProduct=new ArrayList<>();
            ArrayList<String> promotionProductObj=new ArrayList<>();

            for(int i=0;i<listPromotion.size();i++)
            {
                ObjList.add(listPromotion.get(i).getLista_promocion());
            }

            for(int j=0;j<ObjList.size();j++)
            {
                if(!promotionObj.isEmpty())
                {
                    int contador=0;
                    for(int l=0;l<promotionObj.size();l++)
                    {
                        if(ObjList.get(j).equals(promotionObj.get(l)))
                        {
                            contador++;
                        }
                    }
                    if(contador==0)
                    {
                        promotionObj.add(ObjList.get(j).toString());
                    }
                }
                else {
                    promotionObj.add(ObjList.get(j).toString());
                }
            }

            for(int j=0;j<promotionObj.size();j++)
            {
                //ObjArrayListaCorrelativo[j].toString();
                Log.e("REOS","HistoricPromotionPDF.generarPdf.promotionObj:" + promotionObj.get(j));
            }

            for(int i=0;i<listPromotion.size();i++)
            {
                ObjListProduct.add(listPromotion.get(i).getProducto_id() + " " + listPromotion.get(i).getProducto());
            }

            for(int j=0;j<ObjListProduct.size();j++)
            {
                if(!promotionProductObj.isEmpty())
                {
                    int contador=0;
                    for(int l=0;l<promotionProductObj.size();l++)
                    {
                        if(ObjListProduct.get(j).equals(promotionProductObj.get(l)))
                        {
                            contador++;
                        }
                    }
                    if(contador==0)
                    {
                        promotionProductObj.add(ObjListProduct.get(j).toString());
                    }
                }
                else {
                    promotionProductObj.add(ObjListProduct.get(j).toString());
                }
            }

            for(int j=0;j<promotionProductObj.size();j++)
            {
                //ObjArrayListaCorrelativo[j].toString();
                Log.e("REOS","HistoricPromotionPDF.generarPdf.promotionProductObj:" + promotionProductObj.get(j));
            }


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
            Log.d("REOS","HistoricPromotionPDF.generarPdf.BuildConfig.FLAVOR:" + BuildConfig.FLAVOR);
            cellTable=new PdfPCell(new Phrase(Induvis.getInformation(BuildConfig.FLAVOR),font3));
            Log.d("REOS","HistoricPromotionPDF.generarPdf.Induvis.getInformation(BuildConfig.FLAVOR):" + Induvis.getInformation(BuildConfig.FLAVOR));
            cellTable.disableBorderSide(Rectangle.BOX);
            cellTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbldireccion.addCell(cellTable);
            cellTable = new PdfPCell(new Phrase(context.getResources().getString(R.string.promotions_vigents).toUpperCase() ,font2));
            cellTable.disableBorderSide(Rectangle.BOX);
            cellTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbldireccion.addCell(cellTable);
            documento.add(tbldireccion);




            for(int g=0;g<promotionObj.size();g++)
            {
                if(g>0)
                {
                    documento.newPage();
                }
                PdfPTable tblListPromotion = new PdfPTable(1);
                tblListPromotion.setWidthPercentage(100);
                PdfPCell celltblListPromotiona = null;
                celltblListPromotiona=new PdfPCell(new Phrase(promotionObj.get(g).toString(),font2));
                //celltblListPromotiona.disableBorderSide(Rectangle.BOX);
                celltblListPromotiona.setHorizontalAlignment(Element.ALIGN_CENTER);
                tblListPromotion.addCell(celltblListPromotiona);
                documento.add(tblListPromotion);

                float[] columnWidths = {7.5f, 2.5f,7.5f,2.5f};
                PdfPTable tablePromotionHeadTittle = new PdfPTable(columnWidths);
                tablePromotionHeadTittle.setWidthPercentage(100);
                PdfPCell cellTablePromotionHeadTittle = null;
                Log.d("REOS","HistoricPromotionPDF.generarPdf.BuildConfig.FLAVOR:" + BuildConfig.FLAVOR);
                cellTablePromotionHeadTittle=new PdfPCell(new Phrase("Producto\nVenta".toUpperCase(),font5));
                //cellTablePromotionHeadTittle.disableBorderSide(Rectangle.BOX);
                cellTablePromotionHeadTittle.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablePromotionHeadTittle.addCell(cellTablePromotionHeadTittle);
                cellTablePromotionHeadTittle=new PdfPCell(new Phrase("Cantidad\nVenta".toUpperCase(),font5));
                //cellTablePromotionHeadTittle.disableBorderSide(Rectangle.BOX);
                cellTablePromotionHeadTittle.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablePromotionHeadTittle.addCell(cellTablePromotionHeadTittle);
                cellTablePromotionHeadTittle = new PdfPCell(new Phrase("Producto\nRegalo".toUpperCase() ,font5));
                //cellTablePromotionHeadTittle.disableBorderSide(Rectangle.BOX);
                cellTablePromotionHeadTittle.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablePromotionHeadTittle.addCell(cellTablePromotionHeadTittle);
                cellTablePromotionHeadTittle = new PdfPCell(new Phrase("Cantidad\nRegalo".toUpperCase() ,font5));
                //cellTablePromotionHeadTittle.disableBorderSide(Rectangle.BOX);
                cellTablePromotionHeadTittle.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablePromotionHeadTittle.addCell(cellTablePromotionHeadTittle);
                documento.add(tablePromotionHeadTittle);
                for (int i = 0; i < listPromotion.size(); i++) {

                    if(promotionObj.get(g).equals(listPromotion.get(i).getLista_promocion())) {

                        float[] columnWidths1 = {7.5f, 2.5f,7.5f,2.5f};
                        PdfPTable tablePromotionHead = new PdfPTable(columnWidths1);
                        tablePromotionHead.setWidthPercentage(100);
                        PdfPCell cellTablePromotionHead = null;
                        cellTablePromotionHead = null;
                        Log.d("REOS", "HistoricPromotionPDF.generarPdf.BuildConfig.FLAVOR:" + BuildConfig.FLAVOR);
                        if(listPromotion.get(i).getCount().equals("1"))
                        {
                            cellTablePromotionHead = new PdfPCell(new Phrase(listPromotion.get(i).getProducto_id() + " " + listPromotion.get(i).getProducto(), font3));
                            cellTablePromotionHead.disableBorderSide(Rectangle.BOTTOM);
                            cellTablePromotionHead.setHorizontalAlignment(Element.ALIGN_LEFT);
                            tablePromotionHead.addCell(cellTablePromotionHead);
                        }
                        else{
                            cellTablePromotionHead = new PdfPCell(new Phrase("", font3));
                            cellTablePromotionHead.disableBorderSide(Rectangle.TOP);
                            cellTablePromotionHead.disableBorderSide(Rectangle.BOTTOM);
                            cellTablePromotionHead.setHorizontalAlignment(Element.ALIGN_LEFT);
                            tablePromotionHead.addCell(cellTablePromotionHead);
                        }

                        cellTablePromotionHead = new PdfPCell(new Phrase(listPromotion.get(i).getCantidadcompra(), font3));
                        //cellTablePromotionHead.disableBorderSide(Rectangle.BOX);
                        cellTablePromotionHead.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tablePromotionHead.addCell(cellTablePromotionHead);

                        for (int j = 0; j < listPromotion.get(i).getListaPromocionDetalleEntities().size(); j++) {
                            if(j==0){
                                    cellTablePromotionHead = new PdfPCell(new Phrase(listPromotion.get(i).getListaPromocionDetalleEntities().get(j).getProducto_id() + " " + listPromotion.get(i).getListaPromocionDetalleEntities().get(j).getProducto(), font3));
                                    //cellTablePromotionHead.disableBorderSide(Rectangle.BOX);
                                    cellTablePromotionHead.setHorizontalAlignment(Element.ALIGN_LEFT);
                                    tablePromotionHead.addCell(cellTablePromotionHead);
                                    cellTablePromotionHead = new PdfPCell(new Phrase(listPromotion.get(i).getListaPromocionDetalleEntities().get(j).getCantidad(), font3));
                                    //cellTablePromotionHead.disableBorderSide(Rectangle.BOX);
                                    cellTablePromotionHead.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    tablePromotionHead.addCell(cellTablePromotionHead);
                            }else {
                                cellTablePromotionHead = new PdfPCell(new Phrase("", font3));
                                cellTablePromotionHead.disableBorderSide(Rectangle.BOX);
                                cellTablePromotionHead.setHorizontalAlignment(Element.ALIGN_CENTER);
                                tablePromotionHead.addCell(cellTablePromotionHead);
                                cellTablePromotionHead = new PdfPCell(new Phrase("", font3));
                                cellTablePromotionHead.disableBorderSide(Rectangle.BOX);
                                cellTablePromotionHead.setHorizontalAlignment(Element.ALIGN_CENTER);
                                tablePromotionHead.addCell(cellTablePromotionHead);
                                cellTablePromotionHead = new PdfPCell(new Phrase(listPromotion.get(i).getListaPromocionDetalleEntities().get(j).getProducto_id() + " " + listPromotion.get(i).getListaPromocionDetalleEntities().get(j).getProducto(), font3));
                                //cellTablePromotionHead.disableBorderSide(Rectangle.BOX);
                                cellTablePromotionHead.setHorizontalAlignment(Element.ALIGN_LEFT);
                                tablePromotionHead.addCell(cellTablePromotionHead);
                                cellTablePromotionHead = new PdfPCell(new Phrase(listPromotion.get(i).getListaPromocionDetalleEntities().get(j).getCantidad(), font3));
                                //14cellTablePromotionHead.disableBorderSide(Rectangle.BOX);
                                cellTablePromotionHead.setHorizontalAlignment(Element.ALIGN_CENTER);
                                tablePromotionHead.addCell(cellTablePromotionHead);
                            }
                        }
                        documento.add(tablePromotionHead);
                    }
                }

            }


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
            OpenDocumentPDF(fecha,context);
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

    private void OpenDocumentPDF(String fechasap,Context context)
    {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator
                    +NOMBRE_DIRECTORIO+File.separator+fechasap+".pdf");

            Uri excelPath = FileProvider.getUriForFile(context, context.getPackageName(), file);

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

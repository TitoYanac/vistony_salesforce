package com.vistony.salesforce.Controller.Utilitario;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

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

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import harmony.java.awt.Color;
import com.journeyapps.barcodescanner.*;
import com.google.zxing.BarcodeFormat;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

public class DocumentoCobranzaPDF extends AppCompatActivity {

    private final static String NOMBRE_DIRECTORIO = "Cobranza";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
        } else {

        }
    }

    //Procedimiento para mostrar el documento PDF generado
    public void showPdfFile(String fileName, Context context) {
        Toast.makeText(context, "Leyendo documento", Toast.LENGTH_LONG).show();

        File file = null;
        try {
            file = crearFichero(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent2 = new Intent(Intent.ACTION_VIEW);
        //Uri urifile = Uri.fromFile(file);
        Uri urifile = FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider", file);
        intent2.setDataAndType(urifile, "application/pdf");

        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(intent2);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "No Application Available to View PDF", Toast.LENGTH_SHORT).show();
        }
    }

    public void generarPdf(Context context, ArrayList<ListaClienteDetalleEntity> Lista, String fuerzatrabajo_id, String nombrefuerzatrabajo, String recibo, String fecharecibo,String horarecibo) {
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-Lista: "+Lista.size());
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-fuerzatrabajo_id: "+fuerzatrabajo_id);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-nombrefuerzatrabajo: "+nombrefuerzatrabajo);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-recibo: "+recibo);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-fecharecibo: "+fecharecibo);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-horarecibo: "+horarecibo);
        String cliente_id="",nombrecliente="",direccion="",fechaemision="",fechavencimiento="",nrodocumento="",documento_id="",importe="",saldo="",cobrado="",nuevo_saldo=""
                ;
        for(int i=0;i<Lista.size();i++)
        {
            cliente_id=Lista.get(i).getCliente_id();
            nombrecliente=Lista.get(i).getNombrecliente();
            direccion=Lista.get(i).getDireccion();
            fechaemision=Lista.get(i).getFechaemision();
            fechavencimiento=Lista.get(i).getFechavencimiento();
            nrodocumento=Lista.get(i).getNrodocumento();
            documento_id=Lista.get(i).getDocumento_id();
            importe=Lista.get(i).getImporte();
            saldo=Lista.get(i).getSaldo();
            cobrado=Lista.get(i).getCobrado();
            nuevo_saldo=Lista.get(i).getNuevo_saldo();


        }


        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-cliente_id: "+cliente_id);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-nombrecliente: "+nombrecliente);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-nrodocumento: "+nrodocumento);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-importe: "+importe);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-saldo: "+saldo);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-cobrado: "+cobrado);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-nuevo_saldo: "+nuevo_saldo);
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

        try {
            Log.e("REOS","DocumentoCobranzaPDF-generarPdf-EntroalTry1");
            CifradoController cifradoController=new CifradoController();
            String encData="";
            try {
                Log.e("REOS","DocumentoCobranzaPDF-generarPdf-EntroalTryCifrado");
                encData= CifradoController.encrypt("Vistony2019*".getBytes("UTF-16LE"), recibo.getBytes("UTF-16LE"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmapqr = barcodeEncoder.encodeBitmap(encData, BarcodeFormat.QR_CODE, 400, 400);

            ByteArrayOutputStream streamqr = new ByteArrayOutputStream();

            bitmapqr.compress(Bitmap.CompressFormat.PNG, 50, streamqr);
            Image imagenqr = Image.getInstance(streamqr.toByteArray());
            imagenqr.setAlignment(Element.ALIGN_CENTER);

            File f = crearFichero(recibo+".pdf");

            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream(f.getAbsolutePath());

            // Asociamos el flujo que acabamos de crear al documento.
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);
            //Calendar now = GregorianCalendar.getInstance();
            //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);

            documento.open();

            /*if(!BuildConfig.FLAVOR.equals("peru"))
            {
                PdfContentByte waterMar = writer.getDirectContentUnder();
                // Comience a configurar la marca de agua
                waterMar.beginText();
                // Establecer transparencia de marca de agua
                PdfGState gs = new PdfGState();
                // Establece la opacidad de la fuente de relleno en0.4f
                gs.setFillOpacity(0.1f);
                //
                Bitmap bitmap2 = null;
                bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);

                ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                Image imagen2 = Image.getInstance(stream2.toByteArray());
                imagen2.setAlignment(Element.ALIGN_CENTER);
                //

                //Image image = Image.getInstance("d:/tomatocc.jpg");
                // establecer coordenadas posición absoluta X Y
                imagen2.setAbsolutePosition(100, 500);
                // Establecer el radio de rotación
                imagen2.setRotation(0); // Rotar radianes
                // Establecer el ángulo de rotación
                imagen2.setRotationDegrees(0);// Ángulo de rotación
                // Establecer zoom proporcional
                imagen2.scalePercent(90); // Escala proporcionalmente
                imagen2.scaleAbsolute(500, 500); // Tamaño personalizado
                // Establecer transparencia
                waterMar.setGState(gs);
                // Añadir imagen de marca de agua
                waterMar.addImage(imagen2);
                // Establecer transparencia
                waterMar.setGState(gs);
                // Finalizar configuración
                waterMar.endText();
                waterMar.stroke();
            }*/

            Font font = FontFactory.getFont(FontFactory.HELVETICA, 28,
                    Font.BOLD, Color.black);
            Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 20,
                    Font.BOLD, Color.black);

            Font font3 = FontFactory.getFont(FontFactory.HELVETICA, 16,
                    Font.BOLD, Color.black);
            Font font4 = FontFactory.getFont(FontFactory.HELVETICA, 32,
                    Font.BOLD, Color.black);
            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.
            Bitmap bitmap=null;

            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo_negro_vistony);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100 ,stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            imagen.setAlignment(Element.ALIGN_CENTER);
            //documento.add(waterMar);
            documento.add(imagen);


            PdfPTable tblcliente = new PdfPTable(1);
            tblcliente.setWidthPercentage(100);
            PdfPCell cellTable = null;


            Log.d("REOS","DocumentCobranzaPDF.generarPdf.BuildConfig.FLAVOR:" + BuildConfig.FLAVOR);
            cellTable=new PdfPCell(new Phrase(Induvis.getInformation(BuildConfig.FLAVOR),font3));
            Log.d("REOS","DocumentCobranzaPDF.generarPdf.Induvis.getInformation(BuildConfig.FLAVOR):" + Induvis.getInformation(BuildConfig.FLAVOR));
            cellTable.disableBorderSide(Rectangle.BOX);
            cellTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblcliente.addCell(cellTable);
            cellTable = new PdfPCell(new Phrase(cliente_id,font));
            cellTable.disableBorderSide(Rectangle.BOX);
            cellTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblcliente.addCell(cellTable);
            cellTable = new PdfPCell(new Phrase(nombrecliente,font ));

            cellTable.disableBorderSide(Rectangle.BOX);

            cellTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblcliente.addCell(cellTable);
            documento.add(tblcliente);

            PdfPTable tblcobrador = new PdfPTable(1);
            tblcobrador.setWidthPercentage(100);
            PdfPCell cellcobrador = new PdfPCell(new Phrase("*********************DATOS COBRADOR*******************",font2 ));
            cellcobrador.disableBorderSide(Rectangle.BOX);
            cellcobrador.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblcobrador.addCell(cellcobrador);
            cellcobrador = new PdfPCell(new Phrase(fuerzatrabajo_id+" "+nombrefuerzatrabajo,font ));

            cellcobrador.disableBorderSide(Rectangle.BOX);
            cellcobrador.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblcobrador.addCell(cellcobrador);
            cellcobrador = new PdfPCell(new Phrase("*********************DATOS DOCUMENTO*******************",font2 ));
            cellcobrador.disableBorderSide(Rectangle.BOX);
            cellcobrador.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblcobrador.addCell(cellcobrador);
            documento.add(tblcobrador);
            Log.e("REOS","DocumentoCobranzaPDF-generarPdf-Datosdocumento");
            PdfPTable tbl = new PdfPTable(2);
            tbl.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell(new Phrase("Fecha:",font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.FechaHora:" + Induvis.getDate(BuildConfig.FLAVOR,fecharecibo)+" "+Induvis.getTime(BuildConfig.FLAVOR,horarecibo));
            cell = new PdfPCell(new Phrase(Induvis.getDate(BuildConfig.FLAVOR,fecharecibo)+" "+Induvis.getTime(BuildConfig.FLAVOR,horarecibo),font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase("Recibo:",font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.recibo:" + recibo);
            cell = new PdfPCell(new Phrase(recibo,font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase("Documento:",font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.nrodocumento:" + nrodocumento);
            cell = new PdfPCell(new Phrase(nrodocumento,font));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase("Importe         :",font4));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.disableBorderSide(Rectangle.BOX);
            tbl.addCell(cell);
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.importe:" + Convert.currencyForView(importe));
            cell = new PdfPCell(new Phrase(Convert.currencyForView(importe),font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase("Saldo            :",font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.saldo:" + Convert.currencyForView(saldo));
            cell = new PdfPCell(new Phrase(Convert.currencyForView(saldo),font4 ));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase("Cobrado       :",font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.cobrado:" + Convert.currencyForView(cobrado));
            cell = new PdfPCell(new Phrase(Convert.currencyForView(cobrado),font4 ));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase("Nuevo Saldo:",font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.nuevo_saldo:" + Convert.currencyForView(nuevo_saldo));
            cell = new PdfPCell(new Phrase(Convert.currencyForView(nuevo_saldo),font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            //tbl.setWidthPercentage(100);
            tbl.addCell(imagenqr);
            documento.add(tbl);
            documento.add(imagenqr);

        } catch (DocumentException e) {
            e.printStackTrace();
            Log.e("REOS","DocumentCobranzaPDF-generarPdf-DocumentException.E" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("REOS","DocumentCobranzaPDF-generarPdf-IOException.E" + e.toString());
        } catch (Exception e){
            e.printStackTrace();
            Log.e("REOS","DocumentCobranzaPDF-generarPdf-Exception.E" + e.toString());
        }finally {
            // Cerramos el documento.
            documento.close();

            ///////////////////ABRIR PDF////////////////////////
            Log.e("REOS","DocumentCobranzaPDF-generarPdf-SesionEntity.Print" + SesionEntity.Print);
            /*if(SesionEntity.Print.equals("N"))
            {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator
                        + NOMBRE_DIRECTORIO + File.separator + recibo + ".pdf");

                Uri excelPath = FileProvider.getUriForFile(context, context.getPackageName(), file);

                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(excelPath, "application/pdf");
                target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }*/
            switch (BuildConfig.FLAVOR){
                case "bolivia":
                case "ecuador":
                case "chile":
                case "paraguay":
                    OpenDocumentPDF(recibo,context);
                    break;
                case "peru":
                    if(SesionEntity.Print.equals("N"))
                    {
                        OpenDocumentPDF(recibo,context);
                    }
                    break;
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
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
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

    private void OpenDocumentPDF(String recibo,Context context)
    {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator
                    +NOMBRE_DIRECTORIO+File.separator+recibo+".pdf");

            Uri  excelPath = FileProvider.getUriForFile(context, context.getPackageName(), file);

            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(excelPath,"application/pdf");
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(target);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Es necesario que instales algun visor de PDF", Toast.LENGTH_SHORT).show();
        }
    }

}

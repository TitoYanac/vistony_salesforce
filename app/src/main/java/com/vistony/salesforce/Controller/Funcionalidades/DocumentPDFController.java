package com.vistony.salesforce.Controller.Funcionalidades;

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

public class DocumentPDFController extends AppCompatActivity {

    private final static String NOMBRE_DIRECTORIO = "MiPdf";
    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";
    private final static String ETIQUETA_ERROR = "ERROR";


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

    public void generarPdf(Context context, ArrayList<ListaClienteDetalleEntity> Lista, String fuerzatrabajo_id, String nombrefuerzatrabajo, String recibo, String fecharecibo) {
        Log.e("jpcm","la fecha e snull->"+fecharecibo);
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
        // Creamos el documento.
        Rectangle pagina = new Rectangle(
                36, 36,
                //559
                650
                //, 806
                , 1120
                //PageSize.ARCH_A
        );
        Document documento = new Document(
                pagina
        );

        try {

            CifradoController cifradoController=new CifradoController();
            String encData="";
            try {
                encData= CifradoController.encrypt("Vistony2019*".getBytes("UTF-16LE"), recibo.getBytes("UTF-16LE"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmapqr = barcodeEncoder.encodeBitmap(encData, BarcodeFormat.QR_CODE, 400, 400);

            ByteArrayOutputStream streamqr = new ByteArrayOutputStream();
            Log.e("JPCM","Error a comprimir");

            bitmapqr.compress(Bitmap.CompressFormat.PNG, 100, streamqr);
            Image imagenqr = Image.getInstance(streamqr.toByteArray());
            imagenqr.setAlignment(Element.ALIGN_CENTER);




            //image.setImageBitmap(bitmap);


            File f = crearFichero(recibo+".pdf");

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
                    Font.BOLD, Color.black);
            Font font4 = FontFactory.getFont(FontFactory.HELVETICA, 32,
                    Font.BOLD, Color.black);
            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.
            Bitmap bitmap=null;

            if(SesionEntity.compania_id.equals("C001"))
            {
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo_negro_vistony);
            }
            else if(SesionEntity.compania_id.equals("C011")) {
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo_bluker_negro);
            }
            else if(SesionEntity.compania_id.equals("C013"))
            {
                //bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.rofalab_negro_300_90);
                bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.logo_rofalab_negro2);
            }else{
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo_negro_vistony);
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100 ,stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            imagen.setAlignment(Element.ALIGN_CENTER);
            documento.add(imagen);

            PdfPTable tblcliente = new PdfPTable(1);
            tblcliente.setWidthPercentage(100);
            PdfPCell cellTable = null;

            if(SesionEntity.compania_id.equals("C001") )
            {
                cellTable=new PdfPCell(new Phrase(
                        "\n R.U.C N° 20102306598 " +
                                "Mz. B1 Lt. 1 - Parque Industrial de Ancón - Acompia " +
                                "Central: (01) 5521325 E-mail: ventas@vistony.com   " +
                                " Web: www.vistony.com"+
                                "",font3));
            }else if(SesionEntity.compania_id.equals("C011"))
            {
                cellTable= new PdfPCell(new Phrase(
                        "\n R.U.C N° 20536335448 - " +
                                "Mz. B1 Lt. 1 - Parque Industrial de Ancón - Acompia (Km. 46.5) LIMA - ANCÓN - " +
                                "Central: (01) 5521325"+
                                "",font3));
            }else if(SesionEntity.compania_id.equals("C013"))
            {
                cellTable= new PdfPCell(new Phrase(
                        "\n R.U.C N° 20601500605 - " +
                                "Oficina Principal: Mz. D Lt. 3 La Calichera (Altura Km 46 Pan.Norte) Ancon - Lima - " +
                                "Telf: 993088798 E-mail: rofalab@tolbrin.com"+
                                "",font3));
            }

            else
                {
                    cellTable=new PdfPCell(new Phrase(
                            "\n R.U.C N° 20102306598 " +
                                    "Mz. B1 Lt. 1 - Parque Industrial de Ancón - Acompia " +
                                    "Central: (01) 5521325 E-mail: lubricantes@vistony.com   " +
                                    " Web: www.vistony.com"+
                                    "",font3));
                }

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

            PdfPTable tbl = new PdfPTable(2);
            tbl.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell(new Phrase("Fecha:",font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase(fecharecibo,font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase("Recibo:",font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase(recibo,font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase("Documento:",font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase(nrodocumento,font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase("Importe         :S/.",font4));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.disableBorderSide(Rectangle.BOX);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase(importe,font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase("Saldo            :S/.",font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase(saldo,font4 ));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase("Cobrado       :S/.",font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase(cobrado,font4 ));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase("Nuevo Saldo:S/.",font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase(nuevo_saldo,font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            //tbl.setWidthPercentage(100);
            tbl.addCell(imagenqr);
            documento.add(tbl);
            documento.add(imagenqr);

        } catch (DocumentException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } catch (IOException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } catch (Exception e)
        {
            Log.e("JPCM","capturado->"+ e);
        }
        finally {
            // Cerramos el documento.
            documento.close();
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
}

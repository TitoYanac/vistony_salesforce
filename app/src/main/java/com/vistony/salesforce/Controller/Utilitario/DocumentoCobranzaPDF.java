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
import java.util.ArrayList;

import harmony.java.awt.Color;
import com.journeyapps.barcodescanner.*;
import com.google.zxing.BarcodeFormat;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.MenuView;

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

    public void generarPdf(
            Context context, ArrayList<ListaClienteDetalleEntity> Lista, String fuerzatrabajo_id, String nombrefuerzatrabajo, String recibo, String fecharecibo,String horarecibo,String type_description) {
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-Lista: "+Lista.size());
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-fuerzatrabajo_id: "+fuerzatrabajo_id);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-nombrefuerzatrabajo: "+nombrefuerzatrabajo);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-recibo: "+recibo);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-fecharecibo: "+fecharecibo);
        Log.e("REOS","DocumentoCobranzaPDF-generarPdf-horarecibo: "+horarecibo);
        String cliente_id="",nombrecliente="",direccion="",fechaemision="",fechavencimiento="",nrodocumento="",documento_id="",importe="",saldo="",cobrado="",nuevo_saldo="",type="";
        //DeleteDocumentPDF(recibo,context);
        //Get Date User
        UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(context);
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

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
        type=type_description;
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
            Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-EntroalTry1");
            CifradoController cifradoController = new CifradoController();
            String encData = "";
            try {
                Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-EntroalTryCifrado");
                encData = CifradoController.encrypt("Vistony2019*".getBytes("UTF-16LE"), recibo.getBytes("UTF-16LE"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmapqr = barcodeEncoder.encodeBitmap(encData, BarcodeFormat.QR_CODE, 400, 400);

            ByteArrayOutputStream streamqr = new ByteArrayOutputStream();

            bitmapqr.compress(Bitmap.CompressFormat.PNG, 50, streamqr);
            Image imagenqr = Image.getInstance(streamqr.toByteArray());
            imagenqr.setAlignment(Element.ALIGN_CENTER);

            File f = crearFichero(recibo + ".pdf");

            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream(f.getAbsolutePath());

            // Asociamos el flujo que acabamos de crear al documento.
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);
            writer.close();
            //Calendar now = GregorianCalendar.getInstance();
            //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);

            documento.open();
            Font font,font3,font1,font2,font4;

            if(SesionEntity.perfil_id.equals("Chofer")||SesionEntity.perfil_id.equals("CHOFER"))
            {
                font = FontFactory.getFont(FontFactory.HELVETICA, 28,
                        Font.BOLD, Color.black);
                font2 = FontFactory.getFont(FontFactory.HELVETICA, 20,
                        Font.BOLD, Color.black);

                font3 = FontFactory.getFont(FontFactory.HELVETICA, 16,
                        Font.BOLD, Color.black);
                font4 = FontFactory.getFont(FontFactory.HELVETICA, 28,
                        Font.BOLD, Color.black);
            }else {
                font = FontFactory.getFont(FontFactory.HELVETICA, 28,
                        Font.BOLD, Color.black);
                font2 = FontFactory.getFont(FontFactory.HELVETICA, 20,
                        Font.BOLD, Color.black);

                font3 = FontFactory.getFont(FontFactory.HELVETICA, 16,
                        Font.BOLD, Color.black);
                font4 = FontFactory.getFont(FontFactory.HELVETICA, 32,
                        Font.BOLD, Color.black);
            }

            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.
            Bitmap bitmap = null;



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
            PdfPCell cellcobrador = new PdfPCell(new Phrase(context.getResources().getString(R.string.separator_short)+context.getResources().getString(R.string.data).toUpperCase()+" "+context.getResources().getString(R.string.debt_collector).toUpperCase()+context.getResources().getString(R.string.separator_short),font2 ));
            cellcobrador.disableBorderSide(Rectangle.BOX);
            cellcobrador.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblcobrador.addCell(cellcobrador);
            cellcobrador = new PdfPCell(new Phrase(fuerzatrabajo_id+" "+nombrefuerzatrabajo,font ));

            cellcobrador.disableBorderSide(Rectangle.BOX);
            cellcobrador.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblcobrador.addCell(cellcobrador);
            cellcobrador = new PdfPCell(new Phrase(context.getResources().getString(R.string.separator_short)+context.getResources().getString(R.string.data).toUpperCase()+" "+context.getResources().getString(R.string.documents).toUpperCase()+context.getResources().getString(R.string.separator_short),font2 ));
            cellcobrador.disableBorderSide(Rectangle.BOX);
            cellcobrador.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblcobrador.addCell(cellcobrador);
            documento.add(tblcobrador);
            Log.e("REOS","DocumentoCobranzaPDF-generarPdf-Datosdocumento");
            PdfPTable tbl = new PdfPTable(2);
            tbl.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell(new Phrase(context.getResources().getString(R.string.date),font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.FechaHora:" + Induvis.getDate(BuildConfig.FLAVOR,fecharecibo)+" "+Induvis.getTime(BuildConfig.FLAVOR,horarecibo));
            cell = new PdfPCell(new Phrase(Induvis.getDate(BuildConfig.FLAVOR,fecharecibo)+" "+Induvis.getTime(BuildConfig.FLAVOR,horarecibo),font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase(context.getResources().getString(R.string.receip),font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.recibo:" + recibo);
            cell = new PdfPCell(new Phrase(recibo,font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);

            Log.e("REOS", "DocumentCobranzaPDF.generarPdf.BuildConfig.FLAVOR.:" + BuildConfig.FLAVOR);
            if(SesionEntity.perfil_id.equals("Chofer")||SesionEntity.perfil_id.equals("CHOFER"))
            {
                if(!BuildConfig.FLAVOR.equals("paraguay"))
                {
                    cell = new PdfPCell(new Phrase(context.getResources().getString(R.string.type_collection), font4));
                    cell.disableBorderSide(Rectangle.BOX);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    tbl.addCell(cell);
                    Log.e("REOS", "DocumentCobranzaPDF.generarPdf.type:" + type);

                    cell = new PdfPCell(new Phrase(type, font4));
                    cell.disableBorderSide(Rectangle.BOX);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tbl.addCell(cell);
                }
            }

            cell = new PdfPCell(new Phrase(context.getResources().getString(R.string.documents),font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.nrodocumento:" + nrodocumento);
            cell = new PdfPCell(new Phrase(nrodocumento,font));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase(context.getResources().getString(R.string.amount),font4));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.disableBorderSide(Rectangle.BOX);
            tbl.addCell(cell);
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.importe:" + Convert.currencyForView(importe));
            cell = new PdfPCell(new Phrase(Convert.currencyForView(importe),font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase(context.getResources().getString(R.string.balance),font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.saldo:" + Convert.currencyForView(saldo));
            cell = new PdfPCell(new Phrase(Convert.currencyForView(saldo),font4 ));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase(context.getResources().getString(R.string.amount_charged),font4));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cell);
            Log.e("REOS","DocumentCobranzaPDF.generarPdf.cobrado:" + Convert.currencyForView(cobrado));
            cell = new PdfPCell(new Phrase(Convert.currencyForView(cobrado),font4 ));
            cell.disableBorderSide(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase(context.getResources().getString(R.string.new_balance),font4));
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
                case "espania":
                    OpenDocumentPDF(recibo,context);
                    break;
                case "peru":
                case "perurofalab":
                case "marruecos":
                    if(SesionEntity.Print.equals("N"))
                    {
                        OpenDocumentPDF(recibo,context);
                    }
                    else {
                        //DeleteDocumentPDF(recibo,context);
                        File file = new File(Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                                NOMBRE_DIRECTORIO);
                        String Cadenafile = "";
                        Cadenafile = String.valueOf(file);
                        String ruta = Cadenafile + "/" + recibo + ".pdf";
                        MenuView menuView=new MenuView();
                        menuView.getPrinterInstance().printPdf(ruta, 500, 0, 0, 0, 20);
                    }
                    //OpenDocumentPDF(recibo,context);
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
            Toast.makeText(context, context.getResources().getText(R.string.mse_necessary_install_PDF), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean DeleteDocumentPDF(String recibo,Context context)
    {
        boolean status=false;
        Log.e("REOS","DocumentCobranzaPDF-DeleteDocumentPDF-Inicio");
        try {
            status = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator
                    +NOMBRE_DIRECTORIO+File.separator+recibo+".pdf").delete();
            Log.e("REOS","DocumentCobranzaPDF-DeleteDocumentPDF-ruta"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator
                    +NOMBRE_DIRECTORIO+File.separator+recibo+".pdf");
            //Uri  excelPath = FileProvider.getUriForFile(context, context.getPackageName(), file);
            //Intent target = new Intent(Intent.ACTION_VIEW);
            //target.setDataAndType(excelPath,"application/pdf");
            //target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //context.startActivity(target);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("REOS","DocumentCobranzaPDF-DeleteDocumentPDF-error" + e.toString());
            Toast.makeText(context, "No se pudo eliminar el Archivi PDF", Toast.LENGTH_SHORT).show();
        }
        Log.e("REOS","DocumentCobranzaPDF-DeleteDocumentPDF-Finalizo");
        return status;
    }

}

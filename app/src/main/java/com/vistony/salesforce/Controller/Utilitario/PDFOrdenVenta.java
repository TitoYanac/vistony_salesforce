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
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Dao.SQLIte.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLIte.TerminoPagoSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaDetallePromocionSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.TerminoPagoSQLiteEntity;
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

import harmony.java.awt.Color;

public class PDFOrdenVenta extends AppCompatActivity {

    private final static String NOMBRE_DIRECTORIO = "OrdenVenta";
    private final static String ETIQUETA_ERROR = "ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
        } else {

        }
    }

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

    public void generarPdf(Context context, ArrayList<OrdenVentaCabeceraSQLiteEntity> ListaOrdenVentaCabecera, ArrayList<OrdenVentaDetallePromocionSQLiteEntity> ListaOrdenVentaDetalle ) {

        String cliente_id="",nombrecliente="",direccion="",fechaemision="",terminopago="",subtotal="",igv="",descuento="",total="",ordenventa_id="",ordenventa_erp_id="",vendedor="",moneda="";
                ;
        ClienteSQlite clienteSQlite =new ClienteSQlite(context);
        TerminoPagoSQLiteDao terminoPagoSQLiteDao=new TerminoPagoSQLiteDao(context);
        ArrayList<ClienteSQLiteEntity> listaClienteSQLite=new ArrayList<>();
        ArrayList<TerminoPagoSQLiteEntity> listaTerminoPago=new ArrayList<>();

        for(int i=0;i<ListaOrdenVentaCabecera.size();i++)
        {
            ordenventa_id=ListaOrdenVentaCabecera.get(i).getOrdenventa_id();
            ordenventa_erp_id=ListaOrdenVentaCabecera.get(i).getOrdenventa_ERP_id();
            //vendedor=SesionEntity.fuerzatrabajo_id+" "+SesionEntity.nombrefuerzadetrabajo;
            cliente_id=ListaOrdenVentaCabecera.get(i).getCliente_id();
            listaClienteSQLite= clienteSQlite.ObtenerDatosCliente(cliente_id,SesionEntity.compania_id);
            for(int j=0;j<listaClienteSQLite.size();j++)
            {
                nombrecliente=listaClienteSQLite.get(j).getNombrecliente();
                direccion=listaClienteSQLite.get(j).getDireccion();
            }

            listaTerminoPago=terminoPagoSQLiteDao.ObtenerTerminoPagoporID
                    (ListaOrdenVentaCabecera.get(i).getTerminopago_id(),SesionEntity.compania_id);
            fechaemision=ListaOrdenVentaCabecera.get(i).getFecharegistro();
            for(int g=0;g<listaTerminoPago.size();g++)
            {
                terminopago=listaTerminoPago.get(g).getTerminopago();
            }


            subtotal=ListaOrdenVentaCabecera.get(i).getMontosubtotal();
            igv=ListaOrdenVentaCabecera.get(i).getMontoimpuesto();
            descuento=ListaOrdenVentaCabecera.get(i).getMontodescuento();
            total=ListaOrdenVentaCabecera.get(i).getMontototal();
            moneda=ListaOrdenVentaCabecera.get(i).getMoneda_id();
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
            File f = crearFichero(ordenventa_id+".pdf");

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
            cellTable = new PdfPCell(new Phrase("************************************GENERAL*************************************",font3));
            cellTable.disableBorderSide(Rectangle.BOX);
            cellTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblcliente.addCell(cellTable);
            documento.add(tblcliente);


            PdfPTable tblgeneral = new PdfPTable(2);
            tblgeneral.setWidthPercentage(100);
            PdfPCell cellgneral = new PdfPCell(new Phrase("Codigo Orden:",font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            //cellgneral = new PdfPCell(new Phrase(ordenventa_id,font3));
            cellgneral = new PdfPCell(new Phrase(ordenventa_erp_id,font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("RUC/DNI:",font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(cliente_id,font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("Nombres:",font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(nombrecliente,font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("Direccion:",font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(direccion,font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("Fecha:",font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(fechaemision,font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("Cond.Vent:",font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(terminopago,font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            //documento.add(tblgeneral);
            cellgneral = new PdfPCell(new Phrase("Vendedor:",font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(SesionEntity.fuerzatrabajo_id+" "+SesionEntity.nombrefuerzadetrabajo,font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("Moneda:",font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(moneda,font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);

            documento.add(tblgeneral);



            PdfPTable tblDetalle = new PdfPTable(1);
            tblDetalle.setWidthPercentage(100);
            PdfPCell cellineas = null;
            cellineas = new PdfPCell(new Phrase("************************************DETALLE**************************************",font3));
            cellineas.disableBorderSide(Rectangle.BOX);
            cellineas.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblDetalle.addCell(cellineas);
            documento.add(tblDetalle);

            //float[] columnWidths = {1.5f, 6f, 1.5f,2f,2f,2f};
            float[] columnWidths = {1f,2.5f, 9f,1.5f,1.5f,2f};
            PdfPTable tblLineas = new PdfPTable(columnWidths);
            tblLineas.setWidthPercentage(100);
            PdfPCell cellLineasDetalle = null;
            cellLineasDetalle = new PdfPCell(new Phrase("Id",font3));
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblLineas.addCell(cellLineasDetalle);
            cellLineasDetalle = new PdfPCell(new Phrase("Codigo",font3));
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblLineas.addCell(cellLineasDetalle);
            cellLineasDetalle = new PdfPCell(new Phrase("Producto",font3));
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblLineas.addCell(cellLineasDetalle);
            cellLineasDetalle = new PdfPCell(new Phrase("Cant.",font3));
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblLineas.addCell(cellLineasDetalle);
            /*cellLineasDetalle = new PdfPCell(new Phrase("P.Unit",font3));
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblLineas.addCell(cellLineasDetalle);;*/
            cellLineasDetalle = new PdfPCell(new Phrase("% Desc",font3));
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblLineas.addCell(cellLineasDetalle);
            cellLineasDetalle = new PdfPCell(new Phrase("Total",font3));
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblLineas.addCell(cellLineasDetalle);

            for(int l=0;l<ListaOrdenVentaDetalle.size();l++)
            {
                cellLineasDetalle = new PdfPCell(new Phrase(ListaOrdenVentaDetalle.get(l).getLineaordenventa_id(),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblLineas.addCell(cellLineasDetalle);
                cellLineasDetalle = new PdfPCell(new Phrase(ListaOrdenVentaDetalle.get(l).getProducto_id(),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblLineas.addCell(cellLineasDetalle);
                cellLineasDetalle = new PdfPCell(new Phrase(
                         ListaOrdenVentaDetalle.get(l).getProducto(),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblLineas.addCell(cellLineasDetalle);
                cellLineasDetalle = new PdfPCell(new Phrase(ListaOrdenVentaDetalle.get(l).getCantidad(),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                tblLineas.addCell(cellLineasDetalle);
                /*cellLineasDetalle = new PdfPCell(new Phrase(ListaOrdenVentaDetalle.get(l).getPreciounitario(),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblLineas.addCell(cellLineasDetalle);*/
                cellLineasDetalle = new PdfPCell(new Phrase(ListaOrdenVentaDetalle.get(l).getPorcentajedescuento(),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                tblLineas.addCell(cellLineasDetalle);
                cellLineasDetalle = new PdfPCell(new Phrase(ListaOrdenVentaDetalle.get(l).getMontototallinea(),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tblLineas.addCell(cellLineasDetalle);
            }
            documento.add(tblLineas);
            PdfPTable tblResumen = new PdfPTable(1);
            tblResumen.setWidthPercentage(100);
            PdfPCell cellResumen = null;
            cellResumen = new PdfPCell(new Phrase("************************************RESUMEN*************************************",font3));
            cellResumen.disableBorderSide(Rectangle.BOX);
            cellResumen.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblResumen.addCell(cellResumen);
            documento.add(tblResumen);


            PdfPTable tblResu = new PdfPTable(2);
            tblResu.setWidthPercentage(100);
            /*PdfPCell cellResu =  new PdfPCell(new Phrase("SubTotal:",font3));
            cellResu.disableBorderSide(Rectangle.BOX);
            cellResu.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblResu.addCell(cellResu);
            cellResu = new PdfPCell(new Phrase(subtotal,font3));
            cellResu.disableBorderSide(Rectangle.BOX);
            cellResu.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tblResu.addCell(cellResu);
            cellResu = new PdfPCell(new Phrase("Descuento:",font3));
            cellResu.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellResu.disableBorderSide(Rectangle.BOX);
            tblResu.addCell(cellResu);
            cellResu = new PdfPCell(new Phrase(descuento,font3));
            cellResu.disableBorderSide(Rectangle.BOX);
            cellResu.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tblResu.addCell(cellResu);
            cellResu = new PdfPCell(new Phrase("IGV:",font3));
            cellResu.disableBorderSide(Rectangle.BOX);
            cellResu.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblResu.addCell(cellResu);
            cellResu = new PdfPCell(new Phrase(igv,font3));
            cellResu.disableBorderSide(Rectangle.BOX);
            cellResu.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tblResu.addCell(cellResu);*/

            PdfPCell cellResu = new PdfPCell(new Phrase("TOTAL ORDEN VENTA:",font3));
            cellResu.disableBorderSide(Rectangle.BOX);
            cellResu.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblResu.addCell(cellResu);
            cellResu = new PdfPCell(new Phrase(total,font3));
            cellResu.disableBorderSide(Rectangle.BOX);
            cellResu.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tblResu.addCell(cellResu);
            //tbl.setWidthPercentage(100);
            documento.add(tblResu);

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

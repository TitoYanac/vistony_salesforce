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

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.vistony.salesforce.BuildConfig;
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

import harmony.java.awt.Color;

public class DocumentQuotationPDF extends AppCompatActivity {

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

    public void generarPdf(Context context, ArrayList<OrdenVentaCabeceraSQLiteEntity> ListaOrdenVentaCabecera,
                           ArrayList<OrdenVentaDetallePromocionSQLiteEntity> ListaOrdenVentaDetalle

    ) {
        String cliente_id="",nombrecliente="",direccion="",fecharegistro="",terminopago="",subtotal=""
                ,igv="",descuento="",total="",ordenventa_id="",ordenventa_erp_id="",vendedor=""
                ,moneda="",flete="",Rucdni="",telefono="",contacto="",rate="";

        for(int i=0;i<ListaOrdenVentaCabecera.size();i++){
            Rucdni=ListaOrdenVentaCabecera.get(i).getRucdni();
            ordenventa_id=ListaOrdenVentaCabecera.get(i).getOrdenventa_id();
            ordenventa_erp_id=ListaOrdenVentaCabecera.get(i).getOrdenventa_ERP_id();
            direccion=ListaOrdenVentaCabecera.get(i).getDomembarque_text();
            cliente_id=ListaOrdenVentaCabecera.get(i).getRucdni();
            nombrecliente=ListaOrdenVentaCabecera.get(i).getCliente_text();
            terminopago=ListaOrdenVentaCabecera.get(i).getTerminopago_text();
            fecharegistro=ListaOrdenVentaCabecera.get(i).getFecharegistro();
            subtotal=ListaOrdenVentaCabecera.get(i).getMontosubtotal();
            igv=ListaOrdenVentaCabecera.get(i).getMontoimpuesto();
            descuento=ListaOrdenVentaCabecera.get(i).getMontodescuento();
            total=ListaOrdenVentaCabecera.get(i).getMontototal();
            moneda=ListaOrdenVentaCabecera.get(i).getMoneda_id();
            flete=ListaOrdenVentaCabecera.get(i).getU_VIS_Flete();
            rate=ListaOrdenVentaCabecera.get(i).getRate();

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
        Document documento = new Document(pagina);

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

            Font font3 = FontFactory.getFont(FontFactory.HELVETICA, 10,
                    Font.BOLD, Color.black);
            Font font4 = FontFactory.getFont(FontFactory.HELVETICA, 32,
                    Font.BOLD, Color.black);
            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.
            Bitmap bitmap=null;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo_factura);
            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100 ,stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            imagen.setAlignment(Element.ALIGN_CENTER);
            //imagen.scaleToFit(200, 200); // Ajusta el tamaño a 200x200
            PdfPTable tableLogo = new PdfPTable(2); // 1 celda en la tabla
            tableLogo.setWidthPercentage(100);
            PdfPCell cellLogo = new PdfPCell();
            cellLogo.addElement(imagen);
            tableLogo.addCell(cellLogo);
            documento.add(tableLogo);*/

            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100 ,stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            imagen.setAlignment(Element.ALIGN_CENTER);
            documento.add(imagen);*/
            Paragraph paragraph1 = new Paragraph("");
            paragraph1.setSpacingBefore(20f); // Espacio antes del párrafo
            paragraph1.setSpacingAfter(20f);  // Espacio después del párrafo
            documento.add(paragraph1);

            PdfPTable tblcliente = new PdfPTable(1);
            tblcliente.setWidthPercentage(100);
            PdfPCell cellTable = null;
            cellTable = new PdfPCell(new Phrase("COTIZACION DE VENTA N° "+ordenventa_id, new Font(Font.HELVETICA, 12, Font.BOLD, Color.red)));  // Establecer el color del texto en blanco
            //cellTable.disableBorderSide(Rectangle.BOX);
            cellTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            tblcliente.addCell(cellTable);
            documento.add(tblcliente);
            Paragraph paragraph3 = new Paragraph("");
            paragraph3.setSpacingBefore(20f); // Espacio antes del párrafo
            paragraph3.setSpacingAfter(20f);  // Espacio después del párrafo
            documento.add(paragraph3);


            //PdfPTable tblgeneral = new PdfPTable(4);
            float[] columnWidths1 = {2f, 6f,2f,2f};
            PdfPTable tblgeneral = new PdfPTable(columnWidths1);
            tblgeneral.setWidthPercentage(100);
            PdfPCell cellgneral = new PdfPCell(new Phrase("SEÑOR (ES):",font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(nombrecliente,font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("FECHA:",font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(fecharegistro,font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("DIRECCION:",font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(direccion,font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("MONEDA:",font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(moneda,font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("RUC:",font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(Rucdni,font3 ));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("TELEFONO:",font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(telefono,font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            //documento.add(tblgeneral);
            cellgneral = new PdfPCell(new Phrase("CONTACTO:",font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(contacto,font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("T/C:",font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(rate,font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("E-MAIL:",font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(rate,font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase("N° OC:",font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(rate,font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            /*cellgneral = new PdfPCell(new Phrase("% Flete:",font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);
            cellgneral = new PdfPCell(new Phrase(flete,font3));
            cellgneral.disableBorderSide(Rectangle.BOX);
            cellgneral.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblgeneral.addCell(cellgneral);*/
            documento.add(tblgeneral);

            Paragraph paragraph2 = new Paragraph("");
            paragraph2.setSpacingBefore(20f); // Espacio antes del párrafo
            paragraph2.setSpacingAfter(20f);  // Espacio después del párrafo
            documento.add(paragraph2);

            //float[] columnWidths = {1.5f, 6f, 1.5f,2f,2f,2f};
            //float[] columnWidths = {1f,2.5f, 9f,1.5f,1.5f,2f};
            float[] columnWidths = {2f, 6f,2f,2f,2f,2f,2f,3f};
            PdfPTable tblLineas = new PdfPTable(columnWidths);
            tblLineas.setWidthPercentage(100);
            PdfPCell cellLineasDetalle = null;
            //cellLineasDetalle = new PdfPCell(new Phrase("Codigo",font3));
            cellLineasDetalle = new PdfPCell(new Phrase("Codigo", new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE)));  // Establecer el color del texto en blanco
            //cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.setBackgroundColor(Color.BLUE);  // Establecer el color de fondo en negro
            cellLineasDetalle.setBorderColor(Color.BLUE);      // Establecer el color del borde en negro
            cellLineasDetalle.setPadding(1);                        // Añadir un relleno para asegurarse de que el texto no toque los bordes
            cellLineasDetalle.setBorder(Rectangle.NO_BORDER);        // Eliminar el borde
            tblLineas.addCell(cellLineasDetalle);
            //cellLineasDetalle = new PdfPCell(new Phrase("Descripción",font3));
            cellLineasDetalle = new PdfPCell(new Phrase("Descripción", new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE)));  // Establecer el color del texto en blanco
            //cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.setBackgroundColor(Color.BLUE);  // Establecer el color de fondo en negro
            cellLineasDetalle.setBorderColor(Color.BLUE);      // Establecer el color del borde en negro
            cellLineasDetalle.setPadding(1);                        // Añadir un relleno para asegurarse de que el texto no toque los bordes
            cellLineasDetalle.setBorder(Rectangle.NO_BORDER);
            tblLineas.addCell(cellLineasDetalle);
            //cellLineasDetalle = new PdfPCell(new Phrase("UM",font3));
            cellLineasDetalle = new PdfPCell(new Phrase("UM", new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE)));  // Establecer el color del texto en blanco
            //cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.setBackgroundColor(Color.BLUE);  // Establecer el color de fondo en negro
            cellLineasDetalle.setBorderColor(Color.BLUE);      // Establecer el color del borde en negro
            cellLineasDetalle.setPadding(1);                        // Añadir un relleno para asegurarse de que el texto no toque los bordes
            cellLineasDetalle.setBorder(Rectangle.NO_BORDER);
            tblLineas.addCell(cellLineasDetalle);
            //cellLineasDetalle = new PdfPCell(new Phrase("Cant.",font3));
            cellLineasDetalle = new PdfPCell(new Phrase("Cant.", new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE)));  // Establecer el color del texto en blanco
            //cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.setBackgroundColor(Color.BLUE);  // Establecer el color de fondo en negro
            cellLineasDetalle.setBorderColor(Color.BLUE);      // Establecer el color del borde en negro
            cellLineasDetalle.setPadding(1);                        // Añadir un relleno para asegurarse de que el texto no toque los bordes
            cellLineasDetalle.setBorder(Rectangle.NO_BORDER);
            tblLineas.addCell(cellLineasDetalle);
            //cellLineasDetalle = new PdfPCell(new Phrase("Precio",font3));
            cellLineasDetalle = new PdfPCell(new Phrase("Precio.", new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE)));  // Establecer el color del texto en blanco
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.setBackgroundColor(Color.BLUE);  // Establecer el color de fondo en negro
            cellLineasDetalle.setBorderColor(Color.BLUE);      // Establecer el color del borde en negro
            cellLineasDetalle.setPadding(1);                        // Añadir un relleno para asegurarse de que el texto no toque los bordes
            cellLineasDetalle.setBorder(Rectangle.NO_BORDER);
            tblLineas.addCell(cellLineasDetalle);
            //cellLineasDetalle = new PdfPCell(new Phrase("%Dcto",font3));
            cellLineasDetalle = new PdfPCell(new Phrase("%Dcto.", new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE)));  // Establecer el color del texto en blanco
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.setBackgroundColor(Color.BLUE);  // Establecer el color de fondo en negro
            cellLineasDetalle.setBorderColor(Color.BLUE);      // Establecer el color del borde en negro
            cellLineasDetalle.setPadding(1);                        // Añadir un relleno para asegurarse de que el texto no toque los bordes
            cellLineasDetalle.setBorder(Rectangle.NO_BORDER);
            tblLineas.addCell(cellLineasDetalle);
            //cellLineasDetalle = new PdfPCell(new Phrase("Precio Dcto",font3));
            cellLineasDetalle = new PdfPCell(new Phrase("Precio Dcto", new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE)));  // Establecer el color del texto en blanco
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.setBackgroundColor(Color.BLUE);  // Establecer el color de fondo en negro
            cellLineasDetalle.setBorderColor(Color.BLUE);      // Establecer el color del borde en negro
            cellLineasDetalle.setPadding(1);                        // Añadir un relleno para asegurarse de que el texto no toque los bordes
            cellLineasDetalle.setBorder(Rectangle.NO_BORDER);
            tblLineas.addCell(cellLineasDetalle);
            //cellLineasDetalle = new PdfPCell(new Phrase("Total",font3));
            cellLineasDetalle = new PdfPCell(new Phrase("Total", new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE)));  // Establecer el color del texto en blanco
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.disableBorderSide(Rectangle.BOX);
            cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLineasDetalle.setBackgroundColor(Color.BLUE);  // Establecer el color de fondo en negro
            cellLineasDetalle.setBorderColor(Color.BLUE);      // Establecer el color del borde en negro
            cellLineasDetalle.setPadding(1);                        // Añadir un relleno para asegurarse de que el texto no toque los bordes
            cellLineasDetalle.setBorder(Rectangle.NO_BORDER);
            tblLineas.addCell(cellLineasDetalle);

            for(int l=0;l<ListaOrdenVentaDetalle.size();l++)
            {
                cellLineasDetalle = new PdfPCell(new Phrase(ListaOrdenVentaDetalle.get(l).getProducto_id(),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblLineas.addCell(cellLineasDetalle);
                cellLineasDetalle = new PdfPCell(new Phrase(ListaOrdenVentaDetalle.get(l).getProducto(),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblLineas.addCell(cellLineasDetalle);
                cellLineasDetalle = new PdfPCell(new Phrase(ListaOrdenVentaDetalle.get(l).getUmd(),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                tblLineas.addCell(cellLineasDetalle);
                cellLineasDetalle = new PdfPCell(new Phrase(ListaOrdenVentaDetalle.get(l).getCantidad(),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                tblLineas.addCell(cellLineasDetalle);
                cellLineasDetalle = new PdfPCell(new Phrase(ListaOrdenVentaDetalle.get(l).getPreciounitario(),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                tblLineas.addCell(cellLineasDetalle);
                cellLineasDetalle = new PdfPCell(new Phrase(Convert.numberForViewDecimals(ListaOrdenVentaDetalle.get(l).getPorcentajedescuento(),2),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                tblLineas.addCell(cellLineasDetalle);
                cellLineasDetalle = new PdfPCell(new Phrase(ListaOrdenVentaDetalle.get(l).getPreciounitario(),font3));
                cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
                tblLineas.addCell(cellLineasDetalle);
                switch (BuildConfig.FLAVOR)
                {
                    case "peru":
                        cellLineasDetalle = new PdfPCell(new Phrase(Convert.currencyForView(ListaOrdenVentaDetalle.get(l).getMontototallinea()),font3));
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                        cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        tblLineas.addCell(cellLineasDetalle);
                        break;
                    case "bolivia":
                        cellLineasDetalle = new PdfPCell(new Phrase(Convert.currencyForView(ListaOrdenVentaDetalle.get(l).getMontosubtotal()),font3));
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX);
                        cellLineasDetalle.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        tblLineas.addCell(cellLineasDetalle);
                        break;
                }

            }
            documento.add(tblLineas);
            /*PdfPTable tblResumen = new PdfPTable(1);
            tblResumen.setWidthPercentage(100);
            PdfPCell cellResumen = null;
            cellResumen = new PdfPCell(new Phrase("************************************RESUMEN*************************************",font3));
            cellResumen.disableBorderSide(Rectangle.BOX);
            cellResumen.setHorizontalAlignment(Element.ALIGN_LEFT);
            tblResumen.addCell(cellResumen);
            documento.add(tblResumen);*/

            Paragraph paragraph4 = new Paragraph("");
            paragraph4.setSpacingBefore(20f); // Espacio antes del párrafo
            paragraph4.setSpacingAfter(20f);  // Espacio después del párrafo
            documento.add(paragraph4);

            PdfPTable tblResumen = new PdfPTable(1);
            tblResumen.setWidthPercentage(100);
            PdfPCell cellResumen = new PdfPCell(new Phrase("RESUMEN COTIZACIÓN", new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE)));  // Establecer el color del texto en blanco
            cellResumen.disableBorderSide(Rectangle.BOX);
            cellResumen.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellResumen.setBackgroundColor(Color.BLACK);  // Establecer el color de fondo en negro
            cellResumen.setBorderColor(Color.BLACK);      // Establecer el color del borde en negro
            cellResumen.setPadding(5);                        // Añadir un relleno para asegurarse de que el texto no toque los bordes
            cellResumen.setBorder(Rectangle.NO_BORDER);        // Eliminar el borde
            tblResumen.addCell(cellResumen);
            documento.add(tblResumen);



            PdfPTable tblResu = new PdfPTable(2);
            tblResu.setWidthPercentage(100);
            PdfPCell cellResu = new PdfPCell(new Phrase("SubTotal",font3));
            cellResu.disableBorderSide(Rectangle.BOX);
            cellResu.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellResu.setBorder(Rectangle.LEFT);
            tblResu.addCell(cellResu);
            cellResu = new PdfPCell(new Phrase(Convert.currencyForView(subtotal),font3));
            cellResu.disableBorderSide(Rectangle.BOX);
            cellResu.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellResu.setBorder(Rectangle.RIGHT);
            tblResu.addCell(cellResu);
            cellResu = new PdfPCell(new Phrase("Descuento",font3));
            cellResu.disableBorderSide(Rectangle.BOX);
            cellResu.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellResu.setBorder(Rectangle.LEFT);
            tblResu.addCell(cellResu);
            cellResu = new PdfPCell(new Phrase(Convert.currencyForView(descuento),font3));
            cellResu.disableBorderSide(Rectangle.BOX);
            cellResu.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellResu.setBorder(Rectangle.RIGHT);
            tblResu.addCell(cellResu);
            cellResu = new PdfPCell(new Phrase("IGV",font3));
            cellResu.disableBorderSide(Rectangle.BOX);
            cellResu.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellResu.setBorder(Rectangle.LEFT);
            tblResu.addCell(cellResu);
            cellResu = new PdfPCell(new Phrase(Convert.currencyForView(igv),font3));
            cellResu.disableBorderSide(Rectangle.BOX);
            cellResu.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellResu.setBorder(Rectangle.RIGHT);
            tblResu.addCell(cellResu);
            cellResu = new PdfPCell(new Phrase("Total",font3));
            cellResu.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellResu.setBorder(Rectangle.BOX);
            cellResu.disableBorderSide(Rectangle.TOP);
            cellResu.disableBorderSide(Rectangle.RIGHT);
            tblResu.addCell(cellResu);
            cellResu = new PdfPCell(new Phrase(Convert.currencyForView(total),font3));
            cellResu.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellResu.setBorder(Rectangle.BOX);
            cellResu.disableBorderSide(Rectangle.TOP);
            cellResu.disableBorderSide(Rectangle.LEFT);
            tblResu.addCell(cellResu);

            /*BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            String encData="",Data="";
            Data=ordenventa_id+"&&&"+fecharegistro+"&&&"+nombrecliente+"&&&"+total+"&&&"+SesionEntity.fuerzatrabajo_id+" "+SesionEntity.nombrefuerzadetrabajo;
            try {
                Log.e("REOS","DocumentoCobranzaPDF-generarPdf-EntroalTryCifrado");
                encData= CifradoController.encrypt("Vistony2019*".getBytes("UTF-16LE"), Data.getBytes("UTF-16LE"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Bitmap bitmapqr = barcodeEncoder.encodeBitmap(encData, BarcodeFormat.QR_CODE, 400, 400);

            ByteArrayOutputStream streamqr = new ByteArrayOutputStream();

            bitmapqr.compress(Bitmap.CompressFormat.PNG, 50, streamqr);
            Image imagenqr = Image.getInstance(streamqr.toByteArray());
            imagenqr.setAlignment(Element.ALIGN_CENTER);
            tblResu.addCell(cellResu);
            tblResu.addCell(imagenqr);*/

            documento.add(tblResu);
            //documento.add(imagenqr);

        } catch (DocumentException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } catch (IOException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } catch (Exception e){
            e.printStackTrace();
            Log.e("JPCM","capturado->"+ e);
        }
        finally {
            Log.e("JPCM","ENTRO AL FINALLY  AFTER ERROR");
            // Cerramos el documento.
            documento.close();

            ///////////////////ABRIR PDF////////////////////////

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator+"OrdenVenta"+File.separator+ordenventa_id+".pdf");

            Uri excelPath = FileProvider.getUriForFile(context, context.getPackageName(), file);

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

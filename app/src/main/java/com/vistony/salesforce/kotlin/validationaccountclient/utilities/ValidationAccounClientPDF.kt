package com.vistony.salesforce.kotlin.validationaccountclient.utilities

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import com.vistony.salesforce.Entity.Retrofit.Modelo.KardexPagoEntity
import com.lowagie.text.pdf.PdfWriter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.vistony.salesforce.R
import com.lowagie.text.pdf.PdfPTable
import com.lowagie.text.pdf.PdfPCell
import com.vistony.salesforce.Controller.Utilitario.Induvis
import com.vistony.salesforce.Entity.SesionEntity
import android.os.Environment
import androidx.core.content.FileProvider
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.lowagie.text.*
import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Controller.Utilitario.Convert
import harmony.java.awt.Color
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Throws

class ValidationAccounClientPDF : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1000
            )
        } else {
        }
    }

    fun generatePdf(context: Context, ListKardexPagoEntity: List<KardexPagoEntity>) {
        // Creamos el documento.
        val pagina = Rectangle(
            36.0f, 36.0f,  //559
            650.0f //, 806
            , 1120.0f //PageSize.ARCH_A
        )
        val documento = Document(pagina)
        val ObjList = ArrayList<String>()
        val Correlativo = ArrayList<String>()
        var CardCode = ""
        var CardName: String? = ""
        var LicTradNum: String? = ""
        var Street: String? = ""
        var Ubigeo = ""
        var Phone: String? = ""
        val listaCorrelativo: Set<String>
        for (i in ListKardexPagoEntity.indices) {
            CardCode = ListKardexPagoEntity[i].getCardCode()
            CardName = ListKardexPagoEntity[i].getCardName()
            LicTradNum = ListKardexPagoEntity[i].getLicTradNum()
            Street = ListKardexPagoEntity[i].getStreet()
            Ubigeo =
                ListKardexPagoEntity[i].getU_SYP_DEPA() + "-" + ListKardexPagoEntity[i].getU_SYP_PROV() + "-" + ListKardexPagoEntity[i].getU_SYP_DIST()
            Phone = ListKardexPagoEntity[i].getPhone1()
            Log.e(
                "REOS",
                "DocumentCobranzaPDF.generarPdf.ListKardexPagoEntity.get(i).getNumAtCard():" + ListKardexPagoEntity[i].getNumAtCard()
            )
            ObjList.add(ListKardexPagoEntity[i].getNumAtCard())
        }
        listaCorrelativo = HashSet(ObjList)
        for (j in ObjList.indices) {
            if (!Correlativo.isEmpty()) {
                var contador = 0
                for (l in Correlativo.indices) {
                    if (ObjList[j] == Correlativo[l]) {
                        contador++
                    }
                }
                if (contador == 0) {
                    Correlativo.add(ObjList[j])
                }
            } else {
                Correlativo.add(ObjList[j])
            }
        }
        Log.e("REOS", "DocumentCobranzaPDF.generarPdf.listaCorrelativo:" + listaCorrelativo.size)
        val ObjArrayListaCorrelativo: Array<Any> = listaCorrelativo.toTypedArray()
        for (j in Correlativo.indices) {
            //ObjArrayListaCorrelativo[j].toString();
            Log.e("REOS", "DocumentCobranzaPDF.generarPdf.Correlativo:" + Correlativo[j])
        }
        Log.e(
            "REOS",
            "DocumentCobranzaPDF.generarPdf.ObjArrayListaCorrelativo.length:" + ObjArrayListaCorrelativo.size
        )
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val calendario = GregorianCalendar()
            val f = crearFichero("$CardCode.pdf")

            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            val ficheroPdf = FileOutputStream(
                f!!.absolutePath
            )

            // Asociamos el flujo que acabamos de crear al documento.
            val writer = PdfWriter.getInstance(documento, ficheroPdf)
            val now = GregorianCalendar.getInstance()
            val dateFormat = DateFormat.getDateInstance(DateFormat.FULL)
            documento.open()
            val font = FontFactory.getFont(
                FontFactory.HELVETICA, 28f,
                Font.BOLD, Color.black
            )
            val font2 = FontFactory.getFont(
                FontFactory.HELVETICA, 20f,
                Font.BOLD, Color.black
            )
            val font3 = FontFactory.getFont(
                FontFactory.HELVETICA, 16f,
                Font.NORMAL, Color.black
            )
            val font4 = FontFactory.getFont(
                FontFactory.HELVETICA, 32f,
                Font.BOLD, Color.black
            )
            val font5 = FontFactory.getFont(
                FontFactory.HELVETICA, 18f,
                Font.BOLD, Color.black
            )
            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.
            var bitmap: Bitmap? = null
            bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.logo_negro_vistony)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val imagen = Image.getInstance(stream.toByteArray())
            imagen.alignment = Element.ALIGN_CENTER
            documento.add(imagen)
            val tblempresa = PdfPTable(1)
            tblempresa.widthPercentage = 100f
            var cellTableempresa: PdfPCell? = null
            Log.e("REOS", "DocumentCobranzaPDF.generarPdf.BuildConfig.FLAVOR:" + BuildConfig.FLAVOR)
            cellTableempresa = PdfPCell(Phrase(Induvis.getInformation(BuildConfig.FLAVOR), font3))
            Log.e(
                "REOS",
                "DocumentCobranzaPDF.generarPdf.Induvis.getInformation(BuildConfig.FLAVOR):" + Induvis.getInformation(
                    BuildConfig.FLAVOR
                )
            )
            cellTableempresa.disableBorderSide(Rectangle.BOX)
            cellTableempresa.horizontalAlignment = Element.ALIGN_LEFT
            tblempresa.addCell(cellTableempresa)
            documento.add(tblempresa)
            val tbllblkardex = PdfPTable(1)
            tbllblkardex.widthPercentage = 100f
            var celltbllblkardex: PdfPCell? = null
            celltbllblkardex = PdfPCell(
                Phrase(
                    context.resources.getString(R.string.separator_short)
                            + context.resources.getString(R.string.lbl_kardex_of_payment)
                            + context.resources.getString(R.string.separator_short), font
                )
            )
            celltbllblkardex.disableBorderSide(Rectangle.BOX)
            celltbllblkardex.horizontalAlignment = Element.ALIGN_CENTER
            tbllblkardex.addCell(celltbllblkardex)
            documento.add(tbllblkardex)
            val tbllblcliente = PdfPTable(1)
            tbllblcliente.widthPercentage = 100f
            var celltbllblcliente: PdfPCell? = null
            celltbllblcliente = PdfPCell(
                Phrase(
                    context.resources.getString(R.string.separator_short)
                            + context.resources.getString(R.string.data)
                        .uppercase(Locale.getDefault())
                            + context.resources.getString(R.string.client)
                        .uppercase(Locale.getDefault())
                            + context.resources.getString(R.string.separator_short), font2
                )
            )
            celltbllblcliente.disableBorderSide(Rectangle.BOX)
            celltbllblcliente.horizontalAlignment = Element.ALIGN_CENTER
            tbllblcliente.addCell(celltbllblcliente)
            documento.add(tbllblcliente)
            val tblCabecera = PdfPTable(2)
            tblCabecera.widthPercentage = 100f
            var cellCabecera: PdfPCell? = null
            cellCabecera = PdfPCell(Phrase(context.resources.getString(R.string.code), font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera = PdfPCell(Phrase(CardCode, font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera = PdfPCell(Phrase(context.resources.getString(R.string.documents), font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera = PdfPCell(Phrase(LicTradNum, font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera = PdfPCell(Phrase(context.resources.getString(R.string.client), font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera = PdfPCell(Phrase(CardName, font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera = PdfPCell(Phrase(context.resources.getString(R.string.phone), font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera = PdfPCell(Phrase(Phone, font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera = PdfPCell(Phrase(context.resources.getString(R.string.adresses), font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera = PdfPCell(Phrase(Street, font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera = PdfPCell(Phrase(context.resources.getString(R.string.ubigeous), font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera = PdfPCell(Phrase(Ubigeo, font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera =
                PdfPCell(Phrase(context.resources.getString(R.string.generate_with), font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera = PdfPCell(
                Phrase(
                    SesionEntity.fuerzatrabajo_id + " " + SesionEntity.nombrefuerzadetrabajo,
                    font3
                )
            )
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera =
                PdfPCell(Phrase(context.resources.getString(R.string.generate_date), font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            cellCabecera = PdfPCell(Phrase(sdf.format(calendario.time).toString(), font3))
            cellCabecera.disableBorderSide(Rectangle.BOX)
            cellCabecera.horizontalAlignment = Element.ALIGN_LEFT
            tblCabecera.addCell(cellCabecera)
            documento.add(tblCabecera)
            val tbllbldetalle = PdfPTable(1)
            tbllbldetalle.widthPercentage = 100f
            var celltbllbldetalle: PdfPCell? = null
            celltbllbldetalle = PdfPCell(
                Phrase(
                    context.resources.getString(R.string.separator_short)
                            + context.resources.getString(R.string.separator_short)
                            + context.resources.getString(R.string.separator_short)
                            + context.resources.getString(R.string.detail)
                        .uppercase(Locale.getDefault())
                            + context.resources.getString(R.string.separator_short)
                            + context.resources.getString(R.string.separator_short)
                            + context.resources.getString(R.string.separator_short), font2
                )
            )
            celltbllbldetalle.disableBorderSide(Rectangle.BOX)
            celltbllbldetalle.horizontalAlignment = Element.ALIGN_CENTER
            tbllbldetalle.addCell(celltbllbldetalle)
            documento.add(tbllbldetalle)
            for (l in Correlativo.indices) {
                val tbllblfactura = PdfPTable(1)
                tbllblfactura.widthPercentage = 100f
                var celltbllblfactura: PdfPCell? = null
                celltbllblfactura = PdfPCell(
                    Phrase(
                        Correlativo[l] + context.resources.getString(R.string.separator_short) + context.resources.getString(
                            R.string.separator_short
                        ) + context.resources.getString(R.string.separator_short) + context.resources.getString(
                            R.string.separator_short
                        ), font5
                    )
                )
                celltbllblfactura.disableBorderSide(Rectangle.BOX)
                celltbllblfactura.horizontalAlignment = Element.ALIGN_CENTER
                tbllblfactura.addCell(celltbllblfactura)
                documento.add(tbllblfactura)
                var contador = 0
                var item = 1
                for (j in ListKardexPagoEntity.indices) {
                    if (Correlativo[l] == ListKardexPagoEntity[j].getNumAtCard() && contador == 0) {
                        val tblterminopago = PdfPTable(2)
                        tblterminopago.widthPercentage = 100f
                        var celltblterminopago: PdfPCell? = null
                        celltblterminopago = PdfPCell(
                            Phrase(
                                context.resources.getString(R.string.payment_terms),
                                font3
                            )
                        )
                        celltblterminopago.disableBorderSide(Rectangle.BOX)
                        celltblterminopago.horizontalAlignment = Element.ALIGN_LEFT
                        tblterminopago.addCell(celltblterminopago)
                        celltblterminopago =
                            PdfPCell(Phrase(ListKardexPagoEntity[j].getPymntGroup(), font3))
                        celltblterminopago.disableBorderSide(Rectangle.BOX)
                        celltblterminopago.horizontalAlignment = Element.ALIGN_LEFT
                        tblterminopago.addCell(celltblterminopago)
                        celltblterminopago =
                            PdfPCell(Phrase(context.resources.getString(R.string.seller), font3))
                        celltblterminopago.disableBorderSide(Rectangle.BOX)
                        celltblterminopago.horizontalAlignment = Element.ALIGN_LEFT
                        tblterminopago.addCell(celltblterminopago)
                        celltblterminopago =
                            PdfPCell(Phrase(ListKardexPagoEntity[j].getSalesinvoice(), font3))
                        celltblterminopago.disableBorderSide(Rectangle.BOX)
                        celltblterminopago.horizontalAlignment = Element.ALIGN_LEFT
                        tblterminopago.addCell(celltblterminopago)
                        documento.add(tblterminopago)
                        val tblLineas = PdfPTable(4)
                        tblLineas.widthPercentage = 100f
                        var cellLineasDetalle: PdfPCell? = null
                        cellLineasDetalle = PdfPCell(
                            Phrase(
                                context.resources.getString(R.string.date_issue),
                                font3
                            )
                        )
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX)
                        cellLineasDetalle.horizontalAlignment = Element.ALIGN_LEFT
                        tblLineas.addCell(cellLineasDetalle)

                        /*String fecha,año,mes,dia;
                        String[] sourceemision= ListKardexPagoEntity.get(j).getTaxDate().split(" ");
                        fecha= sourceemision[0];
                        String[] sourcefechadesordenada= fecha.split("/");
                        año=sourcefechadesordenada[2];
                        mes=sourcefechadesordenada[0];
                        dia=sourcefechadesordenada[1];*/

                        //cellLineasDetalle = new PdfPCell(new Phrase(dia+"/"+mes+"/"+año, font3));
                        cellLineasDetalle = PdfPCell(
                            Phrase(
                                Induvis.getDate(
                                    BuildConfig.FLAVOR,
                                    ListKardexPagoEntity[j].getTaxDate()
                                ), font3
                            )
                        )
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX)
                        cellLineasDetalle.horizontalAlignment = Element.ALIGN_LEFT
                        tblLineas.addCell(cellLineasDetalle)
                        cellLineasDetalle = PdfPCell(
                            Phrase(
                                context.resources.getString(R.string.date_expiration),
                                font3
                            )
                        )
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX)
                        cellLineasDetalle.horizontalAlignment = Element.ALIGN_LEFT
                        tblLineas.addCell(cellLineasDetalle)

                        /*sourceemision= ListKardexPagoEntity.get(j).getDocDueDate().split(" ");
                        fecha= sourceemision[0];
                        sourcefechadesordenada= fecha.split("/");
                        año=sourcefechadesordenada[2];
                        mes=sourcefechadesordenada[0];
                        dia=sourcefechadesordenada[1];*/
                        //cellLineasDetalle = new PdfPCell(new Phrase(dia+"/"+mes+"/"+año, font3));
                        cellLineasDetalle = PdfPCell(
                            Phrase(
                                Induvis.getDate(
                                    BuildConfig.FLAVOR,
                                    ListKardexPagoEntity[j].getDocDueDate()
                                ), font3
                            )
                        )
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX)
                        cellLineasDetalle.horizontalAlignment = Element.ALIGN_LEFT
                        tblLineas.addCell(cellLineasDetalle)
                        cellLineasDetalle = PdfPCell(
                            Phrase(
                                context.resources.getString(R.string.lbl_DocAmount),
                                font3
                            )
                        )
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX)
                        cellLineasDetalle.horizontalAlignment = Element.ALIGN_LEFT
                        tblLineas.addCell(cellLineasDetalle)
                        cellLineasDetalle = PdfPCell(
                            Phrase(
                                Convert.currencyForView(
                                    ListKardexPagoEntity[j].getDocTotal()
                                ), font3
                            )
                        )
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX)
                        cellLineasDetalle.horizontalAlignment = Element.ALIGN_LEFT
                        tblLineas.addCell(cellLineasDetalle)
                        cellLineasDetalle =
                            PdfPCell(Phrase(context.resources.getString(R.string.balance), font3))
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX)
                        cellLineasDetalle.horizontalAlignment = Element.ALIGN_LEFT
                        tblLineas.addCell(cellLineasDetalle)
                        cellLineasDetalle = PdfPCell(
                            Phrase(
                                Convert.currencyForView(
                                    ListKardexPagoEntity[j].getsALDO()
                                ), font3
                            )
                        )
                        cellLineasDetalle.disableBorderSide(Rectangle.BOX)
                        cellLineasDetalle.horizontalAlignment = Element.ALIGN_LEFT
                        tblLineas.addCell(cellLineasDetalle)
                        documento.add(tblLineas)
                        contador++
                        val tblpagos = PdfPTable(1)
                        tblpagos.widthPercentage = 100f
                        var celltblpagos: PdfPCell? = null
                        celltblpagos = PdfPCell(
                            Phrase(
                                context.resources.getString(R.string.payment) + context.resources.getString(
                                    R.string.separator
                                ) + context.resources.getString(R.string.separator), font3
                            )
                        )
                        celltblpagos.disableBorderSide(Rectangle.BOX)
                        celltblpagos.horizontalAlignment = Element.ALIGN_LEFT
                        tblpagos.addCell(celltblpagos)
                        documento.add(tblpagos)
                    }
                    if (Correlativo[l] == ListKardexPagoEntity[j].getNumAtCard()) {
                        val tblcobrador = PdfPTable(2)
                        tblcobrador.widthPercentage = 100f
                        var cellcobrador: PdfPCell? = null
                        cellcobrador = PdfPCell(
                            Phrase(
                                context.resources.getString(R.string.debt_collector),
                                font3
                            )
                        )
                        cellcobrador.disableBorderSide(Rectangle.BOX)
                        cellcobrador.horizontalAlignment = Element.ALIGN_LEFT
                        tblcobrador.addCell(cellcobrador)
                        cellcobrador =
                            PdfPCell(Phrase(ListKardexPagoEntity[j].getCollectorinvoice(), font3))
                        cellcobrador.disableBorderSide(Rectangle.BOX)
                        cellcobrador.horizontalAlignment = Element.ALIGN_LEFT
                        tblcobrador.addCell(cellcobrador)
                        documento.add(tblcobrador)
                        val tbldatarecibos = PdfPTable(4)
                        tbldatarecibos.widthPercentage = 100f
                        var celltbldatarecibos: PdfPCell? = null
                        celltbldatarecibos =
                            PdfPCell(Phrase(context.resources.getString(R.string.item), font3))
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX)
                        celltbldatarecibos.horizontalAlignment = Element.ALIGN_LEFT
                        tbldatarecibos.addCell(celltbldatarecibos)
                        celltbldatarecibos = PdfPCell(Phrase(item.toString(), font3))
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX)
                        celltbldatarecibos.horizontalAlignment = Element.ALIGN_LEFT
                        tbldatarecibos.addCell(celltbldatarecibos)
                        celltbldatarecibos = PdfPCell(
                            Phrase(
                                context.resources.getString(R.string.date_payment),
                                font3
                            )
                        )
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX)
                        celltbldatarecibos.horizontalAlignment = Element.ALIGN_LEFT
                        tbldatarecibos.addCell(celltbldatarecibos)

                        /*String fecha,año,mes,dia;
                        String[] sourceemision= ListKardexPagoEntity.get(j).getfECHADEPAGO().split(" ");
                        fecha= sourceemision[0];
                        String[] sourcefechadesordenada= fecha.split("/");
                        año=sourcefechadesordenada[2];
                        mes=sourcefechadesordenada[0];
                        dia=sourcefechadesordenada[1];*/
                        //celltbldatarecibos = new PdfPCell(new Phrase(dia+"/"+mes+"/"+año, font3));
                        celltbldatarecibos = PdfPCell(
                            Phrase(
                                Induvis.getDate(
                                    BuildConfig.FLAVOR,
                                    ListKardexPagoEntity[j].getfECHADEPAGO()
                                ), font3
                            )
                        )
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX)
                        celltbldatarecibos.horizontalAlignment = Element.ALIGN_LEFT
                        tbldatarecibos.addCell(celltbldatarecibos)
                        celltbldatarecibos =
                            PdfPCell(Phrase(context.resources.getString(R.string.receip), font3))
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX)
                        celltbldatarecibos.horizontalAlignment = Element.ALIGN_LEFT
                        tbldatarecibos.addCell(celltbldatarecibos)
                        celltbldatarecibos =
                            PdfPCell(Phrase(ListKardexPagoEntity[j].getComments(), font3))
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX)
                        celltbldatarecibos.horizontalAlignment = Element.ALIGN_LEFT
                        tbldatarecibos.addCell(celltbldatarecibos)
                        celltbldatarecibos = PdfPCell(
                            Phrase(
                                context.resources.getString(R.string.amount_charged),
                                font3
                            )
                        )
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX)
                        celltbldatarecibos.horizontalAlignment = Element.ALIGN_LEFT
                        tbldatarecibos.addCell(celltbldatarecibos)
                        celltbldatarecibos = PdfPCell(
                            Phrase(
                                Convert.currencyForView(
                                    ListKardexPagoEntity[j].getImporteCobrado()
                                ), font3
                            )
                        )
                        celltbldatarecibos.disableBorderSide(Rectangle.BOX)
                        celltbldatarecibos.horizontalAlignment = Element.ALIGN_LEFT
                        tbldatarecibos.addCell(celltbldatarecibos)
                        documento.add(tbldatarecibos)
                        item++
                        val tbldatabanco = PdfPTable(2)
                        tbldatabanco.widthPercentage = 100f
                        var celltbldatabanco: PdfPCell? = null
                        celltbldatabanco = PdfPCell(
                            Phrase(
                                context.resources.getString(R.string.number_operation),
                                font3
                            )
                        )
                        celltbldatabanco.disableBorderSide(Rectangle.BOX)
                        celltbldatabanco.horizontalAlignment = Element.ALIGN_LEFT
                        tbldatabanco.addCell(celltbldatabanco)
                        celltbldatabanco =
                            PdfPCell(Phrase(ListKardexPagoEntity[j].getnROOPERA(), font3))
                        celltbldatabanco.disableBorderSide(Rectangle.BOX)
                        celltbldatabanco.horizontalAlignment = Element.ALIGN_LEFT
                        tbldatabanco.addCell(celltbldatabanco)
                        celltbldatabanco =
                            PdfPCell(Phrase(context.resources.getString(R.string.banks), font3))
                        celltbldatabanco.disableBorderSide(Rectangle.BOX)
                        celltbldatabanco.horizontalAlignment = Element.ALIGN_LEFT
                        tbldatabanco.addCell(celltbldatabanco)
                        celltbldatabanco =
                            PdfPCell(Phrase(ListKardexPagoEntity[j].getBanco(), font3))
                        celltbldatabanco.disableBorderSide(Rectangle.BOX)
                        celltbldatabanco.horizontalAlignment = Element.ALIGN_LEFT
                        tbldatabanco.addCell(celltbldatabanco)
                        documento.add(tbldatabanco)
                    }
                    if (Correlativo[l] == ListKardexPagoEntity[j].getNumAtCard()) {
                        val tblpagos = PdfPTable(1)
                        tblpagos.widthPercentage = 100f
                        var celltblpagos: PdfPCell? = null
                        celltblpagos = PdfPCell(
                            Phrase(
                                context.resources.getString(R.string.separator_) + context.resources.getString(
                                    R.string.separator_
                                ), font3
                            )
                        )
                        celltblpagos.disableBorderSide(Rectangle.BOX)
                        celltblpagos.horizontalAlignment = Element.ALIGN_LEFT
                        tblpagos.addCell(celltblpagos)
                        documento.add(tblpagos)
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
        } catch (e: DocumentException) {
            Log.e(ETIQUETA_ERROR, e.message!!)
            Log.e("REOS", "KardexPagoRPDF.DocumentException.e$e")
        } catch (e: IOException) {
            Log.e(ETIQUETA_ERROR, e.message!!)
            Log.e("REOS", "KardexPagoRPDF.IOException.e$e")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("JPCM", "capturado->$e")
            Log.e("REOS", "KardexPagoRPDF.Exception.e$e")
        } finally {
            Log.e("JPCM", "ENTRO AL FINALLY  AFTER ERROR")
            // Cerramos el documento.
            documento.close()

            ///////////////////ABRIR PDF////////////////////////
            val file =
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator + "KardexPago" + File.separator + CardCode + ".pdf")
            val excelPath = FileProvider.getUriForFile(context, context.packageName, file)
            val target = Intent(Intent.ACTION_VIEW)
            target.setDataAndType(excelPath, "application/pdf")
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            try {
                context.startActivity(target)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.mse_necessary_install_PDF),
                    Toast.LENGTH_SHORT
                ).show()
            }

            ///////////////////////////////////////////////////
        }
    }

    companion object {
        private const val NOMBRE_DIRECTORIO = "KardexPago"
        private const val ETIQUETA_ERROR = "ERROR"
        @Throws(IOException::class)
        fun crearFichero(nombreFichero: String?): File? {
            val ruta = ruta
            var fichero: File? = null
            if (ruta != null) fichero = File(ruta, nombreFichero)
            return fichero
        }// El fichero sera almacenado en un directorio dentro del directorio
        // Descargas
        /**
         * Obtenemos la ruta donde vamos a almacenar el fichero.
         *
         * @return
         */
        val ruta: File?
            get() {

                // El fichero sera almacenado en un directorio dentro del directorio
                // Descargas
                var ruta: File? = null
                if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
                    ruta = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        NOMBRE_DIRECTORIO
                    )
                    if (ruta != null) {
                        if (!ruta.mkdirs()) {
                            if (!ruta.exists()) {
                                return null
                            }
                        }
                    }
                } else {
                }
                return ruta
            }
    }
}
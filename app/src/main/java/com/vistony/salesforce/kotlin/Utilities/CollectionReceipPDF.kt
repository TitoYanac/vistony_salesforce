package com.vistony.salesforce.kotlin.Utilities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.lowagie.text.*
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import com.lowagie.text.pdf.PdfWriter
import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Controller.Utilitario.CifradoController
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Controller.Utilitario.Induvis
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.View.MenuView
import com.vistony.salesforce.kotlin.Model.CollectionDetail
import harmony.java.awt.Color
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class CollectionReceipPDF {
    private val NOMBRE_DIRECTORIO = "Cobranza"


    /*
    protected fun onCreate(savedInstanceState: Bundle?) {
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

    //Procedimiento para mostrar el documento PDF generado
    fun showPdfFile(fileName: String?, context: Context) {
        Toast.makeText(context, "Leyendo documento", Toast.LENGTH_LONG).show()
        var file: File? = null
        try {
            file = crearFichero(fileName)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val intent2 = Intent(Intent.ACTION_VIEW)
        //Uri urifile = Uri.fromFile(file);
        val urifile = FileProvider.getUriForFile(
            context,
            BuildConfig.APPLICATION_ID + ".provider", file!!
        )
        intent2.setDataAndType(urifile, "application/pdf")
        intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent2.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        try {
            context.startActivity(intent2)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            Toast.makeText(context, "No Application Available to View PDF", Toast.LENGTH_SHORT)
                .show()
        }
    }*/

    fun generarPdf(
        context: Context,
        listCollectionDetail: CollectionDetail?,
        //fuerzatrabajo_id: String,
        //nombrefuerzatrabajo: String,
        //recibo: String,
        //fecharecibo: String,
        //horarecibo: String,
        //type_description: String
    ) {
        //Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-Lista: " + Lista.size)
        /*Log.e(
            "REOS",
            "DocumentoCobranzaPDF-generarPdf-fuerzatrabajo_id: $fuerzatrabajo_id"
        )
        Log.e(
            "REOS",
            "DocumentoCobranzaPDF-generarPdf-nombrefuerzatrabajo: $nombrefuerzatrabajo"
        )
        Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-recibo: $recibo")
        Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-fecharecibo: $fecharecibo")
        Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-horarecibo: $horarecibo")*/
        /*var cliente_id = ""
        var nombrecliente = ""
        var direccion: String? = ""
        var fechaemision: String? = ""
        var fechavencimiento: String? = ""
        var nrodocumento = ""
        var documento_id: String? = ""
        var importe = ""
        var saldo = ""
        var cobrado = ""
        var nuevo_saldo = ""
        var type = ""*/
        //DeleteDocumentPDF(recibo,context);
        //Get Date User
        var ObjUsuario = UsuarioSQLiteEntity()
        val usuarioSQLite = UsuarioSQLite(context)
        ObjUsuario = usuarioSQLite.ObtenerUsuarioSesion()
        /*for (i in Lista.indices) {
            cliente_id = Lista[i].getCliente_id()
            nombrecliente = Lista[i].getNombrecliente()
            direccion = Lista[i].getDireccion()
            fechaemision = Lista[i].getFechaemision()
            fechavencimiento = Lista[i].getFechavencimiento()
            nrodocumento = Lista[i].getNrodocumento()
            documento_id = Lista[i].getDocumento_id()
            importe = Lista[i].getImporte()
            saldo = Lista[i].getSaldo()
            cobrado = Lista[i].getCobrado()
            nuevo_saldo = Lista[i].getNuevo_saldo()
        }
        type = type_description
        Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-cliente_id: $cliente_id")
        Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-nombrecliente: $nombrecliente")
        Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-nrodocumento: $nrodocumento")
        Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-importe: $importe")
        Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-saldo: $saldo")
        Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-cobrado: $cobrado")
        Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-nuevo_saldo: $nuevo_saldo")*/
        // Creamos el documento.
        val pagina = Rectangle(
            36f, 36f,  //559
            650f //, 806
            , 1120f //PageSize.ARCH_A
        )
        val documento = Document(pagina)
        try {
            Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-EntroalTry1")
            val cifradoController = CifradoController()
            var encData: String? = ""
            try {
                Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-EntroalTryCifrado")
                encData = CifradoController.encrypt(
                    "Vistony2019*".toByteArray(charset("UTF-16LE")),
                    listCollectionDetail?.Receip?.toByteArray(charset("UTF-16LE"))
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val barcodeEncoder = BarcodeEncoder()
            val bitmapqr = barcodeEncoder.encodeBitmap(encData, BarcodeFormat.QR_CODE, 400, 400)
            val streamqr = ByteArrayOutputStream()
            bitmapqr.compress(Bitmap.CompressFormat.PNG, 50, streamqr)
            val imagenqr = Image.getInstance(streamqr.toByteArray())
            imagenqr.alignment = Element.ALIGN_CENTER

            val f = crearFichero(listCollectionDetail?.Receip?.toString()+".pdf")

            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            val ficheroPdf = FileOutputStream(f!!.absolutePath)

            // Asociamos el flujo que acabamos de crear al documento.
            val writer = PdfWriter.getInstance(documento, ficheroPdf)
            writer.close()
            //Calendar now = GregorianCalendar.getInstance();
            //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
            documento.open()
            val font: Font
            val font3: Font
            val font2: Font
            val font4: Font
            font = FontFactory.getFont(
                FontFactory.HELVETICA, 28f,
                Font.BOLD, Color.black
            )
            font2 = FontFactory.getFont(FontFactory.HELVETICA, 20f, Font.BOLD, Color.black)
            font3 = FontFactory.getFont(FontFactory.HELVETICA, 16f, Font.BOLD, Color.black)

            if (SesionEntity.perfil_id == "Chofer" || SesionEntity.perfil_id == "CHOFER")
            {
                font4 = FontFactory.getFont(FontFactory.HELVETICA, 28f, Font.BOLD, Color.black)
            } else
            {
                font4 = FontFactory.getFont(FontFactory.HELVETICA, 32f, Font.BOLD, Color.black)
            }

            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.
            var bitmap: Bitmap? = null
            when (ObjUsuario.compania_id) {
                "01" -> bitmap =
                    BitmapFactory.decodeResource(context.resources, R.mipmap.logo_negro_vistony)
                "C011" -> bitmap =
                    BitmapFactory.decodeResource(context.resources, R.mipmap.logo_bluker_negro)
                "13" -> bitmap =
                    BitmapFactory.decodeResource(context.resources, R.mipmap.logo_rofalab_negro2)
                else -> bitmap =
                    BitmapFactory.decodeResource(context.resources, R.mipmap.logo_negro_vistony)
            }
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val imagen = Image.getInstance(stream.toByteArray())
            imagen.alignment = Element.ALIGN_CENTER
            documento.add(imagen)
            val tblcliente = PdfPTable(1)
            tblcliente.widthPercentage = 100f
            var cellTable: PdfPCell? = null
            Log.d("REOS", "DocumentCobranzaPDF.generarPdf.BuildConfig.FLAVOR:" + BuildConfig.FLAVOR)
            cellTable = PdfPCell(Phrase(Induvis.getInformation(BuildConfig.FLAVOR), font3))
            Log.d(
                "REOS",
                "DocumentCobranzaPDF.generarPdf.Induvis.getInformation(BuildConfig.FLAVOR):" + Induvis.getInformation(
                    BuildConfig.FLAVOR
                )
            )
            cellTable.disableBorderSide(Rectangle.BOX)
            cellTable.setHorizontalAlignment(Element.ALIGN_LEFT)
            tblcliente.addCell(cellTable)
            cellTable = PdfPCell(Phrase(listCollectionDetail?.CardCode, font))
            cellTable.disableBorderSide(Rectangle.BOX)
            cellTable.setHorizontalAlignment(Element.ALIGN_LEFT)
            tblcliente.addCell(cellTable)
            cellTable = PdfPCell(Phrase(listCollectionDetail?.CardName, font))
            cellTable.disableBorderSide(Rectangle.BOX)
            cellTable.setHorizontalAlignment(Element.ALIGN_LEFT)
            tblcliente.addCell(cellTable)
            documento.add(tblcliente)
            val tblcobrador = PdfPTable(1)
            tblcobrador.widthPercentage = 100f
            var cellcobrador = PdfPCell(
                Phrase(
                    context.resources.getString(R.string.separator_short) + context.resources.getString(
                        R.string.data
                    ).uppercase(
                        Locale.getDefault()
                    ) + " " + context.resources.getString(R.string.debt_collector).uppercase(
                        Locale.getDefault()
                    ) + context.resources.getString(R.string.separator_short), font2
                )
            )
            cellcobrador.disableBorderSide(Rectangle.BOX)
            cellcobrador.horizontalAlignment = Element.ALIGN_CENTER
            tblcobrador.addCell(cellcobrador)
            cellcobrador = PdfPCell(Phrase(ObjUsuario.fuerzatrabajo_id+" "+ObjUsuario.nombrefuerzatrabajo, font))
            cellcobrador.disableBorderSide(Rectangle.BOX)
            cellcobrador.horizontalAlignment = Element.ALIGN_LEFT
            tblcobrador.addCell(cellcobrador)
            cellcobrador = PdfPCell(
                Phrase(
                    context.resources.getString(R.string.separator_short) + context.resources.getString(
                        R.string.data
                    ).uppercase(
                        Locale.getDefault()
                    ) + " " + context.resources.getString(R.string.documents).uppercase(
                        Locale.getDefault()
                    ) + context.resources.getString(R.string.separator_short), font2
                )
            )
            cellcobrador.disableBorderSide(Rectangle.BOX)
            cellcobrador.horizontalAlignment = Element.ALIGN_CENTER
            tblcobrador.addCell(cellcobrador)
            documento.add(tblcobrador)
            Log.e("REOS", "DocumentoCobranzaPDF-generarPdf-Datosdocumento")
            val tbl = PdfPTable(2)
            tbl.widthPercentage = 100f
            var cell = PdfPCell(Phrase(context.resources.getString(R.string.date), font4))
            cell.disableBorderSide(Rectangle.BOX)
            cell.horizontalAlignment = Element.ALIGN_LEFT
            tbl.addCell(cell)
            Log.e(
                "REOS",
                "DocumentCobranzaPDF.generarPdf.FechaHora:" + Induvis.getDate(
                    BuildConfig.FLAVOR,
                    listCollectionDetail?.IncomeDate
                ) + " " + Induvis.getTime(
                    BuildConfig.FLAVOR, listCollectionDetail?.IncomeTime
                )
            )
            cell = PdfPCell(
                Phrase(
                    Induvis.getDate(BuildConfig.FLAVOR, listCollectionDetail?.IncomeDate) + " " + Induvis.getTime(
                        BuildConfig.FLAVOR, listCollectionDetail?.IncomeTime
                    ), font4
                )
            )
            cell.disableBorderSide(Rectangle.BOX)
            cell.horizontalAlignment = Element.ALIGN_RIGHT
            tbl.addCell(cell)
            cell = PdfPCell(Phrase(context.resources.getString(R.string.receip), font4))
            cell.disableBorderSide(Rectangle.BOX)
            cell.horizontalAlignment = Element.ALIGN_LEFT
            tbl.addCell(cell)
            Log.e("REOS", "DocumentCobranzaPDF.generarPdf.recibo:${listCollectionDetail?.Receip}")
            cell = PdfPCell(Phrase(listCollectionDetail?.Receip, font4))
            cell.disableBorderSide(Rectangle.BOX)
            cell.horizontalAlignment = Element.ALIGN_RIGHT
            tbl.addCell(cell)
            Log.e(
                "REOS",
                "DocumentCobranzaPDF.generarPdf.BuildConfig.FLAVOR.:" + BuildConfig.FLAVOR
            )
            if (SesionEntity.perfil_id == "Chofer" || SesionEntity.perfil_id == "CHOFER") {
                if (BuildConfig.FLAVOR != "paraguay") {
                    cell = PdfPCell(
                        Phrase(
                            context.resources.getString(R.string.type_collection),
                            font4
                        )
                    )
                    cell.disableBorderSide(Rectangle.BOX)
                    cell.horizontalAlignment = Element.ALIGN_LEFT
                    tbl.addCell(cell)
                    Log.e("REOS", "DocumentCobranzaPDF.generarPdf.type:${listCollectionDetail?.U_VIS_Type}")
                    cell = PdfPCell(Phrase(listCollectionDetail?.U_VIS_Type, font4))
                    cell.disableBorderSide(Rectangle.BOX)
                    cell.horizontalAlignment = Element.ALIGN_RIGHT
                    tbl.addCell(cell)
                }
            }
            cell = PdfPCell(Phrase(context.resources.getString(R.string.documents), font4))
            cell.disableBorderSide(Rectangle.BOX)
            cell.horizontalAlignment = Element.ALIGN_LEFT
            tbl.addCell(cell)
            Log.e("REOS", "DocumentCobranzaPDF.generarPdf.nrodocumento:${listCollectionDetail?.LegalNumber}")
            cell = PdfPCell(Phrase(listCollectionDetail?.LegalNumber, font))
            cell.disableBorderSide(Rectangle.BOX)
            cell.horizontalAlignment = Element.ALIGN_RIGHT
            tbl.addCell(cell)
            cell = PdfPCell(Phrase(context.resources.getString(R.string.amount), font4))
            cell.horizontalAlignment = Element.ALIGN_LEFT
            cell.disableBorderSide(Rectangle.BOX)
            tbl.addCell(cell)
            Log.e(
                "REOS",
                "DocumentCobranzaPDF.generarPdf.importe:" + Convert.currencyForView(listCollectionDetail?.DocTotal)
            )
            cell = PdfPCell(Phrase(Convert.currencyForView(listCollectionDetail?.DocTotal), font4))
            cell.disableBorderSide(Rectangle.BOX)
            cell.horizontalAlignment = Element.ALIGN_RIGHT
            tbl.addCell(cell)
            cell = PdfPCell(Phrase(context.resources.getString(R.string.balance), font4))
            cell.disableBorderSide(Rectangle.BOX)
            cell.horizontalAlignment = Element.ALIGN_LEFT
            tbl.addCell(cell)
            Log.e("REOS", "DocumentCobranzaPDF.generarPdf.saldo:" + Convert.currencyForView(listCollectionDetail?.Balance))
            cell = PdfPCell(Phrase(Convert.currencyForView(listCollectionDetail?.Balance), font4))
            cell.disableBorderSide(Rectangle.BOX)
            cell.horizontalAlignment = Element.ALIGN_RIGHT
            tbl.addCell(cell)
            cell = PdfPCell(Phrase(context.resources.getString(R.string.amount_charged), font4))
            cell.disableBorderSide(Rectangle.BOX)
            cell.horizontalAlignment = Element.ALIGN_LEFT
            tbl.addCell(cell)
            Log.e(
                "REOS",
                "DocumentCobranzaPDF.generarPdf.cobrado:" + Convert.currencyForView(listCollectionDetail?.AmountCharged)
            )
            cell = PdfPCell(Phrase(Convert.currencyForView(listCollectionDetail?.AmountCharged), font4))
            cell.disableBorderSide(Rectangle.BOX)
            cell.horizontalAlignment = Element.ALIGN_RIGHT
            tbl.addCell(cell)
            cell = PdfPCell(Phrase(context.resources.getString(R.string.new_balance), font4))
            cell.disableBorderSide(Rectangle.BOX)
            cell.horizontalAlignment = Element.ALIGN_LEFT
            tbl.addCell(cell)
            Log.e(
                "REOS",
                "DocumentCobranzaPDF.generarPdf.nuevo_saldo:" + Convert.currencyForView(listCollectionDetail?.NewBalance)
            )
            cell = PdfPCell(Phrase(Convert.currencyForView(listCollectionDetail?.NewBalance), font4))
            cell.disableBorderSide(Rectangle.BOX)
            cell.horizontalAlignment = Element.ALIGN_RIGHT
            tbl.addCell(cell)
            //tbl.setWidthPercentage(100);
            tbl.addCell(imagenqr)
            documento.add(tbl)
            documento.add(imagenqr)
        } catch (e: DocumentException) {
            e.printStackTrace()
            Log.e("REOS", "DocumentCobranzaPDF-generarPdf-DocumentException.E$e")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("REOS", "DocumentCobranzaPDF-generarPdf-IOException.E$e")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("REOS", "DocumentCobranzaPDF-generarPdf-Exception.E$e")
        } finally {
            // Cerramos el documento.
            documento.close()

            ///////////////////ABRIR PDF////////////////////////
            Log.e("REOS", "DocumentCobranzaPDF-generarPdf-SesionEntity.Print" + SesionEntity.Print)
            when (BuildConfig.FLAVOR) {
                "bolivia", "ecuador", "chile", "paraguay", "espania" -> OpenDocumentPDF(
                    listCollectionDetail?.Receip,
                    context
                )
                "peru", "perurofalab", "marruecos" -> if (SesionEntity.Print == "N") {
                    OpenDocumentPDF(listCollectionDetail?.Receip, context)
                } else {
                    //DeleteDocumentPDF(recibo,context);
                    val file = File(
                        Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        NOMBRE_DIRECTORIO
                    )
                    var Cadenafile = ""
                    Cadenafile = file.toString()
                    val ruta = "$Cadenafile/${listCollectionDetail?.Receip}.pdf"
                    val menuView = MenuView()
                    MenuView.getPrinterInstance().printPdf(ruta, 500, 0, 0, 0, 20)
                }
            }


            ///////////////////////////////////////////////////
        }
    }


    @Throws(IOException::class)
    fun crearFichero(nombreFichero: String?): File? {
        val ruta = getRuta()
        var fichero: File? = null
        if (ruta != null) fichero = File(ruta, nombreFichero)
        return fichero
    }

    /**
     * Obtenemos la ruta donde vamos a almacenar el fichero.
     *
     * @return
     */
    fun getRuta(): File? {

        // El fichero sera almacenado en un directorio dentro del directorio
        // Descargas
        var ruta: File? = null
        if (Environment.MEDIA_MOUNTED == Environment
                .getExternalStorageState()
        ) {
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

    private fun OpenDocumentPDF(recibo: String?, context: Context) {
        try {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator
                        + NOMBRE_DIRECTORIO + File.separator + recibo + ".pdf"
            )
            val excelPath = FileProvider.getUriForFile(context, context.packageName, file)
            val target = Intent(Intent.ACTION_VIEW)
            target.setDataAndType(excelPath, "application/pdf")
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(target)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                context,
                context.resources.getText(R.string.mse_necessary_install_PDF),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun DeleteDocumentPDF(recibo: String, context: Context): Boolean {
        var status = false
        Log.e("REOS", "DocumentCobranzaPDF-DeleteDocumentPDF-Inicio")
        try {
            status = File(
                (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator
                        + NOMBRE_DIRECTORIO + File.separator + recibo + ".pdf")
            ).delete()
            Log.e(
                "REOS",
                ("DocumentCobranzaPDF-DeleteDocumentPDF-ruta" + Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
                ).path + File.separator
                        + NOMBRE_DIRECTORIO + File.separator + recibo + ".pdf")
            )
            //Uri  excelPath = FileProvider.getUriForFile(context, context.getPackageName(), file);
            //Intent target = new Intent(Intent.ACTION_VIEW);
            //target.setDataAndType(excelPath,"application/pdf");
            //target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //context.startActivity(target);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("REOS", "DocumentCobranzaPDF-DeleteDocumentPDF-error$e")
            Toast.makeText(context, "No se pudo eliminar el Archivi PDF", Toast.LENGTH_SHORT).show()
        }
        Log.e("REOS", "DocumentCobranzaPDF-DeleteDocumentPDF-Finalizo")
        return status
    }
}
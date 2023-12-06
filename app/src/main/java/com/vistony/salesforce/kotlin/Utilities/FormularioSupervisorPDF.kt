package com.vistony.salesforce.kotlin.Utilities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.FileProvider
import com.lowagie.text.*
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import com.lowagie.text.pdf.PdfWriter
import com.vistony.salesforce.Controller.Adapters.ListHistoricStatusDispatchAdapter
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Model.ApiResponse
import com.vistony.salesforce.kotlin.Model.FormularioGaleria
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class FormularioSupervisorPDF {
    private val NOMBRE_DIRECTORIO = "Formulario"

    fun generarPdfFormularioSupervisorPDF(
        context: Context,
        apiResponse: ApiResponse,
        typePdf: String
    ) {
        var ObjUsuario = UsuarioSQLiteEntity()
        val usuarioSQLite = UsuarioSQLite(context)
        ObjUsuario = usuarioSQLite.ObtenerUsuarioSesion()

        val pagina = Rectangle(
            36f, 36f,  //559
            650f //, 806
            , 1120f //PageSize.ARCH_A
        )
        val documento = Document(pagina)
        val f = crearFichero("informe_"+apiResponse.datosPrincipales!!.numInforme +"_" + (apiResponse.datosPrincipales!!.fechaHoy)!!.split(" ").first()+".pdf")
        val ficheroPdf = FileOutputStream(f!!.absolutePath)
        val writer = PdfWriter.getInstance(documento, ficheroPdf)

        documento.open()

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
        /*val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val imagen = Image.getInstance(stream.toByteArray())*/
        val imagen = convertirImageBitmapAImage(bitmap)
        imagen.alignment = Element.ALIGN_CENTER
        documento.add(imagen)



// Agregamos título
        val title = Font(Font.HELVETICA, 24f, Font.BOLD)
        documento.add(Paragraph("Formulario de Supervisión", title).apply {
            spacingBefore = 16f
            alignment = Element.ALIGN_CENTER
        })

        // Datos Principales
        val subheaderFont = Font(Font.HELVETICA, 18f, Font.BOLD)
        val bodyFont = Font(Font.HELVETICA, 14f)

        documento.add(Chunk.NEWLINE)
        documento.add(Paragraph("Datos Principales", subheaderFont).apply {
            spacingAfter = 16f}
        )

// Crear tabla con 12 columnas
        val tablaDatosPrincipales = PdfPTable(12)

// Ajustar la tabla al 100% del ancho del documento
        tablaDatosPrincipales.widthPercentage = 100f
// Definir anchos de las columnas. Los valores son proporcionales, por ejemplo, 1:2 significa que la segunda columna es dos veces más ancha que la primera.
        tablaDatosPrincipales.setWidths(floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f))


// Añadir celdas a la tabla para el número de informe y fecha

        val cellNumInforme = PdfPCell(Phrase("Número de informe:", bodyFont))
        cellNumInforme.colspan = 2
        configurarCelda(cellNumInforme)
        tablaDatosPrincipales.addCell(cellNumInforme)

        val cellValorNumInforme = PdfPCell(Phrase(apiResponse.datosPrincipales!!.numInforme ?: "-", bodyFont))
        cellValorNumInforme.colspan = 4
        configurarCelda(cellValorNumInforme)
        tablaDatosPrincipales.addCell(cellValorNumInforme)


        val cellFechaFormulario = PdfPCell(Phrase("Fecha:", bodyFont))
        cellFechaFormulario.colspan = 2
        configurarCelda(cellFechaFormulario)
        tablaDatosPrincipales.addCell(cellFechaFormulario)

        val cellValorFechaFormulario = PdfPCell(Phrase((apiResponse.datosPrincipales?.fechaHoy)!!.split(" ").first() ?: "-", bodyFont))
        cellValorFechaFormulario.colspan = 4
        configurarCelda(cellValorFechaFormulario)
        tablaDatosPrincipales.addCell(cellValorFechaFormulario)

// Añadir celdas a la tabla para el nombre de supervisor
        val cellNombreSupervisor = PdfPCell(Phrase("Nombre de Supervisor:", bodyFont))
        cellNombreSupervisor.colspan = 3
        configurarCelda(cellNombreSupervisor)
        tablaDatosPrincipales.addCell(cellNombreSupervisor)

        val cellValorSupervisor = PdfPCell(Phrase(apiResponse.datosPrincipales?.nombreSupervisor ?: "-", bodyFont))
        cellValorSupervisor.colspan = 9
        configurarCelda(cellValorSupervisor)
        tablaDatosPrincipales.addCell(cellValorSupervisor)

        val cellNombreVendedor = PdfPCell(Phrase("Nombre de Vendedor:", bodyFont))
        cellNombreVendedor.colspan = 3
        configurarCelda(cellNombreVendedor)
        tablaDatosPrincipales.addCell(cellNombreVendedor)

        val cellValorVendedor = PdfPCell(Phrase(apiResponse.datosPrincipales?.nombreVendedor ?: "-", bodyFont))
        cellValorVendedor.colspan = 9
        configurarCelda(cellValorVendedor)
        tablaDatosPrincipales.addCell(cellValorVendedor)

        documento.add(tablaDatosPrincipales)

        // Datos de Visita
        documento.add(Chunk.NEWLINE)
        documento.add(Paragraph("Datos Generales de la Visita", subheaderFont).apply {
            spacingAfter = 16f}
        )


// Crear tabla con 12 columnas
        val tablaDatosVisita = PdfPTable(12)

// Ajustar la tabla al 100% del ancho del documento
        tablaDatosVisita.widthPercentage = 100f
// Definir anchos de las columnas. Los valores son proporcionales, por ejemplo, 1:2 significa que la segunda columna es dos veces más ancha que la primera.
        tablaDatosVisita.setWidths(floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f))



        val cellZona = PdfPCell(Phrase("Zona", bodyFont))
        cellZona.colspan = 3
        configurarCelda(cellZona)
        tablaDatosVisita.addCell(cellZona)

        val cellValorZona = PdfPCell(Phrase(apiResponse.datosVisita?.zona ?: "-", bodyFont))
        cellValorZona.colspan = 10
        configurarCelda(cellValorZona)
        tablaDatosVisita.addCell(cellValorZona)

        documento.add(tablaDatosVisita)
        documento.add(Paragraph("Comentario adicional:", subheaderFont).apply {
            spacingAfter = 16f}
        )


        // Crear tabla con 12 columnas
        val tablaComentario = PdfPTable(12)

// Ajustar la tabla al 100% del ancho del documento
        tablaComentario.widthPercentage = 100f
// Definir anchos de las columnas. Los valores son proporcionales, por ejemplo, 1:2 significa que la segunda columna es dos veces más ancha que la primera.
        tablaComentario.setWidths(floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f))


        val celdaComentario = PdfPCell(Phrase(apiResponse.datosVisita?.observacionZona ?: "-"))
        celdaComentario.colspan = 12
        configurarCelda(celdaComentario)
        tablaComentario.addCell(celdaComentario)

        documento.add(tablaComentario)
        // Tipos de Salida (puedes hacerlo con una tabla o lista, aquí es una lista simple)
        documento.add(Chunk.NEWLINE)
        documento.add(Paragraph("Tipo de Salida", subheaderFont).apply {
            spacingAfter = 16f}
        )


        // Crear tabla con 12 columnas
        val tablaTipoVisita = PdfPTable(12)

// Ajustar la tabla al 100% del ancho del documento
        tablaTipoVisita.widthPercentage = 100f
// Definir anchos de las columnas. Los valores son proporcionales, por ejemplo, 1:2 significa que la segunda columna es dos veces más ancha que la primera.
        tablaTipoVisita.setWidths(floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f))



        apiResponse.datosPrincipales?.tipoSalida?.forEach { item ->
            if(item.marcado != false){
                val celdaTipoSalida = PdfPCell(Phrase("${item.opcion ?: "-"}"))
                celdaTipoSalida.colspan = 12
                configurarCelda(celdaTipoSalida)
                tablaTipoVisita.addCell(celdaTipoSalida)
            }
        }
        documento.add(tablaTipoVisita)

        documento.add(Chunk.NEWLINE)

        // Crear tabla con 12 columnas
        val tablaDatosVisita2 = PdfPTable(12)

// Ajustar la tabla al 100% del ancho del documento
        tablaDatosVisita2.widthPercentage = 100f
// Definir anchos de las columnas. Los valores son proporcionales, por ejemplo, 1:2 significa que la segunda columna es dos veces más ancha que la primera.
        tablaDatosVisita2.setWidths(floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f))





        val cellHoraInicio = PdfPCell(Phrase("Hora de inicio:", bodyFont))
        cellHoraInicio.colspan = 3
        configurarCelda(cellHoraInicio)
        tablaDatosVisita2.addCell(cellHoraInicio)

        val cellValorHoraInicio = PdfPCell(Phrase(apiResponse.datosVisita?.horaInicio ?: "-", bodyFont))
        cellValorHoraInicio.colspan = 2
        configurarCelda(cellValorHoraInicio)
        tablaDatosVisita2.addCell(cellValorHoraInicio)

        val cellSpaceHora = PdfPCell(Phrase(" ", bodyFont))
        cellSpaceHora.colspan = 3
        configurarCelda(cellSpaceHora)
        tablaDatosVisita2.addCell(cellSpaceHora)


        val cellHoraFin = PdfPCell(Phrase("Hora Fin:", bodyFont))
        cellHoraFin.colspan = 2
        configurarCelda(cellHoraFin)
        tablaDatosVisita2.addCell(cellHoraFin)

        val cellValorHoraFin = PdfPCell(Phrase(apiResponse.datosVisita?.horaFin ?: "-", bodyFont))
        cellValorHoraFin.colspan = 2
        configurarCelda(cellValorHoraFin)
        tablaDatosVisita2.addCell(cellValorHoraFin)

        val cellClientesNuevos = PdfPCell(Phrase("Clientes Nuevos:", bodyFont))
        cellClientesNuevos.colspan = 3
        configurarCelda(cellClientesNuevos)
        tablaDatosVisita2.addCell(cellClientesNuevos)

        val cellValorClientesNuevos = PdfPCell(Phrase(apiResponse.datosVisita?.clientesNuevos ?: "-", bodyFont))
        cellValorClientesNuevos.colspan = 3
        configurarCelda(cellValorClientesNuevos)
        tablaDatosVisita2.addCell(cellValorClientesNuevos)



        val cellClientesEmpadronados = PdfPCell(Phrase("Clientes Empadronados:", bodyFont))
        cellClientesEmpadronados.colspan = 3
        configurarCelda(cellClientesEmpadronados)
        tablaDatosVisita2.addCell(cellClientesEmpadronados)

        val cellValorClientesEmpadronados = PdfPCell(Phrase(apiResponse.datosVisita?.clientesEmpadronados ?: "-", bodyFont))
        cellValorClientesEmpadronados.colspan = 3
        configurarCelda(cellValorClientesEmpadronados)
        tablaDatosVisita2.addCell(cellValorClientesEmpadronados)

        documento.add(tablaDatosVisita2)


        documento.add(Chunk.NEWLINE)

        documento.add(Paragraph("Resumen", subheaderFont).apply {
            spacingAfter = 16f}
        )
        // Crear tabla con 12 columnas
        val tablaResumen = PdfPTable(12)

// Ajustar la tabla al 100% del ancho del documento
        tablaResumen.widthPercentage = 100f
// Definir anchos de las columnas. Los valores son proporcionales, por ejemplo, 1:2 significa que la segunda columna es dos veces más ancha que la primera.
        tablaResumen.setWidths(floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f))

        val celdaVaciaCabecera = PdfPCell(Phrase(" ", bodyFont))
        celdaVaciaCabecera.colspan = 4
        configurarCelda(celdaVaciaCabecera)
        tablaResumen.addCell(celdaVaciaCabecera)
        val celdaCantidadCabecera = PdfPCell(Phrase("Cantidad", bodyFont))
        celdaCantidadCabecera.colspan = 4
        configurarCelda(celdaCantidadCabecera)
        tablaResumen.addCell(celdaCantidadCabecera)
        val celdaMontoCabecera = PdfPCell(Phrase("Monto", bodyFont))
        celdaMontoCabecera.colspan = 4
        configurarCelda(celdaMontoCabecera)
        tablaResumen.addCell(celdaMontoCabecera)

        apiResponse.datosVisita!!.resumen.forEachIndexed() { index, resumen ->
            val celdaResumenTipo = PdfPCell(Phrase(resumen.tipo?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }, bodyFont))
            celdaResumenTipo.colspan = 4
            configurarCelda(celdaResumenTipo)
            tablaResumen.addCell(celdaResumenTipo)
            val celdaResumenCantidad = PdfPCell(Phrase(resumen.cantidad, bodyFont))
            celdaResumenCantidad.colspan = 4
            configurarCelda(celdaResumenCantidad)
            tablaResumen.addCell(celdaResumenCantidad)
            val celdaResumenMonto = PdfPCell(Phrase(resumen.monto, bodyFont))
            celdaResumenMonto.colspan = 4
            configurarCelda(celdaResumenMonto)
            tablaResumen.addCell(celdaResumenMonto)

        }

        documento.add(tablaResumen)


        documento.add(Chunk.NEWLINE)



        documento.add(Chunk.NEWLINE)
        documento.add(Paragraph("Comentario del trabajo de campo realizado", subheaderFont).apply {
            spacingAfter = 16f}
        )


        // Crear tabla con 12 columnas
        val tablaComentarioAdicional = PdfPTable(12)

// Ajustar la tabla al 100% del ancho del documento
        tablaComentarioAdicional.widthPercentage = 100f
// Definir anchos de las columnas. Los valores son proporcionales, por ejemplo, 1:2 significa que la segunda columna es dos veces más ancha que la primera.
        tablaComentarioAdicional.setWidths(floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f))


        val celdaComentarioAdicional = PdfPCell(Phrase(apiResponse.comentario ?: "-"))
        celdaComentarioAdicional.colspan = 12
        configurarCelda(celdaComentarioAdicional)
        tablaComentarioAdicional.addCell(celdaComentarioAdicional)

        documento.add(tablaComentarioAdicional)

        // Formulario
        documento.add(Chunk.NEWLINE)
        documento.add(Paragraph("Formulario", subheaderFont).apply {
            spacingAfter = 16f}
        )



        // Crear tabla con 12 columnas
        val tablaFormulario = PdfPTable(12)

// Ajustar la tabla al 100% del ancho del documento
        tablaFormulario.widthPercentage = 100f
// Definir anchos de las columnas. Los valores son proporcionales, por ejemplo, 1:2 significa que la segunda columna es dos veces más ancha que la primera.
        tablaFormulario.setWidths(floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f))


        val celdaCabeceraPregunta = PdfPCell(Phrase("Preguntas"))
        celdaCabeceraPregunta.colspan = 6
        configurarCelda(celdaCabeceraPregunta)
        tablaFormulario.addCell(celdaCabeceraPregunta)

        val celdaCabeceraRespuesta = PdfPCell(Phrase("Respuestas"))
        celdaCabeceraRespuesta.colspan = 4
        configurarCelda(celdaCabeceraRespuesta)
        tablaFormulario.addCell(celdaCabeceraRespuesta)

        val celdaCabeceraValor = PdfPCell(Phrase("Valor"))
        celdaCabeceraValor.colspan = 2
        configurarCelda(celdaCabeceraValor)
        tablaFormulario.addCell(celdaCabeceraValor)


        var sumaTotal by mutableStateOf(0)


        apiResponse.formulario?.forEachIndexed() { index, item ->
            val celdaPregunta = PdfPCell(Phrase("${index+1}. ${item.pregunta ?: "-"}"))
            celdaPregunta.colspan = 6
            configurarCelda(celdaPregunta)
            tablaFormulario.addCell(celdaPregunta)

            var celdaOpcionSelected = PdfPCell(Phrase("-"))
            if(typePdf=="formularioprincipal"){
                item.opciones.forEachIndexed() { i, element ->
                    if (element.valor == item.respuesta || element.opcion == item.respuesta) {
                        celdaOpcionSelected = PdfPCell(Phrase(" ${item.opciones.get(i).opcion ?: "-"}"))
                    }
                }
            }else{
                celdaOpcionSelected = PdfPCell(Phrase(" ${item.respuesta}"))
            }
            celdaOpcionSelected.colspan = 4
            configurarCelda(celdaOpcionSelected)
            tablaFormulario.addCell(celdaOpcionSelected)

            val puntuacion = obtenerPuntuacionNumerica( item.respuesta ?: "-")
            val celdaRespuesta = PdfPCell(Phrase(puntuacion.toString() ?: "-"))
            celdaRespuesta.colspan = 2
            configurarCelda(celdaRespuesta)
            tablaFormulario.addCell(celdaRespuesta)



            sumaTotal += puntuacion

        }

        val celdaVacioFormulario = PdfPCell(Phrase( " "))
        celdaVacioFormulario.colspan = 6
        configurarCelda(celdaVacioFormulario)
        tablaFormulario.addCell(celdaVacioFormulario)
        val celdaFormularioTotal = PdfPCell(Phrase("Promedio:"))
        celdaFormularioTotal.colspan = 4
        configurarCelda(celdaFormularioTotal)
        tablaFormulario.addCell(celdaFormularioTotal)

        val celdaFormularioValorTotal = PdfPCell(Phrase((sumaTotal/apiResponse.formulario!!.size).toString() ?: "-"))
        celdaFormularioValorTotal.colspan = 2
        configurarCelda(celdaFormularioValorTotal)
        tablaFormulario.addCell(celdaFormularioValorTotal)


        documento.add(tablaFormulario)
        // Comentario
        documento.add(Chunk.NEWLINE)

        apiResponse?.galeria?.takeIf { it.isNotEmpty() }?.let { listGallery ->
            for (photo in listGallery) {
                try {
                    val _bitmap : Bitmap
                    if(typePdf=="formularioprincipal"){
                        _bitmap = photo.bitmap
                    }else{
                        _bitmap = ListHistoricStatusDispatchAdapter.getBitmapFromURL(photo.uri)
                    }

                    val imagen: Image = convertirImageBitmapAImage(_bitmap)
                    imagen.alignment = Image.MIDDLE
                    documento.add(imagen)
                    documento.add(Chunk.NEWLINE)
                } catch (e: IOException) {
                    // Maneja la excepción de decodificación Base64 aquí.
                    Log.e("PDF Generation", "Error al decodificar Base64: ${e.message}")
                    // Puedes optar por continuar con la iteración o realizar otra acción según tus necesidades.
                }
            }
        } ?: run {
            // La galería está vacía o apiResponse o galeria es nulo, puedes manejarlo de acuerdo a tus necesidades.
            Log.e("PDF Generation", "La galería está vacía o apiResponse o galeria es nulo.")
        }

        documento.close()

        writer.close()
        OpenDocumentPDF(apiResponse.datosPrincipales!!.numInforme, context, apiResponse)

    }

    private fun convertirImageBitmapAImage(bitmap: Bitmap): Image {
        // Tamaño deseado del cuadrado
        val squareSize = 500f

        // Obtener las dimensiones originales de la imagen
        val originalWidth = bitmap.width.toFloat()
        val originalHeight = bitmap.height.toFloat()

        // Calcular las nuevas dimensiones de la imagen manteniendo la proporción
        val aspectRatio = originalWidth / originalHeight
        val newWidth: Float
        val newHeight: Float

        if (originalWidth > originalHeight) {
            newWidth = squareSize
            newHeight = squareSize / aspectRatio
        } else {
            newWidth = squareSize * aspectRatio
            newHeight = squareSize
        }

        // Redimensionar la imagen al nuevo tamaño
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth.toInt(), newHeight.toInt(), true)

        // Convertir el bitmap redimensionado a bytes
        val outputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val imageBytes = outputStream.toByteArray()

        // Crear una instancia de la clase Image de iText utilizando los bytes de la imagen redimensionada.
        return Image.getInstance(imageBytes)
    }

    fun configurarCelda(celda: PdfPCell) {
        celda.horizontalAlignment = PdfPCell.ALIGN_LEFT
        celda.verticalAlignment = PdfPCell.ALIGN_MIDDLE
        celda.paddingLeft = 10f
        celda.paddingRight = 10f
        celda.paddingTop = 5f
        celda.paddingBottom = 5f
    }
    @Throws(IOException::class)
    fun crearFichero(nombreFichero: String?): File? {
        val ruta = getRuta()
        var fichero: File? = null
        if (ruta != null) fichero = nombreFichero?.let { File(ruta, it) }
        return fichero
    }



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

    private fun OpenDocumentPDF(recibo: String?, context: Context, apiResponse: ApiResponse) {
        try {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator
                        + NOMBRE_DIRECTORIO + File.separator + "informe_" + recibo +"_" + (apiResponse.datosPrincipales!!.fechaHoy)!!.split(" ").first() + ".pdf"
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

fun obtenerPuntuacionNumerica(puntuacionString: String): Int {
    return when (puntuacionString.lowercase(Locale.ROOT)) {
        "muy malo" -> 1
        "malo" -> 2
        "regular" -> 3
        "bueno" -> 4
        "muy bueno" -> 5
        else -> puntuacionString.toInt() // Valor predeterminado o indicador de puntuación no válida
    }
}


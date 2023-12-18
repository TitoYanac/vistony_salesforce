package com.vistony.salesforce.kotlin.Utilities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import com.lowagie.text.*
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import com.lowagie.text.pdf.PdfWriter
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Model.ApiResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class FormularioSupervisorPDF {
    private val NOMBRE_DIRECTORIO = "Formulario"

    private val subheaderFont = Font(Font.HELVETICA, 18f, Font.BOLD)
    private val bodyFont = Font(Font.HELVETICA, 14f)
    @OptIn(DelicateCoroutinesApi::class)
    fun generarPdfFormularioSupervisorPDF(
        context: Context,
        apiResponse: ApiResponse,
        typePdf: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            if(typePdf != "formularioprincipal"){
                coroutineScope {
                    val jobs = mutableListOf<Job>()
                    if(apiResponse.galeria!= null){
                        val cont = apiResponse.galeria!!.size
                        for ( i in 0 until cont) {
                            val formularioGaleria = apiResponse.galeria?.get(i)
                            if(formularioGaleria != null && formularioGaleria.uri != null){
                                Log.e("FormularioSupervisorPDF2", "Descargando imagen: ${formularioGaleria.uri}")
                                val job = launch {
                                    val nonNullUrlString :String? = formularioGaleria.uri
                                    val bitmap: Bitmap = loadImageFromUrl(nonNullUrlString!!)!!
                                    formularioGaleria.bitmap = bitmap
                                }
                                jobs.add(job)
                            }
                        }
                    }
                }
            }
            val usuarioSQLite = UsuarioSQLite(context)
            val objUsuario = usuarioSQLite.ObtenerUsuarioSesion()
            val pagina = crearPagina()
            val documento = Document(pagina)
            val fichero = crearFichero(apiResponse)
            val ficheroPdf = FileOutputStream(fichero!!.absolutePath)
            val writer = PdfWriter.getInstance(documento, ficheroPdf)
            documento.open()
            agregarLogo(documento, context, objUsuario.compania_id)
            agregarTitulo(documento)
            agregarDatosPrincipales(documento, apiResponse)
            agregarDatosVisita(documento, apiResponse)
            agregarDatosFormulario(documento, apiResponse, typePdf)
            agregarGaleria(documento, apiResponse, typePdf)
            documento.close()
            writer.close()
            openDocumentPDF(apiResponse.datosPrincipales!!.numInforme, context, apiResponse)
        }
    }
    private suspend fun loadImageFromUrl(url: String): Bitmap? {
        Log.e(
            "REOS"
            ,
            "HistoricalDispatchTemplate-loadImageFromUrl-url: "
                    + url)
        return try {
            withContext(Dispatchers.IO)
            {
                var connection = URL(url).openConnection() as? HttpURLConnection
                connection?.instanceFollowRedirects= false
                connection?.connect()
                val responseCode = connection?.responseCode
                if (responseCode == HttpURLConnection.
                    HTTP_OK
                ) {
                    val inputStream: InputStream = connection!!.
                    inputStream
                    val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                    return@withContext bitmap
                } else if (responseCode == HttpURLConnection.
                    HTTP_MOVED_TEMP
                ) {
                    val newUrl = connection?.getHeaderField(
                        "Location"
                    )
                    if (!newUrl.
                        isNullOrBlank
                            ()) {
                        return@withContext loadImageFromUrl(newUrl)
                    }
                }
                Log.e(
                    "REOS"
                    ,
                    "HistoricalDispatchTemplate-loadImageFromUrl-connection failed. Response code:$responseCode"
                )
                return@withContext null
            }
        } catch (e: Exception) {
// Manejar errores aquí
            e.printStackTrace()
            Log.e(
                "REOS"
                ,
                "HistoricalDispatchTemplate-loadImageFromUrl-error: "
                        + e)
            return null
        }
    }
    private fun agregarGaleria(documento: Document, apiResponse: ApiResponse, typePdf: String) {
        apiResponse.galeria?.takeIf { it.isNotEmpty() }?.let { listGallery ->
            for (photo in listGallery) {
                try {
                    val _bitmap : Bitmap = photo.bitmap

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

    }

    private fun agregarDatosFormulario(documento: Document, apiResponse: ApiResponse, typePdf: String) {

        documento.add(Chunk.NEWLINE)
        documento.add(Paragraph("Formulario", subheaderFont).apply {
            spacingAfter = 16f}
        )

        val tablaFormulario : PdfPTable = crearTabla(documento)

        agregarFila(tablaFormulario, listOf(
            mapOf("label" to "Preguntas", "size" to 8),
            mapOf("label" to "Respuesta", "size" to 2),
            mapOf("label" to "Valor", "size" to 2)
        ))


        var sumaTotal by mutableStateOf(0)
        val totalPreguntas = apiResponse.formulario!!.size
        if(typePdf == "formularioprincipal"){
            for(i in 0 until totalPreguntas){
                val pregunta = apiResponse.formulario!![i].pregunta
                val respuesta = apiResponse.formulario!![i].opciones[apiResponse.formulario!![i].respuesta!!.toInt() - 1].opcion
                val valor = apiResponse.formulario!![i].respuesta // respuesta es el codigo de la opcion marcada (1 al 5)
                agregarFila(tablaFormulario, listOf(
                    mapOf("label" to "$i. $pregunta", "size" to 8),
                    mapOf("label" to "$respuesta", "size" to 2),
                    mapOf("label" to "$valor", "size" to 2)
                ))
                sumaTotal += valor!!.toInt()
            }
        }else{
            for(i in 0 until totalPreguntas){
                val pregunta = apiResponse.formulario!![i].pregunta
                val respuesta = apiResponse.formulario!![i].respuesta // respuesta en la pantalla es (muy malo, malo , regular , bueno, muy bueno)
                val valor = obtenerPuntuacionNumerica(apiResponse.formulario!![i].respuesta!!) // respuesta en pantalla busqueda es (muy malo, malo , regular , bueno, muy bueno)
                agregarFila(tablaFormulario, listOf(
                    mapOf("label" to "$i. $pregunta", "size" to 8),
                    mapOf("label" to "$respuesta", "size" to 2),
                    mapOf("label" to "$valor", "size" to 2)
                ))
                sumaTotal += valor!!.toInt()
            }
        }

        agregarFila(tablaFormulario, listOf(
            mapOf("label" to " ", "size" to 8),
            mapOf("label" to "Promedio:", "size" to 2),
            mapOf("label" to "${sumaTotal/totalPreguntas}", "size" to 2)
        ))


        documento.add(tablaFormulario)

        documento.add(Chunk.NEWLINE)
    }

    private fun agregarFila(tabla: PdfPTable, data: List<Map<String, Any>>) {
        for (item in data) {
            val label = item["label"]
            val size = item["size"]

            agregarCelda(tabla ,label, size)
        }
    }

    private fun agregarCelda(tabla : PdfPTable, label: Any?, size: Any?) {
        val celda = PdfPCell(Phrase(label.toString()))
        celda.colspan = size.toString().toInt()
        configurarCelda(celda)
        tabla.addCell(celda)

    }

    private fun crearTabla(documento: Document): PdfPTable {
        val tabla = PdfPTable(12)
        tabla.widthPercentage = 100f
        tabla.setWidths(floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f))
        return tabla
    }


    private fun obtenerPuntuacionNumerica(puntuacionString: String): Int {
        return when (puntuacionString.lowercase(Locale.ROOT)) {
            "muy malo" -> 1
            "malo" -> 2
            "regular" -> 3
            "bueno" -> 4
            "muy bueno" -> 5
            "si" -> 5
            "no" -> 1
            else -> puntuacionString.toInt() // Valor predeterminado o indicador de puntuación no válida
        }
    }

    private fun agregarDatosVisita(documento: Document, apiResponse: ApiResponse) {

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
                val celdaTipoSalida = PdfPCell(Phrase(item.opcion ?: "-"))
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
        val tablaResumen = PdfPTable(12)
        tablaResumen.widthPercentage = 100f
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
        val tablaComentarioAdicional = PdfPTable(12)
        tablaComentarioAdicional.widthPercentage = 100f
        tablaComentarioAdicional.setWidths(floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f))
        val celdaComentarioAdicional = PdfPCell(Phrase(apiResponse.comentario))
        celdaComentarioAdicional.colspan = 12
        configurarCelda(celdaComentarioAdicional)
        tablaComentarioAdicional.addCell(celdaComentarioAdicional)

        documento.add(tablaComentarioAdicional)

    }

    private fun agregarDatosPrincipales(documento: Document, apiResponse: ApiResponse) {

        documento.add(Chunk.NEWLINE)
        documento.add(Paragraph("Datos Principales", subheaderFont).apply {
            spacingAfter = 16f
        })

        val tablaDatosPrincipales = PdfPTable(12).apply {
            widthPercentage = 100f
            setWidths(floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f))
        }

        agregarCeldaConValor(tablaDatosPrincipales, "Número de informe:", apiResponse.datosPrincipales?.numInforme ?: "-", 2, 4, bodyFont)
        agregarCeldaConValor(tablaDatosPrincipales, "Fecha:", (apiResponse.datosPrincipales?.fechaHoy)!!.split(" ").first(), 2, 4, bodyFont)
        agregarCeldaConValor(tablaDatosPrincipales, "Nombre de Supervisor:", apiResponse.datosPrincipales?.nombreSupervisor ?: "-", 3, 9, bodyFont)
        agregarCeldaConValor(tablaDatosPrincipales, "Nombre de Vendedor:", apiResponse.datosPrincipales?.nombreVendedor ?: "-", 3, 9, bodyFont)
        documento.add(tablaDatosPrincipales)
    }

    private fun agregarCeldaConValor(tabla: PdfPTable, textoCelda: String, valor: String, colspanTexto: Int, colspanValor: Int, font: Font) {
        val cellTexto = PdfPCell(Phrase(textoCelda, font)).apply {
            colspan = colspanTexto
            configurarCelda(this)
        }
        tabla.addCell(cellTexto)

        val cellValor = PdfPCell(Phrase(valor, font)).apply {
            colspan = colspanValor
            configurarCelda(this)
        }
        tabla.addCell(cellValor)
    }

    private fun agregarTitulo(documento: Document) {
        val titulo = "Formulario de Supervisión"
        val estiloTitulo = Font(Font.HELVETICA, 24f, Font.BOLD)
        val paragraph = Paragraph(titulo, estiloTitulo).apply {
            spacingBefore = 16f
            alignment = Element.ALIGN_CENTER
        }
        documento.add(paragraph)
    }

    private fun agregarLogo(documento: Document, context: Context, objUsuario: String) {
        val recursosLogos = mapOf(
            "01" to R.mipmap.logo_negro_vistony,
            "C011" to R.mipmap.logo_bluker_negro,
            "13" to R.mipmap.logo_rofalab_negro2
        )

        val resourceId = recursosLogos[objUsuario] ?: R.mipmap.logo_negro_vistony
        val bitmap = BitmapFactory.decodeResource(context.resources, resourceId)

        val imagen = convertirImageBitmapAImage(bitmap)
        imagen.alignment = Element.ALIGN_CENTER
        documento.add(imagen)
    }

    private fun crearPagina(): Rectangle {
        return Rectangle(
            36f, 36f,  //559
            650f //, 806
            , 1120f //PageSize.ARCH_A
        )

    }

    private fun convertirImageBitmapAImage(bitmap: Bitmap): Image {
        val squareSize = 500f
        val originalWidth = bitmap.width.toFloat()
        val originalHeight = bitmap.height.toFloat()
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
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth.toInt(), newHeight.toInt(), true)
        val outputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val imageBytes = outputStream.toByteArray()
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
    fun crearFichero(apiResponse: ApiResponse): File? {
        val nombreFichero = "informe_"+apiResponse.datosPrincipales!!.numInforme +"_" + (apiResponse.datosPrincipales!!.fechaHoy)!!.split(" ").first()+".pdf"
        val ruta = getRuta()
        var fichero: File? = null
        if (ruta != null) fichero = File(ruta, nombreFichero)
        return fichero
    }



    fun getRuta(): File? {
        var ruta: File? = null
        if (Environment.MEDIA_MOUNTED == Environment
                .getExternalStorageState()
        ) {
            ruta = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                NOMBRE_DIRECTORIO
            )
            if (!ruta.mkdirs()) {
                if (!ruta.exists()) {
                    return null
                }
            }
        }
        return ruta
    }

    private fun openDocumentPDF(recibo: String?, context: Context, apiResponse: ApiResponse) {
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

}



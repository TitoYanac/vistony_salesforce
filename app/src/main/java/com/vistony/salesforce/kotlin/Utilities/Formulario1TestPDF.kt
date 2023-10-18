package com.vistony.salesforce.kotlin.Utilities

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.lowagie.text.*
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import com.lowagie.text.pdf.PdfWriter
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.View.Pages.ApiResponse
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class FormularioTest1PDF {
    private val NOMBRE_DIRECTORIO = "Formulario"

    fun generarPdfFormularioTest1PDF(
        context: Context,
        apiResponse: ApiResponse,
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
        val f = crearFichero(apiResponse.datosPrincipales!!.numInforme+".pdf")
        val ficheroPdf = FileOutputStream(f!!.absolutePath)
        val writer = PdfWriter.getInstance(documento, ficheroPdf)

        documento.open()

// Agregamos título
        val title = Font(Font.HELVETICA, 24f, Font.BOLD)
        documento.add(Paragraph("Formulario de Supervisión", title))

        // Datos Principales
        val subheaderFont = Font(Font.HELVETICA, 18f, Font.BOLD)
        val bodyFont = Font(Font.HELVETICA, 14f)

        documento.add(Paragraph("Datos Principales", subheaderFont).apply {
    spacingAfter = 16f}
)
        documento.add(Chunk.NEWLINE)

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
        cellValorNumInforme.colspan = 6
        configurarCelda(cellValorNumInforme)
        tablaDatosPrincipales.addCell(cellValorNumInforme)


        val cellFechaFormulario = PdfPCell(Phrase("Fecha:", bodyFont))
        cellFechaFormulario.colspan = 2
        configurarCelda(cellFechaFormulario)
        tablaDatosPrincipales.addCell(cellFechaFormulario)

        val cellValorFechaFormulario = PdfPCell(Phrase(apiResponse.datosPrincipales?.fechaHoy ?: "-", bodyFont))
        cellValorFechaFormulario.colspan = 2
        configurarCelda(cellValorFechaFormulario)
        tablaDatosPrincipales.addCell(cellValorFechaFormulario)

// Añadir celdas a la tabla para el nombre de supervisor
        val cellNombreSupervisor = PdfPCell(Phrase("Nombre de Supervisor:", bodyFont))
        cellNombreSupervisor.colspan = 2
        configurarCelda(cellNombreSupervisor)
        tablaDatosPrincipales.addCell(cellNombreSupervisor)

        val cellValorSupervisor = PdfPCell(Phrase(apiResponse.datosPrincipales?.nombreSupervisor ?: "-", bodyFont))
        cellValorSupervisor.colspan = 10
        configurarCelda(cellValorSupervisor)
        tablaDatosPrincipales.addCell(cellValorSupervisor)

        val cellNombreVendedor = PdfPCell(Phrase("Nombre de Vendedor:", bodyFont))
        cellNombreVendedor.colspan = 2
        configurarCelda(cellNombreVendedor)
        tablaDatosPrincipales.addCell(cellNombreVendedor)

        val cellValorVendedor = PdfPCell(Phrase(apiResponse.datosPrincipales?.nombreVendedor ?: "-", bodyFont))
        cellValorVendedor.colspan = 10
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

        val cellObservacionZona = PdfPCell(Phrase("Observaciónes de la zona visitada:", bodyFont))
        cellObservacionZona.colspan = 3
        configurarCelda(cellObservacionZona)
        tablaDatosVisita.addCell(cellObservacionZona)

        val cellValorObservacionZona = PdfPCell(Phrase(apiResponse.datosVisita?.observacionZona ?: "-", bodyFont))
        cellValorObservacionZona.colspan = 10
        configurarCelda(cellValorObservacionZona)
        tablaDatosVisita.addCell(cellValorObservacionZona)


        val cellHoraInicio = PdfPCell(Phrase("Hora de inicio:", bodyFont))
        cellHoraInicio.colspan = 2
        configurarCelda(cellHoraInicio)
        tablaDatosVisita.addCell(cellHoraInicio)

        val cellValorHoraInicio = PdfPCell(Phrase(apiResponse.datosVisita?.horaInicio ?: "-", bodyFont))
        cellValorHoraInicio.colspan = 2
        configurarCelda(cellValorHoraInicio)
        tablaDatosVisita.addCell(cellValorHoraInicio)

        val cellSpaceHora = PdfPCell(Phrase(" ", bodyFont))
        cellSpaceHora.colspan = 4
        configurarCelda(cellSpaceHora)
        tablaDatosVisita.addCell(cellSpaceHora)


        val cellHoraFin = PdfPCell(Phrase("Hora Fin:", bodyFont))
        cellHoraFin.colspan = 2
        configurarCelda(cellHoraFin)
        tablaDatosVisita.addCell(cellHoraFin)

        val cellValorHoraFin = PdfPCell(Phrase(apiResponse.datosVisita?.horaFin ?: "-", bodyFont))
        cellValorHoraFin.colspan = 2
        configurarCelda(cellValorHoraFin)
        tablaDatosVisita.addCell(cellValorHoraFin)

        val cellClientesNuevos = PdfPCell(Phrase("Clientes Nuevos:", bodyFont))
        cellClientesNuevos.colspan = 3
        configurarCelda(cellClientesNuevos)
        tablaDatosVisita.addCell(cellClientesNuevos)

        val cellValorClientesNuevos = PdfPCell(Phrase(apiResponse.datosVisita?.clientesNuevos ?: "-", bodyFont))
        cellValorClientesNuevos.colspan = 3
        configurarCelda(cellValorClientesNuevos)
        tablaDatosVisita.addCell(cellValorClientesNuevos)



        val cellClientesEmpadronados = PdfPCell(Phrase("Clientes Empadronados:", bodyFont))
        cellClientesEmpadronados.colspan = 3
        configurarCelda(cellClientesEmpadronados)
        tablaDatosVisita.addCell(cellClientesEmpadronados)

        val cellValorClientesEmpadronados = PdfPCell(Phrase(apiResponse.datosVisita?.clientesEmpadronados ?: "-", bodyFont))
        cellValorClientesEmpadronados.colspan = 3
        configurarCelda(cellValorClientesEmpadronados)
        tablaDatosVisita.addCell(cellValorClientesEmpadronados)

        documento.add(tablaDatosVisita)
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
            val celdaResumenTipo = PdfPCell(Phrase(resumen.tipo, bodyFont))
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
        

        // Tipos de Salida (puedes hacerlo con una tabla o lista, aquí es una lista simple)
        documento.add(Chunk.NEWLINE)
        documento.add(Paragraph("Tipos de Salida", subheaderFont).apply {
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
        celdaCabeceraPregunta.colspan = 10
        configurarCelda(celdaCabeceraPregunta)
        tablaFormulario.addCell(celdaCabeceraPregunta)

        val celdaCabeceraRespuesta = PdfPCell(Phrase("Respuestas"))
        celdaCabeceraRespuesta.colspan = 2
        configurarCelda(celdaCabeceraRespuesta)
        tablaFormulario.addCell(celdaCabeceraRespuesta)

        apiResponse.formulario?.forEachIndexed() { index, item ->
            val celdaPregunta = PdfPCell(Phrase("$index. ${item.pregunta ?: "-"}"))
            celdaPregunta.colspan = 6
            configurarCelda(celdaPregunta)
            tablaFormulario.addCell(celdaPregunta)


            item.opciones.forEachIndexed() { i, element ->
                if (element.valor == item.respuesta) {
                    val celdaOpcionSelected = PdfPCell(Phrase(" ${item.opciones.get(i).opcion ?: "-"}"))
                    celdaOpcionSelected.colspan = 4
                    configurarCelda(celdaOpcionSelected)
                    tablaFormulario.addCell(celdaOpcionSelected)
                }
            }

            val celdaRespuesta = PdfPCell(Phrase(item.respuesta ?: "-"))
            celdaRespuesta.colspan = 2
            configurarCelda(celdaRespuesta)
            tablaFormulario.addCell(celdaRespuesta)

        }
documento.add(tablaFormulario)
        // Comentario
        documento.add(Chunk.NEWLINE)
        documento.add(Paragraph("Comentario Adicional", subheaderFont).apply {
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

        documento.close()

        writer.close()
        OpenDocumentPDF(apiResponse.datosPrincipales!!.numInforme, context)

    }

    fun configurarCelda(celda: PdfPCell) {
        celda.horizontalAlignment = PdfPCell.ALIGN_LEFT
        celda.verticalAlignment = PdfPCell.ALIGN_MIDDLE
        celda.paddingLeft = 10f
        celda.paddingRight = 10f
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
package com.vistony.salesforce.kotlin.View.Template

import PdfListViewModel
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.R
import com.vistony.salesforce.View.MenuView.context
import com.vistony.salesforce.kotlin.Utilities.FormularioSupervisorPDF
import com.vistony.salesforce.kotlin.View.components.DialogView
import com.vistony.salesforce.kotlin.View.components.contexto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun FormularioSupervisorBusquedaTemplate(pdfListViewModel: PdfListViewModel) {
    // Filtrar la lista de elementos entre las fechas seleccionadas
    val pdfItems = pdfListViewModel.pdfItems.collectAsState()
    Log.e("jesusdebug5", "pdfitems: ${pdfItems.value}")

    val contexto = LocalContext.current
    // Crea una lista de elementos filtrados
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botones para abrir el DatePicker para fecha inicial y fecha final
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            DatePickerButton(
                onClick = {
                    showDatePicker(pdfListViewModel=pdfListViewModel,type = "startDate")
                },
                label = pdfListViewModel.startDateButtonText.value,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            DatePickerButton(
                onClick = {
                    showDatePicker(pdfListViewModel=pdfListViewModel,type = "endDate")
                },
                label = pdfListViewModel.endDateButtonText.value,
                modifier = Modifier.weight(1f)
            )
        }
        // Lista de elementos filtrados
        LazyColumn {
            items(pdfItems.value.asReversed()) { item ->
                PdfListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    item = item,
                    pdfListViewModel,
                    contexto
                )
            }
        }
    }
}

@Composable
fun DatePickerButton(onClick: () -> Unit, label: String, modifier: Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(Icons.Outlined.DateRange, contentDescription = null, tint = Color.White)
            Text(label, color = Color.White)
        }
    }
}
fun showDatePicker(pdfListViewModel: PdfListViewModel, type: String) {

    val selectedDate = pdfListViewModel.selectedStartDate.value

    val datePickerDialog = DatePickerDialog(
        contexto,
        { _, year, month, day ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
            }
            if (type == "startDate") {
                pdfListViewModel.onStartDateSelected(selectedCalendar)
                // Actualiza el texto del botón con la fecha seleccionada
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedCalendar.time)
                pdfListViewModel.onStartDateButtonTextUpdated(formattedDate)
            } else if (type == "endDate") {
                pdfListViewModel.onEndDateSelected(selectedCalendar)
                // Actualiza el texto del botón con la fecha seleccionada
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedCalendar.time)
                pdfListViewModel.onEndDateButtonTextUpdated(formattedDate)
            }
        },
        selectedDate.get(Calendar.YEAR),
        selectedDate.get(Calendar.MONTH),
        selectedDate.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}
@Composable
fun PdfListItem(modifier: Modifier = Modifier, item: PdfItemData, listViewModel: PdfListViewModel, _contexto: Context) {

    val _showdialog : MutableState<Boolean> = remember { mutableStateOf(false) }
    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
    ImageDialog(imageBitmap = imageBitmap) { _showdialog.value = false }

    val openDialogShowImage = remember { mutableStateOf(false) }

    if(openDialogShowImage.value)
    {
        DialogView(
            tittle = "Foto", subtittle = "", onClickCancel = {
                openDialogShowImage.value = false
            }, onClickAccept = {
                openDialogShowImage.value = false
            }, statusButtonAccept = false, statusButtonIcon = false, context = context
        ) {
            Image(
                bitmap = imageBitmap.value!!,
                contentDescription = "Google Maps", // decorative
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            )
        }
    }

    if(openDialogShowImage.value)
    {
        DialogView(
            tittle = "Foto", subtittle = "", onClickCancel = {
                openDialogShowImage.value = false
            }, onClickAccept = {
                openDialogShowImage.value = false
            }, statusButtonAccept = false, statusButtonIcon = false, context = context
        ) {
            Image(
                bitmap = imageBitmap.value!!,
                contentDescription = "Google Maps", // decorative
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            )
        }
    }

    Row(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(Icons.Outlined.PictureAsPdf, contentDescription = null)
            Column {
                Row {
                    Text(
                        text = item.numInforme.toString(),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Box(modifier = Modifier.width(12.dp))
                    Text(
                        text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.date.time).toString(),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = item.description,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        //val _context = LocalContext.current

        IconButton(
            onClick = {


                Log.e("BusquedaFormularioSupervisorTemplate", "Descargando PDF: ${item.numInforme}")

                val formularioSupervisorPDF = FormularioSupervisorPDF()
                val apiResponseList = listViewModel.apiResponseList.value?.data ?: emptyList()

                val matchingApiResponse = apiResponseList.firstOrNull {
                    it.datosPrincipales?.numInforme == item.numInforme.toString()
                }

                matchingApiResponse?.let {
                    formularioSupervisorPDF.generarPdfFormularioSupervisorPDF(
                        _contexto,
                        it,
                        "busquedaformulario"
                    )
                    /*if(it.galeria!=null&&it.galeria!!.first().bitmap!=null)
                    {
                        Log.e("retrofitTest4", "popup: ${it.galeria!!.first().bitmap.width} x ${it.galeria!!.first().bitmap.height}")
                        val bitmap =  it.galeria!!.first().bitmap
                        imageBitmap.value = bitmap.asImageBitmap()
                        openDialogShowImage.value = true

                    }*/



                } ?: Log.e(
                    "BusquedaFormularioSupervisorTemplate",
                    "No matching API response found for ${item.numInforme}"
                )
                                              },
            modifier = Modifier
                .size(48.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colors.primary)
        )
        {
            Icon(Icons.Default.Download, contentDescription = null, tint = Color.White)
        }
    }



}

@Composable
fun ImageDialog(imageBitmap: MutableState<ImageBitmap?>, onClose: () -> Unit) {

    val context = LocalContext.current
    val openDialogShowImage = remember { mutableStateOf(false) }

    if(openDialogShowImage.value)
    {
        DialogView(
            tittle = "Foto", subtittle = "", onClickCancel = {
                openDialogShowImage.value = false
            }, onClickAccept = {
                openDialogShowImage.value = false
            }, statusButtonAccept = false, statusButtonIcon = false, context = context
        ) {
            Image(
                bitmap = imageBitmap.value!!,
                contentDescription = "Google Maps", // decorative
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            )
        }
    }
}


class PdfItemData(val numInforme: String?,val name: String, val description: String, val date: Calendar)


private suspend fun loadImageFromUrl(context: Context, url: String): Bitmap {
    Log.e("REOS", "HistoricalDispatchTemplate-loadImageFromUrl-url: " + url)
    return try {
        withContext(Dispatchers.IO) {
            var connection = URL(url).openConnection() as? HttpURLConnection
            connection?.instanceFollowRedirects = false
            connection?.connect()
            val responseCode = connection?.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream: InputStream = connection!!.inputStream
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                return@withContext bitmap
            } else if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                val newUrl = connection?.getHeaderField("Location")
                if (!newUrl.isNullOrBlank()) {
                    // Si hay una nueva ubicación, intenta cargar la imagen desde la nueva URL
                    return@withContext loadImageFromUrl(context, newUrl)
                }
            }
            Log.e("REOS", "HistoricalDispatchTemplate-loadImageFromUrl-connection failed. Response code: $responseCode")
            return@withContext BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background) // o devolver un valor predeterminado
        }
    } catch (e: Exception) {
        // Manejar errores aquí
        e.printStackTrace()
        Log.e("REOS", "HistoricalDispatchTemplate-loadImageFromUrl-error: " + e)
        return BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background) // o devolver un valor predeterminado
    }
}

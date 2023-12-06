package com.vistony.salesforce.kotlin.View.Template

import PdfListViewModel
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import com.vistony.salesforce.kotlin.Model.ApiResponse
import com.vistony.salesforce.kotlin.Utilities.FormularioSupervisorPDF
import com.vistony.salesforce.kotlin.View.components.contexto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.Executor

@Composable
fun BusquedaFormularioSupervisorTemplate() {
    val pdfListViewModel: PdfListViewModel = viewModel()
    // Obtén las fechas de inicio y fin
    val startDate = pdfListViewModel.selectedStartDate.value.time
    val endDate = pdfListViewModel.selectedEndDate.value.time

    // Asegúrate de que endDate sea mayor o igual a startDate
    val validStartDate = if (startDate >= endDate) endDate else startDate
    val validEndDate = if (startDate >= endDate) startDate else endDate

    // Filtrar la lista de elementos entre las fechas seleccionadas
    val pdfItems = pdfListViewModel.pdfItems.value.filter { item ->
        val itemDate = item.date.time
        itemDate in validStartDate..validEndDate
    }

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
                    showDatePicker(
                        pdfListViewModel.selectedStartDate.value,
                        { pdfListViewModel.onStartDateSelected(it) },
                        { updatedText ->
                            pdfListViewModel.onStartDateButtonTextUpdated(updatedText)
                        }
                    )
                },
                label = pdfListViewModel.startDateButtonText.value,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            DatePickerButton(
                onClick = {
                    showDatePicker(
                        pdfListViewModel.selectedEndDate.value,
                        { pdfListViewModel.onEndDateSelected(it) },
                        { updatedText -> pdfListViewModel.onEndDateButtonTextUpdated(updatedText) }
                    )
                },
                label = pdfListViewModel.endDateButtonText.value,
                modifier = Modifier.weight(1f)
            )
        }
        // Lista de elementos filtrados
        LazyColumn {
            items(pdfItems) { item ->
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
fun showDatePicker(selectedDate: Calendar, onDateSelected: (Calendar) -> Unit, updateButtonText: (String) -> Unit) {
    val datePickerDialog = DatePickerDialog(
        contexto,
        { _, year, month, day ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
            }
            onDateSelected(selectedCalendar)
            // Actualiza el texto del botón con la fecha seleccionada
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedCalendar.time)
            updateButtonText(formattedDate)
        },
        selectedDate.get(Calendar.YEAR),
        selectedDate.get(Calendar.MONTH),
        selectedDate.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}
@Composable
fun PdfListItem(modifier: Modifier = Modifier, item: PdfItemData, listViewModel: PdfListViewModel, _contexto: Context) {
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
//contexto = LocaleContext.current
                val formularioSupervisorPDF = FormularioSupervisorPDF()
                val apiResponseList = listViewModel.apiResponseList.value?.data ?: emptyList()

                val matchingApiResponse = apiResponseList.firstOrNull {
                    it.datosPrincipales?.numInforme == item.numInforme.toString()
                }

                matchingApiResponse?.let {
                    formularioSupervisorPDF.generarPdfFormularioSupervisorPDF(_contexto, it,"busquedaformulario")

                } ?: Log.e("BusquedaFormularioSupervisorTemplate", "No matching API response found for ${item.numInforme}")
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

class PdfItemData(val numInforme: String?,val name: String, val description: String, val date: Calendar)

@Preview(showBackground = true)
@Composable
fun PdfListPreview() {
    BusquedaFormularioSupervisorTemplate()
}


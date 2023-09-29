package com.vistony.salesforce.kotlin.View.Template

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.kotlin.Model.*
import com.vistony.salesforce.kotlin.View.Atoms.Cell
import com.vistony.salesforce.kotlin.View.Atoms.SwitchExample
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony

@Composable
fun NotificationTemplate()
{
    val appContext = LocalContext.current
    val notificationRepository : NotificationRepository = NotificationRepository()
    val notificationViewModel: NotificationViewModel = viewModel(
        factory = NotificationViewModel.NotificationViewModelFactory(
            notificationRepository,
            appContext
        )
    )

    notificationViewModel.getNotification("20230901","20230930")
    var notificationDB=notificationViewModel.resultDB.collectAsState()
    //val notificationEntity by notificationViewModel.notificationLiveData.observeAsState(NotificationEntity())

    when (notificationDB.value.Status) {
        "Y" -> {
            CardNotification(notificationDB.value.DATA)
        }
    }
}

@Composable
fun CardNotification(
    listNotification:List<Notification>
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        // Contenido de LazyColumn
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        {
            LazyColumn(
                modifier = Modifier
                    //.weight(1f)
                    .fillMaxWidth()
            ) {
                    itemsIndexed(listNotification) { index, line ->
                        // Tu código aquí
                            Card(
                            elevation = 4.dp, modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                        {
                            Column(
                                modifier = Modifier
                                    .padding(15.dp)
                                    .fillMaxWidth()
                            ) {

                                Row(
                                    //horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.End,
                                        modifier = Modifier.weight(0.5f)
                                    ) {
                                        Cell(
                                            text = "Fecha",
                                            title = false,
                                            Color.Gray
                                        )
                                        Cell(
                                            text = line.date.toString(),
                                            title = true,
                                            textAlign = TextAlign.End
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                    }
                                    Column(
                                        horizontalAlignment = Alignment.End,
                                        modifier = Modifier.weight(0.5f)
                                    ) {
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Cell(
                                            text = "Hora",
                                            title = false,
                                            Color.Gray
                                        )
                                        Cell(
                                            text = line.time.toString(),
                                            title = true,
                                            textAlign = TextAlign.End
                                        )
                                    }
                                }
                                Divider()
                                Row(
                                    //horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.Start,
                                        modifier = Modifier.weight(0.5f)
                                    ) {
                                        Cell(
                                            text = "Mensaje",
                                            title = false,
                                            Color.Gray
                                        )
                                        Cell(
                                            text = line.message.toString(),
                                            title = true,
                                            textAlign = TextAlign.Start
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

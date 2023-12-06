package com.vistony.salesforce.kotlin.View.Template

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Controller.Utilitario.Induvis
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Model.*
import com.vistony.salesforce.kotlin.Utilities.ConvertDateSAPaUserDate
import com.vistony.salesforce.kotlin.Utilities.getDate
import com.vistony.salesforce.kotlin.View.Atoms.Cell
import com.vistony.salesforce.kotlin.View.Atoms.SwitchExample
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.components.ButtonCircle

@Composable
fun NotificationTemplate()
{

    val appContext = LocalContext.current
    val notificationRepository : NotificationRepository = NotificationRepository()
    val notificationViewModel: NotificationViewModel = viewModel(
        factory = NotificationViewModel.NotificationViewModelFactory(
            notificationRepository,
            appContext,
                SesionEntity.imei
        )
    )
    var DateApp: MutableState<String> = remember { mutableStateOf(getDate()!!) }
    var DateApp1: MutableState<String> = remember { mutableStateOf(getDate()!!) }

    Column {
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                //Consulta de fecha de cobranza
                DateQueryDeposi(DateApp)
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column(modifier = Modifier.weight(1f)) {
                //Consulta de fecha de cobranza
                DateQueryDeposi(DateApp1)
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column(modifier = Modifier.width(40.dp)) {
                ButtonCircle(
                    OnClick = {
                        //collectionHeadViewModel.getCollectionHeadForDate(DateApp.value, DateApp1.value)
                        notificationViewModel.getNotification(DateApp.value, DateApp1.value)
                    }, roundedCornerShape = RoundedCornerShape(0.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_search_white_24dp),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                        //tint = if ( stepsStatus.get(index) == "Y") BlueVistony else Color.Gray
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp)
                .background(BlueVistony)
                .height(30.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            TableCell(text = "Notificaciones", color = Color.White, title = true, weight = 1f)
        }
        //notificationViewModel.getNotification("20231101", "20231130")
        var notificationDB = notificationViewModel.resultDB.collectAsState()
        //val notificationEntity by notificationViewModel.notificationLiveData.observeAsState(NotificationEntity())

        when (notificationDB.value.Status) {
            "Y" -> {
                CardNotification(notificationDB.value.DATA)
            }
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
                                            text = ConvertDateSAPaUserDate (line.date.toString())!!,
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
                                            text =  Induvis.getTimeSSAP(BuildConfig.FLAVOR,line.time.toString()),
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

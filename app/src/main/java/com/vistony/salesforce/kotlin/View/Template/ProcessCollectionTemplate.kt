package com.vistony.salesforce.kotlin.View.Template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Model.Invoices
import com.vistony.salesforce.kotlin.Model.InvoicesViewModel
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Molecules.HorizontalStepView
import com.vistony.salesforce.kotlin.View.Molecules.HorizontalStepView2

@Composable
fun ProcessCollectionTemplate(
    invoiceViewModel: InvoicesViewModel
    ,cliente_id: String
){
    val showDialog = remember { mutableStateOf(false) }
    val selectedInvoices = remember { mutableStateOf<Invoices?>(null) }
    val typesCollectionlist = listOf( "Cobranza Ordinaria", "Deposito Directo","Pago POS", "Cobro Vendedor", "Pago Adelantado")
    val currentSelection = remember { mutableStateOf(typesCollectionlist.find { it.toUpperCase() == typesCollectionlist.toString().toUpperCase() } ?: typesCollectionlist.first()) }
    val openDialog = remember { mutableStateOf(false) }
    val invoices = selectedInvoices.value
    var progress:Float=0.6f

    Surface(
        //modifier = Modifier.fillMaxHeight(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(5.dp))
        {
            Row(
                modifier = Modifier
                    //.padding(10.dp, 5.dp, 10.dp, 0.dp)
                    .fillMaxWidth()
            ) {
                Column() {
                    Row() {
                        TableCell(
                            text = "PROCESO DE COBRANZA",
                            weight = 1f,
                            title = true,
                            textAlign = TextAlign.Center
                        )
                    }
                    val steps = listOf(
                        Pair("1", Icons.Filled.Home),
                        Pair(
                            "2",
                            ImageVector.vectorResource(R.drawable.ic_baseline_local_shipping_black_24)
                        ),
                        Pair(
                            "3",
                            ImageVector.vectorResource(R.drawable.ic_local_atm_black_24dp)
                        )
                    )

                    val stepsStatus: Array<String?> = arrayOf(
                        "Y",
                        "Y",
                        "Y"
                    )

                    /*LinearProgressIndicator(
                        progress = progress,
                        color = BlueVistony,
                        backgroundColor = Color.Gray, // el color de fondo siempre será gris
                        //progress = 0.3f,
                        modifier = Modifier
                            .fillMaxWidth()
                            //.padding(0.dp, 20.dp, 0.dp, 20.dp)
                    )*/
                    CollectionType(
                        ExpandableInvoices = { showDialog.value = true },
                        typesCollectionlist,
                        currentSelection
                    )
                    if (showDialog.value) {
                        InvoicesList(
                            invoiceViewModel, cliente_id
                        ) { invoices ->
                            selectedInvoices.value = invoices as Invoices?
                            showDialog.value = true
                        }
                    }
                    if (invoices != null) {
                        CollectionProcess(
                            invoices = invoices,
                            cliente_id,
                            currentSelection.value,
                            InfoDialog = { openDialog.value = true },
                        )
                    }
                    HorizontalStepView2(
                        statusTittleIcon = steps
                        , statusList = stepsStatus
                        , OnClick = {
                            /*result ->
                        resultValue.value=result
                        showDialog.value=true*/
                        },
                        progress,
                        false
                    )

                }
            }
        }
    }
}
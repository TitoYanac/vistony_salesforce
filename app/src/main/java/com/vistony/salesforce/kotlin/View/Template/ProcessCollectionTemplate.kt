package com.vistony.salesforce.kotlin.View.Template

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetUI
import com.vistony.salesforce.kotlin.Model.Invoices
import com.vistony.salesforce.kotlin.Model.InvoicesRepository
import com.vistony.salesforce.kotlin.Model.InvoicesViewModel
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Molecules.HorizontalStepView
import com.vistony.salesforce.kotlin.View.Molecules.HorizontalStepView2

@Composable
fun ProcessCollectionTemplate(
     detailDispatchSheet: DetailDispatchSheetUI,
){

    val appContext = LocalContext.current
    val lifecycleOwner = LocalContext.current as LifecycleOwner
    val invoicesRepository: InvoicesRepository = InvoicesRepository()
    val invoiceViewModel: InvoicesViewModel= viewModel(
        factory = InvoicesViewModel.InvoiceModelFactory(
            SesionEntity.imei,
            appContext,
            lifecycleOwner,
            invoicesRepository
        )
    )
    //invoiceViewModel.resetInvoices()
    val showDialog = remember { mutableStateOf(false) }
    val selectedInvoices = remember { mutableStateOf<Invoices?>(null) }
    val typesCollectionlist = listOf( "Cobranza Ordinaria", "Deposito Directo","Pago POS", "Cobro Vendedor", "Pago Adelantado")
    val currentSelection = remember { mutableStateOf(typesCollectionlist.find { it.toUpperCase() == typesCollectionlist.toString().toUpperCase() } ?: typesCollectionlist.first()) }
    val openDialog = remember { mutableStateOf(false) }
    val invoices = selectedInvoices.value
    var progress:Float=0.6f
    var expandedCollectionType: MutableState<Boolean> = remember { mutableStateOf(true) }
    var expandedInvoiceList:MutableState<Boolean> = remember { mutableStateOf(true) }
    var expandedProcessCollection:MutableState<Boolean> =  remember{ mutableStateOf(true) }

    Log.e("REOS", "ProcessCollectionTemplate-ProcessCollectionTemplate-selectedInvoices.value: " +selectedInvoices.value)
    if (selectedInvoices.value!=null)
    {
        if(!selectedInvoices.value!!.cliente_id.equals(detailDispatchSheet.cliente_id))
        {
            expandedCollectionType.value=true
            expandedInvoiceList.value=false
            expandedProcessCollection.value=false
        }
    }

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
                    CollectionType(
                        appContext,
                        ExpandableInvoices = { showDialog.value = true },
                        typesCollectionlist,
                        currentSelection,
                        expandedCollectionType,
                        expandedInvoiceList,
                        detailDispatchSheet
                    )
                    if (showDialog.value) {
                        InvoicesList(
                            expandedProcessCollection,
                            expandedCollectionType,
                            expandedInvoiceList,
                            invoiceViewModel,
                            detailDispatchSheet.cliente_id
                        ) { invoices ->
                            selectedInvoices.value = invoices as Invoices?
                            showDialog.value = true
                        }
                    }
                    if (invoices != null) {
                        CollectionProcess(
                            invoiceViewModel,
                            expandedInvoiceList,
                            expandedProcessCollection,
                            invoices = invoices,
                            detailDispatchSheet.cliente_id,
                            currentSelection.value,
                            InfoDialog = { openDialog.value = true },
                        )
                    }
                    /*HorizontalStepView2(
                        statusTittleIcon = steps
                        , statusList = stepsStatus
                        , OnClick = {
                            /*result ->
                        resultValue.value=result
                        showDialog.value=true*/
                        },
                        progress,
                        false
                    )*/

                }
            }
        }
    }
}
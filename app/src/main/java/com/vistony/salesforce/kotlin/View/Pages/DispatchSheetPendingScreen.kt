package com.vistony.salesforce.kotlin.View.Pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.Fragment
import com.vistony.salesforce.kotlin.View.Atoms.theme.VistonyTheme
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.View.ContainerDispatchView
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheet
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetEntity
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetRepository
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetViewModel
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.components.DispatchSheetTemplate

class DispatchSheetPendingScreen : Fragment()
{
    private lateinit var parametroFecha: String
    companion object {
        const val ARG_PARAM_FECHA = "parametrofecha"
        fun newInstance(parametroFecha: String): DispatchSheetPendingScreen {
            val fragment = DispatchSheetPendingScreen()
            val args = Bundle()
            args.putString(ARG_PARAM_FECHA, parametroFecha)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            parametroFecha = it.getString(ARG_PARAM_FECHA, "")
        }
        return ComposeView(requireContext()).apply {
            setContent {
                //Text(text = "Hello world.")
                VistonyTheme() {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        //color = MaterialTheme.colors.background
                    ) {

//                        Log.e("REOS", "DispatchSheetPendingScreen-onCreateView.parametroFecha: "+parametroFecha)
                        val appContext = LocalContext.current
                        val lifecycleOwner = LocalContext.current as LifecycleOwner
                        val detailDispatchSheetRepository = DetailDispatchSheetRepository(appContext)
                        val detailDispatchSheetViewModel: DetailDispatchSheetViewModel = viewModel(
                            factory = DetailDispatchSheetViewModel.DetailDispatchSheetViewModelFactory(
                                detailDispatchSheetRepository,
                                appContext,
                                SesionEntity.imei,
                            )
                        )
                        detailDispatchSheetViewModel?.getStateDetailDispatchSheet(
                            //parametroFecha,
                            ContainerDispatchView.parametrofecha,
                            "P"
                        )
                        var isLoading:MutableState<Boolean> = remember { mutableStateOf(true) }

                        var detailDispatchSheetDB=detailDispatchSheetViewModel.result.collectAsState()
                        //val notificationEntity by notificationViewModel.notificationLiveData.observeAsState(NotificationEntity())
                        /*var detailDispatchSheetDB= MutableLiveData<DetailDispatchSheetEntity>()

                        detailDispatchSheetViewModel.resultDB.observeForever { newResultGet ->
                            // Actualizar el valor de _invoices cuando haya cambios
                            detailDispatchSheetDB.value = newResultGet
                            Log.e("REOS", "NotificationViewModel-getNotification-_resultDB.value: " + detailDispatchSheetDB.value)
                        }*/

                        //MyApp(isLoading)
                        when (detailDispatchSheetDB.value?.Status) {
                            "Y" -> {
                                //CardNotification(detailDispatchSheetDB.value.DATA)
                                DispatchSheetTemplate(detailDispatchSheetDB.value!!.UI,appContext,lifecycleOwner)
                                //isLoading.value = false
                            }
                        }
                        //DispatchSheetTemplate(detailDispatchSheetList,appContext,lifecycleOwner)
                    }
                }
            }
        }
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(tag: String?, dato: Any?)
    }

    var mListener: OnFragmentInteractionListener? = null
}


@Composable
fun MyApp(isLoading: MutableState<Boolean>) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón para mostrar o ocultar el diálogo de carga
        /*Button(onClick = { isLoading = !isLoading }) {
            Text(text = if (isLoading) "Ocultar Carga" else "Mostrar Carga")
        }*/

        // Diálogo de carga
        if (isLoading.value) {
            Dialog(
                onDismissRequest = { isLoading.value = false },
                properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Contenido personalizado aquí
                    CircularProgressIndicator(color = BlueVistony)
                }
            }
        }
    }
}
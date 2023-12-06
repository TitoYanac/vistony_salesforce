package com.vistony.salesforce.kotlin.View.Pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.View.ContainerDispatchView
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheet
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetEntity
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetRepository
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetViewModel
import com.vistony.salesforce.kotlin.View.Atoms.theme.VistonyTheme
import com.vistony.salesforce.kotlin.View.components.DispatchSheetTemplate

class DispatchSheetSucessfulScreen : Fragment()
{
    private lateinit var parametroFecha: String
    companion object {
        const val ARG_PARAM_FECHA = "parametrofecha"
        fun newInstance(parametroFecha: String): DispatchSheetSucessfulScreen {
            val fragment = DispatchSheetSucessfulScreen()
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
//                        Log.e("REOS", "DispatchSheetSucessfulScreen-onCreateView.parametroFecha: "+parametroFecha)
                        val appContext = LocalContext.current
                        val lifecycleOwner = LocalContext.current as LifecycleOwner
                        val detailDispatchSheetRepository = DetailDispatchSheetRepository(appContext)
                        /*var detailDispatchSheetList by remember { mutableStateOf(emptyList<DetailDispatchSheet>()) }
                        detailDispatchSheetViewModel = DetailDispatchSheetViewModel(detailDispatchSheetRepository)
                        detailDispatchSheetViewModel?.getStateDetailDispatchSheet(
                            SesionEntity.imei,
                            ContainerDispatchView.parametrofecha,
                            appContext,
                            lifecycleOwner,
                            "E"
                        )
                        detailDispatchSheetViewModel.status.observe(lifecycleOwner) { data ->
                            // actualizar la UI con los datos obtenidos
                            Log.e(
                                "REOS",
                                "DispatchSheetPendingScreen-onCreateView.result.observe.data.size"+data.size
                            )
                            //Conversation1(data)
                            detailDispatchSheetList= data
                        }*/
                        val detailDispatchSheetViewModel: DetailDispatchSheetViewModel = viewModel(
                            factory = DetailDispatchSheetViewModel.DetailDispatchSheetViewModelFactory(
                                detailDispatchSheetRepository,
                                appContext,
                                SesionEntity.imei,
                            )
                        )
                        detailDispatchSheetViewModel?.getStateDetailDispatchSheet(
                            //ContainerDispatchView.parametrofecha,
                            //parametroFecha,
                            //"20231019",
                            ContainerDispatchView.parametrofecha,
                            "E"
                        )

                        var detailDispatchSheetDB=detailDispatchSheetViewModel.result.collectAsState()
                        //val notificationEntity by notificationViewModel.notificationLiveData.observeAsState(NotificationEntity())
                        /*var detailDispatchSheetDB= MutableLiveData<DetailDispatchSheetEntity>()

                        detailDispatchSheetViewModel.resultDB.observeForever { newResultGet ->
                            // Actualizar el valor de _invoices cuando haya cambios
                            detailDispatchSheetDB.value = newResultGet
                            Log.e("REOS", "NotificationViewModel-getNotification-_resultDB.value: " + detailDispatchSheetDB.value)
                        }*/

                        when (detailDispatchSheetDB.value?.Status) {
                            "Y" -> {
                                //CardNotification(detailDispatchSheetDB.value.DATA)
                                DispatchSheetTemplate(detailDispatchSheetDB.value!!.UI,appContext,lifecycleOwner)
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

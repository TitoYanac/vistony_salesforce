package com.vistony.salesforce.kotlin.View.Pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import com.vistony.salesforce.kotlin.View.Atoms.theme.VistonyTheme
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.View.ContainerDispatchView
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheet
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetEntity
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetRepository
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetViewModel
import com.vistony.salesforce.kotlin.Model.NotificationViewModel
import com.vistony.salesforce.kotlin.View.Template.CardNotification
import com.vistony.salesforce.kotlin.View.components.DispatchSheetTemplate

class DispatchSheetFailedScreen : Fragment()
{
    private lateinit var parametroFecha: String
    companion object {
        const val ARG_PARAM_FECHA = "parametrofecha"
        fun newInstance(parametroFecha: String): DispatchSheetFailedScreen {
            val fragment = DispatchSheetFailedScreen()
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
        return ComposeView(requireContext()).apply {
            setContent {
                //Text(text = "Hello world.")
                VistonyTheme() {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        //color = MaterialTheme.colors.background
                    ) {
//                        Log.e("REOS", "DispatchSheetFailedScreen-onCreateView.parametroFecha: "+parametroFecha)
                        val appContext = LocalContext.current
                        val lifecycleOwner = LocalContext.current as LifecycleOwner
                        val detailDispatchSheetRepository = DetailDispatchSheetRepository(appContext)
                        val detailDispatchSheetViewModel: DetailDispatchSheetViewModel = viewModel(
                            factory = DetailDispatchSheetViewModel.DetailDispatchSheetViewModelFactory(
                                detailDispatchSheetRepository,
                                appContext,
                                SesionEntity.imei
                            )
                        )
                        detailDispatchSheetViewModel?.getStateDetailDispatchSheet(
                            ContainerDispatchView.parametrofecha,
                            "F"
                        )
                        //var detailDispatchSheetDB=MutableLiveData<DetailDispatchSheetEntity>()

                        /*detailDispatchSheetViewModel.resultDB.observeForever { newResultGet ->
                            // Actualizar el valor de _invoices cuando haya cambios
                            detailDispatchSheetDB.value = newResultGet
                            Log.e("REOS", "DispatchSheetFailedScreen-onCreateView-newResultGet: " + newResultGet)
                        }*/

                        var detailDispatchSheetDB=detailDispatchSheetViewModel.result.collectAsState()
                        when (detailDispatchSheetDB.value?.Status) {
                            "Y" -> {
                                DispatchSheetTemplate(detailDispatchSheetDB.value!!.UI,appContext,lifecycleOwner)
                            }
                        }
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



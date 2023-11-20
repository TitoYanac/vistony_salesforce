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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.View.ContainerDispatchView
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheet
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetRepository
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetViewModel
import com.vistony.salesforce.kotlin.Model.NotificationViewModel
import com.vistony.salesforce.kotlin.View.Template.CardNotification
import com.vistony.salesforce.kotlin.View.components.DispatchSheetTemplate

class DispatchSheetFailedScreen : Fragment()
{
    private lateinit var detailDispatchSheetViewModel: DetailDispatchSheetViewModel

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
                        val appContext = LocalContext.current
                        val lifecycleOwner = LocalContext.current as LifecycleOwner
                        val detailDispatchSheetRepository = DetailDispatchSheetRepository()
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

                        var detailDispatchSheetDB=detailDispatchSheetViewModel.resultDB.collectAsState()
                        when (detailDispatchSheetDB.value.Status) {
                            "Y" -> {
                                DispatchSheetTemplate(detailDispatchSheetDB.value.UI,appContext,lifecycleOwner)
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



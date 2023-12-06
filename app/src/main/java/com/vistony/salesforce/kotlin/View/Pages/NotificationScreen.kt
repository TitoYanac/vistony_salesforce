package com.vistony.salesforce.kotlin.View.Pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.View.ContainerDispatchView
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheet
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetRepository
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetViewModel
import com.vistony.salesforce.kotlin.View.Atoms.theme.VistonyTheme
import com.vistony.salesforce.kotlin.View.Template.NotificationTemplate
import com.vistony.salesforce.kotlin.View.components.DispatchSheetTemplate

class NotificationScreen: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                VistonyTheme() {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        //color = MaterialTheme.colors.background
                    ) {
                        requireActivity().title = "Notificaciones Cotización"
                        NotificationTemplate()
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
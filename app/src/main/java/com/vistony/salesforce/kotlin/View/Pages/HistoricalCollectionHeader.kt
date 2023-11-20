package com.vistony.salesforce.kotlin.View.Pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.vistony.salesforce.kotlin.View.Atoms.theme.VistonyTheme
import com.vistony.salesforce.kotlin.View.Template.HistoricalCollectionDetailTemplate
import com.vistony.salesforce.kotlin.View.Template.HistoricalCollectionHeaderTemplate

class HistoricalCollectionHeader: Fragment()  {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                requireActivity().title = "Consulta Deposito"
                VistonyTheme() {
                    // A surface container using the 'background' color from the theme
                    Surface(
                            modifier = Modifier.fillMaxSize(),
                            //color = MaterialTheme.colors.background
                    ) {
                        HistoricalCollectionHeaderTemplate()
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

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Preview(showBackground = true)
    @Composable
    fun HistoricalCollectionHeaderPreview(){
        VistonyTheme() {
            // A surface container using the 'background' color from the theme
            Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colors.background
            ) {
                HistoricalCollectionDetailTemplate()
            }
        }
    }
}
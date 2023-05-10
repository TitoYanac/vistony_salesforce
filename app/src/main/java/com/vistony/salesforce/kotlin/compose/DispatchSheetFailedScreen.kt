package com.vistony.salesforce.kotlin.compose

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.vistony.salesforce.kotlin.compose.theme.VistonySalesForce_PedidosTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.vistony.salesforce.R
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import com.bxl.mupdf.Stepper
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.View.ContainerDispatchView
import com.vistony.salesforce.kotlin.compose.components.ScreenDispatch
import com.vistony.salesforce.kotlin.data.DetailDispatchSheet
import com.vistony.salesforce.kotlin.data.DetailDispatchSheetRepository
import com.vistony.salesforce.kotlin.data.DetailDispatchSheetViewModel

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
                VistonySalesForce_PedidosTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        //color = MaterialTheme.colors.background
                    ) {
                        val appContext = LocalContext.current
                        val lifecycleOwner = LocalContext.current as LifecycleOwner
                        val detailDispatchSheetRepository = DetailDispatchSheetRepository()
                        var detailDispatchSheetList by remember { mutableStateOf(emptyList<DetailDispatchSheet>()) }
                        detailDispatchSheetViewModel = DetailDispatchSheetViewModel(detailDispatchSheetRepository)
                        detailDispatchSheetViewModel?.getStateDetailDispatchSheet(
                            SesionEntity.imei,
                            ContainerDispatchView.parametrofecha,
                            appContext,
                            lifecycleOwner,
                            "F"
                        )
                        detailDispatchSheetViewModel.status.observe(lifecycleOwner) { data ->
                            // actualizar la UI con los datos obtenidos
                            Log.e(
                                "REOS",
                                "DispatchSheetPendingScreen-onCreateView.result.observe.data.size"+data.size
                            )
                            //Conversation1(data)
                            detailDispatchSheetList= data

                        }
                        ScreenDispatch(detailDispatchSheetList,appContext,lifecycleOwner)
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



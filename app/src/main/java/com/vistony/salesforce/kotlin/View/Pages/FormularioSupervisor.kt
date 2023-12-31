package com.vistony.salesforce.kotlin.View.Pages

import FormularioSupervisorTemplate
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.kotlin.Model.FormularioSupervisorRepository
import com.vistony.salesforce.kotlin.Model.FormularioSupervisorViewModel
import com.vistony.salesforce.kotlin.View.Atoms.theme.VistonyTheme
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.ModelFormularioSupervisorTemplate.CameraImageViewModel
import kotlinx.coroutines.launch


class FormularioSupervisor : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            requireActivity().title = "Formulario de Supervisión"
            lifecycleScope.launch {
                try {

                    setContent {
                        VistonyTheme {

                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colors.background
                            ) {


                                val cameraImageViewModel: CameraImageViewModel = viewModel()
                                val formularioSupervisorRepository = FormularioSupervisorRepository(context = LocalContext.current)
                                val formularioSupervisorViewModel: FormularioSupervisorViewModel = viewModel(
                                    factory = FormularioSupervisorViewModel.FormularioSupervisorViewModelFactory(formularioSupervisorRepository = formularioSupervisorRepository)
                                )

                                FormularioSupervisorTemplate(cameraImageViewModel,formularioSupervisorViewModel)

                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    setContent {
                        VistonyTheme {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                            ) {
                                ErrorScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}







@Composable
fun ErrorScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(72.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Oh no! Algo salió mal.",
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Por favor intenta después.",
                style = MaterialTheme.typography.body2
            )
        }
    }
}
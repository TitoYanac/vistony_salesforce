
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.kotlin.Model.FormularioTestRepository
import com.vistony.salesforce.kotlin.Model.FormularioTestViewModel
import com.vistony.salesforce.kotlin.Utilities.FormularioTest1PDF
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.Pages.ApiResponse
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Atoms.TitleSection
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Organisms.SectionDatosPrincipales
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Organisms.SectionDatosVisita
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Organisms.SectionFormulario
import com.vistony.salesforce.kotlin.View.components.ButtonView
import com.vistony.salesforce.kotlin.View.components.contexto

@Composable
fun FormularioTest1Template(initialApiResponse: ApiResponse) {
    var apiResponse:MutableState<ApiResponse> = remember { mutableStateOf(initialApiResponse) }

    Log.e("REOS", "FormularioTest1Template-FormularioTest1Template-apiResponse" + apiResponse)
    val updateApiResponse: (ApiResponse) -> Unit = {newValor ->
        Log.e("callback new valor", "newValor: " + newValor)
        apiResponse.value = newValor
    }

    val appContext = LocalContext.current
    val formularioTestRepository : FormularioTestRepository = FormularioTestRepository()
    val formularioTestViewModel: FormularioTestViewModel = viewModel(
            factory = FormularioTestViewModel.FormularioTestViewModelFactory(
                    formularioTestRepository,
                    appContext,
            )
    )
    var responseFormSupervisor=formularioTestViewModel.resultDB.collectAsState()
    when (responseFormSupervisor.value.StatusCode)
    {
        "Y" -> {
            formularioTestViewModel.sendFormularioTest()
        }
    }
    LazyColumn(
        modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
    ) {

        item { TitleSection(label = "Datos Principales") }
        item { SectionDatosPrincipales(apiResponse, updateApiResponse) }

        item { TitleSection(label = "Datos de Visita") }
        item { SectionDatosVisita(apiResponse, updateApiResponse) }

        item { TitleSection(label = "Formulario") }
        item {
            SectionFormulario(apiResponse)
            {
                Log.e("callback it", "it: $it")

                it.formulario?.forEachIndexed { index, preguntaRespuesta ->
                    Log.e(
                        "callback it",
                        "pregunta $index : " + preguntaRespuesta.pregunta
                    )
                    Log.e(
                        "callback it",
                        "respuesta $index : " + preguntaRespuesta.respuesta
                    )
                }
                updateApiResponse(it)
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Row() {

                ButtonView(
                        description = "Guardar",
                        OnClick = { formularioTestViewModel.addFormularioTest(apiResponse.value) },
                        context = contexto,
                        backGroundColor = BlueVistony,
                        textColor = Color.White
                )
                Spacer(modifier = Modifier.width(20.dp))
                ButtonView(
                        description = "Generar PDF",
                        OnClick = {
                            val formularioTest1PDF:FormularioTest1PDF= FormularioTest1PDF()
                            formularioTest1PDF.generarPdfFormularioTest1PDF(contexto,apiResponse.value)
                        },
                        context = contexto,
                        backGroundColor = BlueVistony,
                        textColor = Color.White,
                        status = true
                )
            }
           /* Button(
                onClick = {
                    Log.e("REOS", "FormularioTest1Template-FormularioTest1Template-apiResponse.value.datosVisita?.clientesEmpadronados: "+ apiResponse.value.datosVisita?.clientesEmpadronados)
                    Log.e("REOS", "FormularioTest1Template-FormularioTest1Template-apiResponse.value.datosVisita?.clientesNuevos: "+ apiResponse.value.datosVisita?.clientesNuevos)
                    Log.e("REOS", "FormularioTest1Template-FormularioTest1Template-apiResponse.value.datosVisita?.horaFin: "+ apiResponse.value.datosVisita?.horaFin)
                    Log.e("REOS", "FormularioTest1Template-FormularioTest1Template-apiResponse.value.datosVisita?.horaInicio: "+ apiResponse.value.datosVisita?.horaInicio)
                    apiResponse.value.formulario?.forEachIndexed { index, preguntaRespuesta ->
                        Log.e(
                            "REOS",
                            "pregunta $index : " + preguntaRespuesta.pregunta
                        )
                        Log.e(
                            "REOS",
                            "respuesta $index : " + preguntaRespuesta.respuesta
                        )
                    }

                    formularioTestViewModel.addFormularioTest(apiResponse.value)
                          },
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(color = Color.Blue)
            ) {
                Text(text = "Mostrar ApiResponse", color = Color.White)
            }*/
        }
    }
}















import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.kotlin.Model.ApiResponse
import com.vistony.salesforce.kotlin.Model.FormularioTestRepository
import com.vistony.salesforce.kotlin.Model.FormularioTestViewModel
import com.vistony.salesforce.kotlin.Utilities.FormularioSupervisorPDF
import com.vistony.salesforce.kotlin.View.Pages.getDateCurrentFormulario
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.ModelFormularioSupervisorTemplate.CameraImageViewModel
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Organisms.SectionDatosPrincipales
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Organisms.SectionDatosVisita
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Organisms.SectionFormulario
import com.vistony.salesforce.kotlin.View.components.ButtonView
import com.vistony.salesforce.kotlin.View.components.contexto
import java.io.IOException

@Composable
fun FormularioSupervisorTemplate(
    navController: NavController,
    cameraImageViewModel: CameraImageViewModel,
) {
    Log.e("MainFormularioSupervision", "Entro 2")
    val appContext = LocalContext.current
    val formularioTestRepository = FormularioTestRepository(context = appContext)
    val formularioTestViewModel: FormularioTestViewModel = viewModel(
        factory = FormularioTestViewModel.FormularioTestViewModelFactory(formularioTestRepository, appContext)
    )

    val apiResponseModel=formularioTestViewModel.resultAPI.collectAsState().value

    val isButtonEnabled by formularioTestViewModel.isButtonEnabled.collectAsState()
    val isButtonEnabled2 by formularioTestViewModel.isButtonEnabled2.collectAsState()

    val section1Expanded by formularioTestViewModel.isSection1Expanded.collectAsState()
    val section2Expanded by formularioTestViewModel.isSection2Expanded.collectAsState()
    val section3Expanded by formularioTestViewModel.isSection3Expanded.collectAsState()

    val bgColor = if (isButtonEnabled) Color(0xFF0957C3) else Color.Gray
    val bgColor2 = if (isButtonEnabled2) Color(0xFF0957C3) else Color.Gray



    val shouldShowPopup by formularioTestViewModel.shouldShowPopup.collectAsState()
    val popupMessage by formularioTestViewModel.popupMessage.collectAsState()

    val responseFormSupervisor=formularioTestViewModel.resultDB.collectAsState().value
    when (responseFormSupervisor.StatusCode)
    {
        "Y" -> {
            formularioTestViewModel.sendFormularioTest()
            formularioTestViewModel.updatePopupMessage(if (responseFormSupervisor.StatusCode == "Y") "Operación realizada con éxito" else "Error en la operación")
            formularioTestViewModel.updateShouldShowPopup(true)
            formularioTestViewModel.updateButtonEnabled(false)
            formularioTestViewModel.updateButtonEnabled2(true)
            responseFormSupervisor.StatusCode = "N"
        }
    }

    val imei = SesionEntity.imei
    val date = getDateCurrentFormulario()
    date.let {formularioTestViewModel.getFormularioTest(imei, it)}

    Log.e("gettingForm", "apiresponsemodel_asd: ${apiResponseModel}")


    when (apiResponseModel.StatusCode) {

        "Y" -> {

            if(apiResponseModel.Data != null){
                var apiResponse:MutableState<ApiResponse?> = remember { mutableStateOf(apiResponseModel.Data) }


                Log.e("gettingForm", "apiresponsemodeldata_12345: ${apiResponseModel.Data}")
                val updateApiResponse: (ApiResponse) -> Unit = {newValor -> apiResponse.value = newValor}

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .padding(bottom = 40.dp)
                        .background(color = Color.White)
                ) {
                    itemsIndexed(listOf(0, 1, 2, 3, 4, 5)) { index, _ ->
                        when (index) {
                            0 -> SeccionDesplegable(
                                titulo = "Datos Principales",
                                expanded = section1Expanded,
                                onExpandChanged = { expanded -> formularioTestViewModel.updateSection1Expanded(expanded) }
                            ) {
                                SectionDatosPrincipales(apiResponse, updateApiResponse)
                            }
                            1 -> SeccionDesplegable(
                                titulo = "Datos de Visita",
                                expanded = section2Expanded,
                                onExpandChanged = { expanded -> formularioTestViewModel.updateSection2Expanded(expanded) }
                            ) {
                                SectionDatosVisita(apiResponse, updateApiResponse)
                            }
                            2 -> SeccionDesplegable(
                                titulo = "Formulario",
                                expanded = section3Expanded,
                                onExpandChanged = { expanded -> formularioTestViewModel.updateSection3Expanded(expanded) }
                            ) {
                                SectionFormulario(apiResponse, updateApiResponse )
                            }
                            // agrega un boton para abrir un popup que me de 2 opciones una de elegir imagenes de la galeria y la otra para tomar una foto con la camara y usar la implementacion que tengo
                            3 -> {
                                Spacer(modifier = Modifier.height(16.dp))
                                val context = LocalContext.current

                                val launcher = rememberLauncherForActivityResult(
                                    contract = ActivityResultContracts.GetMultipleContents()
                                ) { uris: List<Uri>? ->
                                    if (uris != null) {
                                        val imageBitmaps: MutableList<Bitmap> = mutableListOf()

                                        uris.forEach { uri ->
                                            try {
                                                val bitmap: Bitmap =
                                                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                                                imageBitmaps.add(bitmap)
                                            } catch (e: IOException) {
                                                e.printStackTrace()
                                                // Manejo de errores si es necesario
                                            }
                                        }

                                        if (imageBitmaps.isNotEmpty()) {
                                            // Procesar las imágenes aquí, utilizando cameraImageViewModel
                                            imageBitmaps.forEachIndexed { index, imageBitmap ->
                                                cameraImageViewModel.agregarImagen(imageBitmap, uris[index])
                                            }
                                        } else {
                                            // Manejo si no se seleccionaron imágenes
                                            Toast.makeText(context, "No se seleccionaron imágenes", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    }
                                }

                                Row {
                                    ButtonView(
                                        description = "Elegir imágenes de la galería",
                                        OnClick = { launcher.launch("image/*") },
                                        context = context,
                                        backGroundColor = Color(0xFF0957C3),
                                        textColor = Color.White,
                                        status = true
                                    )
                                }
                            }
                            4 -> {
                                if (cameraImageViewModel.imagenesBitmap.isNotEmpty()) {
                                    ImageGallery(cameraImageViewModel = cameraImageViewModel)
                                }
                            }
                            5 -> {
                                Spacer(modifier = Modifier.height(16.dp))
                                Row {
                                    ButtonView(
                                        description = "Guardar",
                                        OnClick = {
                                            // Deshabilita el botón
                                            apiResponse.value?.galeria = cameraImageViewModel.imagenesGaleria
                                            formularioTestViewModel.updateButtonEnabled(false)

                                            apiResponse.value?.let {
                                                /*Log.e("gettingForm4", "apiresponsemodel_asd: ${it.galeria?.size}")
                                                Log.e("gettingForm4", "apiresponsemodel_asd: ${apiResponse.value?.galeria?.size}")*/
                                                formularioTestViewModel.addFormularioTest(it)
                                            }
                                        },
                                        context = contexto,
                                        backGroundColor = bgColor,
                                        textColor = Color.White,
                                        status = isButtonEnabled
                                    )
                                    if (shouldShowPopup) {
                                        AlertDialog(
                                            onDismissRequest = { formularioTestViewModel.updateShouldShowPopup(false) },
                                            title = { Text(text = popupMessage) },
                                            confirmButton = {
                                                Button(onClick = { formularioTestViewModel.updateShouldShowPopup(false) }, Modifier.background(Color(0xFF0957C3))) {
                                                    Text(text = "OK", modifier = Modifier.background(Color.Transparent), color = Color.White)
                                                }
                                            }
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(20.dp))
                                    ButtonView(
                                        description = "Generar PDF",
                                        OnClick = {
                                            FormularioSupervisorPDF().generarPdfFormularioSupervisorPDF(
                                                context = contexto,
                                                apiResponse = apiResponse.value!!,
                                                typePdf = "formularioprincipal"
                                            )
                                        },
                                        context = contexto,
                                        backGroundColor = bgColor2,
                                        textColor = Color.White,
                                        status = (isButtonEnabled2)
                                    )
                                }
                            }
                        }
                    }
                }

            }
            else{
                    Text(text = "No hay datos")

            }

        }
    }



}

@Composable
fun SeccionDesplegable(
    titulo: String,
    expanded: Boolean,
    onExpandChanged: (Boolean) -> Unit,
    contenido: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onExpandChanged(!expanded) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (expanded) Color(0xFF0957C3) else Color(0xFFD9D9D9))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = titulo, fontWeight = FontWeight.Bold, color = if(expanded) Color.White else Color.Black)
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if(expanded) Color.White else Color.Black

            )
        }
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Box(modifier = Modifier.clickable(){

            }){
                contenido()
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageGallery(cameraImageViewModel: CameraImageViewModel) {
    val imagenes : List<Bitmap> = cameraImageViewModel.imagenesBitmap
    val showImageDialog = remember { mutableStateOf(false) }
    val selectedImageIndex = remember { mutableStateOf(0) }
    val swipeableState = rememberSwipeableState(0)
    val size = LocalContext.current.resources.displayMetrics.widthPixels
    val anchors = mapOf(0f to 0, size.toFloat() to 1, -size.toFloat() to -1)

    if (showImageDialog.value) {
        Dialog(onDismissRequest = { showImageDialog.value = false }) {
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        orientation = Orientation.Horizontal
                    )
            ) {
                Image(
                    bitmap = imagenes[selectedImageIndex.value].asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                IconButton(
                    onClick = { showImageDialog.value = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = Color.White
                    )
                }

                LaunchedEffect(swipeableState.offset.value) {
                    if (swipeableState.offset.value == size.toFloat()) {
                        selectedImageIndex.value = (selectedImageIndex.value - 1).coerceAtLeast(0)
                        swipeableState.animateTo(0, anim = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))
                    } else if (swipeableState.offset.value == -size.toFloat()) {
                        selectedImageIndex.value = (selectedImageIndex.value + 1).coerceAtMost(imagenes.size - 1)
                        swipeableState.animateTo(0, anim = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))
                    }
                }


            }
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(imagenes.size) { index ->
            Image(
                bitmap = imagenes[index].asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(4.dp)) // Esto asegura que la imagen tenga bordes redondeados
                    .border(2.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                    .background(Color.Black)
                    .clickable {
                        selectedImageIndex.value = index
                        showImageDialog.value = true
                    }
            )
        }
    }
}


@Composable
fun ImagePicker(onImagePicked: (ImageBitmap) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            val imageBitmap = bitmap.asImageBitmap()
            onImagePicked(imageBitmap)
        }
    }

    Row {
        ButtonView(
            description = "Elegir imágen de la galería",
            OnClick = {launcher.launch("image/*") },
            context = contexto,
            backGroundColor = Color(0xFF0957C3),
            textColor = Color.White,
            status = true)
    }
}

@Composable
fun MultipleImagePicker(onImagesPicked: (List<Bitmap>, List<Uri>) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        val imageBitmaps : MutableList<Bitmap> = mutableListOf()
        val failedUris: MutableList<Uri> = mutableListOf()

        uris.forEach { uri ->
            try {
                val bitmap : Bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                imageBitmaps.add(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
                failedUris.add(uri)
            }
        }

        if (imageBitmaps.isNotEmpty()) {
            onImagesPicked(imageBitmaps, uris)
        }

        if (failedUris.isNotEmpty()) {
            // Aquí puedes mostrar un mensaje al usuario indicando que algunas imágenes no pudieron cargarse
            Toast.makeText(context, "Algunas imágenes no pudieron cargarse", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row {
            ButtonView(
                description = "Elegir imágenes de la galería",
                OnClick = { launcher.launch("image/*") },
                context = context,
                backGroundColor = Color(0xFF0957C3),
                textColor = Color.White,
                status = true
            )
        }
    }
}



import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vistony.salesforce.kotlin.Model.ApiResponse
import com.vistony.salesforce.kotlin.Model.DatosPrincipales
import com.vistony.salesforce.kotlin.Model.FormularioSupervisorViewModel
import com.vistony.salesforce.kotlin.Utilities.FormularioSupervisorPDF
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.ModelFormularioSupervisorTemplate.CameraImageViewModel
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Molecules.TileExpansion
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Organisms.TileExpansionBodyFormulario
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Organisms.TileExpansionBodyPrincipal
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Organisms.TileExpansionBodyVisita
import com.vistony.salesforce.kotlin.View.components.ButtonView
import com.vistony.salesforce.kotlin.View.components.contexto
import java.io.IOException

@Composable
fun FormularioSupervisorTemplate(
    cameraImageViewModel: CameraImageViewModel,
    formularioSupervisorViewModel: FormularioSupervisorViewModel
) {

    val resultAPI=formularioSupervisorViewModel.resultAPI.collectAsState().value
    val isButtonEnabled by formularioSupervisorViewModel.isButtonEnabled.collectAsState()
    val isButtonEnabled2 by formularioSupervisorViewModel.isButtonEnabled2.collectAsState()
    val bgColor = if (isButtonEnabled) Color(0xFF0957C3) else Color.Gray
    val bgColor2 = if (isButtonEnabled2) Color(0xFF0957C3) else Color.Gray
    val shouldShowPopup by formularioSupervisorViewModel.shouldShowPopup.collectAsState()
    val popupMessage by formularioSupervisorViewModel.popupMessage.collectAsState()
    val responseFormSupervisor=formularioSupervisorViewModel.resultDB.collectAsState().value



    when (responseFormSupervisor.StatusCode)
    {
        "N" -> {
            Log.e("jesusdebug", "apiresponse N: ${resultAPI.Data}")
        }
        "Y" -> {
            Log.e("jesusdebug", "apiresponse Y: ${resultAPI.Data}")

            formularioSupervisorViewModel.enviar_a_API_FormularioSupervisor()
            formularioSupervisorViewModel.updatePopupMessage(if (responseFormSupervisor.StatusCode == "Y") "Operación realizada con éxito" else "Error en la operación")
            formularioSupervisorViewModel.updateShouldShowPopup(true)
            formularioSupervisorViewModel.updateButtonEnabled(false)
            formularioSupervisorViewModel.updateButtonEnabled2(true)
            responseFormSupervisor.StatusCode = "N"
        }
    }
    if(resultAPI.Data != null){
        val apiResponse:MutableState<ApiResponse?> = remember { mutableStateOf(resultAPI.Data) }
        Log.e("jesusdebug", "resultAPIdata_12345: ${resultAPI.Data}")


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(bottom = 40.dp)
                .background(color = Color.White)
        ) {
            itemsIndexed(listOf(0, 1, 2, 3, 4, 5)) { index, _ ->
                when (index) {
                    0 -> SectionDatosPrincipales(formularioSupervisorViewModel= formularioSupervisorViewModel)

                    1 -> SectionDatosVisita(formularioSupervisorViewModel = formularioSupervisorViewModel)
                    2 -> SectionDatosFormulario(formularioSupervisorViewModel = formularioSupervisorViewModel)

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
                            val showDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
                            // Muestra el diálogo
                            MissingFieldsDialog(
                                showDialog = showDialog.value,
                                onDismiss = { showDialog.value = false }
                            )
                            ButtonView(
                                description = "Guardar",
                                OnClick = {
                                    // Deshabilita el botón
                                    val datosPrincipales: DatosPrincipales = formularioSupervisorViewModel.resultAPI.value.Data!!.datosPrincipales!!
                                    var hasTypeOut = false
                                    for (tipo in datosPrincipales.tipoSalida!!) {
                                        if(tipo.marcado!!){
                                            hasTypeOut = true
                                        }
                                    }
                                    if(cameraImageViewModel.imagenesBitmap.isNotEmpty() && hasTypeOut){
                                        formularioSupervisorViewModel.updateGaleria(cameraImageViewModel.imagenesGaleria)
                                        formularioSupervisorViewModel.updateButtonEnabled(false)
                                        formularioSupervisorViewModel.enviar_a_ROOM_FormularioSupervisor()
                                    }else{
                                        showDialog.value = true
                                    }
                                },
                                context = contexto,
                                backGroundColor = bgColor,
                                textColor = Color.White,
                                status = isButtonEnabled
                            )
                            if (shouldShowPopup) {
                                AlertDialog(
                                    onDismissRequest = { formularioSupervisorViewModel.updateShouldShowPopup(false) },
                                    title = { Text(text = popupMessage) },
                                    confirmButton = {
                                        Button(onClick = { formularioSupervisorViewModel.updateShouldShowPopup(false) }, Modifier.background(Color(0xFF0957C3))) {
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
        Column(
            modifier = Modifier.fillMaxSize(), 
            horizontalAlignment = Alignment.CenterHorizontally, 
            verticalArrangement = Arrangement.Center) { 
            Text(text = "Cagando formulario ...") 
        }
    }






}

@Composable
fun SectionDatosPrincipales(formularioSupervisorViewModel: FormularioSupervisorViewModel) {
    val atributosFormulario = formularioSupervisorViewModel.atributosFormulario.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxWidth()
    ){
        TileExpansion(
            title = "Datos Principales",
            isopen = atributosFormulario.value.isMainDataOpen,
            onExpandChanged = {
                formularioSupervisorViewModel.toggleMainSection()
            })
        if (atributosFormulario.value.isMainDataOpen){
            TileExpansionBodyPrincipal(formularioSupervisorViewModel = formularioSupervisorViewModel)
        }
    }

}
@Composable
fun SectionDatosVisita(formularioSupervisorViewModel: FormularioSupervisorViewModel) {
    val atributosFormulario = formularioSupervisorViewModel.atributosFormulario.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxWidth()
    ){
        TileExpansion(
            title = "Datos de la Visita",
            isopen = atributosFormulario.value.isVisitDataOpen,
            onExpandChanged = {
                formularioSupervisorViewModel.toggleVisitSection()
            })
        if (atributosFormulario.value.isVisitDataOpen){
            TileExpansionBodyVisita(formularioSupervisorViewModel = formularioSupervisorViewModel)
        }
    }

}


@Composable
fun SectionDatosFormulario(formularioSupervisorViewModel: FormularioSupervisorViewModel) {
    Log.e("jesusdebug", "SectionDatosFormulario")
    val atributosFormulario = formularioSupervisorViewModel.atributosFormulario.collectAsState()
    Log.e("jesusdebug", "atributosFormulario")

    Column (
        modifier = Modifier
            .fillMaxWidth()
    ){
        TileExpansion(
            title = "Preguntas",
            isopen = atributosFormulario.value.isQuestionDataOpen,
            onExpandChanged = {
                Log.e("jesusdebug", "se hizo toggleQuestionSection: ${atributosFormulario.value.isQuestionDataOpen}")
                formularioSupervisorViewModel.toggleQuestionSection()
            })
        if (atributosFormulario.value.isQuestionDataOpen){
            Log.e("jesusdebug", "mostrando preguntas")
            TileExpansionBodyFormulario(formularioSupervisorViewModel = formularioSupervisorViewModel)
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

@Composable
fun MissingFieldsDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    //verticalArrangement = Arrangement.Center // Centra verticalmente el contenido
                ) {

                        Row (
                            modifier = Modifier
                                .fillMaxWidth(),
                                //.height(80.dp), // Ajusta la altura según sea necesario
                            //verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Campos Incompletos",
                                style = MaterialTheme.typography.h1.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = BlueVistony
                                )
                            )
                        }

                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            //.padding(top = 20.dp)
                            //.background(color = Color.Red),
                    ){
                        Text(
                            text = "Por favor, complete todos los campos obligatorios.",
                            style = MaterialTheme.typography.body1.copy(
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            },
            buttons = {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        //.background(color = Color.Gray)
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        //.height(60.dp), // Ajusta la altura según sea necesario
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(backgroundColor = BlueVistony)
                        ) {
                            Text(text = "Aceptar", color = Color.White)
                        }
                    }
                }

            }
        )
    }
}

package com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Pages


import android.Manifest.permission.CAMERA
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.ModelFormularioSupervisorTemplate.CameraImageViewModel
import com.vistony.salesforce.kotlin.View.components.ButtonView
import com.vistony.salesforce.kotlin.View.components.contexto
import java.io.File
import java.util.concurrent.Executor

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CamaraScreen(cameraImageViewModel: CameraImageViewModel, navController: NavController) {
    val permissionState = rememberPermissionState(permission = CAMERA)

    val context = LocalContext.current
    val cameraController = remember {
        LifecycleCameraController(context)
    }
    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val executor = ContextCompat.getMainExecutor(context)
                TakePicture(
                    cameraController = cameraController,
                    executor = executor,
                    navController = navController,
                    cameraImageViewModel = cameraImageViewModel,
                    context = context
                )
            }) {
                Box(modifier = Modifier.padding(12.dp)) {
                    Icon(
                        imageVector = Icons.Filled.CameraAlt,
                        contentDescription = "Icono de cámara"
                    )
                }
            }
        }
    ){
        if(permissionState.status.isGranted) {
            CameraComposable(
                modifier = Modifier.padding(it),
                cameraController = cameraController,
                lifecycle = lifecycle
                )

        }else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, // Centrar contenido horizontalmente
                    verticalArrangement = Arrangement.Center // Centrar contenido verticalmente
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Warning Icon",
                        tint = Color.Red,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "Permisos denegados",
                        style = androidx.compose.ui.text.TextStyle(
                            color = Color.Red,
                            fontSize = 18.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        ),
                        modifier = Modifier.padding(24.dp)
                    )
                    Row(modifier = Modifier.width(200.dp)) {
                        ButtonView(
                            description = "Regresar",
                            OnClick = {
                                navController.navigate("formulario")
                            },
                            context = contexto,
                            backGroundColor = Color.Red,
                            textColor = Color.White,
                            status = true
                        )
                    }
                }
            }
        }
    }

}

private fun TakePicture(
    cameraController: LifecycleCameraController,
    executor: Executor,
    navController: NavController,
    cameraImageViewModel: CameraImageViewModel,
    context: Context
) {
    val file = File.createTempFile("imagenFormulario", ".jpg", context.cacheDir)
    val outputDirectory = ImageCapture.OutputFileOptions.Builder(file).build()
    cameraController.takePicture(
        outputDirectory,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                Log.e("debugCamera" , "onImageSaved: ${file.absolutePath}")
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                Log.e("debugCamera" , "onImageSaved: ${bitmap.width} x ${bitmap.height}")
                /*val uri = FileProvider.getUriForFile(context, context.applicationContext.packageName, file)
                Log.e("debugCamera" , "onImageSaved: ${uri.path}")*/
                cameraImageViewModel.agregarImagen(bitmap, Uri.EMPTY)
                navController.navigate("formulario")
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("Camera", "Error al tomar la foto: ${exception.message}", exception)
            }
        }
    )
}


@Composable
fun CameraComposable(
    modifier: Modifier,
    cameraController : LifecycleCameraController,
    lifecycle : LifecycleOwner

    ) {
    cameraController.bindToLifecycle(lifecycle)
    AndroidView(factory = { context ->
        val previewView = PreviewView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        previewView.controller = cameraController
        previewView
    }, modifier = modifier)
}
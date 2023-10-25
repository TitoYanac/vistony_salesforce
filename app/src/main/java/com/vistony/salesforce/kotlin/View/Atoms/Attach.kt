package com.vistony.salesforce.kotlin.View.components

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Utilities.ResourceToImageBitmap
import com.vistony.salesforce.kotlin.Utilities.createImageFile1
import com.vistony.salesforce.kotlin.Utilities.getDate
import java.io.File


/*
@Composable
fun PreviewMakePhoto(
    context: Context, activity: Activity, delivery:String, date:String
    ,appCompatActivity: AppCompatActivity
)
{
    MakePhoto(
        context, activity, delivery, date,appCompatActivity
    )
}*/

/*
@Composable
fun MakePhoto(
    context: Context, activity: Activity, delivery:String, date:String,
    appCompatActivity: AppCompatActivity
) {
    Column(
        modifier = Modifier.selectableGroup()
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(BlueVistony, CircleShape)
                .clickable {
                    //MakePhotoUtil(context, activity, delivery, date)
                    //getPhoto(appCompatActivity)
                }
            , contentAlignment = Alignment.Center
        ) {
            Icon(
                ImageVector.vectorResource(R.drawable.ic_add_a_photo_black_24dp),
                tint = Color.White,
                contentDescription = null
            )
        }

    }
}
*/
/*
@Composable
fun MyComposeScreen(
    context: Context, activity: Activity, delivery:String, date:String
) {
    val requestCameraPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permiso de cámara concedido, ejecutar MakePhotoUtil()
                MakePhotoUtil(context, activity, delivery, date)
            } else {
                // Permiso de cámara denegado, manejar el caso adecuado
            }
        }

    Column {
        Button(onClick = {
            // Solicitar permiso de cámara al hacer clic en el botón
            val cameraPermission = android.Manifest.permission.CAMERA
            val isCameraPermissionGranted =
                ContextCompat.checkSelfPermission(context, cameraPermission) == PackageManager.PERMISSION_GRANTED
            if (isCameraPermissionGranted) {
                // Permiso de cámara concedido, ejecutar MakePhotoUtil()
                MakePhotoUtil(context, activity, delivery, date)
            } else {
                // Permiso de cámara denegado, solicitar permiso al usuario
                requestCameraPermissionLauncher.launch(cameraPermission)
            }
        }) {
            Text("Capturar Foto")
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun rememberLauncherForActivityResult(
    contract: ActivityResultContract<String, Boolean>,
    onResult: (Boolean) -> Unit
): ActivityResultLauncher<String> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    val launcher = rememberLauncherForActivityResult(contract) {
        onResult(it)
    }

    // Collect the result only when the lifecycle is at least STARTED
    lifecycleOwner.lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launcher.launch(it)
        }
    }

    return launcher
}*/

@Composable
fun MyComposeScreen(
    context: Context, activity: Activity, delivery:String, date:String
) {
    //val context = LocalContext.current
   // val activity = LocalLifecycleOwner.current.lifecycleScope
    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // Permiso de cámara concedido, ejecutar MakePhotoUtil()
                //val delivery = "delivery_value" // Reemplaza con el valor adecuado
                //val date = "date_value" // Reemplaza con el valor adecuado
                //MakePhotoUtil(context, activity, delivery, date)
            } else {
                // Permiso de cámara denegado, manejar el caso adecuado
            }
        }
    )

    Column {
        Button(onClick = {
            // Solicitar permiso de cámara al hacer clic en el botón
            val cameraPermission = android.Manifest.permission.CAMERA
            val isCameraPermissionGranted = PackageManager.PERMISSION_GRANTED ==
                    ContextCompat.checkSelfPermission(context, cameraPermission)
            if (isCameraPermissionGranted) {
                // Permiso de cámara concedido, ejecutar MakePhotoUtil()
                //val delivery = "delivery_value" // Reemplaza con el valor adecuado
                //val date = "date_value" // Reemplaza con el valor adecuado
                //MakePhotoUtil(context, activity, delivery, date)
            } else {
                // Permiso de cámara denegado, solicitar permiso al usuario
                requestCameraPermissionLauncher.launch(cameraPermission)
            }
        }) {
            Text("Capturar Foto")
        }
    }
}
/*
fun MakePhotoUtil(
    //context: Context, activity: CoroutineScope, delivery: String, date: String
    context: Context, activity: Activity, delivery:String, date:String, coroutineScope: CoroutineScope
) {
    val permsRequestCode = 255
    val perms = arrayOf(android.Manifest.permission.CAMERA)
    if (ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // Permiso de cámara no concedido, solicitar permiso
        ActivityCompat.requestPermissions(activity, perms, permsRequestCode)
    } else {
        try {
            // Permiso de cámara concedido, crear intent y archivo para la foto
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoFile: File? = createImageFile(delivery + "_" + date, "G", context)
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    context,
                    context.applicationContext.packageName + ".provider",
                    photoFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                coroutineScope.launch()

            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MakePhotoUtil(context: Context, activity: CoroutineScope, delivery: String, date: String) {
    val permsRequestCode = 255
    val perms = arrayOf(Manifest.permission.CAMERA)
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // Permiso de cámara no concedido, solicitar permiso
        ActivityCompat.requestPermissions(activity, perms, permsRequestCode)
    } else {
        try {
            // Permiso de cámara concedido, crear intent y archivo para la foto
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoFile: File? = createImageFile(delivery + "_" + date, "G", context)
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    context,
                    context.applicationContext.packageName + ".provider",
                    photoFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        // La foto se capturó exitosamente
                        // Realiza las acciones necesarias con la foto capturada aquí
                    } else {
                        // El usuario canceló la captura de la foto o ocurrió un error
                        // Maneja el caso adecuado
                    }
                }

                launcher.launch(intent)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }
}

fun createImageFile(fileName: String, directoryName: String, context: Context): File? {
    val storageDir = context.getExternalFilesDir(directoryName)
    return try {
        File.createTempFile(
            fileName,
            ".jpg",
            storageDir
        )
    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun rememberLauncherForActivityResult(
    contract: ActivityResultContract<String, Boolean>,
    onResult: (Boolean) -> Unit
): ActivityResultLauncher<String> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    val launcher = rememberLauncherForActivityResult(contract) {
        onResult(it)
    }
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    val photoFile: File? = createImageFile(delivery + "_" + date, "G", context)
    if (photoFile != null) {
        val photoURI = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            photoFile
        )
    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    // Collect the result only when the lifecycle is at least STARTED
    lifecycleOwner.lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launcher.launch(intent)
        }
    }

    return launcher
}
*/

/*
//Evento que procesa el resultado de la cámara y envía la vista previa de la foto al ImageView
fun getPhoto(appCompatActivity: AppCompatActivity)
{
     val startForResult =
         appCompatActivity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                 result: ActivityResult ->

             if (result.resultCode == Activity.RESULT_OK) {

                val intent = result.data
                val imageBitmap = intent?.extras?.get("data") as Bitmap

                //val imageView = findViewById<ImageView>(R.id.imageView)
                //imageView.setImageBitmap(imageBitmap)
            }
        }


    startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
}*/


@Composable
fun MakePhotoButtom(
    tittle:String,
    resultBitmap: (Bitmap) -> Unit,
    context: Context,
    activity: Activity,
    bitmapState:(Bitmap?)
) {
    val permsRequestCode = 255
    val perms = arrayOf(android.Manifest.permission.CAMERA)
    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }

    if (imageBitmap.value==null){
        if(bitmapState==null)
        {
            imageBitmap.value=ResourceToImageBitmap(R.drawable.ic_add_a_photo_black_24dp)
        }else
        {
            imageBitmap.value=bitmapState.asImageBitmap()
        }
    }
    //var imageBitmap : ImageBitmap
    //imageBitmap=

    Log.e(
        "REOS",
        "Attach-MakePhotoButtom-imageBitmap " + imageBitmap.value
    )
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // Permiso de cámara no concedido, solicitar permiso
        ActivityCompat.requestPermissions(activity, perms, permsRequestCode)
    } else {
            val startForResult = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result: ActivityResult ->
                Log.e(
                    "REOS",
                    "Attach-getPhoto-result " + result
                )
                Log.e(
                    "REOS",
                    "Attach-getPhoto-result.resultCode " + result.resultCode
                )
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                    var bitmap = intent?.extras?.get("data") as Bitmap
                    resultBitmap(bitmap!!)
                    imageBitmap.value=bitmap.asImageBitmap()
                    // Hacer algo con la imagen
                    Log.e(
                        "REOS",
                        "Attach-getPhoto-imageBitmap " + imageBitmap.toString()
                    )
                }

            }
            buttonImage(tittle,startForResult,imageBitmap)
    }
}


@Composable
fun CaptureImage(
    resultBitmap: (Bitmap) -> Unit,
    context: Context,
    activity: Activity,
    tittle: String,
    type:String
) {
    // Función para capturar la imagen con el launcher
    val permsRequestCode = 255
    val perms = arrayOf(android.Manifest.permission.CAMERA)
    val fileMutable = remember { mutableStateOf<File?>(null) }

    // Aquí puedes realizar las comprobaciones de permisos
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // Permiso de cámara no concedido, solicitar permiso
        ActivityCompat.requestPermissions(activity, perms, permsRequestCode)
    } else {
        // Lanzar el launcher para capturar la imagen
        val startForResult = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val file = fileMutable
                var bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(),
                    Uri.fromFile(file.value)
                )
                //val intent = result.data
                //var bitmap = intent?.extras?.get("data") as Bitmap
                resultBitmap(bitmap!!)
            }
        }

        //startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        ItemGetImageCapture(
            ResourceToImageBitmap(R.drawable.ic_add_a_photo_black_24dp),
            "Capturar",
            startForResult,
            context,
            activity,
            tittle,
            type,
            resultFile = { resultFile ->
                fileMutable.value=resultFile
            },
        )
    }
}

@Composable
fun CaptureImageAndRetrieveBitmap(
        activity: Activity,
        context:Context,
        tittle:String,
        type:String,
        onBitmapCaptured: (Bitmap) -> Unit,

) {
    var photoFile: File? = null
    val fileMutable = remember { mutableStateOf<File?>(null) }
    val takePicture: ActivityResultLauncher<Intent> = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            /*val data = result.data
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            imageBitmap?.let { onBitmapCaptured(it) }*/
            val file = fileMutable
            var bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(),
                    Uri.fromFile(file.value)
            )
            //val intent = result.data
            //var bitmap = intent?.extras?.get("data") as Bitmap
            onBitmapCaptured(bitmap!!)
        }
    }

    //val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)


    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    // Crea el File
    // Crea el File


    photoFile = createImageFile1(tittle+ "_" + getDate(), type,activity)
    Log.e("REOS", "MenuGetImage-ItemGetImageCapture-photoFile: "+photoFile)
    if (photoFile != null) {
        Log.e("REOS", "MenuGetImage-ItemGetImageCapture-->FotoLocal-->photoFile != null")
        var photoURI: Uri? = null
        when (BuildConfig.FLAVOR) {
            "ecuador" -> photoURI = FileProvider.getUriForFile(
                    context,
                    "com.vistony.salesforce.ecuador",
                    photoFile
            )

            "peru", "marruecos" -> photoURI =
                    FileProvider.getUriForFile(context, "com.vistony.salesforce.peru", photoFile)

            "espania" -> photoURI = FileProvider.getUriForFile(
                    context,
                    "com.vistony.salesforce.espania",
                    photoFile
            )

            "perurofalab" -> photoURI = FileProvider.getUriForFile(
                    context,
                    "com.vistony.salesforce.perurofalab",
                    photoFile
            )

            "bolivia" -> photoURI = FileProvider.getUriForFile(
                    context,
                    "com.vistony.salesforce.bolivia",
                    photoFile
            )

            "paraguay" -> photoURI = FileProvider.getUriForFile(
                    context,
                    "com.vistony.salesforce.paraguay",
                    photoFile
            )

            "chile" -> photoURI =
                    FileProvider.getUriForFile(context, "com.vistony.salesforce.chile", photoFile)
        }
       // resultFile(photoFile)
        fileMutable.value=photoFile
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        takePicture.launch(intent)
    }
}





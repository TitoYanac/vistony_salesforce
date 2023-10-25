package com.vistony.salesforce.kotlin.Utilities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Controller.Adapters.StatusDispatchDialog
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.View.Atoms.TextLabel
import java.io.*


private const val NameOfFolder = "/RECIBOS"

/*
fun CaptureImageSave(
    context: Context,
    activity: Activity
    ,tittle:String
    ,date:String
    ,resultBitmap: (Bitmap) -> Unit,
){
    val permsRequestCode = 255
    val perms = arrayOf(Manifest.permission.CAMERA)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        requestPermissions(activity,  perms, permsRequestCode)
    } else {
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
                ) {

                    result: ActivityResult ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        val file = fileMutable
                        var bitmap = MediaStore.Images.Media.getBitmap(
                                context.getContentResolver(),
                                Uri.fromFile(file.value)
                        )
                        resultBitmap(bitmap!!)
                    }
                }

                startCaptureImge(
                        startForResult,
                        context,
                        activity,
                        tittle,
                        date,
                        resultFile = { resultFile ->
                            fileMutable.value=resultFile
                        },
                )

            }

    }
}*/

fun startCaptureImge(
        startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
        context: Context,
        activity: Activity,
        tittle:String,
        type: String,
        resultFile: (File) -> Unit,
)
{
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    // Crea el File
    // Crea el File
    var photoFile: File? = null

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
        resultFile(photoFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startForResult.launch(intent)
    }
}




fun SaveImageStatusDispatch(
    context: Context,
    ImageToSave: Bitmap?,
    Entrega_id: String?,
    type: String
): File? {
    val file_path =
        Environment.getExternalStorageDirectory().absolutePath + NameOfFolder
    val dir = File(file_path)
    if (!dir.exists()) {
        dir.mkdirs()
    }
    val file = File(dir, Entrega_id + "_" + type + ".JPG")
    try {
        val fOut = FileOutputStream(file)
        ImageToSave?.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
        fOut.flush()
        fOut.close()
        MakeSureFileWasCreatedThenMakeAvabile(file,context)
        AbleToSave(context)
    } catch (e: FileNotFoundException) {
        UnableToSave(e.toString(),context)
    } catch (ioexception: IOException) {
        UnableToSave(ioexception.toString(),context)
    } catch (exception: Exception) {
        UnableToSave(exception.toString(),context)
    }
    return file
}

private fun MakeSureFileWasCreatedThenMakeAvabile(file: File,   TheThis:Context) {
    MediaScannerConnection.scanFile(
        TheThis, arrayOf(file.toString()), null
    ) { path, uri ->
        //  Toast.makeText(TheThis, "Album actualizado", Toast.LENGTH_SHORT).show();
    }
}

private fun UnableToSave(error: String,TheThis:Context) {
    Toast.makeText(
        TheThis,
        //TheThis.getResources().getString().toString() + error,
        "Error",
        Toast.LENGTH_SHORT
    ).show()
}

private fun AbleToSave(TheThis:Context) {
    Toast.makeText(
        TheThis,
        //TheThis.getResources().getString(R.string.image_save_galery),
        "Imagen guardada",
        Toast.LENGTH_SHORT
    ).show()
}

fun getBASE64(file_path: String): String? {
    var encoded: String? = ""
    Log.e("REOS", "ImageCameraController-getImageSDtoByte-file_path-Ingreso$file_path")
    var bitmapdata: ByteArray? = null
    try {
        val options = BitmapFactory.Options()
        options.inSampleSize = 4
        val bitmap = BitmapFactory.decodeFile(file_path, options)
        val blob = ByteArrayOutputStream()
        //bitmap.compress(Bitmap.CompressFormat.JPEG, 10/* Ignored for PNGs */, blob);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30 /* Ignored for PNGs */, blob)
        bitmapdata = blob.toByteArray()
        encoded = Base64.encodeToString(bitmapdata, Base64.DEFAULT)
        //encoded = Base64.encodeToString(convertBitmapToByteArray(bitmap), Base64.DEFAULT);
    } catch (e: java.lang.Exception) {
        println(e.message)
        encoded = ""
    }
    Log.e("REOS", "ImageCameraController-getImageSDtoByte-file_path-Salio$file_path")
    return encoded
}

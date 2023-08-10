package com.vistony.salesforce.kotlin.utilities

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
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.FileProvider
import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Controller.Adapters.StatusDispatchDialog
import java.io.*


private const val NameOfFolder = "/RECIBOS"

fun MakePhotoUtil(
    context: Context,activity: Activity,delivery:String,date:String
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
        try {
            Log.e("REOS,", "Image-MakePhotoUtil-el permiso fue asignado")
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // Crea el File
            var photoFile: File? = null
            //startActivityForResult(intent,0);
            photoFile = createImageFile(delivery + "_" + date, "G",activity)
            Log.e("REOS,", "Image-MakePhotoUtil-photoFile"+photoFile)
            if (photoFile != null) {
                //Uri photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.peru" , photoFile);
                var photoURI: Uri? = null
                when (BuildConfig.FLAVOR) {
                    "ecuador" -> photoURI = FileProvider.getUriForFile(
                        context,
                        "com.vistony.salesforce.ecuador",
                        photoFile
                    )
                    "peru", "marruecos" -> photoURI = FileProvider.getUriForFile(
                        context,
                        "com.vistony.salesforce.peru",
                        photoFile
                    )
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
                    "paraguay" -> {
                        photoURI = FileProvider.getUriForFile(
                            context,
                            "com.vistony.salesforce.paraguay",
                            photoFile
                        )
                        photoURI = FileProvider.getUriForFile(
                            context,
                            "com.vistony.salesforce.chile",
                            photoFile
                        )
                    }
                    "chile" -> photoURI = FileProvider.getUriForFile(
                        context,
                        "com.vistony.salesforce.chile",
                        photoFile
                    )
                }
                Log.e("REOS,", "Image-MakePhotoUtil-antes.del.intent.putExtra")
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                //startActivityForResult(intent,20);
                //someActivityResultLauncherGuia.launch(intent)
                /*if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                someActivityResultLauncherGuia.launch(intent);
                            }*/
            }
        } catch (ex: IOException) {
            Log.e("REOS,", "StatusDispatchDialog-onCreateDialog-imageViewPhoto-error:$ex")
        }
    }
}

@Throws(IOException::class)
private fun createImageFile(entrega_id: String, type: String,activity: Activity): File? {

    //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    val imageFileName = entrega_id + "_" + type
    val storageDir: File = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    val image = File.createTempFile(imageFileName, ".jpg", storageDir)
    if (type == "G") {
        StatusDispatchDialog.mCurrentPhotoPathG = image.absolutePath
    } else if (type == "L") {
        StatusDispatchDialog.mCurrentPhotoPathL = image.absolutePath
    }
    return image
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
        //options.inSampleSize = 4
        val bitmap = BitmapFactory.decodeFile(file_path, options)
        val blob = ByteArrayOutputStream()
        //bitmap.compress(Bitmap.CompressFormat.JPEG, 10/* Ignored for PNGs */, blob);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100 /* Ignored for PNGs */, blob)
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

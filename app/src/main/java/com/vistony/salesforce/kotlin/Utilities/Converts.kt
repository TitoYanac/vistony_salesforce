package com.vistony.salesforce.kotlin.Utilities

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.vistony.salesforce.Controller.Adapters.StatusDispatchDialog
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun getDateCurrent(): String? {
    val format = "yyyyMMdd"
    val formatter = DateTimeFormatter.ofPattern(format)
    val now = LocalDateTime.now()
    return formatter.format(now)
}

fun getTimeCurrent(): String? {
    val format = "HHmmss"
    val formatter = DateTimeFormatter.ofPattern(format)
    val now = LocalDateTime.now()
    return formatter.format(now)
}

fun getDateTimeCurrent(): String? {
    var ID = ""
    val calendario = GregorianCalendar()
    val year: String
    var mes: String
    var dia: String
    val hora: String
    val min: String
    val segs: String
    try {
        year = calendario[Calendar.YEAR].toString()
        mes = (calendario[Calendar.MONTH] + 1).toString()
        dia = calendario[Calendar.DAY_OF_MONTH].toString()
        hora = calendario[Calendar.HOUR_OF_DAY].toString()
        min = calendario[Calendar.MINUTE].toString()
        segs = calendario[Calendar.SECOND].toString()
        if (mes.length == 1) {
            mes = "0$mes"
        }
        if (dia.length == 1) {
            dia = "0$dia"
        }
        ID = year + mes + dia + hora + min + segs + getRandomNumberString(2)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ID
}

private fun getRandomNumberString(maxLeght: Int): String? {
    // It will generate 6 digit random Number.
    // from 0 to 999999
    val rnd = Random()
    val number = rnd.nextInt(maxLeght)

    // this will convert any number sequence into 6 character.
    return String.format("%02d", number)
}

@Composable
fun ResourceToImageBitmap(resourceId: Int): ImageBitmap {
    val context = LocalContext.current

    val vectorDrawable = VectorDrawableCompat.create(context.resources, resourceId, null)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable?.intrinsicWidth ?: 0,
        vectorDrawable?.intrinsicHeight ?: 0,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable?.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable?.draw(canvas)

    return bitmap.asImageBitmap()
}

fun convertResourceToBitmap(context: Context, resourceId: Int): Bitmap? {
    val vectorDrawable = VectorDrawableCompat.create(context.resources, resourceId, null)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable?.intrinsicWidth ?: 0,
        vectorDrawable?.intrinsicHeight ?: 0,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable?.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable?.draw(canvas)
    return bitmap
}

@Throws(IOException::class)
fun createImageFile1(delivery: String, type: String,activity: Activity): File? {

    //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    val imageFileName = delivery + "_" + type
    val storageDir: File = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    val image = File.createTempFile(imageFileName, ".jpg", storageDir)
    if (type == "G") {
        StatusDispatchDialog.mCurrentPhotoPathG = image.absolutePath
    } else if (type == "L") {
        StatusDispatchDialog.mCurrentPhotoPathL = image.absolutePath
    }
    return image
}

@Throws(IOException::class)
fun getDate(): String? {
    return SimpleDateFormat("yyyyMMdd").format(Date())
}

fun ConvertDateSAPaUserDate(dateSAP: String?): String? {
    var dateUser = ""
    var year = ""
    var month = ""
    var day = ""
    if (dateSAP != null) {
        if (dateSAP.length == 8) {
            year = dateSAP.substring(0, 4)
            month = dateSAP.substring(4, 6)
            day = dateSAP.substring(6, 8)
            dateUser = "$day/$month/$year"
        }
    }
    return dateUser
}

fun getTimeCurrentHHMM(): String? {
    val format = "HHmm"
    val formatter = DateTimeFormatter.ofPattern(format)
    val now = LocalDateTime.now()
    return formatter.format(now)
}


fun getStatusVariableinRange(StartTime:String,EndTime:String): Boolean {
    var status:Boolean=false
    val valor = getTimeCurrentHHMM()!!.toInt()
    when (valor) {
        in StartTime.toInt() ..EndTime.toInt() -> status=true
        else -> status=false
    }
    return status
}


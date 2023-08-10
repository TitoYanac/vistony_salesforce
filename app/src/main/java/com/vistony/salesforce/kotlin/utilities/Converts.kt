package com.vistony.salesforce.kotlin.utilities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import com.vistony.salesforce.Controller.Utilitario.FormulasController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

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


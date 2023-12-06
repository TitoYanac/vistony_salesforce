package com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.ModelFormularioSupervisorTemplate

import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vistony.salesforce.kotlin.Model.FormularioGaleria
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.concurrent.ConcurrentLinkedQueue

class CameraImageViewModel : ViewModel() {
    private val _imagenesBitmap = mutableStateListOf<Bitmap>()
    private val _imagenesGaleria = mutableStateListOf<FormularioGaleria>()
    private val conversionQueue = ConcurrentLinkedQueue<Pair<Bitmap, Uri>>()
    private var isProcessing = false

    val imagenesBitmap: List<Bitmap> get() = _imagenesBitmap
    val imagenesGaleria: List<FormularioGaleria> get() = _imagenesGaleria

    fun agregarImagen(imageBitmap: Bitmap, uri: Uri) {
        _imagenesBitmap.add(imageBitmap)
        conversionQueue.add(Pair(imageBitmap, uri))
        processQueue()
    }

    private fun processQueue() {
        if (isProcessing || conversionQueue.isEmpty()) return

        isProcessing = true
        viewModelScope.launch {
            while (conversionQueue.isNotEmpty()) {
                val (image, uri) = conversionQueue.poll() ?: continue
                val base64String = convertToBase64(image)
                val formularioGaleria = FormularioGaleria(uri = uri.toString(), base64 = base64String, status = "N", numInforme = "", bitmap = image)
                _imagenesGaleria.add(formularioGaleria)
            }
            isProcessing = false
        }
    }

    private suspend fun convertToBase64(bitmap: Bitmap): String = withContext(Dispatchers.IO) {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.DEFAULT)
    }



    fun borrarImagen(index: Int) {
        if (index in _imagenesBitmap.indices) {
            _imagenesBitmap.removeAt(index)
            if (index in _imagenesGaleria.indices) {
                _imagenesGaleria.removeAt(index)
            }
        }
    }

    fun agregarImagenAsBitmap(bitmap: Bitmap?, uri: Uri?) {

    }

}

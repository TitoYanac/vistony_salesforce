package com.vistony.salesforce.kotlin.utilities

import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.vistony.salesforce.R


   public fun  showNotification(context: Context,id: Int,message: String) {
        val channelId = "my_channel_id"
        val notificationId = id

        // Crear el canal de notificación (solo necesario en Android Oreo y superior)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "My Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Construir la notificación
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.logo)
            .setContentTitle("Notificación")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Mostrar la notificación
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notificationBuilder.build())
        }
    }

fun openNotificationSettings(context: Context) {
    val intent = Intent()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
        intent.putExtra("app_package", context.packageName)
        intent.putExtra("app_uid", context.applicationInfo.uid)
    } else {
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:" + context.packageName)
    }
    context.startActivity(intent)
}

fun showNotificationDisabledDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle("Notificaciones desactivadas")
        .setMessage("Las notificaciones de la aplicación están desactivadas. Por favor, habilítalas para recibir notificaciones.")
        .setPositiveButton("Abrir configuración") { dialog, _ ->
            openNotificationSettings(context)
            dialog.dismiss()
        }
        .setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}

fun areNotificationsEnabled(context: Context): Boolean {
    val notificationManager = NotificationManagerCompat.from(context)
    return notificationManager.areNotificationsEnabled()
}



fun createNotification(context: Context?): Notification? {
    var notificationManager: NotificationManager? = null
    var notificationBuilder: NotificationCompat.Builder? = null
    try {
        // Crear un canal de notificación (solo necesario en Android 8.0 y versiones posteriores)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "mi_canal_id"
            val channelName = "Mi Canal"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            //val notificationManager = getSystemService(context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Crear y configurar la notificación
        notificationBuilder = NotificationCompat.Builder(context!!, "mi_canal_id")
            .setContentTitle("Informativo")
            .setContentText("Inicio de servicio de notificaciones")
            //.setSmallIcon(R.drawable.ic_notification)
            .setSmallIcon(R.mipmap.logo)
        // Agregar cualquier otra configuración que desees
    }catch (e:Exception)
    {
        Log.e(
            "REOS",
            "Service-createNotification-error: "+e.toString()
        )
    }


    return notificationBuilder?.build()
}
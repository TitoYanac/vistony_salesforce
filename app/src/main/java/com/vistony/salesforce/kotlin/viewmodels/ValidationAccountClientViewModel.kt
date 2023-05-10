package com.vistony.salesforce.kotlin.viewmodels

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.vistony.salesforce.Dao.SQLite.RutaFuerzaTrabajoSQLiteDao
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.data.ValidationAccountClientApi
import com.vistony.salesforce.kotlin.data.ValidationAccountClientModel

class ValidationAccountClientViewModel: ViewModel() {

    fun getSalesRoute(
        context: Context
    ):
            Array<String>
            //Array<String>
    {

        var list = arrayOf("Favorites", "Options", "Settings", "Share")
        /*val rutaFuerzaTrabajoSQLiteDao: RutaFuerzaTrabajoSQLiteDao? = RutaFuerzaTrabajoSQLiteDao(
            context)

        val items=rutaFuerzaTrabajoSQLiteDao?.ObtenerRutaFuerzaTrabajo()
        var list:ArrayList<String> = ArrayList((items?.size ?: 0))
        Log.e(
            "REOS",
            "DialogValidationAccountClient-MyUI-items"+items?.size)
        for ( i in 0..(items?.size ?: 0) -1)
        {
            list.add(((items?.get(i)?.getDia()
                ?: 0).toString()))
            Log.e(
                "REOS",
                "DialogValidationAccountClient-MyUI-items[i].getEstado(): "+ (items?.get(i)?.getDia()
                    ?: 0)
            )
        }*/

        return list

    }

    fun generatePDF(
        context: Context,
        activity: Activity,
        day: String,
        viewModelStoreOwner: ViewModelStoreOwner,
        lifecycleOwner: LifecycleOwner
    )
    {
        var pd: ProgressDialog? = ProgressDialog(context)
        pd = ProgressDialog.show(
            context,
            context.resources.getString(R.string.please_wait),
            context.resources.getString(R.string.querying_dates),
            true,
            false
        )

        ///////////////////////////// ENVIAR DEPOSITOS ANULADOS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        ///////////////////////////// ENVIAR DEPOSITOS ANULADOS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        var validationAccountClientApi: ValidationAccountClientApi
        validationAccountClientApi = ViewModelProvider(viewModelStoreOwner).get(
            ValidationAccountClientApi::class.java
        )
        validationAccountClientApi.getValidationAccountClientApi(
            SesionEntity.imei,
            SesionEntity.fuerzatrabajo_id,
            context,
            day
        )?.observe(lifecycleOwner,
            Observer { data1: List<ValidationAccountClientModel?>? ->

            })

    }



}
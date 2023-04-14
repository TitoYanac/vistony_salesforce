package com.vistony.salesforce.kotlin.compose

import com.vistony.salesforce.R


sealed class MenuOptions (
    val icon : Int,
    val title: String,
    val ruta: String
) {
    object Pantalla1: MenuOptions(R.drawable.ic_baseline_account_circle_24, "Hoja Despacho" ,"ValidationAccountClient")
    object Pantalla2: MenuOptions(R.drawable.ic_directions_walk_black_24dp,"Ruta Vendedor","pantalla1")
    object Pantalla3: MenuOptions(R.drawable.ic_local_atm_black_24dp,"Depósito","pantalla1")
    object Pantalla4: MenuOptions(R.drawable.ic_search_black_24dp,"Consultas","pantalla1")
    object Pantalla5: MenuOptions(R.drawable.ic_baseline_insert_chart_24,"Comisiones","pantalla1")
    object Pantalla6: MenuOptions(R.drawable.ic_baseline_list_alt_24,"Formulario","pantalla1")
    object Pantalla7: MenuOptions(R.mipmap.moneda_64,"Dinero Cobrado","pantalla1")
    object Pantalla8: MenuOptions(R.mipmap.camion_reparto_64,"Camion","pantalla1")
    object Pantalla9: MenuOptions(R.drawable.ic_arrow_back_black_24dp,"Opciones del Sistema","pantalla1")
    object Pantalla10: MenuOptions(R.drawable.ic_baseline_settings_applications_24,"Configuracion","pantalla1")
    object Pantalla11: MenuOptions(R.drawable.ic_exit_to_app_black_24dp,"Salir","pantalla1")
}

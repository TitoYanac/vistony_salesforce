package com.vistony.salesforce.kotlin.utilities

import com.vistony.salesforce.Controller.Utilitario.FormulasController
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
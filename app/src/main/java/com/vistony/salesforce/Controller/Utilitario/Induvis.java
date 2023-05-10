package com.vistony.salesforce.Controller.Utilitario;

import static java.time.temporal.ChronoUnit.DAYS;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
//import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Dao.Retrofit.LoginRepository;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Induvis {
    static DecimalFormat format = new DecimalFormat("#0.00");
    public static String getInformation(String flavor){
        String information="";

        switch (flavor){
            case "chile":
                information="\n R.U.T N° 764114914 " +
                        "Panamericana Norte 19434 - Bodega 2 - Lampa - Chile " +
                        "Central: (56) 952285492 E-mail: ventas.chile@vistony.com" +
                        " Web: www.vistonylubricantes.cl"+
                        "";
                break;
            case "ecuador":
                information="R.U.C N° 0992354690001 " +
                        "km 13 1/2 Vía Duran Tambo - Guayas – Ecuador" +
                        "Central: (593) 958739241 E-mail: ventas.ecuador@vistony.com" +
                        "\nWeb: www.vistony.com.ec";
                break;
            case "bolivia":
                information="N.I.T N° 1004361020  " +
                        "Carretera a Copacabana S/N Urb. Pomamaya" +
                        "Central: 77793371  E-mail: milton.coronel@vistony.com   " +
                        " Web: www.vistony.com.bo";
                break;
            case "peru":
                information="R.U.C N° 20102306598 " +
                        "Mz. B1 Lt. 1 - Parque Industrial de Ancón - Acompia " +
                        "Central: (01) 5521325 E-mail: ventas@vistony.com   " +
                        " Web: www.vistony.com";
                break;
            case "paraguay":
                information="R.U.C N° 80035312-9" +
                        "Acceso Sur y Benjamín Aceval -Barrio Alegre-Guarambaré" +
                        "Central:0986-454-534/0986-125-810 E-mail: ventas@vistony.com   " +
                        " Web: www.vistony.com.py";
                break;
            case "perurofalab":
                information="R.U.C N° 20601500605 " +
                        "MZA. B1 LOTE. 01 INT. D1 PQUE.IND.DE ANCON - ACOMPIA(ALT.KM.46.5 " +
                        "PAN.NORTE) LIMA - LIMA - ANCON " +
                        "Telf: 945168184 E-mail: rofalab@tolbrin.com" ;
                break;
            case "espania":
                information="N.I.F: B45783081 " +
                        "OFICINA: C/Rafael Alberti, 30 Pol. Industrial Fuente Techada ORGAZ " +
                        "(Toledo) - Apdo. Correos 30, 45100 Sonseca " +
                        "Telf: 925 910 177" ;
                break;
            case "marruecos":
                information="RC - 19145- TP - 58200011 - NIF  : 15257896 - ICE: 000131448000037 -" +
                        " CNSS: 4399912 - Nº COMPTE BANCAIRE - 0303 S000000197" +
                        "Adresse : Tetouan Park, Lot 84 km 7 Route Tanger" +
                        "TEL : 0666 17 33 65 - E-MAIL : halima.hayoun@vistony.com" ;
                break;
        }

        return information;
    }

    public static String getDate(String flavor,String dateBD){
        String dateView="",year,month,day;
        Log.e("REOS","Induvis-getDate-flavor:"+flavor);
        Log.e("REOS","Induvis-getDate-dateBD:"+dateBD);
        try {
        switch (flavor){
            case "bolivia":
            case "india":
            case "chile":
            case "ecuador":
            case "peru":
            case "paraguay":
            case "perurofalab":
            case "espania":
            case "marruecos":
                year=dateBD.substring(0,4);
                month=dateBD.substring(4,6);
                day=dateBD.substring(6,8);
                dateView=year+"-"+month+"-"+day;

                break;
        }
        }catch (Exception e)
        {
            Log.e("REOS","Induvis-getDate-e:"+e);
        }
        Log.e("REOS","Induvis-getDate-dateView:"+dateView);
        return dateView;
    }

    public static String getTime(String flavor,String timeBD){
        String timeView="",hour,minute,second;
        switch (flavor){
            case "chile":
            case "peru":
            case "bolivia":
            case "ecuador":
            case "paraguay":
            case "perurofalab":
            case "espania":
            case "marruecos":
                hour=timeBD.substring(0,2);
                minute=timeBD.substring(2,4);
                second=timeBD.substring(4,6);
                timeView=hour+":"+minute;
                break;
        }
        return timeView;
    }

    public static String getTimeSAP(String flavor,String timeBD){
        String timeView="",hour,minute,second;
        if(timeBD.equals(""))
        {

        }
        else {
            switch (flavor) {
                case "peru":
                case "bolivia":
                case "ecuador":
                case "chile":
                case "paraguay":
                case "perurofalab":
                case "espania":
                case "marruecos":
                    hour = timeBD.substring(0, 2);
                    minute = timeBD.substring(2, 4);
                    second = timeBD.substring(4, 6);
                    timeView = hour + "" + minute;
                    break;
            }
        }
        return timeView;
    }


    public static double getImpuestoDouble(){
        double impuesto=0.0;
        switch (BuildConfig.FLAVOR){
            case "chile":
                impuesto=0.19;
                break;
            case "ecuador":
                impuesto=0.12;
                break;
            case "peru":
                impuesto=0.18;
                break;
            case "bolivia":
                impuesto=0.14942529;
                break;
            case "paraguay":
                impuesto=0.10;
                break;
            case "perurofalab":
                impuesto=0.18;
                break;
            case "espania":
                impuesto=0.21;
                break;
            case "marruecos":
                impuesto=0.20;
                break;
        }

        return impuesto;
    }

    public static String getImpuestoString(){
        String impuesto="0";
        switch (BuildConfig.FLAVOR){
            case "chile":
                impuesto="19";
                break;
            case "ecuador":
                impuesto="12";
                break;
            case "peru":
                impuesto="18";
                break;
            case "bolivia":
                impuesto="14.942529";
                break;
            case "paraguay":
                impuesto="10";
                break;
            case "perurofalab":
                impuesto="18";
                break;
            case "espania":
                impuesto="21";
                break;
            case "marruecos":
                impuesto="20";
                break;
        }

        return impuesto;
    }

    public static String  getAgenciaInduvis(){
        String agencia=null;
        switch (BuildConfig.FLAVOR){
            case "chile":
                agencia="P764114914";
                break;
            case "ecuador":
                agencia="P0992354690001";
                break;
            case "peru":
                agencia="P20102306598";
                break;
            case "bolivia":
                agencia="P1004361020";
                break;
            case "paraguay":
                agencia="P80035312-9";
                break;
            case "perurofalab":
                agencia="P20601500605";
                break;
        }

        return agencia;
    }

    public static String getTaxCodeString(){
        String impuesto="0";
        switch (BuildConfig.FLAVOR){
            case "bolivia":
            case "chile":
                impuesto="IVA";
                break;
            case "ecuador":
                impuesto="IVA_12";
                break;
            case "peru":
            case "perurofalab":
                impuesto="IGV";
                break;
            case "paraguay":
                impuesto="IVA_10";
                break;
            case "espania":
                impuesto="R3";
                break;
            case "marruecos":
                impuesto="C4";
                break;
        }
        return impuesto;
    }

    public static String getTituloVentaString(Context context){
        String tituloventa="";
        switch (BuildConfig.FLAVOR){


            case "perurofalab":
            case "espania":
            case "marruecos":
                tituloventa=context.getResources().getString(R.string.salesorder);
                break;
            case ("ecuador"):
            case ("bolivia"):
            case "paraguay":
            case "chile":
            case "peru":
                if(SesionEntity.quotation.equals("Y"))
                 {
                     tituloventa=context.getResources().getString(R.string.quotation);
                 }
                 else
                     {
                         tituloventa=context.getResources().getString(R.string.salesorder);
                     }
                break;
        }
        return tituloventa;
    }

    static public void setTituloContenedor(String titulo, Activity activity){
        activity.setTitle(titulo);
    }
    public ArrayList<HistoricContainerSalesEntity> convertHistoricContainerSalesEntityTotalPeriod
            (List<HistoricContainerSalesEntity> listHistoricContainerSalesEntity)
    {
        ArrayList<HistoricContainerSalesEntity> listaConvertidaHistoricContainerSalesEntity=new ArrayList<>();
        HistoricContainerSalesEntity historicContainerSalesEntity;
        for(int i=0;i<listHistoricContainerSalesEntity.size();i++)
        {
            historicContainerSalesEntity=new HistoricContainerSalesEntity();
            String Period="";
            if(i==0)
            {
                historicContainerSalesEntity.mes=listHistoricContainerSalesEntity.get(i).getMes();
                historicContainerSalesEntity.anio=listHistoricContainerSalesEntity.get(i).getAnio();
                historicContainerSalesEntity.SlpCode=listHistoricContainerSalesEntity.get(i).getSlpCode();
                historicContainerSalesEntity.company=listHistoricContainerSalesEntity.get(i).getCompany();
                historicContainerSalesEntity.userid=listHistoricContainerSalesEntity.get(i).getUserid();
                historicContainerSalesEntity.variable=listHistoricContainerSalesEntity.get(i).getVariable();
                historicContainerSalesEntity.montototal=listHistoricContainerSalesEntity.get(i).getMontototal();
                listaConvertidaHistoricContainerSalesEntity.add(historicContainerSalesEntity);
            }
            for(int j=0;j<listaConvertidaHistoricContainerSalesEntity.size();i++)
            {
                if((listaConvertidaHistoricContainerSalesEntity.get(j).getAnio()+listaConvertidaHistoricContainerSalesEntity.get(j).getMes()).equals(
                        listHistoricContainerSalesEntity.get(i).getAnio()+listHistoricContainerSalesEntity.get(i).getMes()))
                {
                    listHistoricContainerSalesEntity.get(i).setMontototal(
                            String.valueOf(Double.parseDouble(listHistoricContainerSalesEntity.get(i).getMontototal()) +
                                    Double.parseDouble(listaConvertidaHistoricContainerSalesEntity.get(j).getMontototal())));
                }
                else
                    {
                        historicContainerSalesEntity.mes=listHistoricContainerSalesEntity.get(i).getMes();
                        historicContainerSalesEntity.anio=listHistoricContainerSalesEntity.get(i).getAnio();
                        historicContainerSalesEntity.SlpCode=listHistoricContainerSalesEntity.get(i).getSlpCode();
                        historicContainerSalesEntity.company=listHistoricContainerSalesEntity.get(i).getCompany();
                        historicContainerSalesEntity.userid=listHistoricContainerSalesEntity.get(i).getUserid();
                        historicContainerSalesEntity.variable=listHistoricContainerSalesEntity.get(i).getVariable();
                        historicContainerSalesEntity.montototal=listHistoricContainerSalesEntity.get(i).getMontototal();
                        listaConvertidaHistoricContainerSalesEntity.add(historicContainerSalesEntity);
                    }
            }


        }
        Log.e("REOS", "Induvis-convertHistoricContainerSalesEntityTotalPeriod-listaConvertidaHistoricContainerSalesEntity: "+listaConvertidaHistoricContainerSalesEntity.size());
        return listaConvertidaHistoricContainerSalesEntity;
    }

    public static String changeMonth(String day, int param) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date dt = sdf.parse(day);
        Calendar rightNow = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.MONTH, param);
        Date date = rightNow.getTime();
        rightNow.add(Calendar.DAY_OF_MONTH,1);
        Date date2 = rightNow.getTime();
        return sdf.format(date2);
    }

    public static String changeMonthNow (String day, int param) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date dt = sdf.parse(day);
        Calendar rightNow = Calendar.getInstance();
        //Calendar calendar = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.MONTH, param);
        //Date date = rightNow.getTime();
        //rightNow.add(Calendar.DAY_OF_MONTH,1);
        Date date2 = rightNow.getTime();
        return sdf.format(date2);
    }
    public static int getMaximoDiasDeposito(){
        int dias=0;
        switch (BuildConfig.FLAVOR){
            case "bolivia":
            case "peru":
            case "paraguay":
            case "perurofalab":
            case "espania":
            case "marruecos":
                dias=0;
                break;
            case "ecuador":
            case "chile":
                dias=10;
                break;
        }
        return dias;
    }

    public static String getStatusDraft(){
        String draft="N";
        switch (BuildConfig.FLAVOR){
            case "peru":
            case "perurofalab":
            case "espania":
            case "marruecos":
                draft="N";
                break;
            case "ecuador":
            case "bolivia":
            case "chile":
            case "paraguay":
                if(SesionEntity.quotation.equals("Y"))
                {
                    draft="N";
                }else
                    {
                        draft="Y";
                    }

                break;
        }
        return draft;
    }

    public static String getAmountRouteeffectiveness(double TotalPedidoRutaVendedor,double TotalRutaVendedor){
        String efectividad="";
        Log.e("REOS","FormulasController.getAmountRouteeffectiveness.TotalPedidoRutaVendedor:" + TotalPedidoRutaVendedor);
        Log.e("REOS","FormulasController.getAmountRouteeffectiveness.TotalRutaVendedor:" + TotalRutaVendedor);
        //BigDecimal BigTotalPedidoRutaVendedor = new BigDecimal(TotalPedidoRutaVendedor);
        //BigDecimal BigTotalRutaVendedor = new BigDecimal(TotalRutaVendedor);
        //return ""+BigTotalPedidoRutaVendedor.divide (BigTotalRutaVendedor).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
        double resultado;
        resultado=(TotalPedidoRutaVendedor/TotalRutaVendedor)*100;
        Log.e("REOS","FormulasController.getAmountRouteeffectiveness.resultado:" + resultado);
        return String.valueOf(format.format(resultado))+" %";
    }
   static public void getCrashLytics(){
        FirebaseCrashlytics.getInstance().setUserId(SesionEntity.fuerzatrabajo_id);
        FirebaseCrashlytics.getInstance().setCustomKey("NameSalesForce",SesionEntity.nombrefuerzadetrabajo);
        FirebaseCrashlytics.getInstance().setCustomKey("Country", BuildConfig.FLAVOR);

            if (BuildConfig.DEBUG) {
                FirebaseCrashlytics.getInstance().setCustomKey("AppBuild", "Debug");
            } else {
                FirebaseCrashlytics.getInstance().setCustomKey("AppBuild", "Release");

            }

    }

    public long getDiferenceDays(String Date) throws ParseException {

        LocalDate myDate = LocalDate.parse(Date);

        LocalDate currentDate = LocalDate.now();

       long numberOFDays = DAYS.between(myDate, currentDate);
        Log.e("REOS","Induvis.getDiferenceDays.numberOFDays"+numberOFDays);
        return numberOFDays;

    }

    static public void refreshGlobalVariables(Context context)
    {
        Log.e("REOS","Induvis-refreshGlobalVariables-");
        SesionEntity Sesion= new SesionEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(context);
        UsuarioSQLiteEntity userEntity=usuarioSQLite.ObtenerUsuarioSesion();

        if(userEntity!=null) {
            Sesion.compania_id = userEntity.getCompania_id();
            Sesion.fuerzatrabajo_id = userEntity.getFuerzatrabajo_id();
            Sesion.nombrecompania = userEntity.getNombrecompania();
            Sesion.nombrefuerzadetrabajo = userEntity.getNombrefuerzatrabajo();
            Sesion.nombreusuario = userEntity.getNombreUsuario();
            Sesion.usuario_id = userEntity.getUsuario_id();
            Sesion.imei = userEntity.getImei();
            Sesion.recibo = userEntity.getRecibo();
            Sesion.almacen_id = userEntity.getAlmacen_id();
            Sesion.planta_id = userEntity.getPlanta();
            Sesion.perfil_id = userEntity.getPerfil();
            Sesion.cogsacct = userEntity.getCogsacct();
            Sesion.u_vist_ctaingdcto = userEntity.getU_vist_ctaingdcto();
            Sesion.documentsowner = userEntity.getDocumentsowner();
            Sesion.U_VIST_SUCUSU = userEntity.getU_VIST_SUCUSU();
            Sesion.CentroCosto = userEntity.getCentroCosto();
            Sesion.UnidadNegocio = userEntity.getUnidadNegocio();
            Sesion.LineaProduccion = userEntity.getLineaProduccion();
            Sesion.Impuesto_ID = userEntity.getImpuesto_ID();
            Sesion.Impuesto = userEntity.getImpuesto();
            Sesion.U_VIS_CashDscnt = userEntity.getU_VIS_CashDscnt();
            Sesion.FLAG_STOCK = userEntity.getFLAG_STOCK();
            Sesion.FLAG_BACKUP = userEntity.getFLAG_BACKUP();
            Sesion.rate = userEntity.getRate();
            Sesion.Print = userEntity.getPrint();
            Sesion.activecurrency = userEntity.getActivecurrency();
            Sesion.phone = userEntity.getPlanta();
            Sesion.maxDateDeposit = userEntity.getChkbloqueopago();
        }
    }

    public static String getDateShort(String dateBD){
        String dateView="",year,month,day;

        Log.e("REOS","Induvis-getDate-dateBD:"+dateBD);
        try {
            switch (BuildConfig.FLAVOR){
                case "bolivia":
                case "india":
                case "chile":
                case "ecuador":
                case "peru":
                case "paraguay":
                case "perurofalab":
                case "espania":
                case "marruecos":
                    year=dateBD.substring(0,4);
                    month=dateBD.substring(4,6);
                    day=dateBD.substring(6,8);
                    dateView=day+"/"+month;

                    break;
            }
        }catch (Exception e)
        {
            Log.e("REOS","Induvis-getDate-e:"+e);
        }
        Log.e("REOS","Induvis-getDate-dateView:"+dateView);
        return dateView;
    }
    public static ArrayList<String> getCurrency(){
        ArrayList<String> currency = new  ArrayList<String>();
        try {
            switch (BuildConfig.FLAVOR){
                case "bolivia":
                case "india":
                case "chile":
                case "ecuador":
                case "peru":
                case "paraguay":
                case "perurofalab":
                case "espania":
                    currency.add("EUR - Euro");
                    break;
                case "marruecos":
                    currency.add("MAD - Dírham marroquí");
                    break;
            }
        }catch (Exception e)
        {
            Log.e("REOS","Induvis-getCurrency-e:"+e);
        }
        Log.e("REOS","Induvis-getCurrency-currency:"+currency);
        return currency;
    }

    static SimpleDateFormat dateFormat;
    static Date datefecha;
    static String fecha;
    public static String ConvertdatefordateSAP(String date){
        String dateSAP="";
        dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        datefecha = new Date();
        fecha =dateFormat.format(datefecha);
        if(date!=null) {

            if (date.length() == 10) {
                String[] sourcedate = date.split("-");
                String año = sourcedate[0];
                String mes = sourcedate[1];
                String dia = sourcedate[2];
                dateSAP = año + mes + dia;

            } else {
                dateSAP= fecha;
            }
        }

        return dateSAP;

    }

}

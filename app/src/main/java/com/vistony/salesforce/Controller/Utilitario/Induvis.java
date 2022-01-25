package com.vistony.salesforce.Controller.Utilitario;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
//import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        }

        return information;
    }

    public static String getDate(String flavor,String dateBD){
        String dateView="",year,month,day;

        switch (flavor){
            case "bolivia":
            case "india":
            case "chile":
            case "ecuador":
            case "peru":
                year=dateBD.substring(0,4);
                month=dateBD.substring(4,6);
                day=dateBD.substring(6,8);
                dateView=year+"-"+month+"-"+day;

                break;
        }
        return dateView;
    }

    public static String getTime(String flavor,String timeBD){
        String timeView="",hour,minute,second;
        switch (flavor){
            case "chile":
            case "peru":
            case "bolivia":
            case "ecuador":
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
                impuesto=0.1494;
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
                impuesto="14.94";
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
                impuesto="IGV";
                break;
        }
        return impuesto;
    }

    public static String getTituloVentaString(){
        String tituloventa="";
        switch (BuildConfig.FLAVOR){

            case "peru":
                tituloventa="ORDEN VENTA";
                break;
            case ("ecuador"):
            case ("bolivia"):
            case "chile":
                if(SesionEntity.quotation.equals("Y"))
                 {
                     tituloventa="COTIZACIÓN";
                 }
                 else
                     {
                         tituloventa="ORDEN VENTA";
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
                draft="N";
                break;
            case "ecuador":
            case "bolivia":
            case "chile":
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

}
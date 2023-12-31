package com.vistony.salesforce.Controller.Utilitario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.graphics.Bitmap;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Entity.Adapters.ListKardexOfPaymentEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesAnalysisByRouteEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.KardexPagoEntity;
import com.vistony.salesforce.Entity.SesionEntity;

public class Convert {
//    static DecimalFormat format = new DecimalFormat("##.##.###,");

    public static String getTotaLine(String subTotal,String procentajeDsct,String impuesto) {

        subTotal=(subTotal.equals("0")||subTotal==null)?"0":subTotal;
        procentajeDsct=(procentajeDsct.equals("0")||procentajeDsct==null)?"0":procentajeDsct;
        impuesto=(impuesto.equals("0")||impuesto==null)?"0":impuesto;

        BigDecimal sbTotal=new BigDecimal(subTotal);
        BigDecimal dsct=new BigDecimal(procentajeDsct);
        BigDecimal impt=new BigDecimal(impuesto);

        impt=impt.divide(new BigDecimal("100"));
        dsct=dsct.divide(new BigDecimal("100"));

        sbTotal=sbTotal.subtract(sbTotal.multiply(dsct));
        impt=sbTotal.multiply(impt);


        return sbTotal.add(impt).setScale(3,RoundingMode.HALF_UP).toString();

    }

    public static String currencyForView(String amount){
        amount=amount.replace(" ","");
        String resultado="";
        if(amount==null)
        {
            amount="0";
        }
       if(amount.equals("")){
           amount="0";
       }
       Locale locale=null;
       BigDecimal amountRedonded=null;;
        switch (BuildConfig.FLAVOR){
            case "chile":
                amountRedonded =new BigDecimal(amount).setScale(0, RoundingMode.HALF_UP);
                locale=new Locale("ES","CL");
                break;
            case "ecuador":
                amountRedonded =new BigDecimal(amount).setScale(3, RoundingMode.HALF_UP);
                locale=new Locale("ES","EC");
                break;
            case "peru":
                amountRedonded =new BigDecimal(amount).setScale(3, RoundingMode.HALF_UP);
                locale=new Locale("ES","PE");
                break;
            case "bolivia":
                amountRedonded =new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
                locale=new Locale("ES","BO");
                break;
            case "paraguay":
                amountRedonded =new BigDecimal(amount).setScale(0, RoundingMode.HALF_UP);
                locale=new Locale("ES","PY");
                break;
            case "perurofalab":
                amountRedonded =new BigDecimal(amount).setScale(3, RoundingMode.HALF_UP);
                locale=new Locale("ES","PE");
                break;
            case "espania":
                amountRedonded =new BigDecimal(amount).setScale(3, RoundingMode.HALF_UP);
                locale=new Locale("ES","ES");
                break;
            case "marruecos":
                if(amount.equals("")){
                    amount="0";
                }
                locale=null;
                amountRedonded=new BigDecimal(amount).setScale(0, RoundingMode.HALF_UP);
                break;
        }

        //Log.e("REOS","Convert-currencyForView-amountRedonded-Antes"+amountRedonded.toString());
        switch (BuildConfig.FLAVOR){
            case "chile":
                NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
                nf.setMaximumFractionDigits(0);

                try {
                    resultado = nf.format(amountRedonded);
                }catch (Exception e)
                {
                    e.getMessage();
                }
                break;
            case "ecuador":
            case "peru":
                    if(SesionEntity.currency_id.equals("US$"))
                    {
                        //resultado= " US$ "+amountRedonded;
                        resultado= " US$ "+new BigDecimal(amount).setScale(2, RoundingMode.HALF_DOWN).toString();
                    }else {
                        resultado= NumberFormat.getCurrencyInstance(locale).format(amountRedonded);
                    }
                    break;
            case "bolivia":
            case "paraguay":
            case "perurofalab":
            case "espania":

                resultado= NumberFormat.getCurrencyInstance(locale).format(amountRedonded);
                break;
            case "marruecos":
                resultado=new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP).toString()+ " MAD";

                break;
        }
        //Log.e("REOS","Convert-currencyForView-amountRedonded-Despues"+amountRedonded.toString());
        return resultado;
    }

    public static double stringToDouble(String amount){

        amount=amount.replace(",",".");

        double amountx=Double.parseDouble(amount);
        return amountx;
    }

    public static String multiplicacion(String num1,String num2){
        num1=(num1.equals("0")||num1==null)?"0":num1;
        num2=(num2.equals("0")||num2==null)?"0":num2;

        BigDecimal numero1=new BigDecimal(num1);
        return numero1.multiply(new BigDecimal(num2)).setScale(3, RoundingMode.HALF_UP).toString();
    }

    public static String doubleToTextInteger(String amount){
        amount=amount.replace(",",".");
        double amountx=Double.parseDouble(amount);

        DecimalFormat format = new DecimalFormat("0.#");
        return format.format(amountx);
    }

    public static double amountForTwoDecimal(String amount) {
        final int numeroDecimales=2;
        double parteEntera, resultado;

        if(amount.equals("") && amount.length()==0){
            amount="0";
        }

        resultado= Double.parseDouble(amount);

        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }

    public static String numberForView(String amount){
        if(amount.equals("")){
            amount="0";
        }
        Locale locale=null;

        BigDecimal amountRedonded=new BigDecimal(amount).setScale(0, RoundingMode.HALF_UP);
        return amountRedonded.toString();
    }

    public static String numberForView2(String amount){
        if(amount.equals("")){
            amount="0";
        }
        Locale locale=null;
        BigDecimal amountRedonded=new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
        return amountRedonded.toString();
    }

    public static String numberForViewDecimals(String amount,int decimals){
        if(amount.equals("")){
            amount="0";
        }
        Locale locale=null;
        BigDecimal amountRedonded=new BigDecimal(amount).setScale(decimals, RoundingMode.HALF_UP);
        return amountRedonded.toString();
    }

    public static List<ListKardexOfPaymentEntity> getConvertListKardexOfPayment(List<KardexPagoEntity> ListKardexPagoEntity)
    {
        List<ListKardexOfPaymentEntity> ListKardexOfPaymentEntity=new ArrayList<>();
        ListKardexOfPaymentEntity listKardexOfPaymentEntity;
        for(int i=0;i<ListKardexPagoEntity.size();i++)
        {
            if(ListKardexOfPaymentEntity.isEmpty())
            {
                listKardexOfPaymentEntity=new ListKardexOfPaymentEntity();
                listKardexOfPaymentEntity.legalnumber=ListKardexPagoEntity.get(i).getNumAtCard();
                /*String fecha,año,mes,dia;
                String[] sourceemision= ListKardexPagoEntity.get(i).getTaxDate().split(" ");
                fecha= sourceemision[0];
                String[] sourcefechadesordenada= fecha.split("/");
                año=sourcefechadesordenada[2];
                mes=sourcefechadesordenada[0];
                dia=sourcefechadesordenada[1];*/
                //listKardexOfPaymentEntity.invoicedate=dia+"/"+mes+"/"+año;
                listKardexOfPaymentEntity.invoicedate=ListKardexPagoEntity.get(i).getTaxDate();
                /*String fecha2,año2,mes2,dia2;
                String[] sourceemision2= ListKardexPagoEntity.get(i).getDocDueDate().split(" ");
                fecha2= sourceemision2[0];
                String[] sourcefechadesordenada2= fecha2.split("/");
                año2=sourcefechadesordenada2[2];
                mes2=sourcefechadesordenada2[0];
                dia2=sourcefechadesordenada2[1];*/
                //listKardexOfPaymentEntity.duedate=dia2+"/"+mes2+"/"+año2;
                listKardexOfPaymentEntity.duedate=ListKardexPagoEntity.get(i).getDocDueDate();
                listKardexOfPaymentEntity.DocAmount=ListKardexPagoEntity.get(i).getDocTotal();
                listKardexOfPaymentEntity.balance=ListKardexPagoEntity.get(i).getsALDO();
                listKardexOfPaymentEntity.invoice=false;
                listKardexOfPaymentEntity.paymentterms =ListKardexPagoEntity.get(i).getPymntGroup();
                ListKardexOfPaymentEntity.add(listKardexOfPaymentEntity);
            }
            else
                {
                    int contador=0;
                    for(int j=0;j<ListKardexOfPaymentEntity.size();j++)
                    {
                        if(ListKardexOfPaymentEntity.get(j).getLegalnumber().equals(ListKardexPagoEntity.get(i).getNumAtCard()))
                        {
                            contador++;
                        }
                    }
                    if(contador==0)
                    {
                        listKardexOfPaymentEntity=new ListKardexOfPaymentEntity();
                        listKardexOfPaymentEntity.legalnumber=ListKardexPagoEntity.get(i).getNumAtCard();
                        /*String fecha,año,mes,dia;
                        String[] sourceemision= ListKardexPagoEntity.get(i).getTaxDate().split(" ");
                        fecha= sourceemision[0];
                        String[] sourcefechadesordenada= fecha.split("/");
                        año=sourcefechadesordenada[2];
                        mes=sourcefechadesordenada[0];
                        dia=sourcefechadesordenada[1];
                        listKardexOfPaymentEntity.invoicedate=dia+"/"+mes+"/"+año;*/
                        listKardexOfPaymentEntity.invoicedate=ListKardexPagoEntity.get(i).getTaxDate();
                        /*String fecha2,año2,mes2,dia2;
                        String[] sourceemision2= ListKardexPagoEntity.get(i).getDocDueDate().split(" ");
                        fecha2= sourceemision2[0];
                        String[] sourcefechadesordenada2= fecha2.split("/");
                        año2=sourcefechadesordenada2[2];
                        mes2=sourcefechadesordenada2[0];
                        dia2=sourcefechadesordenada2[1];
                        listKardexOfPaymentEntity.duedate=dia2+"/"+mes2+"/"+año2;*/
                        listKardexOfPaymentEntity.duedate=ListKardexPagoEntity.get(i).getDocDueDate();
                        listKardexOfPaymentEntity.DocAmount=ListKardexPagoEntity.get(i).getDocTotal();
                        listKardexOfPaymentEntity.balance=ListKardexPagoEntity.get(i).getsALDO();
                        listKardexOfPaymentEntity.invoice=false;
                        listKardexOfPaymentEntity.paymentterms =ListKardexPagoEntity.get(i).getPymntGroup();
                        ListKardexOfPaymentEntity.add(listKardexOfPaymentEntity);
                    }
                }
        }
        return ListKardexOfPaymentEntity;
    }

    public static void setMarginsView (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public static List<HistoricSalesAnalysisByRouteEntity> getConvertListHistoricSalesAnalysisByRoute(List<HistoricSalesAnalysisByRouteEntity> listHistoricSalesAnalysisByRouteEntity)
    {
        List<HistoricSalesAnalysisByRouteEntity> ListConvertHistoricSalesAnalysisByRouteEntity=new ArrayList<>();
        HistoricSalesAnalysisByRouteEntity historicSalesAnalysisByRouteEntity;
        ArrayList<String> commercialclass = new ArrayList<String>();

        for(int i=0;i<listHistoricSalesAnalysisByRouteEntity.size();i++)
        {
            commercialclass.add(listHistoricSalesAnalysisByRouteEntity.get(i).getClase_comercial());
        }
        HashSet<String> hashCommercialclass = new HashSet<String>(commercialclass);

        for (String strCommercialclass : hashCommercialclass)
        {
            Log.e("REOS","Convert-getConvertListHistoricSalesAnalysisByRoute-strCommercialclass-"+strCommercialclass);
        }
        for (String strCommercialclass : hashCommercialclass)
        {
            Log.e("REOS","Convert-getConvertListHistoricSalesAnalysisByRoute-hashCommercialclass-"+hashCommercialclass);
            float gal_anio_actual_period_actual=0,gal_anio_actual_pe=0,
                    gal_promedio_trimestre_anio_anterior=0,month_before_year_before=0
                    ,gal_anio_actual_period_actual_quote=0,gal_anio_actual_period_actual_advance=0
                    ,gal_promedio_semestre_anio_actual=0,gal_promedio_semestre_anio_anterior=0;
            historicSalesAnalysisByRouteEntity=new HistoricSalesAnalysisByRouteEntity();
            historicSalesAnalysisByRouteEntity.clase_comercial=strCommercialclass;
                for(int g=0;g<listHistoricSalesAnalysisByRouteEntity.size();g++)
                {

                    if(strCommercialclass.equals(listHistoricSalesAnalysisByRouteEntity.get(g).getClase_comercial()))
                    {

                        Log.e("REOS","Convert-if-getConvertListHistoricSalesAnalysisByRoute-listHistoricSalesAnalysisByRouteEntity.get(g).getClase_comercial():"+listHistoricSalesAnalysisByRouteEntity.get(g).getClase_comercial());
                        Log.e("REOS","Convert-if-getConvertListHistoricSalesAnalysisByRoute-listHistoricSalesAnalysisByRouteEntity.get(g).getCardname():"+listHistoricSalesAnalysisByRouteEntity.get(g).getCardname());
                        Log.e("REOS","Convert-if-getConvertListHistoricSalesAnalysisByRoute-listHistoricSalesAnalysisByRouteEntity.get(g).getPromediotrimestreanioactual():"+listHistoricSalesAnalysisByRouteEntity.get(g).getPromediotrimestreanioactual());
                        Log.e("REOS","Convert-if-getConvertListHistoricSalesAnalysisByRoute-listHistoricSalesAnalysisByRouteEntity.get(g).getPromediotrimestreanioanterior():"+listHistoricSalesAnalysisByRouteEntity.get(g).getPromediotrimestreanioanterior());
                        Log.e("REOS","Convert-if-getConvertListHistoricSalesAnalysisByRoute-consolidado:"+listHistoricSalesAnalysisByRouteEntity.get(g).getClase_comercial()+"-"+listHistoricSalesAnalysisByRouteEntity.get(g).getCardname()+"-"+listHistoricSalesAnalysisByRouteEntity.get(g).getPromediotrimestreanioactual());
                        Log.e("REOS","Convert-if-getConvertListHistoricSalesAnalysisByRoute-consolidado:"+listHistoricSalesAnalysisByRouteEntity.get(g).getClase_comercial()+"-"+listHistoricSalesAnalysisByRouteEntity.get(g).getCardname()+"-"+listHistoricSalesAnalysisByRouteEntity.get(g).getPromediotrimestreanioanterior());
                        //Comparativo Mes Actual vs Mes Año Anterior
                        gal_anio_actual_period_actual=gal_anio_actual_period_actual+Float.parseFloat(listHistoricSalesAnalysisByRouteEntity.get(g).getGalanioactualperiodoactual());
                        month_before_year_before=month_before_year_before+Float.parseFloat(listHistoricSalesAnalysisByRouteEntity.get(g).getGalanioanteriorperiodoactual());

                        //Comparativo Semestre Actual vs Semestre Anterior
                        gal_promedio_semestre_anio_actual=gal_promedio_semestre_anio_actual+Float.parseFloat(listHistoricSalesAnalysisByRouteEntity.get(g).getPromediotrimestreanioactual());
                        gal_promedio_semestre_anio_anterior=gal_promedio_semestre_anio_anterior+Float.parseFloat(listHistoricSalesAnalysisByRouteEntity.get(g).getPromediotrimestreanioanterior());

                        //Comparativo con Cuota de Clase Comercial
                        gal_anio_actual_period_actual_quote=Float.parseFloat(listHistoricSalesAnalysisByRouteEntity.get(g).getCuota());
                    }

                }
                //Comparativo Mes Actual vs Mes Año Anterior
            historicSalesAnalysisByRouteEntity.galanioactualperiodoactual = String.valueOf(gal_anio_actual_period_actual) ;
            historicSalesAnalysisByRouteEntity.galanioanteriorperiodoactual  = String.valueOf(month_before_year_before);
                    if(gal_anio_actual_period_actual>0&&month_before_year_before==0)
                    {
                        historicSalesAnalysisByRouteEntity.prom2122=String.valueOf("100");
                    }
                    else if(gal_anio_actual_period_actual==0&&month_before_year_before==0)
                    {
                        historicSalesAnalysisByRouteEntity.prom2122=String.valueOf("0");
                    }
                    else if(gal_anio_actual_period_actual==0&&month_before_year_before>0)
                    {
                        historicSalesAnalysisByRouteEntity.prom2122=String.valueOf("0");
                    }
                    else if(gal_anio_actual_period_actual<0||month_before_year_before<0)
                    {
                        historicSalesAnalysisByRouteEntity.prom2122=String.valueOf("0");
                    }
                    else {
                        historicSalesAnalysisByRouteEntity.prom2122=(String.valueOf((Float.parseFloat(historicSalesAnalysisByRouteEntity.galanioactualperiodoactual) / Float.parseFloat(historicSalesAnalysisByRouteEntity.galanioanteriorperiodoactual)) * 100));
                    }


            //Comparativo Semestre Actual vs Semestre Anterior

            historicSalesAnalysisByRouteEntity.promediotrimestreanioanterior = String.valueOf(gal_promedio_semestre_anio_anterior);
            historicSalesAnalysisByRouteEntity.promediotrimestreanioactual = String.valueOf(gal_promedio_semestre_anio_actual);
            Log.e("REOS","Convert-getConvertListHistoricSalesAnalysisByRoute-historicSalesAnalysisByRouteEntity.clase_comercial:"+historicSalesAnalysisByRouteEntity.clase_comercial);
            Log.e("REOS","Convert-getConvertListHistoricSalesAnalysisByRoute-historicSalesAnalysisByRouteEntity.promediotrimestreanioactual:"+historicSalesAnalysisByRouteEntity.promediotrimestreanioactual);
            Log.e("REOS","Convert-getConvertListHistoricSalesAnalysisByRoute-historicSalesAnalysisByRouteEntity.promediotrimestreanioanterior:"+historicSalesAnalysisByRouteEntity.promediotrimestreanioanterior);

            if(gal_promedio_semestre_anio_anterior>0&&gal_promedio_semestre_anio_actual==0)
            {
                historicSalesAnalysisByRouteEntity.Prom=String.valueOf("0");
            }
            else if(gal_promedio_semestre_anio_anterior==0&&gal_promedio_semestre_anio_actual==0)
            {
                historicSalesAnalysisByRouteEntity.Prom=String.valueOf("0");
            }
            else if(gal_promedio_semestre_anio_anterior==0&&gal_promedio_semestre_anio_actual>0)
            {
                historicSalesAnalysisByRouteEntity.Prom=String.valueOf("100");
            }
            else if(gal_promedio_semestre_anio_anterior<0||gal_promedio_semestre_anio_actual<0)
            {
                historicSalesAnalysisByRouteEntity.Prom=String.valueOf("100");
            }
            else {
                Log.e("REOS","Convert-getConvertListHistoricSalesAnalysisByRoute-historicSalesAnalysisByRouteEntity.promediotrimestreanioactual:"+historicSalesAnalysisByRouteEntity.promediotrimestreanioactual);
                Log.e("REOS","Convert-getConvertListHistoricSalesAnalysisByRoute-historicSalesAnalysisByRouteEntity.promediotrimestreanioactual:"+historicSalesAnalysisByRouteEntity.promediotrimestreanioactual);
                historicSalesAnalysisByRouteEntity.Prom=(String.valueOf((Float.parseFloat(historicSalesAnalysisByRouteEntity.promediotrimestreanioactual) / Float.parseFloat(historicSalesAnalysisByRouteEntity.promediotrimestreanioanterior)) * 100));
            }
            //historicSalesAnalysisByRouteEntity.Prom=String.valueOf(gal_promedio_semestre_anio_actual/gal_promedio_trimestre_anio_anterior);

            //Comparativo con Cuota de Clase Comercial
            historicSalesAnalysisByRouteEntity.cuota = String.valueOf(gal_anio_actual_period_actual_quote);
            //historicSalesAnalysisByRouteEntity.cuota = listHistoricSalesAnalysisByRouteEntity.get(g).getCuota();
            if(gal_anio_actual_period_actual==0||gal_anio_actual_period_actual_quote==0)
            {
                historicSalesAnalysisByRouteEntity.porcentajeavancecuota=String.valueOf("0");
            }
            else {
                historicSalesAnalysisByRouteEntity.porcentajeavancecuota  = String.valueOf((gal_anio_actual_period_actual/gal_anio_actual_period_actual_quote)*100);
            }
            Log.e("REOS","Convert-getConvertListHistoricSalesAnalysisByRoute-historicSalesAnalysisByRouteEntity.cuota:"+historicSalesAnalysisByRouteEntity.cuota);
            Log.e("REOS","Convert-getConvertListHistoricSalesAnalysisByRoute-historicSalesAnalysisByRouteEntity.porcentajeavancecuota:"+historicSalesAnalysisByRouteEntity.porcentajeavancecuota);


            ListConvertHistoricSalesAnalysisByRouteEntity.add(historicSalesAnalysisByRouteEntity);
        }
        return ListConvertHistoricSalesAnalysisByRouteEntity;
    }

    static public void resizeImage(ImageView imageView, Bitmap bitMap, Activity activity)
    {
        try {
            int currentBitmapWidth = bitMap.getWidth();
            int currentBitmapHeight = bitMap.getHeight();
            int ivWidth = imageView.getWidth();
            int ivHeight = imageView.getHeight();
            int newWidth = ivWidth;
            int newHeight = (int) Math.floor((double) currentBitmapHeight * ((double) newWidth / (double) currentBitmapWidth));
            Bitmap newbitMap = Bitmap.createScaledBitmap(bitMap, newWidth, newHeight, true);
            imageView.setImageBitmap(newbitMap);
        }catch (Exception e)
        {
            Toast.makeText(activity, "No se pudo redimensionar la imagen- error: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public static String convertGalatliter(String gallon,String quantity){
        if(gallon.equals("")){
            gallon="0";
        }
        if(quantity.equals("")){
            quantity="0";
        }
        BigDecimal calcliter=new BigDecimal("3.78");
        BigDecimal quantityProduc=new BigDecimal(quantity);
        BigDecimal amountRedonded=new BigDecimal(gallon).multiply(calcliter).multiply(quantityProduc).setScale(2, RoundingMode.HALF_UP);
        return amountRedonded.toString();
    }
    public static String convertSIGAUS(String literAcum,String price){
        if(price.equals("")){
            price="0";
        }
        if(literAcum.equals("")){
            literAcum="0";
        }
        BigDecimal priceSigaus=new BigDecimal(price);
        BigDecimal amountRedonded=new BigDecimal(literAcum).multiply(priceSigaus).setScale(2, RoundingMode.HALF_UP);
        return amountRedonded.toString();
    }

    public static String convertLiterAcum(String liter,String quantity){
        if(liter.equals("")){
            liter="0";
        }
        if(quantity.equals("")){
            quantity="0";
        }
        BigDecimal quantityProduc=new BigDecimal(quantity);
        BigDecimal amountRedonded=new BigDecimal(liter).multiply(quantityProduc).setScale(2, RoundingMode.HALF_UP);
        return amountRedonded.toString();
    }

    public static boolean  getStatusEditPrice(String val_margin,String val_dinamic){

        boolean status=false;
        val_margin=val_margin.replace(" ","");
        val_dinamic=val_dinamic.replace(" ","");
        BigDecimal val_margin_=new BigDecimal(val_margin);
        BigDecimal val_dinamic_=new BigDecimal(val_dinamic);
        int comparisonResult = val_dinamic_.compareTo(val_margin_);
        if (comparisonResult < 0) {
            System.out.println("price_edit_ es menor que price_listprice_");
            status=false;
        } else if (comparisonResult > 0) {
            status=true;
            System.out.println("price_listprice_ es menor que price_edit_");
        } else {
            status=true;
            System.out.println("Ambos valores son iguales");
        }
        return status;
    }

    public static double getPercentEditPrice(String price_listprice,String price_edit){
        price_listprice=price_listprice.replace(" ","");
        price_edit=price_edit.replace(" ","");
        BigDecimal price_listprice_=new BigDecimal(price_listprice);
        BigDecimal price_edit_=new BigDecimal(price_edit);
        BigDecimal absoluteDifference = price_listprice_.subtract(price_edit_).abs();
        double percentageDifference = (absoluteDifference.doubleValue() / price_listprice_.doubleValue()) * 100.0;
        return percentageDifference;
    }

    public static Integer getScale(){
        Integer Scale=0;
        switch (BuildConfig.FLAVOR){
            case "chile":
                break;
            case "ecuador":
                Scale=6;
                break;
            case "peru":
                Scale=2;
                break;
            case "bolivia":
                Scale=5;
                break;
            case "paraguay":
                Scale=5;
                break;
            case "perurofalab":
                break;
            case "espania":
                break;
            case "marruecos":
                break;
        }
        return Scale;
    }

    public static String getDiscountPercentage(String price1, String price2) {
        try {
            price1 = price1.replace(" ", "");
            price2 = price2.replace(" ", "");
            BigDecimal price1Value = new BigDecimal(price1);
            BigDecimal price2Value = new BigDecimal(price2);

            // Verifica si el Precio 2 es menor que el Precio 1 y evita la división por cero.
            if (price2Value.compareTo(price1Value) < 0) {
                BigDecimal discount = price1Value.subtract(price2Value);
                double discountPercentage = (discount.doubleValue() / price1Value.doubleValue()) * 100.0;

                // Redondea el resultado a tres decimales.
                DecimalFormat df = new DecimalFormat("0.000");
                String formattedDiscount = df.format(discountPercentage);

                return formattedDiscount;
            } else {
                // Si el Precio 2 no es menor que el Precio 1, devuelve "0.000".
                return "0.000";
            }
        } catch (NumberFormatException e) {
            // Maneja el error de conversión de cadenas a BigDecimal.
            System.err.println("Error en la conversión de cadenas a números: " + e.getMessage());
            return "0.000"; // Puedes devolver un valor predeterminado o lanzar una excepción personalizada según tus necesidades.
        }
    }
}

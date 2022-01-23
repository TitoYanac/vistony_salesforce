package com.vistony.salesforce.Controller.Utilitario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.util.Log;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Entity.Adapters.ListKardexOfPaymentEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.KardexPagoEntity;

public class Convert {

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
       if(amount.equals("")){
           amount="0";
       }
       Locale locale=null;
       BigDecimal amountRedonded=null;;
        switch (BuildConfig.FLAVOR){
            case "chile":
                amountRedonded =new BigDecimal(amount).setScale(0, RoundingMode.HALF_UP);
                locale=new Locale("ES","EC");
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
                amountRedonded =new BigDecimal(amount).setScale(3, RoundingMode.HALF_UP);
                locale=new Locale("ES","BO");
            break;
        }
        return NumberFormat.getCurrencyInstance(locale).format(amountRedonded);
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
                String fecha,año,mes,dia;
                String[] sourceemision= ListKardexPagoEntity.get(i).getTaxDate().split(" ");
                fecha= sourceemision[0];
                String[] sourcefechadesordenada= fecha.split("/");
                año=sourcefechadesordenada[2];
                mes=sourcefechadesordenada[0];
                dia=sourcefechadesordenada[1];
                listKardexOfPaymentEntity.invoicedate=dia+"/"+mes+"/"+año;
                String fecha2,año2,mes2,dia2;
                String[] sourceemision2= ListKardexPagoEntity.get(i).getDocDueDate().split(" ");
                fecha2= sourceemision2[0];
                String[] sourcefechadesordenada2= fecha2.split("/");
                año2=sourcefechadesordenada2[2];
                mes2=sourcefechadesordenada2[0];
                dia2=sourcefechadesordenada2[1];
                listKardexOfPaymentEntity.duedate=dia2+"/"+mes2+"/"+año2;
                listKardexOfPaymentEntity.DocAmount=ListKardexPagoEntity.get(i).getDocTotal();
                listKardexOfPaymentEntity.balance=ListKardexPagoEntity.get(i).getsALDO();
                listKardexOfPaymentEntity.invoice=false;
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
                        String fecha,año,mes,dia;
                        String[] sourceemision= ListKardexPagoEntity.get(i).getTaxDate().split(" ");
                        fecha= sourceemision[0];
                        String[] sourcefechadesordenada= fecha.split("/");
                        año=sourcefechadesordenada[2];
                        mes=sourcefechadesordenada[0];
                        dia=sourcefechadesordenada[1];
                        listKardexOfPaymentEntity.invoicedate=dia+"/"+mes+"/"+año;
                        String fecha2,año2,mes2,dia2;
                        String[] sourceemision2= ListKardexPagoEntity.get(i).getDocDueDate().split(" ");
                        fecha2= sourceemision2[0];
                        String[] sourcefechadesordenada2= fecha2.split("/");
                        año2=sourcefechadesordenada2[2];
                        mes2=sourcefechadesordenada2[0];
                        dia2=sourcefechadesordenada2[1];
                        listKardexOfPaymentEntity.duedate=dia2+"/"+mes2+"/"+año2;
                        listKardexOfPaymentEntity.DocAmount=ListKardexPagoEntity.get(i).getDocTotal();
                        listKardexOfPaymentEntity.balance=ListKardexPagoEntity.get(i).getsALDO();
                        listKardexOfPaymentEntity.invoice=false;
                        ListKardexOfPaymentEntity.add(listKardexOfPaymentEntity);
                    }
                }
        }
        return ListKardexOfPaymentEntity;
    }
}

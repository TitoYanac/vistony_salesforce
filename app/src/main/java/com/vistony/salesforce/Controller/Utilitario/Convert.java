package com.vistony.salesforce.Controller.Utilitario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;

public class Convert {

    public static String currencyForView(String amount){
       if(amount.equals("")){
           amount="0";
       }

       BigDecimal amountRedonded=new BigDecimal(amount).setScale(3, RoundingMode.HALF_UP);
        return NumberFormat.getCurrencyInstance(new Locale("ES","CL")).format(amountRedonded);
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
}

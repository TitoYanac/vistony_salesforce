package com.vistony.salesforce.Controller.Utilitario;

import java.util.Locale;

import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;

public class Convert {

    public static String currencyForView(double amount){
       // return NumberFormat.getCurrencyInstance(new Locale(Locale.getDefault().getDisplayLanguage(),Locale.getDefault().getDisplayCountry())).format(amount);
        return NumberFormat.getCurrencyInstance(new Locale("ES","CL")).format(amount);
    }

    public static double stringToDouble(String amount){
        amount=amount.replace(",",".");
        double amountx=Double.parseDouble(amount);
        return amountx;
    }

    public static String doubleToTextInteger(String amount){
        amount=amount.replace(",",".");
        double amountx=Double.parseDouble(amount);

        DecimalFormat format = new DecimalFormat("0.#");
        return format.format(amountx);
    }

    public static double amountForTwoDecimal(double amount) {

        final int numeroDecimales=2;
        double parteEntera, resultado= amount;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
}

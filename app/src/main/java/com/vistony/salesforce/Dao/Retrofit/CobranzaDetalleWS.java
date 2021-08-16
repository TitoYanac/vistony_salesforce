package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Utilitario;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class CobranzaDetalleWS {
    private Context context;
    private String resultado="0";

    public CobranzaDetalleWS(final Context context){
        this.context=context;
    }

    public String PostCobranzaDetalleWS(
            String Imei,
            String TipoCrud,
            String Compania_ID,
            String Banco_ID,
            String TipoIngreso,
            String Deposito_ID,
            String Detalle_Item,
            String Cliente_ID,
            String Documento_ID,
            String ImporteDocumento,
            String SaldoDocumento,
            String MontoCobrado,
            String NuevoSaldoDocumento,
            String FechaCobranza,
            String Recibo,
            String Estado,
            String Comentario,
            String Usuario_ID,
            String Fuerzatrabajo_ID,
            String Bancarizacion,
            String EstadoQR,
            String MotivoAnulacion,
            String DepositoDirecto,
            String PagoPOS
    ){
        Api api = Config.getClient().create(Api.class);

        HashMap<String, String> params = new HashMap<>();

        params.put("Imei", Imei);
        params.put("Type", TipoCrud);
        params.put("BankID", Banco_ID);
        params.put("IncomeType", "0");
        params.put("DepositID", Deposito_ID);
        params.put("ItemDatail", Detalle_Item);
        params.put("CardCode", Cliente_ID);
        params.put("DocNum", Documento_ID);
        params.put("DocTotal", ImporteDocumento);
        params.put("Balance", SaldoDocumento);
        params.put("AmountCharged", MontoCobrado);
        params.put("NewBalance", NuevoSaldoDocumento);
        params.put("IncomeDate", FechaCobranza);
        params.put("Receip", Recibo);
        params.put("Status", Estado);
        params.put("Commentary", Comentario);
        params.put("Banking", Bancarizacion);
        params.put("QRStatus", EstadoQR);
        params.put("DirectDeposit", DepositoDirecto);
        params.put("POSPay", PagoPOS);

        if(Utilitario.validationNotNull(params)){
            Call call = api.sendCollection("https://graph.vistony.pe/CobranzaD",params);
            try{
                Response response= call.execute();
                if(response.isSuccessful()) {
                    resultado = "1";
                }
            }catch (Exception e){
                e.printStackTrace();
                resultado="0";
            }
        }else{
            Log.e("ERROR DE ENVIO","LA COBRANZA DE "+Cliente_ID+" CON EL RECIBO "+Recibo+" EL DIA "+FechaCobranza+" DEL DOCUMENTO "+Documento_ID);
        }
        return resultado;
    }
}

package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Controller.Utilitario.Utilitario;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;
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

        params.put("ItemDetail","1");
        params.put("CardCode", "C10482169703");
        params.put("DocEntryInvoice", "48198");
        params.put("DocNum", "214015676");
        params.put("DocTotal", "66690.06");
        params.put("Balance", "62590.06");
        params.put("AmountCharged", "100");
        params.put("NewBalance", "62490.06");
        params.put("IncomeDate", "20210816");
        params.put("Receip", "1");
        params.put("Status","P");
        params.put("Commentary", "Comentario de prueba hardCodeado");
        params.put("Banking", "N");
        params.put("QRStatus", "N");
        params.put("U_VIS_UserID", "200");
        params.put("U_VIS_SlpCode", "3");
        params.put("UserID", "200");
        params.put("SlpCode", "3");
        params.put("DirectDeposit", "N");
        params.put("POSPay","N");

        if(Utilitario.validationNotNull(params)){

            RequestBody json = null;

            json = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(params)).toString());


            Call call = api.sendCollection(json);
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

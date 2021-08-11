package com.vistony.salesforce.Dao.Network.XML;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdenVentaWSDao extends ViewModel {
    /*
    public  String[]  RegistrarOrdenVentaWS(String JSON){

        String [] Lista=new String[]{"0","0", "0","0"};
        SoapObject rpc = new SoapObject("http://localhost:6340/WebServiceGrupoPana.asmx","RegistrarOrdenDeVenta");
        rpc.addProperty("JsonOrders", JSON);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.encodingStyle = SoapSerializationEnvelope.XSD;
        HttpTransportSE androidHttpTransport = null;
        try{

            String conexion = "http://169.47.196.209/WS_SINCOV_TESTNEW/WebServiceOVSincronizacion.asmx";
            androidHttpTransport = new HttpTransportSE(conexion);
            androidHttpTransport.debug = true;
            androidHttpTransport.call("http://localhost:6340/WebServiceGrupoPana.asmx/RegistrarOrdenDeVenta",envelope);

            SoapObject result = (SoapObject) envelope.getResponse();
            //int totalCount = 0;
            //totalCount=result.getPropertyCount();
            //for (int detailCount = 0; detailCount < totalCount; detailCount++)
            //{
              //  SoapObject pojoSoap =result;

                Lista[0] = result.getProperty(0).toString(); Log.e("OV_LOG","=>"+result.getProperty(0));  //1
                Lista[1] = result.getProperty(1).toString(); Log.e("OV_LOG","=>"+result.getProperty(1)); //Orden de Venta registrada con éxito.
                Lista[2] = result.getProperty(2).toString(); Log.e("OV_LOG","=>"+result.getProperty(2)); //118713
                Lista[3] = result.getProperty(3).toString(); Log.e("OV_LOG","=>"+result.getProperty(3)); //201200007
            //}
        }
        catch (Exception e){

            System.out.println(e.getMessage());
            Lista=new String[]{"0","0", "0","0"};
        }
        return Lista;
    }
*/
    public MutableLiveData<String []> sendSalesOrder(final String jsonOv) {
        MutableLiveData<String []> temp=new MutableLiveData<String []>();
        String [] Lista=new String[]{"1","Orden de Venta registrada con éxito.", "118713","201200007"};
        //Map<String, String>

        try {
            RequestBody json = null;

            json = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonOv)).toString());

            Config.getClient().create(Api.class).sendOrder("https://webhook.site/882ca129-ccf3-4a9a-8e53-05a43b4b8d30",json).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if(response.isSuccessful()){
                        temp.setValue(Lista);
                    }else{
                        temp.setValue(null);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    temp.setValue(null);
                }
            });


        } catch (JSONException e) {
            temp.setValue(null);
            e.printStackTrace();
        }
        return temp;
    }

}

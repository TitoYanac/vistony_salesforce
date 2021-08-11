package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLIte.DocumentoDeudaSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.DocumentoDeudaEntityResponse;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class DocumentoDeudaWS {
    private DocumentoDeudaSQLiteDao documentoDeudaSQLiteDao;
    private ArrayList<DocumentoDeudaSQLiteEntity> LDDeuda = new ArrayList<>();
    private Context context;

    public DocumentoDeudaWS(final Context context){
        this.context=context;
    }

    public ArrayList<DocumentoDeudaSQLiteEntity> getDocumentoDeuda(String Imei){
        Api api = Config.getClient().create(Api.class);
        documentoDeudaSQLiteDao = new DocumentoDeudaSQLiteDao(context);

        Call<DocumentoDeudaEntityResponse> call = api.getDocumentoDeuda("https://graph.vistony.pe/DocumentoDeuda?imei="+Imei);
        try
        {
            Response<DocumentoDeudaEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                    DocumentoDeudaEntityResponse documentoDeudaEntityResponse=response.body();
                    for(int i=0;i<documentoDeudaEntityResponse.getDocumentoDeuda().size();i++){
                        DocumentoDeudaSQLiteEntity ObjDDeuda = new DocumentoDeudaSQLiteEntity();
                        ObjDDeuda.cliente_id = documentoDeudaEntityResponse.getDocumentoDeuda().get(i).getClienteId();
                        ObjDDeuda.documento_id = documentoDeudaEntityResponse.getDocumentoDeuda().get(i).getDocumentoId();
                        ObjDDeuda.domembarque_id   = documentoDeudaEntityResponse.getDocumentoDeuda().get(i).getDomicilioEmbarqueId();
                        ObjDDeuda.fechaemision = documentoDeudaEntityResponse.getDocumentoDeuda().get(i).getFechaEmision();
                        ObjDDeuda.fechavencimiento = documentoDeudaEntityResponse.getDocumentoDeuda().get(i).getFechaVencimiento();
                        ObjDDeuda.importefactura = documentoDeudaEntityResponse.getDocumentoDeuda().get(i).getImporteFactura();
                        ObjDDeuda.moneda = documentoDeudaEntityResponse.getDocumentoDeuda().get(i).getMoneda();
                        ObjDDeuda.nrofactura = documentoDeudaEntityResponse.getDocumentoDeuda().get(i).getNroFactura();
                        ObjDDeuda.saldo = documentoDeudaEntityResponse.getDocumentoDeuda().get(i).getSaldo();
                        ObjDDeuda.saldo_sin_procesar =  documentoDeudaEntityResponse.getDocumentoDeuda().get(i).getSaldoSinProcesar();
                        ObjDDeuda.fuerzatrabajo_id=SesionEntity.fuerzatrabajo_id;
                        ObjDDeuda.compania_id=SesionEntity.compania_id;
                        LDDeuda.add(ObjDDeuda);
                    }
                }

        }catch (Exception e){
            e.printStackTrace();
        }

        return LDDeuda;
    }
}

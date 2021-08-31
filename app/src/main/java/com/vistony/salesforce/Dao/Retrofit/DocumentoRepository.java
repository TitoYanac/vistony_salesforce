package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.DireccionSQLite;
import com.vistony.salesforce.Dao.SQLite.DocumentoSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaDireccionClienteEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.AddressEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.InvoicesEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.DocumentoDeudaEntityResponse;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DocumentoRepository extends ViewModel {
    private DocumentoSQLite documentoSQLite;
    private MutableLiveData<ArrayList<DocumentoDeudaSQLiteEntity>> LDDeuda= new MutableLiveData<>();

    /*
    public ArrayList<DocumentoDeudaSQLiteEntity> getDocumentoDeuda(Context context,String Imei){
        Api api = Config.getClient().create(Api.class);
        documentoSQLite = new DocumentoSQLite(context);

        Call<DocumentoDeudaEntityResponse> call = api.getDocumentoDeuda("https://graph.vistony.pe/DocumentoDeuda?imei="+Imei);
        try
        {
            Response<DocumentoDeudaEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                    DocumentoDeudaEntityResponse documentoDeudaEntityResponse=response.body();
                    for(int i=0;i<documentoDeudaEntityResponse.getDocumentoDeuda().size();i++){
                        DocumentoDeudaSQLiteEntity ObjDDeuda = new DocumentoDeudaSQLiteEntity();
                        //ObjDDeuda.cliente_id = documentoDeudaEntityResponse.getDocumentoDeuda().get(i).getClienteId();
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
    }*/

    public void addInvoices(Context context, List<InvoicesEntity> Lista, String companiCode, String customerCode) {
        if(documentoSQLite==null){
            documentoSQLite = new DocumentoSQLite(context);
        }

        for (int i = 0; i < Lista.size(); i++) {
            documentoSQLite.InsertaDocumentoDeuda(
                    Lista.get(i).getDocumentoId(),
                    Lista.get(i).getDomicilioEmbarqueId(),
                    companiCode,
                    customerCode,
                    SesionEntity.fuerzatrabajo_id,
                    Lista.get(i).getFechaEmision(),
                    Lista.get(i).getFechaVencimiento(),
                    Lista.get(i).getNroFactura(),
                    Lista.get(i).getMoneda(),
                    Lista.get(i).getImporteFactura(),
                    Lista.get(i).getSaldo(),
                    Lista.get(i).getSaldoSinProcesar()
            );
        }
    }
}

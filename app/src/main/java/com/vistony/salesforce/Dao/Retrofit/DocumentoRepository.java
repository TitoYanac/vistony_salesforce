package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Dao.SQLite.DocumentoSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.InvoicesEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

public class DocumentoRepository extends ViewModel {
    private DocumentoSQLite documentoSQLite;
    private MutableLiveData<ArrayList<DocumentoDeudaSQLiteEntity>> LDDeuda= new MutableLiveData<>();


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
                    Lista.get(i).getSaldoSinProcesar(),
                    Lista.get(i).getDocEntry()
            );
        }
    }
}

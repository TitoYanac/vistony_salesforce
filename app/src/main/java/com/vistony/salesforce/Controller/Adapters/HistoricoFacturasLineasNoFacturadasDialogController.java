package com.vistony.salesforce.Controller.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;

public class HistoricoFacturasLineasNoFacturadasDialogController extends DialogFragment  {
    private FragmentManager fragmentManager;
    ArrayList<CobranzaDetalleSQLiteEntity> arrayListCobranzaDetalleSQLiteEntity;
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;
    String reswsanularec="";
    int resanularec=0;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_historico_facturas_factura_no_facturadas,null);
        TextView textTitle = view.findViewById(R.id.tv_titulo);
        ListView listviewhistoricofacturaslineasnofacturadas= view.findViewById(R.id.lv_historico_factura_lineas_no_facturadas);


        return builder.create();
    }
    public int boolToInt(boolean b)
    {
        return b ? 1 : 0;
    }
}

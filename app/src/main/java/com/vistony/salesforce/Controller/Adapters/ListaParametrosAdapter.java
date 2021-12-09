package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.ParametrosView;

import java.util.ArrayList;
import java.util.List;

public class ListaParametrosAdapter  extends ArrayAdapter<ListaParametrosEntity> {
    public static List<ListaParametrosEntity> ArraylistaParametrosEntity = new ArrayList <ListaParametrosEntity>();;
    ListaParametrosEntity listaParametrosEntity;
    private Context context;


    public ListaParametrosAdapter(Context context, List<ListaParametrosEntity> objects) {

        super(context, 0, objects);
        ArraylistaParametrosEntity=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ArraylistaParametrosEntity= new ArrayList <ListaParametrosEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaParametrosAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_parametros,
                    parent,
                    false);

            holder = new ListaParametrosAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.chkparametro = (CheckBox) convertView.findViewById(R.id.chk_cobranzaconciliada);
            holder.tv_nombreparametro = (TextView) convertView.findViewById(R.id.tv_nombreparametro);
            holder.tv_cantidad = (TextView) convertView.findViewById(R.id.tv_cantidad);
            holder.tv_fechacarga = (TextView) convertView.findViewById(R.id.tv_fechacarga);
            holder.relativeLayout=convertView.findViewById(R.id.layoutParameter);
            convertView.setTag(holder);
        } else {
            holder = (ListaParametrosAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaParametrosEntity lead = getItem(position);

        // Setup.
        holder.chkparametro.setChecked(lead.isChkparametro());
        holder.tv_nombreparametro.setText(lead.getNombreparametro());
        holder.tv_cantidad.setText(lead.getCantidad());
        holder.tv_fechacarga.setText(lead.getFechacarga());

        holder.chkparametro.setOnClickListener(v -> {
        //holder.relativeLayout.setOnClickListener(v -> {

                boolean estado=holder.chkparametro.isChecked();

                /*if(estado){
                    holder.chkparametro.setChecked(false);
                }else{
                    holder.chkparametro.setChecked(true);
                }*/

                lead.chkparametro=estado;

                for(int i=0;i<ArraylistaParametrosEntity.size();i++)
                {
                    if(position==i)
                    {
                        ArraylistaParametrosEntity.get(i).setChkparametro(estado);
                    }
                }
        }

        );



        return convertView;
    }

    static class ViewHolder {

        CheckBox chkparametro;
        TextView tv_nombreparametro;
        TextView tv_cantidad;
        TextView tv_fechacarga;
        RelativeLayout relativeLayout;
    }

    public Object ObtenerListaParametros()
    {
        //ArraylistaConsDepositoEntity.size();
        List<ListaParametrosEntity> ObjetoListaConsDepositoEntity = new ArrayList<ListaParametrosEntity>();
        ObjetoListaConsDepositoEntity=ArraylistaParametrosEntity;

        /*for(int i=0;i<ArraylistaConsDepositoEntity.size();i++)
        {
            if(ArraylistaConsDepositoEntity.get(i).isCheckbox()==true)
            {
                ListaConsDepositoEntity listaConsDepositoEntity = new ListaConsDepositoEntity();
                listaConsDepositoEntity.cliente_id=ArraylistaConsDepositoEntity.get(i).getCliente_id();
                listaConsDepositoEntity.nombrecliente=ArraylistaConsDepositoEntity.get(i).getNombrecliente();
                listaConsDepositoEntity.recibo=ArraylistaConsDepositoEntity.get(i).getRecibo();
                listaConsDepositoEntity.documento_id=ArraylistaConsDepositoEntity.get(i).getDocumento_id();
                listaConsDepositoEntity.nrodocumento=ArraylistaConsDepositoEntity.get(i).getNrodocumento();
                listaConsDepositoEntity.fechacobranza=ArraylistaConsDepositoEntity.get(i).getFechacobranza();
                listaConsDepositoEntity.importe=ArraylistaConsDepositoEntity.get(i).getImporte();
                listaConsDepositoEntity.saldo=ArraylistaConsDepositoEntity.get(i).getSaldo();
                listaConsDepositoEntity.cobrado=ArraylistaConsDepositoEntity.get(i).getCobrado();
                listaConsDepositoEntity.nuevosaldo=ArraylistaConsDepositoEntity.get(i).getNuevosaldo();
                listaConsDepositoEntity.checkbox=ArraylistaConsDepositoEntity.get(i).isCheckbox();
                listaConsDepositoEntity.tv_txtbancarizado=ArraylistaConsDepositoEntity.get(i).getTv_txtbancarizado();
                ObjetoListaConsDepositoEntity.add(listaConsDepositoEntity);
            }
        }*/

        return ObjetoListaConsDepositoEntity;
    }


}

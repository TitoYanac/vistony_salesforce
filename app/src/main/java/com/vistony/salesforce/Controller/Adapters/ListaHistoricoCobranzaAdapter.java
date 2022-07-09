package com.vistony.salesforce.Controller.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.HistoricoCobranzaView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaHistoricoCobranzaAdapter  extends ArrayAdapter<ListaHistoricoCobranzaEntity> {
    public static ArrayList<ListaHistoricoCobranzaEntity> ArraylistaHistoricoCobranzaEntity;
    ListaCobranzaDetalleAdapter listaCobranzaDetalleAdapter;
    ClienteSQlite clienteSQlite;
    ArrayList<ClienteSQLiteEntity> arrayListClienteSQLiteEntity;
    String Recibo;
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;
    ArrayList<CobranzaDetalleSQLiteEntity> arrayListCobranzaDetalleSQLiteEntity;

    String reswsanularec = "";
    int resanularec = 0;
    private Context Context;
    private List<ListaHistoricoCobranzaEntity> Listanombres = null;
    LayoutInflater inflater;
    private ArrayList<ListaHistoricoCobranzaEntity> arrayList;
    FragmentManager fragmentManager;
    HistoricoCobranzaView historicoCobranzaView;
    public static String recibo;
    boolean[] itemChecked;
    public ListaHistoricoCobranzaAdapter(Context context, List<ListaHistoricoCobranzaEntity> objects) {

        super(context, 0, objects);
        Context = context;
        this.Listanombres = objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList = new ArrayList<ListaHistoricoCobranzaEntity>();
        if (objects != null) {
            this.arrayList.addAll(objects);
        }
        this.itemChecked=new boolean[objects.size()];


    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Listanombres.clear();
        if (charText.length() == 0) {
            Listanombres.addAll(arrayList);
        } else {
            for (ListaHistoricoCobranzaEntity wp : arrayList) {
                if (wp.getCliente_nombre().toLowerCase(Locale.getDefault()).contains(charText)) {
                    Listanombres.add(wp);
                }
            }
        }
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return Listanombres.size();
    }

    @Override
    public ListaHistoricoCobranzaEntity getItem(int position) {
        return Listanombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ArraylistaHistoricoCobranzaEntity = new ArrayList<ListaHistoricoCobranzaEntity>();
        arrayListClienteSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();

        clienteSQlite = new ClienteSQlite(getContext());
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaHistoricoCobranzaAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(R.layout.layout_lista_historico_cobranza, parent, false);

            holder = new ListaHistoricoCobranzaAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_nombrecliente_cobranza = (TextView) convertView.findViewById(R.id.tv_nombrecliente_cobranza);
            holder.tv_fecha_cobranza = (TextView) convertView.findViewById(R.id.tv_fecha_cobranza);
            holder.tv_recibo = (TextView) convertView.findViewById(R.id.tv_recibo);
            holder.tv_monto_cobrado = (TextView) convertView.findViewById(R.id.tv_monto_cobrado);
            holder.tv_documento_cobrado = (TextView) convertView.findViewById(R.id.tv_documento_cobrado);
            holder.tv_estado_historico_cobranza = (TextView) convertView.findViewById(R.id.tv_estado_historico_cobranza);
            holder.imv_anular_historico_cobranza = (ImageView) convertView.findViewById(R.id.imv_anular_historico_cobranza);
            holder.imv_flecha_historico_cobranza = (ImageView) convertView.findViewById(R.id.imv_flecha_historico_cobranza);
            holder.chk_validacionqrhistoricocobranza = (CheckBox) convertView.findViewById(R.id.chk_validacionqrhistoricocobranza);
            holder.imv_historico_cobranza_respuesta_ws = (ImageView) convertView.findViewById(R.id.imv_historico_cobranza_respuesta_ws);

            holder.chk_wsrecibido = (CheckBox) convertView.findViewById(R.id.chk_wsrecibido);
            //holder.chk_cobranzaconciliada = (CheckBox) convertView.findViewById(R.id.chk_cobranzaconciliada);
            convertView.setTag(holder);
        } else {
            holder = (ListaHistoricoCobranzaAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaHistoricoCobranzaEntity lead = getItem(position);
        //DecimalFormat format = new DecimalFormat("#0.00");

        holder.tv_nombrecliente_cobranza.setText(lead.getCliente_nombre());
        if(lead.getBanco_id().equals(""))
        {
            holder.tv_fecha_cobranza.setText("");
        }else
            {
                holder.tv_fecha_cobranza.setText(lead.getDeposito_id());
            }

        holder.tv_recibo.setText(lead.getRecibo());
        holder.tv_monto_cobrado.setText((Convert.currencyForView(lead.getMontocobrado())));
        holder.tv_documento_cobrado.setText(lead.getNro_documento());
        holder.tv_estado_historico_cobranza.setText(lead.getEstado());


        if (lead.getChkwsrecibido().equals("Y")) {
            holder.chk_wsrecibido.setChecked(true);
        } else {
            holder.chk_wsrecibido.setChecked(false);
        }


        if (lead.getEstadoqr().equals("True")||lead.getEstadoqr().equals("1")||lead.getEstadoqr().equals("Y")) {
            holder.chk_validacionqrhistoricocobranza.setChecked(true);
        } else {
            holder.chk_validacionqrhistoricocobranza.setChecked(false);
        }
        final View finalConvertView = convertView;
        holder.imv_anular_historico_cobranza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "click", Toast.LENGTH_LONG).show();

                if (lead.getEstado().equals("CONCILIADO")) {
                    Toast.makeText(getContext(), "No se puede Anular un Recibo - Conciliado", Toast.LENGTH_LONG).show();
                } else if (lead.getEstado().equals("Anulado")) {
                    Toast.makeText(getContext(), "No se puede Anular un Recibo - Anulado", Toast.LENGTH_LONG).show();
                } else if (lead.getEstado().equals("Pendiente")) {
                    //createSimpleDialog(lead.getRecibo()) .show();
                    recibo = lead.getRecibo();

                    DialogFragment dialogFragment = new DialogFragment();
                    dialogFragment.show(
                            //getCurrentActivity().getSupportFragmentManager()
                            ((FragmentActivity) finalConvertView.getContext()).getSupportFragmentManager()
                            , "un dialogo");
                }

            }
        });

        holder.imv_flecha_historico_cobranza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                ArrayList<ListaHistoricoCobranzaEntity> Lista = new ArrayList<>();
                Lista.add(lead);
                transaction.add(R.id.content_menu_view, historicoCobranzaView.newInstancias(Lista));

                //transaction.add(R.id.content_menu_view, historicoCobranzaView.newInstancias(lead));
            }
        });

        holder.imv_historico_cobranza_respuesta_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertamostrarcomentario("Comentario Ws",lead.getMensajeWS()).show();
            }
        });

        return convertView;
    }


    static class ViewHolder {
        //TextView lbl_documento;
        TextView tv_nombrecliente_cobranza;
        TextView tv_fecha_cobranza;
        //TextView lbl_fecha_emision;
        TextView tv_recibo;
        // TextView lbl_fecha_vencimiento;
        TextView tv_monto_cobrado;
        TextView tv_documento_cobrado;
        TextView tv_estado_historico_cobranza;
        ImageView imv_anular_historico_cobranza;
        ImageView imv_flecha_historico_cobranza;
        CheckBox chk_validacionqrhistoricocobranza;
        CheckBox chk_wsrecibido;
        ImageView imv_historico_cobranza_respuesta_ws;
    }

    public AlertDialog createSimpleDialog(final String recibo) {
        Recibo = recibo;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());

        arrayListCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        builder.setTitle("Advertencia")
                .setMessage("Esta Seguro de Anular el Recibo?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //listener.onNegativeButtonClick();
                            }
                        });

        return builder.create();
    }

    private Dialog alertamostrarcomentario(String Titulo, String Comentario) {

        final Dialog dialog = new Dialog(Context);
        dialog.setContentView(R.layout.layout_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText(Titulo);

        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText((Comentario==null)?"Sin Comentario":Comentario);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);


        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }


}



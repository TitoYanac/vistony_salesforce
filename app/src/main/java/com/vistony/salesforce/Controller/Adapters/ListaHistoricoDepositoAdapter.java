package com.vistony.salesforce.Controller.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/*
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
*/

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Dao.SQLite.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoDepositoEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.HistoricoDepositoView;

import java.util.ArrayList;
import java.util.List;

public class ListaHistoricoDepositoAdapter extends ArrayAdapter<ListaHistoricoDepositoEntity> {
    public static ArrayList<ListaHistoricoDepositoEntity> ArraylistaHistoricoDepositoEntity;
    ListaHistoricoDepositoEntity listaHistoricoDepositoEntity;
    FragmentManager fragmentManager;
    public static Context context;
    HistoricoDepositoView historicoDepositoView;
    CobranzaCabeceraSQLiteDao cobranzaCabeceraSQLiteDao;
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;
    String Deposito_id, Banco_id, Fecha, MontoDeposito;

    ArrayList<CobranzaDetalleSQLiteEntity> arrayListCobranzaDetalleSQLiteEntity;
    String reswsanuladep = "", reswsliberadetalle = "";
    int resultadocabecera = 0, resultadodetalle = 0;
    public static String deposito_id, banco_id, fechadeposito, montodeposito, tipoingreso, chkbancarizado, fechadiferido, chkdepositodirecto;
    FormulasController formulasController;

    public ListaHistoricoDepositoAdapter(Context Context, List<ListaHistoricoDepositoEntity> objects) {

        super(Context, 0, objects);
        this.context = Context;

    }


    public Dialog Alerta() {
        String mensaje = getContext().getResources().getString(R.string.cancel_deposit_available_conexion_internet);
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog);
        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText(getContext().getResources().getString(R.string.mesagge).toUpperCase());
        final TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText(mensaje);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        //Button dialogButtonExit = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        dialogButton.setText(getContext().getResources().getString(R.string.accept).toUpperCase());
        // dialogButtonExit.setVisibility(View.INVISIBLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArraylistaHistoricoDepositoEntity= new ArrayList <ListaHistoricoDepositoEntity>();
        formulasController = new FormulasController(getContext());
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaHistoricoDepositoAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView){
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_historico_deposito,
                    parent,false);

            holder = new ListaHistoricoDepositoAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_deposito_id = (TextView) convertView.findViewById(R.id.tv_deposito_id);
            holder.tv_banco = (TextView) convertView.findViewById(R.id.tv_banco);
            holder.tv_fecha_deposito = (TextView) convertView.findViewById(R.id.tv_fecha_deposito);
            holder.tv_monto = (TextView) convertView.findViewById(R.id.tv_monto_depositado);
            //holder.chkdepositado = (CheckBox) convertView.findViewById(R.id.chkconciliado);



            holder.imv_anular = (ImageView) convertView.findViewById(R.id.imv_anular);
           /*   holder.imv_anular.setEnabled(false);
                holder.linearLayoutDelete=convertView.findViewById(R.id.layoutAnular);
                holder.linearLayoutDelete.setEnabled(false);
            */

            holder.imv_modificar = (ImageView) convertView.findViewById(R.id.imv_modificar);
            holder.tv_estado = (TextView) convertView.findViewById(R.id.tv_estado);
            holder.tv_tipo_ingreso = (TextView) convertView.findViewById(R.id.tv_tipo_ingreso);
            holder.tv_fecha_diferida = (TextView) convertView.findViewById(R.id.tv_fecha_diferida);
            holder.imv_flecha_historico_deposito = (ImageView) convertView.findViewById(R.id.imv_flecha_historico_deposito);
            convertView.setTag(holder);
        } else {
            holder = (ListaHistoricoDepositoAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        String typeDeposit="";
        final ListaHistoricoDepositoEntity lead = getItem(position);
        //DecimalFormat format =  new DecimalFormat("#0.00");
        // Setup.
        holder.tv_deposito_id.setText(lead.getDeposito_id());
       // holder.tv_monto.setText(format.format(Float.parseFloat(lead.getMontodeposito())));
        holder.tv_monto.setText(Convert.currencyForView((lead.getMontodeposito())));
        holder.tv_estado.setText(lead.getEstado());
        holder.tv_banco.setText(lead.getBankname());
        holder.tv_fecha_deposito.setText(
                //formulasController.Convertirfechahoraafechanumerica(
                Induvis.getDate(BuildConfig.FLAVOR,lead.getFechadeposito())

        //)
        );
        /*if(BuildConfig.FLAVOR.equals("chile"))
        {
            if(lead.getTipoingreso().equals("Cheque"))
            {
                typeDeposit="Cheque Diferido";
            }
            else if(lead.getTipoingreso().equals("Deposito"))
            {
                typeDeposit = "Deposito Efectivo";
            }
            else {
                typeDeposit=lead.getTipoingreso();
            }
        }
        else {
            typeDeposit=lead.getTipoingreso();
        }*/
        holder.tv_tipo_ingreso.setText(lead.getTipoingreso());
        //holder.tv_tipo_ingreso.setText(typeDeposit);
        holder.tv_fecha_diferida.setText(Induvis.getDate(BuildConfig.FLAVOR,lead.getFechadiferida()));
        //holder.chkdepositado.setChecked(lead.isCheckbox());
        final View finalConvertView = convertView;
        holder.imv_anular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "click", Toast.LENGTH_LONG).show();
                        if(lead.getEstado().equals("CONCILIADO")) {
                            Toast.makeText(getContext(), getContext().getResources().getText(R.string.not_cancel_receip_reconciled), Toast.LENGTH_LONG).show();
                        }else if(lead.getEstado().equals("Anulado")){
                            Toast.makeText(getContext(),getContext().getResources().getText(R.string.not_cancel_deposit) , Toast.LENGTH_LONG).show();
                        }else if(lead.getPospay().equals("Y")){
                            Toast.makeText(getContext(), getContext().getResources().getText(R.string.not_cancel_deposit_POS), Toast.LENGTH_LONG).show();
                        }else if(lead.getEstado().equals("Pendiente")){
                            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

                            if (networkInfo != null) {
                                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {

                                    deposito_id=lead.getDeposito_id();
                                    banco_id=lead.getBanco_id();
                                    fechadeposito=lead.getFechadeposito();
                                    montodeposito=lead.getMontodeposito();
                                    tipoingreso=lead.getTipoingreso();
                                    if(lead.getBancarizacion().equals("true")){
                                        chkbancarizado="1";
                                    }else{
                                        chkbancarizado="0";
                                    }
                                    chkdepositodirecto=lead.getDepositodirecto();
                                    fechadiferido=lead.getFechadiferida();

                                    androidx.fragment.app.DialogFragment dialogFragment = new HistoricoDepositoDialogAnularController();
                                    dialogFragment.show(((FragmentActivity) finalConvertView.getContext ()). getSupportFragmentManager (),"un dialogo");

                                } else {
                                    Alerta().show();
                                }
                            }else{
                                Alerta().show();
                            }
                        }
            }});

        holder.imv_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deposito_id=lead.getDeposito_id();
                banco_id=lead.getBanco_id();
                fechadeposito=lead.getFechadeposito();
                montodeposito=lead.getMontodeposito();
                DialogFragment dialogFragment = new DialogFragment();
                dialogFragment.show(
                        //getCurrentActivity().getSupportFragmentManager()
                        ((FragmentActivity) finalConvertView.getContext ()). getSupportFragmentManager ()
                        ,"un dialogo");
            }});
        //holder.et_saldo.setFocusable(false);
        //holder.et_saldo.setEnabled(false);
        //holder.et_nuevosaldo.setFocusable(false);
        //holder.et_nuevosaldo.setEnabled(false);
        holder.imv_flecha_historico_deposito.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     Log.e("REOS","ListaHistoricoDepositoAdapter-holder.imv_flecha_historico_deposito.Oncreate-ingreso");
                                                     //String Texto="VALERIA";
                                                     //String Texto1=Texto;
                                                     if(lead.getEstado().equals("Anulado"))
                                                     {
                                                         Toast.makeText(getContext(), getContext().getResources().getText(R.string.cancel_deposit_not_linked_receips), Toast.LENGTH_LONG).show();
                                                     }
                                                     else if(lead.getEstado().equals("Pendiente")) {
                                                         ArraylistaHistoricoDepositoEntity = new ArrayList<ListaHistoricoDepositoEntity>();
                                                         listaHistoricoDepositoEntity = new ListaHistoricoDepositoEntity();


                                                         listaHistoricoDepositoEntity.banco_id = lead.getBanco_id();
                                                         listaHistoricoDepositoEntity.comentario = lead.getComentario();
                                                         listaHistoricoDepositoEntity.compania_id = lead.getCompania_id();
                                                         listaHistoricoDepositoEntity.deposito_id = lead.getDeposito_id();
                                                         listaHistoricoDepositoEntity.estado = lead.getEstado();
                                                         listaHistoricoDepositoEntity.fechadeposito = lead.getFechadeposito();
                                                         listaHistoricoDepositoEntity.fuerzatrabajo_id = lead.getFuerzatrabajo_id();
                                                         listaHistoricoDepositoEntity.montodeposito = lead.getMontodeposito();
                                                         listaHistoricoDepositoEntity.usuario_id = lead.getUsuario_id();
                                                         listaHistoricoDepositoEntity.checkbox = lead.isCheckbox();
                                                         listaHistoricoDepositoEntity.imvdetalle = lead.getImvdetalle();

                                                         ArraylistaHistoricoDepositoEntity.add(listaHistoricoDepositoEntity);

                                                         fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                                                         FragmentTransaction transaction = fragmentManager.beginTransaction();

                                                         if(ArraylistaHistoricoDepositoEntity==null){
                                                            Log.e("jpcm","Es nuloo");
                                                         }else{
                                                             transaction.add(R.id.content_menu_view, historicoDepositoView.newInstance(ArraylistaHistoricoDepositoEntity));
                                                             //transaction.commit();
                                                         }

                                                     }
                                                 }
                                             }

        );


        //Glide.with(getContext()).load(lead.getImage()).into(holder.avatar);

        return convertView;
    }

    static class ViewHolder {
        //TextView lbl_documento;
        TextView tv_deposito_id;
        //TextView lbl_fecha_emision;
        TextView tv_monto;
        // TextView lbl_fecha_vencimiento;
        //CheckBox chkdepositado;
        TextView tv_estado;
        ImageView imv_flecha_historico_deposito;
        TextView tv_banco;
        TextView tv_fecha_deposito;
        TextView tv_tipo_ingreso;
        ImageView imv_anular;
        ImageView imv_modificar;
        TextView tv_fecha_diferida;
        LinearLayout linearLayoutDelete;
    }




    public AlertDialog createSimpleDialog(final String deposito_id, final String banco, final String fecha, final String montodeposito) {
        Deposito_id=deposito_id;
        Banco_id=banco;
        Fecha=fecha;
        MontoDeposito=montodeposito;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        cobranzaCabeceraSQLiteDao = new CobranzaCabeceraSQLiteDao(getContext());
        cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());

        arrayListCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        builder.setTitle("Advertencia")
                .setMessage("Esta Seguro de Anular el Deposito?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String reswsanuladep="";




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


    }










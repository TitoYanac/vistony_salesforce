package com.vistony.salesforce.Controller.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.vistony.salesforce.Dao.Retrofit.DepositoRepository;
import com.vistony.salesforce.Dao.Retrofit.CobranzaRepository;
import com.vistony.salesforce.Dao.Retrofit.HistoricoCobranzaWS;
import com.vistony.salesforce.Dao.SQLite.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.CobranzaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.HistoricoDepositoView;

import java.util.ArrayList;

public class HistoricoDepositoDialogAnularController extends DialogFragment {

    String Deposito_id, Banco_id, Fecha, Montodeposito,tipoingreso,chkbancarizado,fechadiferido,chkdepositodirecto;
    TextView et_comentario_anular_deposito;
    String motivoanulacion;
    HistoricoDepositoView historicoDepositoView;
    private FragmentManager fragmentManager;
    ListaHistoricoDepositoAdapter listaHistoricoDepositoAdapter;
    HiloAnularDeposito hiloAnularDeposito;
    String reswsanuladep="",reswsliberadetalle="";
    int resultadocabecera=0,resultadodetalle=0;
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;
    CobranzaCabeceraSQLiteDao cobranzaCabeceraSQLiteDao;
    ArrayList<CobranzaDetalleSQLiteEntity> arrayListCobranzaDetalleSQLiteEntity;
    ArrayList<CobranzaCabeceraSQLiteEntity> arrayListCobranzaCabeceraSQLiteEntity;
    Context context;
    HiloObtenerRecibos hiloObtenerRecibos;
    String montoTotaldeRecibos="";
    private DepositoRepository depositoRepository;
    private CobranzaRepository cobranzaRepository;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        listaHistoricoDepositoAdapter = new ListaHistoricoDepositoAdapter(getContext(), null);
        depositoRepository = new ViewModelProvider(getActivity()).get(DepositoRepository.class);
        cobranzaRepository = new ViewModelProvider(getActivity()).get(CobranzaRepository.class);



        final View view = getActivity().getLayoutInflater().inflate(R.layout.layout_comentario_anular_historico_deposito, null);
        et_comentario_anular_deposito = (TextView) view.findViewById(R.id.et_comentario_anular_deposito);

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.layout_input_dialog);

            TextView textTitle = dialog.findViewById(R.id.text);
            textTitle.setText("INGRESA UN COMENTARIO");

            final EditText textMsj = dialog.findViewById(R.id.textEditViewMsj);
            textMsj.setSingleLine(false);  //add this
            textMsj.setLines(4);
            textMsj.setMaxLines(5);
            textMsj.setGravity(Gravity.LEFT | Gravity.TOP);
            textMsj.setHorizontalScrollBarEnabled(false); //this
            //textMsj.setText("Esta seguro de salir de la Aplicación?");

            ImageView image = (ImageView) dialog.findViewById(R.id.image);

            Drawable background = image.getBackground();
            image.setImageResource(R.mipmap.logo_circulo);

        Log.e("REOS","HistoricoDepositoDialogAnularController-Ingresa Dialogo");
            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            Button dialogButtonExit = (Button) dialog.findViewById(R.id.dialogButtonCancel);
            // if button is clicked, close the custom dialog
            dialogButton.setText("GUARDAR Y ANULAR");
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(textMsj.length()>2){
                        Log.e("REOS","HistoricoDepositoDialogAnularController-IngresaBoton:");
                        historicoDepositoView = new HistoricoDepositoView();
                        fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                        motivoanulacion = textMsj.getText().toString();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        //transaction.replace(android.R.id.content, fragment);
                        //transaction.addToBackStack("DiscoverPage");
                        Deposito_id = listaHistoricoDepositoAdapter.deposito_id;
                        Banco_id = listaHistoricoDepositoAdapter.banco_id;
                        Fecha   = listaHistoricoDepositoAdapter.fechadeposito;
                        Montodeposito = listaHistoricoDepositoAdapter.montodeposito;
                        context = listaHistoricoDepositoAdapter.context;
                        tipoingreso=listaHistoricoDepositoAdapter.tipoingreso;
                        chkbancarizado=listaHistoricoDepositoAdapter.chkbancarizado;
                        fechadiferido=listaHistoricoDepositoAdapter.fechadiferido;
                        chkdepositodirecto=listaHistoricoDepositoAdapter.chkdepositodirecto;

                        AnnulmentDeposit();
                        //transaction.add(R.id.content_menu_view, historicoDepositoView.newInstanciaComentarioAnulado(comentario));

                        /*cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(getContext());
                        montoTotaldeRecibos=cobranzaDetalleSQLiteDao.ValidarCobranzaDetallePendientePorMonto(Deposito_id, SesionEntity.compania_id);

                        if(Float.parseFloat((Montodeposito))== Float.parseFloat(montoTotaldeRecibos))
                        {
                            Log.e("REOS","HistoricoDepositoDialogAnularController-HiloAnularDeposito:");
                            hiloAnularDeposito = new HiloAnularDeposito();
                            hiloAnularDeposito.execute();

                            //ListenerBackPress.setTempRefreshListAnulado(true);
                            Toast.makeText(getContext(), "Deposito anulado exitosamente...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Log.e("REOS","HistoricoDepositoDialogAnularController-HiloObtenerRecibos:");
                            hiloObtenerRecibos = new HiloObtenerRecibos();
                            hiloObtenerRecibos.execute();
                        }*/

                        dialog.dismiss();
                    }else{
                        Toast.makeText(getActivity(), "Ingrese su descargo en la caja de comentario...", Toast.LENGTH_LONG).show();
                    }

                }
            });

            dialogButtonExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            return  dialog;
    }


    public int boolToInt(boolean b) {
        return b ? 1 : 0;
    }


    private void AnnulmentDeposit()
    {
        cobranzaCabeceraSQLiteDao = new CobranzaCabeceraSQLiteDao(context);
        cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(context);
        arrayListCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        arrayListCobranzaCabeceraSQLiteEntity = new ArrayList<CobranzaCabeceraSQLiteEntity>();
        arrayListCobranzaCabeceraSQLiteEntity=cobranzaCabeceraSQLiteDao.ObtenerCobranzaCabeceraporID(Deposito_id);
        if(!arrayListCobranzaCabeceraSQLiteEntity.isEmpty())
        {
            for (int i = 0; i < arrayListCobranzaCabeceraSQLiteEntity.size(); i++) {
                resultadocabecera = cobranzaCabeceraSQLiteDao.AnularCobranzaCabecera(Deposito_id, SesionEntity.compania_id, SesionEntity.fuerzatrabajo_id, motivoanulacion, reswsanuladep);
            }
            arrayListCobranzaDetalleSQLiteEntity = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporDeposito(Deposito_id, SesionEntity.compania_id);
            for (int j = 0; j < arrayListCobranzaDetalleSQLiteEntity.size(); j++) {
                cobranzaDetalleSQLiteDao.ActualizaWSCobranzaDetalle(
                        arrayListCobranzaDetalleSQLiteEntity.get(j).getRecibo(),
                        arrayListCobranzaDetalleSQLiteEntity.get(j).getCompania_id(),
                        arrayListCobranzaDetalleSQLiteEntity.get(j).getUsuario_id(),
                        "Y",
                        ""
                        , ""
                        , "N"
                        , "N"
                );
            }
            ///////////////////////////// ENVIAR DEPOSITOS ANULADOS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            depositoRepository.UpdatedepositStatus(getContext()).observe(getActivity(), data -> {
                Log.e("Jepicame", "=>" + data);
            });
            ////////////////////////ENVIAR RECIBOS DESVINCULADOS DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            cobranzaRepository.ReceiptDetachedDeposit(getContext()).observe(getActivity(), data -> {
                Log.e("Jepicame", "=>" + data);
            });

            Toast.makeText(getContext(), "Deposito Anulado Correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Advertencia!! El Deposito no existe en el Equipo, no es posible Anular", Toast.LENGTH_SHORT).show();
        }
    }

    private class HiloAnularDeposito extends AsyncTask<String, Void, Object> {

        @Override
        protected String doInBackground(String... arg1) {
            //String argumento1=arg1[0];

            try {
                cobranzaCabeceraSQLiteDao = new CobranzaCabeceraSQLiteDao(context);
                cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(context);
                arrayListCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
                arrayListCobranzaCabeceraSQLiteEntity = new ArrayList<CobranzaCabeceraSQLiteEntity>();
                arrayListCobranzaCabeceraSQLiteEntity=cobranzaCabeceraSQLiteDao.ObtenerCobranzaCabeceraporID(Deposito_id);
                Log.e("REOS","HistoricoDepositoDialogAnularController-arrayListCobranzaCabeceraSQLiteEntity:"+arrayListCobranzaCabeceraSQLiteEntity.size());
                for(int i=0;i<arrayListCobranzaCabeceraSQLiteEntity.size();i++)
                {
                    String fechadiferido="";
                    String[] sourceSplitFecha;

                    /*if(arrayListCobranzaCabeceraSQLiteEntity.get(i).getFechadiferido().length()>10)
                    {
                        sourceSplitFecha= arrayListCobranzaCabeceraSQLiteEntity.get(i).getFechadiferido().split(" ");
                        String fecha=sourceSplitFecha[0];
                        String hora=sourceSplitFecha[1];

                        String[] fechasplit=fecha.split("-");
                        String anioemision= fechasplit[0];
                        String mesemision= fechasplit[1];
                        String diaemision= fechasplit[2];

                        if(anioemision.length()==1)
                        {
                            anioemision="0"+anioemision;
                        }
                        fechadiferido=anioemision+"-"+mesemision+"-"+diaemision;

                    }*/
                    DepositoRepository depositoRepository =new DepositoRepository(getContext());
                    //int resultado=0;

                    /*reswsanuladep= depositoRepository.PostCobranzaCabeceraWS(
                            SesionEntity.imei,
                            "UPDATE",
                            SesionEntity.compania_id,
                            arrayListCobranzaCabeceraSQLiteEntity.get(i).getBanco_id(),
                            arrayListCobranzaCabeceraSQLiteEntity.get(i).getTipoingreso(),
                            Deposito_id,
                            SesionEntity.usuario_id,
                            Fecha,
                            arrayListCobranzaCabeceraSQLiteEntity.get(i).getTotalmontocobrado(),
                            "Anulado",
                            "0",
                            SesionEntity.fuerzatrabajo_id,
                            arrayListCobranzaCabeceraSQLiteEntity.get(i).getChkbancarizado(),
                            fechadiferido,
                            motivoanulacion,
                            arrayListCobranzaCabeceraSQLiteEntity.get(i).getPagodirecto(),
                            "0"
                    );
                    */

                    //reswsanuladep=String.valueOf(resultado);
                    /*reswsanuladep = cobranzaCabeceraWSDao.AnularDeposito(
                            SesionEntity.imei,
                            SesionEntity.compania_id,
                            arrayListCobranzaCabeceraSQLiteEntity.get(i).getBanco_id(),
                            arrayListCobranzaCabeceraSQLiteEntity.get(i).getTipoingreso(),
                            Deposito_id,
                            SesionEntity.usuario_id,
                            Fecha,
                            arrayListCobranzaCabeceraSQLiteEntity.get(i).getTotalmontocobrado(),
                            "",
                            SesionEntity.fuerzatrabajo_id,
                            arrayListCobranzaCabeceraSQLiteEntity.get(i).getChkbancarizado(),
                            //arrayListCobranzaCabeceraSQLiteEntity.get(i).getFechadiferido(),
                            fechadiferido,
                            motivoanulacion,
                            arrayListCobranzaCabeceraSQLiteEntity.get(i).getPagodirecto()
                    );*/

                    /*if (reswsanuladep.equals("1")) {
                        resultadocabecera = cobranzaCabeceraSQLiteDao.AnularCobranzaCabecera(Deposito_id, SesionEntity.compania_id, SesionEntity.fuerzatrabajo_id,motivoanulacion,reswsanuladep);
                    }else{
                        resultadocabecera = cobranzaCabeceraSQLiteDao.AnularCobranzaCabecera(Deposito_id, SesionEntity.compania_id, SesionEntity.fuerzatrabajo_id,motivoanulacion,reswsanuladep);
                    }*/
                    resultadocabecera = cobranzaCabeceraSQLiteDao.AnularCobranzaCabecera(Deposito_id, SesionEntity.compania_id, SesionEntity.fuerzatrabajo_id,motivoanulacion,reswsanuladep);
                }

                arrayListCobranzaDetalleSQLiteEntity = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporDeposito(Deposito_id, SesionEntity.compania_id);
                Log.e("REOS","HistoricoDepositoDialogAnularController-arrayListCobranzaDetalleSQLiteEntity:"+arrayListCobranzaDetalleSQLiteEntity.size());
                for (int j = 0; j < arrayListCobranzaDetalleSQLiteEntity.size(); j++) {
                    //FormulasController formulasController=new FormulasController(getContext());
                    String resultado="0";

                    //Antiguo Envio de Cobranza Detalle WS - SOAP
                    //resultadoenviows=cobranzaDetalleWSDao.enviarRecibo(cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(recibo, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id)
                    //      ,SesionEntity.imei,SesionEntity.usuario_id,comentario+"-"+SesionEntity.serialnumber,SesionEntity.fuerzatrabajo_id);
                    //Nuevo Envio de Cobranza De

                    /*resultado= CobranzaRepository.EnviarReciboWsRetrofit(
                            cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(
                                    arrayListCobranzaDetalleSQLiteEntity.get(j).getRecibo(),
                                    SesionEntity.compania_id,
                                    SesionEntity.fuerzatrabajo_id),
                            getContext(),
                            "UPDATE",
                            "1",
                            "0",
                            "0",
                            "0"
                    );*/
                    //reswsliberadetalle=String.valueOf(resultado);
                    /*reswsliberadetalle = cobranzaDetalleWSDao.DesvinculaReciboconDeposito(
                            SesionEntity.imei,
                            "UPDATE",
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getCompania_id().toString(),
                            "0",
                            "",
                            "1",
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getId(),
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getCliente_id(),
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getDocumento_id(),
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getImportedocumento(),
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getSaldodocumento(),
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getSaldocobrado(),
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getNuevosaldodocumento(),
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getFechacobranza(),
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getRecibo(),
                            "Pendiente",
                            "",
                            SesionEntity.usuario_id,
                            SesionEntity.fuerzatrabajo_id,
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getChkbancarizado(),
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getChkqrvalidado(),
                            "",
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getPagodirecto()
                    );*/


                    /*if (reswsliberadetalle.equals("1")) {
                        cobranzaDetalleSQLiteDao.ActualizaWSCobranzaDetalle(
                                arrayListCobranzaDetalleSQLiteEntity.get(j).getRecibo(),
                                arrayListCobranzaDetalleSQLiteEntity.get(j).getCompania_id(),
                                arrayListCobranzaDetalleSQLiteEntity.get(j).getUsuario_id(),
                                "0",
                                "1"
                                ,"0"
                                ,"0"
                                ,"0"
                                );

                    }
                    else
                    {
                        cobranzaDetalleSQLiteDao.ActualizaWSCobranzaDetalle(
                                arrayListCobranzaDetalleSQLiteEntity.get(j).getRecibo(),
                                arrayListCobranzaDetalleSQLiteEntity.get(j).getCompania_id(),
                                arrayListCobranzaDetalleSQLiteEntity.get(j).getUsuario_id(),
                                "Y",
                                ""
                                ,""
                                ,"N"
                                ,"N"

                        );
                    }*/
                    cobranzaDetalleSQLiteDao.ActualizaWSCobranzaDetalle(
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getRecibo(),
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getCompania_id(),
                            arrayListCobranzaDetalleSQLiteEntity.get(j).getUsuario_id(),
                            "Y",
                            ""
                            ,""
                            ,"N"
                            ,"N"
                    );
                }

               /* if (reswsliberadetalle.equals("1")) {
                    resultadodetalle = cobranzaDetalleSQLiteDao.DesvincularCobranzaDetalleconCabecera(Deposito_id, SesionEntity.fuerzatrabajo_id, SesionEntity.compania_id);

                }*/

            } catch (Exception e) {
                Log.e("REOS","HistoricoDepositoDialogAnularController-Erro:"+e.toString());
            }

            return "1";
        }

        protected void onPostExecute(Object result) {
            if (reswsanuladep.equals("1")) {
                if (resultadocabecera == 1) {

                }
            }
            if (reswsliberadetalle.equals("1")) {
                if (resultadodetalle == 1) {

                }
            }

            HistoricoDepositoView.imb_consultardep.performClick();
            Toast.makeText(context, "Comentario agregado Exitosamente", Toast.LENGTH_SHORT).show();
        }
    }

    private class HiloObtenerRecibos extends AsyncTask<String, Void, Object> {

        @Override
        protected String doInBackground(String... arg1) {
            //String argumento1=arg1[0];

            try {
                cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(context);
                cobranzaCabeceraSQLiteDao = new CobranzaCabeceraSQLiteDao(context);
                ArrayList<CobranzaCabeceraSQLiteEntity> listaCobranzaCabeceraSQLiteEntity=new ArrayList<>();
                String tipofecha="Deposito";
                ArrayList<ListaHistoricoCobranzaEntity> arraylistahistoricocobranzaentity =  new ArrayList<ListaHistoricoCobranzaEntity>();

                //Obtiene el Deposito desde la BD Local
                listaCobranzaCabeceraSQLiteEntity=cobranzaCabeceraSQLiteDao.ObtenerCobranzaCabeceraporID(Deposito_id);
                //Evalua si la lista esta vacia, la Inserta
                String[] fechaenviosplit=Fecha.split("-");
                String fechasugerida;
                String dia,mes,anio;
                dia=fechaenviosplit[0];
                mes=fechaenviosplit[1];
                anio=fechaenviosplit[2];
                fechasugerida=dia+"-"+mes+"-"+anio;




                if(listaCobranzaCabeceraSQLiteEntity.isEmpty())
                {
                    String fechadiferida;
                    String[] sourceSplitFechaDiferido= fechadiferido.split(" ");
                    String fecha=sourceSplitFechaDiferido[0];
                    String hora=sourceSplitFechaDiferido[1];

                    String[] fechasplit=fecha.split("/");
                    String diaemision= fechasplit[0];
                    String mesemision= fechasplit[1];
                    String anioemision= fechasplit[2];

                    if(diaemision.length()==1)
                    {
                        diaemision="0"+diaemision;
                    }
                    fechadiferida=anioemision+"/"+mesemision+"/"+diaemision+" 00:00:00:000";
                    cobranzaCabeceraSQLiteDao.InsertaCobranzaCabecera(
                            Deposito_id,
                            SesionEntity.usuario_id,
                            SesionEntity.fuerzatrabajo_id,
                            Banco_id,
                            SesionEntity.compania_id,
                            Montodeposito,
                            tipoingreso,
                            chkbancarizado,
                            //fechadiferido,
                            fechadiferida,
                            Fecha,
                            chkdepositodirecto,
                            "0"
                    );
                }

                //Obtiene la Cobranza de la BD local
                CobranzaRepository cobranzaRepository=new CobranzaRepository(getContext());
                arraylistahistoricocobranzaentity = cobranzaRepository.getHistoricoCobranza
                        (
                                SesionEntity.imei,
                               // SesionEntity.compania_id,
                               // Banco_id,
                                //Deposito_id,

                                fechasugerida,
                                tipofecha,
                                //SesionEntity.fuerzatrabajo_id,
                                //"0"
                                ""
                        );
                /*arraylistahistoricocobranzaentity = historicoCobranzaWSDao.obtenerHistoricoCobranza
                        (
                        SesionEntity.imei,
                        SesionEntity.compania_id,
                                Banco_id,
                                Deposito_id,
                        tipofecha,
                               // Fecha,
                                fechasugerida,
                        SesionEntity.fuerzatrabajo_id,
                        ""
                );*/

                if(arraylistahistoricocobranzaentity.size()>0){
                    Log.e("jpcm","TIENE DATOS");
                }else{
                    Log.e("jpcm","NO TIENE NADA");
                }

                //Evalua si la lista esta vacia, la Inserta
                for(int i=0;i<arraylistahistoricocobranzaentity.size();i++)
                {

                    ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity = new ArrayList<>();
                    listaCobranzaDetalleSQLiteEntity=
                            cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(arraylistahistoricocobranzaentity.get(i).getRecibo(),SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id);

                    if(listaCobranzaDetalleSQLiteEntity.isEmpty())
                    {
                        String depositado="",chkbancarizado="",chkqrvalidado="",comentario="",fechacobranza="";
                        //CobranzaDetalleSQLiteDao insertarCobranzaDetalle = new CobranzaDetalleSQLiteDao(getContext());

                        if(arraylistahistoricocobranzaentity.get(i).getDeposito_id().equals("1"))
                        {
                            depositado="0";
                        }else
                        {
                            depositado="1";
                        }
                        if(arraylistahistoricocobranzaentity.get(i).getBancarizacion().equals("True"))
                        {
                            chkbancarizado="1";
                        }else
                        {
                            chkbancarizado="0";
                        }
                        if(arraylistahistoricocobranzaentity.get(i).getEstadoqr().equals("True"))
                        {
                            chkqrvalidado="1";
                        }else
                        {
                            chkqrvalidado="0";
                        }
                        if(arraylistahistoricocobranzaentity.get(i).getComentario().equals("anyType{}"))
                        {
                            comentario="0";
                        }else
                        {
                            comentario=arraylistahistoricocobranzaentity.get(i).getComentario();
                        }

                        if(arraylistahistoricocobranzaentity.get(i).getFechacobranza().length()>10)
                        {
                            Log.e("jpcm","--->++++****"+arraylistahistoricocobranzaentity.get(i).getFechacobranza());
                            /*String[] sourceSplitFecha= arraylistahistoricocobranzaentity.get(i).getFechacobranza().split(" ");
                            String fecha=sourceSplitFecha[0];
                            String hora=sourceSplitFecha[1];

                            Log.e("jpcm","--->++++****"+fecha);

                            String[] fechasplit=fecha.split("/");
                            String anioemision= fechasplit[0];
                            String mesemision= fechasplit[1];
                            String diaemision= fechasplit[2];

                            if(anioemision.length()==1)
                            {
                                anioemision="0"+anioemision;
                            }
                            fechacobranza=diaemision+"-"+mesemision+"-"+anioemision;
                            */



                            fechacobranza=arraylistahistoricocobranzaentity.get(i).getFechacobranza();

                            Log.e("jpcm","--->++++======="+fechacobranza);
                        }
                        //Inserta Detalle
                        cobranzaDetalleSQLiteDao.InsertaCobranzaDetalle
                                (
                                        arraylistahistoricocobranzaentity.get(i).getDeposito_id(),
                                        arraylistahistoricocobranzaentity.get(i).getCliente_id(),
                                        arraylistahistoricocobranzaentity.get(i).getDocumento_id(),
                                        arraylistahistoricocobranzaentity.get(i).getCompania_id(),
                                        arraylistahistoricocobranzaentity.get(i).getImportedocumento(),
                                        arraylistahistoricocobranzaentity.get(i).getSaldodocumento(),
                                        arraylistahistoricocobranzaentity.get(i).getNuevosaldodocumento(),
                                        arraylistahistoricocobranzaentity.get(i).getMontocobrado(),
                                        //arraylistahistoricocobranzaentity.get(i).getFechacobranza()
                                        fechacobranza
                                        ,
                                        arraylistahistoricocobranzaentity.get(i).getRecibo(),
                                        arraylistahistoricocobranzaentity.get(i).getNro_documento(),
                                        SesionEntity.fuerzatrabajo_id,
                                        //arraylistahistoricocobranzaentity.get(i).getBancarizacion(),
                                        chkbancarizado,
                                        arraylistahistoricocobranzaentity.get(i).getMotivoanulacion(),
                                        arraylistahistoricocobranzaentity.get(i).getUsuario_id(),
                                        //arraylistahistoricocobranzaentity.get(i).getComentario()
                                        comentario
                                        ,
                                        //arraylistahistoricocobranzaentity.get(i).getChkdepositado()
                                        depositado
                                        ,
                                        //arraylistahistoricocobranzaentity.get(i).getEstadoqr()
                                        chkqrvalidado
                                        ,
                                        arraylistahistoricocobranzaentity.get(i).getBanco_id(),
                                        arraylistahistoricocobranzaentity.get(i).getChkwsrecibido(),
                                        //arraylistahistoricocobranzaentity.get(i).getEstadoqr()
                                        chkqrvalidado
                                        ,
                                        //arraylistahistoricocobranzaentity.get(i).getChkwsdepositorecibido()
                                        depositado
                                        ,"N"
                                        ,arraylistahistoricocobranzaentity.get(i).getPagopos()
                                        ,arraylistahistoricocobranzaentity.get(i).getCodesap()
                                        ,""
                                        ,""
                                        ,""
                                        ,""
                                        ,""
                                );

                    }

                }

            } catch (Exception e) {
                Log.e("jpcm",""+e);
            }

            return "1";
        }

        protected void onPostExecute(Object result)
        {

            montoTotaldeRecibos=cobranzaDetalleSQLiteDao.ValidarCobranzaDetallePendientePorMonto(Deposito_id, SesionEntity.compania_id);

            Log.e("jpcm esta",""+montoTotaldeRecibos);
            Log.e("jpcm este",Float.parseFloat((Montodeposito))+"="+Float.parseFloat(montoTotaldeRecibos));
            if(Float.parseFloat((Montodeposito))== Float.parseFloat(montoTotaldeRecibos))
            {
                hiloAnularDeposito = new HiloAnularDeposito();
                hiloAnularDeposito.execute();

            }
            else
            {
                Toast.makeText(context, "Revisar Acceso a internet x", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
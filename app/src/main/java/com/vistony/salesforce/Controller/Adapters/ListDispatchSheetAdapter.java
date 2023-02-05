package com.vistony.salesforce.Controller.Adapters;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.vistony.salesforce.AppExecutors;
import com.vistony.salesforce.Controller.Utilitario.AlertDialogController;
import com.vistony.salesforce.Controller.Utilitario.GPSController;
import com.vistony.salesforce.Controller.Utilitario.Utilitario;
import com.vistony.salesforce.Dao.Retrofit.StatusDispatchRepository;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.StatusDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Dao.SQLite.VisitSectionSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHojaDespachoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VisitSectionEntity;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.ClienteCabeceraView;
import com.vistony.salesforce.View.DialogMain;
import com.vistony.salesforce.View.DireccionClienteView;
import com.vistony.salesforce.View.DispatchSheetView;
import com.vistony.salesforce.features.featureone.SomeScreenKt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListDispatchSheetAdapter extends ArrayAdapter<ListaHojaDespachoEntity> {

    private android.content.Context Context;
    private List<ListaHojaDespachoEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<ListaHojaDespachoEntity> arrayList;
    private FragmentManager fragmentManager;
    public DireccionClienteView direccionClienteView;
    ListaClienteCabeceraEntity listaClienteCabeceraEntity;
    public ClienteCabeceraView clienteCabeceraView;
    public DispatchSheetView hojaDespachoView;
    public static ArrayList<ListaClienteCabeceraEntity> ArraylistaClienteCabeceraEntity;
    Activity activity;

    public ListDispatchSheetAdapter(
            android.content.Context context,
            List<ListaHojaDespachoEntity> objects,
            Activity activity

    ) {

        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaHojaDespachoEntity>();
        this.arrayList.addAll(objects);
        this.activity=activity;

    }

    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        Listanombres.clear();
        if(charText.length()==0)
        {
            Listanombres.addAll(arrayList);
        }else
        {
            for(ListaHojaDespachoEntity wp: arrayList)
            {
                if(wp.getNombrecliente().toLowerCase(Locale.getDefault()).contains(charText))
                {
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
    public ListaHojaDespachoEntity getItem(int position) {
        return Listanombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListDispatchSheetAdapter .ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_list_dispatch_sheet,
                    parent,
                    false);

            holder = new ListDispatchSheetAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_cliente = (TextView) convertView.findViewById(R.id.tv_client);
            holder.tv_direccion = (TextView) convertView.findViewById(R.id.tv_address);
            holder.imv_delivery_status = (ImageView) convertView.findViewById(R.id.imv_delivery_status);
            holder.imv_collection_status = (ImageView) convertView.findViewById(R.id.imv_collection_status);
            holder.imv_visit_status = (ImageView) convertView.findViewById(R.id.imv_visit_status);

            convertView.setTag(holder);
        } else {
            holder = (ListDispatchSheetAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaHojaDespachoEntity lead = getItem(position);

        // Setup.
        holder.tv_cliente.setText(lead.getNombrecliente());
        holder.tv_direccion.setText(lead.getDireccion());

        if(lead.getEstado().equals("ENTREGADO")||lead.getEstado().equals("ANULADO")||lead.getEstado().equals("VOLVER A PROGRAMAR"))
        {
            holder.imv_delivery_status.setImageResource(R.mipmap.entrega_recibida_32);

        }else {
            holder.imv_delivery_status.setImageResource(R.mipmap.entrega_espera_512);
            //holder.imv_delivery_status.setImageResource(R.mipmap.entrega_recibida_32);
        }
        if(Float.parseFloat(lead.getSaldo())>0)
        {
            holder.imv_collection_status.setImageResource(R.mipmap.pay_money_wait_512);

        }else {
            holder.imv_collection_status.setImageResource(R.mipmap.pay_money_check_512);
        }
        Log.e("REOS", "ListDispatchSheetAdapter-getView-lead.lead.isChkvisitsectionend()" + lead.isChkvisitsectionend());
        Log.e("REOS", "ListDispatchSheetAdapter-getView-lead.lead.getNombrecliente()" + lead.getNombrecliente());
        if(lead.isChkvisitsectionend())
        {
            holder.imv_visit_status.setImageResource(R.mipmap.marker_check_512_2);
        }else {
            holder.imv_visit_status.setImageResource(R.mipmap.marker_wait_512_2);
        }

        holder.imv_visit_status.setOnClickListener(new View.OnClickListener() {
                                                       @Override

                                                       public void onClick(View v)
                                                       {
                                                           Log.e("REOS", "ListDispatchSheetAdapter-holder.imv_visit_status-DialogMain()");
                                                           new DialogMain().showDialog(1);
                                                       }
                                                   }

        );

        holder.imv_delivery_status.setOnClickListener(new View.OnClickListener() {
                                                                @Override

                                                                public void onClick(View v)
                                                                {
                                                                    Log.e("REOS", "MenuAccion-alertDialogVisitSection-cv_dispatch.setOnClickListener:Click");
                                                                    androidx.fragment.app.DialogFragment dialogFragment = new StatusDispatchDialog(lead.getCliente_id(),lead.getNombrecliente(), lead.getControl_id(), lead.getItem_id(), lead.getDomembarque_id());
                                                                    dialogFragment.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "un dialogo");

                                                                }
                                                            }
        );

        AlertDialogController alertDialogController=new AlertDialogController(activity);
        /*alertDialogController.alertDialogVisitSection(
                getContext(),
                lead.getCliente_id(),
                lead.getDomembarque_id(),
                lead.getControl_id(),
                lead.getItem_id(),
                activity
                ).show();*/
        /*holder.relativeListaHojaDespacho.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    hojaDespachoView = new DispatchSheetView();
                                                                    listaClienteCabeceraEntity=new ListaClienteCabeceraEntity();
                                                                    ClienteSQlite clienteSQliteDAO=new ClienteSQlite(getContext());
                                                                    ArrayList<ClienteSQLiteEntity> listaClienteEnvio=new ArrayList<>();
                                                                    Log.e("REOS","ListaHojaDespachoAdapter.lead.nombrecliente:"+lead.nombrecliente);
                                                                    Log.e("REOS","ListaHojaDespachoAdapter.SesionEntity.compania_id:"+ SesionEntity.compania_id);
                                                                    listaClienteEnvio=clienteSQliteDAO.ObtenerDatosCliente(lead.cliente_id,SesionEntity.compania_id);
                                                                    Log.e("REOS","ListaHojaDespachoAdapter.listaClienteEnvio.size:"+listaClienteEnvio.size());
                                                                    Log.e("REOS","ListaHojaDespachoAdapter.lead.cliente_id():"+lead.getCliente_id());
                                                                    Log.e("REOS","ListaHojaDespachoAdapter.lead.getDomembarque_id():"+lead.getDomembarque_id());
                                                                    if(!listaClienteEnvio.isEmpty()) {
                                                                        for (int i = 0; i < listaClienteEnvio.size(); i++) {
                                                                            listaClienteCabeceraEntity = new ListaClienteCabeceraEntity();
                                                                            holder.tv_cliente.setText(listaClienteEnvio.get(i).getNombrecliente());
                                                                            listaClienteCabeceraEntity.setCliente_id(lead.getCliente_id());
                                                                            listaClienteCabeceraEntity.setNombrecliente(listaClienteEnvio.get(i).getNombrecliente());
                                                                            listaClienteCabeceraEntity.setDireccion(lead.getDireccion());
                                                                            listaClienteCabeceraEntity.setSaldo(lead.getSaldo());
                                                                            listaClienteCabeceraEntity.setImvclientecabecera(0);
                                                                            listaClienteCabeceraEntity.setMoneda(listaClienteEnvio.get(i).getMoneda());
                                                                            listaClienteCabeceraEntity.setDomembarque_id(lead.getDomembarque_id());
                                                                            listaClienteCabeceraEntity.setImpuesto_id(listaClienteEnvio.get(i).getImpuesto_id());
                                                                            listaClienteCabeceraEntity.setImpuesto(listaClienteEnvio.get(i).getImpuesto());
                                                                            listaClienteCabeceraEntity.setRucdni(listaClienteEnvio.get(i).getRucdni());
                                                                            listaClienteCabeceraEntity.setCategoria(listaClienteEnvio.get(i).getCategoria());
                                                                            listaClienteCabeceraEntity.setLinea_credito(listaClienteEnvio.get(i).getLinea_credito());
                                                                            listaClienteCabeceraEntity.setTerminopago_id(listaClienteEnvio.get(i).getLinea_credito());
                                                                            listaClienteCabeceraEntity.setZona_id(listaClienteEnvio.get(i).getZona_id());
                                                                            listaClienteCabeceraEntity.setCompania_id(SesionEntity.compania_id);
                                                                            listaClienteCabeceraEntity.setOrdenvisita(listaClienteEnvio.get(i).getOrden());
                                                                            listaClienteCabeceraEntity.setZona(listaClienteEnvio.get(i).getZona());
                                                                            listaClienteCabeceraEntity.setTelefonofijo(listaClienteEnvio.get(i).getTelefonofijo());
                                                                            listaClienteCabeceraEntity.setTelefonomovil(listaClienteEnvio.get(i).getTelefonomovil());
                                                                            listaClienteCabeceraEntity.setCorreo(listaClienteEnvio.get(i).getCorreo());
                                                                            listaClienteCabeceraEntity.setUbigeo_id(listaClienteEnvio.get(i).getUbigeo_id());
                                                                            listaClienteCabeceraEntity.setTipocambio(listaClienteEnvio.get(i).getTipocambio());
                                                                            listaClienteCabeceraEntity.setChk_visita("0");
                                                                            listaClienteCabeceraEntity.setChk_pedido("0");
                                                                            listaClienteCabeceraEntity.setChk_cobranza("0");
                                                                            listaClienteCabeceraEntity.setChk_ruta("0");
                                                                            listaClienteCabeceraEntity.setChk_cobranza("0");
                                                                            listaClienteCabeceraEntity.setControl_id(lead.getControl_id());
                                                                            listaClienteCabeceraEntity.setItem_id(lead.getItem_id());
                                                                            ArraylistaClienteCabeceraEntity.add(listaClienteCabeceraEntity);
                                                                        }
                                                                    }
                                                                    else {
                                                                        listaClienteCabeceraEntity = new ListaClienteCabeceraEntity();
                                                                        listaClienteCabeceraEntity.setCliente_id(lead.getCliente_id());
                                                                        listaClienteCabeceraEntity.setControl_id(lead.getControl_id());
                                                                        listaClienteCabeceraEntity.setItem_id(lead.getItem_id());
                                                                        listaClienteCabeceraEntity.setDomembarque_id(lead.getDomembarque_id());
                                                                        listaClienteCabeceraEntity.setDireccion(lead.getDireccion());
                                                                        listaClienteCabeceraEntity.setSaldo(lead.getSaldo());
                                                                        listaClienteCabeceraEntity.setNombrecliente("Cliente");
                                                                        ArraylistaClienteCabeceraEntity.add(listaClienteCabeceraEntity);
                                                                    }
                                                                    fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                                                                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                                    transaction.add(R.id.content_menu_view, hojaDespachoView.newInstanciaMenu(ArraylistaClienteCabeceraEntity));
                                                                }
                                                            }

        );*/
        return convertView;
    }
    static class ViewHolder {
        TextView tv_cliente;
        TextView tv_direccion;
        ImageView imv_delivery_status;
        ImageView imv_collection_status;
        ImageView imv_visit_status;
    }

    /*

    private Dialog alertDialogVisitSection(ListaHojaDespachoEntity lead,ViewModelStoreOwner viewModelStoreOwner) {

        final Dialog dialog = new Dialog(getContext());
        String fechainicio="",timeini="";
        ArrayList<VisitSectionEntity> listVisitSection=new ArrayList<>();
        StatusDispatchRepository statusDispatchRepository;

        dialog.setContentView(R.layout.layout_dialog_visit_section);
        CheckBox chk_start_visitsection,chk_finish_visitsection;
        Button btn_start_visitsection,btn_finish_visitsection,dialogButtonOK;
        chk_start_visitsection=dialog.findViewById(R.id.chk_start_visitsection);
        chk_finish_visitsection=dialog.findViewById(R.id.chk_finish_visitsection);
        btn_start_visitsection=dialog.findViewById(R.id.btn_start_visitsection);
        btn_finish_visitsection=dialog.findViewById(R.id.btn_finish_visitsection);
        dialogButtonOK=dialog.findViewById(R.id.dialogButtonOK);

        SimpleDateFormat dateFormathora = new SimpleDateFormat("HHmmss", Locale.getDefault());
        SimpleDateFormat FormatFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = new Date();
        //TextView textTitle = dialog.findViewById(R.id.tv_mensaje);
        //textTitle.setText("Elija Tipo de Venta:");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        VisitSectionEntity visitSectionEntity=new VisitSectionEntity();

        VisitSectionSQLite visitSectionSQLite=new VisitSectionSQLite(getContext());
        listVisitSection=visitSectionSQLite.getVisitSection(lead.getCliente_id(), lead.getDomembarque_id(), FormatFecha.format(date),lead.getControl_id());
        statusDispatchRepository = new ViewModelProvider(viewModelStoreOwner).get(StatusDispatchRepository.class);
        for(int i=0;i<listVisitSection.size();i++)
        {
            if(listVisitSection.get(i).getLatitudini()!=null)
            {
                chk_start_visitsection.setChecked(true);
                btn_start_visitsection.setEnabled(false);
                btn_start_visitsection.setClickable(false);
                Utilitario.disabledButtton(btn_start_visitsection);
            }
            if(listVisitSection.get(i).getLatitudfin()!=null)
            {
                if(!listVisitSection.get(i).getLatitudfin().equals("0"))
                {
                    chk_finish_visitsection.setChecked(true);
                    btn_finish_visitsection.setEnabled(false);
                    btn_finish_visitsection.setClickable(false);
                    Utilitario.disabledButtton(btn_finish_visitsection);
                }
            }
            fechainicio=listVisitSection.get(i).getDateini();
            timeini=listVisitSection.get(i).getTimeini();
        }


        btn_start_visitsection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<VisitSectionEntity> listVisitSectionini=new ArrayList<>();
                String Entrega="";
                UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
                DetailDispatchSheetSQLite detailDispatchSheetSQLite=new DetailDispatchSheetSQLite(getContext());
                ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetSQLite=new ArrayList<>();
                ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
                ArrayList<VisitSectionEntity> listVisitSectionValidation=new ArrayList<>();
                listVisitSectionValidation=visitSectionSQLite.getVisitSectionforDate(FormatFecha.format(date));
                listDetailDispatchSheetSQLite=detailDispatchSheetSQLite.getDetailDispatchSheetforControlID(lead.getControl_id(),lead.getItem_id());
                String direccion_id,zona_id;
                Log.e("REOS","StatusDispatchDialog.onCreateDialog.listDetailDispatchSheetSQLite.size():"+listDetailDispatchSheetSQLite.size());
                for(int i=0;i<listDetailDispatchSheetSQLite.size();i++)
                {
                    Entrega=listDetailDispatchSheetSQLite.get(i).getEntrega();
                }
                Log.e("REOS","MenuAccion-alertDialogVisitSection-btn_start_visitsection-longitude: "+longitude);
                Log.e("REOS","MenuAccion-alertDialogVisitSection-btn_start_visitsection-latitude: "+latitude);
                Log.e("REOS","MenuAccion-alertDialogVisitSection-btn_start_visitsection-FormatFecha.format(date): "+FormatFecha.format(date));
                Log.e("REOS","MenuAccion-alertDialogVisitSection-btn_start_visitsection-listVisitSectionValidation.SIZE(): "+listVisitSectionValidation.size());
                if(latitude!=0&&longitude!=0)
                {
                    if(listVisitSectionValidation.isEmpty())
                    {
                        visitSectionEntity.setCompania_id(ObjUsuario.compania_id);
                        visitSectionEntity.setFuerzatrabajo_id(ObjUsuario.fuerzatrabajo_id);
                        visitSectionEntity.setUsuario_id(ObjUsuario.usuario_id);
                        visitSectionEntity.setCliente_id(CardCode);
                        visitSectionEntity.setDomembarque_id(DomEmbarque_ID);
                        visitSectionEntity.setLatitudini(String.valueOf(latitude));
                        visitSectionEntity.setLongitudini(String.valueOf(longitude));
                        visitSectionEntity.setDateini(FormatFecha.format(date));
                        visitSectionEntity.setTimeini(dateFormathora.format(date));
                        visitSectionEntity.setLatitudfin("0");
                        visitSectionEntity.setLongitudfin("0");
                        visitSectionEntity.setDatefin("0");
                        visitSectionEntity.setTimefin("0");
                        visitSectionEntity.setChkrecibido("0");
                        visitSectionEntity.setIdref(Control_id);
                        visitSectionEntity.setIdrefitemid(Item_id);
                        visitSectionEntity.setLegalnumberref(Entrega);
                        listVisitSectionini.add(visitSectionEntity);
                        visitSectionSQLite.addVisitSection(listVisitSectionini);
                        chk_start_visitsection.setChecked(true);
                        btn_start_visitsection.setEnabled(false);
                        btn_start_visitsection.setClickable(false);

                        Utilitario.disabledButtton(btn_start_visitsection);
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.visit_start), Toast.LENGTH_LONG).show();
                        Utilitario.enableCardView(cv_dispatch, getContext(), imv_entrega);
                        Utilitario.enableCardView(cv_cobranza, getContext(), imv_cobranza);
                    }
                    else {
                        Toast.makeText(getContext(), getActivity().getResources().getString(R.string.mse_visits_no_finish), Toast.LENGTH_SHORT).show();
                        getalertListVisitUnClosed(listVisitSectionValidation,getActivity().getResources().getString(R.string.visit_open),getActivity().getResources().getString(R.string.mse_finish_visit_next)).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), getActivity().getResources().getString(R.string.mse_wait_moment_calculing_coordenates), Toast.LENGTH_SHORT).show();
                    getLocation();
                }
            }

        });
        btn_finish_visitsection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Latitudini="0",Longitudini="0";
                CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(getContext());
                StatusDispatchSQLite statusDispatchSQLite=new StatusDispatchSQLite(getContext());
                UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
                ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
                cobranzaDetalleSQLiteDao.getCountCollectionDate(FormatFecha.format(date),ObjUsuario.fuerzatrabajo_id,CardCode);


                if(
                        cobranzaDetalleSQLiteDao.getCountCollectionDate(FormatFecha.format(date),ObjUsuario.fuerzatrabajo_id,CardCode)>0
                                || statusDispatchSQLite.getCountStatusDispatchforDate(ObjUsuario.fuerzatrabajo_id,CardCode,Control_id,Item_id)   >0
                )
                {
                    Log.e("REOS","MenuAccion-alertDialogVisitSection-btn_finish_visitsection-longitude: "+longitude);
                    Log.e("REOS","MenuAccion-alertDialogVisitSection-btn_finish_visitsection-latitude: "+latitude);
                    if(latitude!=0&&longitude!=0)
                    {
                        listVisitSection = visitSectionSQLite.getVisitSection(CardCode, DomEmbarque_ID, FormatFecha.format(date),Control_id);
                        statusDispatchRepository = new ViewModelProvider(getActivity()).get(StatusDispatchRepository.class);
                        for (int i = 0; i < listVisitSection.size(); i++) {
                            fechainicio = listVisitSection.get(i).getDateini();
                            timeini = listVisitSection.get(i).getTimeini();
                            Latitudini = listVisitSection.get(i).getLatitudini();
                            Longitudini = listVisitSection.get(i).getLongitudini();
                        }
                        visitSectionSQLite.UpdateVisitSectionForClient(
                                CardCode, DomEmbarque_ID, FormatFecha.format(date), String.valueOf(latitude), String.valueOf(longitude), FormatFecha.format(date), dateFormathora.format(date)
                        );
                        chk_finish_visitsection.setChecked(true);
                        btn_finish_visitsection.setEnabled(false);
                        btn_finish_visitsection.setClickable(false);
                        Utilitario.disabledButtton(btn_finish_visitsection);

                        if (SesionEntity.perfil_id.equals("CHOFER") || SesionEntity.perfil_id.equals("chofer")) {

                            statusDispatchSQLite.UpdateTimeStatusDispatch(
                                    CardCode,
                                    DomEmbarque_ID, timeini, dateFormathora.format(date), Latitudini, Longitudini
                            );
                            AppExecutors executor=new AppExecutors();
                            /////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                            //statusDispatchRepository.statusDispatchSendTime(getContext()).observe(getActivity(), data -> {
                            statusDispatchRepository.statusDispatchSendTime(getContext(),executor.diskIO()).observe(getActivity(), data -> {
                                Log.e("REOS", "statusDispatchRepository-->statusDispatchSend-->resultdata" + data);
                            });

                        } else {
                            RutaVendedorSQLiteDao rutaVendedorSQLiteDao = new RutaVendedorSQLiteDao(getContext());
                            rutaVendedorSQLiteDao.UpdateChkVisitSection(
                                    CardCode,
                                    DomEmbarque_ID,
                                    ObjUsuario.compania_id,
                                    FormatFecha.format(date)
                            );
                        }
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.visit_finish), Toast.LENGTH_LONG).show();
                    }else
                    {
                        Toast.makeText(getContext(), getActivity().getResources().getString(R.string.mse_wait_moment_calculing_coordenates), Toast.LENGTH_SHORT).show();
                        getLocation();
                    }
                }else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.mse_no_generation_collection), Toast.LENGTH_LONG).show();
                    //alertdialogInformative(getContext(),"","No ah generado cobranza o despacho a este cliente,Seguro que desea Finalizar la Visita?").show();
                }

            }
        });
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    private void getLocation()
    {
        locationManager = (LocationManager) getActivity(). getSystemService(LOCATION_SERVICE);
        CobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        //****Mejora****
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            // AlertNoGps();
            androidx.fragment.app.DialogFragment dialogFragment = new AlertGPSDialogController();
            dialogFragment.show(((FragmentActivity) getContext()). getSupportFragmentManager (),"un dialogo");
        }
        // When you need the permission, e.g. onCreate, OnClick etc.
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.e("REOS","MenuAccionView: No tiene ACCESS_FINE_LOCATION ");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);

        } else {
            Log.e("REOS","MenuAccionView: si tiene ACCESS_FINE_LOCATION ");
            // We have already permission to use the location
            try {
                gpsController =  new GPSController(getContext());
                mLocation = gpsController.getLocation(mLocation);
                latitude = mLocation.getLatitude();
                longitude= mLocation.getLongitude();
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
            }

        }
    }*/

}

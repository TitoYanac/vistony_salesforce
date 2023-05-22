package com.vistony.salesforce.Controller.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.ImageCameraController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListHistoricStatusDispatchAdapter extends ArrayAdapter<HistoricStatusDispatchEntity> {
    LayoutInflater inflater;
    private android.content.Context Context;
    private List<HistoricStatusDispatchEntity> Listanombres = null;
    private ArrayList<HistoricStatusDispatchEntity> arrayList;
    static Bitmap bitmaplocal, bitmapguia;
    Activity activity;
    ProgressDialog pd;

    public ListHistoricStatusDispatchAdapter(Context context, List<HistoricStatusDispatchEntity> objects, Activity activity) {
        super(context, 0, objects);
        Context = context;
        this.Listanombres = objects;
        this.arrayList = new ArrayList<HistoricStatusDispatchEntity>();
        this.arrayList.addAll(objects);
        inflater = LayoutInflater.from(context);
        this.activity = activity;
    }


    public void filter(String charText) {
        try{
            charText = charText.toLowerCase(Locale.getDefault());
            Listanombres.clear();
            if (charText.length() == 0) {
                Listanombres.addAll(arrayList);
            } else {


                for (HistoricStatusDispatchEntity wp : arrayList) {
                    if (wp.getCliente().toLowerCase(Locale.getDefault()).contains(charText)) {
                        Listanombres.add(wp);
                    } else if (wp.getEntrega().toLowerCase().contains(charText)) {
                        Listanombres.add(wp);
                    }
                }

            }
        }catch (Exception e)
        {

        }
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return Listanombres.size();
    }


    @Override
    public HistoricStatusDispatchEntity getItem(int position) {
        return Listanombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListHistoricStatusDispatchAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_historic_statuc_dispatch,
                    parent,
                    false);

            holder = new ListHistoricStatusDispatchAdapter.ViewHolder();
            holder.tv_client = (TextView) convertView.findViewById(R.id.tv_client);
            holder.tv_referral_guide = (TextView) convertView.findViewById(R.id.tv_referral_guide);
            holder.tv_type_dispatch = (TextView) convertView.findViewById(R.id.tv_type_dispatch);
            holder.tv_reason_dispatch = (TextView) convertView.findViewById(R.id.tv_reason_dispatch);
            holder.imv_historic_status_dispatch_photo = (ImageView) convertView.findViewById(R.id.imv_historic_status_dispatch_photo);
            holder.imv_historic_status_dispatch_photo_delivery = (ImageView) convertView.findViewById(R.id.imv_historic_status_dispatch_photo_delivery);
            holder.chk_wsrecibido = (CheckBox) convertView.findViewById(R.id.chk_wsrecibido);
            holder.imv_historico_cobranza_respuesta_ws = (ImageView) convertView.findViewById(R.id.imv_historico_cobranza_respuesta_ws);
            holder.imv_observation = (ImageView) convertView.findViewById(R.id.imv_observation);
            holder.imb_consultar_QR = (ImageButton) convertView.findViewById(R.id.imb_consultar_QR);
            holder.lbl_wsrecibido = (TextView) convertView.findViewById(R.id.lbl_wsrecibido);
            holder.lbl_historico_cobranza_respuesta_ws = (TextView) convertView.findViewById(R.id.lbl_historico_cobranza_respuesta_ws);
            holder.imv_wsrecibido = (ImageView) convertView.findViewById(R.id.imv_wsrecibido);
            holder.tv_drivermobile = (TextView) convertView.findViewById(R.id.tv_driver_mobile);
            holder.tv_drivername = (TextView) convertView.findViewById(R.id.tv_driver_name);
            holder.imv_callmobilephone = (ImageView) convertView.findViewById(R.id.imv_callmobilephone);
            holder.imv_indicator_status = (ImageView) convertView.findViewById(R.id.imv_indicator_status);
            holder.tbr_drivername = (TableRow) convertView.findViewById(R.id.tbr_drivername);
            holder.tbl_drivermobile = (TableLayout) convertView.findViewById(R.id.tbl_drivermobile);
            holder.tr_amount = (TableRow) convertView.findViewById(R.id.tr_amount);
            holder.tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
            holder.lbl_referral_guide = (TextView) convertView.findViewById(R.id.lbl_referral_guide);



            //holder.imv_historic_status_dispatch_photo.setBackgroundResource(R.drawable.portail);
            convertView.setTag(holder);
        } else {
            holder = (ListHistoricStatusDispatchAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final HistoricStatusDispatchEntity lead = getItem(position);

        holder.tv_client.setText(lead.getCliente());
        if(BuildConfig.FLAVOR.equals("chile"))
        {
            holder.lbl_referral_guide.setText(R.string.invoice);
            holder.tv_referral_guide.setText(lead.getFactura());
        }else {
            holder.lbl_referral_guide.setText(R.string.delivery);
            holder.tv_referral_guide.setText(lead.getEntrega());
        }
        //holder.tv_referral_guide.setText(lead.getEntrega());
        holder.tv_type_dispatch.setText(lead.getTipoDespacho());
        holder.tv_reason_dispatch.setText(lead.getMotivoDespacho());
        holder.tv_drivermobile.setText(lead.getDrivermobile());
        holder.tv_drivername.setText(lead.getDrivername());
        holder.tv_amount.setText(Convert.currencyForView(lead.getAmount()));

        if(SesionEntity.perfil_id.equals("Chofer")||SesionEntity.perfil_id.equals("CHOFER"))
        {
            holder.tr_amount.setVisibility(View.GONE);
        }



        if(lead.getTipoDespacho_ID().equals("A"))
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_dangerous_24);
            holder.imv_indicator_status.setImageDrawable(draw);
            holder.imv_indicator_status.setEnabled(false);
        }
        else if(lead.getTipoDespacho_ID().equals("V"))
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_warning_24);
            holder.imv_indicator_status.setImageDrawable(draw);
            holder.imv_indicator_status.setEnabled(false);
        }
        else if(lead.getTipoDespacho_ID().equals("E"))
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_check_circle_24);
            holder.imv_indicator_status.setImageDrawable(draw);
            holder.imv_indicator_status.setEnabled(false);
        }
        else {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_warning_24);
            holder.imv_indicator_status.setImageDrawable(draw);
            holder.imv_indicator_status.setEnabled(false);
        }
        if (lead.getChk_Recibido().equals("1") || lead.getChk_Recibido().equals("Y")) {
            //holder.chk_wsrecibido.setChecked(true);
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_check_box_red_24);
            holder.imv_wsrecibido.setImageDrawable(draw);
            holder.imv_wsrecibido.setEnabled(false);
        }
        else {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_check_box_outline_blank_24);
            holder.imv_wsrecibido.setImageDrawable(draw);
            holder.imv_wsrecibido.setEnabled(false);
        }

        if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer"))
        {
            holder.tbr_drivername.setVisibility(View.GONE);
            holder.tbl_drivermobile.setVisibility(View.GONE);

        }else
        {
            //holder.chk_wsrecibido.setVisibility(View.GONE);
            holder.imv_historico_cobranza_respuesta_ws.setVisibility(View.GONE);
            holder.lbl_wsrecibido.setVisibility(View.GONE);
            holder.lbl_historico_cobranza_respuesta_ws.setVisibility(View.GONE);
            holder.imv_wsrecibido.setVisibility(View.GONE);
        }
        //alertdialogInformative(getContext(),lead.getObservacion());
        /*byte[] byteArray,byteArray2;
        Log.e("REOS","ListHistoricStatusDispatchAdapter.lead.getFotoLocal"+lead.getFotoLocal());
        byteArray = Base64.decode(lead.getFotoLocal(), Base64.DEFAULT);
        Log.e("REOS","ListHistoricStatusDispatchAdapter.byteArray.tostring"+byteArray.toString());
        Log.e("REOS","ListHistoricStatusDispatchAdapter.lead.getFotoGuia"+lead.getFotoGuia());
        byteArray2 = Base64.decode(lead.getFotoGuia(), Base64.DEFAULT);
        //byte[] pic = intent.getByteArrayExtra("pic");
        //capturedImage = (ImageView) findViewById(R.id.capturedImage);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(byteArray2, 0, byteArray2.length);*/
        //Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,holder.imv_historic_status_dispatch_photo.getWidth(),holder.imv_historic_status_dispatch_photo.getHeight(),false);
        /*ImageCameraController imageCameraController=new ImageCameraController();
        Bitmap bitmap,bitmap2;
        byte[] byteArray,byteArray2;
        byteArray=imageCameraController.getImageSDtoByte(getContext(),lead.getFotoLocal());
        byteArray2=imageCameraController.getImageSDtoByte(getContext(),lead.getFotoGuia());
        bitmap=BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        bitmap2=BitmapFactory.decodeByteArray(byteArray2, 0, byteArray2.length);
        holder.imv_historic_status_dispatch_photo.setImageBitmap(bitmap);
        holder.imv_historic_status_dispatch_photo_delivery.setImageBitmap(bitmap2);*/
        /*ImageCameraController imageCameraController=new ImageCameraController();
        imageCameraController.getImageSDtoByte(getContext(), lead.getFotoGuia());
        byte[] byteArray;
        byteArray=imageCameraController.getImageSDtoByte(getContext(),lead.getFotoLocal());
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        holder.imageViewPhoto.setImageBitmap(bitmap);*/

        holder.imv_historic_status_dispatch_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lead.getFotoLocal()!=null)
                {
                    if (SesionEntity.perfil_id.equals("Chofer") || SesionEntity.perfil_id.equals("CHOFER")) {
                    /*ImageCameraController imageCameraController = new ImageCameraController();
                    imageCameraController.OpenImageDispacth(getContext(), lead.getFotoLocal());*/
                    /*ImageCameraController imageCameraController=new ImageCameraController();
                    byte[] byteArray;
                    byteArray=imageCameraController.getImageSDtoByte(getContext(),lead.getFotoLocal());
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    getalertPhoto("FOTO LOCAL",bitmap).show();*/
                        String[] parametros = {lead.getFotoLocal(), "FOTO LOCAL"};
                        AsynktaskgetPhoto asynktaskgetPhoto = new AsynktaskgetPhoto();
                        asynktaskgetPhoto.execute(parametros);
                    } else {
                        Log.e("REOS", "ListHistoricStatusDispatchAdapter-getView-holder.imv_historic_status_dispatch_photo-lead.getFotoLocal()" + lead.getFotoLocal());
                    /*lead.getFotoLocal();
                    bitmaplocal=getBitmapFromURL(lead.getFotoLocal());
                    getalertPhoto("FOTO LOCAL",bitmaplocal).show();*/
                    /*Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try  {
                                bitmaplocal=getBitmapFromURL(lead.getFotoLocal());
                                getalertPhoto("FOTO LOCAL",bitmaplocal).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("REOS", "ListHistoricStatusDispatchAdapter-getView-holder.imv_historic_status_dispatch_photo_delivery-lead.getFotoLocal()-error" + e.toString());
                            }
                        }
                    });

                    thread.start();*/
                    /*activity.runOnUiThread(new Runnable() {
                        public void run() {
                            bitmaplocal = getBitmapFromURL(lead.getFotoLocal());
                            getalertPhoto("FOTO LOCAL", bitmaplocal).show();
                        }
                    });*/
                        String[] parametros = {lead.getFotoLocal(), "FOTO LOCAL"};
                        AsynktaskgetPhoto asynktaskgetPhoto = new AsynktaskgetPhoto();
                        asynktaskgetPhoto.execute(parametros);
                    }
                /*ImageCameraController imageCameraController=new ImageCameraController();
                byte[] byteArray,byteArray2;
                byteArray=imageCameraController.getImageSDtoByte(getContext(),lead.getFotoLocal());
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                getalertPhoto("FOTO DESPACHO",bitmap).show();*/
                }else {
                    Toast.makeText(getContext(), "No hay Foto de Local Disponible...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.imv_historic_status_dispatch_photo_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lead.getFotoGuia()!=null) {
                    if (SesionEntity.perfil_id.equals("Chofer") || SesionEntity.perfil_id.equals("CHOFER")) {
                    /*ImageCameraController imageCameraController = new ImageCameraController();
                    imageCameraController.OpenImageDispacth(getContext(), lead.getFotoGuia());*/
                    /*ImageCameraController imageCameraController=new ImageCameraController();
                    byte[] byteArray,byteArray2;
                    byteArray=imageCameraController.getImageSDtoByte(getContext(),lead.getFotoGuia());
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    getalertPhoto("FOTO GUIA",bitmap).show();*/
                        String[] parametros = {lead.getFotoGuia(), "FOTO GUIA"};
                        AsynktaskgetPhoto asynktaskgetPhoto = new AsynktaskgetPhoto();
                        asynktaskgetPhoto.execute(parametros);
                    } else {
                        Log.e("REOS", "ListHistoricStatusDispatchAdapter-getView-holder.imv_historic_status_dispatch_photo_delivery-lead.getFotoGuia()" + lead.getFotoGuia());
                        //lead.getFotoGuia();
                    /*Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try  {
                                bitmapguia=getBitmapFromURL(lead.getFotoGuia());
                                getalertPhoto("FOTO GUIA",bitmapguia).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("REOS", "ListHistoricStatusDispatchAdapter-getView-holder.imv_historic_status_dispatch_photo_delivery-lead.getFotoGuia()-error" + e.toString());
                            }
                        }
                    });
                    thread.start();*/

                   /* activity.runOnUiThread(new Runnable() {
                        public void run() {
                            bitmapguia = getBitmapFromURL(lead.getFotoGuia());
                            getalertPhoto("FOTO GUIA", bitmapguia).show();
                        }
                    });*/
                        String[] parametros = {lead.getFotoGuia(), "FOTO GUIA"};
                        AsynktaskgetPhoto asynktaskgetPhoto = new AsynktaskgetPhoto();
                        asynktaskgetPhoto.execute(parametros);


                    }
                }else {
                    Toast.makeText(getContext(), "No hay Foto de Guia Disponible...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.imv_historico_cobranza_respuesta_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertamostrarcomentario("Comentario Ws", lead.getMessageServerDispatch()).show();
            }
        });
        holder.imv_observation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertamostrarcomentario("Observacion", lead.getObservacion()).show();
            }
        });

        holder.imv_callmobilephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lead.getDrivermobile()!=null)
                {
                    String phone = lead.getDrivermobile();
                    Intent intent = new Intent(Intent.ACTION_DIAL,
                            Uri.fromParts("tel", phone, null));
                    activity.startActivity(intent);
                }
                else {
                    Toast.makeText(getContext(), "Numero de Telefono no Disponible...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return convertView;
    }

    static class ViewHolder {
        TextView tv_client;
        TextView tv_referral_guide;
        TextView tv_type_dispatch;
        TextView tv_reason_dispatch;
        ImageView imv_historic_status_dispatch_photo;
        ImageView imv_historic_status_dispatch_photo_delivery;
        CheckBox chk_wsrecibido;
        ImageView imv_historico_cobranza_respuesta_ws;
        ImageView imv_observation;
        ImageView imageViewPhoto;
        ImageButton imb_consultar_QR;
        TextView lbl_wsrecibido;
        TextView lbl_historico_cobranza_respuesta_ws;
        ImageView imv_wsrecibido;
        TextView tv_drivermobile;
        TextView tv_drivername;
        ImageView imv_callmobilephone;
        ImageView imv_indicator_status;
        TableRow tbr_drivername;
        TableLayout tbl_drivermobile;
        TextView tv_amount;
        TableRow tr_amount;
        TextView lbl_referral_guide;

    }

    private Dialog getalertPhoto(String foto, Bitmap bitmap) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_photo_informative);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText(foto);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        ImageView imageViewPhoto = (ImageView) dialog.findViewById(R.id.imageViewPhoto);
        imageViewPhoto.setImageBitmap(bitmap);
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
        return dialog;
    }

    private Dialog alertamostrarcomentario(String Titulo, String Comentario) {

        final Dialog dialog = new Dialog(Context);
        dialog.setContentView(R.layout.layout_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText(Titulo);

        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText((Comentario == null) ? "Sin Comentario" : Comentario);

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

        return dialog;
    }

    private Dialog alertdialogInformative(Context context, String texto) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        TextView textViewMsj = (TextView) dialog.findViewById(R.id.textViewMsj);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("IMPORTANTE!!!");
        /*if(SesionEntity.perfil_id.equals("CHOFER"))
        {
            textViewMsj.setText("El SMS fue enviado Correctamente!!!");
        }
        else
        {
            textViewMsj.setText("El SMS fue enviado Correctamente,solicitar al Cliente el codigo de SMS!!!");
        }*/
        textViewMsj.setText(texto);
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    public static Bitmap getBitmapFromURL(String url_image) {

        try {
            URL url = new URL(url_image);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }

    private class AsynktaskgetPhoto extends AsyncTask<String, Void, Bitmap> {
        String argumento = "";
        Bitmap bitmap;
        Object[] object;
        String titulo;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(activity);
            pd = ProgressDialog.show(activity, "Por favor espere", "Descargando Imagen...", true, false);
        }

        @Override
        protected Bitmap doInBackground(String... arg0) {
            try {
                bitmap=getBitmapFromURL(arg0[0]);
                titulo=arg0[1];
                //object[0]=bitmap;
                //object[1]=arg0[1];
            } catch (
                    Exception e) {
                e.printStackTrace();
                Log.e("REOS", "ListHistoricStatusDispatchAdaptar:ErrorHilo:AsynktaskgetPhoto-e" + e.getMessage());
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap Bitmap) {
            //Bitmap bitmapres = (Bitmap) objectres[0];
            //String titulo= (String) objectres[1];
            getalertPhoto(titulo,Bitmap).show();
            pd.dismiss();
        }

        public Bitmap getBitmapFromURL(String url_image) {

            try {
                URL url = new URL(url_image);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                Log.e("Bitmap", "returned");
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Exception", e.getMessage());
                return null;
            }
        }

        private Dialog getalertPhoto(String foto, Bitmap bitmap) {
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.layout_dialog_photo_informative);

            TextView textTitle = dialog.findViewById(R.id.text);
            textTitle.setText(foto);
            ImageView image = (ImageView) dialog.findViewById(R.id.image);
            ImageView imageViewPhoto = (ImageView) dialog.findViewById(R.id.imageViewPhoto);
            imageViewPhoto.setImageBitmap(bitmap);
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
            return dialog;
        }
    }
}

package com.vistony.salesforce.Controller.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vistony.salesforce.Controller.Utilitario.ImageCameraController;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoOrdenVentaEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.InvoicesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WareHousesEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListHistoricStatusDispatchAdapter extends ArrayAdapter<HistoricStatusDispatchEntity> {
    LayoutInflater inflater;
    private android.content.Context Context;
    private List<HistoricStatusDispatchEntity> Listanombres =null;
    private ArrayList<HistoricStatusDispatchEntity> arrayList;

    public ListHistoricStatusDispatchAdapter(Context context, List<HistoricStatusDispatchEntity> objects) {
        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        this.arrayList=new ArrayList<HistoricStatusDispatchEntity>();
        this.arrayList.addAll(objects);
        inflater = LayoutInflater.from(context);
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
            for(HistoricStatusDispatchEntity wp: arrayList)
            {
                if(wp.getCliente() .toLowerCase(Locale.getDefault()).contains(charText))
                {
                    Listanombres.add(wp);
                }
                else if(wp.getCliente_ID()  .toLowerCase(Locale.getDefault()).contains(charText))
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
            holder.tv_reason_dispatch= (TextView) convertView.findViewById(R.id.tv_reason_dispatch);
            holder.imv_historic_status_dispatch_photo= (ImageView) convertView.findViewById(R.id.imv_historic_status_dispatch_photo);
            holder.imv_historic_status_dispatch_photo_delivery= (ImageView) convertView.findViewById(R.id.imv_historic_status_dispatch_photo_delivery);
            holder.chk_wsrecibido= (CheckBox) convertView.findViewById(R.id.chk_wsrecibido);
            holder.imv_historico_cobranza_respuesta_ws= (ImageView) convertView.findViewById(R.id.imv_historico_cobranza_respuesta_ws);
            holder.imv_observation= (ImageView) convertView.findViewById(R.id.imv_observation);

            //holder.imv_historic_status_dispatch_photo.setBackgroundResource(R.drawable.portail);
            convertView.setTag(holder);
        } else {
            holder = (ListHistoricStatusDispatchAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final HistoricStatusDispatchEntity lead = getItem(position);

        holder.tv_client.setText(lead.getCliente());
        holder.tv_referral_guide.setText(lead.getEntrega());
        holder.tv_type_dispatch.setText(lead.getTipoDespacho());
        holder.tv_reason_dispatch.setText(lead.getMotivoDespacho());
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
                ImageCameraController imageCameraController=new ImageCameraController();
                imageCameraController.OpenImageDispacth(getContext(),lead.getFotoLocal());
                /*ImageCameraController imageCameraController=new ImageCameraController();
                byte[] byteArray,byteArray2;
                byteArray=imageCameraController.getImageSDtoByte(getContext(),lead.getFotoLocal());
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                getalertPhoto("FOTO DESPACHO",bitmap).show();*/

            }
        });
        holder.imv_historic_status_dispatch_photo_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getalertPhoto("FOTO LOCAL",bitmap2).show();
                ImageCameraController imageCameraController=new ImageCameraController();
                imageCameraController.OpenImageDispacth(getContext(),lead.getFotoGuia());
            }
        });
        holder.imv_historico_cobranza_respuesta_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertamostrarcomentario("Comentario Ws","").show();
            }
        });
        holder.imv_observation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertamostrarcomentario("Observacion",lead.getObservacion()).show();
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

    }

    private Dialog getalertPhoto(String foto,Bitmap bitmap) {
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
        return  dialog;
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
    private Dialog alertdialogInformative(Context context,String texto) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        TextView textViewMsj=(TextView) dialog.findViewById(R.id.textViewMsj);
        TextView text=(TextView) dialog.findViewById(R.id.text);
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
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }
}

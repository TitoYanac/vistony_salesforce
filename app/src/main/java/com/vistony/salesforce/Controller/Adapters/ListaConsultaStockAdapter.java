package com.vistony.salesforce.Controller.Adapters;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Dao.SQLite.PromocionCabeceraSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaConsultaStockEntity;
import com.vistony.salesforce.Entity.Adapters.ListaProductoEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.ConsultaStockView;
import com.vistony.salesforce.View.ProductoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaConsultaStockAdapter extends ArrayAdapter<ListaConsultaStockEntity> {

    public static ArrayList<ListaConsultaStockEntity> araylistaProductoEntity;
    private android.content.Context Context;
    private List<ListaConsultaStockEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<ListaConsultaStockEntity> arrayList;

    public ListaConsultaStockAdapter(android.content.Context context, List<ListaConsultaStockEntity> objects) {
        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaConsultaStockEntity>();
        this.arrayList.addAll(objects);
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
            for(ListaConsultaStockEntity wp: arrayList)
            {
                if(wp.getProducto_id().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    Listanombres.add(wp);
                }
                else if(wp.getProducto().toLowerCase(Locale.getDefault()).contains(charText))
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
    public ListaConsultaStockEntity getItem(int position) {
        return Listanombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        araylistaProductoEntity= new ArrayList <ListaConsultaStockEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaConsultaStockAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {


            convertView = inflater.inflate(R.layout.layout_lista_consulta_stock,parent,false);
            holder = new ListaConsultaStockAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_producto = (TextView) convertView.findViewById(R.id.tv_producto);
            holder.tv_productoid = (TextView) convertView.findViewById(R.id.tv_productoid);
            holder.tv_umd = (TextView) convertView.findViewById(R.id.tv_umd);
            holder.tv_stock = (TextView) convertView.findViewById(R.id.tv_stock);
            holder.tv_gal = (TextView) convertView.findViewById(R.id.tv_gal);
            holder.tv_price_cash = (TextView) convertView.findViewById(R.id.tv_price_cash);
            holder.tv_price_credit = (TextView) convertView.findViewById(R.id.tv_price_credit);
            holder.imv_enable_promotion = (ImageView) convertView.findViewById(R.id.imv_enable_promotion);
            holder.imv_enable_warehouses = (ImageView) convertView.findViewById(R.id.imv_enable_warehouses);

            holder.relativeListaConsultaStock=convertView.findViewById(R.id.relativeListaConsultaStock);
            convertView.setTag(holder);
        } else {
            holder = (ListaConsultaStockAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaConsultaStockEntity lead = getItem(position);

        // Setup.
        holder.tv_productoid.setText(lead.getProducto_id());
        holder.tv_producto.setText(lead.getProducto());
        holder.tv_umd.setText(lead.getUmd());
        holder.tv_price_cash.setText(lead.getPreciocontadoigv());
        holder.tv_price_credit.setText(lead.getPreciocreditoigv());
        holder.tv_stock.setText(lead.getStock());
        holder.tv_gal.setText(lead.getGal());

        if(lead.isPromotionenable())
        {
            Resources res2 = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res2.getDrawable( R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
            holder.imv_enable_promotion.setImageDrawable(draw);
            holder.imv_enable_promotion.setEnabled(true);

        }else
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable(R.drawable.ic_baseline_card_giftcard_24);
            holder.imv_enable_promotion.setImageDrawable(draw);
            holder.imv_enable_promotion.setEnabled(false);
        }

        holder.imv_enable_promotion.setOnClickListener(v -> {
                    Log.e("REOS", "ListaProductoAdapter.relativeListaProducto.lead.getStock():" + lead.getStock());
                    switch (BuildConfig.FLAVOR){
                        case "bolivia":
                        case "india":
                        case "ecuador":
                        case "chile":
                        case "peru":
                        case "paraguay":
                            ArrayList<ListaPromocionCabeceraEntity> listaListadoPromocionCabeceraEntity=new ArrayList<>();
                            PromocionCabeceraSQLiteDao promocionCabeceraSQLiteDao=new PromocionCabeceraSQLiteDao(getContext());
                            listaListadoPromocionCabeceraEntity=promocionCabeceraSQLiteDao.ObtenerPromocionCabeceraConsultaStock(
                                    SesionEntity.compania_id,
                                    SesionEntity.fuerzatrabajo_id,
                                    SesionEntity.usuario_id,
                                    lead.getProducto_id(),
                                    lead.getUmd()
                            );

                            ConsultaStockView.newInstanceEnviarPromocion(listaListadoPromocionCabeceraEntity);

                            break;
                        default:
                            break;
                    }
                }

        );

        holder.imv_enable_warehouses.setOnClickListener(v -> {
            ConsultaStockView.newInstanceSendWareHouses(lead.getProducto_id());
        });

        return convertView;
    }
    static class ViewHolder {
        TextView tv_producto;
        TextView tv_productoid;
        TextView tv_umd;
        TextView tv_stock;
        TextView tv_gal;
        TextView tv_price_cash;
        TextView tv_price_credit;
        ImageView imv_enable_promotion;
        RelativeLayout relativeListaConsultaStock;
        ImageView imv_enable_warehouses;
    }

    private Dialog alertaProductoSinStock(android.content.Context context, String texto, ListaProductoEntity lead) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_advertencia);

        TextView textMsj = dialog.findViewById(R.id.tv_texto);
        textMsj.setText(texto);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);


        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);

        dialogButtonOK.setOnClickListener(v -> {
            sendArrayProduct(lead);
            dialog.dismiss();
        });
        dialogButtonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    private void sendArrayProduct(ListaProductoEntity lead)
    {
        ListaProductoEntity listaProductoEntity=new ListaProductoEntity();
        listaProductoEntity.setProducto_id(lead.getProducto_id());
        listaProductoEntity.setProducto(lead.getProducto());
        listaProductoEntity.setUmd(lead.getUmd());
        listaProductoEntity.setStock(lead.getStock());
        listaProductoEntity.setPreciobase(lead.getPreciobase());
        listaProductoEntity.setPrecioigv(lead.getPrecioigv()) ;
        listaProductoEntity.setGal(lead.getGal());
        //listaProductoEntity.setPorcentaje_dsct(lead.getPorcentaje_dsct());
        ProductoView.newInstancia(listaProductoEntity);
    }

}

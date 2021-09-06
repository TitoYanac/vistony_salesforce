package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
/*
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Entity.Adapters.ListaCobranzaCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaConsDepositoEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.ConsDepositoView;

import java.util.ArrayList;
import java.util.List;

public class ListaConsDepositoAdapter extends
        ArrayAdapter<ListaConsDepositoEntity>
       // RecyclerView.Adapter<ListaConsDepositoAdapter.ViewHolder>
{
    public static List<ListaConsDepositoEntity> ArraylistaConsDepositoEntity;
    ListaCobranzaCabeceraEntity listaCobranzaCabeceraEntity;
    FragmentManager fragmentManager;
    private Context context;
    ConsDepositoView consDepositoView;
    boolean[] itemChecked;

    public ListaConsDepositoAdapter(
                                    Context context,
                                    List<ListaConsDepositoEntity> objects) {
        super(context,0, objects);
        ArraylistaConsDepositoEntity=objects;
      //  if(objects!=null)
      //  {
            this.itemChecked=new boolean[objects.size()];
      //  }

        this.context=context;
        SesionEntity.listaConsDeposito="0";
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        consDepositoView = new ConsDepositoView();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaConsDepositoAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_consdeposito,
                    parent,
                    false);

            holder = new ListaConsDepositoAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_recibo = (TextView) convertView.findViewById(R.id.tv_recibo_numero);
            holder.tv_nombrecliente = (TextView) convertView.findViewById(R.id.tv_nombrecliente_numero);
            holder.tv_nrodocumento = (TextView) convertView.findViewById(R.id.tv_documento_numero);
            holder.tv_importe = (TextView) convertView.findViewById(R.id.tv_importe_numero);
            holder.tv_cobrado = (TextView) convertView.findViewById(R.id.tv_cobrado_numero);
            holder.tv_saldo = (TextView) convertView.findViewById(R.id.tv_saldo_numero);
            holder.tv_nuevo_saldo = (TextView) convertView.findViewById(R.id.tv_nuevo_saldo_numero);
           // holder.tv_fecha = (TextView) convertView.findViewById(R.id.tv_fecha);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.tv_txtbancarizado = (TextView) convertView.findViewById(R.id.tv_txtbancarizado);
            holder.tv_txtpagodirecto = (TextView) convertView.findViewById(R.id.tv_txtpagodirecto);




            

          /*  ImageButton xd=convertView.findViewById(R.id.imv_flecha_historico_deposito);
            xd.setEnabled(false);

            LinearLayout er=convertView.findViewById(R.id.layoutAnular);
            er.setEnabled(false);
*/




            convertView.setTag(holder);
        } else {
            holder = (ListaConsDepositoAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaConsDepositoEntity lead = getItem(position);

        // Setup.
        holder.tv_recibo.setText(lead.getRecibo());
        holder.tv_nombrecliente.setText(lead.getNombrecliente());
        holder.tv_nrodocumento.setText(lead.getNrodocumento());
        holder.tv_importe.setText( Convert.currencyForView(lead.getImporte()));
        holder.tv_cobrado.setText(Convert.currencyForView(lead.getCobrado()));
        holder.tv_saldo.setText(Convert.currencyForView(lead.getSaldo()));
        holder.tv_nuevo_saldo.setText(Convert.currencyForView(lead.getNuevosaldo()));

        if(lead.getTv_txtbancarizado().equals("1"))
        {
            holder.tv_txtbancarizado.setText("SI");
        }
        else
            {
                holder.tv_txtbancarizado.setText("NO");
            }

        if(lead.getTv_txtpagodirecto().equals("1"))
        {
            holder.tv_txtpagodirecto.setText("SI");
        }
        else
        {
            holder.tv_txtpagodirecto.setText("NO");
        }

        if (holder.checkBox == null) {
            holder.checkBox = new CheckBox(context);
        }


        //holder.checkBox.setTag(position);

        holder.checkBox.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged (CompoundButton btn, boolean isChecked) {
                itemChecked[position] = isChecked;
            }
        });

        holder.checkBox.setChecked(itemChecked[position]);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     String validacheck="",validabancarizado="",validapagodirecto="";
                                                     int cantpagodirecto=0,cantbancarizado=0,cantcobronormal=0,cantcobrosnobancarizados=0;
                                                     //int cantcobrosnobancarizados=0;
                                                     boolean estado=holder.checkBox.isChecked();
                                                     //Colocar Check
                                                     if(holder.checkBox.isChecked())
                                                     {

                                                         for(int j=0;j<ArraylistaConsDepositoEntity.size();j++)
                                                         {
                                                             if(position==j)
                                                             {
                                                                 ArraylistaConsDepositoEntity.get(j).setCheckbox(true);
                                                             }
                                                             //evalua si tiene el check y el recibo es bancarizado
                                                             if(ArraylistaConsDepositoEntity.get(j).isCheckbox()&&ArraylistaConsDepositoEntity.get(j).getTv_txtbancarizado().equals("1")
                                                             )
                                                             {
                                                                 //validacheck="1";
                                                                 //validabancarizado="1";
                                                                 cantbancarizado++;

                                                             }
                                                             if(ArraylistaConsDepositoEntity.get(j).getTv_txtpagodirecto().equals("1")&&ArraylistaConsDepositoEntity.get(j).isCheckbox()
                                                                 //&&ArraylistaConsDepositoEntity.get(j).isCheckbox()
                                                             )
                                                             {
                                                                 cantpagodirecto++;
                                                             }
                                                             else if(
                                                                     ArraylistaConsDepositoEntity.get(j).getTv_txtbancarizado().equals("0")
                                                                             && ArraylistaConsDepositoEntity.get(j).getTv_txtpagodirecto().equals("0")
                                                                             &&ArraylistaConsDepositoEntity.get(j).isCheckbox()
                                                             )
                                                             {
                                                                 cantcobronormal++;
                                                             }


                                                         }



                                                         for(int l=0;l<ArraylistaConsDepositoEntity.size();l++)
                                                         {
                                                             //evalua si tiene el check y el recibo no es bancarizado, de ser positivo aumenta a p
                                                             if(ArraylistaConsDepositoEntity.get(l).isCheckbox()&& ArraylistaConsDepositoEntity.get(l).getTv_txtbancarizado().equals("0")
                                                             )
                                                             {
                                                                 cantcobrosnobancarizados++;
                                                             }
                                                         }

                                                         //evalua si el incremental es mayor a 0 y si el recibo es bancarizado
                                                        /* if(p>0&&lead.getTv_txtbancarizado().equals("1"))
                                                         {
                                                             {
                                                                 validacheck="1";
                                                                 validabancarizado="1";
                                                             }
                                                         }*/

                                                     }
                                                     //Quita Check
                                                     else
                                                     {

                                                         for(int k=0;k<ArraylistaConsDepositoEntity.size();k++)
                                                         {
                                                             ////busca la posicion en la lista y quita el check
                                                             if(position==k)
                                                             {
                                                                 ArraylistaConsDepositoEntity.get(k).setCheckbox(false);
                                                             }
                                                         }
                                                     }

                                                     //Evalua si las validaciones son positivas
                                                     if(
                                                         //validacheck.equals("1")&&validabancarizado.equals("1")&&
                                                             (cantcobrosnobancarizados>0&&cantbancarizado==1)||cantbancarizado>1)
                                                     {
                                                         Toast.makeText(getContext(),"Un Recibo Bancarizado Seleccionado debe ser Unico en el Deposito", Toast.LENGTH_SHORT).show();
                                                         holder.checkBox.setChecked(false);
                                                         for(int k=0;k<ArraylistaConsDepositoEntity.size();k++)
                                                         {
                                                             ////busca la posicion en la lista y quita el check
                                                             if(position==k)
                                                             {
                                                                 ArraylistaConsDepositoEntity.get(k).setCheckbox(false);
                                                             }
                                                         }
                                                     }
                                                     //Evalua si es
                                                     else if(cantpagodirecto>0&&(cantcobronormal>0||cantbancarizado>0))
                                                     {
                                                         Toast.makeText(getContext(),"Un Recibo que ingreso Como Pago Directo solo debe vincularse a otro Recibo como pago Directo", Toast.LENGTH_SHORT).show();
                                                         holder.checkBox.setChecked(false);
                                                         for(int k=0;k<ArraylistaConsDepositoEntity.size();k++)
                                                         {
                                                             ////busca la posicion en la lista y quita el check
                                                             if(position==k)
                                                             {
                                                                 ArraylistaConsDepositoEntity.get(k).setCheckbox(false);
                                                             }
                                                         }

                                                     }
                                                     //Evalua si las validaciones no son positivas
                                                     else
                                                     {
                                                         //iguala la lista con boolean estado
                                                         lead.checkbox=estado;


                                                         for(int i=0;i<ArraylistaConsDepositoEntity.size();i++)
                                                         {
                                                             //busca la posicion en la lista
                                                             if(position==i)
                                                             {
                                                                 //setea el estao eb la lista
                                                                 ArraylistaConsDepositoEntity.get(i).setCheckbox(estado);
                                                             }
                                                         }

                                                         //LLena variable estatica
                                                         SesionEntity.listaConsDeposito=String.valueOf(ArraylistaConsDepositoEntity.size());

                                                         //Envia Datos a Fragment
                                                         fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                                                         FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                         transaction.add(R.id.content_menu_view, consDepositoView.nuevaInstancia(ArraylistaConsDepositoEntity));

                                                     }
                                                 }
                                               //convertView.notifyDataSetChanged();
                                           }

        );




        //Glide.with(getContext()).load(lead.getImage()).into(holder.avatar);

        return convertView;
    }

    static class ViewHolder {
        //TextView lbl_documento;
        TextView tv_recibo;
        //TextView lbl_fecha_emision;
        TextView tv_nombrecliente;
        // TextView lbl_fecha_vencimiento;
        TextView tv_nrodocumento;
        // TextView lbl_importe;
        TextView tv_importe;
        TextView tv_cobrado;
        TextView tv_saldo;
        TextView tv_nuevo_saldo;
        //TextView tv_fecha;
        TextView tv_txtbancarizado;
        TextView tv_txtpagodirecto;
        CheckBox checkBox;

    }

    public Object ObtenerListaConsDeposito()
    {
        //ArraylistaConsDepositoEntity.size();
        ArrayList<ListaConsDepositoEntity> ObjetoListaConsDepositoEntity = new ArrayList<ListaConsDepositoEntity>();
        //ObjetoListaConsDepositoEntity=null;
        //if(ArraylistaConsDepositoEntity!=null)
        //{
            for(int i=0;i<ArraylistaConsDepositoEntity.size();i++)
            {
                if(ArraylistaConsDepositoEntity.get(i).isCheckbox())
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
                    listaConsDepositoEntity.tv_txtpagodirecto=ArraylistaConsDepositoEntity.get(i).getTv_txtpagodirecto();
                    ObjetoListaConsDepositoEntity.add(listaConsDepositoEntity);
                }
            }
        //}

        return ObjetoListaConsDepositoEntity;
    }
}

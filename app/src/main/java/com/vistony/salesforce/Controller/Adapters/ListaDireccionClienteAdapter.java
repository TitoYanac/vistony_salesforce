package com.vistony.salesforce.Controller.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.vistony.salesforce.Entity.Adapters.DireccionCliente;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.DireccionClienteView;

import java.util.ArrayList;

public class ListaDireccionClienteAdapter extends RecyclerView.Adapter<ListaDireccionClienteAdapter.ViewHolderPercona> {

    private ArrayList<DireccionCliente> listaDirecciones;
    private Context context;

    public ListaDireccionClienteAdapter(ArrayList<DireccionCliente>listaDirecciones, Context context){
        this.listaDirecciones=listaDirecciones;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolderPercona onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lista_direccion_cliente,null,false);
        return new ViewHolderPercona(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPercona holder, int position) {
        holder.tv_domembarque_id.setText(listaDirecciones.get(position).getDomembarque_id());
        holder.tv_zona.setText(listaDirecciones.get(position).getZona());
        holder.tv_direccion.setText(listaDirecciones.get(position).getDireccion());
        holder.tv_nombrefuerzatrabajo.setText(listaDirecciones.get(position).getNombrefuerzatrabajo());

        holder.relativeListaDireccionCliente.setOnClickListener(v ->{
            alertaHabilitarDireccionCliente(listaDirecciones.get(position),context).show();
        });
    }

    @Override
    public int getItemCount() {
        return listaDirecciones.size();
    }

    public class ViewHolderPercona extends  RecyclerView.ViewHolder{

        TextView tv_domembarque_id;
        TextView tv_zona;
        TextView tv_direccion;
        TextView tv_nombrefuerzatrabajo;
        RelativeLayout relativeListaDireccionCliente;

        public ViewHolderPercona(@NonNull View itemView) {
            super(itemView);

            tv_domembarque_id = (TextView) itemView.findViewById(R.id.tv_domembarque_id);
            tv_zona = (TextView) itemView.findViewById(R.id.tv_zona);
            tv_direccion = (TextView) itemView.findViewById(R.id.tv_direccion);
            tv_nombrefuerzatrabajo = (TextView) itemView.findViewById(R.id.tv_nombrefuerzatrabajo);
            relativeListaDireccionCliente=itemView.findViewById(R.id.relativeListaDireccionCliente);
        }
    }

    public AlertDialog alertaHabilitarDireccionCliente(DireccionCliente lead, Context contexto) {

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setTitle("Advertencia")
                .setMessage("Esta Seguro de Elegir la Direccion?")
                .setPositiveButton("OK",
                        (dialog, which) -> {

                            FragmentManager fragmentManager = ((AppCompatActivity) contexto).getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.add(R.id.content_menu_view, DireccionClienteView.newInstanceDevuelveDireccion(lead));

                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        return builder.create();
    }

}

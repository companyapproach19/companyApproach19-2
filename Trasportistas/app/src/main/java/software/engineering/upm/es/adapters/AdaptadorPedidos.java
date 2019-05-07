package software.engineering.upm.es.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import software.engineering.upm.es.R;
import software.engineering.upm.es.objetos.parceables.Pedido;

/**
 * Created by MATRIX on 31/10/16.
 */

public class AdaptadorPedidos  extends RecyclerView.Adapter<AdaptadorPedidos.PedidosViewHolder> implements View.OnClickListener {
    private View.OnClickListener listener;
    private ArrayList<Pedido> datos;

    public AdaptadorPedidos(ArrayList<Pedido> datos){

        this.datos=datos;
    }

    @Override
    public PedidosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflamos el layout para representar los elementos
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        //creamos un objeto AlumnosViewHolder para obtener las referencias a las vistas del layout y le pasamos la vista inflada con el layout
        PedidosViewHolder avh= new PedidosViewHolder(itemView);

        // fijamos el evento en la vista del elemento que acabamos de construir
        itemView.setOnClickListener(this);
        return avh;
    }

    @Override
    public void onBindViewHolder(PedidosViewHolder holder, int pos) {
        //obtenemos los datos a mostrar en esa posición
        Pedido item= datos.get(pos);
        // se los pasamos al método bindAlumnos del viewHolder para que los asigne
        holder.bindPedidos(item);

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    // codificamos el método del interfaz View.OnClickListener y le asignamos el listener de la actividad que contiene al RecyclerView
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    // sobreescribimos el método onClick del evento y lo delegamos  al listener de la actividad que contiene el RecyclerView para que lo procese
    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    public static class PedidosViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreTrasportista, empresa, fechaRecogida, fechaEntrega, id, estado;


        public PedidosViewHolder (View miVista){
            super(miVista);
            id = (TextView) miVista.findViewById(R.id.identificador);
            nombreTrasportista = (TextView) miVista.findViewById(R.id.nombre_trasportista);
            empresa = (TextView) miVista.findViewById(R.id.campo_empresa);
            fechaRecogida = (TextView) miVista.findViewById(R.id.fecha_recogida);
            fechaEntrega = (TextView) miVista.findViewById(R.id.fecha_entrega);
            estado = (TextView) miVista.findViewById(R.id.campo_estado);

        }

        public void bindPedidos(Pedido pedido){
            nombreTrasportista.setText(pedido.getTrasportista().getNombre());
            id.setText(""+pedido.getId());
            empresa.setText(pedido.getTrasportista().getEmpresa());
            fechaRecogida.setText(pedido.getTrasportista().getFecha_recogida());
            fechaEntrega.setText(pedido.getTrasportista().getFecha_entrega());

            String miEstado = "";
            if (pedido.getFirmadoRecogida()) {
                if (pedido.getFirmadoEntrega()) miEstado = "Entregado";
                else miEstado = "Está en camino";
            }
            else miEstado = "Listo para recoger";

            estado.setText(miEstado);

        }
    }
}


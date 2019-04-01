package software.engineering.upm.es;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

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

        private TextView trasportista;
        private TextView id;
        private TextView estado;
        private TextView datos;

        public PedidosViewHolder (View miVista){
            super(miVista);
            trasportista = (TextView) miVista.findViewById(R.id.nombre);
            id = (TextView) miVista.findViewById(R.id.matricula);
            estado = (TextView) miVista.findViewById(R.id.grupo);
            datos = (TextView) miVista.findViewById(R.id.anio);

        }
        public void bindPedidos(Pedido a){
            //foto.setImageResource(R.drawable.imagen_0);
            //trasportista.setText(a.getTrasportista());
            //id.setText(a.getId());
            //estado.setText(a.getEstado());
            //datos.setText(a.getDatos());

        }
    }
}


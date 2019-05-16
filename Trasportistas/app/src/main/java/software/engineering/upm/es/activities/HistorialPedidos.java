package software.engineering.upm.es.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import software.engineering.upm.es.R;
import software.engineering.upm.es.adapters.AdaptadorPedidos;
import software.engineering.upm.es.adapters.ItemDecorationSeparador;
import software.engineering.upm.es.objetos.SingletonPedidos;
import software.engineering.upm.es.objetos.parceables.Pedido;
import software.engineering.upm.es.objetos.parceables.Productos;
import software.engineering.upm.es.objetos.parceables.Trasportista;
import software.engineering.upm.es.retrofit.PedidosAPI;

public class HistorialPedidos extends AppCompatActivity {

    private RecyclerView miRecView;
    private AdaptadorPedidos adaptador;
    private int posicion = 0;

    private SingletonPedidos sp;
    private FloatingActionButton refresh;

    final int FICHA = 5;

    private PedidosAPI servicio;
    public static final String URL = "https://beer-company2019.herokuapp.com/damePedidosTransportistaEntregado/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedidos);

        refresh = (FloatingActionButton) findViewById(R.id.button_refresh);

        miRecView = (RecyclerView) findViewById(R.id.reciclador);

        sp = SingletonPedidos.getInstance();

        cargaAdaptador();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargaAdaptador();
            }
        });
    }

    public void cargaAdaptador () {

        // Añado uno por defecto
        if (URL != "") {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            servicio = retrofit.create(PedidosAPI.class);

            Call<Object> peticion = servicio.getPedidosE();
            // Toast.makeText(this,peticion.toString(), Toast.LENGTH_LONG).show();
            peticion.enqueue(new ObtenerResultados());

        }
        adaptador = new AdaptadorPedidos(sp.historialPedidos);

        fijaAdaptador();
    }

    private class ObtenerResultados implements Callback<Object> {
        @Override
        public void onResponse(Call<Object> call, Response <Object> response) {
            try {
                procesarConsulta(response.body());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<Object> call, Throwable t) {

            procesarError(t.getMessage());
        }
    }

    private void procesarConsulta (Object jsonString) throws JSONException {
        // Toast.makeText(this, jsonString.getClass().toString(), Toast.LENGTH_LONG).show();
        System.out.println(jsonString.toString());
        Gson g = new Gson ();
        JsonParser p = new JsonParser();
        List listaJson = (List)jsonString;
        for(Object e: listaJson) {

            JsonObject elem = p.parse(g.toJson(e)).getAsJsonObject();
            int id = elem.get("id").getAsInt();
            int estado = elem.get("estado").getAsInt();

            //System.out.println(elem.get("id"));

            Pedido ped = new Pedido(id,null);
            boolean b = comprobar(id);
            if (!b) {
                sp.historialPedidos.add(ped);
            }

        }
        //sp.historialPedidos.add(new Pedido(1));

    }
    private boolean comprobar(int id){
        boolean b = false;
        int i;
        for (i=0;i<sp.historialPedidos.size();i++){
            Pedido ped = sp.historialPedidos.get(i);
            if(ped.getId()==id) return true;
        }
        return b;
    }
    private void procesarError(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    private void fijaAdaptador() {

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // aqui procesamos el evento delegado por la vista en el AdaptadorAlumnos
                posicion= miRecView.getChildAdapterPosition(v);//devuelve la posision pulsada
                //Snackbar.make(miRecView,"la posicion es " + posicion,Snackbar.LENGTH_LONG).show();

                lanzadorPedidos(posicion);
            }
        });
        miRecView.setAdapter(adaptador);
        //fijamos el layoutManager
        //miRecView.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        miRecView.setLayoutManager(new GridLayoutManager(this, 1));
        //usamos un addItemDecoration para añadir una línea de separación entre cada elemento del RecyclerView
        miRecView.addItemDecoration(
                new ItemDecorationSeparador(this, ItemDecorationSeparador.VERTICAL_LIST));
        // miRecView.addItemDecoration(
        //       new ItemDecorationSeparador(this,ItemDecorationSeparador.HORIZONTAL_LIST));
        //fijamos la animación por defecto
        miRecView.setItemAnimator(new DefaultItemAnimator());
    }

    public void lanzadorPedidos (int posicion) {
        Intent i = new Intent(this, Ficha.class);

        i.putExtra("posicion", posicion);

        startActivityForResult(i,FICHA);
    }

    public void onActivityResult(int resultado, int codigo, Intent data) {
        if (resultado == FICHA && codigo == RESULT_OK) {
            cargaAdaptador();
        }
    }

}

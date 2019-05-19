package software.engineering.upm.es.activities;

import android.content.Intent;
import android.os.health.SystemHealthManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import retrofit2.Retrofit;

import software.engineering.upm.es.adapters.AdaptadorPedidos;
import software.engineering.upm.es.adapters.ItemDecorationSeparador;
import software.engineering.upm.es.R;
import software.engineering.upm.es.objetos.SingletonPedidos;
import software.engineering.upm.es.objetos.parceables.Pedido;
import software.engineering.upm.es.objetos.parceables.Productos;
import software.engineering.upm.es.objetos.parceables.Trasportista;
import software.engineering.upm.es.retrofit.PedidosAPI;

public class MainActivity extends AppCompatActivity {

    private RecyclerView miRecView;
    private AdaptadorPedidos adaptador;
    private int posicion = 0;

    private SingletonPedidos sp;
    private FloatingActionButton refresh;

    private PedidosAPI servicio;
    public static final String URL = "https://beer-company2019.herokuapp.com/damePedidosTransportistaListo/";//https://beer-company2019.herokuapp.com/damePedidosTransportista

    final int FICHA_RECOGIDA = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        if (URL != "") {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            servicio = retrofit.create(PedidosAPI.class);

            Call<Object> peticion = servicio.getPedidosL();
            // Toast.makeText(this,peticion.toString(), Toast.LENGTH_LONG).show();
            peticion.enqueue(new ObtenerResultados());

        }

        adaptador = new AdaptadorPedidos(sp.pedidosSinAsignar);

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
        if(jsonString == null){
            System.out.println("NULLLLLLLLL");
        }

        System.out.println(jsonString.toString());
        Gson g = new Gson ();
        JsonParser p = new JsonParser();
        List listaJson = (List)jsonString;
        for(Object e: listaJson){

            JsonObject elem = p.parse(g.toJson(e)).getAsJsonObject();
            int id = elem.get("id").getAsInt();
            //int estado = elem.get("estado").getAsInt();

            /*Los productos, no deberia salir fuera de rango*/
            JsonObject productos = (JsonObject) elem.get("productosPedidos");


            int cant_malta_palida = productos.get("cant_malta_base_palida").getAsInt();
            int cant_malta_munich = productos.get("cant_malta_munich").getAsInt();
            int cant_malta_negra = productos.get("cant_malta_negra").getAsInt();
            int cant_malta_crystal = productos.get("cant_malta_crystal").getAsInt();
            int cant_malta_chocolate = productos.get("cant_malta_chocolate").getAsInt();
            int cant_malta_caramelo = productos.get("cant_malta_caramelo").getAsInt();
            int cant_malta_pilsner = productos.get("cant_malta_pilsner").getAsInt();
            int cant_cebada_tostada = productos.get("cant_cebada_tostada").getAsInt();
            int cant_lupulo_centenial = productos.get("cant_lupulo_centennial").getAsInt();
            int cant_lupulo_perle = productos.get("cant_lupulo_perle").getAsInt();
            int cant_lupulo_tettnanger = productos.get("cant_lupulo_tettnanger").getAsInt();
            int cant_levadura_lager = productos.get("cant_levadura_lager").getAsInt();
            int cant_levadura_ale =  productos.get("cant_levadura_ale").getAsInt();
            int cant_lotes_stout =  productos.get("cant_lotes_stout").getAsInt();
            int cant_lotes_pilsner =  productos.get("cant_lotes_pilsner").getAsInt();

            //System.out.println(elem.get("id"));
            Productos prod = new Productos(cant_malta_palida,
             cant_malta_munich,
             cant_malta_negra,
             cant_malta_crystal,
             cant_malta_chocolate,
             cant_malta_caramelo,
             cant_malta_pilsner,
             cant_cebada_tostada,
             cant_lupulo_centenial,
             cant_lupulo_perle,
             cant_lupulo_tettnanger,
             cant_levadura_lager,
             cant_levadura_ale,
             cant_lotes_stout,
             cant_lotes_pilsner);

            Pedido ped = new Pedido(id,prod);

            boolean b = comprobar(id);
            if(!b){
            sp.pedidosSinAsignar.add(ped);
            }
        }





    }
    private boolean comprobar(int id){
        boolean b = false;
        int i;
        for (i=0;i<sp.pedidosSinAsignar.size();i++){
            Pedido ped = sp.pedidosSinAsignar.get(i);
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
        Intent i = new Intent(this, FichaRecogida.class);

        i.putExtra("posicion", posicion);

        startActivityForResult(i,FICHA_RECOGIDA);
    }

    public void onActivityResult(int resultado, int codigo, Intent data) {
        if (resultado == FICHA_RECOGIDA && codigo == RESULT_OK) {
            cargaAdaptador();
        }
    }

}

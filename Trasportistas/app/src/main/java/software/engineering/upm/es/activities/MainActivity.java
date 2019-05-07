package software.engineering.upm.es.activities;

import android.content.Intent;
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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
    public static final String URL = "https://beer-company2019.herokuapp.com/damePedidosTransportista/";//https://beer-company2019.herokuapp.com/damePedidosTransportista

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

            Call<Object> peticion = servicio.getPedidos();
           // Toast.makeText(this,peticion.toString(), Toast.LENGTH_LONG).show();
            peticion.enqueue(new ObtenerResultados());

            }
        else if (sp.contador == 0){
            // Añado uno por defecto
            Trasportista trasportista = new Trasportista("PePiTo", "Autónomo", "-/-/-", "-/-/-");
            sp.pedidosSinAsignar.add(0,new Pedido(trasportista,
                    123, false, false,
                    new Productos(1,2,3,4,
                            5,0,0,0,
                            0,0,0)));
            sp.pedidosSinAsignar.add(0,new Pedido(trasportista,
                    1234, false, false,
                    new Productos(1,2,3,4,
                            5,0,0,0,
                            0,0,0)));
            sp.contador = 1;
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
        //ArrayList j = (ArrayList) jsonString;
        Gson g = new Gson ();
        JsonParser p = new JsonParser();
        List listaJson = (List)jsonString;
        for(Object e: listaJson){
            JsonObject elem = p.parse(g.toJson(e)).getAsJsonObject();
            int id = elem.get("id").getAsInt();
            int estado = elem.get("estado").getAsInt();
            //System.out.println(elem.get("id"));
            if(estado == 1){
            Pedido ped = new Pedido(id);
            sp.pedidosSinAsignar.add(ped);
            }
        }
        //JsonObject j = p.parse(g.toJson(((List)jsonString).get(0))).getAsJsonObject();
        //System.out.println(j.get("id"));

       // JsonObject j = ((List)jsonString).get(0);
       // Toast.makeText(this, jsonString.getClass().toString(), Toast.LENGTH_LONG).show();
        //Toast.makeText(this, jsonString.toString(), Toast.LENGTH_LONG).show();
        //for (int i = 0; i < j.size(); i++) {
            //Toast.makeText(this, j.toString(), Toast.LENGTH_LONG).show();
            //sp.pedidosSinAsignar.add(new Pedido(j.get(i)));

        //}

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

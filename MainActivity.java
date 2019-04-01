package software.engineering.upm.es;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.ArrayList;

import retrofit2.Retrofit;
import software.engineering.upm.es.retrofit.PedidosAPI;

public class MainActivity extends AppCompatActivity {

    private RecyclerView miRecView;
    private AdaptadorPedidos adaptador;
    private int posicion = 0;
    private ArrayList<Pedido> pedidos;
    private FloatingActionButton refresh;

    private PedidosAPI servicio;

    private static final String URL = "http";


    final int FICHA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refresh = (FloatingActionButton) findViewById(R.id.button_refresh);

        pedidos = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        servicio = retrofit.create(PedidosAPI.class);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargaAdaptador();
            }
        });

        cargaAdaptador();
        fijaAdaptador();
    }

    public void cargaAdaptador () {
        Call<JSONArray> peticion;

        peticion = servicio.getPedidos();

        peticion.enqueue(new ObtenerResultados());


    }

    private class ObtenerResultados implements Callback<JSONArray> {
        @Override
        public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {

            try {
                procesarConsulta(response.body());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<JSONArray> call, Throwable t) {

            procesarError(t.getMessage());
        }
    }

    private void procesarConsulta (JSONArray jsonArray) throws JSONException {

        for (int i = 0; i < jsonArray.length(); i++) {
            pedidos.add(new Pedido(jsonArray.getJSONObject(i)));
        }

        miRecView = (RecyclerView) findViewById(R.id.reciclador);
        adaptador = new AdaptadorPedidos(pedidos);

    }

    private void procesarError(String mensaje){
        Toast.makeText(this, R.string.errorData, Toast.LENGTH_LONG).show();
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

        i.putExtra("pedido", pedidos.get(posicion));

        startActivityForResult(i,FICHA);
    }

    public void onActivityResult(int resultado, int codigo, Intent data) {
        if (resultado == FICHA && codigo == RESULT_OK) {
            pedidos.set(data.getExtras().getInt("pos"), (Pedido) data.getExtras().get("pedido"));
            adaptador = new AdaptadorPedidos(pedidos);
            fijaAdaptador();
        }
    }
}

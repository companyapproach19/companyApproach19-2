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
    private Call<JSONArray> peticion;

    public Call<JSONArray> getPeticion(){
        return this.peticion;
    }

    private PedidosAPI servicio;

    private static final String URL = "https://beer-company2019.herokuapp.com/";//"https:://beer-company2019.herokuapp.com/damePedidosTransportista";


    final int FICHA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refresh = (FloatingActionButton) findViewById(R.id.button_refresh);

        miRecView = (RecyclerView) findViewById(R.id.reciclador);

        pedidos = new ArrayList<>();
        // Añado uno por defecto
        Trasportista trasportista = new Trasportista("PePiTo", "Autónomo", "-/-/-", "-/-/-");
        pedidos.add(0,new Pedido(trasportista,
                123, false, false,
                new Productos(1,2,3,4,
                        5,0,0,0,
                        0,0,0)));
        pedidos.add(0,new Pedido(trasportista,
                123, false, false,
                new Productos(1,2,3,4,
                        5,0,0,0,
                        0,0,0)));

        if (URL != "") {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            servicio = retrofit.create(PedidosAPI.class);

            cargaAdaptador();
        }

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargaAdaptador();
            }
        });

        adaptador = new AdaptadorPedidos(pedidos);

        fijaAdaptador();
    }

    public void cargaAdaptador () {

        if (URL != "") {

            peticion = servicio.getPedidos(URL);

            peticion.enqueue(new ObtenerResultados());
        }
        else {

        }

/*
        // Se prepara el contenedor de datos para pasarselo a el adaptador
        datos = new ArrayList();
        //int imagenEst, String nombre, String matricula,String grupo,String anio, int imagenFoto
        datos.add(new Pedido("EDUARDO JAVIER ACUñA", "MM001", "android", "2015"));
        datos.add(new Pedido("SERGIO ALCALDE", "MM002", "android", "2015"));
        datos.add(new Pedido("SARA BAEZ", "MM003", "android", "2015"));
        datos.add(new Pedido("RUBEN JOSE DOMINGUEZ", "MM004", "android", "2015"));
        datos.add(new Pedido("DAVID GIMENEZ", "MM005", "android", "2015"));
        datos.add(new Pedido("JAVIER LAZA", "MM006", "android", "2015"));
        datos.add(new Pedido("OVIDIU MIRCEA", "MM007", "android", "2015"));
        datos.add(new Pedido("ESTHER MORENO", "MM008", "android", "2015"));
        datos.add(new Pedido("JUAN JOSE PEREZ", "MM009", "android", "2015"));
        datos.add(new Pedido("IGNACIO MATEO DEL POZO", "MM010", "android", "2015"));
        datos.add(new Pedido("ALBERTO AUGUSTO RODRIGUEZ", "MM011", "android", "2015"));
        datos.add(new Pedido("DENIS ROLDAN", "MM010", "android", "2015"));
        datos.add(new Pedido("ALVARO SAN JUAN", "MM011", "android", "2015"));


        // Inicializamos el RecyclerView
        miRecView = (RecyclerView) findViewById(R.id.reciclador);
        // Inicializamos el adaptador
        adaptador = new AdaptadorPedidos(datos);
        */
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

        i.putExtra("id", pedidos.get(posicion).getId());
        i.putExtra("trasportista", pedidos.get(posicion).getTrasportista());
        i.putExtra("pos", posicion);

        startActivityForResult(i,FICHA);
    }

    public void onActivityResult(int resultado, int codigo, Intent data) {
        if (resultado == FICHA && codigo == RESULT_OK) {
            pedidos.get(data.getExtras().getInt("pos")).setTrasportista((Trasportista) data.getExtras().get("trasportista"));
            adaptador = new AdaptadorPedidos(pedidos);
            fijaAdaptador();
        }
    }

}

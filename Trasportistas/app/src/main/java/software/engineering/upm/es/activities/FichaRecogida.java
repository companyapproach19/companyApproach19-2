package software.engineering.upm.es.activities;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import software.engineering.upm.es.R;
import software.engineering.upm.es.objetos.SingletonPedidos;
import software.engineering.upm.es.objetos.parceables.Pedido;
import software.engineering.upm.es.objetos.parceables.Trasportista;
import software.engineering.upm.es.retrofit.PedidosAPI;

public class FichaRecogida extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "MESSAGE";

    // Declaramos las Views
    private EditText fechR;
    private ImageButton selecFechR;
    private Button firma;

    private DrawingView drawView;

    private SingletonPedidos sp;

    // Para las fechas
    private int mMonth, mDay, mYear;

    // Recuperar la información que nos pasan
    private Trasportista trasportista;
    private int posicion;

    private PedidosAPI servicio;
    public static final String URL = "https://beer-company2019.herokuapp.com/recogidaOrden/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_recogida);

        // inicializamos el singleton
        sp = SingletonPedidos.getInstance();

        // Recuperamos los datos que nos han pasado
        posicion = getIntent().getExtras().getInt("posicion");
        //trasportista = sp.pedidosSinAsignar.get(posicion).getTrasportista();

        // Inicializamos nuestras views
        fechR = (EditText) findViewById(R.id.campo_date_recogida);

        // Introducimos la fecha por defecto
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        fechR.setText(mDay+"/"+mMonth+"/"+mYear);

        // Instanciamos nuestros botones
        selecFechR = (ImageButton) findViewById(R.id.button_date_recogida);
        selecFechR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarDialog(2);
            }
        });

        firma = (Button) findViewById(R.id.button_firma);

        firma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firmarPedido();
            }
        });

        drawView = (DrawingView)findViewById(R.id.drawing);

    }

    public void firmarPedido () {

        Intent intent = new Intent(this, MainActivity.class);

        if (fechR.getText().length() == 0 ) {
            Toast.makeText(this, "Se debe proporcionar una fecha de recogida", Toast.LENGTH_SHORT).show();
        }
        // Comprobar que se ha firmado
        //else if ()
         //   Toast.makeText(this, "No puedes cambiar el ID del pedido", Toast.LENGTH_SHORT).show();

        else {
            // Editamos los valores fecha y firma y cambiamos el pedido de lista
            //trasportista.setFecha_recogida(fechR.getText().toString());
            if (URL != "") {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                servicio = retrofit.create(PedidosAPI.class);
                Pedido pedido_a_enviar = sp.pedidosSinAsignar.get(posicion);

                JsonObject json = new JsonObject();

                json.addProperty("id",pedido_a_enviar.getId());
                System.out.print(pedido_a_enviar.getId());
                json.addProperty("firmaRecogida", "sldibavabv");
                JsonObject subJson = new JsonObject();
                subJson.addProperty("id",1);
                json.add("transportista",subJson);


                Call<Object> peticion = servicio.updatePedidoR(json);

                System.out.println("PUTTTTREcogido");
                // Toast.makeText(this,peticion.toString(), Toast.LENGTH_LONG).show();
                peticion.enqueue(new ObtenerResultados());

            }
            sp.pedidosSinAsignar.remove(posicion);
        }

        setResult(RESULT_OK, intent);
        finish();

    }

    private class ObtenerResultados implements Callback<Object> {
        @Override
        public void onResponse(Call<Object> call, Response<Object> response) {
            System.out.println("Paso por aqui");
            System.out.println(call);
            System.out.println(response);
        }

        @Override
        public void onFailure(Call<Object> call, Throwable t) {

            procesarError(t.getMessage());
        }
    }
    private void procesarError(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void calendarDialog (int i) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        fechR.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}

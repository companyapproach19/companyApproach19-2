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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import software.engineering.upm.es.R;
import software.engineering.upm.es.objetos.SingletonPedidos;
import software.engineering.upm.es.objetos.parceables.Trasportista;

public class Ficha extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "MESSAGE";

    // Declaramos las Views
    private TextView fechR, fechE, id;

    private SingletonPedidos sp;

    // Recuperar la información que nos pasan
    private Trasportista trasportista;
    private int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha);


        // inicializamos el singleton
        sp = SingletonPedidos.getInstance();

        // Recuperamos los datos que nos han pasado
        posicion = getIntent().getExtras().getInt("posicion");
        trasportista = sp.historialPedidos.get(posicion).getTrasportista();

        // Inicializamos nuestras views
        fechR = (TextView) findViewById(R.id.fecha_recogida);
        fechE = (TextView) findViewById(R.id.fecha_entrega);
        id = (TextView) findViewById(R.id.identificador);

        id.setText(""+sp.historialPedidos.get(posicion).getId());
        fechR.setText(sp.historialPedidos.get(posicion).getTrasportista().getFecha_recogida());
        fechE.setText(sp.historialPedidos.get(posicion).getTrasportista().getFecha_entrega());

    }
}

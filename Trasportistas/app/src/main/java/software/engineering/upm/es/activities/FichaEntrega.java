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

import java.util.Calendar;

import software.engineering.upm.es.R;
import software.engineering.upm.es.objetos.SingletonPedidos;
import software.engineering.upm.es.objetos.parceables.Trasportista;

public class FichaEntrega extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "MESSAGE";

    // Declaramos las Views
    private EditText fechE;
    private ImageButton selecFechE;
    private Button firma;

    private DrawingView drawView;

    private SingletonPedidos sp;

    // Para las fechas
    private int mMonth, mDay, mYear;

    // Recuperar la información que nos pasan
    private Trasportista trasportista;
    private int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_entrega);

        // inicializamos el singleton
        sp = SingletonPedidos.getInstance();

        // Recuperamos los datos que nos han pasado
        posicion = getIntent().getExtras().getInt("posicion");
        trasportista = sp.pedidosRecogidos.get(posicion).getTrasportista();

        // Inicializamos nuestras views
        fechE = (EditText) findViewById(R.id.campo_date_entrega);

        // Introducimos la fecha por defecto
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        fechE.setText(mDay+"/"+mMonth+"/"+mYear);

        // Instanciamos nuestros botones
        selecFechE = (ImageButton) findViewById(R.id.button_date_entrega);
        selecFechE.setOnClickListener(new View.OnClickListener() {
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

        Intent intent = new Intent(this, PedidosRecogidos.class);

        if (fechE.getText().length() == 0 ) {
            Toast.makeText(this, "Se debe proporcionar una fecha de recogida", Toast.LENGTH_SHORT).show();
        }
        // Comprobar que se ha firmado
        //else if ()
        //   Toast.makeText(this, "No puedes cambiar el ID del pedido", Toast.LENGTH_SHORT).show();

        else {
            // Editamos los valores fecha y firma y cambiamos el pedido de lista
            trasportista.setFecha_entrega(fechE.getText().toString());
            sp.historialPedidos.add(sp.pedidosRecogidos.remove(posicion));
        }

        setResult(RESULT_OK, intent);
        finish();

    }

    public void calendarDialog (int i) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        fechE.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}

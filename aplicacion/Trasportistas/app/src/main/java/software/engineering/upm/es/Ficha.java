package software.engineering.upm.es;


import android.content.Context;
import android.util.AttributeSet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Ficha extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_MESSAGE = "MESSAGE";

    // Declaramos las Views
    private EditText nombre, fechE, fechR;
    private TextView id;
    private ImageButton selecFechE, selecFechR;
    private Spinner spinner;
    private Button firma;

    private DrawingView drawView;

    public Pedido pedido;

    // Para las fechas
    private int mMonth, mDay, mYear;

    // Recuperar la información que nos pasan
    private Trasportista trasportista;
    private int pos;
    private int identificador;
    private Bundle extras;

    // Para la configuración del Spinner
    private String[] empresas;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha);

        // Recuperamos los datos que nos han pasado
        extras = getIntent().getExtras();
        trasportista = (Trasportista) extras.get("trasportista");
        identificador = extras.getInt("id");
        pos = extras.getInt("pos");

        // Inicializamos nuestras views
       // nombre = (EditText) findViewById(R.id.campo_nombre);
      //  id = (TextView) findViewById(R.id.campo_id);
        fechE = (EditText) findViewById(R.id.campo_date_entrega);
        fechR = (EditText) findViewById(R.id.campo_date_recogida);

        // Configuramos el spinner
      //  spinner = (Spinner) findViewById(R.id.spinner);
      /*  empresas = new String[]{"Empresa Paco", "Empresa Pepito", "Autónomo"};
        spinnerAdapter = new ArrayAdapter<String>(this ,android.R.layout.simple_spinner_dropdown_item, empresas);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this); */

        // Instanciamos nuestros botones
        selecFechE = (ImageButton) findViewById(R.id.button_date_entrega);
        selecFechE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarDialog(1);
            }
        });

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
                Intent intent = new Intent (v.getContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        // Introducimos la información de nuestras views
     /*   nombre.setText(trasportista.getNombre());
        fechR.setText(trasportista.getFecha_recogida());
        fechE.setText(trasportista.getFecha_entrega());*/

     /*   id.setText(""+identificador); */

        drawView = (DrawingView)findViewById(R.id.drawing);




    }

    public void firmarPedido () {
        if (nombre.getText().length() == 0 )
            Toast.makeText(this, "Falta Información", Toast.LENGTH_SHORT).show();
        if (Integer.parseInt(id.getText().toString()) != identificador)
            Toast.makeText(this, "No puedes cambiar el ID del pedido", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);

        trasportista.setNombre(nombre.getText().toString());
        trasportista.setFecha_recogida(fechR.getText().toString());
        trasportista.setFecha_entrega(fechE.getText().toString());

        intent.putExtra("trasportista", trasportista);
        intent.putExtra("pos", pos);
        setResult(RESULT_OK, intent);
        finish();

    }

    public void calendarDialog (int i) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        final int j = i;

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (j == 1)
                            fechE.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        else
                            fechR.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }




    public void firmarEntrega(){
        pedido.setFirmadoRecogida(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        trasportista.setEmpresa(empresas[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

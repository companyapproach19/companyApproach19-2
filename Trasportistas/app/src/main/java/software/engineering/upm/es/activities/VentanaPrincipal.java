package software.engineering.upm.es.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import software.engineering.upm.es.R;
import software.engineering.upm.es.activities.HistorialPedidos;
import software.engineering.upm.es.activities.MainActivity;
import software.engineering.upm.es.activities.PedidosRecogidos;

public class VentanaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_principal);


    }

    public void asignar_pedido (View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void pedido_recogido (View v) {
        startActivity(new Intent(this, PedidosRecogidos.class));
    }

    public void historial_pedidos (View v) {
        startActivity(new Intent(this, HistorialPedidos.class));
    }
}

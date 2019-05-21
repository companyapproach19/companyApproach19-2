package software.engineering.upm.es;

import android.animation.ObjectAnimator;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import software.engineering.upm.es.activities.MainActivity;
import software.engineering.upm.es.objetos.parceables.Pedido;
import software.engineering.upm.es.objetos.parceables.Productos;
import software.engineering.upm.es.retrofit.PedidosAPI;

import static org.junit.Assert.*;

public class PedidoTest {

    private MainActivity activity = new MainActivity();
    public static final String URL = "https://beer-company2019.herokuapp.com/damePedidosTransportistaListo/";//https://beer-company2019.herokuapp.com/damePedidosTransportista


    @Test
    public void test1(){
        Pedido pedido = new Pedido(1,null);
        assertNotNull(pedido);
    }

    @Test
    public void test2(){
        Pedido pedido = null;
        Productos prod=new Productos(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1);
        pedido = new Pedido(0,prod) ;
        assertNotNull(pedido);
    }





    @Test
    public void test3(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        PedidosAPI servicio = retrofit.create(PedidosAPI.class);
        Call<Object> peticion = servicio.getPedidosL();
        assertNotNull(peticion);
    }

    @Test
    public void test4(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        PedidosAPI servicio = retrofit.create(PedidosAPI.class);
        Call<Object> peticion = servicio.getPedidosR();
        assertNotNull(peticion);
    }

    @Test
    public void test5(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        PedidosAPI servicio = retrofit.create(PedidosAPI.class);
        Call<Object> peticion = servicio.getPedidosE();
        assertNotNull(peticion);
    }

    @Test
    public void test6(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        PedidosAPI servicio = retrofit.create(PedidosAPI.class);
        servicio.updatePedidoR(new JsonObject());
        assertNotNull(servicio.getPedidosR());
    }

    @Test
    public void test7(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        PedidosAPI servicio = retrofit.create(PedidosAPI.class);
        servicio.updatePedidoE(new JsonObject());
        assertNotNull(servicio.getPedidosE());
    }

}
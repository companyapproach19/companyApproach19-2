package software.engineering.upm.es;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class PedidoTest {

    private MainActivity activity = new MainActivity();

    @Test
    public void test(){
        try {
            Pedido pedido = new Pedido(new JSONObject(""));
            activity.cargaAdaptador();
            activity.getPeticion();
            assertEquals(activity.getPeticion(), pedido);
        } catch (Exception e){

        }

    }

    @Test
    public void test2(){
        try {
            Pedido pedido = new Pedido(new JSONObject(""));
            assertNotNull(pedido);
        } catch (Exception e){
            System.err.println("Salta excepcion!!!");
            fail();
        }
    }

    @Test
    public void test3(){
        try {
            Pedido pedido = new Pedido(new JSONObject(""));
            if(!pedido.getFirmadoEntrega()){
                System.err.println("No firmado para entregar");
                fail();
            } else {
                JSONObject res = new JSONObject(String.valueOf(pedido.getId()));
            }
        } catch (Exception e){
            System.err.println("Salta excepcion 1!!!");
            fail();
        }
    }

    @Test
    public void test4(){
        try {
            Pedido pedido = new Pedido(new JSONObject(""));
            if(!pedido.getFirmadoRecogida()){
                System.err.println("No firmado para recoger");
                fail();
            } else {
                JSONObject res = new JSONObject(String.valueOf(pedido.getId()));
            }
        } catch (Exception e){
            System.err.println("Salta excepcion 2!!!");
            fail();
        }
    }

    @Test
    public void test5(){

        HistorialPedidos historial = new HistorialPedidos();
        while(!historial.isFinishing()){
            // Send to 7th team
            break;
        }
        // Show the historial

    }

}
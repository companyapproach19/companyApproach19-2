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
            System.err.println(activity.getPeticion().toString());
            assertEquals(activity.getPeticion(), pedido);
        } catch (Exception e){
            System.out.println(activity.getPeticion());
            System.err.println("error");
        }

    }

   @Test
    public void test2(){
        try {
            Pedido pedido = new Pedido(new JSONObject("{\"id\":11,\"actorOrigen\":{\"id\":\"11\",\"nombreUsuario\":\"TranspG3_1\",\"passwordPlana\":\"password\",\"email\":\"tranpG3_1@gmail.es\",\"tipoActor\":2,\"localizacion\":\"40.4022076;-3.8369319\",\"nombre\":\"Maria\",\"direccion\":\"Calle Renacimiento\",\"cifcooperativa\":\"fg4\"},\"actorDestino\":{\"id\":\"12\",\"nombreUsuario\":\"TranspG3_2\",\"passwordPlana\":\"password\",\"email\":\"tranpG3_2@gmail.es\",\"tipoActor\":2,\"localizacion\":\"40.4029076;-3.8369319\",\"nombre\":\"Jose\",\"direccion\":\"Calle IlustraciÃ³n\",\"cifcooperativa\":\"fg5\"},\"necesitaTransportista\":true,\"productosPedidos\":{\"cant_malta_palida\":11,\"cant_malta_munich\":2,\"cant_malta_negra\":9,\"cant_malta_crystal\":2,\"cant_malta_chocolate\":1,\"cant_malta_caramelo\":7,\"cant_cebada\":8,\"cant_cebada_tostada\":0,\"cant_lupulo_centenial\":11,\"cant_lotes_stout\":4,\"cant_lotes_bisner\":7},\"productosAEntregar\":[1],\"estado\":1,\"firmaRecogida\":\"\",\"idRegistro\":11,\"idPedido\":1,\"fecha\":\"May 7, 2019\"}"));
            assertNotNull(pedido);
        } catch (Exception e){
            System.err.println("Salta excepcion!!!");
            fail();
        }
    }

    @Test
    public void test3(){
        try {
            Pedido pedido = new Pedido(new JSONObject("{\"id\":999,\"actorOrigen\":{\"id\":\"15\",\"nombreUsuario\":\"alberto\",\"passwordPlana\":\"password\",\"email\":\"alberto@gmail.es\",\"tipoActor\":1,\"localizacion\":\"40.4028076;-3.8368319\",\"nombre\":\"alberto\",\"direccion\":\"Avenida Jarales\",\"cifcooperativa\":\"fg3\"},\"actorDestino\":{\"id\":\"8\",\"nombreUsuario\":\"Fabrica\",\"passwordPlana\":\"password\",\"email\":\"fab@gmail.es\",\"tipoActor\":3,\"localizacion\":\"40.4085076;-3.8363919\",\"nombre\":\"Santiago\",\"direccion\":\"Calle AndalucÃ\u00ADa\",\"cifcooperativa\":\"fg3\"},\"necesitaTransportista\":true,\"productosPedidos\":{\"cant_malta_palida\":4,\"cant_malta_munich\":2,\"cant_malta_negra\":9,\"cant_malta_crystal\":2,\"cant_malta_chocolate\":4,\"cant_malta_caramelo\":7,\"cant_cebada\":8,\"cant_cebada_tostada\":6,\"cant_lupulo_centenial\":11,\"cant_lotes_stout\":4,\"cant_lotes_bisner\":7},\"productosAEntregar\":[],\"estado\":0,\"firmaRecogida\":\"\",\"idRegistro\":-1,\"idPedido\":-1,\"fecha\":\"May 7, 2019\"}"));
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
            Pedido pedido = new Pedido(new JSONObject("{\"id\":1,\"actorOrigen\":{\"id\":\"15\",\"nombreUsuario\":\"alberto\",\"passwordPlana\":\"password\",\"email\":\"alberto@gmail.es\",\"tipoActor\":1,\"localizacion\":\"40.4028076;-3.8368319\",\"nombre\":\"alberto\",\"direccion\":\"Avenida Jarales\",\"cifcooperativa\":\"fg3\"},\"actorDestino\":{\"id\":\"17\",\"nombreUsuario\":\"felipe\",\"passwordPlana\":\"password\",\"email\":\"felipe@gmail.es\",\"tipoActor\":2,\"localizacion\":\"40.4027076;-3.8368319\",\"nombre\":\"felipe\",\"direccion\":\"Avenida IlustraciÃ³n\",\"cifcooperativa\":\"fg1\"},\"necesitaTransportista\":true,\"productosPedidos\":{\"cant_malta_palida\":1,\"cant_malta_munich\":12,\"cant_malta_negra\":5,\"cant_malta_crystal\":0,\"cant_malta_chocolate\":4,\"cant_malta_caramelo\":7,\"cant_cebada\":8,\"cant_cebada_tostada\":10,\"cant_lupulo_centenial\":11,\"cant_lotes_stout\":0,\"cant_lotes_bisner\":1},\"productosAEntregar\":[1],\"estado\":0,\"firmaRecogida\":\"\",\"idRegistro\":-1,\"idPedido\":-1,\"fecha\":\"May 7, 2019\"}"));
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
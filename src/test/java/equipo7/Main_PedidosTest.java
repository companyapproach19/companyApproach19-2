package equipo7;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import equipo7.model.OrdenTrazabilidad;
import equipo7.otros.CooperativaOrdenes;
import equipo7.otros.DescodificadorJson;
import equipo7.otros.FabricaOrdenes;
import equipo7.otros.Main_pedidos;
import equipo7.otros.Orden;
import equipo7.otros.RetailerOrdenes;
import equipo7.otros.TiendaOrdenes;
import static org.junit.Assert.assertEquals;

public class Main_PedidosTest {
	
	private String pedido ;
	private DescodificadorJson este = new DescodificadorJson();
	private  Main_pedidos clase;
	public  OrdenTrazabilidad OrdenTrazabilidad;
	
	//TODO LO QUE VIENE A CONTINUCION ES DUDOSO, REPASAR URGENTEMENTE 
	@Test
	public void testMain_pedido() {
	     
		
		//ACIERTO
		assertEquals(new Main_pedidos("hola"), este.DescodificadorJson("hola"));
		//FALLO
		assertNotEquals(new Main_pedidos(pedido), null);
	}
	
	@BeforeClass
	public static Orden funcionCrearPedido(int codigo){
		Orden devolver = new Orden();
		switch(codigo) {
		case 1:
			devolver = new CooperativaOrdenes();
			break;
		case 2:
			devolver = new FabricaOrdenes();
			break;
		case 3:
			devolver = new RetailerOrdenes();
			break;
		case 4:
			devolver = new TiendaOrdenes();
			break;
		default:
			System.out.println("Error en el codigo de notificacion");
			break;
		}
		devolver.crearPedido();
		return devolver;
			}
			
	@Test
	public void testCrear_pedido() {
		assertEquals(new Main_pedidos(pedido),new Main_pedidos(pedido));
		assertNotEquals(new Main_pedidos(pedido),null);
		
	}
	

}

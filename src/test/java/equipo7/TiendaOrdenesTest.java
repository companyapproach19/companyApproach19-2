package equipo7;

import static org.junit.Assert.*;

import org.junit.Test;

import equipo6.model.Actor;
import equipo7.otros.RetailerOrdenes;
import equipo7.otros.TiendaOrdenes;

public class TiendaOrdenesTest {

	private Actor actor = new Actor();
	private TiendaOrdenes tiendaOrden = new TiendaOrdenes();

	@Test
	public void testNotificacion() {
	
		//ACIERTO
	      assertEquals("El pedido ha sido aceptado", tiendaOrden.notificacion(1));
	     //FALLO
	      assertNotEquals("El pedido ha sido aceptado", tiendaOrden.notificacion(15));
	}


}

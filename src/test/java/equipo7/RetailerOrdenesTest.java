package equipo7;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import equipo6.model.Actor;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo7.otros.FabricaOrdenes;
import equipo7.otros.RetailerOrdenes;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;


public class RetailerOrdenesTest {
	private Actor actor = new Actor();
	private RetailerOrdenes retailerOrden = new RetailerOrdenes();
	
	@Test
	public void testNotificacion() {
		//ACIERTOS
	      assertEquals("El pedido ha sido aceptado", retailerOrden.notificacion(1, actor));
	     assertEquals("El usuario "+actor.getNombreUsuario()+"desea encargarle el siguiente pedido", retailerOrden.notificacion(7, actor));
	     //FALLO
	      assertNotEquals("El pedido ha sido aceptado", retailerOrden.notificacion(15, actor));
		
		
		
	}

}

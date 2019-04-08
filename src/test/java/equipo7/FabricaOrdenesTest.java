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
import equipo7.otros.AgricultoresOrdenes;
import equipo7.otros.FabricaOrdenes;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

public class FabricaOrdenesTest {


	private Actor actor = new Actor();
	private FabricaOrdenes fabricaOrden = new FabricaOrdenes();

	@Test
	public void testNotificacion() {
		
		//ACIERTO
	     assertEquals("El usuario "+actor.getNombreUsuario()+"desea encargarle el siguiente pedido", fabricaOrden.notificacion(7, actor));
	     //FALLO
	      assertNotEquals("El pedido ha sido aceptado", fabricaOrden.notificacion(15, actor));
	}

	
}

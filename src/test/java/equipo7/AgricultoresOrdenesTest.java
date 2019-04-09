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
import equipo7.otros.TiendaOrdenes;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
public class AgricultoresOrdenesTest {


	private Actor actor = new Actor();
	private AgricultoresOrdenes agricultoresOrden = new AgricultoresOrdenes();
	
	@Test
	public void testNotificacion() {
		
		//ACIERTO
	     assertEquals("El usuario "+actor.getNombreUsuario()+" desea encargarle un pedido ", agricultoresOrden.notificacion(1, actor));
	     //FALLO
	     assertNotEquals("El pedido ha sido aceptado", agricultoresOrden.notificacion(15, actor));
		
	}

}

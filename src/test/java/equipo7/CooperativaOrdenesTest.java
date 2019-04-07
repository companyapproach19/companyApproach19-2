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
import equipo7.otros.CooperativaOrdenes;
import equipo7.otros.FabricaOrdenes;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

public class CooperativaOrdenesTest {


	private Actor actor = new Actor();
	private CooperativaOrdenes cooperativaOrden = new CooperativaOrdenes();

	@Test
	public void testNotificacion() {
		
		//ACIERTO
	     assertEquals("El usuario "+actor.getNombreUsuario()+" desea encargarle un siguiente pedido", cooperativaOrden.notificacion(3, actor));
	     //FALLO
	     assertNotEquals("El pedido ha sido aceptado", cooperativaOrden.notificacion(15, actor));
	}
}

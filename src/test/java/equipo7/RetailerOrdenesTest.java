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

import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class RetailerOrdenesTest {
	
	//-------------------------------------------------------------------------------------------------------
   //lo que es igual para todos

	
	   private int id; 
	   private String mensaje;
	   private Actor emisor;
	   private Actor receptor;
	   private Productos m;
	   private OrdenTrazabilidad pedido; 
	   // paraece que no hace falta private FabricaOrdenes receptorFabrica;
	   
	   
	   //para poder hacer test del switch
	   private int seleccion;
	   private String mensajeResultado;
	   
	   
	   

	   @Before
	   public void inicializarClase() {
		   Actor emisor =new Actor();
			Actor receptor =new Actor();
			Productos m=new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			 pedido=new OrdenTrazabilidad(2,"Fragil", emisor,receptor,m); 
			 
			// receptorFabrica=new FabricaOrdenes(pedido);
	   }

	   // Each parameter should be placed as an argument here
	   // Every time runner triggers, it will pass the arguments
	   // from parameters we defined in primeNumbers() method

	   //constructor de la clase test
	   public RetailerOrdenesTest(int inPut, String outPut) {
	      this.seleccion=inPut;
	      this.mensajeResultado=outPut;
	      
	   }	

	   @Parameterized.Parameters
	   public static Collection posiblesValores() {
	      return Arrays.asList(new Object[][] {
	         {1,"El pedido ha sido aceptado"},//
	         {2,"Su pedido se encuentra en proceso"},	     
	         {3,"Su pedido se encuentra listo para ser recogido"},
	         {4,"Su producto se encuentra en transporte"},
	         {5,"El producto ha sido entregado"},
	         {6,"El producto no ha sido aceptado"},
	         {7,"El usuario "+pedido.getActorOrigen()+"desea encargarle el siguiente pedido :"+this.getPedido().getProductos()},
	      });
	   }
//-------------------------------------------------------------------------------------------------------

	   /* 
parece que esto no hace falta porque es una asignación simplemente y porque 
lo que hay que comprobar son los métodos de la clase padre

	@Test
	public void testCrearPedido() {
		fail("Not yet implemented");
	}

	@Test
	public void testRetailerOrdenes() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testNotificacion() {
		
		
		
		
		
	}

}

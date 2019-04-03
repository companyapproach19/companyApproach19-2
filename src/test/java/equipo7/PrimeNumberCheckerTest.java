package equipo7;
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

import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PrimeNumberCheckerTest {
   private int id; 
   private String mensaje;
   private Actor emisor;
   private Actor receptor;
   private Productos m;
   private OrdenTrazabilidad pedido;

   @Before
   public void initialize() {//creo el objeto
	   Actor emisor =new Actor();
		Actor receptor =new Actor();
		Productos m=new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		 pedido=new OrdenTrazabilidad(2,"Fragil", emisor,receptor,m); 
   }

   // Each parameter should be placed as an argument here
   // Every time runner triggers, it will pass the arguments
   // from parameters we defined in primeNumbers() method
	
   public PrimeNumberCheckerTest(int id,String mensaje,Actor emisor,Actor receptor,Productos m) {
      this.id=id;
      this.mensaje=mensaje;
      this.emisor=emisor;
      this.m=m;
      
   }

   @Parameterized.Parameters
   public static Collection primeNumbers() {
      return Arrays.asList(new Object[][] {
         {1,"hola",new Actor(),new Actor(),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},//
         {2,"hola",new Actor(),new Actor(),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},//,
         {3,"hola",new Actor(),new Actor(),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},//
         {4,"hola",new Actor(),new Actor(),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},//
         {5,"hola",new Actor(),new Actor(),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},//,
         {6,"hola",new Actor(),new Actor(),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},//
      });
   }

   // This test will run 4 times since we have 5 parameters defined
   @Test
   public void testpedido() {//mensajes en caso de error
      pedido.setId(id);
      assertEquals(id,  pedido.getId());
      
      pedido.setActorOrigen(emisor);
      assertEquals(emisor,pedido.getActorOrigen());
      
      pedido.setActorDestino(receptor); 
      assertEquals(receptor,pedido.getActorDestino());
      
      pedido.setProductos(m);
      assertEquals(m, pedido.getProductos());
      
      pedido.setMensaje(mensaje);
      assertEquals(mensaje,pedido.getMensaje()); 
   }
}
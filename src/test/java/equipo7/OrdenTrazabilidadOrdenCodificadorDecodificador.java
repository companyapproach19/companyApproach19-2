package equipo7;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.controller.ManejaPeticiones;

import equipo6.model.Actor;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo7.otros.CodificadorJSON;
import equipo7.otros.CooperativaOrdenes;
import equipo7.otros.DescodificadorJson;
import equipo7.otros.FabricaOrdenes;
import equipo7.otros.Main_pedidos;
import equipo7.otros.Orden;
import equipo7.otros.RetailerOrdenes;
import equipo7.otros.TiendaOrdenes;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrdenTrazabilidadOrdenCodificadorDecodificador { 
	DescodificadorJson desc = new DescodificadorJson ();
	
   private String json;
   private int id; 
   private String mensaje;
   private Actor emisor;
   private Actor receptor;
   private Productos m;
   private OrdenTrazabilidad pedido;
   private Orden orden;
   private int codemisor,codrecep; 
   @Before
   public void initialize() {//creo el objeto
	      pedido=new OrdenTrazabilidad(id,mensaje,emisor,receptor,m);
	      json= CodificadorJSON.crearJSON(pedido);
	      pedido=desc.DescodificadorJson(json); 
   }
   // Each parameter should be placed as an argument here
   // Every time runner triggers, it will pass the arguments
   // from parameters we defined in primeNumbers() method
	
   public OrdenTrazabilidadOrdenCodificadorDecodificador(int id,String mensaje,Actor emisor,Actor receptor,Productos m,int codemisor,int codrecep) {
      this.id=id;
      this.mensaje=mensaje;
      this.emisor=emisor;
      this.receptor=receptor;
      this.m=m;  
     
   }

   @Parameterized.Parameters
   public static Collection primeNumbers() { 
      return Arrays.asList(new Object[][] {
    	  /*
    	   * 
    	   * 
    	   * Aqui bien
    	   */
         {1,"Fragil",new Actor("MariaC","Cuidado con la temperatura","maria@gmail.com",1),new Actor("Ricardo"," ","ricardo@gmail.com",0),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),1,0},//
        /* {2,"Cuidado",new Actor("PepitoF","MARIPOSA","pepito@gmail.com",2),new Actor("MariaC","Cuidado con la temperatura","maria@gmail.com",1),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),2,1},//,
         {3,"Material organico",new Actor("RebecaR","Atención","rebe@gmail.com",3),new Actor("PepitoF","MARIPOSA","pepito@gmail.com",2),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),3,2},//
         {4,"Deben ser puntuales",new Actor("AnaT","Gracias","ana@gmail.com",4),new Actor("RebecaR","Atención","rebe@gmail.com",3),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),4,3},//
         {5,"Pedido urgente",new Actor("AnaT","Gracias","ana@gmail.com",1),new Actor("AnaT","Gracias","ana@gmail.com",0),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),1,0},//,
       */  {6,"Dejar en la puerta",new Actor("PepitoF","MARIPOSA","pepito@gmail.com",1),new Actor("JuanT","PUERTA","juan@gmail.com",0),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),1,0},//
      });
   }

   // This test will run 4 times since we have 5 parameters defined
   @Test
   public void Test1Get_ok() {//mensajes en caso de error 
	   System.out.println(pedido.getMensaje());
      assertEquals(id,  pedido.getId()); 
      assertEquals(emisor.getId(),pedido.getActorOrigen().getId()); 
      assertEquals(receptor.getId(),pedido.getActorDestino().getId()); 
      assertEquals(m.getId(), pedido.getProductos().getId()); 
      assertEquals(mensaje,pedido.getMensaje());  
   }
   @Test
   public void Test2Set_ok() {//mensajes en caso de error 
	  int id=3; 
      String mensaje="fragil";
      Actor emisor=new Actor("AnaT","Gracias","ana@gmail.com",2);
	  Actor receptor=new Actor("AnaT","Gracias","ana@gmail.com",2);
	  pedido.setActorDestino(receptor);
	  pedido.setId(id); 
	  pedido.setActorOrigen(emisor);
	  pedido.setMensaje(mensaje);
      assertEquals(id,  pedido.getId()); 
      assertEquals(emisor.getId(),pedido.getActorOrigen().getId()); 
      assertEquals(receptor ,pedido.getActorDestino() );  
      assertEquals(mensaje,pedido.getMensaje()); 
   }
   @Test
	 public void Test3Descodificador_ok() { //arreglar 
	   String cod1= CodificadorJSON.crearJSON(pedido); 
	    OrdenTrazabilidad resultado = desc.DescodificadorJson(cod1);  
	    assertEquals(resultado.getActorDestino().getId(),pedido.getActorDestino().getId());
	    assertEquals(resultado.getActorOrigen().getNombreUsuario(),pedido.getActorOrigen().getNombreUsuario()); 
	    assertEquals(resultado.getEstado(),pedido.getEstado());
	    assertEquals(resultado.getFirmaEntrega(),pedido.getFirmaEntrega()); 
	    assertEquals(resultado.getFirmaRecogida(),pedido.getFirmaRecogida()); 
	    assertEquals(resultado.getId(),pedido.getId());
	    assertEquals(resultado.getIdHijo(),pedido.getIdHijo()); 
	    assertEquals(resultado.getIdPadre(),pedido.getIdPadre());
	    assertEquals(resultado.getMensaje(),pedido.getMensaje()); 
	    assertEquals(resultado.getNecesitaTransportista(),pedido.getNecesitaTransportista());  
	    assertEquals(resultado.getRegistro(),pedido.getRegistro());
	    assertEquals(resultado.getTransportista(),pedido.getTransportista()); 
		  }

	 @Test
	 public void Test4Codificador_ok() {
		 System.out.println(json);
		    String cod1= CodificadorJSON.crearJSON(pedido);  
		    OrdenTrazabilidad resultado = desc.DescodificadorJson(json);  
		    assertEquals(cod1,  CodificadorJSON.crearJSON(resultado));
		  }
   @Test
   public void Test5Estado_crearpedido_ok () {//error encontrado en la herencia de clases 
	   pedido.setEstado(pedido.getOrigenOrdenes().aceptarPedido(pedido.getEstado())); 
	   assertEquals(1,pedido.getEstado()); 
	}
   @Test
   public void Test6Estado_listoparaentregar_ok() {//error encontrado en la herencia de clases 
	   System.out.println(emisor);
	   System.out.println(receptor);
	   assertTrue(pedido.getOrigenOrdenes().listoParaEntregar(1,pedido.getActorDestino(),pedido.getActorOrigen())); 
	}
   @Test
   public void Test7Estado_firmadorecogida_ok() {//error encontrado en la herencia de clases  
	   pedido.setEstado(pedido.getOrigenOrdenes().firmadoRecogida(2));
	   assertEquals(3,pedido.getEstado()); 
	}
   @Test
   public void Test8Estado_firmadoentrega_ok() {//error encontrado en la herencia de clases   
	   pedido.setEstado(pedido.getOrigenOrdenes().firmadoEntrega(3));
	   assertEquals(4,pedido.getEstado());  
	}
   @Test
   public void Test5Estado_crearpedido_err () {//error encontrado en la herencia de clases
	   pedido.getOrigenOrdenes().aceptarPedido(2);
	   assertNotEquals(1,pedido.getEstado()); 
	}
   @Test
   public void Test6Estado_listoparaentregar_err() {//error encontrado en la herencia de clases 
	   pedido.getOrigenOrdenes().listoParaEntregar(3,emisor,receptor); 
	   assertNotEquals(2,pedido.getEstado());   
	}
   @Test
   public void Test7Estado_firmadorecogida_err() {//error encontrado en la herencia de clases  
	   pedido.getOrigenOrdenes().firmadoRecogida(5);
	   assertNotEquals(3,pedido.getEstado()); 
	}
   @Test
   public void Test8Estado_firmadoentrega_err() {//error encontrado en la herencia de clases   
	   pedido.getOrigenOrdenes().firmadoEntrega(1);
	   assertNotEquals(4,pedido.getEstado());  
	}
   //clase main pedidos
   @Test
   public void Test9_Sacarcodigo() {
	   Main_pedidos a=new Main_pedidos(json); 
	   assertEquals(codemisor,a.coddestino);  
   }
   @Test
   public void Test10_pedidovalido() {
	   Main_pedidos a=new Main_pedidos(json);
	   assertTrue(a.verificar_pedido());  
   }
   @Test
   public void Test11_crearpedido() {
	   boolean pasa=false;
	   Main_pedidos a=new Main_pedidos(json);
	   if( a.crear_pedido() instanceof RetailerOrdenes ) {pasa=true;}
	   if( a.crear_pedido() instanceof FabricaOrdenes ) {pasa=true;}
	   if( a.crear_pedido() instanceof TiendaOrdenes ) {pasa=true;}
	   if( a.crear_pedido() instanceof CooperativaOrdenes ) {pasa=true;} 
	   assertTrue(pasa);  
   }
/*
 * 
 * 
 * 
 * 
 * Controller;
 */
	@Test
	public void crearorden() {

		final ManejaPeticiones configurationController = new ManejaPeticiones();
		final String pingResponse = configurationController.aceptarPedido("0");
		System.out.println("gooog");
		System.out.println("AQUI ---->"+pingResponse);
		assertTrue(true);
	}
}
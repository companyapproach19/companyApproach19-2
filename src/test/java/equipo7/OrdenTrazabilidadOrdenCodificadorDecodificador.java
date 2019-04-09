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
import equipo7.otros.CodificadorJSON;
import equipo7.otros.DescodificadorJson;
import equipo7.otros.Orden;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

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
   @Before
   public void initialize() {//creo el objeto
	      pedido=new OrdenTrazabilidad(id,mensaje,emisor,receptor,m);
	      json= CodificadorJSON.crearJSON(pedido);
	      pedido=desc.DescodificadorJson(json); 
   }
   // Each parameter should be placed as an argument here
   // Every time runner triggers, it will pass the arguments
   // from parameters we defined in primeNumbers() method
	
   public OrdenTrazabilidadOrdenCodificadorDecodificador(int id,String mensaje,Actor emisor,Actor receptor,Productos m) {
      this.id=id;
      this.mensaje=mensaje;
      this.emisor=emisor;
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
         {1,"Fragil",new Actor("MariaC","Cuidado con la temperatura","maria@gmail.com",2),new Actor("Ricardo"," ","ricardo@gmail.com",2),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},//
         {2,"Cuidado",new Actor("PepitoF","MARIPOSA","pepito@gmail.com",2),new Actor("MariaC","Cuidado con la temperatura","maria@gmail.com",2),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},//,
         {3,"Material organico",new Actor("RebecaR","Atención","rebe@gmail.com",2),new Actor("PepitoF","MARIPOSA","pepito@gmail.com",2),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},//
         {4,"Deben ser puntuales",new Actor("AnaT","Gracias","ana@gmail.com",2),new Actor("RebecaR","Atención","rebe@gmail.com",2),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},//
         {5,"Pedido urgente",new Actor("AnaT","Gracias","ana@gmail.com",2),new Actor("AnaT","Gracias","ana@gmail.com",2),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},//,
         {6,"Dejar en la puerta",new Actor("PepitoF","MARIPOSA","pepito@gmail.com",2),new Actor("JuanT","PUERTA","juan@gmail.com",2),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},//
      });
   }

   // This test will run 4 times since we have 5 parameters defined
   @Test
   public void Test1Get_ok() {//mensajes en caso de error 
	   System.out.println(pedido.getMensaje());
      assertEquals(id,  pedido.getId()); 
      assertEquals(emisor.getId(),pedido.getActorOrigen().getId()); 
      assertEquals(receptor,pedido.getActorDestino()); 
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
	    assertEquals(resultado.getActorDestino(),pedido.getActorDestino());
	    assertEquals(resultado.getActorOrigen().getNombreUsuario(),pedido.getActorOrigen().getNombreUsuario()); 
	    assertEquals(resultado.getEstado(),pedido.getEstado());
	    assertEquals(resultado.getFirmaEntrega(),pedido.getFirmaEntrega()); 
	    assertEquals(resultado.getFirmaRecogida(),pedido.getFirmaRecogida()); 
	    assertEquals(resultado.getId(),pedido.getId());
	    assertEquals(resultado.getIdHijo(),pedido.getIdHijo()); 
	    assertEquals(resultado.getIdPadre(),pedido.getIdPadre());
	    assertEquals(resultado.getMensaje(),pedido.getMensaje()); 
	    assertEquals(resultado.getNecesitaTransportista(),pedido.getNecesitaTransportista());
	    assertEquals(resultado.getOrigenOrdenes(),pedido.getOrigenOrdenes());// 
	    assertEquals(resultado.getRegistro(),pedido.getRegistro());
	    assertEquals(resultado.getTransportista(),pedido.getTransportista()); 
		  }

	 @Test
	 public void Test4Codificador_ok() {
		    String cod1= CodificadorJSON.crearJSON(pedido);  
		    OrdenTrazabilidad resultado = desc.DescodificadorJson(json);  
		    assertEquals(cod1,  CodificadorJSON.crearJSON(resultado));
		  }
   @Test
   public void Test5Estado_crearpedido_ok () {//error encontrado en la herencia de clases
	   pedido.getOrigenOrdenes().aceptarPedido(pedido.getEstado());
	   assertEquals(1,pedido.getEstado()); 
	}
   @Test
   public void Test6Estado_listoparaentregar_ok() {//error encontrado en la herencia de clases 
	   pedido.getOrigenOrdenes().listoParaEntregar(1,emisor,receptor); 
	   assertEquals(2,pedido.getEstado()); 
	}
   @Test
   public void Test7Estado_firmadorecogida_ok() {//error encontrado en la herencia de clases  
	   pedido.getOrigenOrdenes().firmadoRecogida(2);
	   assertEquals(3,pedido.getEstado()); 
	}
   @Test
   public void Test8Estado_firmadoentrega_ok() {//error encontrado en la herencia de clases   
	   pedido.getOrigenOrdenes().firmadoEntrega(3);
	   assertEquals(4,pedido.getEstado());  
	}
   @Test
   public void Test5Estado_crearpedido_err () {//error encontrado en la herencia de clases
	   pedido.getOrigenOrdenes().aceptarPedido(2);
	   assertEquals(1,pedido.getEstado()); 
	}
   @Test
   public void Test6Estado_listoparaentregar_err() {//error encontrado en la herencia de clases 
	   pedido.getOrigenOrdenes().listoParaEntregar(3,emisor,receptor); 
	   assertEquals(2,pedido.getEstado());   
	}
   @Test
   public void Test7Estado_firmadorecogida_err() {//error encontrado en la herencia de clases  
	   pedido.getOrigenOrdenes().firmadoRecogida(5);
	   assertEquals(3,pedido.getEstado()); 
	}
   @Test
   public void Test8Estado_firmadoentrega_err() {//error encontrado en la herencia de clases   
	   pedido.getOrigenOrdenes().firmadoEntrega(1);
	   assertEquals(4,pedido.getEstado());  
	}
}
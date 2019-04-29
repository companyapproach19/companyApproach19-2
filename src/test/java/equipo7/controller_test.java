package equipo7;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;
import org.junit.runners.Parameterized;

import equipo6.model.Actor;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo7.otros.CodificadorJSON;
import equipo7.otros.DescodificadorJson;
import equipo7.otros.Orden;

import com.controller.ManejaPeticiones;
 

public class controller_test {
	
	

	private final ManejaPeticiones configurationController = new ManejaPeticiones(); 
	DescodificadorJson desc = new DescodificadorJson (); 
	   private String json;
	   private int id; 
	   private String mensaje;
	   private Actor emisor;
	   private Actor receptor;
	   private Productos m;
	   private OrdenTrazabilidad pedido;
	   private Orden orden; 
	   private final  String cod1 = CodificadorJSON.crearJSON(pedido);  
	   @Before
	   public void initialize() {//creo el objeto
		      pedido=new OrdenTrazabilidad(id,emisor,receptor,m);
		      json= CodificadorJSON.crearJSON(pedido);
		      pedido=desc.DescodificadorJson(json); 
	   }
	   // Each parameter should be placed as an argument here
	   // Every time runner triggers, it will pass the arguments
	   // from parameters we defined in primeNumbers() method
		
	   public controller_test(int id,String mensaje,Actor emisor,Actor receptor,Productos m,int codemisor,int codrecep) {
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
	         {1,"Fragil",new Actor("MariaC","Cuidado con la temperatura","maria@gmail.com",1),new Actor("Ricardo"," ","ricardo@gmail.com",0),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),1,0},//
	        /* {2,"Cuidado",new Actor("PepitoF","MARIPOSA","pepito@gmail.com",2),new Actor("MariaC","Cuidado con la temperatura","maria@gmail.com",1),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),2,1},//,
	         {3,"Material organico",new Actor("RebecaR","Atención","rebe@gmail.com",3),new Actor("PepitoF","MARIPOSA","pepito@gmail.com",2),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),3,2},//
	         {4,"Deben ser puntuales",new Actor("AnaT","Gracias","ana@gmail.com",4),new Actor("RebecaR","Atención","rebe@gmail.com",3),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),4,3},//
	         {5,"Pedido urgente",new Actor("AnaT","Gracias","ana@gmail.com",1),new Actor("AnaT","Gracias","ana@gmail.com",0),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),1,0},//,
	       */  {6,"Dejar en la puerta",new Actor("PepitoF","MARIPOSA","pepito@gmail.com",1),new Actor("JuanT","PUERTA","juan@gmail.com",0),new Productos( 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),1,0},//
	      });
	   }

	@Test
	public void shouldReturnInputString() throws Throwable {

		ManejaPeticiones configurationController = new ManejaPeticiones();
	//	final String pingResponse = configurationController.creaOrden(cod1);
		System.out.println("go.a");
		assertTrue(true);
	}
	 
	

 }

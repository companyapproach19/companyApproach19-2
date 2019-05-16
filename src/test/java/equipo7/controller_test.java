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
	   //1
	   @Test
	    public void TestCrearOrden() throws Throwable  {//Verificado
	        final ManejaPeticiones configurationController = new ManejaPeticiones();
	        String entry = json;//Codificar en un json el id de la peticion
	        String pingResponse  = configurationController.crearOrden(json);
	        assertEquals("OK",pingResponse);//Condicion necesaria para que una orden sea creada
	    }
	   //2
	   @Test
       public void TestObtenerOrden() throws Throwable  {//comprobar formato entrada
           String jsonid ="{name = 3}";//Comprobar
           String pingResponse1  = configurationController.obtenerOrden(json);  
           assertNotEquals("ERROR: no existe la orden asociada a este ID",pingResponse1);
           //descodificar
           OrdenTrazabilidad resp = desc.DescodificadorJson(pingResponse1);
           //boolean respuesta =buscarEnJson();
           assertTrue(resp.getId()==(id));
       }
	   //3
	    @Test
	    public void ListaOrdenesporAceptadar() throws Throwable  {//Pregunta : de los 3 constructores cual.
	        final ManejaPeticiones configurationController = new ManejaPeticiones();
	        String entry1 = "";//Codificar en un json el id de la peticion
	        String entry2 = "";//Codificar en un json el id de la peticion
	        configurationController.crearOrden(json);
	        //Condicion necesaria para que una orden sea aceptada (como esta comprobada arriba FUERA)
	        boolean is=BuscarEnArray(configurationController.ordenesPendientesPorAceptar(entry2));
	        assertTrue(is);//Si esta metida se pasa el test.
	    
	    }
	    //4 Falta
	    
	    //5
	    @Test
	    public void ListaOrdenesRechazadas() throws Throwable  {//Pregunta : de los 3 constructores cual.
	        final ManejaPeticiones configurationController = new ManejaPeticiones();
	        String entry1 = "";//id de la eticion a rechazar
	        String entry2 = "";//actor que tiene las ordenes rechazadas
	        configurationController.crearOrden(json);
	        //Condicion necesaria para que una orden sea aceptada (como esta comprobada arriba FUERA)
	        configurationController.rechazarOrden(entry1);
	        boolean is=BuscarEnArray(configurationController.ordenesRechazadas(entry2));
	        assertTrue(is);//Si esta metida se pasa el test.
	    
	    }
	    //6
	    @Test
	    public void TestOrdenesProcesadas() throws Throwable  {//Pregunta : de los 3 constructores cual.
	        final ManejaPeticiones configurationController = new ManejaPeticiones();
	        String entry1 = "";//Codificar en un json el id de la peticion
	        String entry2 = "";//Codificar en un json el id de la peticion
	        configurationController.crearOrden(json); //creamos la orden
	        configurationController.aceptarOrden(entry1);
	        boolean is=BuscarEnArray(configurationController.ordenesEnProceso(entry2));
	        assertTrue(is);//Si esta metida se pasa el test.
	    } 
	    //7
	    @Test
	    public void TestAceptarOrden() throws Throwable  {//Pregunta : de los 3 constructores cual.
	        final ManejaPeticiones configurationController = new ManejaPeticiones();
	        String entry = "";//Codificar en un json el id de la peticion
	        String pingResponse  = configurationController.crearOrden(entry);
	        assertEquals("OK",pingResponse);//Condicion necesaria para que una orden sea aceptada
	    }
	    //8
	    @Test
	    public void TestRechazarOrden() throws Throwable  {//Pregunta : de los 3 constructores cual.
	        final ManejaPeticiones configurationController = new ManejaPeticiones();
	        String entry1 = "";//id orden a rechazar
	        configurationController.crearOrden(json); //creamos la orden
	        String pingrensponse =configurationController.rechazarOrden(entry1);//rechazamos la orden
	        assertTrue(pingrensponse.equals("ok"));//comprobar salida
	    }
	    //9Falta
	    //10
    @Test
	    public void TestOrdenesRecogidas() throws Throwable  {//Pregunta : de los 3 constructores cual.
	        final ManejaPeticiones configurationController = new ManejaPeticiones();
	        String entry1 = "";//Codificar en un json el id de la peticion
	        String entry2 = "";//Codificar en un json el id de la peticion
	        configurationController.crearOrden(json);
	        configurationController.aceptarOrden(entry1);
	        configurationController.listaOrden(entry1);
	        configurationController.recogidaOrden(json);
	        boolean is=BuscarEnArray(configurationController.ordenesEnProcesoDeEntrega(entry2));
	        assertTrue(is);//Si esta metida se pasa el test.
	    }
    //11
		@Test
	    public void TestEntregadasOrden() throws Throwable  {
	        final ManejaPeticiones configurationController = new ManejaPeticiones();
	        String entry1 = "";//Codificar en un json el id de la peticion
	        String entry2 = "";//Codificar en un json el id de la peticion
	        configurationController.crearOrden(json);
	        configurationController.aceptarOrden(entry1);
	        configurationController.listaOrden(entry1);
	        configurationController.recogidaOrden(json);
	        String response =configurationController.entregadaOrden(json);
	        boolean is=BuscarEnArray(configurationController.entregadaOrden(entry2));
	        assertTrue(is);//Si esta metida se pasa el test.
	    }


	/*
	 * 
	 * 
	 * Funciones auxiliares
	 * 
	 * 
	 */

	    private boolean BuscarEnArray(String ordenesEnProcesoDeEntrega) {
			// TODO Auto-generated method stub
			return false;
		}
 }

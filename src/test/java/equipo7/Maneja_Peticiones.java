package equipo7;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.sql.Date;
import java.lang.reflect.Array; 
import java.text.SimpleDateFormat;
import java.util.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import equipo6.model.Actor;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo7.otros.CodificadorJSON;
import equipo7.otros.DescodificadorJson; 

import com.controller.ManejaPeticiones;
 
@RunWith(Parameterized.class)
public class Maneja_Peticiones {
	
	

	private 
	DescodificadorJson desc = new DescodificadorJson (); 
	   private String json;
	   private int id;  
	   private Actor emisor;
	   private Actor receptor;
	   private boolean necesitatransportista;
	   private Productos productos;
	   ArrayList<Integer> productosAEntregar;
	   private int estado;  
	   private String firmaRecogida;
	   private String firmaEntrega; 
	   private Actor transportista; 
	   private int idRegistro; 
	   private int idPedido;  
	   private Date fecha;
	   private OrdenTrazabilidad pedido;   
	   public final ManejaPeticiones configurationController;
	   @Before
	   public void initialize() {//creo el objeto
		     
		      pedido=new OrdenTrazabilidad(id,emisor,receptor,necesitatransportista,productos,productosAEntregar,estado,firmaRecogida,firmaEntrega,transportista,idRegistro,idPedido,fecha );
		      json= CodificadorJSON.crearJSON(pedido);
		      pedido=desc.DescodificadorJson( json); 
	   }
	   // Each parameter should be placed as an argument here
	   // Every time runner triggers, it will pass the arguments
	   // from parameters we defined in primeNumbers() method
		
	   public Maneja_Peticiones(int id, Actor actorOrigen, Actor actorDestino, boolean necesitaTransportista, 
				Productos productosPedidos, String  productosAEntregar, int estado, String firmaRecogida,
				String firmaEntrega, Actor transportista, int idRegistro, int idPedido, String fecha) {
		   this.id=id;
			this.emisor=actorOrigen;
			this.receptor=actorDestino;
			this.necesitatransportista=necesitaTransportista;
			this.productos=productosPedidos;
			//metemos en la lista
			String [] pasar=productosAEntregar.split(";");
			this.productosAEntregar=new ArrayList<Integer>();
			this.estado=estado;
			this.firmaRecogida=firmaRecogida;
			this.firmaEntrega=firmaEntrega;
			this.transportista=transportista;
			this.idRegistro=idRegistro;
			this.idPedido=idPedido; 
			this.fecha=new Date(1);
			configurationController = new ManejaPeticiones();
	     
	   }

	   @Parameterized.Parameters
	   public static Collection primeNumbers() { 
	      return Arrays.asList(new Object[][] {
	    	  /*
	    	   * 
	    	   * 
	    	   * Aqui bien
	    	   */
	         {-1,new Actor("Retailer","password","ret@gmail.es",4),new Actor("Fabrica","password","fab@gmail.es",3),true,new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),"80,81,82,83" ,-1,"","",new Actor("Fabrica","password","fab@gmail.es",3),2,2,""},//  
	        /* {2,"Cuidado",new Actor("PepitoF","MARIPOSA","pepito@gmail.com",2),new Actor("MariaC","Cuidado con la temperatura","maria@gmail.com",1),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),2,1},//,
	         {3,"Material organico",new Actor("RebecaR","Atención","rebe@gmail.com",3),new Actor("PepitoF","MARIPOSA","pepito@gmail.com",2),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),3,2},//
	         {4,"Deben ser puntuales",new Actor("AnaT","Gracias","ana@gmail.com",4),new Actor("RebecaR","Atención","rebe@gmail.com",3),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),4,3},//
	         {5,"Pedido urgente",new Actor("AnaT","Gracias","ana@gmail.com",1),new Actor("AnaT","Gracias","ana@gmail.com",0),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),1,0},//,
	         {6,"Dejar en la puerta",new Actor("PepitoF","MARIPOSA","pepito@gmail.com",1),new Actor("JuanT","PUERTA","juan@gmail.com",0),new Productos( 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),1,0},//
	     */ });
	   }
	   //1
	   @Test
	    public void TestCrearOrden() throws Throwable  {//Verificado
	       
	        String entry = json;//Codificar en un json el id de la peticion
	        String pingResponse  = configurationController.crearOrden(null,json); 
	        OrdenTrazabilidad resultado = desc.DescodificadorJson(pingResponse);  
	        assertNotEquals(id,resultado.getId());//Condicion necesaria para que una orden sea creada 
	        id=resultado.getId(); 
	        
	    }
	   //3
	    @Test
	    public void ListaOrdenesporAceptadar() throws Throwable  {//Pregunta : de los 3 constructores cual.
	       
	        String entry1 = "";//Codificar en un json el id de la peticion
	        String entry2 = "";//Codificar en un json el id de la peticion
	        configurationController.crearOrden(null,json);
	        //Condicion necesaria para que una orden sea aceptada (como esta comprobada arriba FUERA)
	        String pingResponse  =(configurationController.ordenesPendientesPorAceptar(null,emisor.getId()));
	        System.out.println(pingResponse);
	        
	        assertTrue(pingResponse.contains(""+id));//Si esta metida se pasa el test.
	    
	    }/*
	   //2
	   @Test
       public void TestObtenerOrden() throws Throwable  {//comprobar formato entrada 
		   TestCrearOrden();
           String jsonid =""+id;//Comprobar
           String pingResponse1  = configurationController.obtenerOrden(null,jsonid);  
           System.out.println(id);
           assertEquals("ERROR: no existe la orden asociada a este ID",pingResponse1);
          
           //descodificar
           OrdenTrazabilidad resp = desc.DescodificadorJson(pingResponse1);
           //boolean respuesta =buscarEnJson();
          // assertTrue(resp.getId()==(id));
       }
	   //3
	    @Test
	    public void ListaOrdenesporAceptadar() throws Throwable  {//Pregunta : de los 3 constructores cual.
	       
	        String entry1 = "";//Codificar en un json el id de la peticion
	        String entry2 = "";//Codificar en un json el id de la peticion
	        configurationController.crearOrden(null,json);
	        //Condicion necesaria para que una orden sea aceptada (como esta comprobada arriba FUERA)
	        boolean is=BuscarEnArray(configurationController.ordenesPendientesPorAceptar(null,entry2));
	        System.out.println(configurationController.ordenesPendientesPorAceptar(null,entry2));
	        System.out.println("\n\nAqui json \n\n"+json);
	        
	        assertTrue(is);//Si esta metida se pasa el test.
	    
	    }
	    //4 Falta
	    
	    //5
	    @Test
	    public void ListaOrdenesRechazadas() throws Throwable  {//Pregunta : de los 3 constructores cual.
	       
	        String entry1 = "";//id de la eticion a rechazar
	        String entry2 = "";//actor que tiene las ordenes rechazadas
	        configurationController.crearOrden(null,json);
	        //Condicion necesaria para que una orden sea aceptada (como esta comprobada arriba FUERA)
	        configurationController.rechazarOrden(null,entry1);
	        boolean is=BuscarEnArray(configurationController.ordenesRechazadas(null,entry2));
	        assertTrue(is);//Si esta metida se pasa el test.
	    
	    }
	    //6
	    @Test
	    public void TestOrdenesProcesadas() throws Throwable  {//Pregunta : de los 3 constructores cual.
	       
	        String entry1 = "";//Codificar en un json el id de la peticion
	        String entry2 = "";//Codificar en un json el id de la peticion
	        configurationController.crearOrden(null,json); //creamos la orden
	        configurationController.aceptarOrden(null,entry1);
	        boolean is=BuscarEnArray(configurationController.ordenesEnProceso(null,entry2));
	        assertTrue(is);//Si esta metida se pasa el test.
	    } 
	    //7
	    @Test
	    public void TestAceptarOrden() throws Throwable  {//Pregunta : de los 3 constructores cual.
	       
	        String entry = "";//Codificar en un json el id de la peticion
	        String pingResponse  = configurationController.crearOrden(null,entry);
	        assertEquals("OK",pingResponse);//Condicion necesaria para que una orden sea aceptada
	    }
	    //8
	    @Test
	    public void TestRechazarOrden() throws Throwable  {//Pregunta : de los 3 constructores cual.
	       
	        String entry1 = "";//id orden a rechazar
	        configurationController.crearOrden(null,json); //creamos la orden
	        String pingrensponse =configurationController.rechazarOrden(null,entry1);//rechazamos la orden
	        assertTrue(pingrensponse.equals("ok"));//comprobar salida
	    }
	    //9Falta
	    //10
    @Test
	    public void TestOrdenesRecogidas() throws Throwable  {//Pregunta : de los 3 constructores cual.
	       
	        String entry1 = "";//Codificar en un json el id de la peticion
	        String entry2 = "";//Codificar en un json el id de la peticion
	        configurationController.crearOrden(null,json);
	        configurationController.aceptarOrden(null,entry1);
	        configurationController.listaOrden(null,entry1);
	        configurationController.recogidaOrden(null,json);
	        boolean is=BuscarEnArray(configurationController.ordenesEnProcesoDeEntrega(null,entry2));
	        assertTrue(is);//Si esta metida se pasa el test.
	    }
    //11
		@Test
	    public void TestEntregadasOrden() throws Throwable  {
	       
	        String entry1 = "";//Codificar en un json el id de la peticion
	        String entry2 = "";//Codificar en un json el id de la peticion
	        configurationController.crearOrden(null,json);
	        configurationController.aceptarOrden(null,entry1);
	        configurationController.listaOrden(null,entry1);
	        configurationController.recogidaOrden(null,json);
	        String response =configurationController.entregadaOrden(null,json);
	        boolean is=BuscarEnArray(configurationController.entregadaOrden(null,entry2));
	        assertTrue(is);//Si esta metida se pasa el test.
	    }

*/
	/*
	 * 
	 * 
	 * Funciones auxiliares
	 * 
	 * 
	 */

	    private boolean BuscarEnArray(String ordenesEnProcesoDeEntrega) {
	    	String a=""+id;
			return ordenesEnProcesoDeEntrega.contains(a);
		}
 }

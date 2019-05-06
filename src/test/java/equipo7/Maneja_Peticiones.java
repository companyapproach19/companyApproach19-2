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
	   private OrdenTrazabilidad OrdenCreada;
	   private String jsonOrdenCreada;
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
		
	   public void actualizarjson() {
		   json= CodificadorJSON.crearJSON(pedido);
	   }
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
	         {-1,new Actor("9","Retailer",4),new Actor("8","Fabrica",3),true,new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),"80,81,82,83" ,-1,"","",new Actor("7","Fabrica",3),2,2,""},//  
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
	        pedido.setId(id);
	        actualizarjson(); 
	        
	    }
	      //2
		   @Test
	       public void TestObtenerOrden() throws Throwable  {//comprobar formato entrada 
			   TestCrearOrden();
	           String jsonid =""+id;//Comprobar
	           String pingResponse1  = configurationController.obtenerOrden(null,jsonid); 
	           this.OrdenCreada=desc.DescodificadorJson( json);
	           assertEquals(OrdenCreada.getId(),pedido.getId());  
	           assertEquals(OrdenCreada.getActorDestino().toString(),pedido.getActorDestino().toString());  
	           assertEquals(OrdenCreada.getActorOrigen().toString(),pedido.getActorOrigen().toString());  
	           //cambiar comprobacion
	       } 
	   //3 
		    
	    @Test
	    public void ListaOrdenesporAceptar() throws Throwable  { 
	    	TestCrearOrden(); 
	        String pingResponse2=configurationController.ordenesPendientesPorAceptar(null,""+receptor.getId());
	        assertTrue(pingResponse2.contains(""+id)); 
	    }
	    //4
	    @Test
	    public void OrdenesQueHeEnviado() throws Throwable  { 
	    	TestCrearOrden(); 
	        String pingResponse2=configurationController.ordenesQueHeEnviado(null, this.OrdenCreada.getActorOrigen().getId());
	        assertTrue(pingResponse2.contains(""+id)); 
	    }
	    //5
	    @Test
	    public void TestOrdenesEnProceso() throws Throwable  {//Pregunta : de los 3 constructores cual.
	    	TestObtenerOrden();
	    	String pingResponse  = configurationController.aceptarOrden(null, ""+id); 
	        this.OrdenCreada=desc.DescodificadorJson(pingResponse); 
	        pingResponse = configurationController.ordenesEnProceso(null,""+id);
	        boolean is=BuscarEnArray(pingResponse,""+id);
	        assertTrue(is);
	    } 
	    //6
	    @Test
	    public void ordenesListasParaEntregar() throws Throwable  {//Pregunta : de los 3 constructores cual.
	    	TestCrearOrden(); 
	        String pingResponse  = configurationController.aceptarOrden(null, ""+id); 
	        String entry ="";//poner aqui el id y lo que entregamos
	        configurationController.listaOrden(null, entry);
	        String pingresponse =configurationController.ordenesListasParaEntregar(null, this.OrdenCreada.getActorDestino().getId());
	        boolean is=BuscarEnArray(pingresponse,""+id);
	        assertTrue(is);//Si esta metida se pasa el test.
	    
	    }
	   
	    //7
	    @Test
	    public void ListaOrdenesRechazadas() throws Throwable  {//Pregunta : de los 3 constructores cual.
	    	TestObtenerOrden();
	    	String pingResponse  = configurationController.rechazarOrden(null, ""+id); 
	    	 this.OrdenCreada=desc.DescodificadorJson(pingResponse);
	    	 pingResponse=configurationController.ordenesRechazadas(null,""+OrdenCreada.getActorOrigen().getId());
	        boolean is=BuscarEnArray(pingResponse,""+id);
	        assertTrue(is);//Si esta metida se pasa el test.
	    
	    }
	    //8
	    public void TestOrdenesEnProcesoDeEntrega() throws Throwable  { 
	    	TestCrearOrden(); 
	        String pingResponse  = configurationController.aceptarOrden(null, ""+id); 
	        String entry =""; 
	        configurationController.listaOrden(null, entry);
	        entry=""; 
	        configurationController.recogidaOrden(null, entry);
	        pingResponse =configurationController.ordenesEnProcesoDeEntrega(null, pedido.getActorOrigen().getId());
	        boolean is=BuscarEnArray(pingResponse,""+id);
	        assertTrue(is); 
	    }
	    //9
	    @Test
	    public void TestAceptarOrden() throws Throwable  {
	    	TestCrearOrden();
	        String pingResponse  = configurationController.aceptarOrden(null, ""+id); 
	        this.OrdenCreada=desc.DescodificadorJson(pingResponse); 
	        assertEquals(OrdenCreada.getEstado(),1);
	    }
	    //10
	    @Test
	    public void TestRechazarOrden() throws Throwable  { 
	    	TestCrearOrden();
	        String pingResponse  = configurationController.rechazarOrden(null, ""+id); 
	        this.OrdenCreada=desc.DescodificadorJson(pingResponse); 
	        assertEquals(OrdenCreada.getEstado(),-1); 
	    }
	    //11
	    @Test
	    public void TestListaOrden() throws Throwable  { 
	        TestCrearOrden();
	        String pingResponse  = configurationController.aceptarOrden(null, ""+id); 
	        String entry="";//id y cosas que damos
	        pingResponse  = configurationController.listaOrden(null, entry);
	        this.OrdenCreada=desc.DescodificadorJson(pingResponse); 
	        assertEquals(OrdenCreada.getEstado(),2); 
	    }
	    //12 
    @Test
	    public void TestOrdenRecogida() throws Throwable  { 
    	   TestListaOrden();
    	   String entry ="";//id orden, firma recogida y datos del transportista
    	   String pingResponse=configurationController.recogidaOrden(null, entry);
    	   this.OrdenCreada=desc.DescodificadorJson(pingResponse);
	        assertEquals(OrdenCreada.getEstado(),3); 
	    }
    //13
		@Test
	    public void TestEntregadasOrden() throws Throwable  {
			   TestOrdenRecogida();
	    	   String entry ="";//id orden, firma recogida y datos del transportista
	    	   String pingResponse=configurationController.recogidaOrden(null, entry);
	    	   this.OrdenCreada=desc.DescodificadorJson(pingResponse);
		        assertEquals(OrdenCreada.getEstado(),4);
	    } 
	/*
	 * 
	 * 
	 * Funciones auxiliares
	 * 
	 * 
	 */

	    private boolean BuscarEnArray(String ordenesEnProcesoDeEntrega,String mirar) { 
			return ordenesEnProcesoDeEntrega.contains(mirar);
		}
 }

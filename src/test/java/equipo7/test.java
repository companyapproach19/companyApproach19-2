package equipo7;
//Quitar el True ; 
 
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
import equipo7.otros.ListaIDs;

import com.controller.ManejaPeticiones;
 
@RunWith(Parameterized.class)
public class test {
	
	

	private 
	DescodificadorJson desc = new DescodificadorJson ();
	private String pingResponse ="";
	   private OrdenTrazabilidad pedidocomprobar;
	   private String jsonpedidocomprobar;
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
	   public test(int id, Actor actorOrigen, Actor actorDestino ,
				Productos productosPedidos ) {
		   this.id=id;
			this.emisor=actorOrigen;
			this.receptor=actorDestino; 
			this.productos=productosPedidos;
			//metemos en la lista 
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
	    	  {-1,new Actor("9","Retailer",4 ),new Actor("8","Fabrica" ,3  ), new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) },//  
		        /* {2,"Cuidado",new Actor("PepitoF","MARIPOSA","pepito@gmail.com",2),new Actor("MariaC","Cuidado con la temperatura","maria@gmail.com",1),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),2,1},//,
		         {3,"Material organico",new Actor("RebecaR","Atención","rebe@gmail.com",3),new Actor("PepitoF","MARIPOSA","pepito@gmail.com",2),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),3,2},//
		         {4,"Deben ser puntuales",new Actor("AnaT","Gracias","ana@gmail.com",4),new Actor("RebecaR","Atención","rebe@gmail.com",3),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),4,3},//
		         {5,"Pedido urgente",new Actor("AnaT","Gracias","ana@gmail.com",1),new Actor("AnaT","Gracias","ana@gmail.com",0),new Productos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),1,0},//,
		         {6,"Dejar en la puerta",new Actor("PepitoF","MARIPOSA","pepito@gmail.com",1),new Actor("JuanT","PUERTA","juan@gmail.com",0),new Productos( 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),1,0},//
		     */ });
	   } 
	   /*
	   //1  
	      @Test
	    public void TestCrearOrden() throws Throwable  {//Verificado  
		        pedidocomprobar=crearunaorden();
		        assertNotEquals(id,pedidocomprobar.getId());//Condicion necesaria para que una orden sea creada  
	    }
	      //2
		   @Test
	       public void TestObtenerOrden() throws Throwable  {//comprobar formato entrada 
			   pedido=crearunaorden();
			   pedidocomprobar=obtenerorden(""+pedido.getId());
	           assertEquals(pedidocomprobar.getId(),pedido.getId());  
	           assertEquals(pedidocomprobar.getActorDestino().toString(),pedido.getActorDestino().toString());  
	           assertEquals(pedidocomprobar.getActorOrigen().toString(),pedido.getActorOrigen().toString());  
	           //cambiar comprobacion
	       } 
	   //3   
	    @Test
	    public void ListaOrdenesporAceptar() throws Throwable  { 
	    	pedido=crearunaorden(); 
	        String pingResponse2=configurationController.ordenesPendientesPorAceptar(null,""+pedido.getActorDestino().getId());
	        assertTrue(BuscarEnArray(pingResponse2,pedido.getId())); 
	    } 
	    //4
	    @Test
	    public void OrdenesQueHeEnviado() throws Throwable  { 
	    	pedido=crearunaorden();
	        String pingResponse2=configurationController.ordenesQueHeEnviado(null,""+ pedido.getActorOrigen().getId());
	        assertTrue(BuscarEnArray(pingResponse2,pedido.getId())); 
	    } 
	    //5
	    @Test
	    public void TestOrdenesEnProceso() throws Throwable  {//Pregunta : de los 3 constructores cual.
	    	pedido=crearunaorden(); 
	    	pedido=aceptarorden(""+pedido.getId()); 
	    	System.out.println(pedido.getId());
	        String pingResponse = configurationController.ordenesEnProceso(null,""+pedido.getActorDestino().getId());
	        boolean is=BuscarEnArray(pingResponse,pedido.getId());
	        assertTrue(is);
	    }
	    //6  
	    @Test
	    public void ordenesListasParaEntregar() throws Throwable  {//Pregunta : de los 3 constructores cual.
	    	//Obtenemos una orden con estado=1;
	    	String jsonid ="103";//Comprobar
	        String pingResponse1  = configurationController.obtenerOrden(null,jsonid); 
	           //  
	        this.pedidocomprobar=desc.DescodificadorJson(pingResponse1);
	        String pingresponse =configurationController.ordenesListasParaEntregar(null, this.pedidocomprobar.getActorDestino().getId());
	        boolean is=BuscarEnArray(pingresponse, pedidocomprobar.getId());
	        assertTrue(is);//Si esta metida se pasa el test.
	    
	    }  
	    */
	    //7 
	    @Test
	    public void ListaOrdenesRechazadas() throws Throwable  {//Pregunta : de los 3 constructores cual.
	    	pedido=crearunaorden(); 
	    	pedido=rechazar(""+pedido.getId());  
	        String pingResponse = configurationController.ordenesRechazadas (null,""+pedido.getActorOrigen().getId());
	        boolean is=BuscarEnArray(pingResponse,pedido.getId());
	        assertTrue(is);
	    
	    }  
	    /*
	    //8
	   @Test
	    public void TestOrdenesEnProcesoDeEntrega() throws Throwable  {   
		 //Obtenemos una orden con estado=1;
	    	String jsonid ="104";//Comprobar
	        String pingResponse1  = configurationController.obtenerOrden(null,jsonid); 
	           //  
	        this.pedidocomprobar=desc.DescodificadorJson(pingResponse1);
	        String pingresponse =configurationController.ordenesEnProcesoDeEntrega(null, this.pedidocomprobar.getActorDestino().getId());
	        boolean is=BuscarEnArray(pingresponse, pedidocomprobar.getId());
	        assertTrue(is);//Si esta metida se pasa el test.
	    }  /* 
	    //9
	    @Test
	    public void TestAceptarOrden() throws Throwable  {
	    	System.out.println("9");
	    	pedido=crearunaorden(); 
	        String pingResponse  = configurationController.aceptarOrden(null, ""+pedido.getId()); 
	        this.pedidocomprobar=desc.DescodificadorJson(pingResponse); 
	        assertEquals(pedidocomprobar.getEstado(),1); 
	    }
	    //10
	    @Test
	    public void TestRechazarOrden() throws Throwable  { 
	    	System.out.println("10");
	    	pedido=crearunaorden(); 
	        String pingResponse  = configurationController.rechazarOrden(null, ""+pedido.getId()); 
	        this.pedidocomprobar=desc.DescodificadorJson(pingResponse); 
	        assertEquals(pedidocomprobar.getEstado(),-1); 
	    }  
	    //11
	    @Test
	    public void TestListaOrden() throws Throwable  { 
	    	pedidocomprobar=lista();
	        assertEquals(pedidocomprobar.getEstado(),2); 
	    }
	    //12 
	     
    @Test
	    public void TestOrdenRecogida() throws Throwable  { 
    	String jsonid ="102";//Comprobar    
    	   String entry ="{\"id\": 102,\"firmaEntrega\": \"SG9sYSBxdWUgdGFsIHNveSBjb2xvc2Fs\",\"idRegistro\": 594}";//id orden, firma recogida y datos del transportista
    	   String pingResponse=configurationController.recogidaOrden(null, entry);
    	   System.out.println("salida :"+pingResponse);
    	   this.pedidocomprobar=desc.DescodificadorJson(pingResponse);
	        assertEquals(pedidocomprobar.getEstado(),3); 
	    }
    //13
		@Test
	    public void TestEntregadasOrden() throws Throwable  {
			String jsonid ="104";//Comprobar
	        String pingResponse1  = configurationController.obtenerOrden(null,jsonid);  
	    	   String entry ="{\"id\": 104,\"firmaEntrega\": \"SG9sYSBxdWUgdGFsIHNveSBjb2xvc2Fs\",\"idRegistro\": 594}";//id orden, firma recogida y datos del transportista
	    	   String pingResponse=configurationController.entregadaOrden (null, entry);
	    	   System.out.println("salida :"+pingResponse);
	    	   this.pedidocomprobar=desc.DescodificadorJson(pingResponse);
		        assertEquals(pedidocomprobar.getEstado(),4);
	    } 
	/*
	 * 
	 * 
	 * Funciones auxiliares
	 * 
	 * 
	 */
	    public OrdenTrazabilidad crearunaorden() throws Throwable { 
	          pingResponse  = configurationController.crearOrden(null,json); 
	          return desc.DescodificadorJson(pingResponse); 
	    }
	    public OrdenTrazabilidad obtenerorden(String obtener) throws Throwable {  
	    	pingResponse  = configurationController.obtenerOrden(null,obtener); 
	        return desc.DescodificadorJson(pingResponse);
	    	
	    }
          public OrdenTrazabilidad aceptarorden(String obtener) throws Throwable {
        	pingResponse  = configurationController.aceptarOrden (null,obtener); 
  	        return desc.DescodificadorJson(pingResponse);
	    	
	    }
          public  OrdenTrazabilidad rechazar(String obtener) throws Throwable {
        		pingResponse  = configurationController.rechazarOrden (null,obtener); 
      	        return desc.DescodificadorJson(pingResponse);
  	    }
          public  OrdenTrazabilidad lista( ) throws Throwable {
        	pedido=crearunaorden();
  	        String pingResponse  = configurationController.aceptarOrden(null, ""+pedido.getId()); 
  	        String entry="{\"id\":"+ pedido.getId()+",\"productosAEntregar\": []}";//id y cosas que damos
  	        pingResponse  = configurationController.listaOrden(null, entry);
    	        return desc.DescodificadorJson(pingResponse);
	    }
	    private boolean BuscarEnArray(String lista,Integer mirar) { 
	    	ArrayList<Integer>  listas = (desc.DescodificadorJSONlista(lista)).getListaIDs(); 
	    	return  listas.contains(mirar);
		}
	    
 }

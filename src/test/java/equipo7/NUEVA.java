package test;


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
import equipo7.otros.IDsOrdenes;
import equipo7.otros.ListaIDs;

import com.controller.ManejaPeticiones;
import org.junit.runners.MethodSorters;  
	
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
@RunWith(Parameterized.class)
public class NUEVA {
	
	

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
	   public void initialize() throws Throwable {//creo el objeto
		     

		      //pedidocomprobar=crearunaorden();
	   } 
		
	   public void actualizarjson() {
		   json= CodificadorJSON.crearJSON(pedido);
	   }
	   public NUEVA(int id, Actor actorOrigen, Actor actorDestino ,
			Productos productosPedidos ) throws Throwable {
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
		      pedido=new OrdenTrazabilidad(id,receptor,emisor,necesitatransportista,productos,productosAEntregar,estado,firmaRecogida,firmaEntrega,transportista,idRegistro,idPedido,fecha );
		      json= CodificadorJSON.crearJSON(pedido);
		      pedido=desc.DescodificadorJson( json); 
		      pedido=crearunaorden();  
	     
	   }

	   @Parameterized.Parameters
	   public static Collection primeNumbers() { 
	      return Arrays.asList(new Object[][] {
	    	  /*
	    	   * 
	    	   * 
	    	   * Falta poner todos los actores para probar todas las peticiones
	    	   */
	    	  {-1,new Actor("0","Productor",0 ),new Actor("1","Cooperativa" ,1  ), new Productos(0, 0,0,0,0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0) },
              {-1,new Actor("1","Cooperativa",1 ),new Actor("3","Fabrica" ,3), new Productos(0, 0,0,0,0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0) },
              {-1,new Actor("3","Fabrica",3 ),new Actor("4","Retailer" ,4), new Productos(0, 0,0,0,0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0) },
              });
	   }  
	   //--------------------------------------------------------------------------------------------------------------
	      @Test
	    public void ATest_1_CrearOrden() throws Throwable  {//Verificado    
	    	  System.out.println("ultissssm"+pedido.getId());
		       assertNotEquals(id,pedido.getId());//Condicion necesaria para que una orden sea creada  
	    }   
		   @Test
	       public void BTest_2_ObtenerOrden() throws Throwable  {//comprobar formato entrada 
			   String id=""+pedido.getId();
			   OrdenTrazabilidad pedido=obtenerorden(id);
	           assertEquals(this.pedido.getId(),pedido.getId());  
	           assertEquals(this.pedido.getActorDestino().toString(),pedido.getActorDestino().toString());  
	           assertEquals(this.pedido.getActorOrigen().toString(),pedido.getActorOrigen().toString());  
	           pedido=obtenerorden(id);
	           //cambiar comprobacion
	       }   
	    @Test
	    public void CTest_3_ListaOrdenesporAceptar() throws Throwable  { 
	    	String idactor=""+pedido.getActorDestino().getId();
	        String pingResponse2=configurationController.ordenesPendientesPorAceptar(null,idactor);
	        assertTrue(BuscarEnArray(pingResponse2,pedido.getId())); 
	    }    
	    @Test
	    public void DTest_4_OrdenesQueHeEnviado() throws Throwable  {  
	    	String idactor=""+ pedido.getActorOrigen().getId();
	        String pingResponse2=configurationController.ordenesQueHeEnviado(null,idactor);
	        assertTrue(BuscarEnArray(pingResponse2,pedido.getId())); 
	    }  
	    //9  
		   @ Test 
		    public void ETest_5_AceptarOrden() throws Throwable  {  
			   System.out.println("ultissssm"+pedido.getId());
			    String id=""+pedido.getId(); 
		        pedido=aceptarorden(id);  
		        if(pedido.getActorOrigen().getTipoActor()==1) { 
		        comprobarenbasededatos(pedido .getProductosPedidos());
		        assertEquals(pedido .getEstado(),4); }
		        else {assertEquals(pedido .getEstado(),1); }
		    }  
	    @Test
	    public void FTest_6_OrdenesEnProceso() throws Throwable  {//Pregunta : de los 3 constructores cual. 
	    	 if(pedido.isNecesitaTransportista()) {String id=""+pedido.getId();
	    	pedido=obtenerorden(id); 
	        String pingResponse = configurationController.ordenesEnProceso(null,""+pedido.getActorOrigen().getId());
	        boolean is=BuscarEnArray(pingResponse,pedidocomprobar.getId());
	       assertTrue(is);
	       } 
	    }    
	    @Test
	    public void GTest_7_ListaOrden() throws Throwable  {  
	    	String pedidod=""+pedido.getId();
		    System.out.println(pedidod);
	    	if(pedido.getActorOrigen().getTipoActor()!=1) {
	    	pedido=lista(pedido.getId(),pedido.getActorOrigen().getTipoActor());
	    	  System.out.println(pedido.getId());
	        assertEquals(pedido.getEstado(),2); }
	    }  
	    //6   
	    @Test
	    public void HTest_8_ordenesListasParaEntregar() throws Throwable  { 
	    	 String id=""+pedido.getId();
	        String pingresponse =configurationController.ordenesListasParaEntregar(null,id);
	        boolean is=BuscarEnArray(pingresponse, pedidocomprobar.getId());
	        assertTrue(is);//Si esta metida se pasa el test.
	    
	    }     
	    @Test
	    public void ITest_9_OrdenRecogida() throws Throwable  { //Cambiar
	    	if(pedido.getActorOrigen().getTipoActor()!=1) {
	    		
    	String jsonid =""+(pedido.getId()-2); 
    	   String entry ="{\"id\":"+jsonid+",\"firmaEntrega\": \"SG9sYSBxdWUgdGFsIHNveSBjb2xvc2Fs\",\"idRegistro\": 594}";//id orden, firma recogida y datos del transportista
    	   String pingResponse=configurationController.recogidaOrden(null, entry); 
    	 
    	   this.pedido=desc.DescodificadorJson(pingResponse);
	        assertEquals(pedido.getEstado(),3); }
	    }  
	   @Test
	    public void JTest_10_OrdenesEnProcesoDeEntrega() throws Throwable  {  
		    String idactor=pedido.getActorDestino().getId();
	        String pingresponse =configurationController.ordenesEnProcesoDeEntrega(null, idactor);
	        boolean is=BuscarEnArray(pingresponse, pedidocomprobar.getId());
	        assertTrue(is);//Si esta metida se pasa el test.
	    }   
		@Test
	    public void KTest_11_EntregadasOrden() throws Throwable  {//cambiar
			if(pedido.getActorOrigen().getTipoActor()!=0) {
		       String jsonid =""+(pedido.getId()-2);
	           String pingResponse1  = configurationController.obtenerOrden(null,jsonid);  
	    	   String entry ="{\"id\":"+jsonid+",\"firmaEntrega\": \"SG9sYSBxdWUgdGFsIHNveSBjb2xvc2Fs\",\"idRegistro\": 594}";//id orden, firma recogida y datos del transportista
	    	   String pingResponse=configurationController.entregadaOrden (null, entry);
	    	   this.pedido=desc.DescodificadorJson(pingResponse);
	    	   System.out.println("ultim"+pedido.getId());
		        assertEquals(pedido.getEstado(),4);}
	    } 
		 
		   @Test
		    public void LTest_12_OrdenesCompletadas() throws Throwable  {  
			    String idactor=pedido.getActorDestino().getId();
		        String pingresponse =configurationController.ordenesCompletadas(null, idactor);
		        boolean is=BuscarEnArray(pingresponse, pedidocomprobar.getId());
		        assertTrue(is); 
		    } 
		   @Test
		    public void MTest_13_OrdenesQueMeHanAceptado() throws Throwable  {  
			    String idactor=pedido.getActorDestino().getId();
		        String pingresponse =configurationController.ordenesAceptadas(null, idactor);
		        boolean is=BuscarEnArray(pingresponse, pedidocomprobar.getId());
		        assertTrue(is); 
		    } 
		   
		   
		//--------------------------------------------------------------------------------------------------------------
	    
		   @Test
	    public void NTest_14_RechazarOrden() throws Throwable  { 
	    	String id =""+pedidocomprobar.getId(); 
	        pedidocomprobar=rechazar(id); 
	        assertEquals(pedidocomprobar.getEstado(),-1); 
	        
	    }    
	    
		   @Test
	    public void OTest_15_ListaOrdenesRechazadas() throws Throwable  {//Pregunta : de los 3 constructores cual.
	    	String idactor=""+pedido.getActorOrigen().getId();
	        String pingResponse = configurationController.ordenesRechazadas (null,idactor);
	        boolean is=BuscarEnArray(pingResponse,pedido.getId());
	        assertTrue(is);
	        
		   } 
		   
	   //--------------------------------------------------------------------------------------------------------------
	      
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
          public  OrdenTrazabilidad lista(int responder,int tipo) throws Throwable { 
        	  int comp=0;
        	  if(tipo==2) {comp=(responder-5);}else {comp=(responder-5);}
        	String ids=  CodificadorJSON.crearJSONrespuestas(new IDsOrdenes(responder-1,comp)); 
  	        pingResponse  = configurationController.listaOrden(null,ids);
  	        System.out.println( pingResponse);
    	        return desc.DescodificadorJson(pingResponse);
	    }
	    private boolean BuscarEnArray(String lista,Integer mirar) { 
	    	ArrayList<Integer>  listas = (desc.DescodificadorJSONlista(lista)).getListaIDs(); 
	    	return  listas.contains(mirar);
		}
	    
	    private boolean comprobarenbasededatos(Productos prod) { 
	    	boolean estatodo=true;
	    	
	    	return  estatodo;
		}
	    
 }

package equipo6;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.*;

import com.controller.StockController;

import equipo4.model.Lote;
import equipo4.model.MateriaPrima;
import equipo5.dao.NullException;
import equipo5.dao.metodosCompany;
import equipo5.model.NotInDatabaseException;
import equipo5.model.StockLote;
import equipo6.model.Actor;
import equipo6.otros.BlockchainServices;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo8.model.Registro;

class StockControllerTest3 {

	@Before
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		/*OrdenTrazabilidad orden; 
		int idOrden;
	    int idRegistro;
        BlockchainServices bcs;
	    Actor actor_o;
        Actor actor_d;
	    actor_o = metodosCompany.extraerActor("6");
        actor_d = metodosCompany.extraerActor("10");
	    Productos productos;
	    bcs = new BlockchainServices();
	    productos = new Productos(999, 999, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	    idOrden = metodosCompany.idOrdenTrazabilidad();
        orden = new OrdenTrazabilidad(idOrden, actor_o,actor_d, productos);
        orden.setEstado(0);
        orden.setIdPedido(1);
        try {
			bcs.guardarOrden(orden);
		} catch (Throwable e) {
			System.err.println("ha ido mal guardar orden");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        */
		
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
/*
	@Test
	//test cuando funciona bien
	void testGetCantidadStock() {
		System.out.println("==========================================1");
		Actor actor= new Actor("0"," ",0);
		MateriaPrima mp = new MateriaPrima("cebadaTostada", 7, 0) ;
		try {
			System.out.println(StockController.getCantidadStock(actor,50, mp));
		} catch (ClassNotFoundException | SQLException | NotInDatabaseException e) {
			// TODO Auto-generated catch block
			System.err.println("ha habido un error");
			e.printStackTrace();
		}

	}
	
	@Test
	//test cuando el actor no esta en la orden 
 	void testGetCantidadStock1() {
		System.out.println("==========================================2");
		Actor actor= new Actor("9"," ",0);
		MateriaPrima mp = new MateriaPrima("cebadaTostada", 7, 0) ;
		try {
			System.out.println(StockController.getCantidadStock(actor,50, mp));
		} catch (ClassNotFoundException | SQLException | NotInDatabaseException e) {
			// TODO Auto-generated catch block
			System.err.println("ha habido un error");
			e.printStackTrace();
		}
	}
	
	
	@Test
	//cuano el actor no esta en la bbdd == cuando la orden no esta en la bbdd
	void testGetCantidadStock2() {
		System.out.println("==========================================3");
		Actor actor= new Actor("0"," ",0);
		MateriaPrima mp = new MateriaPrima("cebadaTostada", 7, 0) ;
		try {
			System.out.println(StockController.getCantidadStock(actor,5000, mp));
		} catch (ClassNotFoundException | SQLException | NotInDatabaseException e) {
			// TODO Auto-generated catch block
			System.err.println("ha habido un error");
			e.printStackTrace();
		}
	}
	*/
/*
	@Test
	//cuando va todo bien
	void testGetListaLotes() {
		System.out.println("==========================================9999");
		Productos producto;
		
		System.out.println();
	/*	try {
			
			try {
				try {
					Actor actor_o, actor_d;
					actor_o = metodosCompany.extraerActor("8");
					actor_d = metodosCompany.extraerActor("9");					
					producto = metodosCompany.extraerProductos(106);
					OrdenTrazabilidad orden= new OrdenTrazabilidad (999,actor_o,actor_d,producto);
					orden.setEstado(4);
					metodosCompany.insertarOrdenTrazabilidad(orden);
					
				} catch (NullException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*//*
		Actor actor= new Actor("8"," ",3);
		try {
			LinkedList<StockLote> lista = StockController.getListaLotes(actor);
			System.out.println(lista.size());
			for(StockLote sLote : lista) {
				System.out.println("tipo "+ sLote.getLote().getTipo());
				System.out.println("hoola");
			}
		} catch (ClassNotFoundException | SQLException | NotInDatabaseException e) {
			// TODO Auto-generated catch block
			System.err.println("ha habido un error");
			e.printStackTrace();
		}
		
		
	}
	*/
	/*
	@Test
	//el metodo esta pensado solo para fabrica, en cualquier otro actor devuelve 0
	void testGetListaLotes2() {
		System.out.println("==========================================4");
		Actor actor= new Actor("3"," ",4);
		try {
			LinkedList<StockLote> lista = StockController.getListaLotes(actor);
			System.out.println(lista.size());
			for(StockLote sLote : lista) {
				System.out.println("tipo "+ sLote.getLote().getTipo());
				System.out.println("hoola");
			}
		} catch (ClassNotFoundException | SQLException | NotInDatabaseException e) {
			// TODO Auto-generated catch block
			System.err.println("ha habido un error");
			e.printStackTrace();
		}		
	}
	*/	
	
	/*
	@Test
	//cuando el actor no tiene stock
	void testGetListaLotes2() {
		System.out.println("==========================================5");
		Actor actor= new Actor("1"," ",1);
		try {
			LinkedList<StockLote> lista = StockController.getListaLotes(actor);
			for(StockLote sLote : lista) {
				System.out.println("tipo "+ sLote.getLote().getTipo());
			}
		} catch (ClassNotFoundException | SQLException | NotInDatabaseException e) {
			// TODO Auto-generated catch block
			System.err.println("ha habido un error");
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	//cuando el actor no esta en la bbdd sale 0
	void testGetListaLotes3() {
		System.out.println("==========================================6");
		Actor actor= new Actor("55555"," ",4);
		try {
			LinkedList<StockLote> lista = StockController.getListaLotes(actor);
			for(StockLote sLote : lista) {
				System.out.println("tipo "+ sLote.getLote().getTipo());
			}
		} catch (ClassNotFoundException | SQLException | NotInDatabaseException e) {
			// TODO Auto-generated catch block
			System.err.println("ha habido un error");
			e.printStackTrace();
		}
		
		
	}
	@Test
	//cuando la orden no tiene lotes
	void testGetListaLotes5() {
		System.out.println("==========================================7");
		Actor actor= new Actor("1"," ",1);
		try {
			LinkedList<StockLote> lista = StockController.getListaLotes(actor);
			for(StockLote sLote : lista) {
				System.out.println("tipo "+ sLote.getLote().getTipo());
			}
		} catch (ClassNotFoundException | SQLException | NotInDatabaseException e) {
			// TODO Auto-generated catch block
			System.err.println("ha habido un error");
			e.printStackTrace();
		}		
	}	
	*/
	/*
	@Test
	//prueba con lotes de fabrica
	void testGetStockLote() {
		System.out.println("==========================================8");
		Actor actor= new Actor("8"," ",3);
		Lote loteP= new Lote(0,null,null,null,false,false,false,false,false, "pilsner",null,null,null,null,null);
		Lote loteS= new Lote(0,null,null,null,false,false,false,false,false, "stout",null,null,null,null,null);
		try {
			System.out.println(" stout "+StockController.getStockLote(actor,2,loteS));
			System.out.println("pilsner "+StockController.getStockLote(actor,2,loteP));
		} catch (ClassNotFoundException | SQLException | NotInDatabaseException e) {
			// TODO Auto-generated catch block
			System.err.println("no ha salido bien");
			e.printStackTrace();
		}

	}
	
	
	@Test
	//prueba con lotes de retailer
	void testGetStockLote1() {
		System.out.println("==========================================9");
		Actor actor= new Actor("9"," ",4);
		Lote loteP= new Lote(0,null,null,null,false,false,false,false,false, "pilsner",null,null,null,null,null);
		Lote loteS= new Lote(0,null,null,null,false,false,false,false,false, "stout",null,null,null,null,null);
		try {
			System.out.println(" stout "+StockController.getStockLote(actor,33,loteS));
			System.out.println("pilsner "+StockController.getStockLote(actor,33,loteP));
			System.out.println("alberto: " + metodosCompany.extraerOrdenesActorOrigen("9").get(0).getId());
		} catch (ClassNotFoundException | SQLException | NotInDatabaseException e) {
			// TODO Auto-generated catch block
			System.err.println("no ha salido bien");
			e.printStackTrace();
		}

	}
	
	@Test
	//cuando el actor no almacena lotes
	void testGetStockLote2() {
		System.out.println("==========================================10");
		Actor actor= new Actor("10"," ",0);
		Lote loteP= new Lote(0,null,null,null,false,false,false,false,false, "pilsner",null,null,null,null,null);
		Lote loteS= new Lote(0,null,null,null,false,false,false,false,false, "stout",null,null,null,null,null);
		try {
			System.out.println(" stout "+StockController.getStockLote(actor,2,loteS));
			System.out.println("pilsner "+StockController.getStockLote(actor,2,loteP));
		} catch (ClassNotFoundException | SQLException | NotInDatabaseException e) {
			// TODO Auto-generated catch block
			System.err.println("no ha salido bien");
			e.printStackTrace();
		}
	}
	
	
	@Test
	//cuando la orden no almacena lotes
	void testGetStockLote3() {
		System.out.println("==========================================11");
		Actor actor= new Actor("9"," ",4);
		Lote loteP= new Lote(0,null,null,null,false,false,false,false,false, "pilsner",null,null,null,null,null);
		Lote loteS= new Lote(0,null,null,null,false,false,false,false,false, "stout",null,null,null,null,null);
		try {
			System.out.println(" stout "+StockController.getStockLote(actor,2,loteS));
			System.out.println("pilsner "+StockController.getStockLote(actor,2,loteP));
		} catch (ClassNotFoundException | SQLException | NotInDatabaseException e) {
			// TODO Auto-generated catch block
			System.err.println("no ha salido bien");
			e.printStackTrace();
		}
	}
		
	*/

/*	@Test
	//funciona perfecto
	void testGetStockPedidoFabrica() throws Throwable {
		System.out.println("=========================99999");
		/*OrdenTrazabilidad orden;
        Actor actor_o;
        Actor actor_d;
        Productos productos;
        BlockchainServices bcs;
        Registro reg;
        int idOrden;
        int idRegistro;
        
        bcs = new BlockchainServices();
        actor_o = metodosCompany.extraerActor("2");
        actor_d = metodosCompany.extraerActor("10");
        productos = new Productos(500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500);
        idOrden = metodosCompany.idOrdenTrazabilidad();
        orden = new OrdenTrazabilidad(idOrden, actor_o,actor_d, productos);
        orden.setEstado(4);
        orden.setIdPedido(4);
        bcs.guardarOrden(orden);
        */
        
	/*
		ArrayList<OrdenTrazabilidad> lista;
		try {
			System.out.println("aqui");
			lista = metodosCompany.extraerOrdenesActorOrigen("2");
			System.out.println(lista.size());
			
			for(OrdenTrazabilidad orden : lista  ) {
				System.out.println("ha entrado en el bucle");
				try {
					HashMap<String, Double> hm =StockController.getStockPedidoFabrica(1054);
					System.out.println(hm.size());
					//MateriaPrima mp= new MateriaPrima("maltaChocolate",34,0);
					System.out.println(hm.get("maltaChocolate"));
				} catch (NotInDatabaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
/*	
	@Test
	//cuando el actor no esta en la bbdd devuelve 0
	void testGetStockPedidoFabrica1() {
		System.out.println("=========================19");
		ArrayList<OrdenTrazabilidad> lista;
		try {
			System.out.println("aqui");
			lista = metodosCompany.extraerOrdenesActorOrigen("88");
			System.out.println(lista.size());
			
			for(OrdenTrazabilidad orden : lista  ) {
				System.out.println("ha entrado en el bucle");
				try {
					HashMap<String, Double> hm =StockController.getStockPedidoFabrica(1054);
					System.out.println(hm.size());
					//MateriaPrima mp= new MateriaPrima("maltaChocolate",34,0);
					System.out.println(hm.get("maltaChocolate"));
				} catch (NotInDatabaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	*/
	
	/*
	@Test
	//buen actor mala orden
	void testGetStockPedidoFabrica2() {
		System.out.println("=========================20");
		ArrayList<OrdenTrazabilidad> lista;
		try {
			System.out.println("aqui");
			lista = metodosCompany.extraerOrdenesActorOrigen("2");
			System.out.println(lista.size());
			
			for(OrdenTrazabilidad orden : lista  ) {
				System.out.println("ha entrado en el bucle");
				try {
					HashMap<String, Double> hm =StockController.getStockPedidoFabrica(1004);
					System.out.println(hm.size());
					//MateriaPrima mp= new MateriaPrima("maltaChocolate",34,0);
					System.out.println(hm.get("maltaChocolate"));
				} catch (NotInDatabaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	*/
	
	
	
	
	@Test
    void testSetCantidadMateriaPrima() throws ClassNotFoundException, SQLException, NullException {
        StockController.setCantidadMateriaPrima(metodosCompany.extraerActor("9"), new MateriaPrima("maltaBasePalida", 145, 20000),200000.0,1056);
        
	}

	
	
	
	
	
	
	
/*
	
	@Test
	//saca la cantidad de 
	void testSetCantidadLote() {
		OrdenTrazabilidad orden;
		Lote loteS= new Lote(0,null,null,null,false,false,false,false,false, "stout",null,null,null,null,null);
		Actor actor_o;
		try {
			actor_o = metodosCompany.extraerActor("9");
			try {
				StockController.setCantidadLote(actor_o,loteS,999);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		fail("Not yet implemented");
	}
	
	
	
	
	*/
	
	
	
	
	
	
	
	
	/*
	@Test
	void testBuscarTipoCerveza() {
		try {
			System.out.println(StockController.buscarTipoCerveza(1000));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("ha habido un error");
			e.printStackTrace();
		}
	}

	*/
	
	
	
	
	
	/*
	@Test
	//String del tipo de cerveza del pedido 
	void testBuscarCantidadCerveza() {
		try {
			System.out.println("try");
			System.out.println(" hay "+StockController.buscarCantidadCerveza(1000)+" cantidad de cerveza");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
*/
		

	/*
	
	@Test
	//devuelve la lista de actores el tipo dado
	void testActoresDeTipo() {
		
		try {
			List<Actor> lista=	StockController.actoresDeTipo(2);
			for(int i=0;i<lista.size() ;i++) {
				System.out.println(lista.get(i).getId());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("ha habido un error");
			e.printStackTrace();
		}
		
	}

	@Test
	//devuelve la lista de actores el tipo dado. el tipo no esta en la base de datos
	void testActoresDeTipo2() {
		
		try {
			List<Actor> lista=	StockController.actoresDeTipo(6);
			for(int i=0;i<lista.size() ;i++) {
				System.out.println(lista.get(i).getId());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("ha habido un error");
			e.printStackTrace();
		}
		
	}
	*/

}

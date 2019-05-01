package equipo5;

import equipo5.dao.metodosCompany;
import equipo5.model.Cadena;
import equipo5.model.StockLote;
import equipo6.model.Actor;
import equipo6.model.Bloque;
import equipo6.model.CadenaActores;
import equipo6.model.DatosContainer;
import equipo6.model.geolocalizacion;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo7.model.Transportista;
import equipo8.model.GeneradorQR2;
import equipo8.model.Registro;

import java.lang.reflect.Type;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

import equipo4.model.AlmacenLotes;
import equipo4.model.AlmacenMMPP;
import equipo4.model.Lote;
import equipo4.model.MateriaPrima;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Test extends metodosCompany {

	public static void main(String[] args) throws Throwable {
		try {
			conectar();
			crearBD();

//			extraerBloque("; DROP TABLE bloque; DROP TABLE productos");
			
//			String json = "{\r\n" +
//					                "\"id\": 49126,\r\n" +
//					                "\"actorOrigen\": {\r\n" +
//					                "\"id\": 15,\r\n" +
//					                "\"nombreUsuario\": \"Juan Jose Romagosa\",\r\n" +
//					                "\"email\": \"hipojuan@empresaperros.com\",\r\n" +
//					                "\"tipoActor\": 1\r\n" +
//					                "},\r\n" +
//					                "\"actorDestino\": {\r\n" +
//					                "\"id\": 54,\r\n" +
//					                "\"nombreUsuario\": \"Chocu El Magnate del Cereal\",\r\n" +
//					                "\"email\": \"monitochocu@desatranquesjaen.com\",\r\n" +
//					                "\"tipoActor\": 3\r\n" +
//					                "},\r\n" +
//					                "\"necesitaTransportista\": true,\r\n" +
//					                "\"productos\": {\r\n" +
//					                "\"cant_malta_palida\": 20,\r\n" +
//					                "\"cant_malta_munich\": 40,\r\n" +
//					                "\"cant_malta_negra\": 0,\r\n" +
//					                "\"cant_malta_crystal\": 0,\r\n" +
//					                "\"cant_malta_chocolate\": 0,\r\n" +
//					                "\"cant_malta_caramelo\": 0,\r\n" +
//					                "\"cant_cebada\": 0,\r\n" +
//					                "\"cant_cebada_tostada\": 0,\r\n" +
//					                "\"cant_lupulo_centenial\": 0,\r\n" +
//					                "\"cant_cajas_stout\":0,\r\n" +
//					                "\"cant_cajas_bisner\":0\r\n" +
//					                "},\r\n" +
//					                "\"mensaje\": \"Petición de prueba.\",\r\n" +
//					                "\"estado\": 0,\r\n" +
//					                "\"idPadre\": 5196,\r\n" +
//					                "\"idHijo\": 1952,\r\n" +
//					                "\"transportista\": {\r\n" +
//					                "\"id\": 92,\r\n" +
//					                "\"nombreUsuario\": \"Doctor Jirafa\",\r\n" +
//					                "\"email\": \"dortoc@refranes.com\",\r\n" +
//					                "\"tipoActor\": 2\r\n" +
//					                "}\r\n" +
//					                "}";
//			
//			Actor vacio = new Actor("0", "o", "o", "o", 0, "0", "0", "0", "0");
//			insertarActor(vacio);	
//			
//			Lote loteVacio = new Lote(0,new Date(0, 0, 0),"",new Date(0, 0, 0),true,true,true,true,true,new byte[1],0);
//			insertarLote(loteVacio);
//			
//			Actor tranpG3_2 = new Actor("15", "TranspG3_2", "password", "tranpG3_2@gmail.es", 2, "Calle Santa Elena", "Jose", "Calle Ilustración", "fg5");
//			insertarActor(tranpG3_2);		    
//			
//			Actor tranpG3_3 = new Actor("54", "TranspG3_2", "password", "tranpG3_2@gmail.es", 2, "Calle Santa Elena", "Jose", "Calle Ilustración", "fg5");
//			insertarActor(tranpG3_3);
//		
//	        Gson gson=new Gson();
//	        Type tipoObjeto = new TypeToken<OrdenTrazabilidad>(){}.getType();
//	        OrdenTrazabilidad orden = gson.fromJson(json, tipoObjeto);
//	        insertarOrdenTrazabilidad(orden);
					  
					        
			// PETICIONES GRUPOS QUE DEBEN ESTAR EN LA BBDD.
					        
//	        Actor vacio = new Actor("0", "o", "o", "o", 0, "0", "0", "0", "0");
//			insertarActor(vacio);	
//			Lote loteVacio = new Lote(0,new Date(0, 0, 0),"",new Date(0, 0, 0),true,true,true,true,true,new byte[1],0);
//			insertarLote(loteVacio);
			//Un actor y una localizacion pedido por el grupo6 (Blockchain)
			Actor G6 = new Actor("33", "G6", "password", "G6@gmail.es", 2, "Calle Jueves", "Luis",
					"Calle Olivares", "fg7");
			insertarActor(G6);
			
			Date dategeo1 = new Date(11,04,2018);
			Date dategeo2 = new Date(15,04,2018);
			
			geolocalizacion geo1 = new geolocalizacion(1, 1, 1, "36.92º N 2.94º O",dategeo1);
			geolocalizacion geo2 = new geolocalizacion(2, 2, 2, "36.95º N 2.98º O",dategeo2);
			
			insertarGeolocalizacion(geo1);
			insertarGeolocalizacion(geo2);
			
			//Transportistas y pedidos (Orden trazabilidad) pedidos por el Grupo 3
			Actor tranpG3_1 = new Actor("11", "TranspG3_1", "password", "tranpG3_1@gmail.es", 2, "Calle Diaz", "Maria",
					"Calle Renacimiento", "fg4");
			Actor tranpG3_2 = new Actor("12", "TranspG3_2", "password", "tranpG3_2@gmail.es", 2, "Calle Santa Elena", "Jose",
					"Calle Ilustración", "fg5");
			
			insertarActor(tranpG3_1);
			insertarActor(tranpG3_2);
			
			Productos productosG3_1 = new Productos(11, 2, 9, 2, 1, 7, 8, 0, 11, 4, 7);
			
			insertarProductos(productosG3_1,11);
		
			Date dateiniG3_1 = new Date(11, 11, 2018);
			Date datefinG3_1  = new Date(31, 12, 2019);
			
			Date dateMoli1  = new Date(31, 11, 2019);
			Date dateCoci1  = new Date(1, 12, 2019);
			Date dateFerme1_1  = new Date(4, 12, 2019);
			Date dateFerme1_2  = new Date(12, 12, 2019);
			Date dateEmbo1  = new Date(28, 12, 2019);
			
			Lote loteG3_1 = new Lote(11, dateiniG3_1,  datefinG3_1, null ,false, true, false, true, false, "pilsner"
					, dateMoli1, dateCoci1, dateFerme1_1,  dateFerme1_2 ,dateEmbo1);
			loteG3_1.setQr(GeneradorQR2.generadorQR(loteG3_1.getIdBd()));
			//insertarLote(loteG3_1);
			
			Registro registroG3_1 = new Registro(11, 11, dateiniG3_1.toLocaleString(),
					datefinG3_1.toLocaleString(), 30, 5);
			
			insertarRegistro(registroG3_1);
			
			byte[] firmarecG3_1 = null;
			byte[] firmaentG3_2 = null;

			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(11);
			OrdenTrazabilidad ordenG3_1 = new OrdenTrazabilidad(11, tranpG3_1, tranpG3_2, productosG3_1);
			insertarOrdenTrazabilidad(ordenG3_1);
			
			 //MateriaPrima pedida Grupo 4
			MateriaPrima maltaPilsner = new MateriaPrima("maltaPilsner", 1, 2);
			insertarMateriaPrima(maltaPilsner);
			MateriaPrima maltaBasePalida = new MateriaPrima("maltaBasePalida", 2, 9);
			insertarMateriaPrima(maltaBasePalida);
			MateriaPrima maltaMunich = new MateriaPrima("maltaMunich", 3, 10);
			insertarMateriaPrima(maltaMunich);
			MateriaPrima maltaNegra = new MateriaPrima("maltaNegra", 4, 4);
			insertarMateriaPrima(maltaNegra);
			MateriaPrima maltaCrystal = new MateriaPrima("maltaCrystal", 5, 3);
			insertarMateriaPrima(maltaCrystal);
			MateriaPrima maltaChocolate = new MateriaPrima("maltaChocolate", 6, 2);
			insertarMateriaPrima(maltaChocolate);
			MateriaPrima cebadaTostada = new MateriaPrima("cebadaTostada", 7, 24);
			insertarMateriaPrima(cebadaTostada);
			MateriaPrima lupuloPerle = new MateriaPrima("lupuloPerle", 8, 6);
			insertarMateriaPrima(lupuloPerle);
			MateriaPrima lupuloTettnanger = new MateriaPrima("lupuloTettnanger", 9, 5);
			insertarMateriaPrima(lupuloTettnanger);
			MateriaPrima lupuloCentennial = new MateriaPrima("lupuloCentennial", 10, 23);
			insertarMateriaPrima(lupuloCentennial);
			MateriaPrima levaduraAle = new MateriaPrima("levaduraAle", 11, 14);
			insertarMateriaPrima(levaduraAle);
			MateriaPrima levaduraLager = new MateriaPrima("levaduraLager", 12, 55);
			insertarMateriaPrima(levaduraLager);
			System.out.println("Materias Primas Introducidas");
			
			//Actores Pedidos Grupo 2
			
			Actor agricultorG2 = new Actor("0", "agricultorG2", "password", "agricultorG2@gmail.es", 0, "Calle Enero", "Marta",
					"Calle Girona", "fg3");
//			insertarStockMP(agricultorG2, maltaPilsner, 15);
//			insertarStockMP(agricultorG2, maltaNegra, 22);
//			insertarStockMP(agricultorG2, levaduraAle, 4);
//			insertarStockMP(agricultorG2, cebadaTostada, 39);
			
			Actor cooperativaG2 = new Actor("1", "cooperativaG2", "password", "cooperativaG2@gmail.es", 1, "Calle Febrero", "Maria",
					"Calle Tarragona", "fg2");
//			insertarStockMP(cooperativaG2, maltaMunich, 18);
//			insertarStockMP(cooperativaG2, lupuloCentennial, 2);
//			insertarStockMP(cooperativaG2, levaduraLager, 99);
//			insertarStockMP(cooperativaG2, maltaPilsner, 56);
			
			Actor fabricaG3 = new Actor("2", "fabricaG3", "password", "fabricaG3@gmail.es", 3, "Calle Marzo",
					"Luis", "Calle Lerida", "fg1");
//			insertarStockMP(fabricaG3, maltaPilsner, 45);
//			insertarStockMP(fabricaG3, maltaMunich, 23);
//			insertarStockMP(fabricaG3, lupuloPerle, 97);
//			insertarStockMP(fabricaG3, maltaChocolate, 29);
			
			Actor retailerG2 = new Actor("3", "retailerG2", "password", "retailerG2@gmail.es", 4, "Calle Abril", "Santiago",
					"Calle Barcelona", "fg3");
//			insertarStockMP(retailerG2, levaduraLager, 12);
//			insertarStockMP(retailerG2, maltaNegra, 56);
//			insertarStockMP(retailerG2, lupuloPerle, 32);
//			insertarStockMP(retailerG2, cebadaTostada, 78);

			// Actores pedidos equipo 7
			// 0->4 para Productor, Cooperativa, Transportista, Fabrica y Retailer
			Actor productor = new Actor("5", "Productor", "password", "prod@gmail.es", 0, "Calle Ribadeo", "Marta",
					"Calle Valladolid", "fg3");
			Actor cooperativa = new Actor("6", "Cooperativa", "password", "coop@gmail.es", 1, "Calle Luarca", "Maria",
					"Calle Murcia", "fg2");
			Actor transportista = new Actor("7", "Transportista", "password", "transp@gmail.es", 2, "Calle Lugo",
					"Luis", "Calle Cartagena", "fg1");
			Actor fabrica = new Actor("8", "Fabrica", "password", "fab@gmail.es", 3, "Calle Velázquez", "Santiago",
					"Calle Andalucía", "fg3");
			Actor retailer = new Actor("9", "Retailer", "password", "ret@gmail.es", 4, "Calle Goya", "Pilar",
					"Calle Madrid", "fg2");

			insertarActor(productor);
			insertarActor(cooperativa);
			insertarActor(transportista);
			insertarActor(fabrica);
			insertarActor(retailer);

			// PRUEBAS BASICAS, NINGUNA DEBE LANZAR ERROR.

			// Pruebas Actor
			Actor prueba = new Actor("10", "Agricultor", "password", "agri@gmail.es", 1, "Calle Ribera", "juan",
					"Calle Goicoechea", "fg3");
			Actor emisor1 = new Actor("15", "alberto", "password", "alberto@gmail.es", 1, "Calle Ribera", "alberto",
					"Avenida Jarales", "fg3");
			Actor receptor1 = new Actor("16", "maria", "password", "maria@gmail.es", 3, "Calle Gonzalez", "maria",
					"Avenida Europa", "fg2");
			Actor emisor2 = new Actor("17", "felipe", "password", "felipe@gmail.es", 2, "Calle Sauces", "felipe",
					"Avenida Ilustración", "fg1");
			Actor receptor2 = new Actor("18", "rick", "password", "rick@gmail.es", 4, "Calle Gaseta", "rick",
					"Avenida Argentina", "fg0");

			insertarActor(prueba);
			insertarActor(emisor1);
			insertarActor(emisor2);
			insertarActor(receptor1);
			insertarActor(receptor2);
			System.out.println("Actores Introducidos");
			extraerActor("Agricultor");
			extraerActor("alberto");
			extraerActor("maria");
			extraerActor("felipe");
			extraerActor("rick");
			
			CadenaActores cadena = extraerCadenaActores();
			List<Actor> listact = cadena.getlista_actores();
			System.out.println("Prueba cadena actores: ");
			for (int i = 0; i < listact.size(); i++) {
				System.out.println(listact.get(i).getNombreUsuario());
			}
			
			System.out.println("Actores extraidos");

			// Prueba lotes
			
			Date dateini2 = new Date(2, 12, 2015);
			Date datefin2 = new Date(1, 12, 2019);
			Date dateMoli2  = new Date(31, 11, 2016);
			Date dateCoci2  = new Date(1, 12, 2017);
			Date dateFerme2_1  = new Date(4, 12, 2018);
			Date dateFerme2_2  = new Date(5, 12, 2018);
			Date dateEmbo2  = new Date(28, 12, 2019);
			
			Date dateini3 = new Date(6, 11, 2018);
			Date datefin3 = new Date(8, 12, 2018);
			Date dateMoli3  = new Date(31, 11, 2019);
			Date dateCoci3  = new Date(1, 12, 2019);
			Date dateFerme3_1  = new Date(4, 12, 2019);
			Date dateFerme3_2  = new Date(12, 12, 2019);
			Date dateEmbo3  = new Date(28, 12, 2019);


			Lote lote1 = new Lote(1, dateini2, datefin2, null, false, true, false, true, false, "stout", 
					dateMoli2, dateCoci2, dateFerme2_1, dateFerme2_2 , dateEmbo2);
			Lote lote2 = new Lote(2, dateini3, datefin3, null, false, true, false, true, false, "pilsner", 
					dateMoli3, dateCoci3, dateFerme3_1, dateFerme3_2 , dateEmbo3);

			lote1.setQr(GeneradorQR2.generadorQR(lote1.getIdBd()));
			lote2.setQr(GeneradorQR2.generadorQR(lote2.getIdBd()));

			//insertarLote(lote2);
			//insertarLote(lote3);
			System.out.println("Lotes introducidos");
			
			extraerLote(1);
			extraerLote(2);
			System.out.println("Lotes extraidos");

			
			// Pruebas Productos
			Productos productos1 = new Productos(1, 12, 5, 0, 4, 7, 8, 10, 11, 0, 1);
			Productos productos2 = new Productos(2, 9, 5, 0, 4, 0, 4, 0, 6, 0, 1);

			insertarProductos(productos1,1);
			insertarProductos(productos2,2);
			System.out.println("Productos introducidos");


			extraerProductos(1);
			extraerProductos(2);
			System.out.println("Productos extraidos");
			
			// Pruebas OrdenTrazabilidad

			Productos productosord1 = new Productos(4, 2, 9, 2, 4, 7, 8, 6, 11, 4, 7);
			Productos productosord2 = new Productos(5, 1, 5, 0, 4, 7, 81, 10, 11, 9, 1);

			byte[] firmarec = null;
			byte[] firmaent = null;

			OrdenTrazabilidad orden1 = new OrdenTrazabilidad(1, emisor1, emisor2, productosord1);
			insertarOrdenTrazabilidad(orden1);
			extraerOrdenTrazabilidad(1);

			OrdenTrazabilidad orden2 = new OrdenTrazabilidad(2, emisor2, emisor1, productosord2);
			insertarOrdenTrazabilidad(orden2);
			extraerOrdenTrazabilidad(2);

			// Prueba registros
			
			Registro registro1 = new Registro(1, 1, dateini2.toLocaleString(), datefin2.toLocaleString(), 40,
					10);
			registro1.setId(2);
			Registro registro2 = new Registro(2, 2, dateini2.toLocaleString(), datefin2.toLocaleString(), 25,
					20);
			registro2.setId(3);

			insertarRegistro(registro1);
			insertarRegistro(registro2);

			System.out.println("Registros introducidos");

			extraerRegistro(1);
			extraerRegistro(2);
	
			System.out.println("Registros extraidos");

	

			// Pruebas cadena

			Cadena cadenaPrueba1 = new Cadena(1, "INICIO", 1);
			insertarCadena(cadenaPrueba1);

			Cadena cadenaPrueba2 = new Cadena(2, "hashUltimoBloque2", 3);
			insertarCadena(cadenaPrueba2);

			extraerCadena(1);
			extraerCadena(2);
			System.out.println("Pruebas Cadena OK");

			// Prueba bloques
			DatosContainer datos1 = registro1;
			DatosContainer datos2 = lote1;

			Bloque bloque1 = new Bloque("hashPrevio1", 1, 1, lote1.getIdBd(), datos1, 1);
			Bloque bloque2 = new Bloque("hashPrevio2", 2, 2, lote2.getIdBd(), datos2, 2);

			//insertarBloque(bloque1);
			//insertarBloque(bloque2);

			System.out.println(bloque1.getHashCode().length());

//			extraerBloque(bloque1.getHashCode());
//			extraerBloque(bloque2.getHashCode());

			// Prueba extraerPedidosActorDestino (Relacionado con OrdenTrazabilidad
			ArrayList<OrdenTrazabilidad> ord1 = extraerOrdenesActorDestino("1");
			ArrayList<OrdenTrazabilidad> ord2 = extraerOrdenesActorDestino("2");

			System.out.print("Prueba ord1 de extraerPedidosActorDestino\n");
			for (int i = 0; i < ord1.size(); i++) {
				System.out.println(ord1.get(i).getId());
			}

			System.out.print("Prueba ord2 de extraerPedidosActorDestino\n");
			for (int i = 0; i < ord2.size(); i++) {
				System.out.println(ord2.get(i).getId());
			}


			// Prueba insertarStockMp
//			insertarStockMP(emisor1, maltaPilsner, 4);
//			insertarStockMP(emisor2, cebadaTostada, 5);
//			insertarStockMP(productor, maltaPilsner, 12);
//			insertarStockMP(cooperativa, maltaChocolate, 12);
//			insertarStockMP(fabrica, maltaPilsner, 12);
//			insertarStockMP(productor, cebadaTostada, 10);
//			insertarStockMP(cooperativa, maltaPilsner, 8);
//			insertarStockMP(fabrica, lupuloTettnanger, 7);
//			insertarStockMP(fabrica, maltaPilsner, 12);
//			insertarStockMP(productor, cebadaTostada, 10);
//			insertarStockMP(cooperativa, maltaPilsner, 8);
//			insertarStockMP(fabrica, lupuloTettnanger, 8);
//			insertarStockMP(retailer, cebadaTostada, 19);
//			insertarStockMP(receptor2, maltaNegra, 11);

			System.out.println("Materias Primas Stock introducidas");
			
//			insertarStockLote(emisor1, lote1);
//			insertarStockLote(emisor2, lote2);
//			insertarStockLote(receptor1, lote3);
//			insertarStockLote(productor, lote1);
//			insertarStockLote(fabrica, lote2);
//			insertarStockLote(retailer, lote3);
//			insertarStockLote(retailer, lote1);
//			insertarStockLote(cooperativa, lote3);
//			System.out.println("Lotes Stock introducidas");


			// Prueba extraerStockLote, extraerStockMP
			
//			extraerStockLote(cooperativa);
//			extraerStockLote(receptor1);
//			
//			LinkedList<StockLote> recibido = extraerStockLote(retailer);
//			System.out.println("Stock Retailer");
//			for(int i=0; i<recibido.size(); i++ ) {
//				System.out.print("Identificador: ");
//				System.out.println(recibido.get(i).getIdBd());
//				System.out.print("Tipo: ");
//				System.out.println(recibido.get(i).getTipo());
//
//			}
//			System.out.println("Lotes Stock extraidos");
//
//
//			System.out.print("Stock Fabrica lupuloTettnanger: ");
//			System.out.println(extraerStockMP(fabrica, lupuloTettnanger));
//			extraerStockMP(emisor1, maltaPilsner);
//			extraerStockMP(emisor2, cebadaTostada);
//			extraerStockMP(productor, lupuloPerle);
//			extraerStockMP(retailer, cebadaTostada);
//			extraerStockMP(receptor2, maltaNegra);
//			System.out.println("Materias Primas Stock extraidos");
			
			//Prueba insertarGeolocalizacion, extraerGeolocalizacion, extraerGeolocalizaciones
			
			Date dategeo3 = new Date(17,04,2018);
			Date dategeo4 = new Date(19,06,2018);
			
			geolocalizacion geo3 = new geolocalizacion(3, 1, 1, "22.72º N 12.94º O",dategeo1);
			geolocalizacion geo4 = new geolocalizacion(4, 2, 2, "22.73º N 12.96º O",dategeo2);
			
			insertarGeolocalizacion(geo3);
			insertarGeolocalizacion(geo4);
			
			System.out.println("Geolocalizaciones introducidas");
			
			extraerGeolocalizacion(1);
			extraerGeolocalizacion(2);
			extraerGeolocalizacion(3);
			extraerGeolocalizacion(4);
			
			System.out.println("Geolocalizaciones extraidas");
			
//			System.out.println("Extraemos geolocalizaciones referentes a las orden 1:");
			
//			ArrayList<geolocalizacion> res = extraerGeolocalizaciones(1);
//			for(int i=0; i< res.size(); i++) {
//				System.out.println("Id orden:" + res.get(i).getId());
//			}
			
			//insertarProductosOrden y extraerProductosOrden
			
			System.out.println("Insertamos productos referentes a las orden 1:");
			ArrayList<Integer> productos = new ArrayList<Integer>();
			productos.add(1);
			productos.add(2);
//			insertarProductosOrden(productos, 1);
//			
//			System.out.println("Extraemos productos referentes a las orden 1:");
//			
//			ArrayList<Integer> res = extraerProductosOrden(1);
//			for(int i=0; i< res.size(); i++) {
//				System.out.println("Id orden:" + res.get(i));
//			}
			
			//extraerOrdenesActorOrigen, extraerOrdenesActorDestino, registrosConOrden
			System.out.println("Extraemos ordenes referentes a transpG3_1 y transpG3_2: ");
			
			ArrayList<OrdenTrazabilidad> elem1 = extraerOrdenesActorOrigen("11");
			ArrayList<OrdenTrazabilidad> elem2 = extraerOrdenesActorDestino("12");
		
			for(int i=0; i< elem1.size(); i++) {
				System.out.println("Orden de actor transpG3_1:" + elem1.get(i).getId());
			}
			
			for(int i=0; i< elem2.size(); i++) {
				System.out.println("Orden de actor transpG3_2:" + elem2.get(i).getId());
			}
			System.out.println("Extraemos registros referentes a orden con id=2: ");
			
//			LinkedList<Registro> elem3 = registrosConOrden(2);
//			
//			for(int i=0; i< elem3.size(); i++) {
//				 Registro aux = elem3.get(i);
//				System.out.println("Registro con Orden 2:" + aux.getId() + "Fecha ini:"
//						+ aux.getFechaInicio() + "Fecha fin:" + aux.getFechaFin());
//			}

			System.out.print("Pasados todos los test correctamente");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
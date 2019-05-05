package equipo5;

import equipo5.dao.metodosCompany;
import equipo5.model.Cadena;
import equipo5.model.StockLote;
import equipo5.model.StockMP;
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

			// PETICIONES GRUPOS QUE DEBEN ESTAR EN LA BBDD.

			// Un actor y una localizacion pedido por el grupo6 (Blockchain)
			Actor G6 = new Actor("33", "G6", "password", "G6@gmail.es", 2, "Calle Jueves", "Luis", "Calle Olivares",
					"fg7");
			insertarActor(G6);

			Date dategeo1 = new Date(11, 04, 2018);
			Date dategeo2 = new Date(15, 04, 2018);

			geolocalizacion geo1 = new geolocalizacion(1, 1, 1, "36.9211;-2.9411", dategeo1);
			geolocalizacion geo2 = new geolocalizacion(2, 2, 2, "36.9521;-2.9811", dategeo2);

			insertarGeolocalizacion(geo1);
			insertarGeolocalizacion(geo2);

			// Transportistas y pedidos (Orden trazabilidad) pedidos por el Grupo 3
			Actor tranpG3_1 = new Actor("11", "TranspG3_1", "password", "tranpG3_1@gmail.es", 2, "Calle Diaz", "Maria",
					"Calle Renacimiento", "fg4");
			Actor tranpG3_2 = new Actor("12", "TranspG3_2", "password", "tranpG3_2@gmail.es", 2, "Calle Santa Elena",
					"Jose", "Calle Ilustración", "fg5");

			insertarActor(tranpG3_1);
			insertarActor(tranpG3_2);

			Productos productosG3_1 = new Productos(11, 2, 9, 2, 1, 7, 8, 0, 11, 4, 7);

			insertarProductos(productosG3_1, 11);

			Date dateiniG3_1 = new Date(11, 11, 2018);
			Date datefinG3_1 = new Date(31, 12, 2019);

			Date dateMoli1 = new Date(31, 11, 2019);
			Date dateCoci1 = new Date(1, 12, 2019);
			Date dateFerme1_1 = new Date(4, 12, 2019);
			Date dateFerme1_2 = new Date(12, 12, 2019);
			Date dateEmbo1 = new Date(28, 12, 2019);

			Lote loteG3_1 = new Lote(11, dateiniG3_1, datefinG3_1, null, false, true, false, true, false, "pilsner",
					dateMoli1, dateCoci1, dateFerme1_1, dateFerme1_2, dateEmbo1);
			loteG3_1.setQr(GeneradorQR2.generadorQR(loteG3_1.getIdBd()));
			insertarLote(loteG3_1);

			Registro registroG3_1 = new Registro(11, 11, dateiniG3_1.toLocaleString(), datefinG3_1.toLocaleString(), 30,
					5);

			insertarRegistro(registroG3_1);

			byte[] firmarecG3_1 = null;
			byte[] firmaentG3_2 = null;

			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(11);
			OrdenTrazabilidad ordenG3_1 = new OrdenTrazabilidad(11, tranpG3_1, tranpG3_2, productosG3_1);
			insertarOrdenTrazabilidad(ordenG3_1);

			// MateriaPrima pedida Grupo 4

			MateriaPrima maltaPilsner = new MateriaPrima("maltaPilsner", 1, 0);
			insertarMateriaPrima(maltaPilsner);
			MateriaPrima maltaBasePalida = new MateriaPrima("maltaBasePalida", 2, 0);
			insertarMateriaPrima(maltaBasePalida);
			MateriaPrima maltaMunich = new MateriaPrima("maltaMunich", 3, 0);
			insertarMateriaPrima(maltaMunich);
			MateriaPrima maltaNegra = new MateriaPrima("maltaNegra", 4, 0);
			insertarMateriaPrima(maltaNegra);
			MateriaPrima maltaCrystal = new MateriaPrima("maltaCrystal", 5, 0);
			insertarMateriaPrima(maltaCrystal);
			MateriaPrima maltaChocolate = new MateriaPrima("maltaChocolate", 6, 0);
			insertarMateriaPrima(maltaChocolate);
			MateriaPrima cebadaTostada = new MateriaPrima("cebadaTostada", 7, 0);
			insertarMateriaPrima(cebadaTostada);
			MateriaPrima lupuloPerle = new MateriaPrima("lupuloPerle", 8, 0);
			insertarMateriaPrima(lupuloPerle);
			MateriaPrima lupuloTettnanger = new MateriaPrima("lupuloTettnanger", 9, 0);
			insertarMateriaPrima(lupuloTettnanger);
			MateriaPrima lupuloCentennial = new MateriaPrima("lupuloCentennial", 10, 0);
			insertarMateriaPrima(lupuloCentennial);
			MateriaPrima levaduraAle = new MateriaPrima("levaduraAle", 11, 0);
			insertarMateriaPrima(levaduraAle);
			MateriaPrima levaduraLager = new MateriaPrima("levaduraLager", 12, 0);
			insertarMateriaPrima(levaduraLager);
			System.out.println("Materias Primas Introducidas");

			// Actores Pedidos Grupo 2

			Actor agricultorG2 = new Actor("0", "agricultorG2", "password", "agricultorG2@gmail.es", 0, "Calle Enero",
					"Marta", "Calle Girona", "fg3");
			Actor cooperativaG2 = new Actor("1", "cooperativaG2", "password", "cooperativaG2@gmail.es", 1,
					"Calle Febrero", "Maria", "Calle Tarragona", "fg2");
			Actor fabricaG3 = new Actor("2", "fabricaG3", "password", "fabricaG3@gmail.es", 3, "Calle Marzo", "Luis",
					"Calle Lerida", "fg1");
			Actor retailerG2 = new Actor("3", "retailerG2", "password", "retailerG2@gmail.es", 4, "Calle Abril",
					"Santiago", "Calle Barcelona", "fg3");

			insertarActor(agricultorG2);
			insertarActor(cooperativaG2);
			insertarActor(fabricaG3);
			insertarActor(retailerG2);

			Productos prodG2_1 = new Productos(1, 12, 2, 4, 3, 1, 8, 10, 1, 9, 2);
			Productos prodG2_2 = new Productos(18, 12, 3, 3, 2, 1, 8, 11, 11, 2, 0);
			Productos prodG2_3 = new Productos(11, 1, 5, 23, 2, 12, 5, 2, 11, 3, 1);
			Productos prodG2_4 = new Productos(19, 12, 5, 0, 3, 1, 3, 10, 11, 9, 21);

			OrdenTrazabilidad ordenG2_1 = new OrdenTrazabilidad(50, agricultorG2, agricultorG2, prodG2_1);
			insertarOrdenTrazabilidad(ordenG2_1);
			OrdenTrazabilidad ordenG2_2 = new OrdenTrazabilidad(51, agricultorG2, cooperativaG2, prodG2_2);
			insertarOrdenTrazabilidad(ordenG2_2);
			OrdenTrazabilidad ordenG2_3 = new OrdenTrazabilidad(52, cooperativaG2, fabricaG3, prodG2_3);
			insertarOrdenTrazabilidad(ordenG2_3);
			OrdenTrazabilidad ordenG2_4 = new OrdenTrazabilidad(53, fabricaG3, retailerG2, prodG2_4);
			insertarOrdenTrazabilidad(ordenG2_4);

			Date dateiniG2_1 = new Date(6, 11, 2018);
			Date datefinG2_1 = new Date(19, 11, 2018);
			Date dateiniG2_2 = new Date(21, 11, 2018);
			Date datefinG2_2 = new Date(27, 11, 2018);
			Date dateiniG2_3 = new Date(28, 11, 2018);
			Date datefinG2_3 = new Date(29, 11, 2018);
			Date dateiniG2_4 = new Date(5, 12, 2018);
			Date datefinG2_4 = new Date(7, 12, 2018);

			StockMP stockG2_1 = new StockMP(cebadaTostada, dateiniG2_1, datefinG2_1, 50, 5, "0");
			StockMP stockG2_2 = new StockMP(levaduraAle, dateiniG2_1, datefinG2_1, 50, 5, "0");
			StockMP stockG2_3 = new StockMP(maltaNegra, dateiniG2_2, datefinG2_2, 51, 5, "1");
			StockMP stockG2_4 = new StockMP(maltaPilsner, dateiniG2_2, datefinG2_2, 51, 5, "1");
			StockMP stockG2_5 = new StockMP(levaduraLager, dateiniG2_3, datefinG2_3, 52, 5, "2");
			StockMP stockG2_6 = new StockMP(maltaChocolate, dateiniG2_3, datefinG2_3, 52, 5, "2");
			StockMP stockG2_7 = new StockMP(maltaPilsner, dateiniG2_4, datefinG2_4, 53, 5, "3");
			StockMP stockG2_8 = new StockMP(levaduraAle, dateiniG2_4, datefinG2_4, 53, 5, "3");

			insertarStockMP(stockG2_1);
			insertarStockMP(stockG2_2);
			insertarStockMP(stockG2_3);
			insertarStockMP(stockG2_4);
			insertarStockMP(stockG2_5);
			insertarStockMP(stockG2_6);
			insertarStockMP(stockG2_7);
			insertarStockMP(stockG2_8);

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
			Actor error = new Actor("200", "rickkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"
					+ "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"
					+ "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"
					+ "kkkk"
					+ "", "password", "rick@gmail.es", 4, "Calle Gaseta", "rick",
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
			
			//SQL INJECTION
			//System.out.println(extraerActor("rick; DROP TABLE actor").getId());
			//System.out.println(extraerActor("rick; SELECT * FROM GEOLOCALIZACIONES").getId());
			
			//Sin conocer ningún dato de la BBDD
			//System.out.println(extraerActor("jajks OR 1=1; SELECT * FROM LOTE").getId());
			
			//insertarActor(error);
			//System.out.println(extraerActor("").getId());

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
			Date dateMoli2 = new Date(31, 11, 2016);
			Date dateCoci2 = new Date(1, 12, 2017);
			Date dateFerme2_1 = new Date(4, 12, 2018);
			Date dateFerme2_2 = new Date(5, 12, 2018);
			Date dateEmbo2 = new Date(28, 12, 2019);

			Date dateini3 = new Date(6, 11, 2018);
			Date datefin3 = new Date(8, 12, 2018);
			Date dateMoli3 = new Date(31, 11, 2019);
			Date dateCoci3 = new Date(1, 12, 2019);
			Date dateFerme3_1 = new Date(4, 12, 2019);
			Date dateFerme3_2 = new Date(12, 12, 2019);
			Date dateEmbo3 = new Date(28, 12, 2019);

			Lote lote1 = new Lote(1, dateini2, datefin2, null, false, true, false, true, false, "stout", dateMoli2,
					dateCoci2, dateFerme2_1, dateFerme2_2, dateEmbo2);
			Lote lote2 = new Lote(2, dateini3, datefin3, null, false, true, false, true, false, "pilsner", dateMoli3,
					dateCoci3, dateFerme3_1, dateFerme3_2, dateEmbo3);

			lote1.setQr(GeneradorQR2.generadorQR(lote1.getIdBd()));
			lote2.setQr(GeneradorQR2.generadorQR(lote2.getIdBd()));

			insertarLote(lote1);
			insertarLote(lote2);
			System.out.println("Lotes introducidos");

			extraerLote(1);
			extraerLote(2);
			System.out.println("Lotes extraidos");
			
			
			// Sin fecha de inicio

			Lote lotemal = new Lote(1, null, datefin2, null, false, true, false, true, false, "stout", dateMoli2,
					dateCoci2, dateFerme2_1, dateFerme2_2, dateEmbo2);
//			insertarLote(lotemal);


			// Pruebas Productos
			Productos productos1 = new Productos(1, 12, 5, 0, 4, 7, 8, 10, 11, 0, 1);
			Productos productos2 = new Productos(2, 9, 5, 0, 4, 0, 4, 0, 6, 0, 1);

			insertarProductos(productos1, 1);
			insertarProductos(productos2, 2);
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
			orden1.setNecesitaTransportista(true);
			insertarOrdenTrazabilidad(orden1);
			extraerOrdenTrazabilidad(1);

			OrdenTrazabilidad orden2 = new OrdenTrazabilidad(2, emisor2, emisor1, productosord2);
			insertarOrdenTrazabilidad(orden2);
			extraerOrdenTrazabilidad(2);

			// Prueba registros

			Registro registro1 = new Registro(1, 1, dateini2.toLocaleString(), datefin2.toLocaleString(), 40, 10);
			registro1.setId(2);
			Registro registro2 = new Registro(2, 2, dateini2.toLocaleString(), datefin2.toLocaleString(), 25, 20);
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

			Bloque bloque1 = new Bloque("hashPrevio1", 1, 1, lote1.getIdBd(), datos1, 1, -1);
			Bloque bloque2 = new Bloque("hashPrevio2", 2, 2, lote2.getIdBd(), datos2, 2, -1);

			insertarBloque(bloque1);
			insertarBloque(bloque2);

			System.out.println(bloque1.getHashCode().length());

			extraerBloque(bloque1.getHashCode());
			extraerBloque(bloque2.getHashCode());

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

			Date dateinistock1 = new Date(6, 11, 2019);
			Date datefinstock1 = new Date(19, 11, 2019);
			Date dateinistock2 = new Date(21, 11, 2019);
			Date datefinstock2 = new Date(27, 11, 2019);

			StockMP stockMp1 = new StockMP(cebadaTostada, dateinistock1, datefinstock1, 1, 1, "10");
			StockMP stockMp2 = new StockMP(levaduraAle, dateinistock2, datefinstock2, 2, 2, "16");

			insertarStockMP(stockMp1);
			insertarStockMP(stockMp2);

			System.out.println("Materias Primas Stock introducidas");

			// Prueba insertarStockLote
			Date dateinilote1 = new Date(6, 11, 2020);
			Date datefinlote1 = new Date(19, 11, 2020);
			Date dateinilote2 = new Date(21, 11, 2020);
			Date datefinlote2 = new Date(27, 11, 2020);

			StockLote stockLote1 = new StockLote(lote1, dateinilote1, datefinlote1, 1, 1, "16");
			StockLote stockLote2 = new StockLote(lote2, dateinilote2, datefinlote2, 2, 2, "18");
			insertarStockLote(stockLote1);
			insertarStockLote(stockLote2);

			System.out.println("Lotes Stock introducidas");

			// Prueba extraerStockLote, extraerStockMP

			extraerStockLote(receptor1, 1);
			extraerStockLote(receptor2, 2);

			LinkedList<StockLote> recibido = extraerStockLote(emisor1, 1);
			System.out.println("Stock emisor1");
			for (int i = 0; i < recibido.size(); i++) {
				System.out.print("Identificador actor: ");
				System.out.println(recibido.get(i).getIdActor());
				System.out.print("Orden: ");
				System.out.println(recibido.get(i).getIdOrden());
				System.out.println("Lote:" + recibido.get(i).getLote().getIdBd());

			}
			System.out.println("Lotes Stock extraidos");

			extraerStockMP(prueba, 1);
			extraerStockMP(receptor1, 2);
			
			LinkedList<StockMP> recibido1 = extraerStockMP(emisor1, 1);
			System.out.println("Stock emisor1");
			for(int i=0; i<recibido1.size(); i++ ) {
				System.out.print("Identificador actor: ");
				System.out.println(recibido1.get(i).getIdActor());
				System.out.print("Orden: ");
				System.out.println(recibido1.get(i).getIdOrden());
				System.out.println("MateriaPrima:" + recibido1.get(i).getMp());

			}

			System.out.println("Materias Primas Stock extraidos");

			// Prueba insertarGeolocalizacion, extraerGeolocalizacion,
			// extraerGeolocalizaciones

			Date dategeo3 = new Date(17, 04, 2018);
			Date dategeo4 = new Date(19, 06, 2018);

			geolocalizacion geo3 = new geolocalizacion(3, 1, 1, "22.7212;-12.9434", dategeo1);
			geolocalizacion geo4 = new geolocalizacion(4, 2, 2, "22.7300;-12.9633", dategeo2);

			insertarGeolocalizacion(geo3);
			insertarGeolocalizacion(geo4);

			System.out.println("Geolocalizaciones introducidas");

			extraerGeolocalizacion(1);
			extraerGeolocalizacion(2);
			extraerGeolocalizacion(3);
			extraerGeolocalizacion(4);

			System.out.println("Geolocalizaciones extraidas");

			System.out.println("Extraemos geolocalizaciones referentes a las orden 1:");

			ArrayList<geolocalizacion> res = extraerGeolocalizaciones(1);
			for (int i = 0; i < res.size(); i++) {
				System.out.println("Id orden:" + res.get(i).getId());
			}

			// insertarProductosOrden y extraerProductosOrden

			System.out.println("Insertamos productos referentes a las orden 1:");
			ArrayList<Integer> productos = new ArrayList<Integer>();
			productos.add(1);
			productos.add(2);
			insertarProductosOrden(productos, 1);

			System.out.println("Extraemos productos referentes a las orden 1:");

			ArrayList<Integer> prod = extraerProductosOrden(1);
			for (int i = 0; i < prod.size(); i++) {
				System.out.println("Id orden:" + prod.get(i));
			}

			// extraerOrdenesActorOrigen, extraerOrdenesActorDestino, registrosConOrden
			System.out.println("Extraemos ordenes referentes a transpG3_1 y transpG3_2: ");

			ArrayList<OrdenTrazabilidad> elem1 = extraerOrdenesActorOrigen("11");
			ArrayList<OrdenTrazabilidad> elem2 = extraerOrdenesActorDestino("12");

			for (int i = 0; i < elem1.size(); i++) {
				System.out.println("Orden de actor transpG3_1:" + elem1.get(i).getId());
			}

			for (int i = 0; i < elem2.size(); i++) {
				System.out.println("Orden de actor transpG3_2:" + elem2.get(i).getId());
			}
			System.out.println("Extraemos registros referentes a orden con id=2: ");

			LinkedList<Registro> elem3 = registrosConOrden(2);

			for (int i = 0; i < elem3.size(); i++) {
				Registro aux = elem3.get(i);
				System.out.println("Registro con Orden 2:" + aux.getId() + "Fecha ini:" + aux.getFechaInicio()
						+ "Fecha fin:" + aux.getFechaFin());
			}

			System.out.print("Pasados todos los test correctamente");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
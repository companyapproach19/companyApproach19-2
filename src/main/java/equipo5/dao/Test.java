package equipo5;

import equipo5.dao.NullException;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import equipo4.model.Lote;
import equipo4.model.MateriaPrima;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Test extends metodosCompany {

	public static void main(String[] args) throws Throwable {
		try {
			conectar();
			crearBD();

			// PETICIONES GRUPOS QUE DEBEN ESTAR EN LA BBDD.
			// Pruebas Actor
			
			Actor agricultor = new Actor("0", "Productor", "password", "prod@gmail.es", 0, "40.4026076;-3.8363219", "Marta",
					"Calle Valladolid", "3");
			Actor cooperativa = new Actor("1", "Cooperativa", "password", "coop@gmail.es", 1, "40.4022076;-3.8369319", "Maria",
					"Calle Murcia", null);
			Actor transportista = new Actor("2", "Transportista", "password", "transp@gmail.es", 2, "40.4029076;-3.8369319",
					"Luis", "Calle Cartagena", null);
			Actor fabrica = new Actor("3", "Fabrica", "password", "fab@gmail.es", 3, "40.4025976;-3.8393319", "Fabrica",
					"Calle AndalucÃ­a", null);
			Actor retailer = new Actor("4", "Retailer", "password", "ret@gmail.es", 4, "40.4025079;-3.8369319", "Pilar",
					"Calle Madrid", null);

			insertarActor(agricultor);
			insertarActor(cooperativa);
			insertarActor(transportista);
			insertarActor(fabrica);
			insertarActor(retailer);
			
			// Un actor y una localizacion pedido por el grupo6 (Blockchain)

			Date dategeo1 = Date.valueOf("2018-4-22");
			Date dategeo2 = Date.valueOf("2018-4-23");

			geolocalizacion geo1 = new geolocalizacion(1, 1, 1, "36.9211;-2.9411", dategeo1);
			geolocalizacion geo2 = new geolocalizacion(2, 2, 2, "36.9521;-2.9811", dategeo2);

			insertarGeolocalizacion(geo1);
			insertarGeolocalizacion(geo2);

			// Transportistas y pedidos (Orden trazabilidad) pedidos por el Grupo 3

			Productos productosG3_1 = new Productos(11, 2, 9, 2, 1, 7, 8, 0, 11, 4, 7,5,6,7,8);

			insertarProductos(productosG3_1, 11);

			Date dateiniG3_1 =  Date.valueOf("2018-11-11");
			Date datefinG3_1 = Date.valueOf("2018-12-31");
//			// Un actor y una localizacion pedido por el grupo6 (Blockchain)

			Date dateMoli1 =  Date.valueOf("2018-11-31");
			Date dateCoci1 =  Date.valueOf("2018-12-1");
			Date dateFerme1_1 =  Date.valueOf("2018-4-12");
			Date dateFerme1_2 =  Date.valueOf("2018-12-12");
			Date dateEmbo1 =  Date.valueOf("2018-12-28");

			Lote loteG3_1 = new Lote(11, dateiniG3_1, datefinG3_1, null, false, true, false, true, false, "pilsner",
					dateMoli1, dateCoci1, dateFerme1_1, dateFerme1_2, dateEmbo1);
			loteG3_1.setQr(GeneradorQR2.generadorQR(loteG3_1.getIdBd()));
			insertarLote(loteG3_1);

			Registro registroG3_1 = new Registro(11, 11, dateiniG3_1.toLocaleString(), datefinG3_1.toLocaleString(), 30,
					5);

			insertarRegistro(registroG3_1);

			String firmarecG3_1 = "";
			String firmaentG3_2 ="";

			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(11);
			OrdenTrazabilidad ordenG3_1 = new OrdenTrazabilidad(11, retailer, fabrica, true, productosG3_1,
					list, 1, firmarecG3_1, firmaentG3_2, transportista, 11, 1, dateiniG3_1);
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

			Productos prodG2_1 = new Productos(1, 12, 2, 4, 3, 1, 8, 10, 1, 9, 2,3,4,5,6);
			Productos prodG2_2 = new Productos(18, 12, 3, 3, 2, 1, 8, 11, 11, 2, 0,5,6,7,8);
			Productos prodG2_3 = new Productos(11, 1, 5, 23, 2, 12, 5, 2, 11, 3, 1,6,7,8,9);
			Productos prodG2_4 = new Productos(19, 12, 5, 0, 3, 1, 3, 10, 11, 9, 21,6,56,56,6);

			OrdenTrazabilidad ordenG2_1 = new OrdenTrazabilidad(50, cooperativa, agricultor, prodG2_1);
			insertarOrdenTrazabilidad(ordenG2_1);
			OrdenTrazabilidad ordenG2_2 = new OrdenTrazabilidad(51, fabrica, cooperativa, prodG2_2);
			insertarOrdenTrazabilidad(ordenG2_2);
			OrdenTrazabilidad ordenG2_3 = new OrdenTrazabilidad(52, retailer, fabrica, prodG2_3);
			insertarOrdenTrazabilidad(ordenG2_3);
			OrdenTrazabilidad ordenG2_4 = new OrdenTrazabilidad(53, fabrica, cooperativa, prodG2_4);
			insertarOrdenTrazabilidad(ordenG2_4);
			
			
			//Prueba extraerStockMpPorPedido
			System.out.println("Pruebas extraerStockMpPorPedido");
			System.out.print("Extraemos stock del pedido ordenG2_1, actor fabrica: ");
			extraerStockMpPorPedido(fabrica, ordenG2_1);
			System.out.println("OK");
			System.out.print("Extraemos stock del pedido ordenG2_1, actor fabrica: ");
			extraerStockMpPorPedido(retailer, ordenG2_3);
			System.out.println("OK");
			
			Actor malActor = new Actor();
			//actor erróneo (inexistente en la BBDD)
			System.out.println("extraerStockMpPorPedido con un actor (con todo a null) erróneo devuelve: " + extraerStockMpPorPedido(malActor, ordenG2_1));
			
			//orden errónea (inexistente en la BBDD)
			OrdenTrazabilidad ordenG2_SinInsertar = new OrdenTrazabilidad(555, fabrica, cooperativa, prodG2_4);
			System.out.println("extraerStockMpPorPedido con una orden errónea devuelve: " + extraerStockMpPorPedido(fabrica, ordenG2_SinInsertar));
			

			Date dateiniG2_1 =  Date.valueOf("2018-6-11");
			Date datefinG2_1 =  Date.valueOf("2018-11-22");
			Date dateiniG2_2 = Date.valueOf("2018-11-24");
			Date datefinG2_2 = Date.valueOf("2018-11-25");
			Date dateiniG2_3 = Date.valueOf("2018-11-27");
			Date datefinG2_3 = Date.valueOf("2018-11-28");
			Date dateiniG2_4 = Date.valueOf("2018-12-2");
			Date datefinG2_4 = Date.valueOf("2018-12-7");

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
			
			//insertamos dos veces el mismo stockMp
			insertarStockMP(stockG2_8);
			
			StockMP stockmal = new StockMP(levaduraAle, dateiniG2_4, datefinG2_4, 53, 5, null);
			//insertarStockMP(stockmal);

			// Actores pedidos equipo 7
			
			//Caso error (insertar dos veces)
			//insertarActor(retailer);
			
			//Ordenes en todos los estados posibles 
			//-1=Rechazado
			OrdenTrazabilidad ordenG7_1 = new OrdenTrazabilidad(100, cooperativa, agricultor, prodG2_1);
			ordenG7_1.setEstado(-1);
			insertarOrdenTrazabilidad(ordenG7_1);
			//0=Pendiente por aceptar
			OrdenTrazabilidad ordenG7_2 = new OrdenTrazabilidad(101, cooperativa, agricultor, prodG2_2);
			ordenG7_2.setEstado(0);
			insertarOrdenTrazabilidad(ordenG7_2);
			//1=En proceso de preparación
			OrdenTrazabilidad ordenG7_3 = new OrdenTrazabilidad(102, cooperativa, agricultor, prodG2_3);
			ordenG7_3.setEstado(1);
			insertarOrdenTrazabilidad(ordenG7_3);
			//2=Listo para entregar (aún no se ha enviado)
			OrdenTrazabilidad ordenG7_4 = new OrdenTrazabilidad(103, retailer, fabrica, prodG2_4);
			ordenG7_4.setEstado(2);
			insertarOrdenTrazabilidad(ordenG7_4);
			//3=En proceso de entrega (transportándose)
			OrdenTrazabilidad ordenG7_5 = new OrdenTrazabilidad(104, fabrica, cooperativa, prodG2_4);
			ordenG7_5.setEstado(3);
			insertarOrdenTrazabilidad(ordenG7_5);
			//4=Entregado
			OrdenTrazabilidad ordenG7_6 = new OrdenTrazabilidad(105, fabrica, cooperativa, prodG2_4);
			ordenG7_6.setEstado(4);
			insertarOrdenTrazabilidad(ordenG7_6);

			// PRUEBAS BASICAS, NINGUNA DEBE LANZAR ERROR.

			
			
			//SQL INJECTION
//			//System.out.println(extraerActor("rick; DROP TABLE actor").getId());
//			//System.out.println(extraerActor("rick; SELECT * FROM GEOLOCALIZACIONES").getId());
//			
//			//Sin conocer ningÃºn dato de la BBDD
//			//System.out.println(extraerActor("jajks OR 1=1; SELECT * FROM LOTE").getId());
//			
//			//insertarActor(error);
//			//System.out.println(extraerActor("").getId());
//
			CadenaActores cadena = extraerCadenaActores();
			List<Actor> listact = cadena.getlista_actores();
			System.out.println("Prueba cadena actores: ");
			for (int i = 0; i < listact.size(); i++) {
				System.out.println(listact.get(i).getNombreUsuario());
			}

			System.out.println("Actores extraidos");

			// Prueba lotes

			Date dateini2 = Date.valueOf("2018-11-2");
			Date datefin2 = Date.valueOf("2018-12-2");
			Date dateMoli2 =Date.valueOf("2018-11-3");
			Date dateCoci2 = Date.valueOf("2018-11-6");
			Date dateFerme2_1 = Date.valueOf("2018-11-12");
			Date dateFerme2_2 = Date.valueOf("2018-11-16");
			Date dateEmbo2 = Date.valueOf("2018-11-19");

			Lote lote1 = new Lote(1, dateini2, datefin2, null, false, true, false, true, false, "stout", dateMoli2,
					dateCoci2, dateFerme2_1, dateFerme2_2, dateEmbo2);
			Lote lote2 = new Lote(2, dateini2, datefin2, null, false, true, false, true, false, "pilsner", dateMoli2,
					dateCoci2, dateFerme2_1, dateFerme2_2, dateEmbo2);

			lote1.setQr(GeneradorQR2.generadorQR(lote1.getIdBd()));
			lote2.setQr(GeneradorQR2.generadorQR(lote2.getIdBd()));

			insertarLote(lote1);
			insertarLote(lote2);
			System.out.println("Lotes introducidos");

			extraerLote(1);
			extraerLote(2);
			System.out.println("Lotes extraidos");
			
			
			System.out.println("Extarer lote inexistente devuelve:" + extraerLote(800));     
			
			
			// Sin fecha de inicio

			Lote lotemal = new Lote(1, null, datefin2, null, false, true, false, true, false, "stout", dateMoli2,
					dateCoci2, dateFerme2_1, dateFerme2_2, dateEmbo2);
			//insertarLote(lotemal);


			// Pruebas Productos
			Productos productos1 = new Productos(1, 12, 5, 0, 4, 7, 8, 10, 11, 0, 1,5,6,7,8);
			Productos productos2 = new Productos(2, 9, 5, 0, 4, 0, 4, 0, 6, 0, 1,6,7,2,0);

			insertarProductos(productos1, 1);
			insertarProductos(productos2, 2);
			//Caso error insertar dos veces el mismo
			//insertarProductos(productos2, 2);
			System.out.println("Productos introducidos");

			extraerProductos(1);
			extraerProductos(2);
			System.out.println("Productos extraidos");
			
			//Caso productos inexistentes
			System.out.println(extraerProductos(900));

			// Pruebas OrdenTrazabilidad

			Productos productosord1 = new Productos(4, 2, 9, 2, 4, 7, 8, 6, 11, 4, 7,6,7,8,9);
			Productos productosord2 = new Productos(5, 1, 5, 0, 4, 7, 81, 10, 11, 9, 1,7,8,4,5);

			byte[] firmarec = null;
			byte[] firmaent = null;

			OrdenTrazabilidad orden1 = new OrdenTrazabilidad(1, fabrica, cooperativa, productosord1);
			orden1.setEstado(2);
			//PEDIDO POR EL GRUPO 1 
			orden1.setNecesitaTransportista(true);
			insertarOrdenTrazabilidad(orden1);
			extraerOrdenTrazabilidad(1);
			
			OrdenTrazabilidad orden2 = new OrdenTrazabilidad(33, fabrica, cooperativa, productosord1);
			orden2.setEstado(3);
			//PEDIDO POR EL GRUPO 1 
			orden2.setNecesitaTransportista(true);
			insertarOrdenTrazabilidad(orden2);
			extraerOrdenTrazabilidad(2);
			
			OrdenTrazabilidad orden3 = new OrdenTrazabilidad(34, fabrica, cooperativa, productosord1);
			orden3.setEstado(4);
			//PEDIDO POR EL GRUPO 1 
			orden3.setNecesitaTransportista(true);
			insertarOrdenTrazabilidad(orden3);
			extraerOrdenTrazabilidad(3);

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

			Date dateinistock1 = Date.valueOf("2019-11-6");
			Date datefinstock1 = Date.valueOf("2019-11-19");
			Date dateinistock2 = Date.valueOf("2019-11-21");
			Date datefinstock2 = Date.valueOf("2019-11-27");
			Date dateinistock3 = Date.valueOf("2020-11-6");
			Date datefinstock3 = Date.valueOf("2020-11-19");
			Date dateinistock4 = Date.valueOf("2020-11-21");
			Date datefinstock4 = Date.valueOf("2020-11-27");

			StockMP stockMp1 = new StockMP(cebadaTostada, dateinistock1, datefinstock1, 1, 1, "0");
			StockMP stockMp2 = new StockMP(lupuloPerle, dateinistock2, datefinstock2, 2, 2, "1");
			StockMP stockMp3 = new StockMP(levaduraAle, dateinistock2, datefinstock2, 1, 1, "3");
			StockMP stockMp4 = new StockMP(cebadaTostada, dateinistock3, datefinstock3, 2, 2, "4");
			StockMP stockMp5 = new StockMP(maltaChocolate, dateinistock2, datefinstock2, 1, 1, "1");
			StockMP stockMp6 = new StockMP(levaduraAle, dateinistock4, datefinstock4, 2, 2, "3");
			StockMP stockMp7 = new StockMP(cebadaTostada, dateinistock3, datefinstock3, 1, 1, "2");
			StockMP stockMp8 = new StockMP(levaduraLager, dateinistock4, datefinstock4, 1, 1, "0");

			insertarStockMP(stockMp1);
			insertarStockMP(stockMp2);
			insertarStockMP(stockMp3);
			insertarStockMP(stockMp4);
			insertarStockMP(stockMp5);
			insertarStockMP(stockMp6);
			insertarStockMP(stockMp7);
			insertarStockMP(stockMp8);

			System.out.println("Materias Primas Stock introducidas");

			// Prueba insertarStockLote
			Date dateinilote1 = new Date(2017, 11, 6);
			Date datefinlote1 = new Date(2017, 11, 16);
			Date dateinilote2 = new Date(2017, 11, 21);
			Date datefinlote2 = new Date(2017, 11, 27);
			Date dateinilote3 = new Date(2016, 11, 6);
			Date datefinlote3 = new Date(2016, 11, 16);
			Date dateinilote4 = new Date(2016, 11, 21);
			Date datefinlote4 = new Date(2016, 11, 27);
			
			Lote loteSt1 = new Lote(3, dateinilote3, datefinlote3, null, false, false, false, true, true, "stout", dateMoli2,
					dateCoci2, dateFerme2_1, dateFerme2_2, dateEmbo2);
			Lote loteSt2 = new Lote(4, dateinilote4, datefinlote4,null, false, false, false, true, true, "pilsner", dateMoli2,
					dateCoci2, dateFerme2_1, dateFerme2_2, dateEmbo2);
			insertarLote(loteSt1);
			insertarLote(loteSt2);
			

			StockLote stockLote1 = new StockLote(lote1, dateinilote1, datefinlote1, 11, 11, "0");
			StockLote stockLote2 = new StockLote(lote2, dateinilote2, datefinlote2, 2, 2, "1");
			StockLote stockLote3 = new StockLote(loteG3_1, dateinilote1, datefinlote1, 2, 2, "2");
			StockLote stockLote4 = new StockLote(lote2, dateinilote2, datefinlote2, 1, 1, "4");
			StockLote stockLote5 = new StockLote(loteG3_1, dateinilote3, datefinlote3, 2, 2, "0");
			StockLote stockLote6 = new StockLote(loteSt1, dateinilote4, datefinlote4, 11, 11, "3");
			StockLote stockLote7 = new StockLote(loteSt1, dateinilote4, datefinlote4, 2, 2, "1");
			StockLote stockLote8 = new StockLote(loteSt1, dateinilote3, datefinlote3, 11, 11, "1");
			
			insertarStockLote(stockLote1);
			insertarStockLote(stockLote2);
			insertarStockLote(stockLote3);
			insertarStockLote(stockLote4);
			insertarStockLote(stockLote5);
			insertarStockLote(stockLote6);
			insertarStockLote(stockLote7);
			insertarStockLote(stockLote8);
			

			System.out.println("Lotes Stock introducidas");

			// Prueba extraerStockLote, extraerStockMP

			extraerStockLote(retailer, 1);
			extraerStockLote(retailer, 2);
			
			//Actor (todos los atributos a null) y lotes inexistentes
			//System.out.println(extraerStockLote(noexiste, 1));
			System.out.println(extraerStockLote(retailer, 189283));
			
			LinkedList<StockLote> recibido = extraerStockLote(fabrica, 1);
			System.out.println("Stock emisor1");
			for (int i = 0; i < recibido.size(); i++) {
				System.out.print("Identificador actor: ");
				System.out.println(recibido.get(i).getIdActor());
				System.out.print("Orden: ");
				System.out.println(recibido.get(i).getIdOrden());
				System.out.println("Lote:" + recibido.get(i).getLote().getIdBd());

			}
			System.out.println("Lotes Stock extraidos");

			
			LinkedList<StockMP> recibido1 = extraerStockMP(fabrica, 1);
			System.out.println("Stock prueba (es un agricultor)");
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

			Date dategeo3 = new Date(2018, 04, 17);
			Date dategeo4 = new Date(2018, 06, 19);

			geolocalizacion geo3 = new geolocalizacion(3, 1, 1, "22.7212;-12.9434", dategeo1);
			geolocalizacion geo4 = new geolocalizacion(4, 2, 2, "22.7300;-12.9633", dategeo2);

			insertarGeolocalizacion(geo3);
			insertarGeolocalizacion(geo4);

			System.out.println("Geolocalizaciones introducidas");

			extraerGeolocalizacion(1);
			extraerGeolocalizacion(2);
			extraerGeolocalizacion(3);
			extraerGeolocalizacion(4);
			
			//Caso inexistente
			System.out.println("Objeto geolocalización extraido = null -> " +  extraerGeolocalizacion(900)); 

			System.out.println("Geolocalizaciones extraidas");

			System.out.println("Extraemos geolocalizaciones referentes a las orden 1:");
			
			ArrayList<geolocalizacion> res = extraerGeolocalizaciones(1);
			for (int i = 0; i < res.size(); i++) {
				System.out.println("Id orden:" + res.get(i).getId());
			}
			
			//Extraer geolocalizaciones refentes a una orden inexistente (900)
			System.out.println("extraerGeolocalizaciones(900)=" +  extraerGeolocalizaciones(900));

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
			//Extraer ordenes referentes a un actor origen inexistente
			System.out.println(extraerOrdenesActorOrigen("187812892398"));
			//Actor existente pero sin ordenes asigandas como destino
			System.out.println(extraerOrdenesActorDestino("100"));
			
			System.out.println("Extraemos registros referentes a orden con id=2: ");

			LinkedList<Registro> elem3 = registrosConOrden(2);

			for (int i = 0; i < elem3.size(); i++) {
				Registro aux = elem3.get(i);
				System.out.println("Registro con Orden 2:" + aux.getId() + "Fecha ini:" + aux.getFechaInicio()
						+ "Fecha fin:" + aux.getFechaFin());
			}
			System.out.println(extraerTodasLasOrdenes().size());
			insertarOrdenes();
			
			Lote loteTere = new Lote(840, dateiniG3_1, datefinG3_1, null, false, true, false, true, false, "pilsner",
					dateMoli1, dateCoci1, dateFerme1_1, dateFerme1_2, dateEmbo1);
			loteTere.setQr(GeneradorQR2.generadorQR(loteTere.getIdBd()));
			insertarLote(loteTere);
			
			System.out.print("Pasados todos los test correctamente");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void insertarOrdenes () throws ClassNotFoundException, SQLException, NullException {
		
		Actor agricultor = new Actor("0", "Productor", "password", "prod@gmail.es", 0, "40.4026076;-3.8363219", "Marta",
				"Calle Valladolid", "3");
		Actor cooperativa = new Actor("1", "Cooperativa", "password", "coop@gmail.es", 1, "40.4022076;-3.8369319", "Maria",
				"Calle Murcia", null);
		Actor transportista = new Actor("2", "Transportista", "password", "transp@gmail.es", 2, "40.4029076;-3.8369319",
				"Luis", "Calle Cartagena", null);
		Actor fabrica = new Actor("3", "Fabrica", "password", "fab@gmail.es", 3, "40.4025976;-3.8393319", "Fabrica",
				"Calle AndalucÃ­a", null);
		Actor retailer = new Actor("4", "Retailer", "password", "ret@gmail.es", 4, "40.4025079;-3.8369319", "Pilar",
				"Calle Madrid", null);

		for (int j = 1; j<=30; j++) {
			for (int i = 5; i<=12; i++) {
				Productos prodG2_1 = new Productos(1, 12, 2, 4, 3, 1, 8, 10, 1, 9, 2,3,4,5,6);
				Productos prodG2_2 = new Productos(18, 12, 3, 3, 2, 1, 8, 11, 11, 2, 0,5,6,7,8);
				Productos prodG2_3 = new Productos(0,0,0,0,0,0,0,0,0,0,0,0,0,(i+j)%50,(i*j)%50);
				Productos prodG2_4 = new Productos(19, 12, 5, 0, 3, 1, 3, 10, 11, 9, 21,6,56,56,6);
				
				OrdenTrazabilidad ordenG2_1 = new OrdenTrazabilidad(idOrdenTrazabilidad(), cooperativa, agricultor, prodG2_1);
				ordenG2_1.setFecha(Date.valueOf("2018-"+i+"-"+j));
				ordenG2_1.setEstado(i%4);
				if((i*j)%3 != 0) insertarOrdenTrazabilidadFecha(ordenG2_1);
				OrdenTrazabilidad ordenG2_2 = new OrdenTrazabilidad(idOrdenTrazabilidad(), fabrica, cooperativa, prodG2_2);
				ordenG2_2.setEstado((i+1)%4);
				ordenG2_2.setFecha(Date.valueOf("2018-"+i+"-"+j));
				if((i*j)%5 != 0)insertarOrdenTrazabilidadFecha(ordenG2_2);
				OrdenTrazabilidad ordenG2_3 = new OrdenTrazabilidad(idOrdenTrazabilidad(), retailer, fabrica, prodG2_3);
				ordenG2_3.setEstado((i+2)%4);
				ordenG2_3.setFecha(Date.valueOf("2018-"+i+"-"+j));
				if((i*j)%2 != 0)insertarOrdenTrazabilidadFecha(ordenG2_3);
				OrdenTrazabilidad ordenG2_4 = new OrdenTrazabilidad(idOrdenTrazabilidad(), fabrica, cooperativa, prodG2_4);
				ordenG2_4.setEstado((i+3)%4);
				ordenG2_4.setFecha(Date.valueOf("2018-"+i+"-"+j));
				if((i*j)%4 != 0)insertarOrdenTrazabilidadFecha(ordenG2_4);
			}
			System.out.println("Introducidas Ordenes del dia "+j);
		}
		for (int j = 1; j<=30; j++) {
			for (int i = 1; i<=5; i++) {
				Productos prodG2_1 = new Productos(1, 12, 2, 4, 3, 1, 8, 10, 1, 9, 2,3,4,5,6);
				Productos prodG2_2 = new Productos(18, 12, 3, 3, 2, 1, 8, 11, 11, 2, 0,5,6,7,8);
				Productos prodG2_3 = new Productos(0,0,0,0,0,0,0,0,0,0,0,0,0,(i+j)%50,(i*j)%50);
				Productos prodG2_4 = new Productos(19, 12, 5, 0, 3, 1, 3, 10, 11, 9, 21,6,56,56,6);
				
				OrdenTrazabilidad ordenG2_1 = new OrdenTrazabilidad(idOrdenTrazabilidad(), cooperativa, agricultor, prodG2_1);
				ordenG2_1.setFecha(Date.valueOf("2019-"+i+"-"+j));
				ordenG2_1.setEstado(i%4);
				if((i*j)%3 != 0) insertarOrdenTrazabilidadFecha(ordenG2_1);
				OrdenTrazabilidad ordenG2_2 = new OrdenTrazabilidad(idOrdenTrazabilidad(), fabrica, cooperativa, prodG2_2);
				ordenG2_2.setEstado((i+1)%4);
				ordenG2_2.setFecha(Date.valueOf("2019-"+i+"-"+j));
				if((i*j)%5 != 0)insertarOrdenTrazabilidadFecha(ordenG2_2);
				OrdenTrazabilidad ordenG2_3 = new OrdenTrazabilidad(idOrdenTrazabilidad(), retailer, fabrica, prodG2_3);
				ordenG2_3.setEstado((i+2)%4);
				ordenG2_3.setFecha(Date.valueOf("2019-"+i+"-"+j));
				if((i*j)%2 != 0)insertarOrdenTrazabilidadFecha(ordenG2_3);
				OrdenTrazabilidad ordenG2_4 = new OrdenTrazabilidad(idOrdenTrazabilidad(), fabrica, cooperativa, prodG2_4);
				ordenG2_4.setEstado((i+3)%4);
				ordenG2_4.setFecha(Date.valueOf("2019-"+i+"-"+j));
				if((i*j)%4 != 0)insertarOrdenTrazabilidadFecha(ordenG2_4);
			}
			System.out.println("Introducidas Ordenes del dia "+j);
		}

		
		
	}
	
}
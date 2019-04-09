package equipo5;

import equipo5.dao.metodosCompany;
import equipo5.model.Cadena;
import equipo6.model.Actor;
import equipo6.model.Bloque;
import equipo6.model.CadenaActores;
import equipo6.model.DatosContainer;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo7.model.Transportista;
import equipo8.model.GeneradorQR2;
import equipo8.model.Registro;

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

public class Test extends metodosCompany {

	public static void main(String[] args) throws Throwable {
		try {
			conectar();
			crearBD();

			// PETICIONES GRUPOS QUE DEBEN ESTAR EN LA BBDD.

			// MateriaPrima pedida Grupo 4
			MateriaPrima maltaPilsner = new MateriaPrima("maltaPilsner", 1);
			insertarMateriaPrima(maltaPilsner);
			MateriaPrima maltaBasePalida = new MateriaPrima("maltaBasePalida", 2);
			insertarMateriaPrima(maltaBasePalida);
			MateriaPrima maltaMunich = new MateriaPrima("maltaMunich", 3);
			insertarMateriaPrima(maltaMunich);
			MateriaPrima maltaNegra = new MateriaPrima("maltaNegra", 4);
			insertarMateriaPrima(maltaNegra);
			MateriaPrima maltaCrystal = new MateriaPrima("maltaCrystal", 5);
			insertarMateriaPrima(maltaCrystal);
			MateriaPrima maltaChocolate = new MateriaPrima("maltaChocolate", 6);
			insertarMateriaPrima(maltaChocolate);
			MateriaPrima cebadaTostada = new MateriaPrima("cebadaTostada", 7);
			insertarMateriaPrima(cebadaTostada);
			MateriaPrima lupuloPerle = new MateriaPrima("lupuloPerle", 8);
			insertarMateriaPrima(lupuloPerle);
			MateriaPrima lupuloTettnanger = new MateriaPrima("lupuloTettnanger", 9);
			insertarMateriaPrima(lupuloTettnanger);
			MateriaPrima lupuloCentennial = new MateriaPrima("lupuloCentennial", 10);
			insertarMateriaPrima(lupuloCentennial);
			MateriaPrima levaduraAle = new MateriaPrima("levaduraAle", 11);
			insertarMateriaPrima(levaduraAle);
			MateriaPrima levaduraLager = new MateriaPrima("levaduraLager", 12);
			insertarMateriaPrima(levaduraLager);

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
			Actor emisor1 = new Actor("1", "alberto", "password", "alberto@gmail.es", 1, "Calle Ribera", "alberto",
					"Avenida Jarales", "fg3");
			Actor receptor1 = new Actor("2", "maria", "password", "maria@gmail.es", 3, "Calle Gonzalez", "maria",
					"Avenida Europa", "fg2");
			Actor emisor2 = new Actor("3", "felipe", "password", "felipe@gmail.es", 2, "Calle Sauces", "felipe",
					"Avenida Ilustración", "fg1");
			Actor receptor2 = new Actor("4", "rick", "password", "rick@gmail.es", 4, "Calle Gaseta", "rick",
					"Avenida Argentina", "fg0");

			insertarActor(prueba);
			insertarActor(emisor1);
			insertarActor(emisor2);
			insertarActor(receptor1);
			insertarActor(receptor2);
			extraerActor("Agricultor");
			extraerActor("alberto");
			extraerActor("maria");
			extraerActor("felipe");
			extraerActor("rick");

			CadenaActores cadena = extraerCadenaActores();
			List<Actor> list = cadena.getlista_actores();
			System.out.println("Prueba cadena actores: ");
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getNombreUsuario());
			}

			// Prueba lotes
			Date dateini1 = new Date(2, 12, 2015);
			Date datefin1 = new Date(1, 12, 2019);

			Date dateini2 = new Date(6, 11, 2018);
			Date datefin2 = new Date(8, 10, 2020);

			Date dateini3 = new Date(25, 02, 2018);
			Date datefin3 = new Date(5, 11, 2020);

			Lote lote1 = new Lote(1, dateini1, "pilsner", datefin1, false, true, false, true, false, null, 5);
			Lote lote2 = new Lote(2, dateini2, "stout", datefin2, true, false, true, false, false, null, 4);
			Lote lote3 = new Lote(3, dateini3, "pilsner", datefin3, false, false, true, false, true, null, 3);

			lote1.setQr(GeneradorQR2.generadorQR(lote1.getIdBd()));
			lote2.setQr(GeneradorQR2.generadorQR(lote2.getIdBd()));
			lote3.setQr(GeneradorQR2.generadorQR(lote3.getIdBd()));

			insertarLote(lote1);
			insertarLote(lote2);
			insertarLote(lote3);

			extraerLote(1);
			extraerLote(2);
			extraerLote(3);

			// Pruebas Productos
			Productos productos1 = new Productos(1, 12, 5, 0, 4, 7, 8, 10, 11, 0, 1, 4);

			Productos productos2 = new Productos(2, 9, 5, 0, 4, 0, 4, 0, 6, 0, 1, 4);

			Productos productos3 = new Productos(3, 12, 5, 0, 4, 7, 8, 10, 11, 0, 1, 4);

			insertarProductos(productos1);
			insertarProductos(productos2);
			insertarProductos(productos3);

			extraerProductos(1);
			extraerProductos(2);
			extraerProductos(3);

			// Prueba registros
			Registro registro1 = new Registro(lote1, emisor1, dateini1.toLocaleString(), datefin1.toLocaleString(), 40,
					10);
			registro1.setId(1);
			Registro registro2 = new Registro(lote2, emisor2, dateini2.toLocaleString(), datefin2.toLocaleString(), 25,
					20);
			registro2.setId(2);
			Registro registro3 = new Registro(lote3, prueba, dateini3.toLocaleString(), datefin3.toLocaleString(), 20,
					18);
			registro3.setId(3);

			insertarRegistro(registro1);
			insertarRegistro(registro2);
			insertarRegistro(registro3);

			extraerRegistro(1);
			extraerRegistro(2);
			extraerRegistro(3);

			// Pruebas OrdenTrazabilidad
			Productos productosord1 = new Productos(4, 2, 9, 2, 4, 7, 8, 6, 11, 4, 7, 9);
			Productos productosord2 = new Productos(5, 1, 5, 0, 4, 7, 81, 10, 11, 9, 1, 4);

			byte[] firmarec = null;
			byte[] firmaent = null;

			OrdenTrazabilidad orden1 = new OrdenTrazabilidad(1, emisor1, emisor2, true, productosord1, "", 1, firmarec,
					firmaent, 1, 2, emisor2, registro1);
			insertarOrdenTrazabilidad(orden1);
			extraerOrdenTrazabilidad(1);

			OrdenTrazabilidad orden2 = new OrdenTrazabilidad(2, emisor2, emisor1, false, productosord2, "", 2, firmarec,
					firmaent, 1, 2, receptor2, registro2);
			insertarOrdenTrazabilidad(orden2);
			extraerOrdenTrazabilidad(2);

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

			insertarBloque(bloque1);
			insertarBloque(bloque2);

			System.out.println(extraerBloque(bloque1.getHashCode()).getHashPrevio());
			System.out.println(extraerBloque(bloque2.getHashCode()).getHashPrevio());

			extraerBloque(bloque1.getHashCode());

			extraerBloque(bloque2.getHashCode());

			extraerBloque(bloque1.getHashCode());
			extraerBloque(bloque2.getHashCode());

			// Prueba extraerPedidosActorDestino (Relacionado con OrdenTrazabilidad
			ArrayList<OrdenTrazabilidad> ord1 = extraerPedidosActorDestino("1");
			ArrayList<OrdenTrazabilidad> ord2 = extraerPedidosActorDestino("2");

			System.out.print("Prueba ord1 de extraerPedidosActorDestino");
			for (int i = 0; i < ord1.size(); i++) {
				System.out.println(ord1.get(i).getId());
			}

			System.out.print("Prueba ord2 de extraerPedidosActorDestino");
			for (int i = 0; i < ord2.size(); i++) {
				System.out.println(ord2.get(i).getId());
			}

			// Prueba insertarStockMp, insertarStockLote
			insertarStockMP(emisor1, maltaPilsner, 4);
			insertarStockMP(emisor2, cebadaTostada, 5);
			insertarStockMP(productor, maltaPilsner, 12);
			insertarStockMP(cooperativa, maltaChocolate, 12);
			insertarStockMP(fabrica, maltaPilsner, 12);
			insertarStockMP(productor, cebadaTostada, 10);
			insertarStockMP(cooperativa, maltaPilsner, 8);
			insertarStockMP(fabrica, lupuloTettnanger, 7);
			insertarStockMP(fabrica, maltaPilsner, 12);
			insertarStockMP(productor, cebadaTostada, 10);
			insertarStockMP(cooperativa, maltaPilsner, 8);
			insertarStockMP(fabrica, lupuloTettnanger, 7);
			insertarStockMP(retailer, cebadaTostada, 19);
			insertarStockMP(receptor2, maltaNegra, 11);
			insertarStockMP(productor, cebadaTostada, 8);

			insertarStockLote(emisor1, lote1);
			insertarStockLote(emisor2, lote2);
			insertarStockLote(receptor1, lote3);
			insertarStockLote(productor, lote1);
			insertarStockLote(fabrica, lote2);
			insertarStockLote(receptor1, lote3);
			insertarStockLote(productor, lote1);
			insertarStockLote(fabrica, lote2);
			insertarStockLote(cooperativa, lote3);

			// Prueba extraerStockLote, extraerStockMP
			extraerStockLote(emisor1);
			extraerStockLote(emisor2);
			extraerStockLote(productor);
			extraerStockLote(cooperativa);
			extraerStockLote(receptor1);


			extraerStockMP(emisor1, maltaPilsner);
			extraerStockMP(emisor2, cebadaTostada);
			extraerStockMP(productor, lupuloPerle);
			extraerStockMP(retailer, cebadaTostada);
			extraerStockMP(receptor2, maltaNegra);

			// PRUEBAS QUE DAN DAR ERRROR, MAL USO DEL CÓDIGO.

			// Pruebas Actor
			// Usuario con nombre a null
			Actor pruebamal = new Actor("10", null, "password", "agri@gmail.es", 1, "Calle Ribera", "juan",
					"Calle Goicoechea", "fg3");

			// Contraseña que excede VARCHAR(45)
			Actor contlargamal = new Actor("10", null, "passwordddddddddddddddddddddddddddddddddddddddddddd"
					+ "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" + "",
					"agri@gmail.es", 1, "Calle Ribera", "juan", "Calle Goicoechea", "fg3");
			insertarActor(pruebamal);
			insertarActor(contlargamal);
			extraerActor("10");
			insertarActor(null);

			// Prueba lotes
			// Sin fecha de inicio
			Lote lote1mal = new Lote(9, null, "pilsner", datefin1, false, true, false, true, false, null, 1);
			lote1.setQr(GeneradorQR2.generadorQR(lote1.getIdBd()));
			insertarLote(lote1);

			// Pruebas Productos
			Productos productos1mal = new Productos(1, 12, 5, 0, 4, 7, 8, 10, 11, 0, 1, 4);
			insertarProductos(productos1mal);
			insertarProductos(null);
			// Productos no existentes
			extraerProductos(6);

			// Prueba registros
			// Lote erróneo, no deberia existir en la bbdd
			Registro registro1mal = new Registro(lote1mal, emisor1, dateini1.toLocaleString(),
					datefin1.toLocaleString(), 40, 10);
			registro1.setId(1);
			insertarRegistro(registro1mal);

			// Pruebas OrdenTrazabilidad
			// Inserción de orden sin productos
			OrdenTrazabilidad orden1mal = new OrdenTrazabilidad(1, emisor1, emisor2, true, null, "", 1, firmarec,
					firmaent, 1, 2, emisor2, registro1);
			insertarOrdenTrazabilidad(orden1mal);

			// Pruebas cadena
			// Cadena con un solo atributo
			Cadena cadenaPrueba1mal = new Cadena(7);
			insertarCadena(cadenaPrueba1mal);
			// No debería existir
			extraerCadena(7);

			// Prueba bloques
			// Prueba con DatosContainer (registro1mal y lote1mal) erróneos
			DatosContainer datos1mal = registro1mal;
			DatosContainer datos2mal = lote1mal;

			Bloque bloque1mal = new Bloque("hashPrevio1", 1, 1, lote1.getIdBd(), datos1, 1);
			Bloque bloque2mal = new Bloque("hashPrevio2", 2, 2, lote2.getIdBd(), datos2, 2);

			insertarBloque(bloque1mal);
			insertarBloque(bloque2mal);

			// Prueba extraerPedidosActorDestino (Relacionado con OrdenTrazabilidad
			// Ordenes no existentes
			ArrayList<OrdenTrazabilidad> ord1mal = extraerPedidosActorDestino("200");
			ArrayList<OrdenTrazabilidad> ord2mal = extraerPedidosActorDestino("80");

			MateriaPrima arroz = new MateriaPrima("arroz", 80);

			// Prueba insertarStockMP, insertarStockLote
			// Inserción de una MP inexistente en la BBDD
			insertarStockMP(emisor1, arroz, 4);

			// Inserción sin actor (actor=null)
			insertarStockMP(null, cebadaTostada, 10);

			// Lote mal no está en la BBBDD
			insertarStockLote(emisor1, lote1mal);
			// Actor mal, no está en la BBBDD
			insertarStockLote(pruebamal, lote3);
			// Lote es null
			insertarStockLote(pruebamal, null);

			// Prueba extraerStockLote, extraerStockMP
			extraerStockMP(emisor2, arroz);
			// Intentamos extraer de un lote inexistente en la BBDD
			extraerStockLote(pruebamal);

			// SQL INJECTION
			// Intenta devolver todos los actores
			extraerActor("1 OR 1=1");
			// Intenta tirar abajo la tabla actor
			extraerActor("evrf; DROP TABLE actor");
			// Intenta extraer todos los bloques existentes en la BBDD
			extraerBloque("rbg OR 1=1");
			// Intenta tirar abajo la tabla bloque y la tabla productos
			extraerBloque("lkmn; DROP TABLE bloque; DROP TABLE productos");

			System.out.print("Pasados todos los test correctamente");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

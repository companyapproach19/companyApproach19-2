package equipo5;

import equipo5.dao.metodosCompany;
import equipo5.model.Agricultor;
import equipo5.model.Cadena;
import equipo5.model.Cooperativa;
import equipo5.model.Fabrica;
import equipo5.model.Retailer;
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

import java.util.*;

public class Test extends metodosCompany {

	public static void main(String[] args) throws Throwable {
		try {
			conectar();
			crearBD();
			
			// Pruebas Actor
			Actor prueba = new Actor("10", "Agricultor", "password", "agri@gmail.es", 1, "Calle Ribera", "juan",
					"Calle Goicocechea", "fg3");

			Actor emisor1 = new Actor("1", "alberto", "password", "alberto@gmail.es", 1, "Calle Ribera", "alberto",
					"Avenida Jarales", "fg3");
			Actor receptor1 = new Actor("2", "maria", "password", "maria@gmail.es", 3, "Calle Gonzalez", "maria",
					"Avenida Europa", "fg2");
			Actor emisor2 = new Actor("3", "felipe", "password", "felipe@gmail.es", 2, "Calle Sauces", "felipe",
					"Avenida Ilustración", "fg1");
			Actor receptor2 = new Actor("4", "rick", "password", "rick@gmail.es", 2, "Calle Gaseta", "rick",
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
			for(int i = 0; i<list.size();i++) {
				System.out.println(list.get(i).getNombreUsuario());
			}

			// Prueba lotes
			Date dateini1 = new Date(2, 12, 2015);
			Date datefin1 = new Date(1, 12, 2019);

			Date dateini2 = new Date(6, 11, 2018);
			Date datefin2 = new Date(8, 10, 2020);

			Date dateini3 = new Date(25, 02, 2018);
			Date datefin3 = new Date(5, 11, 2020);

			byte[] qr1 = null;
			byte[] qr2 = null;
			byte[] qr3 = null;

			Lote lote1 = new Lote(1, dateini1, datefin1, false, true, false, true, false, qr1);
			Lote lote2 = new Lote(2, dateini2, datefin2, true, false, true, false, false, qr2);
			Lote lote3 = new Lote(3, dateini3, datefin3, false, false, true, false, true, qr3);

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
			extraerOrdenTrazabalidad(1);

			OrdenTrazabilidad orden2 = new OrdenTrazabilidad(2, emisor2, emisor1, false, productosord2, "", 2, firmarec,
					firmaent, 1, 2, receptor2, registro2);
			insertarOrdenTrazabilidad(orden2);
			extraerOrdenTrazabalidad(2);

			// Pruebas cadena

			Cadena cadenaPrueba1 = new Cadena(1, "hashUltimoBloque1", 1);
			insertarCadena(cadenaPrueba1);

			Cadena cadenaPrueba2 = new Cadena(2, "hashUltimoBloque2", 3);
			insertarCadena(cadenaPrueba2);

			extraerCadena(1);
			extraerCadena(2);

			// Prueba bloques

			DatosContainer datos = null;
			Bloque bloque1 = new Bloque("hashPrevio1", 1, 1, lote1.getIdBd(), datos, 1);
			Bloque bloque2 = new Bloque("hashPrevio2", 2, 2, lote2.getIdBd(), datos, 2);

			insertarBloque(bloque1);
			insertarBloque(bloque2);

			extraerBloque("hashPrevio1");
			extraerBloque("hashPrevio2");
			
			//Prueba extraerPedidosActorDestino
			ArrayList<OrdenTrazabilidad> ord1 = extraerPedidosActorDestino("1");
			ArrayList<OrdenTrazabilidad> ord2 = extraerPedidosActorDestino("2");
			
			System.out.print("Prueba ord1 de extraerPedidosActorDestino");
			for(int i=0; i<ord1.size(); i++) {
				System.out.println(ord1.get(i).getId());
			}
			
			System.out.print("Prueba ord2 de extraerPedidosActorDestino");
			for(int i=0; i<ord2.size(); i++) {
				System.out.println(ord2.get(i).getId());
			}
			
			//Prueba extraerStockLote, extraerStockMP
			
			int stocklote1 = extraerStockLote(emisor1);
			int stocklote2 = extraerStockLote(emisor2);
			int stocklote3 = extraerStockLote(receptor1);
			
			int stockMp1 = extraerStockMP(emisor1);
			int stockMp2 = extraerStockMP(emisor2);
			int stockMp3 = extraerStockMP(receptor1);
			
			//Prueba insertarStockLote
			
			insertarStockLote(emisor1, lote1);
			insertarStockLote(emisor2, lote2);
			insertarStockLote(receptor1, lote3);
			
			

//             
//             //prueba almacen lotes
//            AlmacenLotes alm = new AlmacenLotes(0, 1, lotes);
//            insertarAlmacenLotes(alm);
//            getAlmacenLotes(1).getLista();
//           
//            //prueba fabrica
//            Fabrica fabrica = new Fabrica("lkmn", "avda madrid", 2, 1);
//            insertarFabrica(fabrica);
//            getFabrica("lkmn");
//            
//          //prueba transportista
//            Transportista a = new Transportista("juan", "apple", "12/11", "13/11");
//            a.aceptarPedido();
//            a.setFirmadoEntrega(true);
//            a.setFirmadoRecogida(true);
//            insertarTransportista(a);
//            Transportista b = getTransportista("juan");
//            Transportista c = getTransportista("pedro");
//            
//             
//            //Prueba Agricultor
//            Agricultor agricultor = new Agricultor("evrf", "Juan", "calle Espa�a", 76);
//            insercionAgricultor(agricultor);
//            getAgricultor(agricultor.getCif());
//            
//            
//            //Prueba Cooperativa
//            Cooperativa cooperativa = new Cooperativa("rbg", "Cooperativa1", "Avenida de la Hispanidad");
//            insercionCooperativa(cooperativa);
//            getCooperativa(cooperativa.getCif());
//            
//            Sensor a1 = new Sensor();
//            a1.setID(1);
//            a1.setAnio("2019");
//            a1.setMes("mayo");
//            a1.setDia("12");
//            a1.setHora("08");
//            a1.setMin("21");
//            a1.setSec("47");
//            a1.setTemperatura("25");
//            insertarSensor(a1);
//            Sensor b1 = getSensor(1);
//           // System.out.println(a1.equals(b1));
//            Sensor c1 = getSensor(4);
//            //System.out.println(c1 == null);
//             
//             //Prueba Productos
//             Productos prod = new Productos (1, 12, 5, 0, 4, 7, 8,10, 11, 0, 1, 4);
//             insertarProductos(prod);
//             getProductos(1);
//             //devuelve cantidad de malta palida que es 12
//            // System.out.print(getProductos(1).getCant_malta_palida());
//             
//             //Prueba Actor
//             Actor pri = new Actor(0,"pri", "***", "correo@aux.es", 0);
//             pri.setPasswordPlana("cont");
//             pri.setId(0);
//             Actor em = new Actor(1, "lperez", "**********","lperez@gmail.com", 0);
//             em.setPasswordPlana("holahola");
//             Actor re = new Actor(2, "mlopez", "**********", "mlopez@yahoo.es", 1);
//             re.setPasswordPlana("holar");
//             insercionActor(pri);
//             insercionActor(em);
//             insercionActor(re);
//             getActor("lperez");
//             
//             //pruebas CadenaActores
//             CadenaActores cadena;
//             cadena =  getCadenaActores();
//             
//             //prueba orden
//             OrdenTrazabilidad orden = new OrdenTrazabilidad(1,"que llegue pronto por favor", em, re, prod);
//             insertarOrdenTrazabilidad(orden);
//             extraerOrdenTrazabalidad(1);
//
//             
//             
//             //prueba almacen lotes
//            AlmacenLotes alm = new AlmacenLotes(0, 1, lotes);
//            insertarAlmacenLotes(alm);
//            extraerAlmacenLotes(1);
//            
//            
//            
//            //Almacen lotes sin lotes asignados, caso de error
//            AlmacenLotes alm = new AlmacenLotes(0, 1, lotes);
//            insertarAlmacenLotes(alm);
//            extraerAlmacenLotes(1).getLista();
//           
//            //prueba fabrica
//            Fabrica fabrica = new Fabrica("lkmn", "avda madrid", 2, 1);
//            insertarFabrica(fabrica);
//            
//            //caso error
//              Fabrica fabricaerror = new Fabrica("error", "avda error", 2, 1);
//            fabricaerror.setCIF(null);
//             insertarFabrica(fabricaerror);
//             getFabrica("error");
//                         
//            //prueba fabrica inexistente, error
//            getFabrica("noexiste") ;
//            
//          //prueba transportista
//            Transportista a = new Transportista("juan", "apple", "12/11", "13/11");
//            a.aceptarPedido();
//            a.setFirmadoEntrega(true);
//            a.setFirmadoRecogida(true);
//            insertarTransportista(a);
//         	extraerTransportista("juan");
//         	
//         	//caso error transportista inexistente
//            extraerTransportista("pedro");
//             
//            //Prueba Agricultor
//            Agricultor agricultor = new Agricultor("evrf", "Juan", "calle España", 76);
//            insercionAgricultor(agricultor);
//            getAgricultor(agricultor.getCif());
//            
//            
//            //Prueba Cooperativa
//            Cooperativa cooperativa = new Cooperativa("rbg", "Cooperativa1", "Avenida de la Hispanidad");
//            insercionCooperativa(cooperativa);
//            getCooperativa(cooperativa.getCif());
//            
//            
//            //Prueba Sensor
//            Sensor a1 = new Sensor();
//            a1.setID(1);
//            a1.setAnio("2019");
//            a1.setMes("mayo");
//            a1.setDia("12");
//            a1.setHora("08");
//            a1.setMin("21");
//            a1.setSec("47");
//            a1.setTemperatura("25");
//            insertarSensor(a1);
//            
//            
//            //caso error, insertar sensor ya existente
//            Sensor b1 = extraerSensor(1);
//           	insertarSensor(b1);
//           
//           //sensor inexistente en la BBDD
//            Sensor c1 = getSensor(4);
//           
//          
//             
//             //Prueba Productos
//             Productos prod = new Productos (1, 12, 5, 0, 4, 7, 8,10, 11, 0, 1, 4);
//             insertarProductos(prod);
//             extraerProductos(1);
//            
//             //Prueba Actor
//             Actor pri = new Actor(0,"pri", "***", "correo@aux.es", 0);
//             pri.setPasswordPlana("cont");
//             pri.setId(0);
//             Actor em = new Actor(1, "lperez", "**********","lperez@gmail.com", 0);
//             em.setPasswordPlana("holahola");
//             Actor re = new Actor(2, "mlopez", "**********", "mlopez@yahoo.es", 1);
//             re.setPasswordPlana("holar");
//             insercionActor(pri);
//             insercionActor(em);
//             re.setPasswordPlana(null);
//             insercionActor(re);
//             extraerActor("lperez");
//             
//             Actor em = new Actor("Agricultor", "password", "luis@gmail.com", 0);
//             em.setPasswordPlana("password");
//             em.setId(0);
//           	insercionActor(em);
//      		extraerActor("Agricultor");
//         
//             //prueba orden
//             OrdenTrazabilidad orden = new OrdenTrazabilidad(1,"que llegue pronto por favor", em, re,
//						 a, prod);
//             insertarOrdenTrazabilidad(orden);
//             extraerOrdenTrazabalidad(1);
//             //getOrdenTrazabilidad(8); Caso error (que no esté en la tabla)
//             
//             //prueba bloque --> NO FUNCIONA DA ERROR EL GETHASHCODE
//             Bloque bloq = new Bloque("pasGD312463", 1, 2, 1, (DatosContainer) orden);
//             insertarBloque(bloq);
//             extraerBloque(bloq.getHashCode());
//             
//             
//             Cadena cadenaPrueba = new Cadena(5);
//             insertarCadena(cadenaPrueba);
//                 System.out.println(extraerCadena(5).getCodLote());
//                 System.out.println(extraerCadena(5).getNumBloques());
//             Cadena cadenaPrueba2 = new Cadena(5, "hola", 3);
//             insertarCadena(cadenaPrueba2);
//                 System.out.println(extraerCadena(5).getCodLote());
//                 System.out.println(extraerCadena(5).getNumBloques());
//             Cadena cadenaPrueba3 = new Cadena(6);
//             insertarCadena(cadenaPrueba3);
//             System.out.println(extraerCadena(6).getCodLote());
//             
//             //SQL INJECTION
//            extraerTransportista("pedro OR 1=1");
//            extraerAgricultor("evrf; DROP TABLE Agricultor");
//            extraerCooperativa("rbg OR 1=1");
//            extraerFabrica("lkmn; DROP TABLE Actor; DROP TABLE Productos"); 
//            
//            
//            
//            
//             System.out.print("Pasados todos los test correctamente");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void insertarAlMMPP(AlmacenMMPP almacen1) {
		// TODO Auto-generated method stub

	}

}

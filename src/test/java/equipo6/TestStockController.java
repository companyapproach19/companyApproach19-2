package equipo6;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.controller.StockController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import equipo4.model.Lote;
import equipo4.model.MateriaPrima;
import equipo4.model.Pilsner;
import equipo5.dao.NotInDatabaseException;
import equipo5.dao.metodosCompany;
import equipo6.model.Actor;
import equipo6.model.CadenaActores;
import equipo7.model.OrdenTrazabilidad;

class TestStockController {
/*
	// test hecho anadiendo a mano maltaCrystal en la bbdd con el id pertinente y
	// comentando en el constructor de materia prima esta linea:
	// this.id=metodosCompany.idMateriaPrima();
	@Test
	// test de getCantidadStock cuando el actor no tiene MP
	void test() throws ClassNotFoundException, SQLException, NotInDatabaseException {
		Actor p = metodosCompany.extraerActor("9");
		MateriaPrima mp = new MateriaPrima("maltaCrystal");
		System.out.println(p);
		double result = StockController.getCantidadStock(p, mp);
		System.out.println(result);
	}

	@Test
	// test de getCantidadStock cuando el actor tiene MP, esta comentado el codigo para no petar la bbdd
	void test1() throws ClassNotFoundException, SQLException, NotInDatabaseException {
		Actor p = metodosCompany.extraerActor("5");
		MateriaPrima mp = new MateriaPrima("maltaCrystal");
		double result = StockController.getCantidadStock(p, mp);
		System.out.println(result);
	}

	@Test
	//test de setCantidadMateriaPrima cuando el actor no tiene MP
	void test2() throws ClassNotFoundException, SQLException, NotInDatabaseException {

		Actor p=metodosCompany.extraerActor("9");
		MateriaPrima mp= new MateriaPrima("maltaCrystal");
		System.out.println(p.getId());
		StockController.setCantidadMateriaPrima(p,mp, 300);
		System.out.println(StockController.getCantidadStock(p, mp));


	}
	@Test
	//test de setCantidadMateriaPrima cuando el actor tiene MP, esta comentado el codigo para no petar la bbdd
	void test3() throws ClassNotFoundException, SQLException, NotInDatabaseException {

		Actor p=metodosCompany.extraerActor("5");
		MateriaPrima mp= new MateriaPrima("maltaCrystal");

		StockController.setCantidadMateriaPrima(p,mp, 300);
		System.out.println(StockController.getCantidadStock(p, mp));


	}


	//=======================================


	//estos test han pasado hardcoreando Pilsner y cambiando sentencias que estaban mal
	//en bbdd


	@Test
	// test de getStockLote para cuando el actor no tiene Lotes
	void test4() throws ClassNotFoundException, SQLException, NotInDatabaseException {
		Actor actor = metodosCompany.extraerActor("5");
		Pilsner p = new Pilsner();
		Lote lote = new Lote(p, new java.util.Date());
		int cantidad = StockController.getStockLote(actor, lote);
		System.out.println("cuando el actor no tiene lotes "+cantidad);

	}



	@Test
	// test de getStockLote para cuando el actor si tiene Lotes
	void test5() throws ClassNotFoundException, SQLException, NotInDatabaseException {
		Actor actor = metodosCompany.extraerActor("9");
		Pilsner p = new Pilsner();
		Lote lote = new Lote(p, new java.util.Date());
		int cantidad = StockController.getStockLote(actor, lote);
		System.out.println("cuando el actor tiene lotes "+cantidad);

	}

	@Test
	// test de setCantidadLote para cuando el actor no tiene Lotes
	void test6() throws ClassNotFoundException, SQLException, NotInDatabaseException {
		Actor actor = metodosCompany.extraerActor("5");
		Pilsner p = new Pilsner();
		Lote lote = new Lote(p, new java.util.Date());
		try {
			int cantidad = StockController.getStockLote(actor, lote);
			System.out.println("cuando el actor no tiene lotes cantidad actual "+cantidad);
			StockController.setCantidadLote(actor, lote);
			System.out.println("cuando el actor no tiene lotes cantidad despues de anadir "+cantidad);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	


	@Test
	// test de setCantidadLote para cuando el actor si tiene Lotes, no funciona 
	//el metodo insertarLote de metodosCompany por las fechas, hay que crear una nueva instancia
	//no vale con un casting
	void test7() throws ClassNotFoundException, SQLException, NotInDatabaseException {
		Actor actor = metodosCompany.extraerActor("9");
		Pilsner p = new Pilsner();
		Lote lote = new Lote(p, new java.util.Date());
		try {
			int cantidad = StockController.getStockLote(actor, lote);
			System.out.println("cuando el actor tiene lotes cantidad actual "+cantidad);
			StockController.setCantidadLote(actor, lote);
			cantidad = StockController.getStockLote(actor, lote);
			System.out.println("cuando el actor tiene lotes cantidad despues de anadir "+cantidad);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	

	@Test
	//este test da error pero en la base de datos se modifica bien
	//test de setCantidadMateriaPrima cuando el actor tiene MP, esta comentado el codigo para no petar la bbdd
	void test8() throws ClassNotFoundException, SQLException, NotInDatabaseException {

		Actor p=metodosCompany.extraerActor("8");
		MateriaPrima mp= new MateriaPrima("maltaCrystal");
		StockController.setCantidadMateriaPrima(p,mp, 1739130434);
		System.out.println(StockController.getCantidadStock(p, mp));
	}

	@Test
	void testDameTransportista() throws SQLException, JsonSyntaxException, ClassNotFoundException 
	{


			CadenaActores cadena;
			JsonObject json_resp;
			Gson gson;
			int index;
			JsonParser parse;


			index = 0;
			json_resp = new JsonObject();
			cadena = metodosCompany.extraerCadenaActores();
			gson = new Gson();
			parse = new JsonParser();

			for(Actor actor : cadena.getlista_actores()) 
			{
				for(OrdenTrazabilidad or : metodosCompany.extraerPedidosActorDestino(actor.getId()))
				{
					if(or.getNecesitaTransportista()) 
					{
						json_resp.add(or.getId()+"", parse.parse(gson.toJson(or)).getAsJsonObject());
						index++;
					}
				}
			}

			System.out.println(json_resp.toString());


	}
*/
}

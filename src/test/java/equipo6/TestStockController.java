package equipo6;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.controller.StockController;

import equipo4.model.Lote;
import equipo4.model.MateriaPrima;
import equipo4.model.Pilsner;
import equipo5.dao.NotInDatabaseException;
import equipo5.dao.metodosCompany;
import equipo6.model.Actor;

class TestStockController {

	// test hecho anadiendo a mano maltaCrystal en la bbdd con el id pertinente y
	// comentando en el constructor de materia prima esta linea:
	// this.id=metodosCompany.idMateriaPrima();
	/*
	@Test
	// test de getCantidadStock cuando el actor no tiene MP
	void test() throws ClassNotFoundException, SQLException, NotInDatabaseException {
		Actor p = metodosCompany.extraerActor("Retailer");
		MateriaPrima mp = new MateriaPrima("maltaCrystal");
		double result = StockController.getCantidadStock(p, mp);
		System.out.println(result);
	}
	
	@Test
	// test de getCantidadStock cuando el actor tiene MP, esta comentado el codigo para no petar la bbdd
	void test1() throws ClassNotFoundException, SQLException, NotInDatabaseException {
		Actor p = metodosCompany.extraerActor("Productor");
		MateriaPrima mp = new MateriaPrima("maltaCrystal");
		// double result = StockController.getCantidadStock(p, mp);
		// System.out.println(result);
	}
	
	@Test
	//test de setCantidadMateriaPrima cuando el actor no tiene MP
	void test2() throws ClassNotFoundException, SQLException, NotInDatabaseException {

		Actor p=metodosCompany.extraerActor("Retailer");
		MateriaPrima mp= new MateriaPrima("maltaCrystal");
		
		StockController.setCantidadMateriaPrima(p,mp, 300);
		System.out.println(StockController.getCantidadStock(p, mp));

		
	}

	@Test
	//test de setCantidadMateriaPrima cuando el actor tiene MP, esta comentado el codigo para no petar la bbdd
	void test3() throws ClassNotFoundException, SQLException, NotInDatabaseException {

		Actor p=metodosCompany.extraerActor("Productor");
		MateriaPrima mp= new MateriaPrima("maltaCrystal");
		
	//	StockController.setCantidadMateriaPrima(p,mp, 300);
		System.out.println(StockController.getCantidadStock(p, mp));

		
	}
	*/
	
	
	//=======================================
	
	
	//estos test han pasado hardcoreando Pilsner y cambiando sentencias que estaban mal
	//en bbdd
	

	@Test
	// test de getStockLote para cuando el actor no tiene Lotes
	void test4() throws ClassNotFoundException, SQLException, NotInDatabaseException {
		Actor actor = metodosCompany.extraerActor("Productor");
		Pilsner p = new Pilsner();
		Lote lote = new Lote(p, new java.util.Date());
		int cantidad = StockController.getStockLote(actor, lote);
		System.out.println("cuando el actor no tiene lotes "+cantidad);

	}

	
	
	@Test
	// test de getStockLote para cuando el actor si tiene Lotes
	void test5() throws ClassNotFoundException, SQLException, NotInDatabaseException {
		Actor actor = metodosCompany.extraerActor("Retailer");
		Pilsner p = new Pilsner();
		Lote lote = new Lote(p, new java.util.Date());
		int cantidad = StockController.getStockLote(actor, lote);
		System.out.println("cuando el actor tiene lotes "+cantidad);

	}
	
	@Test
	// test de setCantidadLote para cuando el actor no tiene Lotes
	void test6() throws ClassNotFoundException, SQLException, NotInDatabaseException {
		Actor actor = metodosCompany.extraerActor("Productor");
		Pilsner p = new Pilsner();
		Lote lote = new Lote(p, new java.util.Date());
		try {
			int cantidad = StockController.getStockLote(actor, lote);
			System.out.println(cantidad);
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
		Actor actor = metodosCompany.extraerActor("Retailer");
		Pilsner p = new Pilsner();
		Lote lote = new Lote(p, new java.util.Date());
		try {
			int cantidad = StockController.getStockLote(actor, lote);
			System.out.println(cantidad);
			System.out.println("cuando el actor tiene lotes cantidad actual "+cantidad);
			StockController.setCantidadLote(actor, lote);
			System.out.println("cuando el actor tiene lotes cantidad despues de anadir "+cantidad);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	

}

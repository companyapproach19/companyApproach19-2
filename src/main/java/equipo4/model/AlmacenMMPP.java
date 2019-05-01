package equipo4.model;

import java.sql.SQLException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import equipo5.dao.NotInDatabaseException;

import java.util.ArrayList;
import java.util.LinkedList;

import com.controller.*;

import equipo5.dao.metodosCompany;
import equipo6.model.Actor;

public class AlmacenMMPP {
	private static int id;
	private static int capacidad;	
	private static Actor actor = new Actor(null,null,null,3);
	private static int pedido;

	public static void crearMateriasPrimas() throws SQLException{
	}
	
	
	public static MateriaPrima m10() throws SQLException{
		MateriaPrima m10 = metodosCompany.extraerMateriaPrima("lupuloTettnanger");
		return m10;
	}
	
	public static MateriaPrima m11() throws SQLException{
		MateriaPrima m11 = metodosCompany.extraerMateriaPrima("lupuloCentennial");
		return m11;
	}
	
	public static MateriaPrima m12() throws SQLException{
		MateriaPrima m12 = metodosCompany.extraerMateriaPrima("levaduraAle");
		return m12;
	}
	
	public static MateriaPrima m13() throws SQLException{
		MateriaPrima m13 = metodosCompany.extraerMateriaPrima("levaduraLager");
		return m13;
	}
	public static MateriaPrima m8() throws SQLException{
		MateriaPrima m8 = metodosCompany.extraerMateriaPrima("cebadaTostada");
		return m8;
	}
	
	public static MateriaPrima m9() throws SQLException{
		MateriaPrima m9 = metodosCompany.extraerMateriaPrima("lupuloPerle");
		return m9;
	}
	
	public static MateriaPrima m1() throws SQLException{
		MateriaPrima m1=metodosCompany.extraerMateriaPrima("maltaPilsner");
		return m1;
	}
	
	public static MateriaPrima m2() throws SQLException{
		MateriaPrima m2=metodosCompany.extraerMateriaPrima("maltaCaramelo");
		return m2;
	}
	public static MateriaPrima m3() throws SQLException{
		MateriaPrima m3 = metodosCompany.extraerMateriaPrima("maltaBasePalida");
		return m3;
	}
	
	public static MateriaPrima m4() throws SQLException{
		MateriaPrima m4 = metodosCompany.extraerMateriaPrima("maltaMunich");
		return m4;
	}
	
	public static MateriaPrima m5() throws SQLException{
		MateriaPrima m5 = metodosCompany.extraerMateriaPrima("maltaNegra");
		return m5;
	}
	
	public static MateriaPrima m6() throws SQLException{
		MateriaPrima m6 = metodosCompany.extraerMateriaPrima("maltaCrystal");
		return m6;
	}
	
	public static MateriaPrima m7() throws SQLException{
		MateriaPrima m7 = metodosCompany.extraerMateriaPrima("maltaChocolate");
		return m7;
	}
	
	public static double getLupuloCentennial() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return StockController.getCantidadStock(actor,m11());
	}

	public static void setLupuloCentennial(double c) throws ClassNotFoundException, SQLException {
		try {
			StockController.setCantidadMateriaPrima(actor,m11(),c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static double getLevaduraAle() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException  {
		return StockController.getCantidadStock(actor,m12());
	}

	public static void setLevaduraAle(double c) throws ClassNotFoundException, SQLException {
		try {
			StockController.setCantidadMateriaPrima(actor,m12(),c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static double getLevaduraLager() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return StockController.getCantidadStock(actor,m13());
	}

	public static void setLevaduraLager(double c) throws ClassNotFoundException, SQLException {
		try {
			StockController.setCantidadMateriaPrima(actor,m13(),c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static double getCebadaTostada() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return StockController.getCantidadStock(actor,m8());
	}

	public static void setCebadaTostada(double c) throws ClassNotFoundException, SQLException {
		try {
			StockController.setCantidadMateriaPrima(actor,m8(),c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static double getMaltaChocolate() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return StockController.getCantidadStock(actor,m7());
	}

	public static void setMaltaChocolate(double c) throws ClassNotFoundException, SQLException {
		StockController.setCantidadMateriaPrima(actor,m7(),c);
	}
	
	
	public static double getMaltaCrystal() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return StockController.getCantidadStock(actor,m6());
	}

	public static void setMaltaCrystal(double c) throws ClassNotFoundException, SQLException {
		try {
			StockController.setCantidadMateriaPrima(actor,m6(),c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static double getMaltaNegra() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return StockController.getCantidadStock(actor,m5());
	}

	public static void setMaltaNegra(double c) throws ClassNotFoundException, SQLException {
		try {
			StockController.setCantidadMateriaPrima(actor,m5(),c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static double getMaltaMunich() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return StockController.getCantidadStock(actor,m4());
	}

	public static void setMaltaMunich(double c) throws ClassNotFoundException, SQLException {
		try {
			StockController.setCantidadMateriaPrima(actor,m4(),c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static double getMaltaBasePalida() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return StockController.getCantidadStock(actor,m3());
	}

	public static void setMaltaBasePalida(double c) throws ClassNotFoundException, SQLException {
		try {
			StockController.setCantidadMateriaPrima(actor,m3(),c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static double getMaltaPilsner() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return StockController.getCantidadStock(actor,m1());
	}

	public static void setMaltaPilsner(double c) throws ClassNotFoundException, SQLException {
		try {
			StockController.setCantidadMateriaPrima(actor,m1(),c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static double getMaltaCaramelo() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return StockController.getCantidadStock(actor,m2());
	}

	public static void setMaltaCaramelo(double c) throws ClassNotFoundException, SQLException {
		try {
			StockController.setCantidadMateriaPrima(actor,m2(),c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static double getLupuloPerle() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return StockController.getCantidadStock(actor,m9());
	}

	public static void setLupuloPerle(double c) throws ClassNotFoundException, SQLException {
		try {
			StockController.setCantidadMateriaPrima(actor,m9(),c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static double getLupuloTettnanger() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return StockController.getCantidadStock(actor,m10());
	}

	public static void setLupuloTettnanger(double c) throws ClassNotFoundException, SQLException {
		try {
			StockController.setCantidadMateriaPrima(actor,m10(),c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static double getLevaduraLager1() throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return StockController.getCantidadStock(actor,m13());
	}

	public static void setLevaduraLager1(double c) throws ClassNotFoundException, SQLException {
		try {
			StockController.setCantidadMateriaPrima(actor,m13(),c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static  int maxcapacidad;
	public static  double maxmaltaPilsner;
	public static  double maxmaltaCaramelo;
	public static  double maxmaltaBasePalida;
	public static  double maxmaltaMunich;
	public static  double maxmaltaNegra;
	public static  double maxmaltaCrystal;
	public static  double maxmaltaChocolate;
	public static  double maxcebadaTostada;
	public static  double maxlupuloPerle;
	public static  double maxlupuloTettnanger;
	public static  double maxlupuloCentennial;
	public static  double maxlevaduraAle;
	public static  double maxlevaduraLager;


	public AlmacenMMPP() throws SQLException {
		maxcapacidad =  3714286 * 1000; //gr
		maxmaltaCaramelo = 217391304.3478 * 5;
		maxmaltaBasePalida = 2621426571.428571 * 5;
		maxmaltaCrystal = 6666666.6666667 * 5;
		maxmaltaMunich = 619047619.047619 * 5;
		maxcebadaTostada = 214285714.2857143 * 5;
		maxmaltaNegra = 107142857.1428571 * 5;
		maxmaltaChocolate = 57142857.14285714 * 5;
		maxlupuloPerle = 9523809.523809524 * 5;
		maxlupuloCentennial = 33333333.33333333 * 5;
		maxmaltaPilsner= 1739130434.7826 * 5;
		maxlupuloTettnanger=2621426571.428571 * 5;
		maxlevaduraAle= 16541665;
		maxlevaduraLager=687464;
		
		m2().setCantidad(maxmaltaCaramelo);
		m3().setCantidad(maxmaltaBasePalida);
		m4().setCantidad(maxmaltaMunich);
		m5().setCantidad(maxmaltaNegra);
		m6().setCantidad(maxmaltaCrystal);
		m7().setCantidad(maxmaltaChocolate);
		m8().setCantidad(maxcebadaTostada);
		m9().setCantidad(maxlupuloPerle);
		m1().setCantidad(maxmaltaPilsner);
		m11().setCantidad(maxlupuloCentennial);
		m10().setCantidad(maxlupuloTettnanger);
		m12().setCantidad(maxlevaduraAle);
		m13().setCantidad(maxlevaduraLager);



	}

	public static int getId() {
		return id;
	}
	

}
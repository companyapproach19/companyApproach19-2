package equipo4.model;

import java.io.Serializable;
import java.sql.SQLException;

import equipo5.dao.NotInDatabaseException;

public class Stout implements Serializable {
	private static double maltaCaramelo;
	private static double maltaBasePalida;
	private static double maltaMunich;
	private static double maltaNegra;
	private static double maltaCrystal;
	private static double agua;
	private static double maltaChocolate;
	private static double cebadaTostada;
	private static double lupuloCentennial;
	private static double levaduraAle;
	private static int id;
	private static boolean molido;
	private static boolean cocinado;
	private static boolean fermentacion1;
	private static boolean fermentacion2;
	private static boolean embotellado;

	public Stout() throws ClassNotFoundException, SQLException, NotInDatabaseException {
		agua = 11428571.4286;
		maltaBasePalida = 2619047619.05;
		AlmacenMMPP.setMaltaBasePalida(AlmacenMMPP.getMaltaBasePalida() - 2619047619.05);
		maltaMunich = 619047619.048;
		AlmacenMMPP.setMaltaMunich(AlmacenMMPP.getMaltaMunich() - 619047619.048);
		cebadaTostada = 214285714.286;
		AlmacenMMPP.setCebadaTostada(AlmacenMMPP.getCebadaTostada() - 214285714.286);
		maltaNegra = 107142857.143;
		AlmacenMMPP.setMaltaNegra(AlmacenMMPP.getMaltaNegra() - 107142857.143);
		maltaCrystal = 66666666.6667;
		AlmacenMMPP.setMaltaCrystal(AlmacenMMPP.getMaltaCrystal() - 66666666.6667);
		maltaChocolate = 57142857.1429;
		AlmacenMMPP.setMaltaChocolate(AlmacenMMPP.getMaltaChocolate() - 57142857.1429);
		maltaCaramelo = 42857142.8571;
		AlmacenMMPP.setMaltaCaramelo(AlmacenMMPP.getMaltaCaramelo() - 42857142.8571);
		lupuloCentennial = 33333333.3333;
		AlmacenMMPP.setLupuloCentennial(AlmacenMMPP.getLupuloCentennial() - 33333333.3333);
		levaduraAle=287.5;
		AlmacenMMPP.setLevaduraAle(AlmacenMMPP.getLevaduraAle()-287.5);
	}


	public static double getMaltaCaramelo() {
		return maltaCaramelo;
	}

	public static void setMaltaCaramelo(double maltaCaramelo) {
		Stout.maltaCaramelo = maltaCaramelo;
	}

	public static double getMaltaBasePalida() {
		return maltaBasePalida;
	}

	public static void setMaltaBasePalida(double maltaBasePalida) {
		Stout.maltaBasePalida = maltaBasePalida;
	}

	public static double getMaltaMunich() {
		return maltaMunich;
	}

	public static void setMaltaMunich(double maltaMunich) {
		Stout.maltaMunich = maltaMunich;
	}

	public static double getMaltaNegra() {
		return maltaNegra;
	}

	public static void setMaltaNegra(double maltaNegra) {
		Stout.maltaNegra = maltaNegra;
	}

	public static double getMaltaCrystal() {
		return maltaCrystal;
	}

	public static void setMaltaCrystal(double maltaCrystal) {
		Stout.maltaCrystal = maltaCrystal;
	}

	public static double getAgua() {
		return agua;
	}

	public static void setAgua(double agua) {
		Stout.agua = agua;
	}

	public static double getMaltaChocolate() {
		return maltaChocolate;
	}

	public static void setMaltaChocolate(double maltaChocolate) {
		Stout.maltaChocolate = maltaChocolate;
	}

	public static double getCebadaTostada() {
		return cebadaTostada;
	}

	public static void setCebadaTostada(double cebadaTostada) {
		Stout.cebadaTostada = cebadaTostada;
	}

	public static double getLupuloCentennial() {
		return lupuloCentennial;
	}

	public static void setLupuloCentennial(double lupuloCentennial) {
		Stout.lupuloCentennial = lupuloCentennial;
	}

	public static double getLevaduraAle() {
		return levaduraAle;
	}

	public static void setLevaduraAle(double levaduraAle) {
		Stout.levaduraAle = levaduraAle;
	}

	public static int getId() {
		return id;
	}

}
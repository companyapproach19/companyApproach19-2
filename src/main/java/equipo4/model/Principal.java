package equipo4.model;

import java.util.Scanner;
import java.util.Date;
import java.sql.SQLException;
import java.util.*;

import equipo5.dao.metodosCompany;
import equipo5.model.NotInDatabaseException;
import equipo6.model.DatosContainer;

public class Principal extends DatosContainer{

	private static Date fechaActual = new Date();
	private static double densidad;

	@SuppressWarnings("deprecation")
	public static void moler(Lote lote) throws InterruptedException {
		lote.setFecha_inicio(fechaActual);
		System.out
				.println("Dia: " + fechaActual.getDate() + "/" + fechaActual.getMonth() + "/" + fechaActual.getYear());
		System.out.println("Moliendo...");
		Thread.sleep(3000);
		fechaActual.setDate(fechaActual.getDate() + 1);
		System.out.println("Proceso de molienda finalizado. Dia: " + fechaActual.getDate() + "/"
				+ fechaActual.getMonth() + "/" + fechaActual.getYear());
		lote.setMolido(true);
	}

	@SuppressWarnings("deprecation")
	public static void cocinar(Lote lote) throws InterruptedException {
		System.out
				.println("Dia: " + fechaActual.getDate() + "/" + fechaActual.getMonth() + "/" + fechaActual.getYear());
		if (lote.isMolido()) {
			System.out.println("Cociendo...");
			Thread.sleep(3000);
			fechaActual.setDate(fechaActual.getDate() + 1);
			System.out.println("Proceso de coccion finalizado. Dia: " + fechaActual.getDate() + "/"
					+ fechaActual.getMonth() + "/" + fechaActual.getYear());
			lote.setCocido(true);
		}
	}

	@SuppressWarnings("deprecation")
	public static void fermentar(Lote lote) throws InterruptedException {
		System.out
				.println("Dia: " + fechaActual.getDate() + "/" + fechaActual.getMonth() + "/" + fechaActual.getYear());
		if (lote.isCocido()) {
			System.out.println("Fermentando...");
			Thread.sleep(3000);
			fechaActual.setDate(fechaActual.getDate() + 7);
			densidad = 1.045 - (Math.random() * 0.5);
			System.out.println("Proceso de fermentacion finalizado. Dia: " + fechaActual.getDate() + "/"
					+ fechaActual.getMonth() + "/" + fechaActual.getYear());
			if (densidad > 1.010) {
				System.out.println("Las pruebas de densidad indican una densidad de " + densidad
						+ ", por lo que es necesario realizar una segunda fermentacion.");
				fermentar2(lote);
			}
			lote.setFermentado(true);
		}
	}

	@SuppressWarnings("deprecation")
	public static void fermentar2(Lote lote) throws InterruptedException {
		System.out
				.println("Dia: " + fechaActual.getDate() + "/" + fechaActual.getMonth() + "/" + fechaActual.getYear());
		if (lote.isFermentado()) {
			System.out.println("Fermentando otra vez...");
			Thread.sleep(3000);
			fechaActual.setDate(fechaActual.getDate() + 15);
			densidad -= (Math.random() * 0.1);
			System.out.println("Proceso de segunda fermentacion finalizado. Dia: " + fechaActual.getDate() + "/"
					+ fechaActual.getMonth() + "/" + fechaActual.getYear());
			lote.setFermentado2(true);
		}
	}

	@SuppressWarnings("deprecation")
	public static void embotellar(Lote lote) throws InterruptedException {
		System.out
				.println("Dia: " + fechaActual.getDate() + "/" + fechaActual.getMonth() + "/" + fechaActual.getYear());
		if (lote.isFermentado()) {
			System.out.println("Embotellando...");
			Thread.sleep(3000);
			fechaActual.setDate(fechaActual.getDate() + 2);
			System.out.println("Proceso de embotellado finalizado. Dia: " + fechaActual.getDate() + "/"
					+ fechaActual.getMonth() + "/" + fechaActual.getYear());
			lote.setEmbotellado(true);
			AlmacenLotes.almacenarLote(lote);
			//guardarOrden(lote);
			//lote.setQr(GeneradorQR2.generadorQR(lote.getIdBd()));
			lote.setFecha_final(fechaActual);
		}
	}

	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException, NotInDatabaseException {
		System.out.println("¿Desea generar un lote? (s/n)");
		Scanner sc = new Scanner(System.in);
		String answ = sc.nextLine();
		switch(answ.toLowerCase()) {
		case "s":
			if(AlmacenLotes.lista.size()-AlmacenLotes.getMaxCapacidad()==0) {
				System.err.println("Alerta: El almacen de lotes esta completo. Recuerde que no podra almacenar el lote "+
			"que va a producir si no vacia el almacen");
			}
			else {
				Pilsner a; 
				System.out.println("¿Desea generar un pedido de tipo Pilsner? (s/n)" );
				String pilsner = sc.nextLine();
				Lote lote = null;
				
				switch(pilsner.toLowerCase()) {
				case "s":
					a = new Pilsner();
					lote = new Lote( a, fechaActual);
					AlmacenLotes.almacenarLote(lote);
					System.out.println("Comienza la fase de molienda.");
					moler(lote);
					System.out.println("Comienza la fase de cocinado.");
					cocinar(lote);
					System.out.println("Comienza la fase de fermentacion.");
					fermentar(lote);
					System.out.println("Comienza la fase de embotellado.");
					embotellar(lote);
					break;
				case "n":
					break;
					}
		
		Stout b;	
		System.out.println("¿Desea generar un pedido de tipo Stout? (s/n)" );
		String stout = sc.nextLine();
		Lote lote2 = null;
		switch(pilsner.toLowerCase()) {
		case "s":
			a = new Pilsner();
			lote2 = new Lote( a, fechaActual);
			AlmacenLotes.almacenarLote(lote);
			System.out.println("Comienza la fase de molienda.");
			moler(lote);
			System.out.println("Comienza la fase de cocinado.");
			cocinar(lote);
			System.out.println("Comienza la fase de fermentacion.");
			fermentar(lote);
			System.out.println("Comienza la fase de embotellado.");
			embotellar(lote);
			break;
		case "n":
			break;
		}
		
		Scanner sca = new Scanner(System.in);
		System.out.println("¿Desea consultar informacion sobre algun lote? (s/n)");
		String ans = sca.nextLine();
		switch(ans.toLowerCase()) {
		case "s":
			Scanner sc2 = new Scanner(System.in);
			System.out.println("Inserte el lote sobre el que desea consultar: ");
			String lote3 = sc2.nextLine();
			int id = Integer.parseInt(lote3);
			boolean encontrado=false;
			if(AlmacenLotes.getLista()!=null && AlmacenLotes.existeLoteId(id)) {
			for(int i=0;i<AlmacenLotes.getLista().size() && !encontrado;i++) {
			if (AlmacenLotes.getLista().get(i).getIdBd()-id==0) {
				System.out.println("En preparacion.");
				encontrado=true;
			}
			}
			}
			sc2.close();
			break;
		case "n":
			break;
		}
		sc.close();
	}
		}
	}}

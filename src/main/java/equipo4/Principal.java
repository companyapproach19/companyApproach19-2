package equipo4;

import java.util.Scanner;
import java.util.Date;

public class Principal {
	
	private static Date fechaActual = new Date();
	private static double densidad;

	@SuppressWarnings("deprecation")
	public static int moler(Lote lote) throws InterruptedException {
		System.out.println("Día: "+fechaActual.getDate()+"/"+fechaActual.getMonth()+"/"+fechaActual.getYear());
		System.out.println("Moliendo...");
		Thread.sleep(3000);
		fechaActual.setDate(fechaActual.getDate() + 1);
		System.out.println("Proceso de molienda finalizado. Día: " + fechaActual.getDate()+"/"+fechaActual.getMonth()+"/"+fechaActual.getYear());
		lote.setMolido(true);
		return 0;
	}
	
	@SuppressWarnings("deprecation")
	public static int cocinar(Lote lote) throws InterruptedException {
		System.out.println(
				"Día: " + fechaActual.getDate() + "/" + fechaActual.getMonth() + 
				"/" + fechaActual.getYear());
		if (lote.isMolido()) {
			System.out.println("Cociendo...");
			Thread.sleep(3000);
			fechaActual.setDate(fechaActual.getDate() + 1);
			System.out.println("Proceso de cocción finalizado. Día: " + fechaActual.getDate() + "/"
					+ fechaActual.getMonth() + "/" + fechaActual.getYear());
			lote.setCocido(true);
			return 0;
		} else {
			System.out.println("El lote no ha sido molido!");
			return 1;
		}
	}

	@SuppressWarnings("deprecation")
	public static int fermentar(Lote lote) throws InterruptedException {
		System.out.println("Día: "+fechaActual.getDate()+"/"+fechaActual.getMonth()+"/"+fechaActual.getYear());
		if (lote.isCocido()) {
			System.out.println("Fermentando...");
			Thread.sleep(3000);
			fechaActual.setDate(fechaActual.getDate() + 7);
			densidad = 1.045 - (Math.random() * 0.5);
			System.out.println("Proceso de fermentación finalizado. Día: " + fechaActual.getDate() + "/"
					+ fechaActual.getMonth() + "/" + fechaActual.getYear());
			if (densidad > 1.010) {
				System.out.println("Las pruebas de densidad indican una densidad de " + densidad
						+ ", por lo que es necesario realizar una segunda fermentación.");
				fermentar2(lote);
			}
			lote.setFermentado(true);
			return 0;
		} else {
			System.out.println("El lote no ha sido cocinado!");
			return 1;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static int fermentar2(Lote lote) throws InterruptedException {
		System.out.println("Día: "+fechaActual.getDate()+"/"+fechaActual.getMonth()+
				"/"+fechaActual.getYear());
		if (lote.isFermentado()) {
			System.out.println("Fermentando otra vez...");
			Thread.sleep(3000);
			fechaActual.setDate(fechaActual.getDate() + 15);
			densidad -= (Math.random() * 0.1);
			System.out.println("Proceso de segunda fermentación finalizado. Día: " + fechaActual.getDate() + "/"
					+ fechaActual.getMonth() + "/" + fechaActual.getYear());
			lote.setFermentado2(true);
			return 0;
		} else {
			System.out.println("El lote no ha sido fermentado!");
			return 1;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static int embotellar(Lote lote) throws InterruptedException {
		System.out.println("Día: "+fechaActual.getDate()+"/"+fechaActual.getMonth()+"/"+fechaActual.getYear());
		if (lote.isFermentado()) {
			System.out.println("Embotellando...");
			Thread.sleep(3000);
			fechaActual.setDate(fechaActual.getDate() + 2);
			System.out.println("Proceso de embotellado finalizado. Día: " + fechaActual.getDate() + "/"
					+ fechaActual.getMonth() + "/" + fechaActual.getYear());
			lote.setEmbotellado(true);
			AlmacenLotes.almacenarLote(lote);
			return 0;
		} else {
			System.out.println("El lote no ha sido fermentado!");
			return 1;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("¿Desea generar un lote? (s/n)");
		Scanner sc = new Scanner(System.in);
		String answ = sc.nextLine();
		switch(answ.toLowerCase()) {
		case "s":
			if(AlmacenLotes.lista.size()==AlmacenLotes.getMaxCapacidad()) {
				System.err.println("Alerta: El almacen de lotes esta completo. Recuerde que no podra almacenar el lote "+
			"que va a producir si no vacia el almacen");
			}
			else {
				Pilsner a; Stout b;
				System.out.println("¿Desea generar un pedido de tipo Pilsner? (s/n)" );
				String pilsner = sc.nextLine();
				Lote lote = null;
				switch(pilsner.toLowerCase()) {
				case "s":
					a = new Pilsner();
					lote = new Lote(0, a, fechaActual);
					AlmacenLotes.almacenarLote(lote);
					//System.out.println("Se está empezando a moler su lote de cerveza Pilsner.");
					break;
				case "n":
					System.out.println("¿Desea generar un pedido de tipo Stout? (s/n)");
					String stout = sc.nextLine();
					switch(stout.toLowerCase()) {
					case "s":
						b = new Stout();
						lote = new Lote(0, b, fechaActual);
						//System.out.println("Se está empezando a moler su lote de cerveza Stout.");
					case "n":
						break;
					default:
						System.err.println("Por favor, introduzca una 's' o una 'n'.");
						break;
					
					}
					break;
				default:
					System.err.println("Por favor, introduzca una 's' o una 'n'.");
					break;
				}
				
				try {
					if (lote != null) {
						System.out.println("Comienza la fase de molienda.");
						moler(lote);
						System.out.println("Comienza la fase de cocinado.");
						cocinar(lote);
						System.out.println("Comienza la fase de fermentación.");
						fermentar(lote);
						System.out.println("Comienza la fase de embotellado.");
						embotellar(lote);
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			break;
		case "n":
			Scanner sca = new Scanner(System.in);
			System.out.println("Inserte el lote sobre el que desea consultar: ");
			String lote = sca.nextLine();
			int id = Integer.parseInt(lote);
			if (id > AlmacenLotes.getId() ) {
				System.err.println("Lote introducido incorrecto.");
				break;
			} else {
				System.out.println("En preparación.");
			}
			break;
		default:
			System.err.println("Por favor, introduzca una 's' o una 'n'.");
			break;
		}
		sc.close();
		

	}

}

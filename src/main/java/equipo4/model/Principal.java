package equipo4.model;

//import com.controller.StockLote;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

<<<<<<< HEAD
import com.controller.StockController;

import equipo5.model.NotInDatabaseException;
import equipo5.model.StockLote;
=======
import equipo5.dao.NotInDatabaseException;
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e
import equipo5.dao.metodosCompany;
import equipo5.model.StockLote;
import equipo6.model.Actor;
import equipo6.model.DatosContainer;
import equipo8.model.GeneradorQR2;

public class Principal extends Thread {

	private static Date fechaActual = new Date(System.currentTimeMillis());
	private static double densidad;
	public static LinkedList<Lote> moliendo = new LinkedList<Lote>();
	public static LinkedList<Lote> cociendo = new LinkedList<Lote>();
	public static LinkedList<Lote> fermentando = new LinkedList<Lote>();
	public static LinkedList<Lote> embotellando = new LinkedList<Lote>();
	
	public static Lote crearLote(String tipo) throws Exception {
		Lote l = new Lote();
		l.setTipo(tipo);
		l.setFecha_inicio(fechaActual);
<<<<<<< HEAD
=======
		l.setIdBd(metodosCompany.idLote());
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e
		l.setQr(GeneradorQR2.generadorQR(l.getIdBd()));
		return l;
	}
	
<<<<<<< HEAD
	
	public static void actualizarLista(int orden) throws ClassNotFoundException, SQLException, NotInDatabaseException {
=======
	public static double calcularDensidad(){
		return Math.random()*(0.3)+1.1;
	}
	
	public static void actualizarLista() throws ClassNotFoundException, SQLException, equipo5.model.NotInDatabaseException {
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e
		for(int i=0;i<moliendo.size();i++) {
			moliendo.remove(i);
		}
		for(int j=0;j<cociendo.size();j++) {
<<<<<<< HEAD
			moliendo.remove(j);
		}
		for(int k=0;k<fermentando.size();k++) {
			moliendo.remove(k);
		}
		for(int l=0;l<embotellando.size();l++) {
			moliendo.remove(l);
		}
		HashMap<String,Double> lista = StockController.getStockPedidoFabrica(orden);
		/*for (int i=0; i<lista.size(); i++) {
			StockLote lote1 = lista.get(i);
				Lote lote2 = lote1.getLote();
				Date fechaInicial = lote2.getFecha_inicio();
				Date fechaActual = new Date();
=======
			cociendo.remove(j);
		}
		for(int k=0;k<fermentando.size();k++) {
			fermentando.remove(k);
		}
		for(int l=0;l<embotellando.size();l++) {
			embotellando.remove(l);
		}
        Actor actor = new Actor(null,null,null,3);
		LinkedList<StockLote> lista = com.controller.StockController.getListaLotes(actor);
		for (int i=0; i<lista.size(); i++) {
			StockLote lote1 = lista.get(i);
				Lote lote2 = lote1.getLote();
				Date fechaInicial = (Date) lote2.getFecha_inicio();
				Date fechaActual = new Date(System.currentTimeMillis());
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e
				int tiempo= fechaInicial.getMinutes()-fechaActual.getMinutes();
			
				if(tiempo>=13) {
					lote2.setMolido(true);
					lote2.setCocido(true);
					lote2.setFermentado(true);
					lote2.setEmbotellado(true);
				}
<<<<<<< HEAD
				else if(tiempo<13) {
=======
				else if(tiempo<13 && tiempo>=11) {
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e
					lote2.setMolido(true);
					lote2.setCocido(true);
					lote2.setFermentado(true);
					embotellando.add(lote2);
				}
<<<<<<< HEAD
				else if (tiempo<11) {
=======
				else if (tiempo<11 && tiempo>=4) {
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e
					lote2.setMolido(true);
					lote2.setCocido(true);
					fermentando.add(lote2);
				}
<<<<<<< HEAD
				else if (tiempo<4) {
=======
				else if (tiempo<4 && tiempo>=2) {
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e
					lote2.setMolido(true);
					cociendo.add(lote2);
				}
				else if(tiempo<2) {
					moliendo.add(lote2);
				}
<<<<<<< HEAD
		}*/
	}


=======
		}
	}


>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e
	public static Date getFechaActual() {
		return fechaActual;
	}


	public static void setFechaActual(Date fechaActual) {
		Principal.fechaActual = fechaActual;
	}


	public static double getDensidad() {
		return densidad;
	}


	public static void setDensidad(double densidad) {
		Principal.densidad = densidad;
	}


	public static LinkedList<Lote> getMoliendo() {
		return moliendo;
	}


	public static void setMoliendo(LinkedList<Lote> moliendo) {
		Principal.moliendo = moliendo;
	}


	public static LinkedList<Lote> getCociendo() {
		return cociendo;
	}


	public static void setCociendo(LinkedList<Lote> cociendo) {
		Principal.cociendo = cociendo;
	}


	public static LinkedList<Lote> getFermentando() {
		return fermentando;
	}


	public static void setFermentando(LinkedList<Lote> fermentando) {
		Principal.fermentando = fermentando;
	}


	public static LinkedList<Lote> getEmbotellando() {
		return embotellando;
	}


	public static void setEmbotellando(LinkedList<Lote> embotellando) {
		Principal.embotellando = embotellando;
	}
	
	}
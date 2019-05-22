package equipo4.model;

//import com.controller.StockLote;

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;

import equipo5.dao.NotInDatabaseException;
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
	public static double densidadAnterior=0;
	public static Lote crearLote(String tipo) throws Exception {
		Lote l = new Lote();
		l.setTipo(tipo);
		l.setFecha_inicio(fechaActual);
		l.setIdBd(metodosCompany.idLote());
		l.setQr(GeneradorQR2.generadorQR(l.getIdBd()));
		return l;
	}
	
	public static double calcularDensidad(){
		return Math.random()*(0.3)+1.1;
	}
	
	public static String comprobarFase(int idLote) throws ClassNotFoundException, SQLException, equipo5.model.NotInDatabaseException {
		String res="";
		actualizarLista();
		boolean encontrado=false;
		for(int i=0;i<moliendo.size() && !encontrado;i++) {
			if (moliendo.get(i).getIdBd()==idLote) {
				encontrado=true;
				res="Fase molienda.";
			}
		}
		for(int j=0;j<cociendo.size() && !encontrado;j++) {
			if (cociendo.get(j).getIdBd()==idLote) {
				encontrado=true;
				res="Fase cocinado.";
			}
		}
		for(int k=0;k<fermentando.size() && !encontrado;k++) {
			if (fermentando.get(k).getIdBd()==idLote) {
				encontrado=true;
				double d=calcularDensidad();
				if(d<densidadAnterior) d=densidadAnterior;
				res="Fase fermentaci�n. Densidad actual= "+d;
			}
		}
		for(int l=0;l<embotellando.size() && !encontrado;l++) {
			if (embotellando.get(l).getIdBd()==idLote) {
				encontrado=true;
				res="Fase embotellado. Se esta embotellando con densidad 1.4";
			}
		}
		return res;
	}
	
	public static void actualizarLista() throws ClassNotFoundException, SQLException, equipo5.model.NotInDatabaseException {
		for(int i=0;i<moliendo.size();i++) {
			moliendo.remove(i);
		}
		for(int j=0;j<cociendo.size();j++) {
			cociendo.remove(j);
		}
		for(int k=0;k<fermentando.size();k++) {
			fermentando.remove(k);
		}
		for(int l=0;l<embotellando.size();l++) {
			embotellando.remove(l);
		}
        Actor actor = new Actor("3",null,3);
		LinkedList<StockLote> lista = com.controller.StockController.getListaLotes(actor);
		for (int i=0; i<lista.size(); i++) {
			StockLote lote1 = lista.get(i);
				Lote lote2 = lote1.getLote();
				Date fechaInicial = lote2.getFecha_inicio();
				Date fechaActual = new Date(System.currentTimeMillis());
				double t= fechaActual.getTime()-fechaInicial.getTime();
				double tiempo = t/60000;
				if(tiempo>=1.1) {
					lote2.setMolido(true);
					lote2.setCocido(true);
					lote2.setFermentado(true);
					lote2.setEmbotellado(true);
				}
				else if(tiempo<1.1 && tiempo>=1) {
					lote2.setMolido(true);
					lote2.setCocido(true);
					lote2.setFermentado(true);
					embotellando.add(lote2);
				}
				else if (tiempo<1 && tiempo>=0.7) {
					lote2.setMolido(true);
					lote2.setCocido(true);
					fermentando.add(lote2);
				}
				else if (tiempo<0.7 && tiempo>=0.5) {
					lote2.setMolido(true);
					cociendo.add(lote2);
				}
				else if(tiempo<0.5) {
					moliendo.add(lote2);
				}
		}
	}


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

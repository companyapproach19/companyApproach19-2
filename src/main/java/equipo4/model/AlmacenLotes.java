package equipo4.model;

import java.util.*;

//se implementa una pila FIFO : first in first out 

public class AlmacenLotes{
	private static int idBd;
	private static final int maxCapacidad=7;
	public static LinkedList<Lote> lista  = new LinkedList<Lote>();
	private static int id;
	
	public 	AlmacenLotes(int idBd) {
		this.idBd=idBd;
		this.lista= new LinkedList<Lote>();
		id = 1;
	}
	
	/*
	 * Constructor auxiliar para que compile el código de la BBDD
	 */
	public AlmacenLotes(int idB, int int1, LinkedList<Lote> lista2) {
		this.idBd=idB;
		this.id=int1;
		this.lista=lista2;
	}
	public static int getId() {
		return id;
	}

	public static int getMaxCapacidad() {
		return maxCapacidad;
	}

	public static LinkedList<Lote> getLista() {
		return lista;
	}

	public static void setId(int id) {
		AlmacenLotes.id = id;
	}


	public static void almacenarLote(Lote name) {
		if (lista.size()<=6) {
			lista.addFirst(name);
		}
		else {
			System.err.println("error de capacidad en AlmacenLotes: no caben m�s lotes.");
		}

	}
	public static Lote sacarLote() {
		//devuelve el lote  almacenado hace m�s tiempo -- pila FIFO

		if( lista.size() !=0 ) { 
			return lista.pollLast();
		}
		else {
			System.err.println("No se puede sacar un lote, el almac�n de lotes est� vac�o o contiene un n�mero de lotes que"
					+ "excede la capacidad del almac�n.");
			return null;
		}
	}
	public static boolean existeLoteId(int id) {
		Iterator it = lista.iterator();
		Lote resultado = (Lote) it.next();
		while(it.hasNext()){
			if (resultado.getCode() == id) {
				return true;
			}
			else {
				it.next();
				continue;
			}
		}
		System.out.println("No se ha podido encontrar el lote con el id introducido.");
		return false;
	}

	public static Lote sacarLoteId(int id){
		Iterator it = lista.iterator();
		Lote resultado = (Lote) it.next();
		while(it.hasNext()){
			if (resultado.getCode() == id) {
				return resultado;
			}
			else {
				it.next();
			}
		}
		System.out.println("No se ha podido encontrar el lote con el id introducido.");
		return null;

	}

	public static int getIdBd() {
		return idBd;
	}

	public static void setIdBd(int idBd) {
		AlmacenLotes.idBd = idBd;
	}
}

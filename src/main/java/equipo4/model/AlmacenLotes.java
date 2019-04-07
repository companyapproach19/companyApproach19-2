package equipo4.model;

import java.util.*;

//se implementa una pila FIFO : first in first out 

public class AlmacenLotes {
	private static int idBd;
	private static final int maxCapacidad = 7;
	static LinkedList<Lote> lista = new LinkedList<Lote>();
	private static int id = 1;

	public AlmacenLotes(int idBd, LinkedList<Lote> lista) {
		this.idBd = idBd;
		this.lista = lista;
	}

	public static LinkedList<Lote> getLista() {
		return lista;
	}

	public static int getMaxCapacidad() {
		return maxCapacidad;
	}

	public static void almacenarLote(Lote name) {
		if (lista.size() <= 6) {
			lista.addLast(name);
			name.setIdBd(id);
			id++;
	}}

	public static boolean existeLoteId(int id) {
		boolean existe = false;
		for (int i = 0; i < lista.size() && !existe; i++) {
			if (lista.get(i).getIdBd() - id == 0) {
				existe = true;
			}
		}
		return existe;
	}

	public static Lote sacarLoteId(int id) {
		Lote l = null;
		if (existeLoteId(id)) {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getIdBd() - id == 0) {
					l = lista.get(i);
					lista.remove(lista.get(i));
				}
			}
		}
		return l;
	}

	public static int getIdBd() {
		return idBd;
	}
}
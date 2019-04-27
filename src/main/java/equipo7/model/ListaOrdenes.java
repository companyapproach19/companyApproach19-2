package equipo7.model;

import java.util.ArrayList;

public class ListaOrdenes {

    private ArrayList<Integer> listaIDs;

    public ListaOrdenes() {
        this.listaIDs = new ArrayList<>();
    }

    public void anyadeOrden(int ID) {
        this.listaIDs.add(ID);
    }

    public ArrayList<Integer> getListaIDs() {
    	return this.listaIDs;
    }

}
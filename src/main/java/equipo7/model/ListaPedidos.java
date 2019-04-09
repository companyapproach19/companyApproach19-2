package equipo7.model;

import java.util.ArrayList;

public class ListaPedidos {

    private ArrayList<Integer> listaIDs;

    public ListaPedidos() {
        this.listaIDs = new ArrayList<>();
    }

    public void anyadePedido(int ID) {
        this.listaIDs.add(ID);
    }

    public ArrayList<Integer> getListaIDs() {
    	return this.listaIDs;
    }

}
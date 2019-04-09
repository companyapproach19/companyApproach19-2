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

    public int[] getListaIDs() {
        int N = this.listaIDs.size();
        int[] arrayIDs = new int[N];
        for (int i = 0; i < N; i++)
            arrayIDs[i] = this.listaIDs.get(i);
        return arrayIDs;
    }

}

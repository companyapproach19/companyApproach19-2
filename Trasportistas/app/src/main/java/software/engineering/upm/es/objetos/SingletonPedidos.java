package software.engineering.upm.es.objetos;

import java.util.ArrayList;

import software.engineering.upm.es.objetos.parceables.Pedido;

public class SingletonPedidos {

    public ArrayList<Pedido> pedidosSinAsignar;
    public ArrayList<Pedido> pedidosRecogidos;
    public ArrayList<Pedido> historialPedidos;
    public int contador;


    private static final SingletonPedidos ourInstance = new SingletonPedidos();

    public static SingletonPedidos getInstance() {
        return ourInstance;
    }

    private SingletonPedidos() {
        contador = 0;
        pedidosSinAsignar = new ArrayList<>();
        pedidosRecogidos = new ArrayList<>();
        historialPedidos = new ArrayList<>();
    }
}

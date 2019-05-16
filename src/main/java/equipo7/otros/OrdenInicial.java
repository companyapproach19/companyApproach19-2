package equipo7.otros;

import equipo6.model.Actor;
import equipo7.model.Productos;

public class OrdenInicial {
	
	//id de la cadena, del pedido total
	private int idPedido;
	//Actor que realiza el pedido
	private Actor actorOrigen;
	//Actor que recibe la peticion
	private Actor actorDestino;
	//Productos pedidos (Origen pide a Destino)
	private Productos productosPedidos;
	
	public OrdenInicial(int idPedido,Actor actorOrigen, Actor actorDestino, Productos productosPedidos){
		this.idPedido = idPedido;
		this.actorOrigen = actorOrigen;
		this.actorDestino = actorDestino;
		this.productosPedidos = productosPedidos;
		
	}

	public int getIdPedido() {
		return idPedido;
	}

	public Actor getActorOrigen() {
		return actorOrigen;
	}

	public Actor getActorDestino() {
		return actorDestino;
	}

	public Productos getProductosPedidos() {
		return productosPedidos;
	}
	
}
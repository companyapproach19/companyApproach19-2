package equipo7.otros;

import equipo7.model.OrdenTrazabilidad;

public class Orden {
	
	private OrdenTrazabilidad pedido;
	
	public Orden(OrdenTrazabilidad ordenTrazabilidad) {
		this.pedido=ordenTrazabilidad;
	}
	
	public void crearPedido() {}
	
	
	public void aceptarPedido() {
		this.pedido.setEstado(OrdenTrazabilidad.EstadoOrden.EN_PROCESO);
	}
	
	public void listoParaEntregar() {
		this.pedido.setEstado(OrdenTrazabilidad.EstadoOrden.LISTO_PARA_ENTREGAR);
		if(!(pedido.getActorOrigen().getTipoActor()==1) || !(pedido.getActorDestino().getTipoActor()==0)){
			pedido.setNecesitaTransportista(true);
		}
	}
	
	public void firmadoRecogida() {
		this.pedido.setEstado(OrdenTrazabilidad.EstadoOrden.EN_PROCESO_DE_ENTREGA);
	}
	
	public void firmadoEntrega() {
		this.pedido.setEstado(OrdenTrazabilidad.EstadoOrden.ENTREGADO);
	}
	
	protected OrdenTrazabilidad getPedido() {
		return this.pedido;
	}
}

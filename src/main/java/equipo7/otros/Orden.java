package equipo7.otros;

import equipo7.model.OrdenTrazabilidad;

public class Orden {
	
	private OrdenTrazabilidad pedido;
	
	public Orden(OrdenTrazabilidad ordenTrazabilidad) {
		this.pedido=ordenTrazabilidad;
	}
	
	public void crearPedido() {}
	
	
	public void aceptarPedido() {
		this.pedido.setEstadoProceso(OrdenTrazabilidad.EstadoOrden.EN_PROCESO);
	}
	
	public void listoParaEntregar() {
		this.pedido.setEstadoProceso(OrdenTrazabilidad.EstadoOrden.LISTO_PARA_ENTREGAR);
	}
	
	public void firmadoRecogida() {
		this.pedido.setEstadoProceso(OrdenTrazabilidad.EstadoOrden.EN_PROCESO_DE_ENTREGA);
	}
	
	public void firmadoEntrega() {
		this.pedido.setEstadoProceso(OrdenTrazabilidad.EstadoOrden.ENTREGADO);
	}
	
	protected OrdenTrazabilidad getPedido() {
		return this.pedido;
	}
}

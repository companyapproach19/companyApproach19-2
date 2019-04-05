package equipo7.otros;
public class TiendaOrdenes  extends Orden{
	
	RetailerOrdenes receptor;
	
	public TiendaOrdenes() {
		//super(peticion);
	}
	
	public void crearPedido() {
		//receptor = new RetailerOrdenes(super.getPedido());
	}
	
	public String notificacion(int cod) {
		// todos los mensajes que se han de pasar por pantalla dependiendo del
		// proceso
		String mensaje = "";
		switch (cod) {
		case 1:
			mensaje+="El pedido ha sido aceptado";
			break;
		case 2:
			mensaje+="Su pedido se encuentra en proceso";
			break;
		case 3:
			mensaje+="Su pedido se encuentra listo para ser recogido";
			break;
		case 4:
			mensaje+="Su producto se encuentra en transporte ";
			break;
		case 5:
			mensaje+="El producto ha sido entregado";
			break; 
		case 6:
			mensaje+="El producto no ha sido aceptado";
			break;  

		}
		//this.getPedido().setMensaje(mensaje);
		return mensaje;
	}

}
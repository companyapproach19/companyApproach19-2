package equipo7.otros;
import equipo6.model.Actor;
public class FabricaOrdenes  extends Orden{
	
	CooperativaOrdenes receptor;
	
	public FabricaOrdenes() {
		//super(peticion);
	}
	
	public void crearPedido() {
		//receptor = new CooperativaOrdenes(super.getPedido());
	}
	public String notificacion(int cod, Actor origen) {
		// todos los mensajes que se han de pasar por pantalla dependiendo del
		// proceso
		String mensaje="";
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
		case 7:
			mensaje+="El usuario "+origen.getNombreUsuario()+"desea encargarle el siguiente pedido";
			break; 

		}
		//this.getPedido().setMensaje(mensaje);
		return mensaje;
	}

}

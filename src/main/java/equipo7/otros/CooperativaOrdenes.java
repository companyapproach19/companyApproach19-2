<<<<<<< HEAD
package equipo7.otros;
import equipo6.model.Actor;
import equipo7.model.OrdenTrazabilidad;

public class CooperativaOrdenes  extends Orden {
	
	AgricultoresOrdenes receptor;
	
	public CooperativaOrdenes() {
		//super(peticion);
	}
	
	public void crearPedido() {
		//receptor = new AgricultoresOrdenes(super.getPedido());
	}

	public String notificacion(int cod, Actor origen) {// se notifica un mensaje
		// en funcion del codigo lanzaremos un mensaje u otro
		String mensaje="";
		switch (cod) {
		case 2:
			mensaje+="Su pedido ha sido aceptado y se encuentra en proceso";
			break;
		case 3:
			mensaje+="Su pedido ha sido entregado";
			break;
		case 4:
			mensaje+="El usuario "+origen.getNombreUsuario()+" desea encargarle un siguiente pedido";
			break;
		case 5:
			mensaje+="El producto no ha sido aceptado";
			break; 

		}
		//this.getPedido().setMensaje(mensaje);
		return mensaje;
	}
	
	public boolean listo_recoger() {
		return true;
	}


=======
package equipo7.otros;
import equipo6.model.Actor;
import equipo7.model.OrdenTrazabilidad;

public class CooperativaOrdenes  extends Orden {
	
	AgricultoresOrdenes receptor;
	
	public CooperativaOrdenes() {
		//super(peticion);
	}
	
	public void crearPedido() {
		//receptor = new AgricultoresOrdenes(super.getPedido());
	}

	public String notificacion(int cod, Actor origen) {// se notifica un mensaje
		// en funcion del codigo lanzaremos un mensaje u otro
		String mensaje="";
		switch (cod) {
		case 2:
			mensaje+="Su pedido ha sido aceptado y se encuentra en proceso";
			break;
		case 1:
			mensaje+="Su pedido ha sido entregado";
			break;
		case 3:
			mensaje+="El usuario "+origen.getNombreUsuario()+" desea encargarle un siguiente pedido";
			break;
		case 4:
			mensaje+="El producto no ha sido aceptado";
			break; 
		default:
			return "Error en el codigo de notificacion";

		}
		//this.getPedido().setMensaje(mensaje);
		return mensaje;
	}
	
	public boolean listo_recoger() {
		return true;
	}


>>>>>>> master
}
package equipo7.otros;
import equipo6.model.Actor;

public class AgricultoresOrdenes extends Orden{
	
	public AgricultoresOrdenes() {
		//super(peticion);
	}
	
    public String notificacion(int cod,Actor origen){//se notifica un mensaje
        //en funcion del codigo lanzaremos un mensaje u otro
    	String mensaje="";
    	switch (cod) {
		case 1:
			mensaje+="El usuario "+origen.getNombreUsuario()+" desea encargarle un pedido ";
			break;  
		default:
			return "Error en el codigo de notificacion";	
		}
    	return mensaje;
    } 

}
package equipo7.otros;

import equipo6.model.Actor;

public class Orden {
	
	//private OrdenTrazabilidad pedido;
	
	public Orden() {
		//this.pedido=ordenTrazabilidad;
	}
	
	public void crearPedido() {}
	
	
	public void aceptarPedido(int estado) {
		if(estado == 0) estado=1;
		else estado= -1;
	}
	
	//Devuelve si se necesita transportista
	public boolean listoParaEntregar(int estado, Actor origen, Actor destino) {
		if(estado==1){
			estado=2;
			if(origen.getTipoActor()!=1 && destino.getTipoActor()!=0){
				return true;
			}
		}
		return false;
	}
	
	//TODO: comrobar que se cambia el estado viendo si esta firmado
	public void firmadoRecogida(int estado) {
		if(estado==2) estado=3;
		else estado=-1;
	}
	
	public void firmadoEntrega(int estado) {
		if(estado==3) estado=4;
		else estado=-1;
	}

}

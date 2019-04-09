
package equipo7.otros;

import java.io.Serializable;

import equipo6.model.Actor;

public class Orden implements Serializable{
	
	//private OrdenTrazabilidad pedido;
	
	public Orden() {
		//this.pedido=ordenTrazabilidad;
	}
	
	public void crearPedido() {}
	
	
	public int aceptarPedido(int estado) {
		if(estado == 0) return 1;
		else return -1;
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
	public int firmadoRecogida(int estado) {
		if(estado==2) return 3;
		else return -1;
	}
	
	public int firmadoEntrega(int estado) {
		if(estado==3) return 4;
		else return -1;
	}

}

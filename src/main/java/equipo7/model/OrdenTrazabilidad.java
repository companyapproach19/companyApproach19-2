package equipo7.model;

import equipo6.model.DatosContainer;
import equipo7.otros.Orden;
import equipo8.model.Registro;

import java.util.ArrayList;

import equipo6.model.Actor;

public class OrdenTrazabilidad extends DatosContainer
{

	/*
	 * Los objetos que hay que pasar al grupo de trazabilidad seran de esta clase
	 * Contienen la siguiente informacion:
	 * Identificador(id) para cada pedido
	 * Origen del pedido(quien hace la orden): (productor, cooperativa, f�brica, retailer, tienda)
	 * Destino del pedido(a quien le hacen la peticion): (productor, cooperativa, f�brica, retailer, tienda)
	 * Mensaje del pedido
	 * Estado del pedido (definidos los estados en el ENUM EstadoOrden)
	 * 
	*/
		//Es necesario un identificador por cada pedido
		private static int id;
		//Actor que realiza el pedido
		private Actor actorOrigen;
		//Actor que recibe la peticion
		private Actor actorDestino;
		//Argumento que debe mirar el equipo de transportistas 
		private boolean necesitaTransportista;
		//Productos pedidos
		private Productos productos;
		private String mensaje;
		//Estado del pedido
		private EstadoOrden estado;
		// El transportista firma en dos ocasiones del pedido:
		// en la recogida del pedido (llegada al origen)
		private byte[] firmaRecogida;
		//en la entrega del pedido (llegada al destino)
		private byte[] firmaEntrega;
		//Este objeto sera de la clase Ordenes del actor de origen
		//Contiene al objeto de la clase Ordenes del actor de destino
		private Orden origenOrdenes;
		//Lista de padres
		private int idPadre;
		//Lista de hijos
		private int idHijo;
		//Datos del transportista
		private Actor transportista;
		//Registro de sensor del transporte
		private Registro registro;
		
		

		public OrdenTrazabilidad(int identificador,String mensaje, Actor emisor, Actor receptor, Productos productos) {
			this.id = identificador;
			this.actorDestino=receptor;
			this.actorOrigen=emisor;
			this.productos=productos;
			this.mensaje=mensaje;
			this.estado=null;
			this.firmaRecogida =null;
			this.firmaEntrega =null;
			this.necesitaTransportista=false;
			this.idPadre = -1;
			this.idHijo = -1;
		}
		
		public void setActorOrigen(Actor actorOrigen) {
			this.actorOrigen = actorOrigen;
		}

		public void setActorDestino(Actor actorDestino) {
			this.actorDestino = actorDestino;
		}

		public void setProductos(Productos productos) {
			this.productos = productos;
		}

		public void setEstado(EstadoOrden estado) {
			this.estado = estado;
		}

		public void setId(int id) {
			this.id=id;
		}
		
		public void setNecesitaTransportista(boolean necesitaTransportista) {
			this.necesitaTransportista=true;
		}
		
		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}
		
		public void setFirmaRecogida(byte[] firmaRecogida) {
			this.firmaRecogida = firmaRecogida;
		}

		public void setFirmaEntrega(byte[] firmaEntrega) {
			this.firmaEntrega = firmaEntrega;
		}
		
		public void setOrigenOrdenes(Orden origenOrdenes) {
			this.origenOrdenes=origenOrdenes;
		}

		public void setPadres(int idPadre) {
			this.idPadre = idPadre;
		}

		public void setHijos(int idHijo) {
			this.idHijo = idHijo;
		}
		
		public void setTransportista(Actor transportista) {
			this.transportista = transportista;
		}
		

		public void setRegistro(Registro registro) {
			this.registro = registro;
		}

		public boolean getNecesitaTransportista() {
			return this.necesitaTransportista;
		}
		
		public static int getId() {
			return id;
		}

		public String getMensaje() {
			return mensaje;
		}

		public Actor getActorOrigen() {
			return actorOrigen;
		}

		public Actor getActorDestino() { return actorDestino; }

		public EstadoOrden getEstado() {
			return estado;
		}

		public byte[] getFirmaRecogida() {
			return firmaRecogida;
		}

		public byte[] getFirmaEntrega() {
			return firmaEntrega;
		}
		
		public Productos getProductos() {
			return productos;
		}
    
		public Orden getOrigenOrdenes() {
			return origenOrdenes;
		}
		
		public int getIdPadre() {
			return idPadre;
		}
		
		public int getIdHijo() {
			return idHijo;
		}
		
		public Actor getTransportista() {
			return transportista;
		}
		
		public Registro getRegistro() {
			return registro;
		}


		
		public enum EstadoOrden {
			EN_PROCESO, LISTO_PARA_ENTREGAR, EN_PROCESO_DE_ENTREGA, ENTREGADO
		}

 
}
package equipo7.model;

import equipo6.model.DatosContainer;
import equipo6.model.Actor;
import java.util.Base64;

import java.sql.Date;
import java.util.ArrayList;


public class OrdenTrazabilidad extends DatosContainer
{

	/*
	 * Los objetos que hay que pasar al grupo de trazabilidad seran de esta clase
	 * Contienen la siguiente informacion:
	 * Identificador(id) para cada pedido
	 * Origen del pedido(quien hace la orden): (productor, cooperativa, fabrica, retailer, tienda)
	 * Destino del pedido(a quien le hacen la peticion): (productor, cooperativa, fabrica, retailer, tienda)
	 * Mensaje del pedido
	 * Estado del pedido (definidos los estados en el ENUM EstadoOrden)
	 * 
	*/
		//Es necesario un identificador por cada pedido
		private int id;
		//Actor que realiza el pedido
		private Actor actorOrigen;
		//Actor que recibe la peticion
		private Actor actorDestino;
		//Argumento que debe mirar el equipo de transportistas 
		private boolean necesitaTransportista;
		//Productos pedidos (Origen pide a Destino)
		private Productos productosPedidos;
		//id de los Productos(MateriaPrima y Lote) que devuelve el Destino al Origen
		private ArrayList<Integer> productosAEntregar;
		//Estado del pedido
		//Siendo 0=pendienteDeAceptar;1=enProceso;2=ListoParaEntregar;3=EnProcesoDeEntrega;4=Entregado;
		//-1=Rechazado
		private int estado;
		// El transportista firma en dos ocasiones del pedido:
		// en la recogida del pedido (llegada al origen)
		private String firmaRecogida;
		//en la entrega del pedido (llegada al destino)
		private String firmaEntrega;
		//Datos del transportista
		private Actor transportista;
		//idRegistro del transporte -> Lo rellena el transportista en firmaEntrega
		private int idRegistro;
		//id de la cadena, del pedido total
		private int idPedido;
		//fecha para BBDD
		private Date fecha;
		
		

		public OrdenTrazabilidad(int id, Actor actorOrigen, Actor actorDestino, boolean necesitaTransportista, 
				Productos productosPedidos, ArrayList<Integer> productosAEntregar, int estado, String firmaRecogida,
				String firmaEntrega, Actor transportista, int idRegistro, int idPedido, Date fecha) {
			this.id=id;
			this.actorOrigen=actorOrigen;
			this.actorDestino=actorDestino;
			this.necesitaTransportista=necesitaTransportista;
			this.productosPedidos=productosPedidos;
			this.productosAEntregar=productosAEntregar;
			this.estado=estado;
			this.firmaRecogida=firmaRecogida;
			this.firmaEntrega=firmaEntrega;
			this.transportista=transportista;
			this.idRegistro=idRegistro;
			this.idPedido=idPedido;
			this.fecha=fecha;
			
		}
		
		//Constructor para descodificar json de vistas inicial
		public OrdenTrazabilidad(int id, Actor actorOrigen, Actor actorDestino, Productos productosPedidos) {
			this.id = id;
			this.actorDestino=actorOrigen;
			this.actorOrigen=actorDestino;
			this.productosPedidos=productosPedidos;
			
			this.productosAEntregar=new ArrayList<Integer>();
			this.estado=0;
			this.necesitaTransportista=false;
			this.firmaRecogida = "";
			this.firmaEntrega = "";
			this.transportista=null;
			this.idPedido=-1;
			this.idRegistro=-1;
			
			
		}
		
		//Constructor para descodificar json de transportistas
		//firmaRecogida
		public OrdenTrazabilidad(int id, Actor actorOrigen, Actor actorDestino, boolean necesitaTransportista, 
				Productos productosPedidos,ArrayList<Integer> productosAEntregar, String firmaRecogida,
				Actor transportista) {
			this.id=id;
			this.actorOrigen=actorOrigen;
			this.actorDestino=actorDestino;
			this.necesitaTransportista=necesitaTransportista;
			this.productosPedidos=productosPedidos;
			this.productosAEntregar=productosAEntregar;
			this.firmaRecogida=firmaRecogida;
			this.firmaEntrega= "";
			this.transportista=transportista;
			this.estado=-1;
			this.idRegistro=-1;
			this.idPedido=-1;
			
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Actor getActorOrigen() {
			return actorOrigen;
		}

		public void setActorOrigen(Actor actorOrigen) {
			this.actorOrigen = actorOrigen;
		}

		public Actor getActorDestino() {
			return actorDestino;
		}

		public void setActorDestino(Actor actorDestino) {
			this.actorDestino = actorDestino;
		}

		public boolean isNecesitaTransportista() {
			return necesitaTransportista;
		}

		public void setNecesitaTransportista(boolean necesitaTransportista) {
			this.necesitaTransportista = necesitaTransportista;
		}

		public Productos getProductosPedidos() {
			return productosPedidos;
		}

		public void setProductosPedidos(Productos productosPedidos) {
			this.productosPedidos = productosPedidos;
		}

		public ArrayList<Integer> getProductosAEntregar() {
			return productosAEntregar;
		}

		public void setProductosAEntregar(ArrayList<Integer> productosAEntregar) {
			this.productosAEntregar = productosAEntregar;
		}

		public int getEstado() {
			return estado;
		}

		public void setEstado(int estado) {
			this.estado = estado;
		}

		public String getFirmaRecogida() {
			return firmaRecogida;
		}

		public void setFirmaRecogida(String firmaRecogida) {
			this.firmaRecogida = firmaRecogida;
		}

		public String getFirmaEntrega() {
			return firmaEntrega;
		}

		public void setFirmaEntrega(String firmaEntrega) {
			this.firmaEntrega = firmaEntrega;
		}

		public byte[] getFirmaRecogidaBBDD() {
			byte[] decodedBytes = Base64.getDecoder().decode(this.firmaRecogida.getBytes());
			return decodedBytes;
		}

		public void setFirmaRecogidaBBDD(byte[] firmaRecogida) {
			byte[] encodedBytes = Base64.getEncoder().encode(firmaRecogida);
			this.firmaRecogida = new String(encodedBytes);
		}

		public byte[] getFirmaEntregaBBDD() {
			byte[] decodedBytes = Base64.getDecoder().decode(this.firmaRecogida.getBytes());
			return decodedBytes;
		}

		public void setFirmaEntregaBBDD(byte[] firmaEntrega) {
			byte[] encodedBytes = Base64.getEncoder().encode(firmaEntrega);
			this.firmaRecogida = new String(encodedBytes);
		}

		public Actor getTransportista() {
			return transportista;
		}

		public void setTransportista(Actor transportista) {
			this.transportista = transportista;
		}

		public int getIdRegistro() {
			return idRegistro;
		}

		public void setIdRegistro(int idRegistro) {
			this.idRegistro = idRegistro;
		}

		public int getIdPedido() {
			return idPedido;
		}

		public void setIdPedido(int idPedido) {
			this.idPedido = idPedido;
		}

		public Date getFecha() {
			return fecha;
		}

		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}
		
		
 
}
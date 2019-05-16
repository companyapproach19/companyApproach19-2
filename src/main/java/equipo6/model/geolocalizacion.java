package equipo6.model;

import java.sql.Date;


public class geolocalizacion extends DatosContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int idPedido; 
	private int idOrden;
	private String coordenadas; 
	private Date date;

	//constructor bbdd
	public geolocalizacion(int id,int idPedido, int idOrden, String coordenadas,Date date) {
		super();
		this.id=id;
		this.idPedido = idPedido;
		this.idOrden = idOrden;
		this.coordenadas = coordenadas;
		this.date=date;
	}

	//constructor vista
	public geolocalizacion(int id,int idPedido, int idOrden, String coordenadas) {
		super();
		this.id=id;
		this.idPedido = idPedido;
		this.idOrden = idOrden;
		this.coordenadas = coordenadas;
	}
	
	
	
	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public int getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(int idOrden) {
		this.idOrden = idOrden;
	}

	public String getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
<<<<<<< HEAD
}
=======
}
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e

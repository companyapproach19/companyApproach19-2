package equipo5.model;

import java.sql.Date;

import equipo4.model.MateriaPrima;

public class StockMP {
	private MateriaPrima mp;
	private Date fecha_entrada;
	private Date fecha_salida;
	private int idOrden;
	private int idPedido;
	private int idActor;
	public MateriaPrima getMp() {
		return mp;
	}
	public void setMp(MateriaPrima mp) {
		this.mp = mp;
	}
	public Date getFecha_entrada() {
		return fecha_entrada;
	}
	public void setFecha_entrada(Date fecha_entrada) {
		this.fecha_entrada = fecha_entrada;
	}
	public Date getFecha_salida() {
		return fecha_salida;
	}
	public void setFecha_salida(Date fecha_salida) {
		this.fecha_salida = fecha_salida;
	}
	public int getIdOrden() {
		return idOrden;
	}
	public void setIdOrden(int idOrden) {
		this.idOrden = idOrden;
	}
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public int getIdActor() {
		return idActor;
	}
	public void setIdActor(int idActor) {
		this.idActor = idActor;
	}
	public StockMP(MateriaPrima mp, Date fecha_entrada, Date fecha_salida, int idOrden, int idPedido, int idActor) {
		this.mp = mp;
		this.fecha_entrada = fecha_entrada;
		this.fecha_salida = fecha_salida;
		this.idOrden = idOrden;
		this.idPedido = idPedido;
		this.idActor = idActor;
	}
	
}
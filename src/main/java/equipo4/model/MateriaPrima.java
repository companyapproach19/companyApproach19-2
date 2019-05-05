package equipo4.model;

import java.sql.SQLException;

import equipo5.dao.metodosCompany;

public class MateriaPrima {
	private String tipo;
	private int id ;
	private double cantidad;
	
	public MateriaPrima(String tipo, int id, double cantidad) {
		this.tipo = tipo;
		this.id = id;
		this.cantidad = cantidad;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
}
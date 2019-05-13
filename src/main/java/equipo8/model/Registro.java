package equipo8.model;

import java.sql.SQLException;

import equipo6.model.DatosContainer;
//import equipo5.dao.metodosCompany;

public class Registro extends DatosContainer{
	
	private int id;
	private int idOrdenTrazabilidad;
	private int idPedido;
	private String fechaInicio;
	private String fechaFin;
	private int tempMax;
	private int tempMin;
	private static int contId = 1; //tests sin servidor
	
	public Registro(int id,int idOrdenTrazabilidad, int idPedido, String fechaInicio, String fechaFin, int tempMax, int tempMin) {
		this.id = id;
		this.idOrdenTrazabilidad=idOrdenTrazabilidad;
		this.idPedido = idPedido;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}
	
	public Registro(int idPedido,int idOrdenTrazabilidad,String fechaInicio, String fechaFin, int tempMax, int tempMin) {
//		try {
//			this.id = equipo5.dao.metodosCompany.idRegistro();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		//tests sin servidors
		this.id = contId;
		contId++;
		this.idOrdenTrazabilidad=idOrdenTrazabilidad;
		this.idPedido = idPedido;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}
	
	
	public Registro(String error) {
		this.id=-1;
		this.fechaInicio = "error";
		this.fechaFin = "error";
		this.tempMax=-1000;
		this.tempMin=-1000;
    
	}
	

	public String toString(){
		return "\n"+ "idRegistro: " + this.id + "\nidOrdenTrazabilidad: "+ this.idOrdenTrazabilidad + "\nidPedido: " + this.idPedido +"\nTemperatura máxima: "+ this.tempMax +"ºC"+"\n"+"Temperatura mínima: "+ this.tempMin +"ºC"+"\n"+"Fecha inicio: "+ this.fechaInicio +"\n"+"Fecha fin: " + this.fechaFin+"\n";   
	}
	
	public void setId (int id) {
		this.id = id;		
	}
	
	public void setIdOrdenTrazabilidad (int idOrdenTrazabilidad) {
		this.idOrdenTrazabilidad = idOrdenTrazabilidad;		
	}
	
	public void setIdPedido (int idPedido) {
		this.idPedido = idPedido;
	}
	
	public void setFechaInicio (String fechaInicio) {
		this.fechaInicio = fechaInicio;		
	}
	
	public void setFechaFin (String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public void setTempMax (int tempMax) {
		this.tempMax = tempMax;
	}
	
	public void setTempMin (int tempMin) {
		this.tempMin = tempMin;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getIdOrdenTrazabilidad() {
		return this.idOrdenTrazabilidad;
	}

	public int getIdPedido() {
		return this.idPedido;
	}

	
	public String getFechaInicio () {
		return this.fechaInicio;
	}
	
	public String getFechaFin() {
		return this.fechaFin;
	}
	
	public int getTempMax() {
		return this.tempMax;
	}
	
	public int getTempMin() {
		return this.tempMin;
	}
}

package equipo8.model;

import equipo6.model.DatosContainer;

public class Registro extends DatosContainer{
	
	private int idLote;
	private int idActor;
	private String fechaInicio;
	private String fechaFin;
	private int tempMax;
	private int tempMin;
	
	public Registro(int idLote,int idactor, int tempMax, int tempMin, String fechaInicio, String fechaFin) {
		this.idLote=idLote;
		this.idActor = idactor;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}

	public String toString(){
		return "idLote:"+idLote +"\n"+"idActor:"+idActor+"\n"+"Temperatura máxima:"+tempMax+"ºC"+"\n"+"Temperatura mínima:"+tempMin +"ºC"+"\n"+"Fecha inicio:"+fechaInicio +"\n"+"Fecha fin:"+fechaFin+"\n";   
	}
	
	public void setIdLote (int idLote) {
		this.idLote = idLote;		
	}
	
	public void setActor (int idActor) {
		this.idActor = idActor;
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
	
	public int getIdLote() {
		return this.idLote;
	}

	public int getActor() {
		return this.idActor;
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



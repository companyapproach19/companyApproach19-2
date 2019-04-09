package equipo8.model;

import equipo4.model.Lote;
import equipo6.model.Actor;
import equipo6.model.DatosContainer;
import java.sql.SQLException;
import equipo5.dao.metodosCompany;

public class Registro extends DatosContainer{
	
	private int id;
	private Lote lote;
	private Actor actor;
	private String fechaInicio;
	private String fechaFin;
	private int tempMax;
	private int tempMin;
	
	public Registro(int id,Lote lote, Actor actor, String fechaInicio, String fechaFin, int tempMax, int tempMin) {
		this.id = id;
		this.lote=lote;
		this.actor = actor;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}
	
	public Registro(Lote lote, Actor actor, String fechaInicio, String fechaFin, int tempMax, int tempMin) {
		this.id = metodosCompany.idRegistro(); 
		this.lote=lote;
		this.actor = actor;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}

	public String toString(){
		return "Lote:" +lote.getTipo()+"\n"+"Actor:"+ actor.getNombreUsuario() +"\n"+"Temperatura máxima:"+tempMax+"ºC"+"\n"+"Temperatura mínima:"+tempMin +"ºC"+"\n"+"Fecha inicio:"+fechaInicio +"\n"+"Fecha fin:"+fechaFin+"\n";   
	}
	
	public void setId (int id) {
		this.id = id;		
	}
	
	public void setLote (Lote lote) {
		this.lote = lote;		
	}
	
	public void setActor (Actor actor) {
		this.actor = actor;
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
	
	public Lote getLote() {
		return this.lote;
	}

	public Actor getActor() {
		return this.actor;
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





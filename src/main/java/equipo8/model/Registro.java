package equipo8.model;

import java.sql.SQLException;

import equipo4.model.Lote;
import equipo5.dao.metodosCompany;
import equipo6.model.Actor;
import equipo6.model.DatosContainer;

public class Registro extends DatosContainer{
	
	private int id;
	private Lote lote;
	private Actor actor;
	private String fechaInicio;
	private String fechaFin;
	private int tempMax;
	private int tempMin;
	
	public Registro(Lote lote, Actor actor, String fechaInicio, String fechaFin, int tempMax, int tempMin) throws ClassNotFoundException, SQLException {
		this.id=metodosCompany.idRegistro();
		this.lote=lote;
		this.actor = actor;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}
	public Registro(int id, Lote lote, Actor actor, String fechaInicio, String fechaFin, int tempMax, int tempMin) throws ClassNotFoundException, SQLException {
		this.id=id;
		this.lote=lote;
		this.actor = actor;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}

//	public String toString(){
//		return "idLote:"+idLote +"\n"+"idActor:"+idActor+"\n"+"Temperatura máxima:"+tempMax+"ºC"+"\n"+"Temperatura mínima:"+tempMin +"ºC"+"\n"+"Fecha inicio:"+fechaInicio +"\n"+"Fecha fin:"+fechaFin+"\n";   
//	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
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

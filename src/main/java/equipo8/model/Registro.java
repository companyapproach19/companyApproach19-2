package equipo8.model;

public class Registro {
	private int idLote;
  	private int anio;
  	private int mes;
  	private int dia;
	private int hora;
  	private int min;
  	private int sec;
	private String fechaInicio;
	private String fechaFin;
	private int actor;
	private int tempMax;
	private int tempMin;
	
	public Registro(int idLote,int actor, int tempMax, int tempMin, String fechaInicio, String fechaFin) {
		this.idLote=idLote;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.actor = actor;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}

	public void setIdLote(int idLote) {
		this.idLote = idLote;
	}
	
	private void setAnio(int anio) {
		this.anio = anio;
	}

	private void setMes(int mes) {
		this.mes = mes;
	}

	private void setDia(int dia) {
		this.dia = dia;
	}

	private void setHora(int hora) {
		this.hora = hora;
	}

	private void setMin(int min) {
		this.min = min;
	}

	private void setSec(int sec) {
		this.sec = sec;
	}
	
	public void setFechaInicio (String fechaInicio) {
		this.fechaInicio = fechaInicio;		
	}
	
	public void setFechaFin (String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public void setActor (int actor) {
		this.actor = actor;
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
	private int getAnio() {
		return this.anio;
	}
	
	private int getMes() {
		return this.mes;
	}
	
	private int getDia() {
		return this.dia;
	}
	
	private int getHora() {
		return this.hora;
	}
	
	private int getMin() {
		return this.min;
	}
	
	private int getSec() {
		return this.sec;
	}
	
	public String getFechaInicio () {
		return this.fechaInicio;
	}
	
	public String getFechaFin() {
		return this.fechaFin;
	}
	
	public int getActor() {
		return this.actor;
	}
	
	public int getTempMax() {
		return this.tempMax;
	}
	
	public int getTempMin() {
		return this.tempMin;
	}
}



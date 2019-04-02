package sensor;

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
	
	public Registro(int idLote,int anio, int mes, int dia, int hora, int min, int sec,int actor, int tempMax, int tempMin) {
		tthis.idLote=idLote;
		this.anio = anio;
		this.mes = mes;
		this.dia = dia;
		this.hora = hora;
		this.min = min;
		this.sec = sec;
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
	
	public void setFechaInicio (int fechaInicio) {
		this.fechaInicio = fechaInicio;		
	}
	
	public void setFechaFin (int fechaFin) {
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
	
	public int getFechaInicio () {
		return this.fechaInicio;
	}
	
	public int getFechaFin() {
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


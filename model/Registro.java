package sensor;

public class Registro {
  	private String anio;
  	private String mes;
  	private String dia;
	private String hora;
  	private String min;
  	private String sec;
	private String fechaInicio;
	private String fechaFin;
	private String actor;
	private int tempMax;
	private int tempMin;
	
	public Registro(String anio, String mes, String dia, String hora, String min, String sec, String fechaInicio, String fechaFin, String actor, int tempMax, int tempMin) {
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

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}
	
	public void setFechaInicio (String fechaInicio) {
		this.fechaInicio = fechaInicio;		
	}
	
	public void setFechaFin (String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public void setActor (String actor) {
		this.actor = actor;
	}
	
	public void setTempMax (int tempMax) {
		this.tempMax = tempMax;
	}
	
	public void setTempMin (int tempMin) {
		this.tempMin = tempMin;
	}
	
	
	public String getAnio() {
		return this.anio;
	}
	
	public String getMes() {
		return this.mes;
	}
	
	public String getDia() {
		return this.dia;
	}
	
	public String getHora() {
		return this.hora;
	}
	
	public String getMin() {
		return this.min;
	}
	
	public String getSec() {
		return this.sec;
	}
	
	public String getFechaInicio () {
		return this.fechaInicio;
	}
	
	public String getFechaFin() {
		return this.fechaFin;
	}
	
	public String getActor() {
		return this.actor;
	}
	
	public int getTempMax() {
		return this.tempMax;
	}
	
	public int getTempMin() {
		return this.tempMin;
	}
}



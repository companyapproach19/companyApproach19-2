package equipo8.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import equipo6.model.DatosContainer;

public class Sensor extends DatosContainer{
	
    private static int id;
	//Aquí se encuentra el .txt con el registro
  	private static BufferedReader log; 
  	
  	//Contiene cada linea del registro
  	private static String linea;
	//Formato año-mes-dia
  	private static String fecha;
  	private static String anio;
  	private static String mes;
  	private static String dia;
	// Al principio contiene hora:minuto:segundo, después solo la hora
	private static String hora;
  	private static String min;
  	private static String sec;
	//Array que sirve para detectar las partes de la fecha y de la hora
  	private static String partes [];
  	private static String partesFecha [];
 	private static String partesHora [];
  	//Donde se guardará la temperatura
  	private static String temperatura ;

  	// Cambiar por la ruta donde esté el log que se quiera probar
  	private static String ruta="C:\\Users\\Laura Colomer\\Documents\\datosSensor.txt" ;
  	
  	//Permite buscar la temperatura ó humedad de una determinada fecha y hora dentro del registro
	public String buscar(String anioParam, String mesParam, String diaParam, String horaParam, String minParam, String secParam) throws IOException {
		// log contiene todo el registro de temperaturas
		log = new BufferedReader(new FileReader(ruta)); 
		// Va leyendo línea por línea...
		while ((linea = log.readLine()) != null) {
			//Se saltan las lineas vacías
			if(!linea.isEmpty()){
				partes  = linea.split(" ");
				fecha = partes[0];
				partesFecha  = fecha.split("-");
				anio=partesFecha [0];
				//Si coincide toda la fecha...
				if(anio.equals(anioParam)) {
					mes=partesFecha [1];
					if(mes.equals(mesParam)) {
						dia=partesFecha [2];
						if(dia.equals(diaParam)) {
							hora = partes[1];
							partesHora = hora.split(":");
							hora=partesHora[0];
							//Si coincide toda la hora...
							if(hora.equals(horaParam)){
								min=partesHora[1];
								if(min.equals(minParam)){
									sec=partesHora[2];
									if(sec.equals(secParam)){
										if(partes[2].contains("ºC")){
											//Devuelve la temperatura "XºC"
											return partes[2];
										}
									}																		
								}
							}
						}
					}
				}
			}
		}
		log.close();
		return "Incorrecto";
	}
	
	
	//Cambiar el tipo de retorno a Regsitro
	public  Object registro ()throws IOException {
		HashMap<Fecha,Integer> listaRegistros= new HashMap<Fecha,Integer>();
		log = new BufferedReader(new FileReader(ruta)); 
		 Fecha fecha,fechaInicio;
		 int contador=0;
		 int Tmax=0;
		 int Tmin=0;
		while ((linea = log.readLine()) != null) {
			//Se saltan las lineas vacías
			if(!linea.isEmpty() && linea.contains("º")){
				 
				 String fecha_temp[] =linea.split("+");
				 String fecha_hora [] = fecha_temp[0].split("|");
				 String anio_mes_dia [] = fecha_hora[0].split("-");
				 String hora_min_seg [] = fecha_hora[1].split(":");
				 int anio,mes,dia,hora,minuto,segundo,temp;
				 anio=Integer.valueOf(anio_mes_dia [0]);
				 mes=Integer.valueOf(anio_mes_dia [1]);
				 dia=Integer.valueOf(anio_mes_dia [2]);
				 
				 hora=Integer.valueOf(hora_min_seg [0] );
				 minuto=Integer.valueOf(hora_min_seg [1] );
				 segundo=Integer.valueOf(hora_min_seg [2] );
				 
				 temp=Integer.valueOf(fecha_temp[1].split("º")[0]);
				 
				 fecha =new Fecha (anio,mes,dia,hora,minuto,segundo);
	             if (contador==0) {
	            	 Tmax=temp;
	            	 Tmin=temp;
	            	 fechaInicio=fecha;
	             }
				 listaRegistros.put(fecha, temp);
				
			}
			//al salir del bucle fecha contiene la fecha final
			
		Iterator<Fecha> it=	listaRegistros.keySet().iterator();
		
		while(it.hasNext()) {
			Fecha f = it.next();
			int t= listaRegistros.get(f);
			if(t<Tmin)Tmin=t;
			else if (t>Tmax)Tmax=t;	
		}
			
		}
		
		//importar clase registro y devolver el registro
	
		return null;
		
	}

	//Futuros gettes y setters
	public static String getTemperatura() {
		return temperatura;
	}

	public static void setTemperatura(String temperatura) {
		Sensor.temperatura = temperatura;
	}


	public static String getAnio() {
		return anio;
	}

	public static void setAnio(String anio) {
		Sensor.anio = anio;
	}


	public static String getMes() {
		return mes;
	}

	public static void setMes(String mes) {
		Sensor.mes = mes;
	}


	public static String getDia() {
		return dia;
	}

	public static void setDia(String dia) {
		Sensor.dia = dia;
	}


	public static String getHora() {
		return hora;
	}

	public static void setHora(String hora) {
		Sensor.hora = hora;
	}


	public static String getMin() {
		return min;
	}

	public static void setMin(String min) {
		Sensor.min = min;
	}


	public static String getSec() {
		return sec;
	}

	public static void setSec(String sec) {
		Sensor.sec = sec;
	}

	public static void setID (int id){
    Sensor.id = id;
 }
 public static int getID (){
  return id;
 }
	
	//clase auxiliar que vamos a usar para facilitar 
 private class Fecha {
	 public int anio ;
	 public int mes ;
	 public int dia;
	 public int hora ;
	 public int minuto ;
	 public int segundo ;
	 
	 public Fecha (int anio , int mes , int dia, int hora , int minuto , int segundo ) {
		 
		 this.anio=anio;
		 this.mes=mes;
		 this.dia=dia;
		 this.hora=hora;
		 this.minuto=minuto;
		 this.segundo=segundo;
		 
		 
	 }
	 
	 
	 
 }

}

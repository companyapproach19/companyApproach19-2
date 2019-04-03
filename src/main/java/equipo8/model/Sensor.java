package equipo8.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

//PENDIENTE PONER CONSTRUCTOR
public class Sensor {

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
	private static String ruta="/Users/Carlos/Documents/Processing/Escritura/test5.txt" ;



	//Cambiar el tipo de retorno a Regsitro
	public  Registro registro ()throws IOException {

		HashMap<Fecha,Integer> listaRegistros= new HashMap<Fecha,Integer>();
		log = new BufferedReader(new FileReader(ruta)); 
		Fecha fecha = null,fechaInicio = null;
		int contador=0;
		int Tmax=0;
		int Tmin=0;
		while ((linea = log.readLine()) != null) {
			//Se saltan las lineas vacías
			if(!linea.isEmpty() && linea.contains("º")){

				String fecha_temp[] =linea.split("\\+");
				String fecha_hora [] = fecha_temp[0].split("\\|");
				String anio_mes_dia [] = fecha_hora[0].split("\\-");
				String hora_min_seg [] = fecha_hora[1].split("\\:");
				int anio,mes,dia,hora,minuto,segundo,temp;
				anio=Integer.valueOf(anio_mes_dia [0].trim());
				mes=Integer.valueOf(anio_mes_dia [1].trim());
				dia=Integer.valueOf(anio_mes_dia [2].trim());

				hora=Integer.valueOf(hora_min_seg [0].trim() );
				minuto=Integer.valueOf(hora_min_seg [1].trim() );
				segundo=Integer.valueOf(hora_min_seg [2].trim() );

				temp=Integer.valueOf(fecha_temp[1].split("\\º")[0].trim());

				fecha = new Fecha (anio,mes,dia,hora,minuto,segundo);
				if (contador==0) {
					Tmax=temp;
					Tmin=temp;
					fechaInicio=fecha;
				}
				listaRegistros.put(fecha, temp);
				contador++;

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

		Registro registro= new Registro(0, 0, Tmax, Tmin,  fechaInicio.toString(), fecha.toString());
		File file = new File(ruta);
		file.delete();
		file.createNewFile();
		return registro;

	}

	public static void setID (int id){
		Sensor.id = id;
	}
	public static int getID (){
		return id;
	}

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
		
		public String toString() {
			return anio + "-" + mes + "-" + dia + "|" + hora + ":" + minuto + ":" + segundo;
		}



	}

}

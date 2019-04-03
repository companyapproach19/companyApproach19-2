
package equipo8.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

// Clase para parsear el .txt donde guardamos los datos recogidos por Arduino
public class Sensor {

	private static int idSensor;
	//Aquí se encuentra el .txt con el registro
	private static BufferedReader log; 

	//Contiene cada linea del registro
	private static String linea;	
	
	public Sensor(int idSensor) {
		Sensor.idSensor=idSensor;
	}
	
	
	public  Registro crearRegistro(int idLote, int idActor,String ruta)throws IOException {

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

			Iterator<Fecha> it=	listaRegistros.keySet().iterator();

			while(it.hasNext()) {
				Fecha f = it.next();
				int t= listaRegistros.get(f);
				if(t<Tmin)Tmin=t;
				else if (t>Tmax)Tmax=t;	
			}

		}
		
		//al salir del bucle fecha contiene la fecha final
		Registro registro= new Registro(idLote, idActor, Tmax, Tmin,  fechaInicio.toString(), fecha.toString());
		//File file = new File(ruta);
		//file.delete();
		//file.createNewFile();
		return registro;
	}

	public static void setID (int id){
		Sensor.idSensor = id;
	}
	public static int getID (){
		return idSensor;
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

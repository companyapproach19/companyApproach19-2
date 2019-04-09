package equipo8.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import equipo4.model.Lote;
import equipo6.model.Actor;

// Clase para parsear el .txt donde guardamos los datos recogidos por Arduino
public class Sensor {
	
	//El idSensor será el mismo que el numero de serie del Arduino al que corresponda
	private static int idSensor;
	
	//Aquí se encuentra el .txt con el registro
	private static BufferedReader log; 

	//Contiene cada linea del registro
	private static String linea;	
	
	public Sensor(int idSensor) {
		Sensor.idSensor=idSensor;
	}
	

	//Método que permite parsear "fichero" para crear Objeto Registro del mismo
	public  Registro crearRegistro(Lote lote, Actor actor,String fichero) throws IOException, ClassNotFoundException, SQLException {

		  try {
	        	//Da la ruta donde está "fichero"
		        URL fileUrl = getClass().getResource(fichero);
	            if(!actor.equals(null)&&!lote.equals(null)) {
					HashMap<Fecha,Integer> listaRegistros= new HashMap<Fecha,Integer>();
					log = new BufferedReader(new FileReader(fileUrl.toString().substring(5))); 
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
			
						Iterator<Fecha> it=listaRegistros.keySet().iterator();
			                        //Se guarda la temperatura mínima y máxima
						while(it.hasNext()) {
							Fecha f = it.next();
							int t= listaRegistros.get(f);
							if(t<Tmin)Tmin=t;
							else if (t>Tmax)Tmax=t;	
						}
			
					}
					// fecha contiene la fecha de fin de registro
					Registro registro= new Registro(lote, actor,  fechaInicio.toString(), fecha.toString(), Tmax, Tmin);
					return registro;
	        }
	        }
	        catch(  Exception ex) {
	        	
	        }
	        
		return new Registro("error");
	}
	
	
	// Método que EQUIPO TRANSPORTISTAS meta jsonRegistro en Pedido 
	public String jsonRegistro (Actor actor) throws Exception{
		   //TODO: Averiguar como se rellena el lote 
		   Lote lote=new Lote();
		   // De momento para el miercoles vamos a dejar un .txt llamado "datosSensorMiercoles.txt" en la carpeta donde
		   // los transportistas vayan a ejecutar el jsonRegistro y en la de Sensor.java
		   String ruta="datosSensorMiercoles.txt";
		   Registro registro = this.crearRegistro(lote, actor, ruta);
		   GsonBuilder builder = new GsonBuilder();
		   Gson gson = builder.setPrettyPrinting().create();    
	           return gson.toJson(registro);   
	}

	public static void setId (int id){
		Sensor.idSensor = id;
	}
	public static int getId (){
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

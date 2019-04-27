package equipo8.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


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
	

	//Método que permite parsear "fichero" para crear un Objeto Registro 
	public  Registro crearRegistro(int idOrdenTrazabilidad, int idPedido,String fichero) throws IOException{

		        	//Da la ruta donde está "fichero"
			        URL fileUrl = getClass().getResource(fichero);
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
					Registro registro= new Registro(idOrdenTrazabilidad, idPedido,  fechaInicio.toString(), fecha.toString(), Tmax, Tmin);
					return registro;
	        
		  
	}

	//Método que permite crear un Objeto Registro cada x minutos 
	public  void crearRegistroCadaXMinutos(int idOrdenTrazabilidad, int idPedido,String fichero,int cadaXMinutos) throws IOException {
		
		Timer minutosTranscurridos=new Timer();
		TimerTask timerTask = new TimerTask()
	     {
	         public void run() 
	         {
	 			try {
	 				/*
	 				if(llegamosALaHoraDeEntrega) 
	 					timerTask.cancel();
	 				*/
					crearRegistro(idOrdenTrazabilidad,idPedido,fichero);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         }
	     }; 
			minutosTranscurridos.schedule(timerTask, 0, cadaXMinutos*60000); 
	}

	/* forma de interrumpir el hilo de CrearRegistrosCadaXMinutos
	public void pararCreacionRegistros(String fichero) throws IOException {
		// File (or directory) with old name
		File ficheroAParar = new File(fichero);
		// File (or directory) with new name
		File ficheroParado = new File(fichero.substring(0,fichero.length()-4)+"FIN.txt");
		ficheroAParar.renameTo(ficheroParado);
	}
	*/
	

	
	// Método que EQUIPO TRANSPORTISTAS meta jsonRegistro en Pedido 
	public String jsonRegistro (int idOrdenTrazabilidad, int idPedido) throws Exception{
		 
		   String ruta="datosSensorMiercoles.txt";
		   Registro registro = this.crearRegistro(idOrdenTrazabilidad, idPedido, ruta);
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

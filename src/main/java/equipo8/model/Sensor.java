
package equipo8.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import com.fazecast.jSerialComm.SerialPort;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// Clase para parsear el .txt donde guardamos los datos recogidos por Arduino
public class Sensor{


	private int idSensor;
	private int idPedido;
	private int idOrdentrazabilidad;
	private Fecha fechainicio;
	//Aquí se encuentra el .txt con el registro
	private BufferedReader log; 

	//Contiene cada linea del registro
	private  String linea;

	//txt processing en java
	private SerialPort serialPort;
	private SerialPort[] ports;
	private File txt;
	private TimerTask taskreceive;
	private TimerTask taskcreate;
	private Timer timer;

	public Sensor(int idOrden,int idPedido) {
		this.serialPort = null;
		this.idPedido = idPedido;
		this.idOrdentrazabilidad = idOrden;
		Calendar date = Calendar.getInstance();
		this.fechainicio = new Fecha(date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH),date.get(Calendar.HOUR_OF_DAY),date.get(Calendar.MINUTE),date.get(Calendar.SECOND));
		
		//ports
		this.ports = SerialPort.getCommPorts();
		if(ports.length>0) {
			this.serialPort = this.ports[0];
		}
		
		System.out.println("Puerto seleccionado de forma predeterminada: " + this.serialPort.getSystemPortName());
		System.out.println("Puertos disponibles del sistema:");
		for (int i=0;i<ports.length; i++)
			System.out.println((i+1) + ") " + ports[i].getSystemPortName() + "\t" + ports[i].isOpen());
		
		//txt
		this.txt = new File("datosArduino"+ idOrden + ".txt");
		if(txt.exists()){
			txt.delete();
		}
		try {
			txt.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//timer tasks
		this.timer = new Timer();
		this.taskreceive = new TimerTask() {
			public void run() {
				recibirDatosArduino();
			}
		};
		this.taskcreate = new TimerTask() {
			public void run() {
				try {
					System.out.println(crearRegistro(idOrden, idPedido).toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		this.timer.schedule(taskreceive, 0, 5000);//cada 5 segs desde el principio
		this.timer.schedule(taskcreate, 60000,60000);//cada minuto a partir del primer minuto
		

	}

	
	
	
	public void selectPort(int port) {
		this.serialPort = this.ports[port-1];
		System.out.println("Puerto seleccionado: " + this.serialPort.getSystemPortName());
	}
	
	public void terminar() {
		this.timer.cancel();//paramos el recibo de datos
		System.out.println("Fin recibo de datos.");
		txt.delete();//borramos el txt del servidor
	}
	
	
	public void recibirDatosArduino() {

		if(serialPort==null) {
			System.err.println("No se ha seleccionado un puerto"); 
			System.exit(0);
		}

		String data = "";
		serialPort.openPort();
		while(serialPort.bytesAvailable()<1) {
			try {
				Thread.sleep(1200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		byte[] buffer = new byte[serialPort.bytesAvailable()];
		serialPort.readBytes(buffer, serialPort.bytesAvailable());
		for(int i=0; i<buffer.length;i++) {
			data += (char) buffer[i];
		}
		
		if(!data.isEmpty()) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss+");  
			LocalDateTime now = LocalDateTime.now();  
			try {
				BufferedWriter txtWrite = new BufferedWriter(new FileWriter(txt,true));
				txtWrite.write(dtf.format(now) + data + "\n");
				txtWrite.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		serialPort.closePort();
	}
	
	public void cambiarIntervalo(int millisleer,int milliscrear) {
		this.timer.cancel();
		this.timer.schedule(taskreceive, 0, millisleer);
		this.timer.schedule(taskcreate, milliscrear, milliscrear);
	}



	public  Registro crearRegistro(int idOrdentrazabilidad,int idPedido) throws IOException {
		String fileUrl = "datosArduino"+ idOrdentrazabilidad + ".txt";
		HashMap<Fecha,Integer> listaRegistros= new HashMap<Fecha,Integer>();
		log = new BufferedReader(new FileReader(fileUrl));
		Fecha fecha = null;
		int contador=0;
		int Tmax=0;
		int Tmin=0;
		while ((linea = log.readLine()) != null) {
			//Se saltan las lineas vacías
			if(!linea.isEmpty() && linea.contains("g")){
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

				temp=Integer.valueOf(fecha_temp[1].split("g")[0].trim());

				fecha = new Fecha (anio,mes,dia,hora,minuto,segundo);
				if (contador==0) {
					Tmax=temp;
					Tmin=temp;
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

		Registro registro= new Registro(idPedido, idOrdentrazabilidad,  this.fechainicio.toString(), fecha.toString(), Tmax, Tmin);
		log.close();
		return registro;
	}

	// Método que EQUIPO TRANSPORTISTAS meta jsonRegistro en Pedido 
	public String jsonRegistro (int idOrdenTrazabilidad, int idPedido) throws Exception{
		 
		   Registro registro = this.crearRegistro(idOrdenTrazabilidad, idPedido);
		   GsonBuilder builder = new GsonBuilder();
		   Gson gson = builder.setPrettyPrinting().create();    
	           return gson.toJson(registro);   
	}

	public void cerrarPuerto() {
		this.serialPort.closePort();
	}

	public void setID (int id){
		this.idSensor = id;
	}
	public int getID (){
		return idSensor;
	}

	public int getIdPedido() {
		return idPedido;
	}




	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public int getIdOrdentrazabilidad() {
		return idOrdentrazabilidad;
	}




	public void setIdOrdentrazabilidad(int idOrdentrazabilidad) {
		this.idOrdentrazabilidad = idOrdentrazabilidad;
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

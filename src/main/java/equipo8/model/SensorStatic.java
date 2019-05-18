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
import equipo8.model.SendEmail;

import equipo6.otros.BlockchainServices;

//Clase para parsear el .txt donde guardamos los datos recogidos por Arduino
public class SensorStatic{


	//private static int idSensor;
	private static int idPedido;
	private static int idOrdentrazabilidad;
	private static Fecha fechainicio;
	//Aquí se encuentra el .txt con el registro
	private static BufferedReader log; 

	//Contiene cada linea del registro
	private static String linea;

	//txt processing en java
	private static SerialPort serialPort;
	private static SerialPort[] ports;
	private static File txt;
	private static TimerTask taskreceive;
	private static TimerTask taskcreate;
	private static Timer timer;
	private static BlockchainServices blockchain = new BlockchainServices();
	private static boolean mailMandado = false;

	public static void iniciarTransporte(int idOrden, int idPed) {
//		serialPort = null;
//		Calendar date = Calendar.getInstance();
//		idPedido = idPed;
//		idOrdentrazabilidad = idOrden;
//		fechainicio = new Fecha(date.get(Calendar.YEAR),date.get(Calendar.MONTH)+1,date.get(Calendar.DAY_OF_MONTH),date.get(Calendar.HOUR_OF_DAY),date.get(Calendar.MINUTE),date.get(Calendar.SECOND));
//
//		//ports
//		ports = SerialPort.getCommPorts();
//		if(ports.length>0) {
//			serialPort = ports[0];
//		}
//
//		else {
//			System.err.print("No hay puertos");
//			return;
//		}
//		
//		System.out.println("Puerto seleccionado de forma predeterminada: " + serialPort.getSystemPortName());
//		System.out.println("Puertos disponibles del sistema:");
//		for (int i=0;i<ports.length; i++)
//			System.out.println((i+1) + ") " + ports[i].getSystemPortName() + "\t" + ports[i].isOpen());
//
//		//txt
//		txt = new File("datosArduino.txt");
//		if(txt.exists()){
//			txt.delete();
//		}
//		try {
//			txt.createNewFile();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		//timer tasks
//		timer = new Timer();
//		taskreceive = new TimerTask() {
//			public void run() {
//				recibirDatosArduino();
//			}
//		};
//		taskcreate = new TimerTask() {
//			public void run() {
//				try {
//					Registro reg = crearRegistro(idOrdentrazabilidad, idPedido);
//					System.out.println(reg);
//					try {
//						blockchain.guardarOrden(reg);
//					} catch (Throwable e) {
//						System.err.println("Error al enviar Registro al blockchain!");
//						e.printStackTrace();
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		};
//		timer.schedule(taskreceive, 0, 5000);//cada 5 segs desde el principio
//		timer.schedule(taskcreate, 20000,60000);//cada minuto a partir del primer minuto
		idPedido = idPed;
		idOrdentrazabilidad = idOrden;
	}




	public static void selectPort(int port) {
		serialPort = ports[port-1];
		System.out.println("Puerto seleccionado: " + serialPort.getSystemPortName());
	}

	public static int terminar() {
//		timer.cancel();//paramos el recibo de datos
//		System.out.println("Último registro:");
//		Registro reg = null;
//		try {
//			reg = crearRegistro(idOrdentrazabilidad,idPedido);
//			System.out.println(reg.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("Fin recibo de datos.");
//		txt.delete();//borramos el txt del servidor
//		return reg.getId();
		Registro reg = equipo5.dao.metodosCompany.extraerUltimoRegistro(idOrdentrazabilidad);
		if(reg==null) {
			return 0;
		}
		return reg.getId();
	}


	public static void recibirDatosArduino() {

		
		if(serialPort==null) {
			System.err.println("No se ha seleccionado un puerto"); 
			System.exit(0);
		}

		String data = "";
		serialPort.openPort();
		if(!serialPort.isOpen()) {
			System.err.println("Fallo de conexión!");
			terminar();
			System.exit(1);
		}
		while(serialPort.bytesAvailable()<1) {
			try {
				//System.out.println("waiting...");
				Thread.sleep(120);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("Received!");
		byte[] buffer = new byte[serialPort.bytesAvailable()];
		serialPort.readBytes(buffer, serialPort.bytesAvailable());
		for(int i=0; i<buffer.length;i++) {
			data += (char) buffer[i];
		}

		if(!data.isEmpty()) {
			//System.out.println("Data: " + data);
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

	public static int idUltimoRegistro(int idOrden) {
		try {
			return equipo5.dao.metodosCompany.extraerUltimoRegistro(idOrden).getId();
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static void cambiarIntervalo(int millisleer,int milliscrear) {
		if(millisleer <1500 || milliscrear < millisleer) {
			System.err.print("Intervalos no posibles");
			return;
		}
		
		timer.cancel();
		//timer tasks
		timer = new Timer();
		taskreceive = new TimerTask() {
			public void run() {
				recibirDatosArduino();
			}
		};
		taskcreate = new TimerTask() {
			public void run() {
				try {
					Registro reg = crearRegistro(idOrdentrazabilidad, idPedido);
					System.out.println(reg);
					try {
						blockchain.guardarOrden(reg);
					} catch (Throwable e) {
						System.err.println("Error al enviar Registro al blockchain!");
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		timer.schedule(taskreceive, 0, millisleer);
		timer.schedule(taskcreate, milliscrear, milliscrear);
		System.out.println("Cambio de Intervalo");
	}



	public static Registro crearRegistro(int idOrdentrazabilidad,int idPedido) throws IOException {
		String fileUrl = "datosArduino.txt";
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

		//Registro registro= new Registro(idPedido, idOrdentrazabilidad,  fechainicio.toString(), fecha.toString(), Tmax, Tmin);
		log.close();
		if(Tmax>=27 && !mailMandado) {
			new SendEmail("cb1508@hotmail.com","Alerta","Temperatura Excedida!",idPedido);
			mailMandado = true;
		}
		return new Registro(idPedido, idOrdentrazabilidad,  fechainicio.toString(), fecha.toString(), Tmax, Tmin);
	}

	// Método que EQUIPO TRANSPORTISTAS meta jsonRegistro en Pedido 
	public static String jsonRegistro (int idOrdenTrazabilidad, int idPedido) throws Exception{
		Registro registro = crearRegistro(idOrdenTrazabilidad, idPedido);
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.setPrettyPrinting().create();    
		return gson.toJson(registro);   
	}

	public static void cerrarPuerto() {
		serialPort.closePort();
	}

	//	public static void setID (int id){
	//		idSensor = id;
	//	}
	//	public int getID (){
	//		return idSensor;
	//	}

	public static int getIdPedido() {
		return idPedido;
	}

	public static void setIdPedido(int idPed) {
		idPedido = idPed;
	}

	public static int getIdOrdentrazabilidad() {
		return idOrdentrazabilidad;
	}

	public static void setIdOrdentrazabilidad(int idOrden) {
		idOrdentrazabilidad = idOrden;
	}

	//clase auxiliar que vamos a usar para facilitar 
	private static class Fecha {
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

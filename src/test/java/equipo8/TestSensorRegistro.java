package equipo8;

import equipo4.model.Lote;
import equipo6.model.Actor;
import equipo8.model.Registro;
import equipo8.model.Sensor;

public class TestSensorRegistro {

	
	//Ejemplo registro temperatura Arduino numero de serie 354325
	public static void main(String[] args) {
		
		// Objeto Sensor correspondiente
		int idSensor=354325;
		Sensor sensor = new Sensor(idSensor);
		

		Lote lote=new Lote();
		Actor actor=new Actor();
	
		
		Registro registroCorrecto,registroFicheroIncorrecto,registroLoteIncorrecto,registroActorIncorrecto;
		
		try {
			registroCorrecto = sensor.crearRegistro(lote,actor,"datosSensorMiercoles.txt");
			System.out.println("\nEJEMPLO TRAYECTO MIERCOLES: \n\n"+registroCorrecto.toString());
			
			registroFicheroIncorrecto = sensor.crearRegistro(lote,actor,"datosensor.txt");
			System.out.println("\nEJEMPLO FICHERO ERROR: \n\n"+registroFicheroIncorrecto.toString());
			
			registroLoteIncorrecto = sensor.crearRegistro(null,actor,"datosSensor.txt");
			System.out.println("\nEJEMPLO LOTE NULL: \n\n"+registroLoteIncorrecto.toString());
			
			registroActorIncorrecto = sensor.crearRegistro(lote,null,"datosSensor.txt");
			System.out.println("\nEJEMPLO ACTOR NULL: \n\n"+registroActorIncorrecto.toString());


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

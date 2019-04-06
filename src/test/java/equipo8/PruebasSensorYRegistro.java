package equipo8;

import equipo4.model.Lote;
import equipo6.model.Actor;
import equipo8.model.Registro;
import equipo8.model.Sensor;

public class PruebasSensorYRegistro {

	

	
	
	//Ejemplo registro temperatura del Lote id 54 en dos trayectos:
	public static void main(String[] args) {
		
		// Solo tenemos un sensor, id 1
		int idSensor=1;
		Sensor sensor = new Sensor(idSensor);
		
		// El Lote lo tiene Dani?
		Lote lote=new Lote(idSensor, idSensor, null, null, null, null);
		// El Actor lo tiene Dani?
		Actor actor=new Actor();
	
		// Registro de dos trayectos:
		Registro reg1,reg2;
		
		try {
			reg1 = sensor.crearRegistro(lote,actor,"datosSensor.txt");
			System.out.println("\nEJEMPLO 1: \n\n"+reg1.toString());

			reg2 = sensor.crearRegistro(lote,actor,"testTemp1Hora.txt");
			System.out.println("\n\nEJEMPLO 2: MEDICIÓN DURANTE 1H \n\n"+reg2.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

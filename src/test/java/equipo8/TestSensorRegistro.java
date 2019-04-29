package equipo8;

import equipo8.model.Sensor;

public class TestSensorRegistro {

	//private Timer t;

	//Ejemplo registro temperatura del Lote id 54 en dos trayectos:
	public static void main(String[] args) {

		Sensor sensor = new Sensor(45,98);
		sensor.selectPort(5);
		try {
			Thread.sleep(200000);//200 segs
			//Registro reg = sensor.crearRegistro(45, 98);
			sensor.terminar();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
